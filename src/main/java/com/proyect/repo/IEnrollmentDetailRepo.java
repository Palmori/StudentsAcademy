package com.proyect.repo;

import com.proyect.model.EnrollmentDetail;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEnrollmentDetailRepo extends IGenericRepo<EnrollmentDetail, Integer> {

    @Query("SELECT d FROM EnrollmentDetail d JOIN FETCH d.course JOIN FETCH d.enrollment e JOIN FETCH e.student")
    List<EnrollmentDetail> findAllWithCourseAndStudent();
}
