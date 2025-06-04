package com.medTime.repository;

import com.medTime.model.Appointment;
import com.medTime.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorAndAppointmentDateTimeBetween(
        User doctor, 
        LocalDateTime start, 
        LocalDateTime end
    );

    List<Appointment> findByPatient(User patient);

    @Query("SELECT a FROM Appointment a WHERE a.doctor = :doctor AND a.appointmentDateTime >= :startTime AND a.appointmentDateTime < :endTime AND a.status = 'SCHEDULED'")
    List<Appointment> findDoctorAppointmentsForDay(User doctor, LocalDateTime startTime, LocalDateTime endTime);
} 