/*
 * FILE: BookingDAO.java
 * PURPOSE: Interface for booking database operations.
 * 
 * OOPS CONCEPTS USED:
 * - Interface: Defines contract for booking operations
 */
package org.expleo.TicketBookingJavaProject.dao;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Booking;

/*
 * Data Access Object interface for Booking operations.
 */
public interface BookingDAO {

    // Get all bookings
    List<Booking> getAllBookings();

    // Get booking by ID
    Booking getBookingById(String bookingId);

    // Add new booking
    void addBooking(Booking booking);

    // Update booking
    void updateBooking(Booking booking);

    // Delete booking
    void deleteBooking(String bookingId);

    // Cancel booking
    void cancelBooking(String bookingId);
}
