/*
 * FILE: OfficerController.java
 * PURPOSE: Handles Officer operations.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields
 * - Composition: Uses MovieController and BookingController
 * 
 * WHAT THIS FILE DOES:
 * - View movies in their assigned theatre
 * - Book tickets (only for their theatre)
 * - Cancel tickets
 * 
 * WHO USES THIS:
 * - Officer only (role: "Officer")
 * 
 * IMPORTANT:
 * Officers can ONLY book tickets for their assigned theatre.
 * They cannot view or manage movies in other theatres.
 */
package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;

/*
 * Controller for Officer operations.
 * Officers can view movies and manage bookings for their assigned theatre.
 */
public class OfficerController {

    // Reference to MovieController
    private MovieController movieController;
    
    // Reference to BookingController
    private BookingController bookingController;

    /*
     * Constructor - Sets up the controller
     */
    public OfficerController(MovieController movieController, BookingController bookingController) {
        this.movieController = movieController;
        this.bookingController = bookingController;
    }

    /*
     * viewMovies - Shows movies at officer's theatre
     */
    public void viewMovies(User officerUser) {
        Theatre theatre = TheatreRepositoryImpl.getTheatreById(officerUser.getTheatreId());
        if (theatre != null) {
            System.out.println("\nMovies at " + theatre.getName() + " (" + theatre.getCity() + "):");
            movieController.viewMoviesForTheatre(theatre.getId());
        } else {
            System.out.println("Error: No theatre assigned to you!");
        }
    }

    /*
     * viewAllMovies - Shows all movies (for general viewing)
     */
    public void viewAllMovies() {
        movieController.viewMovies();
    }

    /*
     * bookTicket - Books a ticket for officer's theatre
     * 
     * Officer can only book for their assigned theatre.
     */
    public void bookTicket(User officerUser) {
        Theatre theatre = TheatreRepositoryImpl.getTheatreById(officerUser.getTheatreId());
        
        if (theatre == null) {
            System.out.println("\nError: You are not assigned to any theatre!");
            System.out.println("Please contact your Theatre Admin.");
            return;
        }
        
        System.out.println("\n--- BOOKING TICKET ---");
        System.out.println("Booking for: " + theatre.getName() + " (" + theatre.getCity() + ")");
        
        // Book ticket for officer's assigned theatre
        bookingController.startBookingForTheatre(theatre, officerUser.getUserId());
    }

    /*
     * cancelTicket - Cancels a booking
     * 
     * Officers can cancel any booking.
     */
    public void cancelTicket() {
        bookingController.cancelBooking(0);
    }
}
