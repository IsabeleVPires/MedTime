package com.medTime.service;

import com.medTime.DTO.DoctorDTO;
import com.medTime.DTO.UserDTO;
import com.medTime.model.Specialization;
import com.medTime.model.User;
import com.medTime.model.UserType;
import com.medTime.repository.SpecializationRepository;
import com.medTime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SpecializationRepository specializationRepository;

    public void createUser(UserDTO userDTO) {
        User newUser = new User(userDTO.name(), userDTO.email(), userDTO.password());
        newUser.setUserType(UserType.PATIENT);
        userRepository.save(newUser);
    }

    public void createDoctor(DoctorDTO doctorDTO) {
        Specialization specialization = specializationRepository.findById(doctorDTO.specializationId())
            .orElseThrow(() -> new RuntimeException("Especialização não encontrada"));

        User newDoctor = new User(
            doctorDTO.name(),
            doctorDTO.email(),
            doctorDTO.password(),
            doctorDTO.crm(),
            specialization
        );
        userRepository.save(newDoctor);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    public void updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        
        userRepository.save(user);
    }
}
