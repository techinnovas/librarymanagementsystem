package com.Bookdetails;
import com.Enumeration.AvailabilityStatus;
import com.Enumeration.Row;
/**
 * To create the Librarian class 
 * @author JayasuriyaPJ(Expleo)
 * @since 19 FEB 2024
 **/
public class Book{
	int BookId;
	String Bookname;
	String Authorname;
	String ISBN;
	String availabilitystatus;
	String rack;
	public Book(int BookId,String Bookname,String Authorname,String ISBN, String availabilityStatus2,String rack){
		this.BookId=BookId;
		this.Bookname=Bookname;
		this.Authorname=Authorname;
		this.ISBN=ISBN;	
		this.availabilitystatus=availabilityStatus2;
		this.rack=rack;
	}
	public int getBookId() {
		return BookId;
	}
	public String getBookname() {
		return Bookname;
	}
	public String getAuthorname() {
		return Authorname;
	}
	public String getISBN() {
		return ISBN;
	}
	public String getAvailabilityStatus() {
		return availabilitystatus;
	}
	public String getRow() {
		return rack;
	}
	void setBookId(int BookId) {
		this.BookId=BookId;
	}
	void setBookname(String Bookname) {
		this.Bookname=Bookname;
	}
	void setAuthorname(String Authorname){
			this.Authorname=Authorname;
	}
	public  void setAvailabilityStatus(String availabilitystatus) {
		this.availabilitystatus=availabilitystatus;
	}
	public void setRow(String rack) {
		this.rack=rack;
	}	
	@Override
    public String toString() {
          String ANSI_RESET = "\u001B[0m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_YELLOW = "\u001B[33m";
        
        return ANSI_CYAN + "\n----------------------\n" +
               "Book ID: " + BookId +
               ANSI_YELLOW + "\n Name: " + Bookname +
               "\n Author: " + Authorname +
               "\n ISBN: " + ISBN +
               "\n Availability: " + availabilitystatus +
               "\n Rack: " + rack +
               "\n\n" + ANSI_RESET;
    }
}