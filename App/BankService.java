import java.util.UUID;
import java.util.Scanner;
import java.sql.*;
public class BankService {
    Scanner sc=new Scanner(System.in);
    public void addAccount(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try(Connection con=DBConnection.getConnection()){
            String sql="Insert into accounts(account_no,name,email,phone,balance) values(?,?,?,?,?)";
            PreparedStatement psmt=con.prepareStatement(sql);
            while(true){

                String accountNo=generateAccountNo();

                System.out.print("Enter Full Name: ");
                String name=sc.next().toUpperCase();
                System.out.println();

                System.out.print("Enter Email:");
                String email=sc.next();
                System.out.println();

                System.out.print("Enter Phone No: ");
                String phone=sc.next();
                System.out.println();

                System.out.print("Enter Initial Balance: ");
                double bal=sc.nextDouble();
                System.out.println();

                if(bal<0){
                    System.out.println("Initial amount Must be positive!!!");
                    return;
                }

                psmt.setString(1,accountNo);
                psmt.setString(2,name);
                psmt.setString(3,email);
                psmt.setString(4,phone);
                psmt.setDouble(5,bal);
                psmt.addBatch();

                System.out.println("Do you want to add another Account?(Y/N)");
                String choice=sc.next();
                if(choice.equalsIgnoreCase("N")) break;
            }
            int[] results= psmt.executeBatch();
            for(int i=0; i< results.length; i++){
                if(results[i]==0){
                    System.out.println("Account "+ i+1 +" not added");
                }else{
                    System.out.println(" Account" +(i+1)+ " added successfully");
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private String generateAccountNo(){
        return String.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits())).substring(0,10);
    }
    public void viewAllAccounts() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT account_no, name, email, phone, balance FROM accounts";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("---------- Account Holders ----------");
            while (rs.next()) {
                System.out.println("Account No: " + rs.getString("account_no"));
                System.out.println("Name      : " + rs.getString("name"));
                System.out.println("Email     : " + rs.getString("email"));
                System.out.println("Phone     : " + rs.getString("phone"));
                System.out.println("Balance   : " + rs.getDouble("balance"));
                System.out.println("------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deposit() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Account No: ");
            String accNo = sc.next();
            System.out.println();

            String sql = "SELECT balance FROM accounts WHERE account_no=?";
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setString(1, accNo);
            ResultSet rs = psmt.executeQuery();


            if (!rs.next()) {
                System.out.println("Account not found!");
                return;
            }
            double bal=rs.getDouble("balance");

            System.out.print("Enter amount to deposit: ");
            double amt = sc.nextDouble();
            System.out.println();

            if (amt < 0) {
                System.out.println("Amount must be positive!!!");
                return;
            }

            String sql2 = "UPDATE accounts SET balance = balance + ? WHERE account_no=?";
            PreparedStatement pt = con.prepareStatement(sql2);
            pt.setDouble(1, amt);
            pt.setString(2, accNo);
            int rowsAffected = pt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Deposit successful.");
                System.out.println("Balance: "+(bal+amt));
            } else {
                System.out.println("Deposit failed.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void withdraw() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Account No: ");
            String accNo = sc.next();
            System.out.println();

            String sql = "SELECT balance FROM accounts WHERE account_no = ?";
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setString(1, accNo);
            ResultSet rs = psmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Account not found!");
                return;
            }

            double balance = rs.getDouble("balance");

            System.out.print("Enter amount to withdraw: ");
            double amt = sc.nextDouble();
            System.out.println();

            if (amt < 0) {
                System.out.println("Amount must be positive!");
                return;
            }
            if (amt > balance) {
                System.out.println("Insufficient balance!");
                return;
            }

            String sql2 = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";
            PreparedStatement pt = con.prepareStatement(sql2);
            pt.setDouble(1, amt);
            pt.setString(2, accNo);
            int rowsAffected = pt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Withdrawal successful.");
                System.out.println("Remaining Balance: " + (balance - amt));
            } else {
                System.out.println("Withdrawal failed.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void checkBalance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Account No: ");
            String accNo = sc.next();
            sc.nextLine();

            String sql = "SELECT name, balance FROM accounts WHERE account_no = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, accNo);
            ResultSet rs = pt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                double bal = rs.getDouble("balance");

                System.out.println("Account Holder: " + name);
                System.out.println("Balance       : ₹" + bal);
            } else {
                System.out.println("Account not found.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Account Number: ");
            String accNo = sc.next();
            sc.nextLine(); // flush

            String fetchSql = "SELECT name FROM accounts WHERE account_no = ?";
            PreparedStatement checkStmt = con.prepareStatement(fetchSql);
            checkStmt.setString(1, accNo);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Account not found.");
                return;
            }

            String name = rs.getString("name");
            System.out.println("Account Holder: " + name);
            System.out.println("Are you sure you want to delete this account? (Y/N): ");
            String confirm = sc.nextLine().trim().toUpperCase();

            if (confirm.equals("Y")) {
                String deleteSql = "DELETE FROM accounts WHERE account_no = ?";
                PreparedStatement deleteStmt = con.prepareStatement(deleteSql);
                deleteStmt.setString(1, accNo);
                int rows = deleteStmt.executeUpdate();

                if (rows > 0) {
                    System.out.println("Account deleted successfully.");
                } else {
                    System.out.println("Failed to delete account.");
                }
            } else {
                System.out.println("Account deletion cancelled.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void transferMoney() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false); // Begin transaction

            System.out.print("Enter Sender Account No: ");
            String sender = sc.next();
            System.out.print("Enter Receiver Account No: ");
            String receiver = sc.next();
            System.out.print("Enter Amount to Transfer: ");
            double amt = sc.nextDouble();

            // Step 1: Check Sender Balance
            String checkBalance = "SELECT balance FROM accounts WHERE account_no = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkBalance);
            checkStmt.setString(1, sender);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Sender Account Not Found.");
                return;
            }

            double senderBalance = rs.getDouble("balance");
            if (senderBalance < amt) {
                System.out.println("Insufficient Funds.");
                return;
            }

            // Step 2: Deduct from sender
            String deduct = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";
            PreparedStatement deductStmt = con.prepareStatement(deduct);
            deductStmt.setDouble(1, amt);
            deductStmt.setString(2, sender);
            deductStmt.executeUpdate();

            // Step 3: Add to receiver
            String add = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";
            PreparedStatement addStmt = con.prepareStatement(add);
            addStmt.setDouble(1, amt);
            addStmt.setString(2, receiver);
            int rowsAffected = addStmt.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Receiver Account Not Found. Rolling back...");
                con.rollback(); // Rollback everything
                return;
            }

            con.commit(); // Transaction successful
            System.out.println("Money Transferred Successfully.");

        } catch (SQLException e) {
            System.out.println("Error during transaction: " + e.getMessage());
            try {
                // Rollback if any error
                DBConnection.getConnection().rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
        }
    }


}
