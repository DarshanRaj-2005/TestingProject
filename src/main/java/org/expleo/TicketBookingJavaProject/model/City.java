/*
 * FILE: City.java
 * PURPOSE: Stores city information.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Fields are private, accessed via getters
 * 
 * Note: This class is mostly replaced by TheatreRepositoryImpl.getAllCities()
 */
package org.expleo.TicketBookingJavaProject.model;

/*
 * WHAT THIS CLASS DOES:
 * Holds city name and ID.
 * Used to represent locations where theatres are located.
 */
public class City {

    // City ID
    private int id;

    // City name
    private String name;

    /*
     * Constructor - Creates a City
     */
    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Get city name
    public String getName() {
        return name;
    }

    // Get city ID
    public int getId() {
        return id;
    }
}
