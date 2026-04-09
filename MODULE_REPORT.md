================================================================================
                    TICKET BOOKING SYSTEM - MODULE REPORT
================================================================================

This report shows which modules/features are implemented in which files and
their line numbers.

================================================================================
1. USER AUTHENTICATION & MANAGEMENT MODULE
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/model/User.java
  Purpose: User data model (base class for all users)
  Lines: 1-92
  
- src/main/java/org/expleo/TicketBookingJavaProject/model/Customer.java
  Purpose: Customer class extending User
  Lines: 1-21
  
- src/main/java/org/expleo/TicketBookingJavaProject/model/Administrator.java
  Purpose: Administrator class extending User
  Lines: 1-56

- src/main/java/org/expleo/TicketBookingJavaProject/controller/AuthController.java
  Purpose: Handles user registration and login
  Lines: 1-150
  Key Methods:
  - register() - Lines 21-63
  - login() - Lines 124-149

- src/main/java/org/expleo/TicketBookingJavaProject/repository/impl/UserRepositoryImpl.java
  Purpose: Database operations for users
  Lines: 1-339
  Key Methods:
  - getUserByEmail() - Lines 128-154 (used for login)
  - addUser() - Lines 160-211 (registration)
  - initializeDefaultAdmin() - Lines 23-45

- src/main/java/org/expleo/TicketBookingJavaProject/service/UserService.java
  Purpose: User business logic
  Lines: 1-36
  Key Methods:
  - updateProfile() - Lines 21-35

- src/main/java/org/expleo/TicketBookingJavaProject/dao/UserDAO.java
  Purpose: Interface for user operations
  Lines: 1-64

FEATURES:
- User registration (Customer only)
- User login with email/password
- Profile viewing
- Profile updating (name, phone, password)
- Default Super Admin creation

================================================================================
2. MOVIE MANAGEMENT MODULE
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/model/Movie.java
  Purpose: Movie data model
  Lines: 1-59

- src/main/java/org/expleo/TicketBookingJavaProject/controller/MovieController.java
  Purpose: Handles movie operations (add, update, delete, view)
  Lines: 1-276
  Key Methods:
  - addMovie() - Lines 39-80
  - updateMovie() - Lines 86-147
  - deleteMovie() - Lines 153-188
  - viewMovies() - Lines 194-213, 219-240

- src/main/java/org/expleo/TicketBookingJavaProject/repository/impl/MovieRepositoryImpl.java
  Purpose: Database operations for movies
  Lines: 1-291
  Key Methods:
  - getAllMovies() - Lines 19-43
  - addMovie() - Lines 81-101
  - updateMovie() - Lines 110-134
  - deleteMovie() - Lines 140-158
  - searchByTitle() - Lines 165-191
  - searchByLanguage() - Lines 198-224
  - searchByGenre() - Lines 231-257

- src/main/java/org/expleo/TicketBookingJavaProject/service/MovieService.java
  Purpose: Movie business logic (minimal)
  Lines: 1-20

- src/main/java/org/expleo/TicketBookingJavaProject/dao/MovieDAO.java
  Purpose: Interface for movie operations
  Lines: 1-64

FEATURES:
- Add movie to theatre (Theatre Admin only)
- Update movie details
- Delete movie
- View movies (all or by theatre)
- Search by title
- Search by language
- Search by genre

================================================================================
3. THEATRE MANAGEMENT MODULE
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/model/Theatre.java
  Purpose: Theatre data model
  Lines: 1-54

- src/main/java/org/expleo/TicketBookingJavaProject/controller/SuperAdminController.java
  Purpose: Super Admin operations for theatres
  Lines: 1-291
  Key Methods:
  - createTheatre() - Lines 55-74
  - createTheatreAdmin() - Lines 79-132
  - removeTheatre() - Lines 201-228
  - removeTheatreAdmin() - Lines 233-266
  - viewTheatres() - Lines 271-290

- src/main/java/org/expleo/TicketBookingJavaProject/controller/TheatreAdminController.java
  Purpose: Theatre Admin operations
  Lines: 1-156
  Key Methods:
  - createOfficer() - Lines 64-127

- src/main/java/org/expleo/TicketBookingJavaProject/repository/impl/TheatreRepositoryImpl.java
  Purpose: Database operations for theatres
  Lines: 1-226
  Key Methods:
  - getAllTheatres() - Lines 19-41
  - getTheatreById() - Lines 48-71
  - addTheatre() - Lines 77-100
  - getTheatresByCity() - Lines 179-203
  - getAllCities() - Lines 209-225

- src/main/java/org/expleo/TicketBookingJavaProject/service/TheatreService.java
  Purpose: Theatre business logic (placeholder)
  Lines: 1-5

FEATURES:
- Create theatre (Super Admin)
- Remove theatre (Super Admin)
- View all theatres (Super Admin)
- Create Theatre Admin (Super Admin)
- Assign admin to theatre
- Get theatres by city
- Get all cities

