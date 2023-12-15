package com.aspiresys.supermarket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Filtration {
	static Scanner scanner = new Scanner(System.in);
	static String sqlData = "";

	void categories(String filterType) {
		if(filterType.equals("CategoryName")) {
			System.out.println(ReusableColors.blue +"Categories Listed Below"+ ReusableColors.defaultColor);
			sqlData = ReusableQueries.getProductCategory();
		}
		try {
			PreparedStatement preparedStatement = JdbcConnect.statement(sqlData);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(filterType.equals("CategoryName")) {
            	while(resultSet.next()) {
                	String CategoryData = resultSet.getString("product_category");
            		System.out.println(ReusableColors.green +CategoryData+ ReusableColors.defaultColor);
                }
            } 
		}catch (Exception e) {
			System.out.println( ReusableColors.red + " Error While Fetching Categories "+ e + ReusableColors.defaultColor);
		}
	}

	void filterProducts(String filterType) {
		System.out.println(ReusableColors.blue + "----------Product Filtration----------" + ReusableColors.defaultColor);

		String sql="", userRequestedCategory="", searchProductString="";
		double userRequestedDiscount = 0;
		
		int startPrice = 0, endPrice = 0;
		
		if(filterType.equals("CategoryName")) { 
			sql = ReusableQueries.getFilterProductCategory();
			categories(filterType);
			System.out.print(ReusableColors.blue + "Enter the Category Name : " + ReusableColors.defaultColor);
			userRequestedCategory = scanner.nextLine().toLowerCase();
		}
		
		if(filterType.equals("Price")) {
			sql = ReusableQueries.getProductPrice();
			System.out.print(ReusableColors.blue + "Enter the Starting Price : " + ReusableColors.defaultColor);
			startPrice = scanner.nextInt();
			System.out.print(ReusableColors.blue + "Enter the Ending Price : " + ReusableColors.defaultColor);
			endPrice = scanner.nextInt();
		}
		
		if(filterType.equals("Discount")) { 
			sql = ReusableQueries.getProductDiscount();
			String resultData = ReusablePages.userDiscountFiltrationOptionSelection();
			userRequestedDiscount = Double.parseDouble(resultData);
		}
		
		if(filterType.equals("ProductName")) {
			sql = ReusableQueries.getFitlerProductName();
			System.out.print(ReusableColors.blue + "Enter the Product Name to Search : " + ReusableColors.defaultColor);
			searchProductString = scanner.nextLine().toLowerCase();
		}
		
		try {
			PreparedStatement preparedStatement = JdbcConnect.statement(sql);
			if(filterType.equals("CategoryName")) {
				preparedStatement.setString(1, userRequestedCategory);
			}
			if(filterType.equals("Price")) {
				preparedStatement.setInt(1, startPrice);
				preparedStatement.setInt(2, endPrice);
			}

			if(filterType.equals("Discount")) {
				preparedStatement.setDouble(1, userRequestedDiscount);
			}
		
			if(filterType.equals("ProductName")) {
				preparedStatement.setString(1, searchProductString);
			}
			
			ResultSet resultSet = preparedStatement.executeQuery();

			System.out.printf(
				    ReusableColors.backgroundWhite + ReusableColors.red + "%-10s %-20s %-20s %-20s %-15s %-15s %-15s %-15s\n",
				    "ProductID", "Product Name", "Product Category", "Product Quantity", "Product Price", "Original Price", "Discount" ,"Product Stock" + ReusableColors.defaultColor
				);

			while (resultSet.next()) {
				int productId = resultSet.getInt("product_id");
				String productName = resultSet.getString("product_name");
				String productCategory = resultSet.getString("product_category");
				String productQuantity = resultSet.getString("product_quantity");
				int productPrice = resultSet.getInt("product_price");
				int productOriginalPrice = resultSet.getInt("product_original_price");
				String productDiscount = resultSet.getString("product_discount");
				int productStock = resultSet.getInt("product_stock");

				System.out.printf("%-10s %-20s %-20s %-20s %-15s %-15s %-15s %-15s\n", productId, productName, productCategory, productQuantity, productPrice, productOriginalPrice, productDiscount, productStock);

			}
			System.out.printf("----------------");

		} catch (SQLException e) {
			System.out.println(ReusableColors.red + " Error While Fetching Products " + e + ReusableColors.defaultColor);
		}
		ReusablePages.userProductPageOptionSelection();
	}
}

public class Products extends Filtration {
	
	void displayAllProducts() {
		System.out.println(ReusableColors.blue + " Products " + ReusableColors.defaultColor);
		String sql = ReusableQueries.getDisplayproductsquery();
		ReusablePages.reusableProductDisplay(sql);
	}

	void displayProducts() {
		displayAllProducts();
		ReusablePages.userProductPageOptionSelection();
	}

	public static void main(String[] args) {
		Products products = new Products();
		products.filterProducts("");
	}
}
