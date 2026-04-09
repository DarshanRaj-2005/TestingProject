/*
 * FILE: TheatreAdminController.java
 * PURPOSE: Handles Theatre Admin operations.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields
 * - Composition: Uses MovieController
 * 
 * WHAT THIS FILE DOES:
 * - Add, update, delete movies in their theatre
 * - Create officers for their theatre
 * - View movies in their theatre
 * 
 * WHO USES THIS:
 * - Theatre Admin only (role: "Theatre Admin")
 * 
 * NOTE: Theatre Admin can only manage movies in their assigned theatre.
 */
package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/*
 * Controller for Theatre Admin operations.
 * Theatre admins can manage movies and create officers.
 */
public class TheatreAdminController {

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);
    
    // Reference to MovieController (for movie operations)
    private MovieController movieController;

    /*
     * Constructor - Sets up the controller
     */
    public TheatreAdminController(MovieController movieController) {
        this.movieController = movieController;
    }

    /*
     * isValidEmail - Checks if email contains @
     */
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.contains("@");
    }

    /*
     * isValidPhone - Checks if phone is 10 digits
     */
    private boolean isValidPhone(String phone) {
        if (phone == null || phone.length() != 10) {
            return false;
        }
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /*
     * createOfficer - Creates an officer for the theatre
     * 
     * Officer can only book tickets for this theatre.
     */
    public void createOfficer(User adminUser) {
        // Find theatre this admin manages
        Theatre theatre = TheatreRepositoryImpl.getAllTheatres().stream()
            .filter(t -> t.getAdminId() == adminUser.getUserId())
            .findFirst().orElse(null);
        
        if (theatre == null) {
            System.out.println("Error: You are not assigned to any theatre!");
            return;
        }
        
        System.out.println("\n--- CREATE NEW OFFICER for " + theatre.getName() + " ---");
        System.out.println("This officer will only be able to book tickets for " + theatre.getName() + " (" + theatre.getCity() + ")");
        
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty!");
            return;
        }
        
        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        
        if (!isValidEmail(email)) {
            System.out.println("Error: Email must contain '@' symbol!");
            return;
        }
        
        // Check if email exists
        if (UserRepositoryImpl.getUserByEmail(email) != null) {
            System.out.println("Error: Email already exists!");
            return;
        }

        System.out.print("Enter Phone (10 digits): ");
        String phone = sc.nextLine().trim();
        
        if (!isValidPhone(phone)) {
            System.out.println("Error: Phone number must be exactly 10 digits!");
            return;
        }
        
        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        if (password.isEmpty()) {
            System.out.println("Error: Password cannot be empty!");
            return;
        }

        // Create officer with theatre assignment
        User officer = new User(0, name, email, phone, password, "Officer");
        officer.setTheatreId(theatre.getId());
        UserRepositoryImpl.addUser(officer);
        
        System.out.println("\nOfficer '" + name + "' created successfully!");
        System.out.println("Assigned to: " + theatre.getName() + " (" + theatre.getCity() + ")");
        System.out.println("This officer can only book tickets for this theatre.");
    }

    // Movie management methods - delegate to MovieController
    public void addMovie(User adminUser) {
        movieController.addMovie(adminUser);
    }

    public void updateMovie(User adminUser) {
        movieController.updateMovie(adminUser);
    }

    public void deleteMovie(User adminUser) {
        movieController.deleteMovie(adminUser);
    }

    public void viewMovies(User adminUser) {
        movieController.viewMovies(adminUser);
    }
}
