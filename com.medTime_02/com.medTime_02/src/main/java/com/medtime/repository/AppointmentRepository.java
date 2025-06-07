package com.medtime.repository;

import com.medtime.model.Appointment;
import com.medtime.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUser(User user);
    List<Appointment> findByUserAndAppointmentDateBetween(User user, LocalDateTime start, LocalDateTime end);
    boolean existsByUserAndAppointmentDate(User user, LocalDateTime appointmentDate);
} 