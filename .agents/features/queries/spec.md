# Cursos con alumnos

## Goal
Exponer una consulta que devuelva la lista de cursos y, dentro de cada curso, los alumnos inscritos en él. Pensada para clientes de la API que necesitan ver la composición de cada curso en una sola llamada.

## Requirements
1. `GET /v1/courses/students` devuelve `200 OK` con un arreglo JSON de cursos.
2. Cada elemento tiene `idCourse`, `name`, `subjectCode`, `enabled` y `students` (arreglo de `StudentDTO`: `idStudent`, `firstName`, `lastName`, `dni`, `birthdate`).
3. Se devuelven **todos** los cursos, incluidos los deshabilitados (`enabled = false`).
4. Un curso sin alumnos aparece con `students: []`.
5. Se incluyen todos los enrollments, sin filtrar por `enabled`.
6. Un alumno aparece una sola vez por curso, aunque tenga varios detalles en ese curso.
7. Relación: `EnrollmentDetail` tiene un `@ManyToOne Course course` (columna `id_course`, NOT NULL, FK `fk_enrollment_detail_course`). Camino: `Course ← EnrollmentDetail → Enrollment → Student`.
8. `EnrollmentDetailDTO` incluye `course` (`@NotNull`). Al crear o actualizar un detalle, el cliente envía `"course": { "idCourse": 1 }`.
9. La consulta se resuelve con una única JPQL con `JOIN FETCH` (sin N+1) y la agrupación se hace en el servicio.

Ejemplo:
```json
[
  { "idCourse": 1, "name": "Matemáticas", "subjectCode": "MAT1", "enabled": true,
    "students": [ { "idStudent": 1, "firstName": "Ana", "lastName": "Pérez", "dni": 123, "birthdate": "2000-01-01" } ] },
  { "idCourse": 2, "name": "Física", "subjectCode": "FIS1", "enabled": false, "students": [] }
]
```

## Out of scope
- Paginación y ordenación configurable.
- Filtros por `enabled` (curso o enrollment).
- Consulta de un solo curso por id.

## Status
Done

## Implementation notes
- `model/EnrollmentDetail`: `@ManyToOne Course course` (`id_course`, NOT NULL, FK `fk_enrollment_detail_course`). `dto/EnrollmentDetailDTO`: `@NotNull CourseDTO course`.
- `repo/IEnrollmentDetailRepo.findAllWithCourseAndStudent()`: JPQL con `JOIN FETCH` de `course`, `enrollment` y `enrollment.student` (una sola consulta).
- `service/ICourseService.findAllWithStudents()` → `Map<Course, List<Student>>`. `CourseServiceImpl` parte de `repo.findAll()` en un `LinkedHashMap` (todos los cursos, lista vacía por defecto) y añade cada alumno una sola vez por curso. `@Transactional(readOnly = true)`.
- `dto/CourseStudentsDTO`: campos de `Course` + `List<StudentDTO> students`.
- `controller/CourseController`: `GET /v1/courses/students`; mapea el curso con `defaultMapper` y los alumnos a `StudentDTO`.
- Cambio de esquema: con `ddl-auto: update`, si `enrollment_detail` ya tiene filas, Postgres rechaza la nueva columna NOT NULL `id_course`. Vaciar la tabla o rellenar la columna antes de arrancar.
- Verificado: `./mvnw compile` OK. No se ha probado contra una base de datos real.
