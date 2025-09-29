package com.elevate.task4;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class NotesApp {
    private static final String NOTES_FILE = "notes.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Java Notes App ===");
        
        while (true) {
            displayMenu();
            int choice = getIntInput("Choose an option: ");
            
            switch (choice) {
                case 1:
                    viewAllNotes();
                    break;
                case 2:
                    addNewNote();
                    break;
                case 3:
                    deleteNote();
                    break;
                case 4:
                    searchNotes();
                    break;
                case 5:
                    System.out.println("Thank you for using Notes App. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    // Display main menu
    private static void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. View All Notes");
        System.out.println("2. Add New Note");
        System.out.println("3. Delete Note");
        System.out.println("4. Search Notes");
        System.out.println("5. Exit");
    }

    // 1. VIEW ALL NOTES - Using BufferedReader
    private static void viewAllNotes() {
        System.out.println("\n--- All Notes ---");
        
        // Using try-with-resources to automatically close the file
        try (BufferedReader reader = new BufferedReader(new FileReader(NOTES_FILE))) {
            String line;
            int noteNumber = 1;
            
            while ((line = reader.readLine()) != null) {
                System.out.println(noteNumber + ". " + line);
                noteNumber++;
            }
            
            if (noteNumber == 1) {
                System.out.println("No notes found. Add some notes first!");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("No notes file found. Add your first note!");
        } catch (IOException e) {
            System.out.println("Error reading notes: " + e.getMessage());
        }
    }

    // 2. ADD NEW NOTE - Using FileWriter
    private static void addNewNote() {
        System.out.println("\n--- Add New Note ---");
        System.out.print("Enter your note: ");
        String newNote = scanner.nextLine();
        
        if (newNote.trim().isEmpty()) {
            System.out.println("Note cannot be empty!");
            return;
        }
        
        // Using FileWriter with append mode (true parameter)
        try (FileWriter writer = new FileWriter(NOTES_FILE, true)) {
            writer.write(newNote + "\n");
            System.out.println("Note added successfully!");
        } catch (IOException e) {
            System.out.println("Error saving note: " + e.getMessage());
        }
    }

    // 3. DELETE NOTE - Read all, exclude one, rewrite all
    private static void deleteNote() {
        viewAllNotes();
        
        List<String> notes = readAllNotes();
        if (notes.isEmpty()) {
            return;
        }
        
        int noteNumber = getIntInput("Enter note number to delete: ");
        
        if (noteNumber < 1 || noteNumber > notes.size()) {
            System.out.println("Invalid note number!");
            return;
        }
        
        // Remove the selected note
        String deletedNote = notes.remove(noteNumber - 1);
        
        // Rewrite all remaining notes to file
        try (FileWriter writer = new FileWriter(NOTES_FILE)) {
            for (String note : notes) {
                writer.write(note + "\n");
            }
            System.out.println("Note deleted: " + deletedNote);
        } catch (IOException e) {
            System.out.println("Error deleting note: " + e.getMessage());
        }
    }

    // 4. SEARCH NOTES
    private static void searchNotes() {
        System.out.println("\n--- Search Notes ---");
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();
        
        List<String> notes = readAllNotes();
        List<String> matchingNotes = new ArrayList<>();
        
        // Find matching notes
        for (String note : notes) {
            if (note.toLowerCase().contains(searchTerm)) {
                matchingNotes.add(note);
            }
        }
        
        // Display results
        if (matchingNotes.isEmpty()) {
            System.out.println("No notes found containing: " + searchTerm);
        } else {
            System.out.println("Found " + matchingNotes.size() + " matching note(s):");
            for (int i = 0; i < matchingNotes.size(); i++) {
                System.out.println((i + 1) + ". " + matchingNotes.get(i));
            }
        }
    }

    // HELPER METHODS

    // Read all notes into a List
    private static List<String> readAllNotes() {
        List<String> notes = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(NOTES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                notes.add(line);
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet - return empty list
        } catch (IOException e) {
            System.out.println("Error reading notes: " + e.getMessage());
        }
        
        return notes;
    }

    // Get integer input with validation
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }
}