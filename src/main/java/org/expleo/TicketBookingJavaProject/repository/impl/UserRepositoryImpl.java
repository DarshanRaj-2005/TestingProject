/*
 * FILE: UserRepositoryImpl.java
 * PURPOSE: Handles all user database operations.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Static methods
 * - Data Access Object (DAO) Pattern: Direct database access
 * 
 * WHAT THIS FILE DOES:
 * - Create, Read, Update, Delete users
 * - Create default Super Admin
 * - Update user profiles
 * - Find users by email or ID
 * 
 * DATABASE TABLE: users
 */
package org.expleo.TicketBookingJavaProject.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.config.DBConnection;

/*
 * Repository implementation for User database operations.
 * Handles all CRUD operations for users.
 */
public class UserRepositoryImpl {

    // Static initializer - Runs once when class is loaded
    // Creates default Super Admin if not exists
    static {
        initializeDefaultAdmin();
    }

    /*
     * initializeDefaultAdmin - Creates admin account if not exists
     * 
     * Default credentials:
     * - Email: admin@gmail.com
     * - Password: admin123
     * - Role: Super Admin
     */
    private static void initializeDefaultAdmin() {
        try (Connection conn = DBConnection.getConnection()) {
            // Check if admin exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            checkStmt.setString(1, "admin@gmail.com");
            ResultSet rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                // Create default admin
                PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO users (name, email, phone, password, role, theatre_id) VALUES (?, ?, ?, ?, ?, 0)");
                insertStmt.setString(1, "Super Admin");
                insertStmt.setString(2, "admin@gmail.com");
                insertStmt.setString(3, "1234567890");
                insertStmt.setString(4, "admin123");
                insertStmt.setString(5, "Super Admin");
                insertStmt.executeUpdate();
                System.out.println("Default Super Admin created!");
            }
        } catch (SQLException e) {
            System.out.println("Warning: Could not initialize default admin: " + e.getMessage());
        }
    }

    /*
     * getTheatreIdSafe - Gets theatre_id safely
     * 
     * Handles case where column doesn't exist in old databases.
     */
    private static int getTheatreIdSafe(ResultSet rs) {
        try {
            return rs.getInt("theatre_id");
        } catch (SQLException e) {
            return 0;
        }
    }

    /*
     * getAllUsers - Gets all users from database
     * 
     * Returns: List of all User objects
     */
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                user.setTheatreId(getTheatreIdSafe(rs));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    /*
     * getUserById - Gets user by ID
     * 
     * Returns: User object or null
     */
    public static User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                user.setTheatreId(getTheatreIdSafe(rs));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /*
     * getUserByEmail - Gets user by email
     * 
     * Used for login and registration validation.
     * Returns: User object or null
     */
    public static User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                user.setTheatreId(getTheatreIdSafe(rs));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user by email: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /*
     * addUser - Adds new user to database
     * 
     * Sets the userId on the object after insertion.
     */
    public static void addUser(User user) {
        // Try with theatre_id column first
        try {
            String query = "INSERT INTO users (name, email, phone, password, role, theatre_id) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPhone());
                stmt.setString(4, user.getPassword());
                stmt.setString(5, user.getRole());
                stmt.setInt(6, user.getTheatreId());
                
                stmt.executeUpdate();
                
                // Get generated ID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            // Fallback: try without theatre_id column
            try {
                String query = "INSERT INTO users (name, email, phone, password, role) VALUES (?, ?, ?, ?, ?)";
                
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    
                    stmt.setString(1, user.getName());
                    stmt.setString(2, user.getEmail());
                    stmt.setString(3, user.getPhone());
                    stmt.setString(4, user.getPassword());
                    stmt.setString(5, user.getRole());
                    
                    stmt.executeUpdate();
                    
                    // Get generated ID
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            user.setUserId(generatedKeys.getInt(1));
                        }
                    }
                }
            } catch (SQLException e2) {
                System.out.println("Error adding user: " + e2.getMessage());
            }
        }
    }

    /*
     * updateUser - Updates user information
     */
    public static void updateUser(int userId, User user) {
        try {
            String query = "UPDATE users SET name = ?, email = ?, phone = ?, password = ?, role = ?, theatre_id = ? WHERE user_id = ?";
            
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPhone());
                stmt.setString(4, user.getPassword());
                stmt.setString(5, user.getRole());
                stmt.setInt(6, user.getTheatreId());
                stmt.setInt(7, userId);
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User updated successfully!");
                } else {
                    System.out.println("User not found!");
                }
            }
        } catch (SQLException e) {
            // Fallback without theatre_id
            try {
                String query = "UPDATE users SET name = ?, email = ?, phone = ?, password = ?, role = ? WHERE user_id = ?";
                
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    
                    stmt.setString(1, user.getName());
                    stmt.setString(2, user.getEmail());
                    stmt.setString(3, user.getPhone());
                    stmt.setString(4, user.getPassword());
                    stmt.setString(5, user.getRole());
                    stmt.setInt(6, userId);
                    
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("User updated successfully!");
                    } else {
                        System.out.println("User not found!");
                    }
                }
            } catch (SQLException e2) {
                System.out.println("Error updating user: " + e2.getMessage());
            }
        }
    }

    /*
     * updateProfile - Updates name, phone, password only
     */
    public static void updateProfile(User user) {
        String query = "UPDATE users SET name = ?, phone = ?, password = ? WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getUserId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Profile updated successfully in database!");
            } else {
                System.out.println("Failed to update profile!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating profile: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * updateUserTheatre - Updates theatre assignment
     */
    public static void updateUserTheatre(int userId, int theatreId) {
        String query = "UPDATE users SET theatre_id = ? WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, theatreId);
            stmt.setInt(2, userId);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating user theatre: " + e.getMessage());
        }
    }

    /*
     * removeUser - Deletes user from database
     */
    public static void removeUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("User removed successfully!");
            } else {
                System.out.println("User not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error removing user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