================================================================================
4. SEARCH MODULE
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/controller/SearchController.java
  Purpose: Handles all search operations
  Lines: 1-540
  Key Methods:
  - searchMovie() - Lines 34-64
  - searchByMovieName() - Lines 71-118
  - searchByLanguage() - Lines 125-196
  - searchByGenre() - Lines 203-276
  - searchByCity() - Lines 283-361
  - searchByTheatre() - Lines 368-445

- src/main/java/org/expleo/TicketBookingJavaProject/service/SearchService.java
  Purpose: Search business logic (minimal)
  Lines: 1-29

FEATURES:
- Search by movie name
- Search by language
- Search by genre
- Search by city
- Search by theatre
- Select movie for booking

================================================================================
5. BOOKING MODULE
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/model/Booking.java
  Purpose: Booking data model
  Lines: 1-115

- src/main/java/org/expleo/TicketBookingJavaProject/controller/BookingController.java
  Purpose: Complete booking flow controller
  Lines: 1-824
  Key Methods:
  - startBooking() - Lines 104-171
  - bookSeats() - Lines 353-493
  - selectSeats() - Lines 498-531
  - calculatePrice() - Lines 539-575
  - processPayment() - Lines 629-724
  - cancelBooking() - Lines 736-782
  - viewMyBookings() - Lines 787-823

- src/main/java/org/expleo/TicketBookingJavaProject/service/BookingService.java
  Purpose: Booking business logic
  Lines: 1-90
  Key Methods:
  - generateBookingId() - Lines 22-24
  - confirmBooking() - Lines 40-44
  - cancelBooking() - Lines 51-74

- src/main/java/org/expleo/TicketBookingJavaProject/repository/impl/BookingRepositoryImpl.java
  Purpose: Database operations for bookings
  Lines: 1-272
  Key Methods:
  - getBookings() - Lines 51-75
  - getBookingsByUserId() - Lines 83-108
  - getBookingsByTheatreId() - Lines 116-141
  - addBooking() - Lines 180-199
  - cancelBooking() - Lines 252-271

- src/main/java/org/expleo/TicketBookingJavaProject/dao/BookingDAO.java
  Purpose: Interface for booking operations
  Lines: 1-54

FEATURES:
- Movie selection
- City selection
- Theatre selection
- Showtime selection
- Seat selection
- Price calculation with GST
- Booking confirmation
- Booking cancellation
- View booking history
- Seat modification before payment

================================================================================
6. SEAT MANAGEMENT MODULE
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/model/Seat.java
  Purpose: Seat data model
  Lines: 1-72

- src/main/java/org/expleo/TicketBookingJavaProject/model/BillDetails.java
  Purpose: Bill calculation model
  Lines: 1-68
  Key Methods:
  - printBill() - Lines 56-67

- src/main/java/org/expleo/TicketBookingJavaProject/controller/SeatController.java
  Purpose: Seat display and selection UI
  Lines: 1-160
  Key Methods:
  - displaySeatLayout() - Lines 33-60
  - displayAvailableSeats() - Lines 65-73
  - selectSeats() - Lines 123-150

- src/main/java/org/expleo/TicketBookingJavaProject/service/SeatService.java
  Purpose: Seat business logic
  Lines: 1-134
  Key Methods:
  - getAvailableSeats() - Lines 32-40
  - getBookedSeats() - Lines 48-56
  - validateSingleSeatSelection() - Lines 81-93
  - validateMultipleSeatSelection() - Lines 103-124

- src/main/java/org/expleo/TicketBookingJavaProject/repository/impl/SeatRepositoryImpl.java
  Purpose: Database operations for seats
  Lines: 1-138
  Key Methods:
  - getSeatsForSession() - Lines 49-80
  - createSeatLayout() - Lines 90-115
  - updateSeat() - Lines 122-137

- src/main/java/org/expleo/TicketBookingJavaProject/dao/SeatDAO.java
  Purpose: Interface for seat operations
  Lines: 1-42

FEATURES:
- Seat layout display
- Available/booked seat listing
- Seat validation
- Seat status update
- Tiered pricing (Rs.190, Rs.160, Rs.60)
- GST calculation (3.5%)
- Application fee (Rs.10 per ticket)
- Bill generation

================================================================================
7. PAYMENT MODULE
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/model/Payment.java
  Purpose: Payment data model
  Lines: 1-39

- src/main/java/org/expleo/TicketBookingJavaProject/model/Cardpayment.java
  Purpose: Card payment extending Payment
  Lines: 1-15

- src/main/java/org/expleo/TicketBookingJavaProject/model/Upipayment.java
  Purpose: UPI payment extending Payment
  Lines: 1-12

- src/main/java/org/expleo/TicketBookingJavaProject/model/PaymentResponse.java
  Purpose: Payment result model
  Lines: 1-41

- src/main/java/org/expleo/TicketBookingJavaProject/service/PaymentService.java
  Purpose: Payment processing logic
  Lines: 1-67
  Key Methods:
  - validateCardPayment() - Lines 32-44
  - validateUpiPayment() - Lines 51-57

