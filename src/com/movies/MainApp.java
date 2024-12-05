package com.movies;

import java.sql.SQLException;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do 
        {
            System.out.println("Welcome to Movie Booking Application");
            System.out.println("1. Register\n2. Login\n3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice)
            {
                case 1:
                    System.out.println("Enter username, password, and role (user/admin):");
                    String username = scanner.next();
                    String password = scanner.next();
                    String role = scanner.next();
                    UserAuth.registerUser(username, password, role);
                    break;

                case 2:
                    System.out.println("Enter username and password:");
                    String loggedInRole = UserAuth.loginUser(scanner.next(), scanner.next());

                    if ("user".equalsIgnoreCase(loggedInRole)) {
                        handleUserOptions(scanner);
                    } 
                    else if ("admin".equalsIgnoreCase(loggedInRole))
                    {
                        handleAdminOptions(scanner);
                    }
                    else {
                        System.out.println("Invalid credentials or role Please try again");
                    }
                    break;

                case 3:
                    System.out.println("Exiting the application");
                    break;

                default:
                    System.out.println("Invalid choice please select a valid option");
            }
        }
        while (choice != 3);

        scanner.close();
    }

    private static void handleUserOptions(Scanner scanner) throws SQLException {
        int userChoice;
        do {
            System.out.println("Welcome User Select an option:");
            System.out.println("1. Book Ticket\n2. View Upcoming Movies\n3. Cancel Ticket\n4. Change Credentials\n5. Exit");
            System.out.print("Enter your choice: ");
            userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                	UserFunctions.showAllMovies();
                    System.out.println("Enter movie ID to book:");
                    int movieId = scanner.nextInt();
                    System.out.println("Enter your user ID:");
                    int userId = scanner.nextInt();
                    UserFunctions.bookTicket(userId, movieId);
                    break;

                case 2:
                    UserFunctions.viewUpcomingMovies();
                    break;

                case 3:
                	UserFunctions.showAllMovies();
                    System.out.println("Enter booking ID to cancel:");
                    int bookingId = scanner.nextInt();
                    UserFunctions.cancelTicket(bookingId);
                    break;

                case 4:
                    System.out.println("Enter your user name:");
                    String userName = scanner.next();
                    System.out.println("Enter old password:");
                    String oldPassword = scanner.next();
                    System.out.println("Enter new password:");
                    String newPassword = scanner.next();
                    UserFunctions.changeCredentials(userName,oldPassword, newPassword);
                    break;

                case 5:
                    System.out.println("Exiting user menu");
                    break;

                default:
                    System.out.println("Invalid option please try again");
            }
        } 
        while (userChoice != 5);
    }

    private static void handleAdminOptions(Scanner scanner) throws SQLException {
        int adminChoice;
        do {
            System.out.println("Welcome Admin Select an option:");
            System.out.println("1. Change Movies\n2. Approve Tickets\n3. Change Credentials\n4. Exit");
            System.out.print("Enter your choice: ");
            adminChoice = scanner.nextInt();

            switch (adminChoice) {
                case 1:
                	AdminFunctions.showAllMovies();
                    System.out.println("Enter movie ID to update:");
                    int movieId = scanner.nextInt();
                    System.out.println("Enter new name:");
                    String newName = scanner.next();
                    System.out.println("Enter new release date (YYYY-MM-DD):");
                    String newReleaseDate = scanner.next();
                    System.out.println("Enter available tickets:");
                    int newTickets = scanner.nextInt();
                    AdminFunctions.changeMovie(movieId, newName, newReleaseDate, newTickets);
                    break;

                case 2:
                	AdminFunctions.showUnapprovedTickets();
                    System.out.println("Enter booking ID to approve:");
                    int bookingId = scanner.nextInt();
                    AdminFunctions.approveTicket(bookingId);
                    break;

                case 3:
                    System.out.println("Enter your admin user Name:");
                    String adminId = scanner.next();
                    System.out.println("Enter old password:");
                    String oldAdminPassword = scanner.next();
                    System.out.println("Enter new password:");
                    String newAdminPassword = scanner.next();
                    UserFunctions.changeCredentials(adminId,oldAdminPassword, newAdminPassword);
                    break;

                case 4:
                    System.out.println("Exiting admin menu.");
                    break;

                default:
                    System.out.println("Invalid option please try again");
            }
        } while (adminChoice != 4);
    }
}