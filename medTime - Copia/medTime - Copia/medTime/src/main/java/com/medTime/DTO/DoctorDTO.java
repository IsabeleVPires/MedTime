package com.medTime.DTO;

public record DoctorDTO(
    String name,
    String email,
    String password,
    String crm,
    Long specializationId
) {} 