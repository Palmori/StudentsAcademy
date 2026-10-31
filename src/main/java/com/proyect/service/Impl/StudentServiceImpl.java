package com.proyect.service.Impl;

import com.proyect.model.Student;
import com.proyect.repo.IGenericRepo;
import com.proyect.repo.IStudentRepo;
import com.proyect.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDImpl<Student, Integer> implements IStudentService {

    private final IStudentRepo repo;

    @Override
    protected IGenericRepo<Student, Integer> getRepo() {
        return repo;
    }
}
