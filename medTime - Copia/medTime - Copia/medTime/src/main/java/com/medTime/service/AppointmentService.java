package com.medTime.service;

import com.medTime.DTO.AppointmentDTO;
import com.medTime.model.Appointment;
import com.medTime.model.User;
import com.medTime.model.UserType;
import com.medTime.repository.AppointmentRepository;
import com.medTime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    public List<User> getDoctorsBySpecialization(Long specializationId) {
        return userRepository.findByUserTypeAndSpecialization_Id(UserType.DOCTOR, specializationId);
    }

    public List<LocalDateTime> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        // Buscar consultas já agendadas para o médico neste dia
        List<Appointment> existingAppointments = appointmentRepository
                .findDoctorAppointmentsForDay(doctor, startOfDay, endOfDay);

        // Gerar todos os horários possíveis do dia (considerando intervalo de 45 minutos)
        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalDateTime currentSlot = date.atTime(8, 0); // Começa às 8h
        LocalDateTime endTime = date.atTime(17, 0); // Termina às 17h

        while (currentSlot.plusMinutes(45).isBefore(endTime) || currentSlot.plusMinutes(45).equals(endTime)) {
            final LocalDateTime slotToCheck = currentSlot;
            boolean isSlotAvailable = existingAppointments.stream()
                    .noneMatch(app -> app.getAppointmentDateTime().equals(slotToCheck));

            if (isSlotAvailable) {
                availableSlots.add(currentSlot);
            }
            currentSlot = currentSlot.plusMinutes(45);
        }

        return availableSlots;
    }

    public Appointment createAppointment(Long patientId, AppointmentDTO appointmentDTO) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        User doctor = userRepository.findById(appointmentDTO.doctorId())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        // Verificar se o horário está disponível
        LocalDateTime appointmentTime = appointmentDTO.appointmentDateTime();
        List<Appointment> existingAppointments = appointmentRepository
                .findDoctorAppointmentsForDay(doctor, appointmentTime, appointmentTime.plusMinutes(45));

        if (!existingAppointments.isEmpty()) {
            throw new RuntimeException("Horário não disponível");
        }

        Appointment appointment = new Appointment(patient, doctor, appointmentTime);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getPatientAppointments(Long patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        return appointmentRepository.findByPatient(patient);
    }
} 