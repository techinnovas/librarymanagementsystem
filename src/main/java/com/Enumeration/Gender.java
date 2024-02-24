package com.Enumeration;

import java.util.Scanner;

public enum Gender {
 Male,
 Female,
 Transgender;
public String toString() {
    return this.name().toLowerCase();
	}

public Gender SelectGeneder() {
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter the Choice \n1.Male\n2.Female\n3.Transgender");
	int choice=sc.nextInt();
	switch(choice) {
	case 1:
		return Male;
	
	case 2:
		return Female;
	
	case 3:
		return Transgender;
	
	}
	return null;
		
}
}
