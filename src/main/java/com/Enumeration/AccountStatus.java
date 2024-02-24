package com.Enumeration;
public enum AccountStatus {
	Active,
	Inactive,
	Blocked;
	 @Override
	    public String toString() {
		 
	        return this.name().toString();
	    }
}
