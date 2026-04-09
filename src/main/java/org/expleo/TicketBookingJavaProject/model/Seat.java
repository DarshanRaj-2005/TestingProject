/*
 * FILE: Seat.java
 * PURPOSE: Represents a single seat in a theatre.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: All fields are private, accessed through methods
 * - Abstraction: Hides seat management complexity
 * 
 * HOW IT'S USED:
 * - Created when a session starts (theatre + movie + showtime)
 * - Updated when seat status changes (AVAILABLE -> BOOKED)
 * - Used to show seat layout to users
 */
package org.expleo.TicketBookingJavaProject.model;

/*
 * WHAT THIS CLASS DOES:
 * Represents one seat in a theatre.
 * Seats are organized in rows (A, B, C, etc.) and numbers (1, 2, 3, etc.).
 * 
 * SEAT LABEL EXAMPLE: "A5" means Row A, Seat Number 5
 * 
 * SEAT STATUS:
 * - "AVAILABLE": Seat can be booked
 * - "BOOKED": Seat is already taken
 */
public class Seat {

    // Database ID for this seat
    private int seatId;
    
    // Row letter (A, B, C, D, E, F, G, H, I, J)
    private String row;
    
    // Seat number in the row (1 to 10)
    private int number;
    
    // Status: "AVAILABLE" or "BOOKED"
    private String status;
    
    // Price of this seat (based on row)
    private double price;

    /*
     * Constructor - Creates a new Seat object
     * 
     * Parameters:
     * - seatId: Database ID
     * - row: Row letter (A-J)
     * - number: Seat number (1-10)
     * - status: AVAILABLE or BOOKED
     * - price: Price of the seat
     */
    public Seat(int seatId, String row, int number, String status, double price) {
        this.seatId = seatId;
        this.row = row;
        this.number = number;
        this.status = status;
        this.price = price;
    }

    // Getter and Setter methods
    public int getSeatId() { 
        return seatId; 
    }
    
    public String getRow() { 
        return row; 
    }
    
    public int getNumber() { 
        return number; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public double getPrice() { 
        return price; 
    }
    
    public void setPrice(double price) { 
        this.price = price; 
    }

    /*
     * getSeatLabel - Combines row and number to form seat label
     * 
     * Returns: "A1", "B5", "J10", etc.
     * 
     * Example:
     * Seat seat = new Seat(1, "A", 1, "AVAILABLE", 190.0);
     * seat.getSeatLabel(); // Returns "A1"
     */
    public String getSeatLabel() {
        return row + number;
    }

    /*
     * toString - Converts seat to readable text
     * 
     * Example output: "A1 - AVAILABLE - Rs. 190.0"
     */
    @Override
    public String toString() {
        return getSeatLabel() + " - " + status + " - Rs. " + price;
    }
}
