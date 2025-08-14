package com.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeApp {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		int choice;
		do {
			System.out.println("=== Employee Database Menu ===");
			System.out.println("1. Add Employee");
			System.out.println("2. View All Employees");
			System.out.println("3. Update Employee");
			System.out.println("4. Delete Employee");
			System.out.println("5. Exit");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			sc.nextLine(); // consume newline

			switch (choice) {
			case 1:
				addEmployee();
				break;
			case 2:
				viewEmployees();
				break;
			case 3:
				updateEmployee();
				break;
			case 4:
				deleteEmployee();
				break;
			case 5:
				System.out.println("Exiting... Goodbye!");
				break;
			default:
				System.out.println("Invalid choice. Try again.");
			}
		} while (choice != 5);

	}

	private static void addEmployee() {
		System.out.print("Enter Name: ");
		String name = sc.nextLine();
		System.out.print("Enter Position: ");
		String position = sc.nextLine();
		System.out.print("Enter Salary: ");
		double salary = sc.nextDouble();

		String sql = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, name);
			ps.setString(2, position);
			ps.setDouble(3, salary);

			int rows = ps.executeUpdate();
			if (rows > 0) {
				System.out.println("Employee added successfully.");
			}

		} catch (SQLException e) {
			System.out.println("Error adding employee: " + e.getMessage());
		}
	}

	private static void viewEmployees() {
		String sql = "SELECT * FROM employees";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			System.out.println("=== Employee List ===");
			while (rs.next()) {
				System.out.printf("ID: %d | Name: %s | Position: %s | Salary: %.2f%n", rs.getInt("id"),
						rs.getString("name"), rs.getString("position"), rs.getDouble("salary"));
			}

		} catch (SQLException e) {
			System.out.println("Error retrieving employees: " + e.getMessage());
		}
	}

	private static void updateEmployee() {
		System.out.print("Enter Employee ID to Update: ");
		int id = sc.nextInt();
		sc.nextLine();

		System.out.print("Enter New Name: ");
		String name = sc.nextLine();
		System.out.print("Enter New Position: ");
		String position = sc.nextLine();
		System.out.print("Enter New Salary: ");
		double salary = sc.nextDouble();

		String sql = "UPDATE employees SET name = ?, position = ?, salary = ? WHERE id = ?";

		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, name);
			ps.setString(2, position);
			ps.setDouble(3, salary);
			ps.setInt(4, id);

			int rows = ps.executeUpdate();
			if (rows > 0) {
				System.out.println("Employee updated successfully.");
			} else {
				System.out.println("Employee ID not found.");
			}

		} catch (SQLException e) {
			System.out.println("Error updating employee: " + e.getMessage());
		}
	}

	private static void deleteEmployee() {
		System.out.print("Enter Employee ID to Delete: ");
		int id = sc.nextInt();

		String sql = "DELETE FROM employees WHERE id = ?";

		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);

			int rows = ps.executeUpdate();
			if (rows > 0) {
				System.out.println("Employee deleted successfully.");
			} else {
				System.out.println("Employee ID not found.");
			}

		} catch (SQLException e) {
			System.out.println("Error deleting employee: " + e.getMessage());
		}
	}

}
