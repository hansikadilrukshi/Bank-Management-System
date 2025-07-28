package com.bank.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String DATA_DIR = System.getProperty("catalina.base", System.getProperty("user.home")) + File.separator + "bankdata";
    
    static {
        // Create data directory if it doesn't exist
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    public static String getDataPath(String fileName) {
        return DATA_DIR + File.separator + fileName;
    }
    
    public static boolean isFileExist(String fileName) {
        File file = new File(getDataPath(fileName));
        return file.exists();
    }
    
    public static boolean createFile(String fileName) {
        File file = new File(getDataPath(fileName));
        boolean state = false;
        try {
            state = file.createNewFile();
        } catch (Exception e) {
            System.err.println("Error creating file: " + fileName + " - " + e.getMessage());
            e.printStackTrace();
        }
        return state;
    }
    
    public static boolean writeToFile(String fileName, boolean append, String data) {
        boolean fileCreated = true;
        String filePath = getDataPath(fileName);
        
        if (!isFileExist(fileName)) {
            fileCreated = createFile(fileName);
        }
        
        if (fileCreated) {
            try (FileWriter fileWriter = new FileWriter(filePath, append)) {
                fileWriter.write(data);
                return true;
            } catch (IOException e) {
                System.err.println("Error writing to file: " + fileName + " - " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
    
    public static String[] readFromFile(String fileName) {
        if (!isFileExist(fileName)) {
            return new String[0];
        }
        
        List<String> lines = new ArrayList<>();
        String filePath = getDataPath(fileName);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName + " - " + e.getMessage());
            e.printStackTrace();
        }
        
        return lines.toArray(new String[0]);
    }
    
    public static boolean deleteFile(String fileName) {
        File file = new File(getDataPath(fileName));
        return file.delete();
    }
    
    public static boolean clearFile(String fileName) {
        return writeToFile(fileName, false, "");
    }
}