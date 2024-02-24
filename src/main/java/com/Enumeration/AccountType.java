package com.Enumeration;

public enum AccountType {
	Patron,
	Librarian,
	Admin;
	public String toString() {
		 
        return this.name().toString();
    }

}
