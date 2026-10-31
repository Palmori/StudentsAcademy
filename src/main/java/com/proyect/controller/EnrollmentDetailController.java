package com.proyect.controller;

import com.proyect.dto.EnrollmentDetailDTO;
import com.proyect.model.EnrollmentDetail;
import com.proyect.service.IEnrollmentDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/enrollment-details")
@RequiredArgsConstructor
public class EnrollmentDetailController {

    private final IEnrollmentDetailService service;
    private final ModelMapper defaultMapper;

    @GetMapping
    public ResponseEntity<List<EnrollmentDetailDTO>> findAll(){
        List<EnrollmentDetailDTO> list = service.findAll().stream().map(this::toDTO).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDetailDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(toDTO(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<EnrollmentDetailDTO> save(@Valid @RequestBody EnrollmentDetailDTO dto){
        EnrollmentDetail obj = service.save(toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(obj)); //201 CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDetailDTO> update(@PathVariable Integer id, @Valid @RequestBody EnrollmentDetailDTO dto) throws Exception {
        EnrollmentDetail obj = service.update(id, toEntity(dto));
        return ResponseEntity.ok(toDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build(); //204 NO CONTENT
    }

    private EnrollmentDetailDTO toDTO(EnrollmentDetail obj){
        return defaultMapper.map(obj, EnrollmentDetailDTO.class);
    }

    private EnrollmentDetail toEntity(EnrollmentDetailDTO dto) {
        return defaultMapper.map(dto, EnrollmentDetail.class);
    }

}
