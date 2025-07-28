package com.bank.model;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable, Comparable<Account> {
    private String accountNumber;
    private String customerName;
    private String password;
    private String accountType;
    private double balance;
    private Date createdDate;
    
    public Account() {
        this.createdDate = new Date();
    }
    
    public Account(String accountNumber, String customerName, String password, 
                   String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.password = password;
        this.accountType = accountType;
        this.balance = balance;
        this.createdDate = new Date();
    }
    
    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAccountType() {
        return accountType;
    }
    
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }
    
    @Override
    public String toString() {
        return accountNumber + "," + customerName + "," + password + "," + 
               accountType + "," + balance + "," + createdDate.getTime();
    }
    
    public static Account fromString(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        
        String[] parts = data.split(",", -1); // -1 to include empty strings
        if (parts.length >= 5) {
            try {
                Account account = new Account(parts[0].trim(), parts[1].trim(), 
                                            parts[2].trim(), parts[3].trim(), 
                                            Double.parseDouble(parts[4].trim()));
                if (parts.length > 5 && !parts[5].trim().isEmpty()) {
                    account.setCreatedDate(new Date(Long.parseLong(parts[5].trim())));
                }
                return account;
            } catch (NumberFormatException e) {
                System.err.println("Error parsing account data: " + data + " - " + e.getMessage());
                return null;
            }
        }
        return null;
    }
}