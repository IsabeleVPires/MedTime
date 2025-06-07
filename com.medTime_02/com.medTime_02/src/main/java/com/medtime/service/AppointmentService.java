package com.medtime.service;

import com.medtime.model.Appointment;
import com.medtime.model.User;
import com.medtime.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserService userService;

    public Appointment createAppointment(Appointment appointment, Long userId) {
        User user = userService.getUserById(userId);
        
        if (appointmentRepository.existsByUserAndAppointmentDate(user, appointment.getAppointmentDate())) {
            throw new IllegalArgumentException("You already have an appointment scheduled for this time");
        }

        appointment.setUser(user);
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
        appointment.setSpecialty(appointmentDetails.getSpecialty());
        appointment.setNotes(appointmentDetails.getNotes());
        appointment.setStatus(appointmentDetails.getStatus());

        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
    }

    public List<Appointment> getUserAppointments(Long userId) {
        User user = userService.getUserById(userId);
        return appointmentRepository.findByUser(user);
    }

    public List<Appointment> getUserAppointmentsBetweenDates(Long userId, LocalDateTime start, LocalDateTime end) {
        User user = userService.getUserById(userId);
        return appointmentRepository.findByUserAndAppointmentDateBetween(user, start, end);
    }
} 