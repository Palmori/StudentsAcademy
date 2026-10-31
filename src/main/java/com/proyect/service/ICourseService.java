package com.proyect.service;

import com.proyect.model.Course;
import com.proyect.model.Student;

import java.util.List;
import java.util.Map;

public interface ICourseService extends ICRUD<Course, Integer> {

    Map<Course, List<Student>> findAllWithStudents();
}