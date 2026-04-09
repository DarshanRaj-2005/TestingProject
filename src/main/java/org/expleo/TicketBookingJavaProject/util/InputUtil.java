/*
 * FILE: InputUtil.java
 * PURPOSE: Utility class for reading user input safely.
 * 
 * OOPS CONCEPTS USED:
 * - Static methods: No need to create object
 * - Encapsulation: Hides input handling complexity
 * 
 * WHAT THIS FILE DOES:
 * - Reads integers, strings, doubles from keyboard
 * - Handles errors gracefully
 * - Gets yes/no confirmations
 */
package org.expleo.TicketBookingJavaProject.util;

import java.util.Scanner;
import java.util.NoSuchElementException;

/*
 * Utility class for handling user input.
 * Provides safe methods to read different types of input from console.
 */
public class InputUtil {
    
    /*
     * getIntInput - Reads an integer from user
     * 
     * Returns: The number entered, or -1 if invalid/empty
     */
    public static int getIntInput(Scanner sc) {
        try {
            String input = sc.nextLine().trim();
            
            // Empty input returns -1
            if (input.isEmpty()) {
                return -1;
            }
            
            // Parse and return integer
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        } catch (NoSuchElementException e) {
            return -1;
        }
    }
    
    /*
     * getStringInput - Reads a string from user
     * 
     * Returns: The text entered (trimmed)
     */
    public static String getStringInput(Scanner sc) {
        try {
            return sc.nextLine().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
    
    /*
     * getConfirmation - Reads yes/no from user
     * 
     * Parameters:
     * - sc: Scanner
     * - defaultValue: What to return if no input
     * 
     * Returns: true for "yes"/"y", false otherwise
     */
    public static boolean getConfirmation(Scanner sc, boolean defaultValue) {
        try {
            String input = sc.nextLine().trim().toLowerCase();
            
            if (input.isEmpty()) {
                return defaultValue;
            }
            
            return input.equals("yes") || input.equals("y");
        } catch (NoSuchElementException e) {
            return defaultValue;
        }
    }
    
    /*
     * getDoubleInput - Reads a decimal number from user
     * 
     * Returns: The number entered, or -1 if invalid
     */
    public static double getDoubleInput(Scanner sc) {
        try {
            String input = sc.nextLine().trim();
            
            if (input.isEmpty()) {
                return -1;
            }
            
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return -1;
        } catch (NoSuchElementException e) {
            return -1;
        }
    }
}
