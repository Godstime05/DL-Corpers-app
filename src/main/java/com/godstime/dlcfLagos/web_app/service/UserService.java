package com.godstime.dlcfLagos.web_app.service;

import com.godstime.dlcfLagos.web_app.dto.UserDTO;
import com.godstime.dlcfLagos.web_app.model.User;

import java.util.List;

public interface UserService {
    
    UserDTO createUser(UserDTO userDTO);
    
    UserDTO updateUser(Long id, UserDTO userDTO);
    
    void deleteUser(Long id);
    
    UserDTO getUserByEmail(String email);
    
    User findUserByEmail(String email);
    
    List<UserDTO> getAllUsers();
    
    List<UserDTO> getUsersByLga(String lga);
    
    List<UserDTO> getUsersByZone(String zone);
    
    List<UserDTO> getUsersByCdsGroup(String cdsGroup);
    
    List<UserDTO> getUsersByUnitInFellowship(String unitInFellowship);
    
    User getCurrentUser();
    
    boolean existsByEmail(String email);
    
    /**
     * Changes the password for the current authenticated user
     * @param currentPassword The user's current password
     * @param newPassword The new password to set
     * @return true if password was changed successfully, false otherwise
     */
    boolean changePassword(String currentPassword, String newPassword);
    
    /**
     * Resets a user's password to a new temporary password
     * @param email The email of the user whose password needs to be reset
     * @param newPassword The new temporary password
     * @return true if password was reset successfully, false otherwise
     */
    boolean resetPassword(String email, String newPassword);
    
    /**
     * Sets the initial password for a new user
     * @param userId The ID of the user
     * @param initialPassword The initial password to set
     * @return true if password was set successfully, false otherwise
     */
    boolean setInitialPassword(Long userId, String initialPassword);

    User findUserById(Long id);

    UserDTO getUserById(Long id);


} 