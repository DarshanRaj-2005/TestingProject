/*
 * FILE: AuthController.java
 * PURPOSE: Handles user registration and login.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: All fields are private
 * - Abstraction: Hides validation complexity
 * 
 * WHAT THIS FILE DOES:
 * - Registers new customers
 * - Validates email and phone formats
 * - Authenticates users during login
 * 
 * Only customers can self-register. Admins are created by Super Admin.
 */
package org.expleo.TicketBookingJavaProject.controller;

import java.util.Scanner;

import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;

/*
 * Controller for user registration and login operations.
 * Handles customer self-registration with validation.
 */
public class AuthController {

    // Scanner for reading user input
    private Scanner sc = new Scanner(System.in);

    /* 
     * Steps:
     * 1. Ask for user details
     * 2. Validate email (must contain @)
     * 3. Validate phone (must be 10 digits)
     * 4. Check if email already exists
     * 5. Save to database
     * 
     * Only customers can use this. Admins are created by Super Admin.
     */
    
    public void register() {
        System.out.println("\n--- CUSTOMER REGISTRATION ---");

        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        
        // Check if name is empty
        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty!");
            return;
        }

        // Get and validate email
        String email = getValidEmail();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine().trim();
        
        // Check phone is 10 digits
        if (!isValidPhone(phone)) {
            System.out.println("Error: Phone number must be exactly 10 digits!");
            return;
        }

        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        // Check password is not empty
        if (password.isEmpty()) {
            System.out.println("Error: Password cannot be empty!");
            return;
        }

        // Create new customer with role "Customer"
        User newUser = new User(0, name, email, phone, password, "Customer");
        UserRepositoryImpl.addUser(newUser);

        System.out.println("\nRegistration Successful!");
        System.out.println("You can now login with your email and password.");
    }

    /*
     * getValidEmail - Gets and validates email from user
     * 
     * Keeps asking until valid email is entered
     * Checks for:
     * - Not empty
     * - Contains @
     * - Not already registered
     */
    private String getValidEmail() {
        while (true) {
            System.out.print("Enter Email: ");
            String email = sc.nextLine().trim();
            
            // Check if email is empty
            if (email.isEmpty()) {
                System.out.println("Error: Email cannot be empty!");
                continue;
            }
            
            // Check if email contains @
            if (!email.contains("@")) {
                System.out.println("Error: Email must contain '@' symbol!");
                System.out.println("Please enter a valid email (e.g., user@example.com)");
                continue;
            }
            
            // Check if email already exists
            if (UserRepositoryImpl.getUserByEmail(email) != null) {
                System.out.println("Error: This email is already registered!");
                continue;
            }
            
            return email;
        }
    }

    /*
     * isValidPhone - Checks if phone number is valid
     * 
     * Returns true if:
     * - Phone is not null
     * - Phone has exactly 10 characters
     * - All characters are digits
     */
    private boolean isValidPhone(String phone) {
        // Check length is 10
        if (phone == null || phone.length() != 10) {
            return false;
        }
        
        // Check all characters are digits
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }

    /*
     * login - Authenticates user with email and password
     * 
     * Returns: User object if login successful, null otherwise
     * 
     * How it works:
     * 1. Ask for email and password
     * 2. Look up user by email
     * 3. Check if password matches
     * 4. Return user if both match
     */
    public User login() {
        System.out.println("\n--- LOGIN ---");

        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        
        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        // Check inputs are not empty
        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Email and Password are required!");
            return null;
        }

        // Find user by email
        User user = UserRepositoryImpl.getUserByEmail(email);

        // Check if user exists and password matches
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("\nLogin Successful! Welcome, " + user.getName() + "!");
            return user;
        }

        System.out.println("Error: Invalid email or password!");
        return null;
    }
}
