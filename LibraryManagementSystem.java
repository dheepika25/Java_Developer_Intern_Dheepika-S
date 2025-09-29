package com.elevate.task3;

//LibraryManagementSystem.java
import java.util.ArrayList;
import java.util.List;

//Book Class
class Book {
 private String title;
 private String author;
 private String isbn;
 private boolean isAvailable;
 
 public Book(String title, String author, String isbn) {
     this.title = title;
     this.author = author;
     this.isbn = isbn;
     this.isAvailable = true;
 }
 
 public String getTitle() { return title; }
 public String getAuthor() { return author; }
 public String getIsbn() { return isbn; }
 public boolean isAvailable() { return isAvailable; }
 public void setAvailable(boolean available) { this.isAvailable = available; }
 
 public void displayInfo() {
     System.out.println("Title: " + title + 
                      ", Author: " + author + 
                      ", ISBN: " + isbn + 
                      ", Available: " + (isAvailable ? "Yes" : "No"));
 }
}

//User Class
class User {
 private String name;
 private String userId;
 private List<Book> borrowedBooks;
 
 public User(String name, String userId) {
     this.name = name;
     this.userId = userId;
     this.borrowedBooks = new ArrayList<>();
 }
 
 public String getName() { return name; }
 public String getUserId() { return userId; }
 public List<Book> getBorrowedBooks() { return borrowedBooks; }
 
 public void borrowBook(Book book) {
     if (book.isAvailable()) {
         borrowedBooks.add(book);
         book.setAvailable(false);
         System.out.println(name + " successfully borrowed: " + book.getTitle());
     } else {
         System.out.println("Sorry, " + book.getTitle() + " is not available.");
     }
 }
 
 public void returnBook(Book book) {
     if (borrowedBooks.contains(book)) {
         borrowedBooks.remove(book);
         book.setAvailable(true);
         System.out.println(name + " successfully returned: " + book.getTitle());
     } else {
         System.out.println("You didn't borrow this book: " + book.getTitle());
     }
 }
 
 public void displayInfo() {
     System.out.println("User: " + name + " (ID: " + userId + ")");
     System.out.println("Borrowed Books:");
     if (borrowedBooks.isEmpty()) {
         System.out.println("  No books borrowed");
     } else {
         for (Book book : borrowedBooks) {
             System.out.println("  - " + book.getTitle());
         }
     }
 }
}

//Library Class
class Library {
 private List<Book> books;
 private List<User> users;
 
 public Library() {
     this.books = new ArrayList<>();
     this.users = new ArrayList<>();
 }
 
 public void addBook(Book book) {
     books.add(book);
     System.out.println("Book added: " + book.getTitle());
 }
 
 public void removeBook(String isbn) {
     Book bookToRemove = null;
     for (Book book : books) {
         if (book.getIsbn().equals(isbn)) {
             bookToRemove = book;
             break;
         }
     }
     
     if (bookToRemove != null) {
         books.remove(bookToRemove);
         System.out.println("Book removed: " + bookToRemove.getTitle());
     } else {
         System.out.println("Book with ISBN " + isbn + " not found.");
     }
 }
 
 public void addUser(User user) {
     users.add(user);
     System.out.println("User added: " + user.getName());
 }
 
 public void issueBook(String userId, String isbn) {
     User user = findUserById(userId);
     Book book = findBookByIsbn(isbn);
     
     if (user != null && book != null) {
         user.borrowBook(book);
     } else {
         if (user == null) {
             System.out.println("User with ID " + userId + " not found.");
         }
         if (book == null) {
             System.out.println("Book with ISBN " + isbn + " not found.");
         }
     }
 }
 
 public void returnBook(String userId, String isbn) {
     User user = findUserById(userId);
     Book book = findBookByIsbn(isbn);
     
     if (user != null && book != null) {
         user.returnBook(book);
     } else {
         if (user == null) {
             System.out.println("User with ID " + userId + " not found.");
         }
         if (book == null) {
             System.out.println("Book with ISBN " + isbn + " not found.");
         }
     }
 }
 
 private User findUserById(String userId) {
     for (User user : users) {
         if (user.getUserId().equals(userId)) {
             return user;
         }
     }
     return null;
 }
 
 private Book findBookByIsbn(String isbn) {
     for (Book book : books) {
         if (book.getIsbn().equals(isbn)) {
             return book;
         }
     }
     return null;
 }
 
 public void displayAllBooks() {
     System.out.println("\n=== ALL BOOKS IN LIBRARY ===");
     if (books.isEmpty()) {
         System.out.println("No books in library.");
     } else {
         for (Book book : books) {
             book.displayInfo();
         }
     }
 }
 
 public void displayAllUsers() {
     System.out.println("\n=== ALL LIBRARY USERS ===");
     if (users.isEmpty()) {
         System.out.println("No users registered.");
     } else {
         for (User user : users) {
             user.displayInfo();
             System.out.println();
         }
     }
 }
 
 public void displayAvailableBooks() {
     System.out.println("\n=== AVAILABLE BOOKS ===");
     boolean foundAvailable = false;
     for (Book book : books) {
         if (book.isAvailable()) {
             book.displayInfo();
             foundAvailable = true;
         }
     }
     if (!foundAvailable) {
         System.out.println("No available books at the moment.");
     }
 }
}

//Main Class
public class LibraryManagementSystem {
 public static void main(String[] args) {
     // Create library instance
     Library library = new Library();
     
     // Create some books
     Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "12345");
     Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "12346");
     Book book3 = new Book("1984", "George Orwell", "12347");
     Book book4 = new Book("Pride and Prejudice", "Jane Austen", "12348");
     
     // Add books to library
     library.addBook(book1);
     library.addBook(book2);
     library.addBook(book3);
     library.addBook(book4);
     
     // Create some users
     User user1 = new User("Alice Johnson", "U001");
     User user2 = new User("Bob Smith", "U002");
     
     // Add users to library
     library.addUser(user1);
     library.addUser(user2);
     
     // Display initial state
     library.displayAllBooks();
     library.displayAllUsers();
     
     System.out.println("\n=== TESTING BOOK TRANSACTIONS ===");
     
     // Test borrowing books
     library.issueBook("U001", "12345");
     library.issueBook("U001", "12346");
     library.issueBook("U002", "12347");
     
     // Try to borrow unavailable book
     library.issueBook("U002", "12345");
     
     // Display current state
     library.displayAllBooks();
     library.displayAllUsers();
     
     System.out.println("\n=== TESTING BOOK RETURNS ===");
     
     // Test returning books
     library.returnBook("U001", "12345");
     library.returnBook("U002", "12347");
     
     // Display final state
     library.displayAllBooks();
     library.displayAllUsers();
     library.displayAvailableBooks();
 }
}