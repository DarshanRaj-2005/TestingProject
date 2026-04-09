/*
 * FILE: UserDAO.java
 * PURPOSE: Interface for user database operations.
 * 
 * OOPS CONCEPTS USED:
 * - Interface: Defines contract for user operations
 * - Abstraction: Hides implementation details
 * 
 * Note: Implementation is in UserRepositoryImpl.
 */
package org.expleo.TicketBookingJavaProject.dao;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.User;

/*
 * Data Access Object interface for User operations.
 * Defines the contract for user-related database operations.
 */
public interface UserDAO {
    
    // Get all users
    List<User> getAllUsers();
    
    // Get user by ID
    User getUserById(int userId);
    
    // Get user by email
    User getUserByEmail(String email);
    
    // Add new user
    void addUser(User user);
    
    // Update existing user
    void updateUser(int userId, User user);
    
    // Delete user
    void deleteUser(int userId);
    
    // Update profile (name, phone, password)
    void updateProfile(User user);
    
    // Authenticate user
    User authenticate(String email, String password);
}
