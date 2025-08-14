package com.employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static final String URL = "jdbc:mysql://localhost:3306/employee_db";
	private static final String USER = "YOUR_DATABASE_USERNAME"; 
	private static final String PASSWORD = "YOUR_DATABASE_PASSWORD"; 

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found.");
		} catch (SQLException e) {
			System.out.println("Database connection failed: " + e.getMessage());
		}
		return con;
	}
}
