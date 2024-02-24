package com.PaymentStatus;
import java.time.LocalDate;
import java.time.Period;;
public class Fine {
    private double finerateperday=5; // assuming you have this defined somewhere
    public Fine(double finerateperday) {
        this.finerateperday = finerateperday;
    }
    // Method to calculate the fine amount based on the number of days overdue
    public double calculateFine(LocalDate dueDate, LocalDate returnDate) {
    	 Period period = Period.between(dueDate, returnDate);
 	    int daysOverdue = period.getDays();
        if (daysOverdue > 0) {
            return daysOverdue * finerateperday;
        } 
        else {
            return 0;
        }
    }
}
