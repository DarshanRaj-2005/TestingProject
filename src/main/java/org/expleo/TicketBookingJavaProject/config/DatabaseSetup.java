/*
 * FILE: DatabaseSetup.java
 * PURPOSE: Creates database tables.
 * 
 * TABLES CREATED:
 * - users: User accounts
 * - theatres: Theatre information
 * - movies: Movie listings
 * - seats: Seat availability
 * - bookings: Ticket bookings
 * 
 * Note: Uses "IF NOT EXISTS" so it doesn't overwrite existing data.
 */
package org.expleo.TicketBookingJavaProject.config;

import java.sql.Connection;
import java.sql.Statement;

/*
 * Creates database tables when application starts.
 */
public class DatabaseSetup {

    /*
     * initialize - Creates all tables
     * 
     * Called once at startup from App class.
     * Uses "CREATE TABLE IF NOT EXISTS" to be safe.
     */
    public static void initialize() {
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()) {

            // Create users table
            String createUsers = "CREATE TABLE IF NOT EXISTS users (" 
                    + "user_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(100) NOT NULL, "
                    + "email VARCHAR(100) UNIQUE NOT NULL, "
                    + "phone VARCHAR(15) NOT NULL, "
                    + "password VARCHAR(100) NOT NULL, "
                    + "role VARCHAR(50) NOT NULL"
                    + ")";

            // Create theatres table
            String createTheatres = "CREATE TABLE IF NOT EXISTS theatres (" 
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(100) NOT NULL, "
                    + "city VARCHAR(100) NOT NULL, "
                    + "adminId INT"
                    + ")";

            // Create movies table
            String createMovies = "CREATE TABLE IF NOT EXISTS movies (" 
                    + "id VARCHAR(50) PRIMARY KEY, "
                    + "title VARCHAR(150) NOT NULL, "
                    + "genre VARCHAR(50), "
                    + "language VARCHAR(50), "
                    + "duration INT, "
                    + "releaseDate VARCHAR(50), "
                    + "theatre_id INT"
                    + ")";

            // Create seats table
            String createSeats = "CREATE TABLE IF NOT EXISTS seats (" 
                    + "seat_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "session_key VARCHAR(100) NOT NULL, "
                    + "row_char VARCHAR(5) NOT NULL, "
                    + "seat_number INT NOT NULL, "
                    + "status VARCHAR(30) NOT NULL, "
                    + "price DOUBLE NOT NULL"
                    + ")";

            // Create bookings table
            String createBookings = "CREATE TABLE IF NOT EXISTS bookings (" 
                    + "booking_id VARCHAR(50) PRIMARY KEY, "
                    + "user_id VARCHAR(50), "
                    + "movie_id VARCHAR(50), "
                    + "theatre_id INT, "
                    + "showtime VARCHAR(50), "
                    + "seat_labels TEXT, "
                    + "total_amount DOUBLE, "
                    + "status VARCHAR(50)"
                    + ")";

            // Execute all create statements
            stmt.execute(createUsers);
            stmt.execute(createTheatres);
            stmt.execute(createMovies);
            stmt.execute(createSeats);
            stmt.execute(createBookings);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
