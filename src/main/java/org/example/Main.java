package org.example;

import java.io.*;
import java.util.Scanner;

public class Main {
    // Define a variable to keep track of the number of entries in the array
    private static int numOfEntries = 0;
    // Opret Array til Student og deres tlf nr. med max 20 studenter.
    private static final String[] studentInfo = new String[20];

    public static void main(String[] args) {
        // Load from the file
        loadFromFile();
        // Interaction with the user:
        Scanner input = new Scanner(System.in);

        // While-loop med menu startup
        while (true) {
            menu();
            int choice = input.nextInt();
            input.nextLine(); // Consume the newline Character

            switch (choice) {
                // New Entry
                case 1:
                    addEntry();
                    break;

                // Show list
                case 2:
                    printList();
                    break;

                // Delete name/number
                case 3:
                    deleteStudent();
                    break;

                // exit the program
                case 4:
                    quit();
                    return;
            }
        }
    }

    // Menu Method
    private static void menu() {
        System.out.println("-----Welcome to the student phone number list-----");
        System.out.println("""
                1. for new entry
                2. for show list
                3. for delete name/number
                4. to quit
                """);
    }

    // New entry method
    private static void addEntry() {
        // Indlæser brugerens input
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Student name and phone number separated by space: ");
        // Laver en ny student med brugerens input.
        String entry = input.nextLine();
        studentInfo[numOfEntries++] = entry;
        saveToFile();
    }

    // Show list method
    public static void printList() {
        System.out.println("Student + Phone Number List:");
        for (int i= 0; i < numOfEntries; i++) {
            System.out.println(studentInfo[i]);
        }
    }

    // Delete name/number method
    private static void deleteStudent() {
        // Indlæser brugerens input
        Scanner input = new Scanner(System.in);
        printList();
        System.out.println("Enter the name/phone number to delete:");
        System.out.println("!Caution!: if there are two same name \nthen write only phone to safely delete the student you wish.");
        String searchKey = input.nextLine();
        boolean found = false;
        for (int i = 0; i < numOfEntries; i++) {
            /*
            'contains' for a part of the student to delete
            or
            'equalsIgnoreCase' for full info of the student to delete.
            */
            if (studentInfo[i].contains(searchKey)) {
                // Move subsequent entries up one position
                for (int j = i; j < numOfEntries - 1; j++) {
                    studentInfo[j] = studentInfo[j + 1];
                }
                numOfEntries--;
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Entry deleted successfully");
            saveToFile();
        } else {
            System.out.println("Entry not found.");
        }
    }

    // Quit method
    private static void quit() {
        System.out.println("The program died");
    }

    // Save to File
    private static void saveToFile() {
        String fileName = "NamePhoneNr.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (int i = 0; i < numOfEntries; i++) {
                writer.println(studentInfo[i]);
            }
            System.out.println("List saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving list to file: " + e.getMessage());
        }
    }
    // Load from File
    private static void loadFromFile() {
        String fileName = "NamePhoneNr.txt";
        try (Scanner reader = new Scanner(new File(fileName))) {
            int index = 0;
            while (reader.hasNextLine() && index < studentInfo.length) {
                studentInfo[index++] = reader.nextLine();
            }
            numOfEntries = index; // update the number of entries
            System.out.println("List loaded from file successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
// Other methods for testing:
//PrintList method test
/*
public static void printListTest() {
        // define the file name
        String fileName = "NamePhoneNr.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Read line from the file until the end
            while ((line = reader.readLine()) != null) {
                // print each line
                System.out.println(line);
            }
        } catch (IOException e) {
            // Handle any IO exceptions
            e.printStackTrace();
        }
    }
*/
