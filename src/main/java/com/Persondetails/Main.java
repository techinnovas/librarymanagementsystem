package com.Persondetails;
import java.util.*;
import com.Bookdetails.Bookitems;
import com.Enumeration.AccountStatus;
import com.ExceptionalHandling.InvalidChoiceException;
/**
 * To create the Main
 * @author JayasuriyaPJ(Expleo)
 * @since 18 FEB 2024
 */
public class Main {
    public static void main(String[] args) throws InvalidChoiceException {
        System.out.println("Study makes you steady");
        System.out.println("------------------------\n\nWelcome to our Library\n\n------------------------");
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            int choice = displayMainMenu(sc);
            switch (choice) {
                case 1:
                    patronOption(sc);
                    break;
                case 2:
                    adminOption(sc);
                    break;
                case 3:
                    System.out.println("Exiting from Library page");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    // Method for displaying main menu
    public static int displayMainMenu(Scanner sc) {
        int choice;
        while (true) {
            try {
                System.out.println("\n\033[1;35mHii buddy! Who is this?\033[0m");
                System.out.println("\033[1;36m1. Patron\033[0m");
                System.out.println("\033[1;36m2. Admin\033[0m");
                System.out.println("\033[1;36m3. Exit\033[0m");
                System.out.println("\n\033[1;33mEnter your choice: \033[0m");
                choice = sc.nextInt();
                break; // If input is valid, exit the loop
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer choice.");
                sc.nextLine(); // Consume the invalid input
            }
        }
        return choice;
    }
    public static void patronOption(Scanner sc) throws InvalidChoiceException {//Customised Exceptional Handling
        boolean patronrun = true;
        Bookitems bookitm = new Bookitems();
        while (patronrun) {
            try {
                System.out.println("1.Guest\n2.LoginUser\n3.Register New patron\n4.back");
                System.out.println("Enter the option");
                int choicepatron = sc.nextInt();
                switch (choicepatron) {
                    case 1:
                        System.out.println("You are acting as a Guest User!! You could see the book items");
                        bookitm.displayBooks();
                        System.out.println("---------------------------\n");
                        Patron patronsrc = new Patron(null, null, null, null);// object for patron class
                        System.out.println("\n You can search for books also\n");
                        // Pass the patron ID when invoking the searchbookby() method
                        patronsrc.searchbookby();

                        break;
                    case 2:
                        // Modify this section to get the patron ID after login
                        Patron patron1 = new Patron(null, null, null, null);// object for patron class
                        patron1.loginUser();
                        // Retrieve the patron ID after login
                        String patronId = patron1.getId();
                        if (patronId != null) {
                            patron1.setId(patronId); 
                        }
                        break;
                    case 3:
                        Patron patron = new Patron(null, null, null, null);
                        patron.newPatronReg();
                        // Retrieve the patron ID after registration
                        String newPatronId = patron.getId();
                        if (newPatronId != null) {
                            patron.setId(newPatronId); // Set the patron ID in the Patron object
                            // Pass the patron ID when invoking relevant methods
                            // For example: patron.searchbookby();
                        }
                        break;
                    case 4:
                        System.out.println("Back....");
                        patronrun = false;
                        break;
                    default:
                       throw new InvalidChoiceException("Invalid Option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Select the option only in numbers");
                
            }
        }
    }
    public static void adminOption(Scanner sc) throws InvalidChoiceException {
        System.out.println("Are you?\n1.Librarian\n2.Admin");
        try {
            int adminloginchoice = sc.nextInt();
            boolean run = true;
            while (run) {
                switch (adminloginchoice) {
                    case 1:
                        Librarian librarian = new Librarian();
                        librarian.librarianlogin();
                        break;
                    case 2:
                        Admin admin = new Admin();
                        admin.adminlogin();
                        break;
                    case 3:
                        System.out.println("Back...");
                        run = false;
                        break;
                    default:
                        System.out.println("Invalid Choice");
                }
            }
        } 
        catch (InputMismatchException e) {
            System.out.println("You can provide the choice in number");
        }
    }
}





