package com.Bookdetails;
import com.BorrowDetails.Borrow;
import com.Database.DatabaseConnection;
import com.Enumeration.AvailabilityStatus;
import com.Enumeration.Row;
import com.SearchDetails.Search;
import java.sql.*;
import java.util.*;
import com.Enumeration.AvailabilityStatus;
/**
 * To create the Librarian class
 * @author JayasuriyaPJ(Expleo)
 * @since 19 FEB 2024
 */
@FunctionalInterface
interface Bookdisplayinterface{
	abstract void display();
}
class Booklist implements Bookdisplayinterface{
	public void display() {
		
	}	
}
public class Bookitems implements Search {
	Scanner sc= new Scanner(System.in);
    private HashMap<Integer, Book> books;
    public Bookitems() {
        books = new HashMap<>();
        fetchBooksFromDatabase(); // Fetch books from database upon initialization
    }
    public void fetchBooksFromDatabase() {
        String query = "SELECT * FROM LIBRARYMANAGEMENTSYSTEMDB.books";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int bookId = resultSet.getInt("BookId");
                String bookName = resultSet.getString("Bookname");
                String ISBN = resultSet.getString("ISBN");
                String Authorname = resultSet.getString("Authorname");
                String rack = resultSet.getString("rack");
                // Default availability status is Available
               String availabilityStatus="Available";
                Book book = new Book(bookId, bookName, Authorname, ISBN, availabilityStatus, rack);
                books.put(book.getBookId(), book);
            }
        } 
        catch (SQLException e) {
            System.out.println("SQL Exceptions");
        }
    }
    // Method to update availability status to NotAvailable if book is not found by ID
    public void searchbybookid(int BookId) {
        if (!books.containsKey(BookId)) {
            System.out.println("---The Book is not available---");
        } 
        else {
            System.out.println("\n\u001B[30m----Book available----\n");
        }
    }
    public void searchbybookname(String Bookname) {
        boolean found = false;
        for (Book book : books.values()) {
            if (book.getBookname().equalsIgnoreCase(Bookname)) {
                System.out.println("Book is found");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Book not found");
        }
    }   
    public void addBook(Book book) {
        // Check if the book already exists
        if (books.containsKey(book.getBookId())) {
            System.out.println("Book with ID " + book.getBookId() + " already exists.");
        } else {
            books.put(book.getBookId(), book);
            System.out.println("Book added successfully.");
        }
    }
    public void deleteBook(int BookId) {
        // Check if the book exists
        if (books.containsKey(BookId)) {
            books.remove(BookId);
            System.out.println("Book with ID "+BookId + " deleted successfully.");
        } else {
            System.out.println("Book with ID "+
        BookId + " not found.");
        }
    }
	@Override
	public void searchbyuathorname(String Authorname) {
		 boolean found = false;
	        for (Book book : books.values()) {
	            if (book.getAuthorname().equalsIgnoreCase(Authorname)) {
	                System.out.println("Book found");
	                found = true;
	                break;
	            }
	        }
	        if (!found) {
	            System.out.println("The book is not found");
	        }
	}
	public Book getBookById(int bookToReturn) {
	    return books.values().stream()
	            .filter(book -> book.getBookId() == bookToReturn)
	            .findFirst()
	            .orElse(null);
	}
	public void displayBooks() {
	    
	    Bookdisplayinterface bookDisplay = () -> {
	        System.out.println("Displaying books...");
	        for (Book book : books.values()) {
	            System.out.println(book); 
	        }
	    };
	    bookDisplay.display();
	}
}


