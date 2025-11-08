/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package CA2AlgoBankProject2025113;

/**
 *
 * @author Aliss
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrganizationManager {

    // Main List will save the data 
    private static List<EmployeeRecord> employeeList = new ArrayList<>();

    // Statistics Variables to Validation (based on our choose, Bank)
    private static final List<String> VALID_MANAGERS = List.of("CEO", "Senior Manager", "Branch Manager", "Team Lead", "Relationship Manager", "Security Officer");
    private static final List<String> VALID_DEPARTMENTS = List.of("IT", "HR", "Finance", "Compliance", "Customer Service", "Treasury", "Credit Analysis", "Marketing");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.print("Please enter the filename to read: ");
        String filename = scanner.nextLine();
        
        // 1. Read the file
        if (readFile(filename)) {
            System.out.println("File read successfully. " + employeeList.size() + " records loaded.");
        } else {
            System.out.println("Error reading file or file is empty. Exiting.");
            return;
        }

        // Principal Loop Menu
        while (running) {
            displayMenu();
            
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                MenuOption selectedOption = MenuOption.fromValue(choice);
                scanner.nextLine(); 

                if (selectedOption != null) {
                    switch (selectedOption) {
                        case SORT:
                            System.out.println("SORT selected (Feature to be implemented).");
                            // sortList(); 
                            break;
                        case SEARCH:
                            System.out.println("SEARCH selected (Feature to be implemented).");
                            // searchList(scanner);
                            break;
                        case ADD_RECORDS:
                            System.out.println("ADD RECORDS selected (Feature to be implemented).");
                            // addRecord(scanner);
                            break;
                        case CREATE_BINARY_TREE:
                            System.out.println("Create a binary tree selected (Feature to be implemented).");
                            // createHierarchy();
                            break;
                        case EXIT:
                            System.out.println("Exiting application. Goodbye!");
                            running = false;
                            break;
                    }
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the buffer
            }
        }
        scanner.close();
    }

    //  Here we have methods of inicial support  

    private static void displayMenu() {
        System.out.println("\nDo You wish to SORT or SEARCH:");
        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getValue() + ". " + option.getDescription());
        }
    }
    
    private static boolean readFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    // Add a EmployeeRecord with Manager/Dept 
                    employeeList.add(new EmployeeRecord(line.trim(), "Unknown", "Unknown"));
                }
            }
            return !employeeList.isEmpty();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.err.println("Ensure '" + filename + "' is in the project root directory.");
            return false;
        }
    }

    // The functionality methods (sortList, searchList, addRecord, createHierarchy) will be here.
}
