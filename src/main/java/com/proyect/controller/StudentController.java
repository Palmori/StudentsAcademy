package com.proyect.controller;

import com.proyect.dto.StudentDTO;
import com.proyect.model.Student;
import com.proyect.service.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final IStudentService service;
    private final ModelMapper defaultMapper;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> findAll(){
        List<StudentDTO> list = service.findAll().stream().map(this::toDTO).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(toDTO(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> save(@Valid @RequestBody StudentDTO dto){
        Student obj = service.save(toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(obj)); //201 CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable Integer id, @Valid @RequestBody StudentDTO dto) throws Exception {
        Student obj = service.update(id, toEntity(dto));
        return ResponseEntity.ok(toDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build(); //204 NO CONTENT
    }

    private StudentDTO toDTO(Student obj){
        return defaultMapper.map(obj, StudentDTO.class);
    }

    private Student toEntity(StudentDTO dto) {
        return defaultMapper.map(dto, Student.class);
    }

}
