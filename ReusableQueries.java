package com.aspiresys.supermarket;

public class ReusableQueries {
	private static final String ADMIN_LOGIN_DETAILS_QUERY = "Select * from adminLoginCredentials where user_mail = ? AND user_password = ?";

	private static final String LOGIN_DETAILS_SELECT_QUERY = "Select * from users where user_mail = ? AND user_password = ?";
    private static final String LOGIN_DETAILS_EMAIL_QUERY = "Select * from users where user_mail = ?";
	private static final String LOGIN_DETAILS_INSERT_QUERY = "insert into loginCredentials values(?, ?, ?, ?)";
	private static final String REGISTER_DETAILS_INSERT_QUERY = "insert into users ( user_name, user_mail, user_mobile, user_password, confirm_password, role ) values(?, ?, ?, ?, ?, ?)";
	
	private static final String DISPLAY_PRODUCTS_QUERY = "select * from products";
	private static final String DISPLAY_PRODUCTS_ID = "select * from products where product_id = ?";

	private static final String FILTER_PRODUCT_CATEGORY  = "Select * from products where product_category = ?";
	private static final String PRODUCT_CATEGORY  = "Select DISTINCT product_category from products";
	private static final String PRODUCT_PRICE  = "Select * from products where product_price Between ? And ?";
	private static final String PRODUCT_DISCOUNT = "Select * from products WHERE CAST(product_discount AS DOUBLE) >= ?";
	private static final String FITLER_PRODUCT_NAME = "Select * from products WHERE product_name = ?";

	private static final String CART_INSERT = "insert into cart ( user_id, product_name, product_quantity, product_price, product_totalPrice ) value(?, ?, ?, ?, ?) ";
	private static final String CART_ITEMS_FETCH = "Select * from cart where user_id = ?";

	public static String getCartItemsFetch() {
		return CART_ITEMS_FETCH;
	}

	public static String getCartInsert() {
		return CART_INSERT;
	}

	public static String getDisplayProductsId() {
		return DISPLAY_PRODUCTS_ID;
	}

	public static String getFitlerProductName() {
		return FITLER_PRODUCT_NAME;
	}

	public static String getProductDiscount() {
		return PRODUCT_DISCOUNT;
	}

	public static String getProductPrice() {
		return PRODUCT_PRICE;
	}

	public static String getProductCategory() {
		return PRODUCT_CATEGORY;
	}

	public static String getDisplayproductsquery() {
		return DISPLAY_PRODUCTS_QUERY;
	}

	public static String getFilterProductCategory() {
		return FILTER_PRODUCT_CATEGORY;
	}

	public static String getAdminlogindetailsquery() {
		return ADMIN_LOGIN_DETAILS_QUERY;
	}

	public static String getLogindetailsselectquery() {
		return LOGIN_DETAILS_SELECT_QUERY;
	}

	public static String getLogindetailsemailquery() {
		return LOGIN_DETAILS_EMAIL_QUERY;
	}

	public static String getLogindetailsinsertquery() {
		return LOGIN_DETAILS_INSERT_QUERY;
	}

	public static String getRegisterdetailsinsertquery() {
		return REGISTER_DETAILS_INSERT_QUERY ;
	}
	
	public static void main(String[] args) {}

}
