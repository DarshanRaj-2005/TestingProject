/*
 * FILE: App.java
 * PURPOSE: Main entry point of the Ticket Booking System.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Static fields and methods
 * - Abstraction: Main menu hides complex logic
 * - Composition: Uses controllers and services
 * 
 * WHAT THIS FILE DOES:
 * - Shows the main menu
 * - Routes users to appropriate menus based on their role
 * - Manages Super Admin, Theatre Admin, Officer, Customer, and Guest menus
 * 
 * HOW TO RUN:
 * Compile and run this class to start the application.
 * Default admin: admin@gmail.com / admin123
 */
package org.expleo.TicketBookingJavaProject;

import java.util.List;
import java.util.Scanner;

import org.expleo.TicketBookingJavaProject.controller.AuthController;
import org.expleo.TicketBookingJavaProject.controller.BookingController;
import org.expleo.TicketBookingJavaProject.controller.MovieController;
import org.expleo.TicketBookingJavaProject.controller.OfficerController;
import org.expleo.TicketBookingJavaProject.controller.SearchController;
import org.expleo.TicketBookingJavaProject.controller.SuperAdminController;
import org.expleo.TicketBookingJavaProject.controller.TheatreAdminController;
import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;
import org.expleo.TicketBookingJavaProject.service.UserService;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/*
 * Main Application class for the Online Ticket Booking System.
 * Entry point of the application.
 */
public class App {

    // Scanner for reading user input from keyboard
    private static Scanner sc;

    // Controllers - Handle different parts of the application
    // Each controller manages a specific feature
    private static AuthController authController;
    private static MovieController movieController;
    private static SearchController searchController;
    private static BookingController bookingController;
    private static SuperAdminController superAdminController;
    private static TheatreAdminController theatreAdminController;
    private static OfficerController officerController;

    // Service for user-related operations
    private static UserService userService;

    /*
     * Static initializer - Runs once when class is loaded
     * Sets up all controllers and database tables
     */
    static {
        sc = new Scanner(System.in);

        // Initialize all controllers
        authController = new AuthController();
        movieController = new MovieController();
        searchController = new SearchController(movieController);
        bookingController = new BookingController(searchController, movieController);
        superAdminController = new SuperAdminController();
        theatreAdminController = new TheatreAdminController(movieController);
        officerController = new OfficerController(movieController, bookingController);
        userService = new UserService();

        // Create database tables if they don't exist
        org.expleo.TicketBookingJavaProject.config.DatabaseSetup.initialize();
    }

