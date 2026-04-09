/*
 * FILE: Showtime.java
 * PURPOSE: Stores showtime information for movies.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: All fields are private
 * - Composition: Contains Movie and Theatre objects
 * 
 * Note: This class is not actively used; showtimes are handled as strings.
 */
package org.expleo.TicketBookingJavaProject.model;

import java.time.LocalTime;

/*
 * WHAT THIS CLASS DOES:
 * Represents a specific showtime for a movie at a theatre.
 * Contains the time, movie, and theatre information.
 */
public class Showtime {

    // Showtime ID
    private int id;

    // Time of the show
    private LocalTime time;

    // Movie being shown
    private Movie movie;

    // Theatre where it's shown
    private Theatre theatre;

    /*
     * Constructor - Creates a Showtime
     */
    public Showtime(int id, LocalTime time, Movie movie, Theatre theatre) {
        this.id = id;
        this.time = time;
        this.movie = movie;
        this.theatre = theatre;
    }

    // Get methods
    public int getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    public Movie getMovie() {
        return movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    /*
     * toString - Shows showtime details
     */
    @Override
    public String toString() {
        return id + ". " + time + " - " + movie.getTitle() + " @ " + theatre.getName();
    }
}
