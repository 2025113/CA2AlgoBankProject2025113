/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2AlgoBankProject2025113;

/**
 *
 * @author Aliss
 */
public class EmployeeRecord implements Comparable<EmployeeRecord> {
    private final String name;
    private String managerType;
    private String department;

    public EmployeeRecord(String name, String managerType, String department) {
        this.name = name;
        this.managerType = managerType;
        this.department = department;
    }
    
    // We need this Getters
    public String getName() {
        return name;
    }

    public String getManagerType() {
        return managerType;
    }

    public String getDepartment() {
        return department;
    }

    // Implements the compareTo method for the Comparable interface. This is crucial for both Merge Sort and Binary Search.
    // Which require the list to be ordered based on the 'name' field.
    @Override
    public int compareTo(EmployeeRecord other) {
        return this.name.compareTo(other.name);
    }
    
    @Override
    public String toString() {
        return "Name: " + name + 
               " | Manager Type: " + managerType + 
               " | Department: " + department;
    }
}
