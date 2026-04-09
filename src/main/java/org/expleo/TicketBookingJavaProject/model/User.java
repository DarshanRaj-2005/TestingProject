/*
 * FILE: User.java
 * PURPOSE: Stores user information like name, email, phone, password, and role.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: All fields are private, accessed through getter/setter methods
 * - Inheritance: This class is the parent for Customer and Administrator classes
 * - Abstraction: Hides user details complexity, provides simple interface via getters
 */
package org.expleo.TicketBookingJavaProject.model;

/*
 * WHAT THIS CLASS DOES:
 * This class holds all information about a user in the system.
 * Users can be: Super Admin, Theatre Admin, Officer, or Customer.
 * 
 * HOW IT'S USED:
 * - Created when a new user registers
 * - Retrieved from database when user logs in
 * - Updated when user changes their profile
 * 
 * IMPORTANT: This is the base class. Other classes like Customer and Administrator
 * inherit from this class to add their own features.
 */
public class User {

    // Stores the user's unique ID number (given by database)
    protected int userId;
    
    // User's full name (like "John Doe")
    protected String name;
    
    // User's email address (used for login)
    protected String email;
    
    // User's phone number (10 digits)
    protected String phone;
    
    // User's password (stored as plain text for simplicity)
    protected String password;
    
    // User's role in the system: "Super Admin", "Theatre Admin", "Officer", or "Customer"
    protected String role;
    
    // ID of the theatre this user works at (used for Theatre Admin and Officer)
    protected int theatreId;

    /*
     * Constructor - Creates a new User object
     * 
     * Parameters:
     * - userId: Unique ID from database (0 for new users)
     * - name: Full name
     * - email: Email address
     * - phone: Phone number (10 digits)
     * - password: Login password
     * - role: User role in the system
     * 
     * Note: theatreId defaults to 0 (no theatre assigned yet)
     */
    public User(int userId, String name, String email, String phone, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.theatreId = 0;
    }

    // Getter methods - Used to read private fields
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public int getTheatreId() { return theatreId; }
    public void setTheatreId(int theatreId) { this.theatreId = theatreId; }
}
