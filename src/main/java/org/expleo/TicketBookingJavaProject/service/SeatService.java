/*
 * FILE: SeatService.java
 * PURPOSE: Handles seat-related business logic.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields
 * - Abstraction: Simple seat interface
 * - Composition: Uses SeatRepositoryImpl
 * 
 * WHAT THIS FILE DOES:
 * - Gets seat layout
 * - Gets available/booked seats
 * - Validates seat selection
 * - Updates seat status
 */
package org.expleo.TicketBookingJavaProject.service;

import java.util.*;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;

/*
 * Service class for seat-related operations.
 * Handles business logic for seat management.
 */
public class SeatService {

    // Repository for seat database operations
    private static SeatRepositoryImpl repo = new SeatRepositoryImpl();

    /*
     * getSeatLayout - Gets all seats for a session
     * 
     * Parameter: sessionKey (format: theatre_movie_showtime)
     * Returns: List of all seats
     */
    public List<Seat> getSeatLayout(String sessionKey) {
        return repo.getSeatsForSession(sessionKey);
    }

    /*
     * getAvailableSeats - Gets only available seats
     * 
     * Returns: List of seats with status "AVAILABLE"
     */
    public List<Seat> getAvailableSeats(String sessionKey) {
        List<Seat> available = new ArrayList<>();
        for (Seat s : repo.getSeatsForSession(sessionKey)) {
            if (s.getStatus().equalsIgnoreCase("AVAILABLE")) {
                available.add(s);
            }
        }
        return available;
    }

    /*
     * getBookedSeats - Gets only booked seats
     * 
     * Returns: List of seats with status "BOOKED"
     */
    public List<Seat> getBookedSeats(String sessionKey) {
        List<Seat> booked = new ArrayList<>();
        for (Seat s : repo.getSeatsForSession(sessionKey)) {
            if (s.getStatus().equalsIgnoreCase("BOOKED")) {
                booked.add(s);
            }
        }
        return booked;
    }

    /*
     * getSeatByLabel - Finds a seat by label
     * 
     * Parameter: label like "A1"
     * Returns: Seat object or null
     */
    public Seat getSeatByLabel(String sessionKey, String label) {
        for (Seat s : repo.getSeatsForSession(sessionKey)) {
            if (s.getSeatLabel().equalsIgnoreCase(label)) {
                return s;
            }
        }
        return null;
    }

    /*
     * validateSingleSeatSelection - Validates one seat
     * 
     * Checks:
     * - Seat exists
     * - Seat is available
     * 
     * Returns: "VALID" or error message
     */
    public String validateSingleSeatSelection(String sessionKey, String label) {
        Seat seat = getSeatByLabel(sessionKey, label);

        if (seat == null) {
            return "Error: Seat '" + label + "' does not exist.";
        }

        if (seat.getStatus().equalsIgnoreCase("BOOKED")) {
            return "Error: Seat '" + label + "' is already booked.";
        }

        return "VALID";
    }

    /*
     * validateMultipleSeatSelection - Validates multiple seats
     * 
     * Checks:
     * - Correct number of seats selected
     * - No duplicate seats
     * - All seats exist and are available
     * 
     * Returns: "VALID" or error message
     */
    public String validateMultipleSeatSelection(String sessionKey, List<String> labels, int count) {
        // Check count matches
        if (labels.size() != count) {
            return "Error: Please select exactly " + count + " seats.";
        }

        // Check for duplicates
        Set<String> uniqueSeats = new HashSet<>(labels);
        if (uniqueSeats.size() != labels.size()) {
            return "Error: You have selected duplicate seats.";
        }

        // Validate each seat
        for (String label : labels) {
            String result = validateSingleSeatSelection(sessionKey, label);
            if (!result.equals("VALID")) {
                return result;
            }
        }

        return "VALID";
    }

    /*
     * updateSeat - Updates seat in database
     */
    public void updateSeat(Seat seat) {
        repo.updateSeat(seat);
    }
}
