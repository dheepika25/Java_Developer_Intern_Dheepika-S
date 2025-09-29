package com.elevate.task5;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Complete Bank Account Simulation
 * This program demonstrates OOP concepts with a functional bank system
 */
public class BankAccountSystem {
    
    /**
     * Account class represents a single bank account
     * Demonstrates: Encapsulation, Methods, Constructors
     */
    static class Account {
        // Private variables - Encapsulation
        private String accountNumber;
        private String accountHolder;
        private double balance;
        private ArrayList<String> transactionHistory;
        
        /**
         * Constructor - initializes the account object
         */
        public Account(String accountNumber, String accountHolder, double initialBalance) {
            this.accountNumber = accountNumber;
            this.accountHolder = accountHolder;
            this.balance = initialBalance;
            this.transactionHistory = new ArrayList<>();
            
            // Record initial transaction
            String initialTransaction = String.format("ACCOUNT CREATED - Initial deposit: $%.2f", initialBalance);
            transactionHistory.add(initialTransaction);
            System.out.println("✓ Account created successfully for " + accountHolder);
        }
        
        /**
         * Deposit method - adds money to account
         * @param amount to deposit
         * @return true if successful
         */
        public boolean deposit(double amount) {
            if (amount > 0) {
                balance += amount;
                String transaction = String.format("%s - DEPOSIT: $%.2f - New Balance: $%.2f", 
                                                  new Date(), amount, balance);
                transactionHistory.add(transaction);
                System.out.println("✓ Deposit successful! New balance: $" + balance);
                return true;
            }
            System.out.println("✗ Error: Deposit amount must be positive!");
            return false;
        }
        
        /**
         * Withdraw method - removes money from account
         * @param amount to withdraw
         * @return true if successful
         */
        public boolean withdraw(double amount) {
            if (amount <= 0) {
                System.out.println("✗ Error: Withdrawal amount must be positive!");
                return false;
            }
            if (amount > balance) {
                System.out.println("✗ Error: Insufficient funds! Available: $" + balance);
                return false;
            }
            
            balance -= amount;
            String transaction = String.format("%s - WITHDRAWAL: $%.2f - New Balance: $%.2f", 
                                              new Date(), amount, balance);
            transactionHistory.add(transaction);
            System.out.println("✓ Withdrawal successful! New balance: $" + balance);
            return true;
        }
        
        /**
         * Getter methods - provide controlled access to private data
         */
        public double getBalance() {
            return balance;
        }
        
        public String getAccountNumber() {
            return accountNumber;
        }
        
        public String getAccountHolder() {
            return accountHolder;
        }
        
        /**
         * Display transaction history
         */
        public void displayTransactionHistory() {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("TRANSACTION HISTORY - Account: " + accountNumber);
            System.out.println("Account Holder: " + accountHolder);
            System.out.println("Current Balance: $" + String.format("%.2f", balance));
            System.out.println("-".repeat(50));
            
            if (transactionHistory.isEmpty()) {
                System.out.println("No transactions yet.");
            } else {
                for (int i = 0; i < transactionHistory.size(); i++) {
                    System.out.println((i + 1) + ". " + transactionHistory.get(i));
                }
            }
            System.out.println("=".repeat(50));
        }
        
        /**
         * Display account summary
         */
        public void displayAccountInfo() {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("ACCOUNT SUMMARY");
            System.out.println("-".repeat(40));
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Account Holder: " + accountHolder);
            System.out.println("Current Balance: $" + String.format("%.2f", balance));
            System.out.println("Total Transactions: " + (transactionHistory.size() - 1));
            System.out.println("=".repeat(40));
        }
    }
    
    /**
     * Bank class manages multiple accounts
     * Demonstrates: Composition, Collections, User Interaction
     */
    static class Bank {
        private HashMap<String, Account> accounts;
        private Scanner scanner;
        
        public Bank() {
            accounts = new HashMap<>();
            scanner = new Scanner(System.in);
            System.out.println("🏦 Bank System Initialized!");
        }
        
        /**
         * Create a new bank account
         */
        public void createAccount() {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("CREATE NEW ACCOUNT");
            System.out.println("-".repeat(40));
            
            System.out.print("Enter account number: ");
            String accountNumber = scanner.nextLine();
            
            // Check if account already exists
            if (accounts.containsKey(accountNumber)) {
                System.out.println("✗ Error: Account number already exists!");
                return;
            }
            
            System.out.print("Enter account holder name: ");
            String accountHolder = scanner.nextLine();
            
            System.out.print("Enter initial deposit amount: $");
            double initialBalance = scanner.nextDouble();
            scanner.nextLine(); // Clear the newline character
            
            if (initialBalance < 0) {
                System.out.println("✗ Error: Initial balance cannot be negative!");
                return;
            }
            
            // Create and store the new account
            Account newAccount = new Account(accountNumber, accountHolder, initialBalance);
            accounts.put(accountNumber, newAccount);
        }
        
