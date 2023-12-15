package com.aspiresys.supermarket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddProduct {
	private static String productName, productCategory, productQuantity, discount;
	private static int productPrice, productOriginalPrice, productStock;

	private static final String PRODUCT_NAME_PATTERN = "^[A-Za-z\\s]+$";
	private static final String CATEGORY_PATTERN = "^[A-Za-z]+$";
	private static final String QUANTITY_PATTERN = "^[A-Za-z0-9\\s-]+$";
	private static final String PRICE_PATTERN = "^[1-9][0-9]*$";
	private static final String STOCK_PATTERN = "^[0-9]+$";

	private static Scanner scanner = new Scanner(System.in);

	public boolean dataValidation(String data, String regexData, String errorMessage) {
		Pattern regexPattern = Pattern.compile(regexData);
		Matcher matcher = regexPattern.matcher(data);
		if (!matcher.matches()) {
			System.out.println(ReusableColors.red + errorMessage + ReusableColors.defaultColor);
			return false;
		}
		return true;
	}

	public String validateData(String data, String regexPattern, String errorMessage) {
		String value;
		do {
			System.out.print(data);
			value = scanner.nextLine();
		} while (!dataValidation(value, regexPattern, errorMessage));
		return value;
	}
	
	public void validateProductName(String data, String regexPattern, String errorMessage) {
		String dataResult = validateData(data, regexPattern, errorMessage); 
		if(!dataResult.isEmpty()) {
			try {
				String sql = AdminReusablePages.getProductNameQuery();
		        PreparedStatement preparedEmailStatement = JdbcConnect.statement(sql);
		 		preparedEmailStatement.setString(1, dataResult);
		 		ResultSet resultSet = preparedEmailStatement.executeQuery();

		 		if(resultSet.next()) {
					System.out.println(ReusableColors.red + " Product Already Exist "+ReusableColors.defaultColor);

					try {
						String userSelected = AdminReusablePages.adminSelection();

						if(userSelected.equals("Continue")) {
							validateProductName(data, regexPattern, errorMessage);
							dataResult="";
						}
					} catch (Exception e) {
						System.out.println( ReusableColors.red + " Error "+ e + ReusableColors.defaultColor);
					}
		 		}else {
		 			setProductName(dataResult);
		 		}
			} catch (Exception e) {
				System.out.println( ReusableColors.red + " Error "+ e + ReusableColors.defaultColor);
				addProduct();
			} 
		}
	}

	void calculateDiscount(int price, int original_price) {
		double result = (double) (original_price - price) / original_price * 100;
		int resultDiscount = (int) Math.round(result);
		setDiscount( Integer.toString(resultDiscount) + "%" );
	}

	void addProduct() {
		System.out.println(ReusableColors.green + "Add Product " + ReusableColors.defaultColor);

		validateProductName("Enter Product Name : ", getProductnamepattern(),
				"Invalid Product Name. Enter Only Alphabets");
		String productCategoryString = validateData("Enter Category : ", getCategorypattern(),
				"Invalid Category. Enter Only Alphabets");
		String productQuantityString = validateData("Enter Product Quantity : ", getQuantitypattern(),
				"Invalid Quantity. Enter Only Alphabets and Numbers. No Special Characters are Allowed");
		String productPriceData = validateData("Enter Product Price : ", getPricepattern(),
				"Invalid Product Price. Enter Only Positive Numbers. Only Numbers are Allowed");
		String productOriginalPriceData = validateData("Enter Product Original Price : ", getPricepattern(),
				"Invalid Product Price. Enter Only Positive Numbers. Only Numbers are Allowed");
		String productStockData = validateData("Enter Product Stock : ", getStockpattern(),
				"Invalid Product Stock. Enter Only Numbers");

		if (!productCategoryString.trim().isEmpty()
				&& !productQuantityString.trim().isEmpty() && !productPriceData.toString().trim().isEmpty()
				&& !productOriginalPriceData.toString().trim().isEmpty()
				&& !productStockData.toString().trim().isEmpty()) {

			String sql = AdminReusablePages.getAddproductquery();
			setProductCategory(productCategoryString);
			setProductPrice(Integer.parseInt(productPriceData));
			setProductOriginalPrice(Integer.parseInt(productOriginalPriceData));
			setProductQuantity(productQuantityString);
			setProductStock(Integer.parseInt(productStockData));

			calculateDiscount(getProductPrice(), getProductOriginalPrice());

			try {
				PreparedStatement preparedStatement = JdbcConnect.statement(sql);
				preparedStatement.setString(1, getProductName());
				preparedStatement.setString(2, getProductCategory());
				preparedStatement.setString(3, getProductQuantity());
				preparedStatement.setLong(4, getProductPrice());
				preparedStatement.setLong(5, getProductOriginalPrice());
				preparedStatement.setString(6, getDiscount());
				preparedStatement.setLong(7, getProductStock());

				int resultPreparedStatement = preparedStatement.executeUpdate();

				if (resultPreparedStatement > 0) {
					System.out.println(ReusableColors.green + "Product Added Successfully" + ReusableColors.defaultColor);
					preparedStatement.close();
					AdminReusablePages.adminPanelOptionSelection();
				} else {
					System.out.println(ReusableColors.red + "Error Occured Adding Product" + ReusableColors.defaultColor);
				}

			} catch (Exception e) {
				System.out.println(ReusableColors.red + " Already Product Exist " + e + ReusableColors.defaultColor);
				String optionSelected =  AdminReusablePages.adminSelection();
				if(optionSelected.equals("Continue")) { addProduct();  }
			}
		}
	}

	public static String getProductnamepattern() {
		return PRODUCT_NAME_PATTERN;
	}

	public static String getCategorypattern() {
		return CATEGORY_PATTERN;
	}

	public static String getQuantitypattern() {
		return QUANTITY_PATTERN;
	}

	public static String getPricepattern() {
		return PRICE_PATTERN;
	}

	public static String getStockpattern() {
		return STOCK_PATTERN;
	}

	public static String getProductName() {
		return productName;
	}

	public static void setProductName(String productName) {
		AddProduct.productName = productName;
	}

	public static String getProductCategory() {
		return productCategory;
	}

	public static void setProductCategory(String productCategory) {
		AddProduct.productCategory = productCategory;
	}

	public static String getProductQuantity() {
		return productQuantity;
	}

	public static void setProductQuantity(String productQuantity) {
		AddProduct.productQuantity = productQuantity;
	}

	public static int getProductPrice() {
		return productPrice;
	}

	public static void setProductPrice(int productPrice) {
		AddProduct.productPrice = productPrice;
	}

	public static int getProductOriginalPrice() {
		return productOriginalPrice;
	}

	public static void setProductOriginalPrice(int productOriginalPrice) {
		AddProduct.productOriginalPrice = productOriginalPrice;
	}

	public static int getProductStock() {
		return productStock;
	}

	public static void setProductStock(int productStock) {
		AddProduct.productStock = productStock;
	}
	
	public static String getDiscount() {
		return discount;
	}

	public static void setDiscount(String discount) {
		AddProduct.discount = discount;
	}

	public static void main(String[] args) {
		AddProduct addProduct = new AddProduct();
		addProduct.addProduct();
	}
}
