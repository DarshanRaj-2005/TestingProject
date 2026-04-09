/*
 * FILE: BookingController.java
 * PURPOSE: Handles the complete ticket booking process.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields and methods
 * - Abstraction: Hides complex booking logic
 * - Composition: Uses SeatService, BookingService, PaymentService
 * 
 * WHAT THIS FILE DOES:
 * - Shows movie, theatre, and seat selection
 * - Calculates ticket prices with GST
 * - Processes payments
 * - Confirms or cancels bookings
 * 
 * BOOKING FLOW:
 * 1. Select movie
 * 2. Select city
 * 3. Select theatre
 * 4. Select showtime
 * 5. Select seats
 * 6. Review and pay
 * 7. Confirmation
 */
package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;
import org.expleo.TicketBookingJavaProject.model.*;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;
import org.expleo.TicketBookingJavaProject.service.SeatService;
import org.expleo.TicketBookingJavaProject.service.BookingService;
import org.expleo.TicketBookingJavaProject.service.PaymentService;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/*
 * Controller for booking operations.
 * Handles the complete booking flow from movie selection to payment.
 */
public class BookingController {

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);

    // Reference to SearchController (for pre-selected movie from search)
    private SearchController searchController;

    // Reference to MovieController
    private MovieController movieController;

    // Services for seat and booking operations
    private SeatService seatService = new SeatService();
    private BookingService bookingService = new BookingService();
    private PaymentService paymentService = new PaymentService();

    // Pricing constants
    private static final double GST_PERCENTAGE = 3.5;  // 3.5% tax
    private static final double APPLICATION_FEE = 10.0; // Rs.10 per ticket

    /*
     * Constructor - Sets up the controller
     */
    public BookingController(SearchController searchController, MovieController movieController) {
        this.searchController = searchController;
        this.movieController = movieController;
    }

    /*
     * startBookingForTheatre - For Officers to book tickets
     * 
     * Officers can only book for their assigned theatre
     */
    public void startBookingForTheatre(Theatre theatre, int officerUserId) {
        // Select movie from this theatre only
        Movie movie = selectMovieFromTheatre(theatre);
        if (movie == null) {
            return;
        }

        // Select showtime
        String showtime = selectShowtime();
        if (showtime == null) {
            return;
        }

        // Book seats
        bookSeats(movie, theatre, theatre.getCity(), showtime, -1, officerUserId);
    }

    /*
     * selectMovieFromTheatre - Shows movies at a specific theatre
     */
    private Movie selectMovieFromTheatre(Theatre theatre) {
        List<Movie> movies = movieController.getMoviesForTheatre(theatre.getId());

        if (movies.isEmpty()) {
            System.out.println("No movies available in this theatre.");
            return null;
        }

        System.out.println("\n--- SELECT MOVIE ---");
        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre() + " | "
                    + m.getDuration() + " mins");
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > movies.size()) {
            System.out.println("Invalid selection!");
            return null;
        }

        return movies.get(choice - 1);
    }

    /*
     * startBooking - Main booking entry point
     * 
     * Parameters:
     * - ticketCount: Number of tickets (-1 if not specified)
     * - userId: User ID (0 for guest)
     * 
     * Handles two cases:
     * 1. Movie was pre-selected through search
     * 2. Normal flow - select everything step by step
     */
    public void startBooking(int ticketCount, int userId) {
        System.out.println("\n--- BOOKING FLOW ---");

        Movie preSelected = searchController.getSelectedMovie();
        Theatre theatre = null;
        Movie movie = null;
        String city = null;

        if (preSelected != null) {
            // Case 1: Movie pre-selected from search
            System.out.println("Using previously selected movie: " + preSelected.getTitle() + " ("
                    + preSelected.getLanguage() + ")");

            // Step 1: Select city (where movie is playing)
            city = selectCityForMovie(preSelected);
            if (city == null) {
                searchController.clearSelectedMovie();
                return;
            }

            // Step 2: Select theatre in that city
            theatre = selectTheatreForMovie(preSelected, city);
            if (theatre == null) {
                searchController.clearSelectedMovie();
                return;
            }

            // Get movie object from this theatre
            movie = movieController.getMoviesForTheatre(theatre.getId()).stream()
                    .filter(m -> m.getTitle().equalsIgnoreCase(preSelected.getTitle())
                            && m.getLanguage().equalsIgnoreCase(preSelected.getLanguage()))
                    .findFirst().orElse(null);

            if (movie == null) {
                System.out.println("Error: Movie not found in selected theatre!");
                searchController.clearSelectedMovie();
                return;
            }

        } else {
            // Case 2: Normal flow - select everything
            city = selectCity();
            if (city == null) return;

            theatre = selectTheatre(city);
            if (theatre == null) return;

            movie = selectMovie(theatre.getId());
            if (movie == null) return;
        }

        // Step: Select showtime
        String showtime = selectShowtime();
        if (showtime == null) return;

        // Step: Book seats
        bookSeats(movie, theatre, city, showtime, ticketCount, userId);

        // Clear selected movie after booking
        searchController.clearSelectedMovie();
    }

    /*
     * selectCityForMovie - Select city when movie is pre-selected
     * 
     * Only shows cities where the movie is available
     */
    private String selectCityForMovie(Movie movie) {
        List<Theatre> theatresWithMovie = searchController.getTheatresForSelectedMovie(null);

        if (theatresWithMovie.isEmpty()) {
            System.out.println("Error: This movie is not available in any theatre.");
            return null;
        }

        // Get unique cities
        Set<String> citiesSet = new HashSet<>();
        for (Theatre t : theatresWithMovie) {
            citiesSet.add(t.getCity());
        }

        List<String> cities = new ArrayList<>(citiesSet);
        Collections.sort(cities);

        System.out.println("\n--- SELECT CITY ---");
        System.out.println("Available cities where '" + movie.getTitle() + "' is playing:");

        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > cities.size()) {
            System.out.println("Invalid selection!");
            return null;
        }

        return cities.get(choice - 1);
    }

    /*
     * selectTheatreForMovie - Select theatre for pre-selected movie
     */
    private Theatre selectTheatreForMovie(Movie movie, String city) {
        List<Theatre> theatres = searchController.getTheatresForSelectedMovie(city);

        if (theatres.isEmpty()) {
            System.out.println("No theatres found in " + city + " for this movie.");
            return null;
        }

        System.out.println("\n--- SELECT THEATRE in " + city + " ---");
        System.out.println("Theatres showing '" + movie.getTitle() + "':");

        for (int i = 0; i < theatres.size(); i++) {
            Theatre t = theatres.get(i);
            System.out.println((i + 1) + ". " + t.getName());
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > theatres.size()) {
            System.out.println("Invalid selection!");
            return null;
        }

        return theatres.get(choice - 1);
    }

    /*
     * selectCity - Select city from all available cities
     */
    private String selectCity() {
        List<String> cities = TheatreRepositoryImpl.getAllCities();

        if (cities.isEmpty()) {
            System.out.println("No cities available. Please contact Super Admin to add theatres.");
            return null;
        }

        System.out.println("\n--- SELECT CITY ---");
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > cities.size()) {
            System.out.println("Invalid selection!");
            return null;
        }

        return cities.get(choice - 1);
    }

    /*
     * selectTheatre - Select theatre in a city
     */
    private Theatre selectTheatre(String city) {
        List<Theatre> theatres = TheatreRepositoryImpl.getTheatresByCity(city);

        if (theatres.isEmpty()) {
            System.out.println("No theatres available in " + city + ".");
            return null;
        }

        System.out.println("\n--- SELECT THEATRE in " + city + " ---");
        for (int i = 0; i < theatres.size(); i++) {
            System.out.println((i + 1) + ". " + theatres.get(i).getName());
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > theatres.size()) {
            System.out.println("Invalid selection!");
            return null;
        }

        return theatres.get(choice - 1);
    }

    /*
     * selectMovie - Select movie from a theatre
     */
    private Movie selectMovie(int theatreId) {
        List<Movie> movies = movieController.getMoviesForTheatre(theatreId);

        if (movies.isEmpty()) {
            System.out.println("No movies available in this theatre.");
            return null;
        }

        System.out.println("\n--- SELECT MOVIE ---");
        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre() + " | "
                    + m.getDuration() + " mins");
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > movies.size()) {
            System.out.println("Invalid selection!");
            return null;
        }

        return movies.get(choice - 1);
    }

    /*
     * selectShowtime - Select when to watch
     */
    private String selectShowtime() {
        List<String> shows = Arrays.asList("10:00 AM", "01:30 PM", "06:00 PM", "10:00 PM");

        System.out.println("\n--- SELECT SHOWTIME ---");
        for (int i = 0; i < shows.size(); i++) {
            System.out.println((i + 1) + ". " + shows.get(i));
        }

        System.out.print("Choice: ");
        int choice = InputUtil.getIntInput(sc);

        if (choice < 1 || choice > shows.size()) {
            System.out.println("Invalid selection!");
            return null;
        }

        return shows.get(choice - 1);
    }

    /*
     * bookSeats - Handles the seat selection and booking process
     * 
     * Steps:
     * 1. Get number of tickets
     * 2. Show seat layout
     * 3. Let user select seats
     * 4. Calculate price
     * 5. Allow modification
     * 6. Process payment
     * 7. Confirm booking
     */
    private void bookSeats(Movie movie, Theatre theatre, String city, String showtime, int ticketCount, int userId) {
        // Session key: theatreID_movieID_showtime
        String sessionKey = theatre.getId() + "_" + movie.getId() + "_" + showtime.replace(" ", "_").replace(":", "");

        // Get number of tickets
        if (ticketCount <= 0) {
            System.out.print("\nEnter number of tickets to book: ");
            ticketCount = InputUtil.getIntInput(sc);

            if (ticketCount <= 0) {
                System.out.println("Error: Please enter a valid number of tickets!");
                return;
            }
        }

        // Maximum 10 seats per transaction
        if (ticketCount > 10) {
            System.out.println("Error: You can only book up to 10 seats in a single transaction!");
            return;
        }

        // Get seat availability
        List<Seat> allSeats = seatService.getSeatLayout(sessionKey);
        List<Seat> availableSeats = seatService.getAvailableSeats(sessionKey);
        int totalCapacity = allSeats.size();
        int availableCount = availableSeats.size();

        if (ticketCount > totalCapacity) {
            System.out.println("Error: Requested seats exceed theatre capacity!");
            return;
        }

        if (ticketCount > availableCount) {
            System.out.println("Error: Only " + availableCount + " seats are available.");
            return;
        }

        // Show seat layout
        displaySeatLayoutWithPrices(sessionKey);

        // Get seat selection
        List<String> selectedSeats = selectSeats(sessionKey, ticketCount);
        if (selectedSeats == null) {
            return;
        }

        // Calculate price
        double[] priceInfo = calculatePrice(selectedSeats);
        double ticketAmount = priceInfo[0];
        
        // Create bill
        BillDetails bill = new BillDetails(ticketCount, ticketAmount, GST_PERCENTAGE, APPLICATION_FEE);

        // Show summary
        showBookingSummary(movie, theatre, city, showtime, selectedSeats, bill);

        // Allow seat modification
        List<String> finalSeats = selectedSeats;
        while (true) {
            System.out.print("\nDo you want to modify seats? (yes/no): ");
            String modifyChoice = sc.nextLine().trim().toLowerCase();
            
            if (modifyChoice.equals("yes")) {
                // Release current seats
                for (String label : finalSeats) {
                    Seat s = seatService.getSeatByLabel(sessionKey, label);
                    if (s != null) {
                        s.setStatus("AVAILABLE");
                        seatService.updateSeat(s);
                    }
                }
                
                // Get new number of tickets
                System.out.print("Enter new number of tickets: ");
                ticketCount = InputUtil.getIntInput(sc);
                
                if (ticketCount <= 0) {
                    System.out.println("Error: Please enter a valid number of tickets!");
                    return;
                }

                if (ticketCount > 10) {
                    System.out.println("Error: Maximum 10 seats per transaction!");
                    return;
                }

                List<Seat> currentAvailable = seatService.getAvailableSeats(sessionKey);
                int currentTotal = seatService.getSeatLayout(sessionKey).size();
                
                if (ticketCount > currentTotal) {
                    System.out.println("Error: Requested seats exceed theatre capacity!");
                    return;
                }
                
                if (ticketCount > currentAvailable.size()) {
                    System.out.println("Error: Only " + currentAvailable.size() + " seats are available.");
                    return;
                }
                
                // Show updated layout
                displaySeatLayoutWithPrices(sessionKey);
                
                // Select new seats
                finalSeats = selectSeats(sessionKey, ticketCount);
                if (finalSeats == null) {
                    return;
                }
                
                // Recalculate price
                priceInfo = calculatePrice(finalSeats);
                ticketAmount = priceInfo[0];
                bill = new BillDetails(ticketCount, ticketAmount, GST_PERCENTAGE, APPLICATION_FEE);
                
                showBookingSummary(movie, theatre, city, showtime, finalSeats, bill);
            } else if (modifyChoice.equals("no")) {
                break;
            } else {
                System.out.println("Invalid choice! Please enter 'yes' or 'no'.");
            }
        }

        // Proceed to payment
        System.out.print("\nProceed to Payment? (yes/no): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
            System.out.println("Booking cancelled.");
            // Release seats
            for (String label : finalSeats) {
                Seat s = seatService.getSeatByLabel(sessionKey, label);
                if (s != null) {
                    s.setStatus("AVAILABLE");
                    seatService.updateSeat(s);
                }
            }
            return;
        }

        // Process payment
        processPayment(movie, theatre, city, showtime, finalSeats, bill, userId);
    }

    /*
     * selectSeats - Gets user's seat selection
     */
    private List<String> selectSeats(String sessionKey, int ticketCount) {
        List<String> selectedSeats = new ArrayList<>();

        System.out.println("Enter " + ticketCount + " seat labels to book (comma-separated, e.g., A1, A2): ");
        String input = sc.nextLine().toUpperCase();

        // Parse input
        String[] labels = input.split("[,\\s]+");
        for (String label : labels) {
            if (!label.trim().isEmpty()) {
                selectedSeats.add(label.trim());
            }
        }

        // Validate count
        if (selectedSeats.size() != ticketCount) {
            System.out.println("Error: You must select exactly " + ticketCount + " seats!");
            return null;
        }

        if (selectedSeats.isEmpty()) {
            System.out.println("Error: No seats selected!");
            return null;
        }

        // Validate selection
        String validation = seatService.validateMultipleSeatSelection(sessionKey, selectedSeats, ticketCount);
        if (!validation.equals("VALID")) {
            System.out.println("Error: " + validation);
            return null;
        }

        return selectedSeats;
    }

    /*
     * calculatePrice - Calculates ticket price based on seat rows
     * 
     * PRICING:
     * - Rows A, B, C (top): Rs. 190
     * - Rows D, E, F, G: Rs. 160
     * - Rows H, I, J (bottom): Rs. 60
     */
    private double[] calculatePrice(List<String> seats) {
        double totalPrice = 0;
        StringBuilder priceBreakdown = new StringBuilder();
        
        for (String seat : seats) {
            double price = 0;
            if (seat.length() > 0) {
                char row = seat.charAt(0);
                
                // Top rows: A, B, C
                if (row >= 'A' && row <= 'C') {
                    price = 190;
                }
                // Middle rows: D, E, F, G
                else if (row >= 'D' && row <= 'G') {
                    price = 160;
                }
                // Bottom rows: H, I, J
                else if (row >= 'H' && row <= 'J') {
                    price = 60;
                }
                else {
                    price = 160;
                }
            }
            
            totalPrice += price;
            if (!priceBreakdown.isEmpty()) {
                priceBreakdown.append(", ");
            }
            priceBreakdown.append(seat).append(": Rs.").append((int)price);
        }
        
        System.out.println("\nSeat Prices: " + priceBreakdown.toString());
        return new double[]{totalPrice, GST_PERCENTAGE, APPLICATION_FEE};
    }

    /*
     * displaySeatLayoutWithPrices - Shows seat map with prices
     */
    private void displaySeatLayoutWithPrices(String sessionKey) {
        System.out.println("\n--- SEAT LAYOUT WITH PRICES ---");
        System.out.println("Row A-C: Rs.190 | Row D-G: Rs.160 | Row H-J: Rs.60");
        System.out.println("[ ] = Available  [X] = Booked");
        System.out.println();
        
        List<Seat> seats = seatService.getSeatLayout(sessionKey);

        char currentRow = ' ';
        for (Seat s : seats) {
            if (s.getRow().charAt(0) != currentRow) {
                if (currentRow != ' ') {
                    System.out.println();
                }
                currentRow = s.getRow().charAt(0);
                
                // Show row price
                String rowPrice = "";
                if (currentRow >= 'A' && currentRow <= 'C') {
                    rowPrice = " (Rs.190)";
                } else if (currentRow >= 'D' && currentRow <= 'G') {
                    rowPrice = " (Rs.160)";
                } else {
                    rowPrice = " (Rs.60)";
                }
                System.out.print(currentRow + rowPrice + " | ");
            }
            String statusSym = s.getStatus().equalsIgnoreCase("AVAILABLE") ? "[ ]" : "[X]";
            System.out.print(s.getSeatLabel() + statusSym + " ");
        }
        System.out.println();
    }

    /*
     * showBookingSummary - Shows booking details
     */
    private void showBookingSummary(Movie movie, Theatre theatre, String city, String showtime, 
                                    List<String> selectedSeats, BillDetails bill) {
        System.out.println("\n--- BOOKING SUMMARY ---");
        System.out.println("Movie: " + movie.getTitle() + " (" + movie.getLanguage() + ")");
        System.out.println("Theatre: " + theatre.getName() + " (" + city + ")");
        System.out.println("Showtime: " + showtime);
        System.out.println("Seats: " + String.join(", ", selectedSeats));
        bill.printBill();
    }

    /*
     * processPayment - Handles payment for booking
     * 
     * Supports: Card, UPI, Cash
     * Cash only for Officers/Admins (not online customers)
     */
    private void processPayment(Movie movie, Theatre theatre, String city, String showtime, 
                                List<String> selectedSeats, BillDetails bill, int userId) {
        String method = "";
        boolean validPayment = false;

        // Get user role
        String role = "Customer";
        if (userId > 0) {
            User user = UserRepositoryImpl.getUserById(userId);
            if (user != null) {
                role = user.getRole();
            }
        }

        while (!validPayment) {
            System.out.print("\nEnter Payment Method (Card/UPI/Cash): ");
            method = sc.nextLine().trim();

            try {
                if (method.equalsIgnoreCase("Card")) {
                    System.out.print("Enter Card Number (16 digits): ");
                    String cardNumber = sc.nextLine().trim();
                    System.out.print("Enter CVV (3 digits): ");
                    String cvv = sc.nextLine().trim();
                    paymentService.validateCardPayment(cardNumber, cvv);
                    validPayment = true;

                } else if (method.equalsIgnoreCase("UPI")) {
                    System.out.print("Enter UPI ID (e.g., user@bank): ");
                    String upiId = sc.nextLine().trim();
                    paymentService.validateUpiPayment(upiId);
                    validPayment = true;

                } else if (method.equalsIgnoreCase("Cash")) {
                    // Cash only for Officers/Admins
                    if (role.equalsIgnoreCase("Customer") || userId <= 0) {
                        System.out.println("Error: Cash not allowed for online booking!");
                    } else {
                        validPayment = true;
                    }
                } else {
                    System.out.println("Error: Invalid payment method!");
                }
            } catch (org.expleo.TicketBookingJavaProject.exception.PaymentErrorException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.print("Try another payment? (yes/no): ");
                if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                    System.out.println("Booking cancelled due to payment failure.");
                    // Release seats
                    String sessionKey = theatre.getId() + "_" + movie.getId() + "_" + showtime.replace(" ", "_").replace(":", "");
                    for (String label : selectedSeats) {
                        Seat s = seatService.getSeatByLabel(sessionKey, label);
                        if (s != null) {
                            s.setStatus("AVAILABLE");
                            seatService.updateSeat(s);
                        }
                    }
                    return;
                }
            }
        }

        System.out.println("Payment Successful via " + method + "!");

        // Create booking
        String bookingId = bookingService.generateBookingId();
        Booking booking = new Booking(bookingId, userId, movie.getId(), theatre.getId(), showtime, 
                                       selectedSeats, bill.getTotalAmount(), "CONFIRMED");
        bookingService.confirmBooking(booking);

        // Update seat status
        String sessionKey = theatre.getId() + "_" + movie.getId() + "_" + showtime.replace(" ", "_").replace(":", "");
        for (String label : selectedSeats) {
            Seat s = seatService.getSeatByLabel(sessionKey, label);
            if (s != null) {
                s.setStatus("BOOKED");
                seatService.updateSeat(s);
            }
        }

        // Show confirmation
        System.out.println("\n=================================");
        System.out.println("       BOOKING CONFIRMED!        ");
        System.out.println("=================================");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Movie: " + movie.getTitle() + " (" + movie.getLanguage() + ")");
        System.out.println("Theatre: " + theatre.getName() + " (" + city + ")");
        System.out.println("Showtime: " + showtime);
        System.out.println("Seats: " + String.join(", ", selectedSeats));
        System.out.println("-------------------------------------------");
        bill.printBill();
        System.out.println("=================================");
        System.out.println("Payment Method: " + method);
        System.out.println("=================================");
    }

    /*
     * cancelBooking - Cancels an existing booking
     */
    public void cancelBooking(int userId) {
        System.out.println("\n--- CANCEL BOOKING ---");
        
        // Show user's bookings
        if (userId > 0) {
            List<Booking> userBookings = BookingRepositoryImpl.getBookingsByUserId(userId);
            if (!userBookings.isEmpty()) {
                System.out.println("\nYour Bookings:");
                for (Booking b : userBookings) {
                    if (b.getStatus().equalsIgnoreCase("CONFIRMED")) {
                        System.out.println("- " + b.getBookingId() + " | Seats: " + String.join(",", b.getSeatLabels()) 
                            + " | Amount: Rs." + b.getTotalAmount());
                    }
                }
            }
        }
        
        System.out.print("Enter Booking ID: ");
        String id = sc.nextLine().toUpperCase().trim();

        try {
            Booking booking = BookingRepositoryImpl.getBookingById(id);
            if (booking != null) {
                // Check ownership (customers only)
                if (userId > 0 && booking.getUserId() != userId) {
                    System.out.println("Error: You can only cancel your own bookings!");
                    return;
                }
                
                double refundAmount = booking.getTotalAmount();
                bookingService.cancelBooking(id);
                
                // Show refund info
                System.out.println("\n=================================");
                System.out.println("      REFUND INFORMATION          ");
                System.out.println("=================================");
                System.out.println("Booking ID: " + id);
                System.out.println("Amount Paid: Rs." + refundAmount);
                System.out.println("Refund Amount: Rs." + refundAmount);
                System.out.println("-------------------------------------------");
                System.out.println("NOTE: Refund will be processed within 5-7 business days.");
                System.out.println("=================================");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /*
     * viewMyBookings - Shows user's booking history
     */
    public void viewMyBookings(int userId) {
        System.out.println("\n--- MY BOOKINGS ---");
        
        if (userId <= 0) {
            System.out.println("Please login to view your bookings.");
            return;
        }
        
        List<Booking> userBookings = BookingRepositoryImpl.getBookingsByUserId(userId);
        
        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings yet.");
            return;
        }
        
        System.out.println("\n+------------------------------------------+");
        System.out.println("|           YOUR BOOKINGS                   |");
        System.out.println("+------------------------------------------+");
        
        for (Booking b : userBookings) {
            Movie movie = MovieRepositoryImpl.getMovieById(b.getMovieId());
            Theatre theatre = TheatreRepositoryImpl.getTheatreById(b.getTheatreId());
            
            String movieName = (movie != null) ? movie.getTitle() : "Unknown";
            String theatreName = (theatre != null) ? theatre.getName() : "Unknown";
            
            System.out.println("| Booking ID: " + b.getBookingId());
            System.out.println("| Movie: " + movieName);
            System.out.println("| Theatre: " + theatreName);
            System.out.println("| Showtime: " + b.getShowtime());
            System.out.println("| Seats: " + String.join(", ", b.getSeatLabels()));
            System.out.println("| Amount: Rs." + b.getTotalAmount());
            System.out.println("| Status: " + b.getStatus());
            System.out.println("+------------------------------------------+");
        }
    }
}
