package com.medTime.controller;

import com.medTime.DTO.AppointmentDTO;
import com.medTime.model.Appointment;
import com.medTime.model.User;
import com.medTime.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/doctors/{specializationId}")
    public ResponseEntity<List<User>> getDoctorsBySpecialization(@PathVariable Long specializationId) {
        try {
            List<User> doctors = appointmentService.getDoctorsBySpecialization(specializationId);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/available-slots/{doctorId}")
    public ResponseEntity<List<LocalDateTime>> getAvailableTimeSlots(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<LocalDateTime> slots = appointmentService.getAvailableTimeSlots(doctorId, date);
            return ResponseEntity.ok(slots);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/schedule/{patientId}")
    public ResponseEntity<Appointment> scheduleAppointment(
            @PathVariable Long patientId,
            @RequestBody AppointmentDTO appointmentDTO) {
        try {
            Appointment appointment = appointmentService.createAppointment(patientId, appointmentDTO);
            return ResponseEntity.ok(appointment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getPatientAppointments(@PathVariable Long patientId) {
        try {
            List<Appointment> appointments = appointmentService.getPatientAppointments(patientId);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 