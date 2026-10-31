package com.proyect.service.Impl;

import com.proyect.model.EnrollmentDetail;
import com.proyect.repo.IEnrollmentDetailRepo;
import com.proyect.repo.IGenericRepo;
import com.proyect.service.IEnrollmentDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentDetailServiceImpl extends CRUDImpl<EnrollmentDetail, Integer> implements IEnrollmentDetailService {

    private final IEnrollmentDetailRepo repo;

    @Override
    protected IGenericRepo<EnrollmentDetail, Integer> getRepo() {
        return repo;
    }
}
