package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.structure.MergeSort;
import com.bank.utils.FileHandler;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private AccountService accountService;
    
    public TransactionService() {
        this.accountService = new AccountService();
    }
    
    public boolean deposit(String accountNumber, double amount, String description) {
        Account account = accountService.getAccountByNumber(accountNumber);
        if (account == null || amount <= 0) {
            return false;
        }
        
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        
        // Update account
        if (accountService.updateAccount(account)) {
            // Record transaction
            Transaction transaction = new Transaction(accountNumber, "DEPOSIT", 
                amount, newBalance, description);
            return saveTransaction(transaction);
        }
        
        return false;
    }
    
    public boolean withdraw(String accountNumber, double amount, String description) {
        Account account = accountService.getAccountByNumber(accountNumber);
        if (account == null || amount <= 0 || account.getBalance() < amount) {
            return false;
        }
        
        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        
        // Update account
        if (accountService.updateAccount(account)) {
            // Record transaction
            Transaction transaction = new Transaction(accountNumber, "WITHDRAWAL", 
                amount, newBalance, description);
            return saveTransaction(transaction);
        }
        
        return false;
    }
    
    private boolean saveTransaction(Transaction transaction) {
        String fileName = "transactions_" + transaction.getAccountNumber() + ".txt";
        return FileHandler.writeToFile(fileName, true, transaction.toString() + "\n");
    }
    
    public List<Transaction> getTransactionHistory(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        String fileName = "transactions_" + accountNumber + ".txt";
        String[] transactionData = FileHandler.readFromFile(fileName);
        
        for (String line : transactionData) {
            Transaction transaction = Transaction.fromString(line);
            if (transaction != null) {
                transactions.add(transaction);
            }
        }
        
        return MergeSort.sortTransactionsByDate(transactions);
    }
    
    public List<Transaction> sortTransactionsByAmount(String accountNumber) {
        return MergeSort.sortTransactionsByAmount(getTransactionHistory(accountNumber));
    }
    
    public List<Transaction> sortTransactionsByDate(String accountNumber) {
        return MergeSort.sortTransactionsByDate(getTransactionHistory(accountNumber));
    }
    
    public double getAccountBalance(String accountNumber) {
        Account account = accountService.getAccountByNumber(accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }
}