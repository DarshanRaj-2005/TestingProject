/*
 * FILE: SearchController.java
 * PURPOSE: Handles searching for movies.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields
 * - Abstraction: Simple search interface
 * - Composition: Uses MovieRepositoryImpl
 * 
 * WHAT THIS FILE DOES:
 * - Search movies by name, language, genre
 * - Search by city or theatre
 * - Allows selecting a movie for booking
 * 
 * SEARCH OPTIONS:
 * 1. By movie name
 * 2. By language
 * 3. By genre
 * 4. By city
 * 5. By theatre
 */
package org.expleo.TicketBookingJavaProject.controller;

import java.util.*;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.util.InputUtil;

/*
 * Controller for search operations.
 * Handles searching movies by various criteria.
 */
public class SearchController {

    // Scanner for user input
    private Scanner sc = new Scanner(System.in);
    
    // Reference to MovieController
    private MovieController movieController;
    
    // Movie selected from search (for booking)
    private Movie selectedMovie;

    /*
     * Constructor - Sets up the controller
     */
    public SearchController(MovieController movieController) {
        this.movieController = movieController;
    }

    /*
     * searchMovie - Main search menu
     * 
     * Shows search options and handles user choice.
     * 
     * Parameter:
     * - allowBooking: true = can book after search, false = view only
     */
    public Movie searchMovie(boolean allowBooking) {
        while (true) {
            System.out.println("\n--- SEARCH OPTIONS ---");
            System.out.println("1. Search by Movie Name");
            System.out.println("2. Search by Language");
            System.out.println("3. Search by Genre");
            System.out.println("4. Search by City");
            System.out.println("5. Search by Theatre");
            System.out.println("6. Back to Menu");
            
            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput(sc);
            
            switch (choice) {
                case 1:
                    return searchByMovieName(allowBooking);
                case 2:
                    return searchByLanguage(allowBooking);
                case 3:
                    return searchByGenre(allowBooking);
                case 4:
                    return searchByCity(allowBooking);
                case 5:
                    return searchByTheatre(allowBooking);
                case 6:
                    return null;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /*
     * searchByMovieName - Find movies by title
     * 
     * Shows movies matching the search term.
     * If allowBooking is true, lets user select a movie.
     */
    private Movie searchByMovieName(boolean allowBooking) {
        System.out.println("\n--- SEARCH BY MOVIE NAME ---");
        System.out.print("Enter Movie Name to search: ");
        String searchQuery = sc.nextLine().trim().toLowerCase();
        
        if (searchQuery.isEmpty()) {
            System.out.println("Error: Please enter a movie name!");
            return null;
        }

        // Search in database
        List<Movie> matchingMovies = MovieRepositoryImpl.searchByTitle(searchQuery);
        
        if (matchingMovies.isEmpty()) {
            System.out.println("No movies found matching '" + searchQuery + "'");
            return null;
        }

        // Display movies
        System.out.println("\n--- MATCHING MOVIES ---");
        List<Movie> uniqueMovies = getUniqueMovies(matchingMovies);
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getLanguage() + ") | " + m.getGenre());
        }

        // Offer to book
        if (allowBooking) {
            System.out.print("\nDo you want to book a ticket for one of these movies? (yes/no): ");
            String choice = sc.nextLine().trim().toLowerCase();
            
            if (choice.equals("yes")) {
                System.out.print("Select Movie (Number): ");
                int index = InputUtil.getIntInput(sc);
                
                if (index >= 1 && index <= uniqueMovies.size()) {
                    selectedMovie = uniqueMovies.get(index - 1);
                    System.out.println("\nMovie Selected: " + selectedMovie.getTitle() + " (" + selectedMovie.getLanguage() + ")");
                    System.out.println("Please proceed to 'Book Ticket' option to complete your booking.");
                    return selectedMovie;
                } else {
                    System.out.println("Invalid selection!");
                }
            }
        }
        
        return null;
    }

    /*
     * searchByLanguage - Find movies by language
     * 
     * Shows available languages, then movies in that language.
     */
    private Movie searchByLanguage(boolean allowBooking) {
        System.out.println("\n--- SEARCH BY LANGUAGE ---");
        System.out.println("Available Languages:");
        
        // Get all unique languages
        Set<String> languages = new HashSet<>();
        List<Movie> allMovies = MovieRepositoryImpl.getAllMovies();
        for (Movie m : allMovies) {
            languages.add(m.getLanguage());
        }
        
        if (languages.isEmpty()) {
            System.out.println("No languages available (no movies in system).");
            return null;
        }

        // Show languages
        List<String> langList = new ArrayList<>(languages);
        Collections.sort(langList);
        
        for (int i = 0; i < langList.size(); i++) {
            System.out.println((i + 1) + ". " + langList.get(i));
        }
        
        System.out.print("\nSelect Language (Number): ");
        int langChoice = InputUtil.getIntInput(sc);
        
        if (langChoice < 1 || langChoice > langList.size()) {
            System.out.println("Invalid selection!");
            return null;
        }
        
        String selectedLanguage = langList.get(langChoice - 1);
        
        // Search movies
        List<Movie> matchingMovies = MovieRepositoryImpl.searchByLanguage(selectedLanguage);
        
        if (matchingMovies.isEmpty()) {
            System.out.println("No movies found in " + selectedLanguage + " language.");
            return null;
        }

        // Display movies
        System.out.println("\n--- MOVIES IN " + selectedLanguage.toUpperCase() + " ---");
        List<Movie> uniqueMovies = getUniqueMovies(matchingMovies);
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " | " + m.getGenre() + " | " + m.getDuration() + " mins");
        }

