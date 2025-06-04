package com.medTime.repository;

import com.medTime.model.User;
import com.medTime.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findByUserTypeAndSpecialization_Id(UserType userType, Long specializationId);

}