package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.structure.BinarySearchTree;
import com.bank.structure.MergeSort;
import com.bank.utils.FileHandler;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String ADMIN_FILE = "admin.txt";
    
    public AccountService() {
        initializeAdminAccount();
    }
    
    private void initializeAdminAccount() {
        if (!FileHandler.isFileExist(ADMIN_FILE)) {
            FileHandler.writeToFile(ADMIN_FILE, false, "admin,admin123\n");
        }
    }
    
    public boolean validateAdmin(String username, String password) {
        String[] adminData = FileHandler.readFromFile(ADMIN_FILE);
        for (String line : adminData) {
            String[] parts = line.split(",");
            if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    public Account validateCustomer(String accountNumber, String password) {
        String[] accountData = FileHandler.readFromFile(ACCOUNTS_FILE);
        for (String line : accountData) {
            Account account = Account.fromString(line);
            if (account != null && account.getAccountNumber().equals(accountNumber) 
                && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }
    
    public boolean createAccount(Account account) {
        // Check if account already exists
        if (getAccountByNumber(account.getAccountNumber()) != null) {
            return false;
        }
        
        return FileHandler.writeToFile(ACCOUNTS_FILE, true, account.toString() + "\n");
    }
    
    public Account getAccountByNumber(String accountNumber) {
        String[] accountData = FileHandler.readFromFile(ACCOUNTS_FILE);
        for (String line : accountData) {
            Account account = Account.fromString(line);
            if (account != null && account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
    
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String[] accountData = FileHandler.readFromFile(ACCOUNTS_FILE);
        
        for (String line : accountData) {
            Account account = Account.fromString(line);
            if (account != null) {
                accounts.add(account);
            }
        }
        return accounts;
    }
    
    public List<Account> searchAccountsByName(String name) {
        BinarySearchTree<Account> bst = new BinarySearchTree<>();
        List<Account> allAccounts = getAllAccounts();
        
        for (Account account : allAccounts) {
            bst.insert(account);
        }
        
        return bst.searchByName(name);
    }
    
    public List<Account> sortAccountsByName() {
        return MergeSort.sortAccountsByName(getAllAccounts());
    }
    
    public List<Account> sortAccountsByBalance() {
        return MergeSort.sortAccountsByBalance(getAllAccounts());
    }
    
    public boolean updateAccount(Account updatedAccount) {
        List<Account> accounts = getAllAccounts();
        boolean updated = false;
        
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(updatedAccount.getAccountNumber())) {
                accounts.set(i, updatedAccount);
                updated = true;
                break;
            }
        }
        
        if (updated) {
            // Rewrite the entire file
            FileHandler.clearFile(ACCOUNTS_FILE);
            for (Account account : accounts) {
                FileHandler.writeToFile(ACCOUNTS_FILE, true, account.toString() + "\n");
            }
        }
        
        return updated;
    }
    
    public boolean deleteAccount(String accountNumber) {
        List<Account> accounts = getAllAccounts();
        boolean removed = accounts.removeIf(account -> 
            account.getAccountNumber().equals(accountNumber));
        
        if (removed) {
            // Rewrite the entire file
            FileHandler.clearFile(ACCOUNTS_FILE);
            for (Account account : accounts) {
                FileHandler.writeToFile(ACCOUNTS_FILE, true, account.toString() + "\n");
            }
            
            // Delete transaction file
            FileHandler.deleteFile("transactions_" + accountNumber + ".txt");
        }
        
        return removed;
    }
    
    public String generateAccountNumber() {
        List<Account> accounts = getAllAccounts();
        int maxNumber = 1000;
        
        for (Account account : accounts) {
            try {
                String accountNum = account.getAccountNumber();
                if (accountNum != null && accountNum.length() > 1 && accountNum.startsWith("A")) {
                    String numPart = accountNum.substring(1);
                    int num = Integer.parseInt(numPart);
                    if (num >= maxNumber) {
                        maxNumber = num + 1;
                    }
                }
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                // Continue if parsing fails
                System.err.println("Error parsing account number: " + account.getAccountNumber());
            }
        }
        
        return "A" + maxNumber;
    }
}