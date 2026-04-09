/*
 * FILE: SeatController.java
 * PURPOSE: Handles seat display and selection.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields
 * - Abstraction: Simple seat selection interface
 * 
 * This class is used for UI display. Main seat logic is in SeatService.
 */
package org.expleo.TicketBookingJavaProject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.service.SeatService;

/*
 * Controller for seat display and selection.
 * Acts as UI layer for seat-related operations.
 */
public class SeatController {

    // Service layer for seat logic
    private SeatService seatService = new SeatService();

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);

    /*
     * showSeatSelectionPage - Shows complete seat selection page
     */
    public void showSeatSelectionPage(String sessionKey) {
        displaySeatLayout(sessionKey);
        displayAvailableSeats(sessionKey);
        displayBookedSeats(sessionKey);
    }

    /*
     * displaySeatLayout - Shows theatre seat map
     * 
     * Format: A1 A2 A3 ... A10
     *         B1 B2 B3 ... B10
     *         ...
     *         [O] = Available, [X] = Booked
     */
    public void displaySeatLayout(String sessionKey) {
        List<Seat> seats = seatService.getSeatLayout(sessionKey);

        System.out.println("\n========== SEAT SELECTION PAGE ==========");
        System.out.println("SCREEN\n");

        String currentRow = "";

        // Loop through seats and print row by row
        for (Seat seat : seats) {

            // Print new row label
            if (!seat.getRow().equals(currentRow)) {
                currentRow = seat.getRow();
                System.out.print(currentRow + "  ");
            }

            // Show status symbol
            String symbol = seat.getStatus().equalsIgnoreCase("BOOKED") ? "[X]" : "[O]";

            // Print seat with status
            System.out.print(seat.getSeatLabel() + symbol + "  ");

            // New line after 10 seats
            if (seat.getNumber() == 10)
                System.out.println();
        }
    }

    /*
     * displayAvailableSeats - Shows all available seats
     */
    public void displayAvailableSeats(String sessionKey) {
        System.out.println("\nAvailable Seats:");

        for (Seat s : seatService.getAvailableSeats(sessionKey)) {
            System.out.print(s.getSeatLabel() + " ");
        }

        System.out.println();
    }

    /*
     * displayBookedSeats - Shows all booked seats
     */
    public void displayBookedSeats(String sessionKey) {
        System.out.println("\nBooked Seats:");

        for (Seat s : seatService.getBookedSeats(sessionKey)) {
            System.out.print(s.getSeatLabel() + " ");
        }

        System.out.println();
    }

    /*
     * selectSingleSeat - Selects one seat
     * 
     * Returns: Seat object if valid, null if invalid
     */
    public Seat selectSingleSeat(String sessionKey) {
        System.out.print("\nEnter seat number: ");
        String label = sc.nextLine().toUpperCase();

        // Validate seat
        String validation = seatService.validateSingleSeatSelection(sessionKey, label);

        if (!validation.equals("VALID")) {
            System.out.println(validation);
            return null;
        }

        // Get seat object
        Seat seat = seatService.getSeatByLabel(sessionKey, label);

        System.out.println("Seat selected: " + seat.getSeatLabel());

        return seat;
    }

    /*
     * selectMultipleSeats - Selects multiple seats
     * 
     * Returns: List of seats if valid, null if invalid
     */
    public List<Seat> selectMultipleSeats(String sessionKey, int count) {
        System.out.print("\nEnter seats (comma separated): ");
        String input = sc.nextLine();

        // Parse input
        List<String> labels = new ArrayList<>();
        for (String s : input.split(",")) {
            labels.add(s.trim().toUpperCase());
        }

        // Validate selection
        String validation = seatService.validateMultipleSeatSelection(sessionKey, labels, count);

        if (!validation.equals("VALID")) {
            System.out.println(validation);
            return null;
        }

        // Get seat objects
        List<Seat> result = new ArrayList<>();
        for (String label : labels) {
            result.add(seatService.getSeatByLabel(sessionKey, label));
        }

        return result;
    }

    /*
     * confirmSeats - Marks seats as booked
     */
    public void confirmSeats(List<Seat> seats) {
        for (Seat s : seats) {
            s.setStatus("BOOKED");
        }
    }
}
