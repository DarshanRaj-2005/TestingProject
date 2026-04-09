/*
 * FILE: Booking.java
 * PURPOSE: Stores information about a ticket booking.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: All fields are private, accessed through getters and setters
 * - Abstraction: Shows booking details without exposing complexity
 * 
 * HOW IT'S USED:
 * - Created when a user books tickets
 * - Stored in database for future reference
 * - Used to show booking history
 */
package org.expleo.TicketBookingJavaProject.model;

import java.util.List;

/*
 * WHAT THIS CLASS DOES:
 * Represents a single booking made by a user.
 * Contains details like which movie, which seats, how much paid, etc.
 * 
 * EXAMPLE:
 * Booking booking = new Booking("BK12345", 1, "M001", 1, "10:00 AM", ["A1", "A2"], 500.0, "CONFIRMED");
 */
public class Booking {

    // Unique booking ID (like "BK12345ABC")
    private String bookingId;

    // ID of user who made booking (0 means guest/unregistered user)
    private int userId;

    // ID of the movie being watched
    private String movieId;

    // ID of the theatre
    private int theatreId;

    // Showtime (like "10:00 AM", "06:00 PM")
    private String showtime;

    // List of seat labels booked (like ["A1", "A2", "A3"])
    private List<String> seatLabels;

    // Total amount paid for this booking
    private double totalAmount;

    // Status: "CONFIRMED" or "CANCELLED"
    private String status;

    /*
     * Constructor - Creates a new Booking object
     * 
     * Parameters:
     * - bookingId: Unique ID for this booking
     * - userId: Who booked (0 for guests)
     * - movieId: Which movie
     * - theatreId: Which theatre
     * - showtime: When the show is
     * - seatLabels: Which seats
     * - totalAmount: How much paid
     * - status: CONFIRMED or CANCELLED
     */
    public Booking(String bookingId, int userId, String movieId, int theatreId, String showtime,
                   List<String> seatLabels, double totalAmount, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.showtime = showtime;
        this.seatLabels = seatLabels;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getter methods - Read booking details
    public String getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public String getShowtime() {
        return showtime;
    }

    public List<String> getSeatLabels() {
        return seatLabels;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    // Setter methods - Update booking details
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public void setSeatLabels(List<String> seatLabels) {
        this.seatLabels = seatLabels;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
