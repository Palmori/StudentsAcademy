package com.proyect.service.Impl;

import com.proyect.model.Course;
import com.proyect.model.EnrollmentDetail;
import com.proyect.model.Student;
import com.proyect.repo.ICourseRepo;
import com.proyect.repo.IEnrollmentDetailRepo;
import com.proyect.repo.IGenericRepo;
import com.proyect.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends CRUDImpl<Course, Integer> implements ICourseService {

    private final ICourseRepo repo;
    private final IEnrollmentDetailRepo enrollmentDetailRepo;

    @Override
    protected IGenericRepo<Course, Integer> getRepo() {
        return repo;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Course, List<Student>> findAllWithStudents() {
        // Start from every course so the ones without students are kept with an empty list
        Map<Course, List<Student>> result = new LinkedHashMap<>();
        repo.findAll().forEach(course -> result.put(course, new ArrayList<>()));

        for (EnrollmentDetail detail : enrollmentDetailRepo.findAllWithCourseAndStudent()) {
            List<Student> students = result.computeIfAbsent(detail.getCourse(), _ -> new ArrayList<>());
            Student student = detail.getEnrollment().getStudent();
            if (!students.contains(student)) {
                students.add(student);
            }
        }

        return result;
    }
}