- src/main/java/org/expleo/TicketBookingJavaProject/service/PaymentController.java
  Purpose: Payment UI controller
  Lines: 1-33

- src/main/java/org/expleo/TicketBookingJavaProject/exception/PaymentErrorException.java
  Purpose: Payment error exception
  Lines: 1-7

FEATURES:
- Card payment validation (16 digit card, 3 digit CVV)
- UPI payment validation (@ symbol required)
- Cash payment (Officers/Admins only)
- Payment processing

================================================================================
8. REPORT MODULE
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/App.java
  Purpose: Report generation
  Lines: 359-491
  Key Methods:
  - viewTheatreAdminReports() - Lines 363-396
  - viewMovieReport() - Lines 401-440
  - viewBookingReport() - Lines 445-491

FEATURES:
- Movie Report (for Theatre Admin)
- Booking Report (for Theatre Admin)
- Revenue summary
- Booking statistics

================================================================================
9. OFFICER MANAGEMENT MODULE
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/model/Officer.java
  Purpose: Officer placeholder (not actively used)
  Lines: 1-5

- src/main/java/org/expleo/TicketBookingJavaProject/controller/OfficerController.java
  Purpose: Officer operations
  Lines: 1-74
  Key Methods:
  - viewMovies() - Lines 30-38
  - bookTicket() - Lines 51-65
  - cancelTicket() - Lines 70-73

FEATURES:
- View movies in assigned theatre
- Book tickets (only for assigned theatre)
- Cancel tickets

================================================================================
10. DATABASE & CONFIGURATION MODULE
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/config/DBConnection.java
  Purpose: Database connection manager
  Lines: 1-38
  Key Methods:
  - getConnection() - Lines 26-37

- src/main/java/org/expleo/TicketBookingJavaProject/config/DatabaseSetup.java
  Purpose: Creates database tables
  Lines: 1-46
  Key Methods:
  - initialize() - Lines 9-45

TABLES CREATED:
- users
- theatres
- movies
- seats
- bookings

================================================================================
11. UTILITY MODULES
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/util/InputUtil.java
  Purpose: Safe input reading
  Lines: 1-95
  Key Methods:
  - getIntInput() - Lines 19-36
  - getStringInput() - Lines 45-51
  - getConfirmation() - Lines 60-72

- src/main/java/org/expleo/TicketBookingJavaProject/util/PaymentUtil.java
  Purpose: Payment utilities
  Lines: 1-15

- src/main/java/org/expleo/TicketBookingJavaProject/util/BookingIdGenerator.java
  Purpose: Generates booking IDs
  Lines: 1-10

================================================================================
12. EXCEPTION HANDLING MODULE
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/exception/CustomException.java
  Purpose: Base exception class
  Lines: 1-7

- src/main/java/org/expleo/TicketBookingJavaProject/exception/BookingNotFoundException.java
  Purpose: Booking not found exception
  Lines: 1-7

- src/main/java/org/expleo/TicketBookingJavaProject/exception/InvalidSeatSelectionException.java
  Purpose: Invalid seat selection exception
  Lines: 1-8

================================================================================
13. LEGACY/SUPPORT CLASSES
================================================================================

FILES:
- src/main/java/org/expleo/TicketBookingJavaProject/model/City.java
  Purpose: City model (mostly replaced)
  Lines: 1-27

- src/main/java/org/expleo/TicketBookingJavaProject/model/Showtime.java
  Purpose: Showtime model (mostly replaced)
  Lines: 1-53

- src/main/java/org/expleo/TicketBookingJavaProject/model/Guest.java
  Purpose: Guest placeholder
  Lines: 1-5

- src/main/java/org/expleo/TicketBookingJavaProject/model/Hall.java
  Purpose: Hall placeholder
  Lines: 1-5

- src/main/java/org/expleo/TicketBookingJavaProject/model/Ticket.java
  Purpose: Ticket placeholder
  Lines: 1-5

- src/main/java/org/expleo/TicketBookingJavaProject/model/Cashpayment.java
  Purpose: Cash payment placeholder
  Lines: 1-5

- src/main/java/org/expleo/TicketBookingJavaProject/controller/UserController.java
  Purpose: Placeholder
  Lines: 1-5

- src/main/java/org/expleo/TicketBookingJavaProject/controller/AppController.java
  Purpose: Placeholder
  Lines: 1-9

- src/main/java/org/expleo/TicketBookingJavaProject/controller/SelectionController.java
  Purpose: Alternative selection methods
  Lines: 1-85

- src/main/java/org/expleo/TicketBookingJavaProject/service/SelectionService.java
  Purpose: Alternative selection service
  Lines: 1-42

- src/main/java/org/expleo/TicketBookingJavaProject/repository/impl/PaymentRepositoryImpl.java
  Purpose: Placeholder
  Lines: 1-5

================================================================================
                              END OF REPORT
================================================================================
