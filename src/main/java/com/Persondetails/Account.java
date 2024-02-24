package com.Persondetails;
import com.Enumeration.AccountStatus;
import com.Enumeration.AccountType;
/**
 * To create Account class
 * @author JayasuriyaPJ(Expleo)
 * @since 20 FEB 2024
 */
public class Account {
    protected String id;
    protected String password;
    protected String accountstatus;
    protected String accountType; 
    public Account(String id, String password, String accountstatus, String accountType) {
        this.id = id;
        this.password = password;
        this.accountstatus = accountstatus;
        this.accountType = accountType;
    }
    public String getId() {
        return id;
    }
    public String getAccountStatus() {
        return accountstatus;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setAccountStatus(String accountstatus) {
        this.accountstatus = accountstatus;
    }
    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType =accountType;
    }
}
