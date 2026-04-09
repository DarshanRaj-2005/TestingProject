/*
 * FILE: TheatreDAO.java
 * PURPOSE: Interface for theatre database operations.
 * 
 * OOPS CONCEPTS USED:
 * - Interface: Defines contract for theatre operations
 * 
 * Note: Implementation is in TheatreRepositoryImpl.
 */
package org.expleo.TicketBookingJavaProject.dao;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Theatre;

/*
 * Data Access Object interface for Theatre operations.
 * Defines the contract for theatre-related database operations.
 */
public interface TheatreDAO {
    
    // Get all theatres
    List<Theatre> getAllTheatres();
    
    // Get theatre by ID
    Theatre getTheatreById(int theatreId);
    
    // Add new theatre
    void addTheatre(Theatre theatre);
    
    // Update theatre
    void updateTheatre(int theatreId, Theatre theatre);
    
    // Delete theatre
    void deleteTheatre(int theatreId);
    
    // Get theatres by city
    List<Theatre> getTheatresByCity(String city);
    
    // Get all cities
    List<String> getAllCities();
    
    // Assign admin to theatre
    void updateTheatreAdmin(int theatreId, int adminId);
}
