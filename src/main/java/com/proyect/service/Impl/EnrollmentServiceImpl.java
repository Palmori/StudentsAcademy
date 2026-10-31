package com.proyect.service.Impl;

import com.proyect.model.Enrollment;
import com.proyect.repo.IEnrollmentRepo;
import com.proyect.repo.IGenericRepo;
import com.proyect.service.IEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl extends CRUDImpl<Enrollment, Integer> implements IEnrollmentService {

    private final IEnrollmentRepo repo;

    @Override
    protected IGenericRepo<Enrollment, Integer> getRepo() {
        return repo;
    }
}
