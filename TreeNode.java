/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA2AlgoBankProject2025113;

/**
 *
 * @author Aliss
 */
public class TreeNode {
    EmployeeRecord data; // The Node store one object EmployeeRecord
    TreeNode left;
    TreeNode right;

    public TreeNode(EmployeeRecord data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
