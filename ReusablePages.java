package com.aspiresys.supermarket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class ReusablePages {
	public static final HashMap<String, String> USER_SELECTION = new HashMap<>() {
		{
			put("1", "Login");
			put("2", "Register");
			put("3", "Products");
			put("4", "ContactUs");
		}
	};

	public static final HashMap<String, String> USER_LOGIN_SELECTION = new HashMap<>() {
		{
			put("1", "Products");
			put("2", "Cart");
			put("3", "MyOrders");
			put("3", "Logout");
		}
	};

	public static final HashMap<String, String> USER_REGISTRATION_SELECTION = new HashMap<>() {
		{
			put("1", "Continue");
			put("2", "Login");
			put("3", "HomePage");
		}
	};

	public static final HashMap<String, String> USER_PRODUCTPAGE_SELECTION = new HashMap<>() {
		{
			put("1", "View All Products");
			put("2", "Filter Product");
			put("3", "Home");
		}
	};
	
	public static final HashMap<String, String> USER_LOGGEDIN_PRODUCTPAGE_SELECTION = new HashMap<>() {
		{
			put("1", "View All Products");
			put("2", "Filter Product");
			put("3", "Add to Cart");
			put("4", "View Cart");
			put("5", "My Orders");
			put("6", "Logout");

		}
	};
	
	public static final HashMap<String, String> FILTER_PRODUCT = new HashMap<>() {
		{
			put("1", "CategoryName");
			put("2", "Price");
			put("3", "Discount");
			put("4", "ProductName");
		}
	};
	
	public static final HashMap<String, String> FILTER_PRODUCT_DISCOUNT = new HashMap<>() {
		{
			put("1", "50");
			put("2", "40");
			put("3", "30");
			put("4", "20");
			put("5", "10");
		}
	};
	
	public static final HashMap<String, String> USER_ADDTOCART_SELECTION = new HashMap<>() {
		{
			put("1", "Continue");
			put("2", "View All Products");
		}
	};
	
	public static void reusableProductDisplay(String sql) {
		try {
			PreparedStatement preparedStatement = JdbcConnect.statement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			resultSetDataDisplay(resultSet);
		} catch (SQLException e) {
			System.out.println(ReusableColors.red + " Error While Fetching Products " + e + ReusableColors.defaultColor);
		}
	}
	
	public static void resultSetDataDisplay(ResultSet resultSet) {
		try {
			System.out.printf(
					ReusableColors.backgroundWhite + ReusableColors.red + "%-10s %-20s %-20s %-20s %-15s %-15s %-15s %-15s\n",
					"ProductID", "Product Name", "Product Category", "Product Quantity", "Product Price", "Original Price", "Discount",
					"Product Stock" + ReusableColors.defaultColor);
			while (resultSet.next()) {
				int productId = resultSet.getInt("product_id");
				String productName = resultSet.getString("product_name");
				String productCategory = resultSet.getString("product_category");
				String productQuantity = resultSet.getString("product_quantity");
				int productPrice = resultSet.getInt("product_price");
				int productOriginalPrice = resultSet.getInt("product_original_price");
				String productDiscount = resultSet.getString("product_discount");
				int productStock = resultSet.getInt("product_stock");
				System.out.printf("%-10s %-20s %-20s %-20s %-15s %-15s %-15s %-15s\n", productId, productName, productCategory,
						productQuantity, productPrice, productOriginalPrice, productDiscount, productStock);
			}
		} catch (SQLException e) {
			System.out.println(ReusableColors.red + " Error While Fetching Products " + e + ReusableColors.defaultColor);
		}
	}
	
	public static void resultSetCartDataDisplay(ResultSet resultSet) {
		try {
			System.out.printf(
					ReusableColors.backgroundWhite + ReusableColors.red + "%-20s %-20s %-20s %-20s\n",
					"Product Name", "Product Quantity", "Product Price", "Total Price" + ReusableColors.defaultColor);
			while (resultSet.next()) {
				String productName = resultSet.getString("product_name");
				String productQuantity = resultSet.getString("product_quantity");
				int productPrice = resultSet.getInt("product_price");
				int productTotalPrice = resultSet.getInt("product_totalprice");
				System.out.printf("%-20s %-20s %-20s %-20s \n", productName, productQuantity, productPrice, productTotalPrice);
			}
			
		} catch (SQLException e) {
			System.out.println(ReusableColors.red + " Error While Fetching Products " + e + ReusableColors.defaultColor);
			
		}
	}

	public static void userOptionSelection() {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nEnter any of the Option Given Below 1/2/3/4");
			System.out.println("1. Login \n2. Register \n3. Products \n4. Contact Us");

			System.out.print("\nEnter Your Option : ");
			String userOption = scanner.next();
			String dataReturn = USER_SELECTION.getOrDefault(userOption, "Invalid");

			if (!userOption.isBlank() && !userOption.isEmpty()) {
				if (dataReturn.equals("Login")) {
					loginPage();
					break;
				} else if (dataReturn.equals("Register")) {
					registerPage();
					break;
				} else if (dataReturn.equals("Products")) {
					products();
					break;
				} else {
					System.out.println(ReusableColors.red + " Enter Only Valid Option" + ReusableColors.defaultColor);
					userOptionSelection();
				}
			} else {
				System.out.println(ReusableColors.red + " Fill all the Fields" + ReusableColors.defaultColor);
				userOptionSelection();
			}
		}
	}

	public static String userRegistrationOptionSelection() {
		Scanner scanner = new Scanner(System.in);
		String userSelected = null;

		while (true) {
			System.out.println("\nEnter any of the Option Given Below 1/2/3");
			System.out.println("1. Continue \n2. Login \n3. Home Page");

			System.out.print("\nEnter Your Option : ");
			String userOption = scanner.next();
			String dataReturn = USER_REGISTRATION_SELECTION.getOrDefault(userOption, "Invalid");

			if (!userOption.isBlank() && !userOption.isEmpty()) {
			    if(dataReturn.equals("Continue")) {
			    	return dataReturn;
			    }else if (dataReturn.equals("Login")) {
					loginPage();
					break;
				} else if (dataReturn.equals("HomePage")) {
					homePage();
					break;
				} else {
					System.out.println(ReusableColors.red + " Enter Only Valid Option" + ReusableColors.defaultColor);
					userRegistrationOptionSelection();
				}
			} else {
				System.out.println(ReusableColors.red + " Fill all the Fields" + ReusableColors.defaultColor);
				userRegistrationOptionSelection();
			}
			userSelected = dataReturn;
		}
		return userSelected;
	}

	public static void userLoginOptionSelection() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("\nEnter any of the Option Given Below 1/2/3");
			System.out.println("1. Products \n2. Cart \n3. My Orders \n4. Logout");

			System.out.print("\nEnter Your Option : ");
			String userOption = scanner.next();
			String dataReturn = USER_LOGIN_SELECTION.getOrDefault(userOption, "Invalid");

			if (!userOption.isBlank() && !userOption.isEmpty()) {
				if (dataReturn.equals("Products")) {
					userProductPageOptionSelection();
					break;
				} else if (dataReturn.equals("Cart")) {
					cart();
					break;
				} else if (dataReturn.equals("MyOrders")) {
					myOrders();
					break;
				} else if (dataReturn.equals("Logout")) {
					logout();
					break;
				} else {
					System.out.println(ReusableColors.red + " Enter Only Valid Option" + ReusableColors.defaultColor);
					userLoginOptionSelection();
				}
			} else {
				System.out.println(ReusableColors.red + " Fill all the Fields" + ReusableColors.defaultColor);
				userLoginOptionSelection();
			}
		}
	}

	public static void userProductPageOptionSelection() {
		boolean loggedinStatus = Login.isLoggedinUserStatus();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println(ReusableColors.backgroundBlue + ReusableColors.white
					+ "\nEnter any of the Option Given Below" + ReusableColors.defaultColor);
			if (loggedinStatus) {
				System.out.println(
						"1. View All Products \n2. Filter Product \n3. Add to Cart \n4. View Cart \n5. My Orders \n6. Logout");
			}else if (!loggedinStatus) {
				System.out.println("1. View All Products \n2. Filter Product \n3. Home");
			}

			System.out.print("\nEnter Your Option : ");
			String userOption = scanner.nextLine();

			if (!userOption.isBlank() && !userOption.isEmpty()) {
				String dataReturn= "";
				if(Login.isLoggedinUserStatus()) {
					dataReturn = USER_LOGGEDIN_PRODUCTPAGE_SELECTION .getOrDefault(userOption, "Invalid");
				}else {
					dataReturn = USER_PRODUCTPAGE_SELECTION.getOrDefault(userOption, "Invalid");
				}
				if (dataReturn.equals("View All Products")) {
					products();
					break;
				} else if (dataReturn.equals("Filter Product")) {
					filtration();
					break;
				} else if (dataReturn.equals("Add to Cart") && Login.isLoggedinUserStatus()) {
					addToCart();
					break;
				} else if (dataReturn.equals("View Cart") && Login.isLoggedinUserStatus()) {
					cart();
					break;
				} else if (dataReturn.equals("My Orders") && Login.isLoggedinUserStatus()) {
					myOrders();
					break;
				} else if (dataReturn.equals("Home")) {
					homePage();
					break;
				} else if (dataReturn.equals("Logout")) {
					logout();
					break;
				}
			} else {
				System.out.println(ReusableColors.red + " Fill all the Fields" + ReusableColors.defaultColor);
				userProductPageOptionSelection();
				break;
			}
		}
	}

	public static String userCategoryFiltrationOptionSelection() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("\nEnter any of the Option Given Below 1/2/3/4");
			System.out.println("1. Category Name \n2. Price \n3. Discount \n4. Product Name");
			
			System.out.print("\nEnter Your Option : ");
			String userOption = scanner.next();
			String dataReturn = FILTER_PRODUCT.getOrDefault(userOption, "Invalid");

			if (!userOption.isBlank() && !userOption.isEmpty()) {
				if (!dataReturn.isEmpty()) {
					return dataReturn;
				} else {
					System.out.println(ReusableColors.red + " Enter Only Valid Option" + ReusableColors.defaultColor);
					userOptionSelection();
				}
			} else {
				System.out.println(ReusableColors.red + " Fill all the Fields" + ReusableColors.defaultColor);
				userCategoryFiltrationOptionSelection();
			}
		}
	}
	
	public static String userDiscountFiltrationOptionSelection() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("\nEnter any of the Option Given Below");
			System.out.println("1. 50% & Above \n2. 40% & Above \n3. 30% & Above \n4. 20% & Above \n5. 10% & Above ");
			
			System.out.print("\nEnter Your Option : ");
			String userOption = scanner.next();
			String dataReturn = FILTER_PRODUCT_DISCOUNT.getOrDefault(userOption, "Invalid");

			if (!userOption.isBlank() && !userOption.isEmpty()) {
				if (!dataReturn.isEmpty()) {
					return dataReturn;
				} else {
					System.out.println(ReusableColors.red + " Enter Only Valid Option" + ReusableColors.defaultColor);
					userOptionSelection();
				}
			} else {
				System.out.println(ReusableColors.red + " Fill all the Fields" + ReusableColors.defaultColor);
				userCategoryFiltrationOptionSelection();
			}
		}
	}

	public static String userAddToCartOptionSelection() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("\nEnter any of the Option Given Below");
			System.out.println("1. Continue \n2. Back to Product Page");
			
			System.out.print("\nEnter Your Option : ");
			String userOption = scanner.next();
			String dataReturn = USER_ADDTOCART_SELECTION .getOrDefault(userOption, "Invalid");

			if (!userOption.isBlank() && !userOption.isEmpty()) {
				if (!dataReturn.isEmpty()) {
					if(dataReturn.equals("View All Products")) {
						userProductPageOptionSelection();
					}else if(dataReturn.equals("Invalid")) {
						System.out.println(ReusableColors.red + " Enter Only Valid Option" + ReusableColors.defaultColor);
						userAddToCartOptionSelection();
					}
					return dataReturn;
				} else {
					System.out.println(ReusableColors.red + " Enter Only Valid Option" + ReusableColors.defaultColor);
					userAddToCartOptionSelection();
				}
			} else {
				System.out.println(ReusableColors.red + " Fill all the Fields" + ReusableColors.defaultColor);
				userAddToCartOptionSelection();
			}
		}
	}
	
	private static void filtration() {
		String resultString =  userCategoryFiltrationOptionSelection();
		Filtration filtration = new Filtration();
		filtration.filterProducts(resultString);
	}

	public static void homePage() {
		HomePage homePage = new HomePage();
		homePage.displayWelcomeData();
	}

	public static void loginPage() {
		Login login = new Login();
		login.loginValidation();
	}

	public static void registerPage() {
		Register register = new Register();
		register.registration();
	}

	public static void products() {
		Products products = new Products();
		products.displayProducts();
	}

	public static void addToCart() {
		AddToCart addToCart = new AddToCart();
		addToCart.addToCart();
	}

	public static void cart() {
		AddToCart addToCart = new AddToCart();
		addToCart.cartOptions();
	}

	public static void myOrders() {}

	public static void logout() {
		Login.setLoggedinUserId(0);
		Login.setLoggedinUserMail(null);
		Login.setLoggedinUserName(null);
		Login.setLoggedinUserStatus(false);
		homePage();
	}
}
