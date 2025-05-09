package com.godstime.dlcfLagos.web_app.services.impl;

import com.godstime.dlcfLagos.web_app.dto.UserDTO;
import com.godstime.dlcfLagos.web_app.exception.ResourceNotFoundException;
import com.godstime.dlcfLagos.web_app.models.Role;
import com.godstime.dlcfLagos.web_app.models.User;
import com.godstime.dlcfLagos.web_app.repositories.RoleRepository;
import com.godstime.dlcfLagos.web_app.repositories.UserRepository;
import com.godstime.dlcfLagos.web_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }
        
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setLga(userDTO.getLga());
        user.setCdsGroup(userDTO.getCdsGroup());
        user.setPlaceOfPrimaryAssignment(userDTO.getPlaceOfPrimaryAssignment());
        user.setUnitInFellowship(userDTO.getUnitInFellowship());
        user.setSpiritualStatus(userDTO.getSpiritualStatus());
        user.setZone(userDTO.getZone());
        user.setProfilePictureUrl(userDTO.getProfilePictureUrl());
        
        Set<Role> roles = new HashSet<>();
        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName(Role.ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            userDTO.getRoles().forEach(role -> {
                Role userRole = roleRepository.findByName(Role.ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role " + role + " is not found."));
                roles.add(userRole);
            });
        }
        user.setRoles(roles);
        
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }
    
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setLga(userDTO.getLga());
        user.setCdsGroup(userDTO.getCdsGroup());
        user.setPlaceOfPrimaryAssignment(userDTO.getPlaceOfPrimaryAssignment());
        user.setUnitInFellowship(userDTO.getUnitInFellowship());
        user.setSpiritualStatus(userDTO.getSpiritualStatus());
        user.setZone(userDTO.getZone());
        user.setProfilePictureUrl(userDTO.getProfilePictureUrl());
        
        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            userDTO.getRoles().forEach(role -> {
                Role userRole = roleRepository.findByName(Role.ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role " + role + " is not found."));
                roles.add(userRole);
            });
            user.setRoles(roles);
        }
        
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }
    
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    

    
    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        return convertToDTO(user);
    }
    
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<UserDTO> getUsersByLga(String lga) {
        return userRepository.findByLga(lga).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<UserDTO> getUsersByZone(String zone) {
        return userRepository.findByZone(zone).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<UserDTO> getUsersByCdsGroup(String cdsGroup) {
        return userRepository.findByCdsGroup(cdsGroup).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<UserDTO> getUsersByUnitInFellowship(String unitInFellowship) {
        return userRepository.findByUnitInFellowship(unitInFellowship).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    /**
     * Changes the password for the current authenticated user
     * @param currentPassword The user's current password
     * @param newPassword The new password to set
     * @return true if password was changed successfully, false otherwise
     */
    public boolean changePassword(String currentPassword, String newPassword) {
        User currentUser = getCurrentUser();
        
        // Verify current password
        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            return false;
        }
        
        // Set new password
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);
        return true;
    }
    
    /**
     * Resets a user's password to a new temporary password
     * @param email The email of the user whose password needs to be reset
     * @param newPassword The new temporary password
     * @return true if password was reset successfully, false otherwise
     */
    public boolean resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }
    
    /**
     * Sets the initial password for a new user
     * @param userId The ID of the user
     * @param initialPassword The initial password to set
     * @return true if password was set successfully, false otherwise
     */
    public boolean setInitialPassword(Long userId, String initialPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        
        // Check if user already has a password set
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            throw new RuntimeException("Error: User already has a password set.");
        }
        
        user.setPassword(passwordEncoder.encode(initialPassword));
        userRepository.save(user);
        return true;
    }
    
    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        return convertToDTO(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setLga(user.getLga());
        userDTO.setCdsGroup(user.getCdsGroup());
        userDTO.setPlaceOfPrimaryAssignment(user.getPlaceOfPrimaryAssignment());
        userDTO.setUnitInFellowship(user.getUnitInFellowship());
        userDTO.setSpiritualStatus(user.getSpiritualStatus());
        userDTO.setZone(user.getZone());
        userDTO.setProfilePictureUrl(user.getProfilePictureUrl());
        
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
        userDTO.setRoles(roles);
        
        return userDTO;
    }
} 