    /*
     * Main method - Program starts here
     * 
     * Shows main menu and handles user choices
     */
    public static void main(String[] args) {
        System.out.println("\n========================================");
        System.out.println("   WELCOME TO ONLINE TICKET BOOKING");
        System.out.println("========================================");

        // Keep showing main menu until user exits
        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Register (Customer only)");
            System.out.println("2. Login");
            System.out.println("3. Continue as Guest");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
            case 1:
                // Register new customer
                authController.register();
                break;

            case 2:
                // Login and go to appropriate menu
                User user = authController.login();
                if (user != null) {
                    routeToUserMenu(user);
                }
                break;

            case 3:
                // Guest menu - limited features
                guestMenu();
                break;

            case 4:
                // Exit the application
                System.out.println("\nThank you for using the system. Goodbye!");
                System.exit(0);

            default:
                System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /*
     * Routes user to their role-specific menu
     * 
     * Based on user's role, calls the appropriate menu method
     */
    private static void routeToUserMenu(User user) {
        String role = user.getRole();

        switch (role.toLowerCase()) {
        case "super admin":
            superAdminMenu(user);
            break;
        case "theatre admin":
            theatreAdminMenu(user);
            break;
        case "officer":
            officerMenu(user);
            break;
        case "customer":
            customerMenu(user);
            break;
        default:
            System.out.println("Unknown role: " + role);
        }
    }

    // ==================== SUPER ADMIN MENU ====================
    /*
     * Menu for Super Admin
     * 
     * Super Admin can:
     * - Create and manage theatres
     * - Create and manage theatre admins
     * - View all theatres
     */
    private static void superAdminMenu(User user) {
        while (true) {
            System.out.println("\n--- SUPER ADMIN MENU ---");
            System.out.println("1. Create Theatre");
            System.out.println("2. Create Theatre Admin");
            System.out.println("3. View Theatres");
            System.out.println("4. Remove Theatre");
            System.out.println("5. Remove Theatre Admin");
            System.out.println("6. View Profile");
            System.out.println("7. Update Profile");
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
            case 1:
                superAdminController.createTheatre();
                break;
            case 2:
                superAdminController.createTheatreAdmin();
                break;
            case 3:
                superAdminController.viewTheatres();
                break;
            case 4:
                superAdminController.removeTheatre();
                break;
            case 5:
                superAdminController.removeTheatreAdmin();
                break;
            case 6:
                viewProfile(user);
                break;
            case 7:
                updateProfile(user);
                break;
            case 8:
                System.out.println("Logged out successfully!");
                return;
            default:
                System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== THEATRE ADMIN MENU ====================
    /*
     * Menu for Theatre Admin
     * 
     * Theatre Admin can:
     * - Manage movies in their theatre
     * - Create officers
     * - View reports
     */
    private static void theatreAdminMenu(User user) {
        while (true) {
            System.out.println("\n--- THEATRE ADMIN MENU ---");
            System.out.println("1. Add Movie");
            System.out.println("2. Update Movie");
            System.out.println("3. Delete Movie");
            System.out.println("4. Create Officer");
            System.out.println("5. View Movie List");
            System.out.println("6. View Reports");
            System.out.println("7. View Profile");
            System.out.println("8. Update Profile");
            System.out.println("9. Logout");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
            case 1:
                theatreAdminController.addMovie(user);
                break;
            case 2:
                theatreAdminController.updateMovie(user);
                break;
            case 3:
                theatreAdminController.deleteMovie(user);
                break;
            case 4:
                theatreAdminController.createOfficer(user);
                break;
            case 5:
                theatreAdminController.viewMovies(user);
                break;
            case 6:
                viewTheatreAdminReports(user);
                break;
            case 7:
                viewProfile(user);
                break;
            case 8:
                updateProfile(user);
                break;
            case 9:
                System.out.println("Logged out successfully!");
                return;
            default:
                System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== OFFICER MENU ====================
    /*
     * Menu for Officer
     * 
     * Officer can:
     * - View movies in their assigned theatre
     * - Book tickets
     * - Cancel tickets
     */
    private static void officerMenu(User user) {
        while (true) {
            System.out.println("\n--- OFFICER MENU ---");
            System.out.println("1. View Movie List");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Logout");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
            case 1:
                officerController.viewMovies(user);
                break;
            case 2:
                officerController.bookTicket(user);
                break;
            case 3:
                officerController.cancelTicket();
                break;
            case 4:
                System.out.println("Logged out successfully!");
                return;
            default:
                System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== CUSTOMER MENU ====================
    /*
     * Menu for Customer
     * 
     * Customer can:
     * - Search and view movies
     * - Book and cancel tickets
     * - View their bookings
     */
    private static void customerMenu(User user) {
        while (true) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. Search Movie");
            System.out.println("2. Book Ticket");
            System.out.println("3. View Movie List");
            System.out.println("4. View My Bookings");
            System.out.println("5. Cancel Ticket");
            System.out.println("6. View Profile");
            System.out.println("7. Update Profile");
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
            case 1:
                searchController.searchMovie(true);
                break;
            case 2:
                bookingController.startBooking(-1, user.getUserId());
                break;
            case 3:
                movieController.viewMovies();
                break;
            case 4:
                bookingController.viewMyBookings(user.getUserId());
                break;
            case 5:
                bookingController.cancelBooking(user.getUserId());
                break;
            case 6:
                viewProfile(user);
                break;
            case 7:
                updateProfile(user);
                break;
            case 8:
                System.out.println("Logged out successfully!");
                return;
            default:
                System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== GUEST MENU ====================
    /*
     * Menu for Guest (unregistered users)
     * 
     * Guest can:
     * - Search movies
     * - View movie list
     * Cannot book tickets without registering
     */
    private static void guestMenu() {
        while (true) {
            System.out.println("\n--- GUEST MENU ---");
            System.out.println("1. Search Movie");
            System.out.println("2. View Movie List");
            System.out.println("3. Back to Main Menu");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
            case 1:
                searchController.searchMovie(false);
                break;
            case 2:
                movieController.viewMovies();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== REPORTS ====================
    /*
     * Shows reports menu for Theatre Admin
     * 
     * Reports available:
     * - Movie Report: Shows all movies with booking counts
     * - Booking Report: Shows all bookings with revenue
     */
    private static void viewTheatreAdminReports(User adminUser) {
        // Find the theatre this admin manages
        Theatre theatre = TheatreRepositoryImpl.getAllTheatres().stream()
                .filter(t -> t.getAdminId() == adminUser.getUserId()).findFirst().orElse(null);

        if (theatre == null) {
            System.out.println("Error: No theatre assigned to you!");
            return;
        }

        while (true) {
            System.out.println("\n--- THEATRE ADMIN REPORTS ---");
            System.out.println("Theatre: " + theatre.getName() + " (" + theatre.getCity() + ")");
            System.out.println("1. Movie Report");
            System.out.println("2. Booking Report");
            System.out.println("3. Back");

            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);

            switch (choice) {
            case 1:
                viewMovieReport(theatre);
                break;
            case 2:
                viewBookingReport(theatre);
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice!");
            }
        }
    }

    /*
     * Shows Movie Report
     * 
     * Displays:
     * - All movies in the theatre
     * - Number of bookings and seats sold for each movie
     */
    private static void viewMovieReport(Theatre theatre) {
        System.out.println("\n========================================");
        System.out.println("         MOVIE REPORT                   ");
        System.out.println("========================================");
        System.out.println("Theatre: " + theatre.getName());
        System.out.println("City: " + theatre.getCity());
        System.out.println("-------------------------------------------");

        List<Movie> movies = MovieRepositoryImpl.getMoviesByTheatre(theatre.getId());

        if (movies.isEmpty()) {
            System.out.println("No movies found in this theatre.");
            return;
        }

        System.out.println("Total Movies: " + movies.size());
        System.out.println("-------------------------------------------");

        for (Movie m : movies) {
            // Count bookings for this movie
            List<Booking> bookings = BookingRepositoryImpl.getBookingsByTheatreId(theatre.getId());
            int movieBookings = 0;
            int totalSeatsBooked = 0;

            for (Booking b : bookings) {
                if (b.getMovieId().equals(m.getId()) && b.getStatus().equalsIgnoreCase("CONFIRMED")) {
                    movieBookings++;
                    totalSeatsBooked += b.getSeatLabels().size();
                }
            }

            System.out.println("Movie Title : " + m.getTitle());
            System.out.println("Language    : " + m.getLanguage());
            System.out.println("Genre       : " + m.getGenre());
            System.out.println("Duration    : " + m.getDuration() + " mins");
            System.out.println("Bookings    : " + movieBookings);
            System.out.println("Seats Sold  : " + totalSeatsBooked);
            System.out.println("-------------------------------------------");
        }
    }

    /*
     * Shows Booking Report
     * 
     * Displays:
     * - All bookings in the theatre
     * - Booking details and status
     * - Summary with total revenue
     */
    private static void viewBookingReport(Theatre theatre) {
        System.out.println("\n========================================");
        System.out.println("         BOOKING REPORT                  ");
        System.out.println("========================================");
        System.out.println("Theatre: " + theatre.getName());
        System.out.println("City: " + theatre.getCity());
        System.out.println("-------------------------------------------");

        List<Booking> bookings = BookingRepositoryImpl.getBookingsByTheatreId(theatre.getId());

        if (bookings.isEmpty()) {
            System.out.println("No bookings found for this theatre.");
            return;
        }

        int confirmedCount = 0;
        int cancelledCount = 0;
        double totalRevenue = 0;

        System.out.println("+------------------------------------------+");

        for (Booking b : bookings) {
            Movie movie = MovieRepositoryImpl.getMovieById(b.getMovieId());
            String movieName = (movie != null) ? movie.getTitle() : "Unknown";

            System.out.println("| Booking ID: " + b.getBookingId());
            System.out.println("| Movie: " + movieName);
            System.out.println("| Showtime: " + b.getShowtime());
            System.out.println("| Seats: " + String.join(", ", b.getSeatLabels()));
            System.out.println("| Amount: Rs." + b.getTotalAmount());
            System.out.println("| Status: " + b.getStatus());
            System.out.println("+------------------------------------------+");

            if (b.getStatus().equalsIgnoreCase("CONFIRMED")) {
                confirmedCount++;
                totalRevenue += b.getTotalAmount();
            } else if (b.getStatus().equalsIgnoreCase("CANCELLED")) {
                cancelledCount++;
            }
        }

        System.out.println("\n--- SUMMARY ---");
        System.out.println("Total Bookings  : " + bookings.size());
        System.out.println("Confirmed       : " + confirmedCount);
        System.out.println("Cancelled       : " + cancelledCount);
        System.out.println("Total Revenue   : Rs." + totalRevenue);
    }

    // ==================== PROFILE MANAGEMENT ====================
    /*
     * Shows user profile information
     * 
     * Displays basic info and role-specific details
     */
    private static void viewProfile(User user) {
        System.out.println("\n--- YOUR PROFILE ---");
        System.out.println("User ID : " + user.getUserId());
        System.out.println("Name    : " + user.getName());
        System.out.println("Email   : " + user.getEmail());
        System.out.println("Phone   : " + user.getPhone());
        System.out.println("Role    : " + user.getRole());

        // Show theatre info for Theatre Admins
        if (user.getRole().equalsIgnoreCase("Theatre Admin")) {
            Theatre theatre = TheatreRepositoryImpl.getAllTheatres().stream()
                    .filter(t -> t.getAdminId() == user.getUserId()).findFirst().orElse(null);

            if (theatre != null) {
                System.out.println("Theatre : " + theatre.getName() + " (" + theatre.getCity() + ")");
            } else {
                System.out.println("Theatre : Not Assigned");
            }
        }

        // Show theatre info for Officers
        if (user.getRole().equalsIgnoreCase("Officer")) {
            Theatre theatre = TheatreRepositoryImpl.getTheatreById(user.getTheatreId());
            if (theatre != null) {
                System.out.println("Theatre : " + theatre.getName() + " (" + theatre.getCity() + ")");
                System.out.println("Note   : You can only book tickets for this theatre.");
            } else {
                System.out.println("Theatre : Not Assigned");
            }
        }
    }

    /*
     * Updates user profile
     * 
     * Allows changing name, phone, and password
     * Press Enter to keep current value
     */
    private static void updateProfile(User user) {
        System.out.println("\n--- UPDATE PROFILE ---");

        System.out.print("Enter New Name (press Enter to keep '" + user.getName() + "'): ");
        String name = sc.nextLine().trim();

        System.out.print("Enter New Phone (press Enter to keep '" + user.getPhone() + "'): ");
        String phone = sc.nextLine().trim();

        System.out.print("Enter New Password (press Enter to keep current): ");
        String password = sc.nextLine().trim();

        userService.updateProfile(user, name, phone, password);
    }
}
