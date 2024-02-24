package com.NotificationDetails;
import com.PaymentStatus.*;
import com.Persondetails.*;
/**
 * To creating the Notification class
 * @author Jayasuriya(Expleo)
 * @since 23 Feb 2024
 */
public class Notification {
	public void sendNotificationConsole(double fineamount) {
		Person personname=new Person(null, null, null, null, null);
		System.out.println("Dear "+personname.name+"you are paid your fine amount!!");
	}
	public void sendNotificationDecline(double fineamount) {
		Person personname=new Person(null,null,null,null,null);
		System.out.println("Dear "+personname.name+"you are declined to pay amount");
	}
}
