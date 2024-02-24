package com.PaymentStatus;
import java.time.LocalDate;
import java.util.Scanner;
/**
 * To create Card class
 * @authorname JayasuriyaPJ(Expleo)
 * @since 22Feb2024
 */
public class Card extends Finetransaction {
    String transactionId;
    long cardNo;
    String payAck;
public Card(double fineAmount, String transactionId, long cardNo) {
        super(fineAmount);
        this.transactionId = transactionId;
        this.cardNo = cardNo;
        this.payAck = "pending"; 
    }
    // Method to handle payment acknowledgment using switch-case
    public void acknowledgePayment(String choice) {
        switch (choice.toLowerCase()) {
            case "yes":
                this.payAck = "yes";
                break;
            case "no":
                this.payAck = "no";
                break;
            default:
                System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
        }
    }
    // Getter and setter methods can be added here if needed
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public long getCardNo() {
        return cardNo;
    }
    public void setCardNo(long cardNo) {
        this.cardNo = cardNo;
    }
    public String getPayAck() {
        return payAck;
    }
   public void simulateInputHandling() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to acknowledge the payment? (yes/no)");
        String choice = scanner.nextLine();
        // Call the acknowledgePayment method with the user's choice
        acknowledgePayment(choice);
        // Display the payment acknowledgment
        System.out.println("Payment acknowledgment: " + getPayAck());
    }
}
