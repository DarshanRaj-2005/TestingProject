/*
 * FILE: Theatre.java
 * PURPOSE: Stores theatre information like name, city, and admin assignment.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: All fields are private, accessed through getters/setters
 * - Abstraction: Hides theatre details, provides simple interface
 * 
 * HOW IT'S USED:
 * - Created when Super Admin adds a new theatre
 * - Used to show theatres to users
 * - Links to Theatre Admin who manages it
 */
package org.expleo.TicketBookingJavaProject.model;

/*
 * WHAT THIS CLASS DOES:
 * Represents a movie theatre/cinema.
 * Each theatre has a name, city, and an admin who manages it.
 * 
 * EXAMPLE:
 * Theatre theatre = new Theatre(1, "PVR Cinemas", "Chennai");
 * theatre.setAdminId(5); // Assign admin with userId 5
 */
public class Theatre {

    // Unique ID for the theatre (1, 2, 3, etc.)
    private int id;
    
    // Name of the theatre (like "PVR Cinemas", "INOX")
    private String name;
    
    // City where the theatre is located
    private String city;
    
    // ID of the admin who manages this theatre (0 = no admin assigned)
    private int adminId;

    /*
     * Constructor - Creates a new Theatre object
     * 
     * Parameters:
     * - id: Theatre ID (0 for new theatres, database assigns the real ID)
     * - name: Theatre name
     * - city: City name
     */
    public Theatre(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.adminId = 0; // Default: no admin yet
    }

    // Getter and Setter methods
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) { this.adminId = adminId; }

    /*
     * toString - Converts theatre to readable text
     * 
     * Example output: "1. PVR Cinemas (Chennai)"
     */
    @Override
    public String toString() {
        return id + ". " + name + " (" + city + ")";
    }
}
