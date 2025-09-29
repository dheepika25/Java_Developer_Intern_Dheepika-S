package com.elevate.task2;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Student Record Management System
 * A complete CRUD application for managing student records
 */
class Student {
    private int id;
    private String name;
    private double marks;
    private String grade;
    private String email;
    private String phone;
    
    // Constructor
    public Student(int id, String name, double marks, String email, String phone) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.email = email;
        this.phone = phone;
        this.grade = calculateGrade(marks);
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public double getMarks() { return marks; }
    public void setMarks(double marks) { 
        this.marks = marks; 
        this.grade = calculateGrade(marks);
    }
    
    public String getGrade() { return grade; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    /**
     * Calculates grade based on marks
     */
    private String calculateGrade(double marks) {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 50) return "D";
        else return "F";
    }
    
    @Override
    public String toString() {
        return String.format("│ %-4d │ %-20s │ %-6.2f │ %-4s │ %-25s │ %-12s │", 
                            id, name, marks, grade, email, phone);
    }
    
    /**
     * Returns detailed student information
     */
    public String getDetailedInfo() {
        return "\n=== STUDENT DETAILS ===\n" +
               "ID: " + id + "\n" +
               "Name: " + name + "\n" +
               "Marks: " + marks + "\n" +
               "Grade: " + grade + "\n" +
               "Email: " + email + "\n" +
               "Phone: " + phone + "\n" +
               "======================";
    }
}

/**
 * Main management system class
 */
public class StudentRecordManagementSystem {
    private ArrayList<Student> students;
    private int nextId;
    private Scanner scanner;
    
