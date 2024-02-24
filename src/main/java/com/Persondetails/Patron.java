package com.Persondetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import com.Bookdetails.Book;
import com.Bookdetails.Bookitems;
import com.BorrowDetails.Borrow;
import com.Database.DatabaseConnection;
import com.Enumeration.AccountStatus;
import com.Enumeration.AccountType;
import com.ExceptionalHandling.InvalidChoiceException;

public class Patron extends Account {
    Scanner sc = new Scanner(System.in);
    Bookitems bk=new Bookitems();
    public Patron(String id, String password, String activestatus, String accountType) {
        super(id, password, activestatus, accountType);
    }
    public void newPatronReg() {
        System.out.println("Enter Name:");
        String name = sc.nextLine();
        System.out.println("Enter Address:");
        String address = sc.nextLine();
        String email = getValidEmail(sc);
        String password = getValidPassword(sc);
        String contactNumber = getValidContactNumber(sc);
        LocalDate memberSince = LocalDate.now();
        LocalDate expireDate = memberSince.plus(Period.ofYears(5));
        String defaultStatus = "Active";
        if (LocalDate.now().isAfter(memberSince.plus(Period.ofYears(5)))) {
            defaultStatus = "Inactive";
        }
        String accountQuery = "INSERT INTO LIBRARYMANAGEMENTSYSTEMDB.Account (email, password, account_type, account_status) VALUES (?, ?, ?, ?)";
        String patronQuery = "INSERT INTO LIBRARYMANAGEMENTSYSTEMDB.Patrons(id, name, address, email, password, contact_number, member_since, expire_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement accountStatement = connection.prepareStatement(accountQuery);
             PreparedStatement patronStatement = connection.prepareStatement(patronQuery)) {
            // Setting values for the Account table
            accountStatement.setString(1, email);
            accountStatement.setString(2, password);
            accountStatement.setString(3, "patron");
            accountStatement.setString(4, defaultStatus);
            int rowsAffected = accountStatement.executeUpdate();
            // Retrieving the generated ID from Account table
            int accountId = -1;
            if (rowsAffected > 0) {
                // Execute query to get the maximum ID from Account table
                try (Statement maxIdStatement = connection.createStatement();
                     ResultSet maxIdResultSet = maxIdStatement.executeQuery("SELECT MAX(id) FROM LIBRARYMANAGEMENTSYSTEMDB.Account")) {
                    if (maxIdResultSet.next()) {
                        accountId = maxIdResultSet.getInt(1);
                    }
                }
            }
            // Set values for the Patrons table
            patronStatement.setInt(1, accountId);
            patronStatement.setString(2, name);
            patronStatement.setString(3, address);
            patronStatement.setString(4, email);
            patronStatement.setString(5, password);
            patronStatement.setString(6, contactNumber);
            patronStatement.setDate(7, java.sql.Date.valueOf(memberSince));
            patronStatement.setDate(8, java.sql.Date.valueOf(expireDate));
            rowsAffected = patronStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("--- Patron registered successfully ---");
                System.out.println("\n\n--- Could you please login with the registered email id and password ---");
                	
            } 
            else {
                System.out.println("--- Failed to register patron. Please try again ---");
            }
        } 
        catch (SQLException e) {
            System.out.println("Error occurred while registering patron ");
        }
		
        }
	private String getValidContactNumber(Scanner sc) {
        System.out.println("---Enter valid Phone number---");
        while (true) {
            System.out.println("Enter Contact Number:");
            String contactNumber = sc.nextLine();
            if (isValidContactNumber(contactNumber)) {
                return contactNumber;
            } else {
                System.out.println("Invalid Contact Number. Please enter a 10-digit number starting with a digit greater than 5");
            }
        }
    }
	private String getValidEmail(Scanner sc) {
	    while (true) {
	        System.out.println("Enter Email:");
	        String email = sc.nextLine();
	        if (isValidEmail(email) && !emailExistsInDatabase(email)) {
	            return email;
	        } else {
	            System.out.println("Invalid Email or Email already exists. Please enter a valid email address.");
	        }
	    }
	}
    private String getValidPassword(Scanner sc) {
        System.out.println("Password should be at least 8 characters and include lowercase, uppercase, digit, and special character.");
        while (true) {
            System.out.println("Enter Password:");
            String password = sc.nextLine();
            if (isValidPassword(password)) {
                return password;
            } else {
                System.out.println("Invalid Password. Password should be at least 8 characters and include lowercase, uppercase, digit, and special character");
            }
        }
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean emailExistsInDatabase(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.getConnection();
            if (connection != null) {
                String query = "SELECT COUNT(*) FROM LIBRARYMANAGEMENTSYSTEMDB.Account WHERE email = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, email);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while checking email existence in the database.");
        } finally {
            // Close resources in reverse order of creation to avoid resource leaks
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("Error occurred while closing result set.");
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Error occurred while closing statement.");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error occurred while closing connection.");
                }
            }
        }
        return false;
    }
    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
    private boolean isValidContactNumber(String contactNumber) {
        return contactNumber.matches("[6-9]\\d{9}");
    }
    //Method for login User
    public void loginUser() throws InvalidChoiceException {
        System.out.println("Enter Email:");
        String email = sc.next();
        System.out.println("Enter Password:");
        String password = sc.next(); 
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String loginQuery = "SELECT name FROM LIBRARYMANAGEMENTSYSTEMDB.Patrons WHERE email = ? AND password = ?";
                try (PreparedStatement loginStatement = connection.prepareStatement(loginQuery)) {
                    loginStatement.setString(1, email);
                    loginStatement.setString(2, password);
                    try (ResultSet resultSet = loginStatement.executeQuery()) {
                        if (resultSet.next()) {
                            String patronName = resultSet.getString("name");
                            System.out.println("\033[1;33mWelcome, " + patronName + "!\033[0m");
                            patronMenu();
                            return; 
                        } 
                        else {
                            System.out.println("Invalid email or password. Please try again.");
                        }
                    }
                }
            } 
            else {
                System.out.println("Unnable to login");
            }
        } catch (SQLException e) {
            System.out.println("Error occur in login ");
        }
    }  
    public void patronMenu() throws InvalidChoiceException {
        boolean running = true;
        while (running) {
        	System.out.println("\u001B[34m\nPatron Menu:"); 
        	System.out.println("\u001B[31m1. Search for Books"); 
        	System.out.println("\u001B[31m2. Borrow a Book");
        	System.out.println("\u001B[31m3. Return a Book");
        	System.out.println("\u001B[31m4. Exit");
        	System.out.println("\u001B[31mSelect an option:");
            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                    	searchbookby();
                    	break;
                 
                    case 2:
                    	System.out.println("Enter the ID of the book you want to borrow:");
                    	int bookIdToBorrow = sc.nextInt();
                    	Book book = bk.getBookById(bookIdToBorrow);
                    	if(book != null) {
                    	    Borrow borrow = new Borrow();
                    	    borrow.borrowBook(book, this, LocalDate.now().plusDays(20)); 
                    	} else {
                    	    System.out.println("Book not found.");
                    	}
                    	break;
                    case 3:
                        System.out.println("Return a Book");
                        System.out.println("Enter the id of the book you want to return:");
                        int bookToReturn = sc.nextInt();
                        Book bookReturn = bk.getBookById(bookToReturn);
                        if (bookReturn != null) {
                            Borrow borrow1 = new Borrow();
                            try {
								borrow1.returnBook(bookReturn, this);
							} catch (SQLException e) {
								System.out.println("It cause the error in return the book");
							}
                        } 
                        else {
                            System.out.println("Book not found.");
                        }
                        break;                        
                        
                    case 4:
                        System.out.println("Exiting Patron Menu...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Please select again.");
                }
            } 
            catch (InputMismatchException e) {
                System.out.println("Choose option only in numbers");
                sc.next(); // Consume the invalid input
            }
        }
        }
    //method for searchbookby menu
       public void searchbookby() {
    	  
               	 boolean running1 = true;
                   while (running1) {
                	   System.out.println("\u001B[35m1. Search by Book Id : "); 
                	   System.out.println("\u001B[35m2. Search by Book Name : ");
                	   System.out.println("\u001B[35m3. Search by Author Name : ");
                	   System.out.println("\u001B[35mEnter your choice : ");
                       try {
                           int searchChoice = sc.nextInt();
                           switch (searchChoice) {
                               case 1:
                                   running1 = false;
                                   System.out.println("Enter the Book Id:");
                                   int bookId = sc.nextInt();
                                   
                                   bk.searchbybookid(bookId);
                                   break;
                               case 2:
                                   System.out.println("Enter the Book Name:");
                                   String bookName = sc.nextLine();
                                   // Call method to search by Book Name
                                   bk.searchbybookname(bookName);
                                   boolean running = false;
                                   break;
                               case 3:
                                   System.out.println("Enter the Author Name:");
                                   String authorName = sc.nextLine();
                                   running1 = false;
                                   // Call method to search by Author Name
                                   bk.searchbyuathorname(authorName);
                                   break;
                               case 4:
                                   System.out.println("Back....");
                                   running1 = false;
                                   break;
                               default:
                                   System.out.println("Invalid choice! Please select again.");
                           }
                       } 
                       catch (InputMismatchException e) {
                           System.out.println("Enter the option only in numbers ");
                       }
                   }
       		}
    }
    

//root for  maven Project
// Arch type - template structure for build the project
//dependency can identify-gav(group artifact version)
//maven build 
//pulgins can have any number of goals
//.m2 file local repository
//local and remote repository
// at the first time creating the jar file for our project it can comes from central repository at next time it will come from .m2 file
