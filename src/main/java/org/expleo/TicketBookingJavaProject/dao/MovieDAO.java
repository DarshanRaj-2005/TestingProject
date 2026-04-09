/*
 * FILE: MovieDAO.java
 * PURPOSE: Interface for movie database operations.
 * 
 * OOPS CONCEPTS USED:
 * - Interface: Defines contract for movie operations
 * 
 * Note: Implementation is in MovieRepositoryImpl.
 */
package org.expleo.TicketBookingJavaProject.dao;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Movie;

/*
 * Data Access Object interface for Movie operations.
 * Defines the contract for movie-related database operations.
 */
public interface MovieDAO {
    
    // Get all movies
    List<Movie> getAllMovies();
    
    // Get movie by ID
    Movie getMovieById(String movieId);
    
    // Add new movie
    void addMovie(Movie movie);
    
    // Update movie
    void updateMovie(String movieId, Movie movie);
    
    // Delete movie
    void deleteMovie(String movieId);
    
    // Get movies for a theatre
    List<Movie> getMoviesByTheatre(int theatreId);
    
    // Search by title
    List<Movie> searchByTitle(String title);
    
    // Search by language
    List<Movie> searchByLanguage(String language);
}
