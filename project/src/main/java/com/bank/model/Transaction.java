package com.bank.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable, Comparable<Transaction> {
    private String transactionId;
    private String accountNumber;
    private String transactionType;
    private double amount;
    private double balanceAfter;
    private Date transactionDate;
    private String description;
    
    public Transaction() {
        this.transactionDate = new Date();
        this.transactionId = generateTransactionId();
    }
    
    public Transaction(String accountNumber, String transactionType, double amount, 
                      double balanceAfter, String description) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
        this.transactionDate = new Date();
        this.transactionId = generateTransactionId();
    }
    
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
    
    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public double getBalanceAfter() {
        return balanceAfter;
    }
    
    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    
    public Date getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(transactionDate);
    }
    
    @Override
    public int compareTo(Transaction other) {
        return this.transactionDate.compareTo(other.transactionDate);
    }
    
    @Override
    public String toString() {
        return transactionId + "," + accountNumber + "," + transactionType + "," + 
               amount + "," + balanceAfter + "," + transactionDate.getTime() + "," + 
               (description != null ? description : "");
    }
    
    public static Transaction fromString(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        
        String[] parts = data.split(",", -1); // -1 to include empty strings
        if (parts.length >= 6) {
            try {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(parts[0].trim());
                transaction.setAccountNumber(parts[1].trim());
                transaction.setTransactionType(parts[2].trim());
                transaction.setAmount(Double.parseDouble(parts[3].trim()));
                transaction.setBalanceAfter(Double.parseDouble(parts[4].trim()));
                transaction.setTransactionDate(new Date(Long.parseLong(parts[5].trim())));
                if (parts.length > 6 && !parts[6].trim().isEmpty()) {
                    transaction.setDescription(parts[6].trim());
                }
                return transaction;
            } catch (NumberFormatException e) {
                System.err.println("Error parsing transaction data: " + data + " - " + e.getMessage());
                return null;
            }
        }
        return null;
    }
}