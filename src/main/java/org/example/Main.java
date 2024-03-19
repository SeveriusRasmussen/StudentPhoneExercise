package org.example;
// Finally that's over...? Frodo
import java.io.*;
import java.util.Scanner;

public class Main {
    private static final int MAX_STUDENTS = 20;
    private static String[] studentNames = new String[MAX_STUDENTS];
    private static String[] phoneNumbers = new String[MAX_STUDENTS];
    private static int numStudents = 0;

    public static void main(String[] args) {
        loadFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addEntry(scanner);
                    break;
                case 2:
                    printList();
                    break;
                case 3:
                    deleteEntry(scanner);
                    break;
                case 4:
                    saveToFile();
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("----- Student Phone List Menu -----");
        System.out.println("1. Add new entry");
        System.out.println("2. Show list");
        System.out.println("3. Delete entry");
        System.out.println("4. Quit");
        System.out.print("Enter your choice: ");
    }

    private static void addEntry(Scanner scanner) {
        if (numStudents >= MAX_STUDENTS) {
            System.out.println("Sorry, the list is full.");
            return;
        }

        System.out.print("Enter student's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student's phone number: ");
        String phoneNumber = scanner.nextLine();

        // Check for the first available index
        int index = findFirstAvailableIndex();
        if (index == -1) {
            System.out.println("No available index found.");
            return;
        }

        studentNames[index] = name;
        phoneNumbers[index] = phoneNumber;
        numStudents++;

        System.out.println("Entry added successfully.");
    }

    private static int findFirstAvailableIndex() {
        for (int i = 0; i < MAX_STUDENTS; i++) {
            if (studentNames[i] == null) {
                return i;
            }
        }
        return -1; // No available index found
    }

    private static void printList() {
        if (numStudents == 0) {
            System.out.println("The list is empty.");
            return;
        }

        System.out.println("----- Student Phone List -----");
        for (int i = 0; i < numStudents; i++) {
            if (studentNames[i] != null) {
                System.out.printf("Name: %s%nPhone number: %s%n%n", studentNames[i], phoneNumbers[i]);

            }
        }
    }

    private static void deleteEntry(Scanner scanner) {
        if (numStudents == 0) {
            System.out.println("The list is empty.");
            return;
        }

        printList();
        System.out.print("Enter the index of the entry to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (index < 0 || index >= numStudents) {
            System.out.println("Invalid index.");
            return;
        }

        // Set the deleted student's name and phone number to null
        studentNames[index] = null;
        phoneNumbers[index] = null;

        System.out.println("Entry deleted successfully.");
    }

    private static void saveToFile() {
        String fileName = "student_phone_list.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (int i = 0; i < numStudents; i++) {
                writer.printf("%s,%s%n", studentNames[i], phoneNumbers[i]);
            }
            System.out.println("List saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving list to file: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        String fileName = "student_phone_list.txt";
        try (Scanner reader = new Scanner(new File(fileName))) {
            while (reader.hasNextLine() && numStudents < MAX_STUDENTS) {
                String line = reader.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    studentNames[numStudents] = parts[0];
                    phoneNumbers[numStudents] = parts[1];
                    numStudents++;
                }
            }
            System.out.println("List loaded from file successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}