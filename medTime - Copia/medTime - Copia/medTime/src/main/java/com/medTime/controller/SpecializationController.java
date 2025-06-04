package com.medTime.controller;

import com.medTime.model.Specialization;
import com.medTime.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specializations")
public class SpecializationController {

    @Autowired
    private SpecializationRepository specializationRepository;

    @PostMapping
    public ResponseEntity<Specialization> createSpecialization(@RequestBody String name) {
        try {
            Specialization specialization = new Specialization(name);
            Specialization saved = specializationRepository.save(specialization);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        try {
            List<Specialization> specializations = specializationRepository.findAll();
            return new ResponseEntity<>(specializations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 