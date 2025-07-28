package com.bank.structure;

import com.bank.model.Account;
import com.bank.model.Transaction;
import java.util.ArrayList;
import java.util.List;

public class MergeSort {
    
    public static List<Account> sortAccountsByName(List<Account> accounts) {
        if (accounts.size() <= 1) return accounts;
        
        List<Account> sorted = new ArrayList<>(accounts);
        mergeSortByName(sorted, 0, sorted.size() - 1);
        return sorted;
    }
    
    public static List<Account> sortAccountsByBalance(List<Account> accounts) {
        if (accounts.size() <= 1) return accounts;
        
        List<Account> sorted = new ArrayList<>(accounts);
        mergeSortByBalance(sorted, 0, sorted.size() - 1);
        return sorted;
    }
    
    public static List<Transaction> sortTransactionsByAmount(List<Transaction> transactions) {
        if (transactions.size() <= 1) return transactions;
        
        List<Transaction> sorted = new ArrayList<>(transactions);
        mergeSortTransactionsByAmount(sorted, 0, sorted.size() - 1);
        return sorted;
    }
    
    public static List<Transaction> sortTransactionsByDate(List<Transaction> transactions) {
        if (transactions.size() <= 1) return transactions;
        
        List<Transaction> sorted = new ArrayList<>(transactions);
        mergeSortTransactionsByDate(sorted, 0, sorted.size() - 1);
        return sorted;
    }
    
    // Sort accounts by name
    private static void mergeSortByName(List<Account> arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortByName(arr, left, mid);
            mergeSortByName(arr, mid + 1, right);
            mergeByName(arr, left, mid, right);
        }
    }
    
    private static void mergeByName(List<Account> arr, int left, int mid, int right) {
        List<Account> leftArr = new ArrayList<>(arr.subList(left, mid + 1));
        List<Account> rightArr = new ArrayList<>(arr.subList(mid + 1, right + 1));
        
        int i = 0, j = 0, k = left;
        
        while (i < leftArr.size() && j < rightArr.size()) {
            if (leftArr.get(i).getCustomerName().compareToIgnoreCase(
                    rightArr.get(j).getCustomerName()) <= 0) {
                arr.set(k++, leftArr.get(i++));
            } else {
                arr.set(k++, rightArr.get(j++));
            }
        }
        
        while (i < leftArr.size()) arr.set(k++, leftArr.get(i++));
        while (j < rightArr.size()) arr.set(k++, rightArr.get(j++));
    }
    
    // Sort accounts by balance
    private static void mergeSortByBalance(List<Account> arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortByBalance(arr, left, mid);
            mergeSortByBalance(arr, mid + 1, right);
            mergeByBalance(arr, left, mid, right);
        }
    }
    
    private static void mergeByBalance(List<Account> arr, int left, int mid, int right) {
        List<Account> leftArr = new ArrayList<>(arr.subList(left, mid + 1));
        List<Account> rightArr = new ArrayList<>(arr.subList(mid + 1, right + 1));
        
        int i = 0, j = 0, k = left;
        
        while (i < leftArr.size() && j < rightArr.size()) {
            if (leftArr.get(i).getBalance() >= rightArr.get(j).getBalance()) {
                arr.set(k++, leftArr.get(i++));
            } else {
                arr.set(k++, rightArr.get(j++));
            }
        }
        
        while (i < leftArr.size()) arr.set(k++, leftArr.get(i++));
        while (j < rightArr.size()) arr.set(k++, rightArr.get(j++));
    }
    
    // Sort transactions by amount
    private static void mergeSortTransactionsByAmount(List<Transaction> arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortTransactionsByAmount(arr, left, mid);
            mergeSortTransactionsByAmount(arr, mid + 1, right);
            mergeTransactionsByAmount(arr, left, mid, right);
        }
    }
    
    private static void mergeTransactionsByAmount(List<Transaction> arr, int left, int mid, int right) {
        List<Transaction> leftArr = new ArrayList<>(arr.subList(left, mid + 1));
        List<Transaction> rightArr = new ArrayList<>(arr.subList(mid + 1, right + 1));
        
        int i = 0, j = 0, k = left;
        
        while (i < leftArr.size() && j < rightArr.size()) {
            if (leftArr.get(i).getAmount() >= rightArr.get(j).getAmount()) {
                arr.set(k++, leftArr.get(i++));
            } else {
                arr.set(k++, rightArr.get(j++));
            }
        }
        
        while (i < leftArr.size()) arr.set(k++, leftArr.get(i++));
        while (j < rightArr.size()) arr.set(k++, rightArr.get(j++));
    }
    
    // Sort transactions by date
    private static void mergeSortTransactionsByDate(List<Transaction> arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortTransactionsByDate(arr, left, mid);
            mergeSortTransactionsByDate(arr, mid + 1, right);
            mergeTransactionsByDate(arr, left, mid, right);
        }
    }
    
    private static void mergeTransactionsByDate(List<Transaction> arr, int left, int mid, int right) {
        List<Transaction> leftArr = new ArrayList<>(arr.subList(left, mid + 1));
        List<Transaction> rightArr = new ArrayList<>(arr.subList(mid + 1, right + 1));
        
        int i = 0, j = 0, k = left;
        
        while (i < leftArr.size() && j < rightArr.size()) {
            if (leftArr.get(i).getTransactionDate().after(rightArr.get(j).getTransactionDate())) {
                arr.set(k++, leftArr.get(i++));
            } else {
                arr.set(k++, rightArr.get(j++));
            }
        }
        
        while (i < leftArr.size()) arr.set(k++, leftArr.get(i++));
        while (j < rightArr.size()) arr.set(k++, rightArr.get(j++));
    }
}