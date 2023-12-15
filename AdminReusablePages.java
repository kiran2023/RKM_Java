package com.aspiresys.supermarket;

import java.util.HashMap;
import java.util.Scanner;

public class AdminReusablePages {

	private static final String ADD_PRODUCT_QUERY = "insert into products (product_name, product_category, product_quantity, product_price, product_original_price, product_discount, product_stock) values(?, ?, ?, ?, ?, ?, ?)";
	private static final String PRODUCT_NAME_QUERY = "Select product_name from products where product_name = ? ";

	public static final HashMap<String, String> ADMIN_SELECTION = new HashMap<>() {
		{
			put("1", "Continue");
			put("2", "Admin Panel");
		}
	};

	public static final HashMap<String, String> ADMIN_PANEL_OPTIONS = new HashMap<>() {
		{
			put("1", "AddProducts");
		}
	};

	public static String adminSelection() {
		Scanner scanner = new Scanner(System.in);
		String userSelected = null;

		while (true) {
			System.out.println("\nEnter Option Given Below To Perfom Operation");
			System.out.println("1. Continue \n2. Admin Panel");

			System.out.print("\nEnter Your Option : ");
			String adminOption = scanner.next();
			String dataReturn = ADMIN_SELECTION.getOrDefault(adminOption, "Invalid");

			if (!adminOption.isBlank() && !adminOption.isEmpty()) {
				if(dataReturn.equals("Continue")) {
			    	return dataReturn;
			    }else if (dataReturn.equals("Admin Panel")) {
					adminPanel();
				} else if (dataReturn.equals("invalid")) {
					System.out.println(ReusableColors.red + " Enter Only Valid Option" + ReusableColors.defaultColor);
					adminSelection();
					break;
				}
			} else {
				System.out.println(ReusableColors.red + " Fill all the Fields" + ReusableColors.defaultColor);
				adminSelection();
			}
			userSelected = dataReturn;
		}
		return userSelected;
	}

	public static void adminPanelOptionSelection() {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nEnter Option Given Below To Perfom Operation");
			System.out.println("1. Add Products");

			System.out.print("\nEnter Your Option : ");
			String userOption = scanner.next();
			String dataReturn = ADMIN_PANEL_OPTIONS.getOrDefault(userOption, "Invalid");

			if (!userOption.isBlank() && !userOption.isEmpty()) {
				if (dataReturn.equals("AddProducts")) {
					addProducts();
					break;
				} else if (dataReturn.equals("invalid")) {
					System.out.println(ReusableColors.red + " Enter Only Valid Option" + ReusableColors.defaultColor);
					adminPanelOptionSelection();
					break;
				}
			} else {
				System.out.println(ReusableColors.red + " Fill all the Fields" + ReusableColors.defaultColor);
				adminPanelOptionSelection();
			}
		}
		scanner.close();
	}

	public static void addProducts() {
		AddProduct addProduct = new AddProduct();
		addProduct.addProduct();
	}
	
	public static void adminPanel() {
		AdminPanel adminPanel = new AdminPanel();
		adminPanel.showAdminPanel();;
	}

	public static String getProductNameQuery() {
		return PRODUCT_NAME_QUERY;
	}

	public static String getAddproductquery() {
		return ADD_PRODUCT_QUERY;
	}

	public static void main(String[] args) {
		AdminReusablePages adminReusablePages = new AdminReusablePages();
	}
}
