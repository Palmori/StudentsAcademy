-- =====================================================================
-- students-academic: sample data inserts (PostgreSQL)
-- Run AFTER script.sql (the schema must already exist).
--
-- Ids are not hardcoded: they come from the identity columns, and child
-- rows look up their parents by natural key with subqueries:
--   student            -> dni
--   course             -> subject_code
--   enrollment         -> student dni + date
--   enrollment_detail  -> (enrollment as above) + classroom
-- This keeps the identity sequences in sync with the data, so the
-- application can keep inserting rows without duplicate-key errors.
-- =====================================================================

BEGIN;

-- ---------------------------------------------------------------------
-- student (no dependencies)
-- ---------------------------------------------------------------------
INSERT INTO student (first_name, last_name, dni, birthdate) VALUES
    ('Lucía',     'Ramírez Torres',    45872319, '2003-05-14'),
    ('Diego',     'Mamani Quispe',     71203846, '2004-09-02'),
    ('Valeria',   'Huamán Ccoyllo',    60915427, '2002-11-21'),
    ('Sebastián', 'Flores Vargas',     48230915, '2005-01-08'),
    ('Camila',    'Rojas Medina',      73568102, '2006-07-19'),
    ('Andrés',    'Castillo Pinedo',   56117783, '2001-03-30'),
    ('Fiorella',  'Tello Salazar',     69480215, '2003-12-11'),
    ('Renzo',     'Chávez Huanca',     40792658, '1999-06-25'),
    ('Mariana',   'Vilca Cárdenas',    77134590, '2007-02-17'),
    ('Joaquín',   'Peralta Ortiz',     52806341, '2000-10-05');

-- ---------------------------------------------------------------------
-- course (no dependencies)
-- ---------------------------------------------------------------------
INSERT INTO course (name, subject_code, enabled) VALUES
    ('Programación Orientada a Objetos', 'POO-101', true),
    ('Cálculo Diferencial',              'MAT-102', true),
    ('Base de Datos',                    'BDD-201', true),
    ('Estructuras de Datos',             'EDD-203', true),
    ('Metodología de la Investigación',  'MET-110', true),
    ('Física General',                   'FIS-105', false);

-- ---------------------------------------------------------------------
-- enrollment (depends on student)
-- ---------------------------------------------------------------------
INSERT INTO enrollment (date, enabled, id_student) VALUES
    ('2026-03-02 09:30:00', true,  (SELECT id_student FROM student WHERE dni = 45872319)),
    ('2026-03-02 10:15:00', true,  (SELECT id_student FROM student WHERE dni = 71203846)),
    ('2026-03-02 11:00:00', true,  (SELECT id_student FROM student WHERE dni = 60915427)),
    ('2026-03-03 08:00:00', true,  (SELECT id_student FROM student WHERE dni = 48230915)),
    ('2026-03-03 09:00:00', true,  (SELECT id_student FROM student WHERE dni = 73568102)),
    ('2026-03-04 14:30:00', false, (SELECT id_student FROM student WHERE dni = 56117783)),
    ('2026-03-05 16:00:00', true,  (SELECT id_student FROM student WHERE dni = 69480215)),
    ('2026-03-06 09:30:00', true,  (SELECT id_student FROM student WHERE dni = 40792658)),
    ('2026-03-06 11:45:00', true,  (SELECT id_student FROM student WHERE dni = 77134590)),
    ('2026-03-09 10:00:00', true,  (SELECT id_student FROM student WHERE dni = 52806341)),
    ('2026-08-10 09:30:00', true,  (SELECT id_student FROM student WHERE dni = 45872319)),
    ('2026-08-11 10:15:00', true,  (SELECT id_student FROM student WHERE dni = 71203846));

-- ---------------------------------------------------------------------
-- enrollment_detail (depends on enrollment and course)
-- ---------------------------------------------------------------------
INSERT INTO enrollment_detail (classroom, id_enrollment, id_course) VALUES
    ('A-101',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 45872319 AND e.date = '2026-03-02 09:30:00'),
     (SELECT id_course FROM course WHERE subject_code = 'POO-101')),
    ('A-102',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 45872319 AND e.date = '2026-03-02 09:30:00'),
     (SELECT id_course FROM course WHERE subject_code = 'MAT-102')),
    ('B-201',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 71203846 AND e.date = '2026-03-02 10:15:00'),
     (SELECT id_course FROM course WHERE subject_code = 'POO-101')),
    ('B-202',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 71203846 AND e.date = '2026-03-02 10:15:00'),
     (SELECT id_course FROM course WHERE subject_code = 'BDD-201')),
    ('A-103',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 60915427 AND e.date = '2026-03-02 11:00:00'),
     (SELECT id_course FROM course WHERE subject_code = 'EDD-203')),
    ('C-301',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 48230915 AND e.date = '2026-03-03 08:00:00'),
     (SELECT id_course FROM course WHERE subject_code = 'MAT-102')),
    ('C-302',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 73568102 AND e.date = '2026-03-03 09:00:00'),
     (SELECT id_course FROM course WHERE subject_code = 'MET-110')),
    ('A-104',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 56117783 AND e.date = '2026-03-04 14:30:00'),
     (SELECT id_course FROM course WHERE subject_code = 'BDD-201')),
    ('B-203',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 69480215 AND e.date = '2026-03-05 16:00:00'),
     (SELECT id_course FROM course WHERE subject_code = 'POO-101')),
    ('D-101',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 40792658 AND e.date = '2026-03-06 09:30:00'),
     (SELECT id_course FROM course WHERE subject_code = 'EDD-203')),
    ('D-102',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 77134590 AND e.date = '2026-03-06 11:45:00'),
     (SELECT id_course FROM course WHERE subject_code = 'MET-110')),
    ('A-105',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 52806341 AND e.date = '2026-03-09 10:00:00'),
     (SELECT id_course FROM course WHERE subject_code = 'MAT-102')),
    ('A-101',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 45872319 AND e.date = '2026-08-10 09:30:00'),
     (SELECT id_course FROM course WHERE subject_code = 'BDD-201')),
    ('B-201',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 71203846 AND e.date = '2026-08-11 10:15:00'),
     (SELECT id_course FROM course WHERE subject_code = 'EDD-203')),
    ('B-202',
     (SELECT e.id_enrollment FROM enrollment e JOIN student s ON s.id_student = e.id_student
       WHERE s.dni = 71203846 AND e.date = '2026-08-11 10:15:00'),
     (SELECT id_course FROM course WHERE subject_code = 'MET-110'));

COMMIT;
