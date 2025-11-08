/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2AlgoBankProject2025113;

/**
 *
 * @author Aliss
 */
public enum MenuOption {
    SORT(1, "SORT"),
    SEARCH(2, "SEARCH"),
    ADD_RECORDS(3, "ADD RECORDS"),
    CREATE_BINARY_TREE(4, "Create a binary tree"),
    EXIT(5, "EXIT");

    private final int value;
    private final String description;

    // Constructor
    MenuOption(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    // Method to convert inpunt in Enum
    public static MenuOption fromValue(int value) {
        for (MenuOption option : MenuOption.values()) {
            if (option.value == value) {
                return option;
            }
        }
        return null; // Invalid Input
    }
}
