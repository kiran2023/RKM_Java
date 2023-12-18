package com.aspiresys.supermarket;

import java.sql.*;

public class JdbcConnect {
	
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/supermarket_data";
	private static final String NAME = "root";
	private static final String PASSWORD = "";
	private static Connection connection;

	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		JdbcConnect.connection = connection;
	}

	public static void connectToDatabase() {
		try {
			if(connection==null || connection.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				connection = DriverManager.getConnection(URL, NAME, PASSWORD);
			}
		} catch (Exception e) {
			System.out.println( ReusableColors.red + " Error "+ e + ReusableColors.defaultColor);
		}
	}
	
	public static void closeDatabaseConnection() {
		try {
			if(connection!=null || !connection.isClosed()) {
				connection.close();
			}
		} catch (Exception e) {
			System.out.println( ReusableColors.red + " Error "+ e + ReusableColors.defaultColor);
		}
	}
	
	public static PreparedStatement statement(String sql) throws SQLException {
		connectToDatabase();
		return connection.prepareStatement(sql);
	}
	
	public static void main(String[] args) {

	}
}
