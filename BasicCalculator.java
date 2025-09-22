package com.elevate.task1;

//BasicCalculator.java
import java.util.Scanner;

public class BasicCalculator {
 
 // Calculation methods
 public static double add(double num1, double num2) {
     return num1 + num2;
 }
 
 public static double subtract(double num1, double num2) {
     return num1 - num2;
 }
 
 public static double multiply(double num1, double num2) {
     return num1 * num2;
 }
 
 public static double divide(double num1, double num2) {
     if (num2 == 0) {
         System.out.println("Error: Cannot divide by zero!");
         return 0;
     }
     return num1 / num2;
 }
 
 // Show menu method
 public static void showMenu() {
     System.out.println("\nChoose an operation:");
     System.out.println("1. Addition (+)");
     System.out.println("2. Subtraction (-)");
     System.out.println("3. Multiplication (*)");
     System.out.println("4. Division (/)");
     System.out.println("5. Exit");
     System.out.print("Enter your choice (1-5): ");
 }
 
 // Perform calculation based on user choice
 public static void performCalculation(int choice, Scanner scanner) {
     System.out.print("Enter first number: ");
     double num1 = scanner.nextDouble();
     
     System.out.print("Enter second number: ");
     double num2 = scanner.nextDouble();
     
     double result = 0;
     String operation = "";
     
     switch (choice) {
         case 1:
             result = add(num1, num2);
             operation = "+";
             break;
         case 2:
             result = subtract(num1, num2);
             operation = "-";
             break;
         case 3:
             result = multiply(num1, num2);
             operation = "*";
             break;
         case 4:
             result = divide(num1, num2);
             operation = "/";
             break;
     }
     
     System.out.println("Result: " + num1 + " " + operation + " " + num2 + " = " + result);
 }
 
 // Main method
 public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);
     boolean continueCalculating = true;
     
     System.out.println("Welcome to Basic Calculator!");
     
     while (continueCalculating) {
         showMenu();
         int choice = scanner.nextInt();
         
         if (choice == 5) {
             continueCalculating = false;
             System.out.println("Thank you for using the calculator!");
         } else if (choice >= 1 && choice <= 4) {
             performCalculation(choice, scanner);
         } else {
             System.out.println("Invalid choice! Please try again.");
         }
     }
     
     scanner.close();
 }
}