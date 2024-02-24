package com.Persondetails;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.Bookdetails.Bookitems;
import com.BorrowDetails.Borrow;
import com.Database.DatabaseConnection;
import com.ExceptionalHandling.InvalidChoiceException;
public class Admin {
     Scanner sc = new Scanner(System.in);
   //Method for Admin login
     public void adminlogin() throws InvalidChoiceException {
    	    boolean loggedIn = false;
    	    while (!loggedIn) {
    	        System.out.println("Enter the Admin Id:");
    	        int id = sc.nextInt();
    	        System.out.println("Enter the password:");
    	        String password = sc.next();
    	        try (Connection connection = DatabaseConnection.getConnection()) {
    	            String queryadminlogin = "SELECT id,password FROM LIBRARYMANAGEMENTSYSTEMDB.accounts WHERE id=? AND password=?";
    	            try (PreparedStatement pst = connection.prepareStatement(queryadminlogin)) {
    	                pst.setInt(1, id);
    	                pst.setString(2, password);
    	                try (ResultSet rs = pst.executeQuery()) {
    	                    if (rs.next()) {
    	                        System.out.println("Login successful!");
    	                        loggedIn = true;
    	                        adminMenu();
    	                    } 
    	                    else {
    	                        System.out.println("Login failed. Invalid Admin Id or password. Please try again.");
    	                    }
    	                }
    	            }
    	        } 
    	        catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }
    	}
     //It is the Admin Menu
     public void adminMenu() throws InvalidChoiceException {
    	    boolean running = true;
    	    while (running) {
    	        displayAdminMenu();
    	        try {
    	            int adminChoice = sc.nextInt();
    	            handleAdminChoice(adminChoice);
    	        } catch (InputMismatchException e) {
    	            System.out.println("Invalid input! Please enter a number.");
    	            sc.nextLine(); 
    	        }
    	    }
    	}

    	private void displayAdminMenu() {
    	    System.out.println("\nAdmin Menu:");
    	    System.out.println("1. Add Book");
    	    System.out.println("2. Delete Book");
    	    System.out.println("3. Add Member");
    	    System.out.println("4. Block Member");
    	    System.out.println("5. Search for Book");
    	    System.out.println("6. Exit");
    	    System.out.println("Select an option:");
    	}

    	public void handleAdminChoice(int adminChoice)throws InvalidChoiceException {
    	    switch (adminChoice) {
    	        case 1:
    	            addBooks();
    	            break;
    	        case 2:
    	            deleteBook();
    	            break;
    	        case 3:
    	            addMember();
    	            break;
    	        case 4:
    	            blockMember();
    	            break;
    	        case 5:
    	            searchForBook();
    	            break;
    	        case 6:
    	        	displayAdminMenu();
    	            break;
    	        default:
    	        	//Customised Exception
    	           throw new InvalidChoiceException ("Invalid choice! Please select again.");
    	    }
    	    displayAdminMenu();
    	}
   
