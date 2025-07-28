# Project Bolt

A full-stack web application combining Java (Spring-like structure), JSP, and a modern TypeScript + React frontend using Vite and Tailwind CSS. The project appears to be a banking management system with features for admin and customer users, including transaction management and account handling.

## 🚀 Features

- Java backend with servlet-based controllers
- JSP views for server-rendered pages
- React + TypeScript frontend using Vite
- Tailwind CSS for styling
- Algorithms: Merge Sort, Binary Search Tree (BST) implementation
- Transaction and Account services
- File handling utilities

## 📁 Project Structure

project/
├── src/
│ ├── main/java/com/bank/
│ │ ├── controller/ # Login, Logout, Admin, Transaction controllers
│ │ ├── model/ # Account and Transaction model classes
│ │ ├── service/ # Business logic for accounts and transactions
│ │ ├── structure/ # MergeSort & BinarySearchTree algorithms
│ │ └── utils/ # File handling utility
│ ├── main/webapp/ # JSPs, CSS, and web.xml
│ └── main.tsx, App.tsx # React frontend entry points
├── index.html # HTML entry point for Vite
├── package.json # NPM dependencies
├── pom.xml # Maven dependencies for backend
├── tailwind.config.js # Tailwind CSS config
├── vite.config.ts # Vite build config

shell
Copy
Edit

## 🛠️ Getting Started

### Prerequisites

- Java 17+
- Maven
- Node.js & npm

### Backend Setup

```bash
cd project
mvn clean install
# Deploy WAR or run with a servlet container (e.g., Tomcat)
Frontend Setup
bash


npm install
npm run dev
🧪 Sample Pages
login.jsp — User login page

adminDashboard.jsp, customerDashboard.jsp — Role-based dashboards

transactionHistory.jsp, adminAccounts.jsp — Data views

error.jsp — Error handling page

📦 Algorithms
MergeSort.java — For sorting transactions

BinarySearchTree.java — For account searching


📄 License
This project is licensed under the MIT License - see the LICENSE file for details.

