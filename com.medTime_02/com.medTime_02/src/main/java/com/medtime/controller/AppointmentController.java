package com.medtime.controller;

import com.medtime.model.Appointment;
import com.medtime.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestBody Appointment appointment,
            @RequestParam Long userId) {
        return ResponseEntity.ok(appointmentService.createAppointment(appointment, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment appointmentDetails) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, appointmentDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Appointment>> getUserAppointments(@PathVariable Long userId) {
        return ResponseEntity.ok(appointmentService.getUserAppointments(userId));
    }

    @GetMapping("/user/{userId}/between")
    public ResponseEntity<List<Appointment>> getUserAppointmentsBetweenDates(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(appointmentService.getUserAppointmentsBetweenDates(userId, start, end));
    }
} 