	public void searchForBook() {
		try {
            boolean runs = true;
            while (runs) {
                System.out.println("1.Search by Id");
                System.out.println("2. Search by Book Name");
                System.out.println("3. Search by Author Name");
                System.out.println("4. Back to Admin Menu");
                System.out.println("Enter your choice:");
                int choiceforsearch = sc.nextInt();
                sc.nextLine();
                Bookitems bk = new Bookitems();
                switch (choiceforsearch) {
                    case 1:
                        System.out.println("Enter the Book Id:");
                        int bookId = sc.nextInt();
                        // Call method to search by Book Id
                        bk.searchbybookid(bookId);
                        break;
                    case 2:
                        System.out.println("Enter the Book Name:");
                        String bookName = sc.nextLine();
                        // Call method to search by Book Name
                        bk.searchbybookname(bookName);
                        break;
                    case 3:
                        System.out.println("Enter the Author Name:");
                        String authorName = sc.nextLine();
                        // Call method to search by Author Name
                        bk.searchbyuathorname(authorName);
                        break;
                    case 4:
                    	runs = false;
                        break;
                        
                    default:
                        System.out.println("Invalid choice! Please select again.");
                }
                
            }
        } catch (InputMismatchException e) {
            System.out.println("Choose option only in numbers");
        }
			
		}
	/**
     public void adminMenu() {
    	    boolean running = true;
    	    while (running) {
    	        System.out.println("\nAdmin Menu:");
    	        System.out.println("1. Add Book");
    	        System.out.println("2. Delete Book");
    	        System.out.println("3. Add Member");
    	        System.out.println("4. Block Member");
    	        System.out.println("5. Search for Book");
    	        System.out.println("6. Exit");
    	        System.out.println("Select an option:");
    	        try {
    	            int adminChoice = sc.nextInt();
    	            
    	            switch (adminChoice) {
    	                case 1:
    	                    addBooks();   	                    
    	                    break;
    	                case 2:
    	                    deleteBook();
    	                    break;
    	                case 3:
    	                	Patron patrons=new Patron(null, null, null, null);
    	                	patrons.newPatronReg();
    	                    break;
    	                case 4:
    	                    blockMember();
    	                    break;
    	                case 5:
    	                    try {
    	                        boolean runs = true;
    	                        while (runs) {
    	                            System.out.println("1. Search by Id");
    	                            System.out.println("2. Search by Book Name");
    	                            System.out.println("3. Search by Author Name");
    	                            System.out.println("4. Back to Admin Menu");
    	                            System.out.println("Enter your choice:");
    	                            int choiceforsearch = sc.nextInt();
    	                            sc.nextLine();
    	                            Bookitems bk = new Bookitems();
    	                            switch (choiceforsearch) {
    	                                case 1:
    	                                    System.out.println("Enter the Book Id:");
    	                                    int bookId = sc.nextInt();
    	                                    // Call method to search by Book Id
    	                                    bk.searchbybookid(bookId);
    	                                    break;
    	                                case 2:
    	                                    System.out.println("Enter the Book Name:");
    	                                    String bookName = sc.nextLine();
    	                                    // Call method to search by Book Name
    	                                    bk.searchbybookname(bookName);
    	                                    break;
    	                                case 3:
    	                                    System.out.println("Enter the Author Name:");
    	                                    String authorName = sc.nextLine();
    	                                    // Call method to search by Author Name
    	                                    bk.searchbyuathorname(authorName);
    	                                    break;
    	                                case 4:
    	                                	runs = false;
    	                                    break;
    	                                    
    	                                default:
    	                                    System.out.println("Invalid choice! Please select again.");
    	                            }
    	                            
    	                        }
    	                    } catch (InputMismatchException e) {
    	                        System.out.println("Choose option only in numbers");
    	                    }
    	                    break;
    	                case 6:
    	                   
    	                    System.out.println("Exiting Admin Menu...");
    	                    Main.adminOption(sc);
    	                    running = false;
    	                    break;
    	                default:
    	                    System.out.println("Invalid choice! Please select again.");
    	            }
    	            running=false;
    	            
    	        } 
    	        catch (InputMismatchException e) {
    	            System.out.println("Invalid input! Please enter a number.");
    	            sc.nextLine(); 
    	        }
    	    }
    	}**/
 // Method for deleteBook from the Bookitems
    public void deleteBook() {
        System.out.println("Enter Book ID to delete:");
        int bookId = sc.nextInt();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM LIBRARYMANAGEMENTSYSTEMDB.books WHERE bookid = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, bookId);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book with ID " + bookId + " deleted successfully!");
            } else {
                System.out.println("No book found with ID " + bookId + ". Deletion failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
		//Adding the books to the booklists
        public void addBooks() throws InvalidChoiceException {
            System.out.println("Enter Book Details:\n");
            System.out.println("Book ID:");
            int bookId = sc.nextInt();
            sc.nextLine(); 
            System.out.println("Book Name:");
            String bookName = sc.nextLine();
            System.out.println("Author Name:");
            String authorName = sc.nextLine();
            System.out.println("ISBN:");
            String isbn = sc.nextLine();
            System.out.println("Enter the Rack: ");
            String rack=sc.nextLine();
            try(Connection connection = DatabaseConnection.getConnection()) {           
                String query = "INSERT INTO LIBRARYMANAGEMENTSYSTEMDB.books (bookId, bookname, Authorname, Isbn,  AvailabilityStatus,Rack) VALUES (?, ?, ?, ?,?,?)";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setInt(1, bookId);
                pst.setString(2, bookName);
                pst.setString(3, authorName);
                pst.setString(4, isbn);
                pst.setString(5, "Available");
                pst.setString(6, rack);
                pst.executeUpdate();
                System.out.println("Book added successfully!");
                Main.adminOption(sc);
            } catch (SQLException e) {
                e.printStackTrace();
            } 
        }
        //Blocking the Member
        private void blockMember() {
            System.out.println("Enter Email of the Member to Block:");
            String email = sc.nextLine();
            try (Connection connection = DatabaseConnection.getConnection()){
                String query = "UPDATE LIBRARYMANAGEMENTSYSTEMDB.Patrons SET status = ? WHERE email = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, "Blocked");
                    statement.setString(2, email);
                    int rowsAffected = statement.executeUpdate();// It will returns 
                    if (rowsAffected > 0) {
                        System.out.println("Member blocked successfully!");
                    } else {
                        System.out.println("Failed to block member. Member not found.");
                    }
                }
            } 
            catch (SQLException e) {
               System.out.println("Error Occurs");;
            }
        }
        //Method to add member to admin
        private void addMember() {
        	Patron patronreg= new Patron(null, null, null, null);
        	patronreg.newPatronReg();
            try (Connection connection = DatabaseConnection.getConnection()) {
                System.out.println("Enter Member Details:");
                System.out.println("Name:");
                String name = sc.nextLine();
                System.out.println("Email:");
                String email = sc.nextLine();
                System.out.println("Address:");
                String address = sc.nextLine();
                String query = "INSERT INTO Patron (name, email, address) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, name);
                    statement.setString(2, email);
                    statement.setString(3, address);                 
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Member added successfully!");
                    } 
                    else {
                        System.out.println("Failed to add member.");
                    }
                }
            } 
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: Failed to add member.");
            }    
        }
    }