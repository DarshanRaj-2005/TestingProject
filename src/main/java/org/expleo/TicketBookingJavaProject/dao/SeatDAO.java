/*
 * FILE: SeatDAO.java
 * PURPOSE: Interface for seat database operations.
 * 
 * OOPS CONCEPTS USED:
 * - Interface: Defines contract for seat operations
 */
package org.expleo.TicketBookingJavaProject.dao;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;

/*
 * Data Access Object interface for Seat operations.
 */
public interface SeatDAO {

    // Get seats for a session
    List<Seat> getSeatsForSession(String sessionKey);

    // Get seat by label
    Seat getSeatByLabel(String sessionKey, String seatLabel);

    // Update seat
    void updateSeat(Seat seat);

    // Create seats for new session
    void initializeSeats(String sessionKey);
}
