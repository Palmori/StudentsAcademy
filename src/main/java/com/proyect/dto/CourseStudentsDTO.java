package com.proyect.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseStudentsDTO {

    private Integer idCourse;

    private String name;

    private String subjectCode;

    private Boolean enabled;

    private List<StudentDTO> students;
}