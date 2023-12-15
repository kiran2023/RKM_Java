package com.aspiresys.supermarket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddToCart extends Products {

	private static final String QUANTITY_PATTERN = "^[0-9]+$";

	void cartOptions() {
		System.out
				.println(ReusableColors.blue + "1. Add Product to Cart. \n2. View Cart" + ReusableColors.defaultColor);
		System.out.print("Your Option : ");

		Scanner scanner = new Scanner(System.in);
		String userSelectedString = scanner.nextLine();

		if (userSelectedString.equals("1")) {
			addToCart();
		} else if (userSelectedString.equals("2")) {
			viewCart();
		} else {
			System.out.println(ReusableColors.red + " Enter Only Valid Option 1/2 " + ReusableColors.defaultColor);
		}
	}

	void viewCart() {
		System.out.println(ReusableColors.blue + "----------Your Cart----------" + ReusableColors.defaultColor);
		try {
			String sql = ReusableQueries.getCartItemsFetch();
			PreparedStatement preparedStatement = JdbcConnect.statement(sql);
			preparedStatement.setInt(1, Login.getLoggedinUserId());
			ResultSet resultSet = preparedStatement.executeQuery();
			ReusablePages.resultSetCartDataDisplay(resultSet);
			ReusablePages.userProductPageOptionSelection();
		} catch (Exception e) {
			System.out.println(ReusableColors.red + " Error " + e + ReusableColors.defaultColor);
			ReusablePages.userProductPageOptionSelection();
		}
	}

	void addToCart() {
		Scanner scanner = new Scanner(System.in);
		displayAllProducts();
		System.out.print("Enter Product ID to ADD PRODUCT to CART : ");
		int productID = scanner.nextInt();

		try {
			String sql = ReusableQueries.getDisplayProductsId();
			PreparedStatement preparedStatement = JdbcConnect.statement(sql);
			preparedStatement.setInt(1, productID);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.printf(
					ReusableColors.backgroundWhite + ReusableColors.red
							+ "%-10s %-20s %-20s %-20s %-15s %-15s %-15s %-15s\n",
					"ProductID", "Product Name", "Product Category", "Product Quantity", "Product Price",
					"Original Price", "Discount", "Product Stock" + ReusableColors.defaultColor);
			while (resultSet.next()) {

				int productId = resultSet.getInt("product_id");
				String productName = resultSet.getString("product_name");
				String productCategory = resultSet.getString("product_category");
				String productQuantity = resultSet.getString("product_quantity");
				int productPrice = resultSet.getInt("product_price");
				int productOriginalPrice = resultSet.getInt("product_original_price");
				String productDiscount = resultSet.getString("product_discount");
				int productStock = resultSet.getInt("product_stock");
				System.out.printf("%-10s %-20s %-20s %-20s %-15s %-15s %-15s %-15s\n", productId, productName,
						productCategory, productQuantity, productPrice, productOriginalPrice, productDiscount,
						productStock);

				String resulData = ReusablePages.userAddToCartOptionSelection();

				if (resulData.equals("Continue")) {
					System.out.print("Enter Product Quantity : ");
					int quantity = scanner.nextInt();
					Pattern regexPattern = Pattern.compile(getQuantityPattern());
					Matcher matcher = regexPattern.matcher(Integer.toString(quantity));
					if (matcher.matches()) {
						String sqlString = ReusableQueries.getCartInsert();
						try {
							PreparedStatement preparedStatement1 = JdbcConnect.statement(sqlString);
							preparedStatement1.setInt(1, Login.getLoggedinUserId());
							preparedStatement1.setString(2, productName);
							preparedStatement1.setInt(3, quantity);
							preparedStatement1.setInt(4, productPrice);
							preparedStatement1.setInt(5, quantity * productPrice);

							int resultPreparedStatement = preparedStatement1.executeUpdate();

							if (resultPreparedStatement > 0) {
								System.out.println(ReusableColors.green + "Product Added to Cart Successfully"
										+ ReusableColors.defaultColor);
								preparedStatement1.close();
								ReusablePages.products();
							} else {
								System.out.println(ReusableColors.red + "Error Occured Adding Product"
										+ ReusableColors.defaultColor);
							}

						} catch (Exception e) {
							System.out.println(ReusableColors.red + " Error " + e + ReusableColors.defaultColor);
							addToCart();
						}
					} else {
						System.out.println("Enter Valid Data. Only Number is Allowed");
						addToCart();
					}
				}
			}
		} catch (SQLException e) {
			System.out
					.println(ReusableColors.red + " Error While Fetching Products " + e + ReusableColors.defaultColor);
			addToCart();

		}

	}

	public static String getQuantityPattern() {
		return QUANTITY_PATTERN;
	}

	public static void main(String[] args) {}
}
