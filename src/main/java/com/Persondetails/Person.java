package com.Persondetails;
import com.Enumeration.Gender;
public class Person {
    public String name;
    private String email;
    private String phone_No;
    private Gender gender;
    private Account account; // Add Account attribute
    public Person(String name, String email, String phone_No, Gender gender, Account account) {
        this.name = name;
        this.email = email;
        this.phone_No = phone_No;
        this.gender = gender;
        this.account = account;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNo() {
        return phone_No;
    }
    public Gender getGender() {
        return gender;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNo(String phone_No) {
        this.phone_No = phone_No;
    }
    public void setGender(Gender gender) {
        this.gender = gender.SelectGeneder();
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
}
