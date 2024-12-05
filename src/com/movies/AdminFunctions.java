package com.movies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminFunctions {
	public static void showAllMovies() {
        String sql = "SELECT movie_id, name, release_date FROM movies";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Movie ID | Movie Name | Release Date");
            System.out.println("-----------------------------------");
            while (rs.next()) {
                int movieId = rs.getInt("movie_id");
                String name = rs.getString("name");
                String releaseDate = rs.getDate("release_date").toString();
                System.out.printf("%-9d | %-10s | %s%n", movieId, name, releaseDate);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
	
	public static void showUnapprovedTickets() {
        String sql = "SELECT booking_id, movie_id, user_id, status FROM bookings WHERE status = 'PENDING'";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Booking ID | Movie ID | User ID | Status");
            System.out.println("----------------------------------------");
            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int movieId = rs.getInt("movie_id");
                int userId = rs.getInt("user_id");
                String status = rs.getString("status");
                System.out.printf("%-10d | %-8d | %-7d | %s%n", bookingId, movieId, userId, status);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void changeMovie(int movieId, String newName, String newReleaseDate, int newTicketsAvailable) {
       String sql = "update movies SET name = ?, release_date = ?, tickets_available = ? WHERE movie_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
           ps.setString(1, newName);
           ps.setString(2, newReleaseDate);
           ps.setInt(3, newTicketsAvailable);
           ps.setInt(4, movieId);
           ps.executeUpdate();
            System.out.println("Movie updated successfully");
        } 
        catch (SQLException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void approveTicket(int bookingId)
    {
        String sql = "update bookings SET status = 'APPROVED' WHERE booking_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, bookingId);
//            ps.setInt(2, movename);
            ps.executeUpdate();
            System.out.println("Ticket approved successfully");
        } 
        catch (SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

