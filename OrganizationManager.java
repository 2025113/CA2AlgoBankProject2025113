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

    // The main list to store employee records. It must be sorted for Binary Search efficiency.
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
                            sortList();
                            break;
                        case SEARCH:
                            searchList(scanner);
                            break;
                        case ADD_RECORDS:
                            addRecord(scanner);
                            break;
                        case CREATE_BINARY_TREE:
                            createHierarchy();
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

    // The main entry method for the sorting functionality.
    // Merge Sort was chosen as the required RECURSIVE algorithm because it is stable
    // and guarantees an O(n log n) time complexity in all cases (best, average, worst).
    private static void sortList() {
        if (employeeList.isEmpty()) {
            System.out.println("The list is empty. Please load or add records first.");
            return;
        }

        // 1. Create a copy of the list to be sorted (to avoid modifying the list during recursion)
        List<EmployeeRecord> sortedList = new ArrayList<>(employeeList);

        System.out.println("\nStarting Recursive Merge Sort...");
        
        // 2. Call the recursive sorting method.
        // We update the main employeeList with the newly sorted version.
        employeeList = mergeSort(sortedList); 
        
        System.out.println("Merge Sort completed!");
        
        // 3. Display the first 20 names (Assignment requirement for the SORT function).
        System.out.println("\n--- Alphabetically Sorted List (First 20) ---");
        int count = 0;
        for (EmployeeRecord record : employeeList) {
            if (count >= 20) {
                break;
            }
            // We display ONLY the name for the SORT output:
            System.out.println((count + 1) + ". " + record.getName()); 
            count++;
        }
        System.out.println("--- End of 20 records ---");
    }

    // Algoritmo Recursivo de Merge Sort. Implements the "Divide and Conquer" strategy.
    private static List<EmployeeRecord> mergeSort(List<EmployeeRecord> list) {
        // BASE CASE: Recursion stops when the list has 0 or 1 element.
        if (list.size() < 2) {
            return list; 
        }

        // Division: Splits the list into two halves.
        int mid = list.size() / 2;
        
        List<EmployeeRecord> left = new ArrayList<>(list.subList(0, mid));
        List<EmployeeRecord> right = new ArrayList<>(list.subList(mid, list.size()));
        
        // Recursion (Conquer): Calls itself to sort the halves.
        left = mergeSort(left); 
        right = mergeSort(right); 
        
        // Merge: Combines the two sorted sub-lists efficiently.
        return merge(left, right);
    }

    // The Merge method: Combines two already sorted lists into a single sorted list.
    private static List<EmployeeRecord> merge(List<EmployeeRecord> left, List<EmployeeRecord> right) {
        List<EmployeeRecord> result = new ArrayList<>();
        int i = 0; // Index for 'left'
        int j = 0; // Index for 'right'

        while (i < left.size() && j < right.size()) {
            // Compares using the compareTo() from EmployeeRecord (orders by name)
            if (left.get(i).compareTo(right.get(j)) <= 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        // Add any remaining elements (always sorted)
        result.addAll(left.subList(i, left.size()));
        result.addAll(right.subList(j, right.size()));

        return result;
    }
    
    // Entry method for Binary Search.
    // Binary Search was chosen for its O(log n) efficiency, as it is the fastest 
    // algorithm for searching within an already sorted list.
    private static void searchList(Scanner scanner) {
        if (employeeList.isEmpty()) {
            System.out.println("The list is empty. Cannot search.");
            return;
        }

        System.out.print("Enter the name of the person to search: ");
        String searchName = scanner.nextLine().trim();

        // Call to the recursive search algorithm.
        int index = binarySearch(employeeList, searchName, 0, employeeList.size() - 1);

        if (index != -1) {
            EmployeeRecord foundRecord = employeeList.get(index);
            System.out.println("\n--- Record Found (Binary Search) ---");
            System.out.println(foundRecord.toString()); 
            System.out.println("-------------------------------------------");
        } else {
            System.out.println("Person '" + searchName + "' not found in the list.");
        }
    }

    // The recursive Binary Search algorithm.
    private static int binarySearch(List<EmployeeRecord> list, String targetName, int low, int high) {
        // BASE CASE 1: The search interval is invalid (element not found).
        if (low > high) {
            return -1; 
        }

        int mid = low + (high - low) / 2;
        int comparison = targetName.compareTo(list.get(mid).getName());

        if (comparison == 0) {
            return mid; // BASE CASE 2: Element found at the midpoint.
        } else if (comparison < 0) {
            return binarySearch(list, targetName, low, mid - 1); // Search left half
        } else {
            return binarySearch(list, targetName, mid + 1, high); // Search right half
        }
    }

    // Placeholders for future commits
    private static void addRecord(Scanner scanner) {
        // Implementation for Commit N.º 8, 9, 10
        System.out.println("ADD RECORDS functionality to be implemented.");
    }

    private static void createHierarchy() {
        // Implementation for Commit N.º 11, 12, 13
        System.out.println("CREATE BINARY TREE functionality to be implemented.");
    }
}
