package com.movies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuth {
    public static void registerUser(String username, String password, String role) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.executeUpdate();
            System.out.println("User registered successfully");
        } 
        catch (SQLException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

  
    public static String loginUser(String username, String password) {
        String sql = "select role from users where username = ? AND password = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getString("role");
            } 
            else {
                System.out.println("Invalid credentials.");
            }
        } 
        catch (SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}