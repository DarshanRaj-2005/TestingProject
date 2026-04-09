/*
 * FILE: SuperAdminController.java
 * PURPOSE: Handles Super Admin operations.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields and methods
 * - Abstraction: Simple interface for admin operations
 * 
 * WHAT THIS FILE DOES:
 * - Create and remove theatres
 * - Create and remove theatre admins
 * - Assign admins to theatres
 * - View all theatres
 * 
 * WHO USES THIS:
 * - Super Admin only (role: "Super Admin")
 * 
 * DEFAULT LOGIN:
 * - Email: admin@gmail.com
 * - Password: admin123
 */
package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/*
 * Controller for Super Admin operations.
 * Handles theatre and theatre admin management.
 */
public class SuperAdminController {

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);

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
     * createTheatre - Adds a new theatre
     */
    public void createTheatre() {
        System.out.println("\n--- CREATE NEW THEATRE ---");
        
        System.out.print("Enter Theatre Name: ");
        String name = sc.nextLine().trim();
        
        System.out.print("Enter City: ");
        String city = sc.nextLine().trim();

        if (name.isEmpty() || city.isEmpty()) {
            System.out.println("Error: Name and City cannot be empty!");
            return;
        }

        Theatre theatre = new Theatre(0, name, city);
        TheatreRepositoryImpl.addTheatre(theatre);
        
        System.out.println("\nTheatre '" + name + "' created successfully in " + city + "!");
        System.out.println("Note: Please create a Theatre Admin and assign them to this Theatre.");
    }

    /*
     * createTheatreAdmin - Creates a theatre admin
     * 
     * Steps:
     * 1. Get admin details
     * 2. Create user with role "Theatre Admin"
     * 3. Ask to assign to a theatre
     */
    public void createTheatreAdmin() {
        System.out.println("\n--- CREATE NEW THEATRE ADMIN ---");
        
        // Get admin details
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

        // Check if email exists
        if (UserRepositoryImpl.getUserByEmail(email) != null) {
            System.out.println("Error: Email already exists!");
            return;
        }

        // Create user
        User admin = new User(0, name, email, phone, password, "Theatre Admin");
        UserRepositoryImpl.addUser(admin);

        System.out.println("\nTheatre Admin '" + name + "' created successfully!");
        
        // Offer to assign theatre
        assignTheatreToAdmin(admin);
    }

    /*
     * assignTheatreToAdmin - Assigns a theatre to an admin
     */
    private void assignTheatreToAdmin(User admin) {
        List<String> cities = TheatreRepositoryImpl.getAllCities();
        
        if (cities.isEmpty()) {
            System.out.println("\nNo theatres in system yet. Admin created but unassigned.");
            System.out.println("Please create a theatre first.");
            return;
        }

        System.out.println("\n--- ASSIGN THEATRE ---");
        System.out.println("Available cities:");
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }
        
        System.out.print("Select City (Number): ");
        int cityChoice = InputUtil.getIntInput(sc);
        
        if (cityChoice < 1 || cityChoice > cities.size()) {
            System.out.println("Invalid selection. Admin created but unassigned.");
            return;
        }
        
        String selectedCity = cities.get(cityChoice - 1);
        
        // Get theatres in selected city
        List<Theatre> cityTheatres = TheatreRepositoryImpl.getTheatresByCity(selectedCity);
        
        System.out.println("\nTheatres in " + selectedCity + ":");
        for (int i = 0; i < cityTheatres.size(); i++) {
            Theatre t = cityTheatres.get(i);
            String status = t.getAdminId() > 0 ? "(Already has admin)" : "(No admin)";
            System.out.println((i + 1) + ". " + t.getName() + " " + status);
        }
        
        System.out.print("Select Theatre (Number): ");
        int theatreChoice = InputUtil.getIntInput(sc);
        
        if (theatreChoice < 1 || theatreChoice > cityTheatres.size()) {
            System.out.println("Invalid selection. Admin created but unassigned.");
            return;
        }
        
        Theatre selected = cityTheatres.get(theatreChoice - 1);
        
        // Check if theatre already has admin
        if (selected.getAdminId() > 0) {
            System.out.print("This theatre already has an admin. Replace with new admin? (yes/no): ");
            String confirm = sc.nextLine().trim().toLowerCase();
            if (!confirm.equals("yes")) {
                System.out.println("Admin assignment cancelled.");
                return;
            }
        }

        // Assign admin to theatre
        selected.setAdminId(admin.getUserId());
        TheatreRepositoryImpl.updateTheatreAdmin(selected.getId(), admin.getUserId());
        System.out.println("Success! Admin '" + admin.getName() + "' assigned to '" + selected.getName() + "'.");
    }

    /*
     * removeTheatre - Deletes a theatre
     */
    public void removeTheatre() {
        System.out.println("\n--- REMOVE THEATRE ---");
        
        List<Theatre> theatres = TheatreRepositoryImpl.getAllTheatres();
        if (theatres.isEmpty()) {
            System.out.println("No theatres found.");
            return;
        }

        viewTheatres();
        
        System.out.print("Enter Theatre ID to remove: ");
        int id = InputUtil.getIntInput(sc);
        
        if (id <= 0) {
            System.out.println("Invalid theatre ID!");
            return;
        }
        
        System.out.print("Are you sure you want to remove this theatre? (yes/no): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        
        if (confirm.equals("yes")) {
            TheatreRepositoryImpl.removeTheatre(id);
        } else {
            System.out.println("Removal cancelled.");
        }
    }

    /*
     * removeTheatreAdmin - Deletes a theatre admin
     */
    public void removeTheatreAdmin() {
        System.out.println("\n--- REMOVE THEATRE ADMIN ---");
        
        // Get all theatre admins
        List<User> admins = UserRepositoryImpl.getAllUsers().stream()
            .filter(u -> u.getRole().equals("Theatre Admin"))
            .collect(Collectors.toList());
        
        if (admins.isEmpty()) {
            System.out.println("No Theatre Admins found.");
            return;
        }
        
        System.out.println("Available Theatre Admins:");
        for (User u : admins) {
            System.out.println(u.getUserId() + ". " + u.getName() + " (" + u.getEmail() + ")");
        }
        
        System.out.print("Enter Admin User ID to remove: ");
        int id = InputUtil.getIntInput(sc);
        
        if (id <= 0) {
            System.out.println("Invalid user ID!");
            return;
        }
        
        System.out.print("Are you sure you want to remove this admin? (yes/no): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        
        if (confirm.equals("yes")) {
            UserRepositoryImpl.removeUser(id);
        } else {
            System.out.println("Removal cancelled.");
        }
    }

    /*
     * viewTheatres - Shows all theatres
     */
    public void viewTheatres() {
        System.out.println("\n--- THEATRE LIST ---");
        
        List<Theatre> theatres = TheatreRepositoryImpl.getAllTheatres();
        if (theatres.isEmpty()) {
            System.out.println("No theatres found.");
            return;
        }

        for (Theatre t : theatres) {
            String adminName = "Not Assigned";
            User admin = UserRepositoryImpl.getAllUsers().stream()
                .filter(u -> u.getUserId() == t.getAdminId())
                .findFirst().orElse(null);
            if (admin != null) {
                adminName = admin.getName();
            }
            System.out.println("ID: " + t.getId() + " | Name: " + t.getName() + " | City: " + t.getCity() + " | Admin: " + adminName);
        }
    }
}
