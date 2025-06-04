package com.medTime.DTO;

import java.time.LocalDateTime;

public record AppointmentDTO(
    Long doctorId,
    LocalDateTime appointmentDateTime
) {} 