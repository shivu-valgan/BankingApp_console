# 💳 Bank Management System (JDBC Console Project)

This is a **Java-based mini project** built using **JDBC (Java Database Connectivity)** that simulates a **bank management system**. It allows basic account operations like deposit, withdrawal, balance check, and account management using a **MySQL database** from a console-based interface.

## 🔧 Tech Stack

- Java (Core)
- JDBC
- MySQL
- IntelliJ IDEA

---

## 📁 Features

- ➕ Add New Bank Account
- 📄 View All Accounts
- 💰 Deposit Money
- 🧾 Withdraw Money
- 🔍 Check Account Balance
- ❌ Delete Account
- 🔐 Custom Exceptions for Input Validation
- ✅ Batch Insertion using `addBatch()`
- 🧠 Follows basic **SOLID principles**

---

## 🧪 How It Works

- Uses **JDBC API** to connect to **MySQL database**
- Uses `PreparedStatement` to prevent SQL injection
- Exception handling ensures safe transactions
- Console interface handles user interaction
- Account details are stored in a table named `accounts`

---

## 🧱 Database Structure (MySQL)

```sql
CREATE DATABASE bankdb;

USE bankdb;

CREATE TABLE accounts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    account_no VARCHAR(20) UNIQUE NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(100),
    balance DOUBLE DEFAULT 0
);
````

---

## 🚀 How to Run

1. Clone the repository
2. Set up your MySQL database with the `accounts` table
3. Update DB credentials in `DBConnection.java`:

   ```java
   private static final String url = "jdbc:mysql://localhost:3306/bankdb";
   private static final String user = "root";
   private static final String pass = "your_password";
   ```
4. Run the `Main.java` or `BankService.java` file
5. Use console to interact with the system

---

## 📌 Sample Console Output

```
1. Add Account
2. View Accounts
3. Deposit
4. Withdraw
5. Check Balance
6. Delete Account
7. Exit
```

---

## 📂 Project Structure

```
├── BankService.java        // Main service logic
├── DBConnection.java       // Handles DB connection
├── Main.java               // Entry point
├── Custom Exceptions       // InvalidPinException, InsufficientFundsException
├── SQL Dump                // Table creation
```

---

## 👨‍💻 Author

**Shivakumar**
Aspiring Full Stack Java Developer
LinkedIn: [@shivu-valgan](https://www.linkedin.com/in/shivu-valgan)

---

## ✅ Status

✔️ Completed phase-1 with core JDBC
🔜 To be extended using **Spring Boot + JPA + REST APIs** for a full backend project

---

## 📄 License

This project is open for learning and modification. Free to use.

```

---

Let me know if you'd like a shorter version or if you want me to push this to a `.md` file for direct use on GitHub.
```
