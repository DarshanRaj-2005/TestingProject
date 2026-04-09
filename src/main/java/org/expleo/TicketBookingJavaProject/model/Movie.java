/*
 * FILE: Movie.java
 * PURPOSE: Stores movie information like title, genre, language, duration, etc.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: All fields are private, accessed through getter methods
 * - Abstraction: Shows only movie details, hides internal complexity
 * 
 * HOW IT'S USED:
 * - Created when adding a new movie to a theatre
 * - Retrieved when displaying movie list to users
 * - Used in search results
 */
package org.expleo.TicketBookingJavaProject.model;

/*
 * WHAT THIS CLASS DOES:
 * Holds all information about a single movie.
 * Each movie belongs to a specific theatre.
 * 
 * EXAMPLE:
 * Movie movie = new Movie("M001", "Baahubali", "Action", "Telugu", 180, "2024-01-15", 1);
 */
public class Movie {

    // Movie's unique ID (like "M001")
    private String id;
    
    // Movie title (like "Baahubali")
    private String title;
    
    // Movie genre (like "Action", "Comedy", "Drama")
    private String genre;
    
    // Language of the movie (like "Tamil", "English", "Hindi")
    private String language;
    
    // How long the movie runs (in minutes)
    private int duration;
    
    // When the movie was released (stored as text, format: YYYY-MM-DD)
    private String releaseDate;
    
    // Which theatre is showing this movie (links to Theatre table)
    private int theatreId;

    /*
     * Constructor - Creates a new Movie object
     * 
     * Parameters:
     * - id: Unique movie ID
     * - title: Movie name
     * - genre: Type of movie
     * - language: Language of the movie
     * - duration: Length in minutes
     * - releaseDate: Release date
     * - theatreId: Which theatre shows this movie
     */
    public Movie(String id, String title, String genre, String language, int duration, String releaseDate, int theatreId) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.theatreId = theatreId;
    }

    // Getter methods - Read-only access to movie fields
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getLanguage() { return language; }
    public int getDuration() { return duration; }
    public String getReleaseDate() { return releaseDate; }
    public int getTheatreId() { return theatreId; }

    /*
     * toString - Converts movie to readable text
     * 
     * Example output: "Baahubali (Telugu) | Action | 180 mins"
     */
    @Override
    public String toString() {
        return title + " (" + language + ") | " + genre + " | " + duration + " mins";
    }
}
