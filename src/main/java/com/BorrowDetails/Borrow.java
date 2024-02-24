package com.BorrowDetails;
import com.Bookdetails.*;
import com.Database.DatabaseConnection;
import com.Enumeration.AccountStatus;
import com.Enumeration.AvailabilityStatus;
import com.ExceptionalHandling.InvalidChoiceException;
import com.Persondetails.Patron;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * To create the Borrow class 
 * @author JayasuriyaPJ(Expleo)
 * @since 21 FEB 2024
 */
public class Borrow {
	public void borrowBook(Book book, Patron patron, LocalDate returnDate) {
	    // Check if the book is available for borrowing
	    if (bookIsAvailable(book)) {
	        // Check if the patron's account is active
	        if (patronIsActive(patron)) {
	            try (Connection connection = DatabaseConnection.getConnection()) {
	                String recordBorrowingQuery = "INSERT INTO LIBRARYMANAGEMENTSYSTEMDB.BorrowingHistory(BookId, PatronId, BorrowingDate, DueDate, Returndate) VALUES (?, ?, ?, ?, ?)";
	                try (PreparedStatement recordBorrowingStatement = connection.prepareStatement(recordBorrowingQuery)) {
	                    recordBorrowingStatement.setInt(1, book.getBookId());
	                    recordBorrowingStatement.setString(2, patron.getId()); // Set Patron ID
	                    recordBorrowingStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
	                    recordBorrowingStatement.setDate(4, java.sql.Date.valueOf(returnDate));
	                    recordBorrowingStatement.setNull(5, Types.DATE); //Not yet returned               
	                    // Execute the update
	                    int rowsAffected = recordBorrowingStatement.executeUpdate();
	                    // Check if the update was successful
	                    if (rowsAffected > 0) {
	                        System.out.println("Book borrowed successfully");
	                        String updateAvailabilityQuery = "UPDATE LIBRARYMANAGEMENTSYSTEMDB.Books SET availabilitystatus = ? WHERE bookid = ?";
	                        try (PreparedStatement updateAvailabilityStatement = connection.prepareStatement(updateAvailabilityQuery)) {
	                            updateAvailabilityStatement.setString(1, "Notavailable");
	                            updateAvailabilityStatement.setInt(2, book.getBookId());
	                            updateAvailabilityStatement.executeUpdate();
	                        }
	                    } 
	                    else {
	                        System.out.println("Failed to borrow the book. Please try again.");
	                    }
	                }
	            } 
	            catch (SQLException e) {
	                System.out.println("Error borrowing book");
	            }
	        } 
	        else {
	            System.out.println("Unable to borrow book. Patron's account is not active.");
	        }
	    } 
	    else {
	        System.out.println("Unable to borrow book. Book is not available.");
	    }
	}
	//Customised Exceptional Handling
	public void returnBook(Book book, Patron patron) throws SQLException, InvalidChoiceException {
        Scanner sc = new Scanner(System.in);
        try (Connection connection = DatabaseConnection.getConnection()) {
            Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"); // Regex pattern for YYYY-MM-DD format
            String returnDateInput;
            LocalDate returnDate = null;
            while (true) {
                System.out.println("Enter the return date (YYYY-MM-DD):");
                returnDateInput = sc.nextLine();
                Matcher matcher = pattern.matcher(returnDateInput);
                if (matcher.matches()) {
                    returnDate = LocalDate.parse(returnDateInput);
                    break;
                } else {
                    throw new InvalidChoiceException("Invalid date format. Please enter date in YYYY-MM-DD format.");
                }
            }
            String updateAvailabilityQuery = "UPDATE LIBRARYMANAGEMENTSYSTEMDB.Borrowinghistory SET Returndate=? WHERE bookid=?";
            try (PreparedStatement updateAvailabilityStatement = connection.prepareStatement(updateAvailabilityQuery)) {
                updateAvailabilityStatement.setDate(1, java.sql.Date.valueOf(returnDate));
                updateAvailabilityStatement.setInt(2, book.getBookId());
                int rowsUpdated = updateAvailabilityStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Book returned successfully");
                    // Update book availability status to Available
                    String updateAvailabilityQuery1 = "UPDATE LIBRARYMANAGEMENTSYSTEMDB.Books SET availabilitystatus = ? WHERE Bookid = ?";
                    try (PreparedStatement updateAvailabilityStatement1 = connection.prepareStatement(updateAvailabilityQuery1)) {
                        updateAvailabilityStatement1.setString(1, "Available");
                        updateAvailabilityStatement1.setInt(2, book.getBookId());
                        updateAvailabilityStatement1.executeUpdate();
                    }
                } else {
                    System.out.println("Failed to return the book. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error returning book ");
        }
	}
    // Method to check if the book is available
    private boolean bookIsAvailable(Book book) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String checkAvailabilityQuery = "SELECT availabilitystatus FROM LIBRARYMANAGEMENTSYSTEMDB.Books WHERE bookid=?";
            try (PreparedStatement checkAvailabilityStatement = connection.prepareStatement(checkAvailabilityQuery)) {
                checkAvailabilityStatement.setInt(1, book.getBookId());
                try (ResultSet availabilityResult = checkAvailabilityStatement.executeQuery()) {
                    if (availabilityResult.next()) {
                        String availabilityStatus = availabilityResult.getString("availabilitystatus");
                        return availabilityStatus.equalsIgnoreCase("available");
                    }
                }
            }
        } 
        catch (SQLException e) {
            System.out.println("Error checking book availability: ");
        }
        return false;
    }
    // Method to check if the patron's account is active
    public boolean patronIsActive(Patron patron) {
        String accountStatus = "Active";
        return accountStatus.equalsIgnoreCase("Active");
    } 


}
