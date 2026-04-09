/*
 * FILE: BookingIdGenerator.java
 * PURPOSE: Generates unique booking IDs.
 */
package org.expleo.TicketBookingJavaProject.util;

import java.util.UUID;

/*
 * Generates unique IDs for bookings.
 */
public class BookingIdGenerator {

    /*
     * generateBookingId - Creates a unique booking ID
     * 
     * Format: "BOOK-" + 6 random characters
     * Example: "BOOK-A1B2C3"
     */
    public static String generateBookingId() {
        return "BOOK-" + UUID.randomUUID().toString().substring(0, 6);
    }
}