        // Offer to book
        if (allowBooking) {
            System.out.print("\nDo you want to book a ticket for one of these movies? (yes/no): ");
            String choice = sc.nextLine().trim().toLowerCase();
            
            if (choice.equals("yes")) {
                System.out.print("Select Movie (Number): ");
                int index = InputUtil.getIntInput(sc);
                
                if (index >= 1 && index <= uniqueMovies.size()) {
                    selectedMovie = uniqueMovies.get(index - 1);
                    System.out.println("\nMovie Selected: " + selectedMovie.getTitle() + " (" + selectedMovie.getLanguage() + ")");
                    System.out.println("Please proceed to 'Book Ticket' option to complete your booking.");
                    return selectedMovie;
                } else {
                    System.out.println("Invalid selection!");
                }
            }
        }
        
        return null;
    }

    /*
     * searchByGenre - Find movies by genre
     * 
     * Shows available genres, then movies in that genre.
     */
    private Movie searchByGenre(boolean allowBooking) {
        System.out.println("\n--- SEARCH BY GENRE ---");
        System.out.println("Available Genres:");
        
        // Get all unique genres
        Set<String> genres = new HashSet<>();
        List<Movie> allMovies = MovieRepositoryImpl.getAllMovies();
        for (Movie m : allMovies) {
            if (m.getGenre() != null && !m.getGenre().isEmpty()) {
                genres.add(m.getGenre());
            }
        }
        
        if (genres.isEmpty()) {
            System.out.println("No genres available (no movies in system).");
            return null;
        }

        // Show genres
        List<String> genreList = new ArrayList<>(genres);
        Collections.sort(genreList);
        
        for (int i = 0; i < genreList.size(); i++) {
            System.out.println((i + 1) + ". " + genreList.get(i));
        }
        
        System.out.print("\nSelect Genre (Number): ");
        int genreChoice = InputUtil.getIntInput(sc);
        
        if (genreChoice < 1 || genreChoice > genreList.size()) {
            System.out.println("Invalid selection!");
            return null;
        }
        
        String selectedGenre = genreList.get(genreChoice - 1);
        
        // Search movies
        List<Movie> matchingMovies = MovieRepositoryImpl.searchByGenre(selectedGenre);
        
        if (matchingMovies.isEmpty()) {
            System.out.println("No movies found in " + selectedGenre + " genre.");
            return null;
        }

        // Display movies
        System.out.println("\n--- MOVIES IN " + selectedGenre.toUpperCase() + " GENRE ---");
        List<Movie> uniqueMovies = getUniqueMovies(matchingMovies);
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " | " + m.getLanguage() + " | " + m.getDuration() + " mins");
        }

        // Offer to book
        if (allowBooking) {
            System.out.print("\nDo you want to book a ticket for one of these movies? (yes/no): ");
            String choice = sc.nextLine().trim().toLowerCase();
            
            if (choice.equals("yes")) {
                System.out.print("Select Movie (Number): ");
                int index = InputUtil.getIntInput(sc);
                
                if (index >= 1 && index <= uniqueMovies.size()) {
                    selectedMovie = uniqueMovies.get(index - 1);
                    System.out.println("\nMovie Selected: " + selectedMovie.getTitle() + " (" + selectedMovie.getLanguage() + ")");
                    System.out.println("Please proceed to 'Book Ticket' option to complete your booking.");
                    return selectedMovie;
                } else {
                    System.out.println("Invalid selection!");
                }
            }
        }
        
        return null;
    }

    /*
     * searchByCity - Find movies by city
     * 
     * Shows theatres in a city, then movies at those theatres.
     */
    private Movie searchByCity(boolean allowBooking) {
        System.out.println("\n--- SEARCH BY CITY ---");
        
        // Get cities with theatres
        List<String> cities = TheatreRepositoryImpl.getAllCities();
        
        if (cities.isEmpty()) {
            System.out.println("No cities available (no theatres in system).");
            return null;
        }

        // Show cities
        System.out.println("Available Cities:");
        Collections.sort(cities);
        
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }
        
        System.out.print("\nSelect City (Number): ");
        int cityChoice = InputUtil.getIntInput(sc);
        
        if (cityChoice < 1 || cityChoice > cities.size()) {
            System.out.println("Invalid selection!");
            return null;
        }
        
        String selectedCity = cities.get(cityChoice - 1);
        
        // Get theatres in this city
        List<Theatre> theatres = TheatreRepositoryImpl.getTheatresByCity(selectedCity);
        
        if (theatres.isEmpty()) {
            System.out.println("No theatres found in " + selectedCity + ".");
            return null;
        }

        // Get movies from all theatres in this city
        Set<Movie> cityMovies = new HashSet<>();
        for (Theatre t : theatres) {
            List<Movie> theatreMovies = MovieRepositoryImpl.getMoviesByTheatre(t.getId());
            cityMovies.addAll(theatreMovies);
        }
        
        if (cityMovies.isEmpty()) {
            System.out.println("No movies available in " + selectedCity + ".");
            return null;
        }

        // Display movies
        System.out.println("\n--- MOVIES IN " + selectedCity.toUpperCase() + " ---");
        List<Movie> uniqueMovies = getUniqueMovies(new ArrayList<>(cityMovies));
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " | " + m.getLanguage() + " | " + m.getGenre());
        }

        // Offer to book
        if (allowBooking) {
            System.out.print("\nDo you want to book a ticket for one of these movies? (yes/no): ");
            String choice = sc.nextLine().trim().toLowerCase();
            
            if (choice.equals("yes")) {
                System.out.print("Select Movie (Number): ");
                int index = InputUtil.getIntInput(sc);
                
                if (index >= 1 && index <= uniqueMovies.size()) {
                    selectedMovie = uniqueMovies.get(index - 1);
                    System.out.println("\nMovie Selected: " + selectedMovie.getTitle() + " (" + selectedMovie.getLanguage() + ")");
                    System.out.println("Please proceed to 'Book Ticket' option to complete your booking.");
                    return selectedMovie;
                } else {
                    System.out.println("Invalid selection!");
                }
            }
        }
        
        return null;
    }

    /*
     * searchByTheatre - Find movies by theatre
     * 
     * Shows all theatres, then movies at selected theatre.
     */
    private Movie searchByTheatre(boolean allowBooking) {
        System.out.println("\n--- SEARCH BY THEATRE ---");
        
        // Get all theatres
        List<Theatre> allTheatres = TheatreRepositoryImpl.getAllTheatres();
        
        if (allTheatres.isEmpty()) {
            System.out.println("No theatres available in the system.");
            return null;
        }

        // Group theatres by city
        Map<String, List<Theatre>> theatresByCity = new HashMap<>();
        for (Theatre t : allTheatres) {
            theatresByCity.computeIfAbsent(t.getCity(), k -> new ArrayList<>()).add(t);
        }
        
        System.out.println("Available Theatres:");
        int counter = 1;
        List<Theatre> theatreList = new ArrayList<>();
        
        for (String city : new TreeMap<>(theatresByCity).keySet()) {
            System.out.println("\n--- " + city + " ---");
            for (Theatre t : theatresByCity.get(city)) {
                System.out.println((counter) + ". " + t.getName());
                theatreList.add(t);
                counter++;
            }
        }
        
        System.out.print("\nSelect Theatre (Number): ");
        int theatreChoice = InputUtil.getIntInput(sc);
        
        if (theatreChoice < 1 || theatreChoice > theatreList.size()) {
            System.out.println("Invalid selection!");
            return null;
        }
        
        Theatre selectedTheatre = theatreList.get(theatreChoice - 1);
        
        // Get movies at this theatre
        List<Movie> movies = MovieRepositoryImpl.getMoviesByTheatre(selectedTheatre.getId());
        
        if (movies.isEmpty()) {
            System.out.println("No movies available in " + selectedTheatre.getName() + ".");
            return null;
        }

        // Display movies
        System.out.println("\n--- MOVIES AT " + selectedTheatre.getName().toUpperCase() + " ---");
        List<Movie> uniqueMovies = getUniqueMovies(movies);
        
        for (int i = 0; i < uniqueMovies.size(); i++) {
            Movie m = uniqueMovies.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " | " + m.getLanguage() + " | " + m.getGenre());
        }

        // Offer to book
        if (allowBooking) {
            System.out.print("\nDo you want to book a ticket for one of these movies? (yes/no): ");
            String choice = sc.nextLine().trim().toLowerCase();
            
            if (choice.equals("yes")) {
                System.out.print("Select Movie (Number): ");
                int index = InputUtil.getIntInput(sc);
                
                if (index >= 1 && index <= uniqueMovies.size()) {
                    selectedMovie = uniqueMovies.get(index - 1);
                    System.out.println("\nMovie Selected: " + selectedMovie.getTitle() + " (" + selectedMovie.getLanguage() + ")");
                    System.out.println("Please proceed to 'Book Ticket' option to complete your booking.");
                    return selectedMovie;
                } else {
                    System.out.println("Invalid selection!");
                }
            }
        }
        
        return null;
    }

    /*
     * getUniqueMovies - Removes duplicate movies
     * 
     * Same movie might be in multiple theatres.
     * This method keeps only one copy based on title+language.
     */
    private List<Movie> getUniqueMovies(List<Movie> movies) {
        Map<String, Movie> uniqueMap = new LinkedHashMap<>();
        for (Movie m : movies) {
            String key = m.getTitle().toLowerCase() + "_" + m.getLanguage().toLowerCase();
            uniqueMap.putIfAbsent(key, m);
        }
        return new ArrayList<>(uniqueMap.values());
    }

    // Getter and setter for selected movie
    public Movie getSelectedMovie() {
        return selectedMovie;
    }
    
    public void setSelectedMovie(Movie movie) {
        this.selectedMovie = movie;
    }

    /*
     * clearSelectedMovie - Clears the selected movie
     */
    public void clearSelectedMovie() {
        selectedMovie = null;
    }

    /*
     * getCitiesWithMovies - Gets cities where movies are available
     */
    public List<String> getCitiesWithMovies() {
        Set<String> cities = new HashSet<>();
        List<Movie> movies = MovieRepositoryImpl.getAllMovies();
        
        for (Movie m : movies) {
            Theatre theatre = TheatreRepositoryImpl.getTheatreById(m.getTheatreId());
            if (theatre != null) {
                cities.add(theatre.getCity());
            }
        }
        
        List<String> cityList = new ArrayList<>(cities);
        Collections.sort(cityList);
        return cityList;
    }

    /*
     * getTheatresForSelectedMovie - Gets theatres showing selected movie
     */
    public List<Theatre> getTheatresForSelectedMovie(String city) {
        if (selectedMovie == null) {
            return new ArrayList<>();
        }
        
        List<Theatre> result = new ArrayList<>();
        List<Theatre> allTheatres = TheatreRepositoryImpl.getAllTheatres();
        
        for (Theatre t : allTheatres) {
            // Check if this theatre has the movie
            List<Movie> theatreMovies = MovieRepositoryImpl.getMoviesByTheatre(t.getId());
            boolean hasMovie = false;
            
            for (Movie m : theatreMovies) {
                if (m.getTitle().equalsIgnoreCase(selectedMovie.getTitle()) &&
                    m.getLanguage().equalsIgnoreCase(selectedMovie.getLanguage())) {
                    hasMovie = true;
                    break;
                }
            }
            
            if (hasMovie) {
                // Apply city filter if provided
                if (city == null || city.isEmpty() || t.getCity().equalsIgnoreCase(city)) {
                    result.add(t);
                }
            }
        }
        
        return result;
    }
}
