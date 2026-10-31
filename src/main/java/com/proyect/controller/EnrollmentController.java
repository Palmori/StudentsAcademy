package com.proyect.controller;

import com.proyect.dto.EnrollmentDTO;
import com.proyect.model.Enrollment;
import com.proyect.service.IEnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final IEnrollmentService service;
    private final ModelMapper defaultMapper;

    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> findAll(){
        List<EnrollmentDTO> list = service.findAll().stream().map(this::toDTO).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(toDTO(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> save(@Valid @RequestBody EnrollmentDTO dto){
        Enrollment obj = service.save(toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(obj)); //201 CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> update(@PathVariable Integer id, @Valid @RequestBody EnrollmentDTO dto) throws Exception {
        Enrollment obj = service.update(id, toEntity(dto));
        return ResponseEntity.ok(toDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build(); //204 NO CONTENT
    }

    private EnrollmentDTO toDTO(Enrollment obj){
        return defaultMapper.map(obj, EnrollmentDTO.class);
    }

    private Enrollment toEntity(EnrollmentDTO dto) {
        return defaultMapper.map(dto, Enrollment.class);
    }

}
