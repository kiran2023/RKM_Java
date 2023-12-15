package com.aspiresys.supermarket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.print.attribute.SupportedValuesAttribute;

public class Login extends ReusableQueries {
	
    private static String userMail, userPassword;
    static String userSql = ReusableQueries.getLogindetailsselectquery();
    static String adminSql = ReusableQueries.getAdminlogindetailsquery(); 
	private static String loggedinUserName, loggedinUserMail;
    private static int loggedinUserId ;
	private static boolean loggedinUserStatus;
	
    class LoginTime{
    	static LocalDateTime now = LocalDateTime.now();
		static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        static String formattedDate = now.format(dateFormatter);
        static String formattedTime = now.format(timeFormatter);
    }
	
	public void loginValidation(){
		System.out.println("\nLogin to "+ ReusableColors.red +"RK "+ ReusableColors.defaultColor + ReusableColors.blue + "MART" + ReusableColors.defaultColor);
		Scanner scanner = new Scanner(System.in);
		
        System.out.print( "\nEnter User Mail : ");
        String userMail = scanner.nextLine();
        
        System.out.print( "Enter User Password : ");
        String userPassword = scanner.nextLine();
        
        if(!userMail.trim().isEmpty()|| !userMail.trim().isBlank()) {
        	 setUserMail(userMail.trim());
             setUserPassword(userPassword.trim());
             setLoggedinUserStatus(true);
             loginCredentials(getUserMail(),getUserPassword());
        }else {
 			System.out.println( ReusableColors.red + " Fill all the Fields" + ReusableColors.defaultColor);
 			loginValidation();
        }
	 }
	
    public static void loginCredentials(String userMail, String userPassword) {
    	if(isAdmin( userMail, userPassword )) {
			 System.out.println( ReusableColors.green+" Admin Loggedin Successfully" + ReusableColors.defaultColor);
			 AdminPanel adminPanel = new AdminPanel();
			 adminPanel.showAdminPanel();
    	}else if(isUser(userMail, userPassword)) {
 		    System.out.println( ReusableColors.green+ getLoggedinUserName() +" Loggedin Successfully" + ReusableColors.defaultColor);
 		    insertLoginData();
 		    UserPanel userPanel = new UserPanel();
 		    userPanel.showUserPanel();
    	}else {
			System.out.println( ReusableColors.red + " Invalid Login Credentials" + ReusableColors.defaultColor);
			ReusablePages reusablePages = new ReusablePages();
			reusablePages.userOptionSelection();
		}
    }

	public static boolean isAdmin(String userMail, String userPassword) {
    	return executeLoginCredentials(adminSql, userMail, userPassword);
	}
    
    public static boolean isUser(String userMail, String userPassword) {
    	return executeLoginCredentials(userSql, userMail, userPassword);	
	}
    
    public static boolean executeLoginCredentials( String sql, String userMail, String userPassword ) {
    	PreparedStatement preparedUserStatement;
    	
		try {
			preparedUserStatement = JdbcConnect.statement(sql);
			preparedUserStatement.setString(1, getUserMail());
			preparedUserStatement.setString(2, getUserPassword());
			ResultSet resultSet = preparedUserStatement.executeQuery();
			 if(resultSet.next()) {
				 setLoggedinUserName(resultSet.getString("user_name"));
				 setLoggedinUserMail(resultSet.getString("user_mail"));
				 setLoggedinUserId(resultSet.getInt("id"));
	            return true;
	         }
			
		} catch (SQLException e) {
			System.out.println( ReusableColors.red + " Error Occured While Logging" + e + ReusableColors.defaultColor);
		}
    	return false;
    }
    
    public static void insertLoginData() {
    	String loginDetails = ReusableQueries.getLogindetailsinsertquery();
        
        try {
            PreparedStatement preparedStatementLoginDetails = JdbcConnect.statement(loginDetails);

			preparedStatementLoginDetails.setString(1, getUserMail());
			preparedStatementLoginDetails.setString(2, getUserPassword());
		    preparedStatementLoginDetails.setString(3, LoginTime.formattedTime);
		    preparedStatementLoginDetails.setString(4, LoginTime.formattedDate);

		    preparedStatementLoginDetails.executeUpdate(); 
		} catch (SQLException e) {
			System.out.println( ReusableColors.red + " Error Occured While Storing Details" + e + ReusableColors.defaultColor);
		}
    }
	
	public static String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public static String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public static String getLoggedinUserName() {
		return loggedinUserName;
	}

	public static void setLoggedinUserName(String loggedinUserName) {
		Login.loggedinUserName = loggedinUserName;
	}

	public static String getLoggedinUserMail() {
		return loggedinUserMail;
	}

	public static void setLoggedinUserMail(String loggedinUserMail) {
		Login.loggedinUserMail = loggedinUserMail;
	}
	
    public static boolean isLoggedinUserStatus() {
		return loggedinUserStatus;
	}

	public static void setLoggedinUserStatus(boolean loggedinUserStatus) {
		Login.loggedinUserStatus = loggedinUserStatus;
	}
	
    public static int getLoggedinUserId() {
		return loggedinUserId;
	}

	public static void setLoggedinUserId(int loggedinUserId) {
		Login.loggedinUserId = loggedinUserId;
	}

	public static void main(String[] args) {
		Login login = new Login();
		login.loginValidation();
	}
}