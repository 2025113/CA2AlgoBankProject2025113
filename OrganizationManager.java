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
import java.util.LinkedList;
import java.util.Queue;

public class OrganizationManager {

    // The main list to store employee records. It must be sorted for Binary Search efficiency.
    private static List<Employee> employeeList = new ArrayList<>();

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
                    employeeList.add(new Employee(line.trim(), "Unknown", "Unknown"));
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
        List<Employee> sortedList = new ArrayList<>(employeeList);

        System.out.println("\nStarting Recursive Merge Sort...");
        
        // 2. Call the recursive sorting method.
        // We update the main employeeList with the newly sorted version.
        employeeList = mergeSort(sortedList); 
        
        System.out.println("Merge Sort completed!");
        
        // 3. Display the first 20 names (Assignment requirement for the SORT function).
        System.out.println("\n--- Alphabetically Sorted List (First 20) ---");
        int count = 0;
        for (Employee record : employeeList) {
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
    private static List<Employee> mergeSort(List<Employee> list) {
        // BASE CASE: Recursion stops when the list has 0 or 1 element.
        if (list.size() < 2) {
            return list; 
        }

        // Division: Splits the list into two halves.
        int mid = list.size() / 2;
        
        List<Employee> left = new ArrayList<>(list.subList(0, mid));
        List<Employee> right = new ArrayList<>(list.subList(mid, list.size()));
        
        // Recursion (Conquer): Calls itself to sort the halves.
        left = mergeSort(left); 
        right = mergeSort(right); 
        
        // Merge: Combines the two sorted sub-lists efficiently.
        return merge(left, right);
    }

    // The Merge method: Combines two already sorted lists into a single sorted list.
    // This function is essential to the Merge Sort's efficiency, as it combines two already sorted sub-lists into a single fully sorted list in O(n) time.
    // This ensures the overall O(n log n) complexity is maintained.
    private static List<Employee> merge(List<Employee> left, List<Employee> right) {
        List<Employee> result = new ArrayList<>();
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
            Employee foundRecord = employeeList.get(index);
            System.out.println("\n--- Record Found (Binary Search) ---");
            System.out.println(foundRecord.toString()); 
            System.out.println("-------------------------------------------");
        } else {
            System.out.println("Person '" + searchName + "' not found in the list.");
        }
    }

    // The recursive Binary Search algorithm. It relies on the list being previously sorted (by Merge Sort) to halve
    // the search space with each recursive call, achieving O(log n) performance
    private static int binarySearch(List<Employee> list, String targetName, int low, int high) {
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

    // Implementation Add Register
    private static void addRecord(Scanner scanner) {
        System.out.println("\n--- ADD NEW EMPLOYEE RECORD ---");

        // Get the name
        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine().trim();

        // Get and valid Manager Type
        String managerType = getValidInput(scanner, "Enter Manager Type (" + VALID_MANAGERS + "): ", VALID_MANAGERS);

        // Get and valid Department
        String department = getValidInput(scanner, "Enter Department (" + VALID_DEPARTMENTS + "): ", VALID_DEPARTMENTS);

        // Create and add the register
        if (!name.isEmpty()) {
            Employee newRecord = new Employee(name, managerType, department);
            employeeList.add(newRecord);
            // The list should be reordered to keep the Binary Search working
            sortList();
            
            System.out.println("New record added and list re-sorted successfully:");
            System.out.println(newRecord.toString());
        } else {
            System.out.println("Error: Employee name cannot be empty. Record not added.");
        }
    }

    // Check if the user input is acording o the value list
    private static String getValidInput(Scanner scanner, String prompt, List<String> validList) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            
            if (validList.stream().anyMatch(val -> val.equalsIgnoreCase(input))) {
                // Return the value 
                return validList.stream().filter(val -> val.equalsIgnoreCase(input)).findFirst().get();
            } else {
                System.out.println("Invalid input. Must be one of: " + validList);
            }
        }
    }
    // Global variable to store the Hierarchy Binary Tree
    private static TreeNode root;

    // Method to create the Hierarchy Employee
    private static void createHierarchy() {
        if (employeeList.size() < 20) {
            System.out.println("Warning: Need at least 20 records to build the hierarchy (Current: " + employeeList.size() + ").");
            System.out.println("Please add more records or ensure the file was read correctly.");
            return;
        }

        root = null; // Restart the tree
        System.out.println("\n--- Building Employee Hierarchy (Binary Tree) ---");
        
        // Here we use the actual list of employees to build the tree
        // 20 register at least
        
        for (int i = 0; i < 20; i++) {
            Employee record = employeeList.get(i);
            root = insertLevelOrder(root, record);
        }
        
        System.out.println("\n--- Level-Order Traversal (Breadth-First Display) ---");
        levelOrderTraversal(root);
        
        int height = getHeight(root);
        int count = countNodes(root);
        
        System.out.println("\n--- Tree Metrics ---");
        System.out.println("Tree Height (Max Levels): " + height);
        System.out.println("Total Node Count: " + count);
        System.out.println("--------------------");

}
    private static void levelOrderTraversal(TreeNode root) {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            System.out.print("[" + temp.data.getName() + " - " + temp.data.getManagerType() + "]");
            
            if (temp.left != null) {
                queue.add(temp.left);
            }
            if (temp.right != null) {
                queue.add(temp.right);
            }
            System.out.print(" | ");
        }
        System.out.println();
    }
    
    private static int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }
    private static int countNodes(TreeNode root) {
        if (root == null) {
        return 0;
        }
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
    
    
     // Insert a new node in Binary Tree using level-order (breadth-first).
     // This will garantee the tree keep balanced 
    
    private static TreeNode insertLevelOrder(TreeNode root, Employee data) {
        if (root == null) {
            return new TreeNode(data);
        }

        // We use a Queue to track nodes in level-order
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        // While has node on queue
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();

            // Inster to left
            if (temp.left == null) {
                temp.left = new TreeNode(data);
                return root;
            } else {
                queue.add(temp.left);
            }

            // Insert to right
            if (temp.right == null) {
                temp.right = new TreeNode(data);
                return root;
            } else {
                queue.add(temp.right);
            }
        }
        return root;
    }
}