    public StudentRecordManagementSystem() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
        nextId = 1;
        initializeSampleData();
    }
    
    /**
     * Initialize with sample students
     */
    private void initializeSampleData() {
        students.add(new Student(nextId++, "Alice Johnson", 85.5, "alice@email.com", "123-456-7890"));
        students.add(new Student(nextId++, "Bob Smith", 92.0, "bob@email.com", "123-456-7891"));
        students.add(new Student(nextId++, "Carol Davis", 76.5, "carol@email.com", "123-456-7892"));
        students.add(new Student(nextId++, "David Wilson", 88.0, "david@email.com", "123-456-7893"));
        students.add(new Student(nextId++, "Eva Brown", 65.5, "eva@email.com", "123-456-7894"));
    }
    
    /**
     * Add a new student
     */
    public void addStudent() {
        System.out.println("\n🎓 ADD NEW STUDENT");
        System.out.println("==================");
        
        try {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine().trim();
            
            if (name.isEmpty()) {
                System.out.println("❌ Name cannot be empty!");
                return;
            }
            
            double marks = getValidMarks();
            
            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine().trim();
            
            Student student = new Student(nextId++, name, marks, email, phone);
            students.add(student);
            
            System.out.println("✅ Student added successfully!");
            System.out.println(student.getDetailedInfo());
            
        } catch (Exception e) {
            System.out.println("❌ Error adding student: " + e.getMessage());
        }
    }
    
    /**
     * View all students in a formatted table
     */
    public void viewAllStudents() {
        System.out.println("\n📋 ALL STUDENTS");
        System.out.println("===============");
        
        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
            return;
        }
        
        printTableHeader();
        for (Student student : students) {
            System.out.println(student);
        }
        printTableFooter();
        System.out.println("Total students: " + students.size());
    }
    
    /**
     * Update student information
     */
    public void updateStudent() {
        System.out.println("\n✏️  UPDATE STUDENT");
        System.out.println("=================");
        
        if (students.isEmpty()) {
            System.out.println("❌ No students available to update.");
            return;
        }
        
        viewAllStudents();
        System.out.print("\nEnter student ID to update: ");
        
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Student student = findStudentById(id);
            
            if (student == null) {
                System.out.println("❌ Student with ID " + id + " not found.");
                return;
            }
            
            System.out.println("Current student details:");
            System.out.println(student.getDetailedInfo());
            
            // Update name
            System.out.print("Enter new name (press enter to keep '" + student.getName() + "'): ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                student.setName(newName);
            }
            
            // Update marks
            System.out.print("Enter new marks (press enter to keep " + student.getMarks() + "): ");
            String marksInput = scanner.nextLine();
            if (!marksInput.trim().isEmpty()) {
                double newMarks = Double.parseDouble(marksInput);
                if (newMarks >= 0 && newMarks <= 100) {
                    student.setMarks(newMarks);
                } else {
                    System.out.println("❌ Marks must be between 0 and 100.");
                }
            }
            
            // Update email
            System.out.print("Enter new email (press enter to keep '" + student.getEmail() + "'): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.trim().isEmpty()) {
                student.setEmail(newEmail);
            }
            
            // Update phone
            System.out.print("Enter new phone (press enter to keep '" + student.getPhone() + "'): ");
            String newPhone = scanner.nextLine();
            if (!newPhone.trim().isEmpty()) {
                student.setPhone(newPhone);
            }
            
            System.out.println("✅ Student updated successfully!");
            System.out.println("Updated details:" + student.getDetailedInfo());
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID format!");
        } catch (Exception e) {
            System.out.println("❌ Error updating student: " + e.getMessage());
        }
    }
    
    /**
     * Delete a student
     */
    public void deleteStudent() {
        System.out.println("\n🗑️  DELETE STUDENT");
        System.out.println("================");
        
        if (students.isEmpty()) {
            System.out.println("❌ No students available to delete.");
            return;
        }
        
        viewAllStudents();
        System.out.print("\nEnter student ID to delete: ");
        
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Student student = findStudentById(id);
            
            if (student == null) {
                System.out.println("❌ Student with ID " + id + " not found.");
                return;
            }
            
            System.out.println("Student to delete:");
            System.out.println(student.getDetailedInfo());
            System.out.print("❓ Are you sure you want to delete this student? (y/n): ");
            String confirmation = scanner.nextLine();
            
            if (confirmation.equalsIgnoreCase("y")) {
                students.remove(student);
                System.out.println("✅ Student deleted successfully!");
            } else {
                System.out.println("✅ Deletion cancelled.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID format!");
        }
    }
    
    /**
     * Search for a student by ID
     */
    public void searchStudent() {
        System.out.println("\n🔍 SEARCH STUDENT");
        System.out.println("================");
        
        if (students.isEmpty()) {
            System.out.println("❌ No students available to search.");
            return;
        }
        
        System.out.print("Enter student ID to search: ");
        
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Student student = findStudentById(id);
            
            if (student == null) {
                System.out.println("❌ Student with ID " + id + " not found.");
            } else {
                System.out.println(student.getDetailedInfo());
            }
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID format!");
        }
    }
    
    /**
     * Search students by name
     */
    public void searchStudentByName() {
        System.out.println("\n🔍 SEARCH STUDENT BY NAME");
        System.out.println("=======================");
        
        if (students.isEmpty()) {
            System.out.println("❌ No students available to search.");
            return;
        }
        
        System.out.print("Enter student name to search: ");
        String searchName = scanner.nextLine().toLowerCase().trim();
        
        if (searchName.isEmpty()) {
            System.out.println("❌ Search name cannot be empty!");
            return;
        }
        
        List<Student> foundStudents = students.stream()
            .filter(student -> student.getName().toLowerCase().contains(searchName))
            .collect(Collectors.toList());
        
        if (foundStudents.isEmpty()) {
            System.out.println("❌ No students found with name containing: " + searchName);
        } else {
            System.out.println("Found " + foundStudents.size() + " student(s):");
            printTableHeader();
            for (Student student : foundStudents) {
                System.out.println(student);
            }
            printTableFooter();
        }
    }
    
    /**
     * Display statistics about students
     */
    public void showStatistics() {
        System.out.println("\n📊 STUDENT STATISTICS");
        System.out.println("====================");
        
        if (students.isEmpty()) {
            System.out.println("No students available for statistics.");
            return;
        }
        
        double totalMarks = students.stream().mapToDouble(Student::getMarks).sum();
        double average = totalMarks / students.size();
        double highest = students.stream().mapToDouble(Student::getMarks).max().orElse(0);
        double lowest = students.stream().mapToDouble(Student::getMarks).min().orElse(0);
        
        // Grade distribution
        Map<String, Long> gradeDistribution = students.stream()
            .collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
        
        System.out.printf("Total students: %d%n", students.size());
        System.out.printf("Average marks: %.2f%n", average);
        System.out.printf("Highest marks: %.2f%n", highest);
        System.out.printf("Lowest marks: %.2f%n", lowest);
        
        System.out.println("\nGrade Distribution:");
        gradeDistribution.forEach((grade, count) -> 
            System.out.printf("Grade %s: %d students%n", grade, count));
    }
    
    /**
     * Sort students by various criteria
     */
    public void sortStudents() {
        System.out.println("\n🔢 SORT STUDENTS");
        System.out.println("===============");
        System.out.println("1. Sort by ID");
        System.out.println("2. Sort by Name (A-Z)");
        System.out.println("3. Sort by Name (Z-A)");
        System.out.println("4. Sort by Marks (High to Low)");
        System.out.println("5. Sort by Marks (Low to High)");
        System.out.print("Choose sorting option (1-5): ");
        
        String choice = scanner.nextLine();
        ArrayList<Student> sortedStudents = new ArrayList<>(students);
        
        switch (choice) {
            case "1":
                sortedStudents.sort(Comparator.comparingInt(Student::getId));
                System.out.println("Sorted by ID:");
                break;
            case "2":
                sortedStudents.sort(Comparator.comparing(Student::getName));
                System.out.println("Sorted by Name (A-Z):");
                break;
            case "3":
                sortedStudents.sort(Comparator.comparing(Student::getName).reversed());
                System.out.println("Sorted by Name (Z-A):");
                break;
            case "4":
                sortedStudents.sort(Comparator.comparingDouble(Student::getMarks).reversed());
                System.out.println("Sorted by Marks (High to Low):");
                break;
            case "5":
                sortedStudents.sort(Comparator.comparingDouble(Student::getMarks));
                System.out.println("Sorted by Marks (Low to High):");
                break;
            default:
                System.out.println("❌ Invalid option!");
                return;
        }
        
        printTableHeader();
        for (Student student : sortedStudents) {
            System.out.println(student);
        }
        printTableFooter();
    }
    
    // Helper methods
    private Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
    
    private double getValidMarks() {
        while (true) {
            try {
                System.out.print("Enter marks (0-100): ");
                double marks = Double.parseDouble(scanner.nextLine());
                if (marks >= 0 && marks <= 100) {
                    return marks;
                } else {
                    System.out.println("❌ Marks must be between 0 and 100. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.");
            }
        }
    }
    
    private void printTableHeader() {
        System.out.println("┌──────┬──────────────────────┬────────┬──────┬─────────────────────────┬──────────────┐");
        System.out.println("│ ID   │ Name                 │ Marks  │ Grade│ Email                   │ Phone        │");
        System.out.println("├──────┼──────────────────────┼────────┼──────┼─────────────────────────┼──────────────┤");
    }
    
    private void printTableFooter() {
        System.out.println("└──────┴──────────────────────┴────────┴──────┴─────────────────────────┴──────────────┘");
    }
    
    /**
     * Display main menu
     */
    public void displayMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🏫 STUDENT RECORD MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));
        System.out.println("1. 📋 View All Students");
        System.out.println("2. 🎓 Add New Student");
        System.out.println("3. ✏️  Update Student");
        System.out.println("4. 🗑️  Delete Student");
        System.out.println("5. 🔍 Search Student by ID");
        System.out.println("6. 🔎 Search Student by Name");
        System.out.println("7. 📊 Show Statistics");
        System.out.println("8. 🔢 Sort Students");
        System.out.println("9. ❌ Exit");
        System.out.println("=".repeat(60));
        System.out.print("Choose an option (1-9): ");
    }
    
    /**
     * Main program loop
     */
    public void run() {
        System.out.println("🚀 Welcome to Student Record Management System!");
        System.out.println("✅ System initialized with sample data.");
        
        while (true) {
            displayMenu();
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    viewAllStudents();
                    break;
                case "2":
                    addStudent();
                    break;
                case "3":
                    updateStudent();
                    break;
                case "4":
                    deleteStudent();
                    break;
                case "5":
                    searchStudent();
                    break;
                case "6":
                    searchStudentByName();
                    break;
                case "7":
                    showStatistics();
                    break;
                case "8":
                    sortStudents();
                    break;
                case "9":
                    System.out.println("👋 Thank you for using Student Management System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Invalid option! Please choose 1-9.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        StudentRecordManagementSystem system = new StudentRecordManagementSystem();
        system.run();
    }
}