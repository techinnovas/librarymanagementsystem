package com.Persondetails;
import com.Bookdetails.*;
import com.ExceptionalHandling.InvalidChoiceException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import com.Bookdetails.*;

/**
 * To create the Librarian class 
 * @author JayasuriyaPJ(Expleo)
 * @since 18 FEB 2024
 */
public class Librarian {
	static void librarianlogin() {
	    Scanner sc = new Scanner(System.in);
	    boolean running = true;
	    while (running) {
	        try {
	            System.out.println("Enter ID:");
	            String id = sc.next();
	            System.out.println("Enter Password:");
	            String password = sc.next();
	            System.out.println("***********************************************");
	            System.out.println("\n\033[1;35mAdmin Menu:\033[0m");
	            System.out.println("\033[1;36m1. Add Book\033[0m");
	            System.out.println("\033[1;36m2. Delete Book\033[0m");
	            System.out.println("\033[1;36m3. Search for book\033[0m");
	            System.out.println("\033[1;36m4. Back\033[0m");
	            System.out.println("\n\033[1;33mSelect an option: \033[0m\n");
	            int adminchoice = sc.nextInt();
	            Admin admin = new Admin();
	            switch (adminchoice) {
	                case 1:
	                    admin.addBooks();
	                    break;
	                case 2:
	                    admin.deleteBook();
	                    break;
	                case 3:
	                    System.out.println("Search for books");
	                    break;
	                case 4:
	                    System.out.println("Exiting Admin Menu...");
	                    Main.displayMainMenu(sc);
	                    running = false;
	                    break;
	                default:
	                    throw new InvalidChoiceException("Invalid choice! Please select again.");
	            }
	        } 
	        catch (InvalidChoiceException e) {
	            System.out.println(e.getMessage());
	        } 
	        catch (InputMismatchException e) {
	            System.out.println("Invalid input! Please enter a valid number.");
	            sc.nextLine();
	        }
	    }
	}

}
