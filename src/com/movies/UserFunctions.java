	package com.movies;
	
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	
	public class UserFunctions {
		
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
	
		public static void bookTicket(int userId, int movieId) {
		    String fetchMovieSql = "select name from movies WHERE movie_id = ?";
		    String insertBookingSql = "INSERT INTO bookings (user_id, movie_id, status) VALUES (?, ?, 'PENDING')";
		    
		    try (Connection con = DatabaseConnection.getConnection();
		         PreparedStatement fetchMoviePs = con.prepareStatement(fetchMovieSql)) {
		        
		        fetchMoviePs.setInt(1, movieId);
		        ResultSet rs = fetchMoviePs.executeQuery();
		        
		        if (rs.next()) {
		            String movieName = rs.getString("name");
		            System.out.println("Booking ticket for movie: " + movieName);
		            
		            try (PreparedStatement insertBookingPs = con.prepareStatement(insertBookingSql)) {
		                insertBookingPs.setInt(1, userId);
		                insertBookingPs.setInt(2, movieId);
		                insertBookingPs.executeUpdate();
		                System.out.println("Ticket booked successfully for movie: " + movieName);
		            }
		        } else {
		            System.out.println("Invalid movie ID. Please try again.");
		        }
		        
		    } catch (SQLException e) {
		        System.out.println("Error: " + e.getMessage());
		    }
		}

	
	
	    public static void viewUpcomingMovies()
	    {
	        String sql = "select * from movies where release_date > NOW()";
	        try (Connection con = DatabaseConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery())
	        {
//	            while (rs.next())
//	            {
//	                System.out.println("Movie:    |"+ rs.getString("name") + ", Release Date: " + rs.getDate("release_date"));
//	            }
	        	String cloumn="Movie Name        |Release Date  |";
				System.out.println(cloumn);
				while(rs.next()) {
					System.out.println(rs.getString("name")+"          |"+rs.getDate("release_date")+"     |");
	        } 
	        }
	        catch (SQLException e)
	        {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	
	   
	    public static void cancelTicket(int bookingId) 
	    {
	        String sql = "delete from bookings where booking_id = ?";
	        try (Connection con = DatabaseConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql))
	        {
	            ps.setInt(1, bookingId);
	            ps.executeUpdate();
	            System.out.println("Ticket canceled successfully");
	        }
	        catch (SQLException e)
	        {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	
	 
	    public static void changeCredentials(String userName, String oldPassword, String newPassword) {
	        String validateSql = "SELECT username FROM users WHERE username = ? AND password = ?";
	        String updateSql = "UPDATE users SET password = ? WHERE username = ?";

	        try (Connection con = DatabaseConnection.getConnection();
	             PreparedStatement validatePs = con.prepareStatement(validateSql)) {

	            validatePs.setString(1, userName);
	            validatePs.setString(2, oldPassword);
	            ResultSet rs = validatePs.executeQuery();

	            if (rs.next()) {
	                try (PreparedStatement updatePs = con.prepareStatement(updateSql)) {
	                    updatePs.setString(1, newPassword);
	                    updatePs.setString(2, userName);
	                    updatePs.executeUpdate();
	                    System.out.println("Password updated successfully");
	                }
	            } else {
	                System.out.println("Invalid old password please try again.");
	            }

	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	}