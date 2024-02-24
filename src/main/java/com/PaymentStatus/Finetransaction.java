package com.PaymentStatus;
import java.time.LocalDate;
import com.NotificationDetails.*;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Finetransaction extends Fine {
    public Finetransaction(double finerateperday) {
        super(finerateperday);
    }
    public void displayFineAmount(LocalDate dueDate, LocalDate currentDate) {
        double fineAmount = calculateFine(dueDate, currentDate);
        System.out.println("Fine Amount:" + fineAmount+"Rs");
        System.out.println("How are you going to pay\n");
        System.out.println("1. Debit Card\n2. Cash");
        Scanner sc = new Scanner(System.in);
        try {
            int payChoice = sc.nextInt();
            switch (payChoice) {
                case 1:
                    System.out.println("You have chosen debit card");
                    // Instantiate Card object
                    Card card =processDebitCardPayment(fineAmount);
                    card.simulateInputHandling();
                    break;
                case 2:
                    System.out.println("You have chosen Cash");    
                    System.out.println("You can pay the amount at library");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } 
        catch (InputMismatchException e) {
            System.out.println("Please select in numbers");
        }
    }
    private Card processDebitCardPayment(double fineAmount) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter transaction ID:");
        String transactionId = sc.nextLine();
        System.out.println("Enter card number:");
        long cardNo = sc.nextLong();
        return new Card(fineAmount, transactionId, cardNo);
    }
}

