package com.bank.structure;

import com.bank.model.Account;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;
    
    private static class Node<T> {
        T data;
        Node<T> left, right;
        
        Node(T data) {
            this.data = data;
        }
    }
    
    public void insert(T data) {
        root = insertRec(root, data);
    }
    
    private Node<T> insertRec(Node<T> root, T data) {
        if (root == null) {
            root = new Node<>(data);
            return root;
        }
        
        if (data.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, data);
        } else if (data.compareTo(root.data) > 0) {
            root.right = insertRec(root.right, data);
        }
        
        return root;
    }
    
    public T search(T key) {
        return searchRec(root, key);
    }
    
    private T searchRec(Node<T> root, T key) {
        if (root == null || root.data.compareTo(key) == 0) {
            return root != null ? root.data : null;
        }
        
        if (key.compareTo(root.data) < 0) {
            return searchRec(root.left, key);
        }
        
        return searchRec(root.right, key);
    }
    
    public List<T> searchByName(String name) {
        List<T> results = new ArrayList<>();
        searchByNameRec(root, name.toLowerCase(), results);
        return results;
    }
    
    private void searchByNameRec(Node<T> root, String name, List<T> results) {
        if (root == null) return;
        
        if (root.data instanceof Account) {
            Account account = (Account) root.data;
            if (account.getCustomerName().toLowerCase().contains(name)) {
                results.add(root.data);
            }
        }
        
        searchByNameRec(root.left, name, results);
        searchByNameRec(root.right, name, results);
    }
    
    public List<T> inorderTraversal() {
        List<T> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }
    
    private void inorderRec(Node<T> root, List<T> result) {
        if (root != null) {
            inorderRec(root.left, result);
            result.add(root.data);
            inorderRec(root.right, result);
        }
    }
    
    public void clear() {
        root = null;
    }
}