        /**
         * Access an existing account for operations
         */
        public void accessAccount() {
            System.out.print("\nEnter account number to access: ");
            String accountNumber = scanner.nextLine();
            
            Account account = accounts.get(accountNumber);
            if (account == null) {
                System.out.println("✗ Error: Account not found!");
                return;
            }
            
            System.out.println("✓ Welcome, " + account.getAccountHolder() + "!");
            accountMenu(account);
        }
        
        /**
         * Menu for individual account operations
         */
        private void accountMenu(Account account) {
            while (true) {
                System.out.println("\n" + "=".repeat(40));
                System.out.println("ACCOUNT MENU - " + account.getAccountNumber());
                System.out.println("-".repeat(40));
                System.out.println("1. 💰 Deposit Money");
                System.out.println("2. 💸 Withdraw Money");
                System.out.println("3. 📊 Check Balance");
                System.out.println("4. 📋 View Transaction History");
                System.out.println("5. 👤 Account Summary");
                System.out.println("6. ↩️  Back to Main Menu");
                System.out.print("Choose an option (1-6): ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear newline
                
                switch (choice) {
                    case 1:
                        performDeposit(account);
                        break;
                    case 2:
                        performWithdrawal(account);
                        break;
                    case 3:
                        checkBalance(account);
                        break;
                    case 4:
                        account.displayTransactionHistory();
                        break;
                    case 5:
                        account.displayAccountInfo();
                        break;
                    case 6:
                        System.out.println("Returning to main menu...");
                        return;
                    default:
                        System.out.println("✗ Invalid choice! Please enter 1-6.");
                }
            }
        }
        
        /**
         * Perform deposit operation
         */
        private void performDeposit(Account account) {
            System.out.print("\nEnter deposit amount: $");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            account.deposit(amount);
        }
        
        /**
         * Perform withdrawal operation
         */
        private void performWithdrawal(Account account) {
            System.out.print("\nEnter withdrawal amount: $");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            account.withdraw(amount);
        }
        
        /**
         * Check and display current balance
         */
        private void checkBalance(Account account) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("BALANCE INQUIRY");
            System.out.println("Account: " + account.getAccountNumber());
            System.out.println("Holder: " + account.getAccountHolder());
            System.out.println("Current Balance: $" + String.format("%.2f", account.getBalance()));
            System.out.println("-".repeat(30));
        }
        
        /**
         * Display all accounts in the bank
         */
        public void displayAllAccounts() {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("ALL ACCOUNTS IN BANK");
            System.out.println("-".repeat(50));
            
            if (accounts.isEmpty()) {
                System.out.println("No accounts found in the system.");
            } else {
                int count = 1;
                for (Account account : accounts.values()) {
                    System.out.println(count + ". " + account.getAccountNumber() + 
                                     " - " + account.getAccountHolder() + 
                                     " - Balance: $" + String.format("%.2f", account.getBalance()));
                    count++;
                }
                System.out.println("Total Accounts: " + accounts.size());
            }
            System.out.println("=".repeat(50));
        }
        
        /**
         * Main menu for the bank system
         */
        public void showMainMenu() {
            System.out.println("\n" + "✨".repeat(25));
            System.out.println("      WELCOME TO BANK ACCOUNT SYSTEM");
            System.out.println("✨".repeat(25));
            
            while (true) {
                System.out.println("\n" + "=".repeat(40));
                System.out.println("MAIN MENU");
                System.out.println("-".repeat(40));
                System.out.println("1. 🆕 Create New Account");
                System.out.println("2. 🔓 Access Existing Account");
                System.out.println("3. 📋 Display All Accounts");
                System.out.println("4. 🚪 Exit System");
                System.out.print("Choose an option (1-4): ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear newline
                
                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        accessAccount();
                        break;
                    case 3:
                        displayAllAccounts();
                        break;
                    case 4:
                        System.out.println("\n" + "💫".repeat(20));
                        System.out.println("  Thank you for using our Bank System!");
                        System.out.println("            Have a great day! 👋");
                        System.out.println("💫".repeat(20));
                        return;
                    default:
                        System.out.println("✗ Invalid choice! Please enter 1-4.");
                }
            }
        }
    }
    
    /**
     * MAIN METHOD - Program entry point
     */
    public static void main(String[] args) {
        // Create bank instance and start the system
        Bank bank = new Bank();
        bank.showMainMenu();
    }
}