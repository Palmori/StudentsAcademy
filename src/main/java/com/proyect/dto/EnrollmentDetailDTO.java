package com.proyect.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollmentDetailDTO {

    private Integer idEnrollmentDetail;

    @NotNull
    @Size(min = 1, max = 10)
    private String classroom;

    @NotNull
    private EnrollmentDTO enrollment;

    @NotNull
    private CourseDTO course;
}
