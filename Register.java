package com.aspiresys.supermarket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register {
	private static String userName, userMail, userPassword, userConfirmPassword, role="Customer";
	private static Long userMobile;
	private static boolean userNameValid, userMailValid, userMobileValid, userPasswordValid, userConfirmPasswordValid = false;
	
	static Scanner scanner = new Scanner(System.in);
	
	private static final String USER_NAME_PATTERN = "^(?!.*(.).*\\1\\1\\1)[a-zA-Z]{3,20}$" ;
	private static final String USER_MAIL_PATTERN = "^[0-9a-zA-Z]+(?:[.]{0,1}[0-9a-zA-Z])?[@][a-zA-Z]+[.][a-zA-Z]{2,3}([.][a-zA-Z]{2,3}){0,1}$" ;
	private static final String USER_MOBILE_PATTERN = "^[6-9]{1}[0-9]{9}$" ;
	private static final String USER_PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{7,}$";
	
	 public boolean dataValidation(String data, String regexData, String errorMessage) {
		 Pattern regexPattern = Pattern.compile(regexData);
	        Matcher matcher = regexPattern.matcher(data);
	        if (!matcher.matches()) {
	            System.out.println( ReusableColors.red+ errorMessage + ReusableColors.defaultColor);
	            do{
	            	System.out.print( ReusableColors.backgroundWhite + ReusableColors.blue + "Enter 1 to Continue. 2 to Return Home : " + ReusableColors.defaultColor);
		            String userSelected = scanner.nextLine();
		            if (userSelected.equals("1")) {
		                return false;
		            } else if (userSelected.equals("2")) {
		                ReusablePages.homePage();
		                break;
		            }else {
		            	System.out.println(ReusableColors.red + "Enter Valid Input"+ReusableColors.defaultColor);
		            }	
	            }while(true);
	        }   
	        return true;
	 }
	 
	 public String validateData(String data, String regexPattern, String errorMessage) {
		 String value;
		do {
			System.out.print(data);
			value = scanner.nextLine();
		}while(!dataValidation(value,regexPattern,errorMessage));
		return value;
	 }
	 
	 public String validatePasswordData(String data, String regexPattern, String errorMessage) {
		String dataResult = validateData(data, regexPattern, errorMessage); 
		if(!dataResult.isEmpty()) {
			System.out.print("Re-Enter Your Password : ");
	        String userConfirmPassword = scanner.nextLine();
	        while(!dataResult.equals(userConfirmPassword)) {
	        	System.out.println(ReusableColors.red+" Passwords do not match. Please try again."+ReusableColors.defaultColor);
	        	System.out.print("Re-Enter Your Password :");
	        	userConfirmPassword = scanner.nextLine();
	        }
	        setUserPassword(dataResult); setUserConfirmPassword(dataResult);
	        setUserPasswordValid(true); setUserConfirmPasswordValid(true);
		}
		return dataResult;
	 }
	 
	 public void validateMailData(String data, String regexPattern, String errorMessage) {
		String dataResult = validateData(data, regexPattern, errorMessage); 
		if(!dataResult.isEmpty()) {
			try {
				String sql = ReusableQueries.getLogindetailsemailquery();
		        PreparedStatement preparedEmailStatement = JdbcConnect.statement(sql);
		 		preparedEmailStatement.setString(1, dataResult);
		 		ResultSet resultSet = preparedEmailStatement.executeQuery();

		 		if(resultSet.next()) {
					System.out.println(ReusableColors.red + " Email Already Exist "+ReusableColors.defaultColor);
					try {
						String userSelected = ReusablePages.userRegistrationOptionSelection();
						if(userSelected=="Continue") {
							validateMailData(data, regexPattern, errorMessage);
							dataResult="";
						}
					} catch (Exception e) {
						System.out.println( ReusableColors.red + " Error "+ e + ReusableColors.defaultColor);
					}
		 		}else {
		 			setUserMail(dataResult);
		 		}
			} catch (Exception e) {
				System.out.println( ReusableColors.red + " Error "+ e + ReusableColors.defaultColor);
			} 
		}
	 }
	
	public void registration() {
		System.out.println( ReusableColors.backgroundWhite + ReusableColors.red + "----Register---- " + ReusableColors.defaultColor);
        
        String userName = validateData("Enter User Name : ", getUserNamePattern(), " User Name Should not be Empty, Contain Only Alphbets.\n No Space Allowed.\n Minimum 3 Letters is required.\n Consecutive 4 Letters not allowed");
        validateMailData("Enter User Mail : ", getUserMailPattern(), " User Mail Should Not be Empty.\n Enter Valid Mail.\n Format : supermarket@gmail.com/supermarket@yahoo.co.in");
        String userMobile = validateData("Enter User Mobile : ", getUserMobilePattern(), " Mobile Number Should not be Empty.\n Enter Valid Mobile Number.\n Length Should be 10.\n Only Number is Allowed.\n Starts with 6 to 9 Number Only ");
        String userPassword = validatePasswordData("Enter Your Password : ", getUserPasswordPattern()," Password Should Not be Empty.\n Enter Valid Password Number.\n Minimum Length Should be 7.\n One Caps & Small Alphabet, Number and a Special Character is Required");   
   
        if(!userName.isEmpty()&&!userMail.isEmpty()&&!userMobile.isEmpty()&&!userPassword.isEmpty()) {
        	Long userMobileData = Long.parseLong(userMobile);
        	setUserName(userName); setUserMobile(userMobileData); 
        	verifyValidation();
        }else {
    		System.out.println(  ReusableColors.red + " Error While Registering. Fill Out all the Fields" + ReusableColors.defaultColor);
    		registration();
        }
	}
 
    void verifyValidation() {
    		String sql = ReusableQueries.getRegisterdetailsinsertquery() ;
    		
    		try {
    			PreparedStatement preparedStatement = JdbcConnect.statement(sql);
    			preparedStatement.setString(1, getUserName());
    			preparedStatement.setString(2, getUserMail());
    			preparedStatement.setLong(3, getUserMobile());
    			preparedStatement.setString(4, getUserPassword());
    			preparedStatement.setString(5, getUserConfirmPassword());
    			preparedStatement.setString(6, getRole());
    			
    			int resultPreparedStatement = preparedStatement.executeUpdate();
    			
    			if(resultPreparedStatement>0) {
					System.out.println( ReusableColors.green+ "Registered Successfully" + ReusableColors.defaultColor);
					preparedStatement.close();
					ReusablePages reusablePages = new ReusablePages();
					reusablePages.userOptionSelection();
    			}else {
    				System.out.println( ReusableColors.red + "Error Occured While Registering" + ReusableColors.defaultColor);
				}
				
			} catch (Exception e) {
				System.out.println( ReusableColors.red + " Error  "+ e + ReusableColors.defaultColor);
				
			}
    }
    
	public static String getUserNamePattern() {
		return USER_NAME_PATTERN;
	}

	public static String getUserMailPattern() {
		return USER_MAIL_PATTERN;
	}

	public static String getUserMobilePattern() {
		return USER_MOBILE_PATTERN;
	}

	public static String getUserPasswordPattern() {
		return USER_PASSWORD_PATTERN;
	}

	public static void setRole(String role) {
		Register.role = role;
	}
    
    public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		Register.userName = userName;
	}

	public static boolean isUserNameValid() {
		return userNameValid;
	}

	public static void setUserNameValid(boolean userNameValid) {
		Register.userNameValid = userNameValid;
	}
	
	public static String getUserMail() {
		return userMail;
	}

	public static void setUserMail(String userMail) {
		Register.userMail = userMail;
	}
	
	public static boolean isUserMailValid() {
		return userMailValid;
	}

	public static void setUserMailValid(boolean userMailValid) {
		Register.userMailValid = userMailValid;
	}

	public static Long getUserMobile() {
		return userMobile;
	}

	public static void setUserMobile(Long userMobile) {
		Register.userMobile = userMobile;
	}
	
	public static boolean isUserMobileValid() {
		return userMobileValid;
	}

	public static void setUserMobileValid(boolean userMobileValid) {
		Register.userMobileValid = userMobileValid;
	}

	public static String getUserPassword() {
		return userPassword;
	}

	public static void setUserPassword(String userPassword) {
		Register.userPassword = userPassword;
	}

	public static boolean isUserPasswordValid() {
		return userPasswordValid;
	}

	public static void setUserPasswordValid(boolean userPasswordValid) {
		Register.userPasswordValid = userPasswordValid;
	}
	
	public static String getUserConfirmPassword() {
		return userConfirmPassword;
	}

	public static void setUserConfirmPassword(String userConfirmPassword) {
		Register.userConfirmPassword = userConfirmPassword;
	}

	public static boolean isUserConfirmPasswordValid() {
		return userConfirmPasswordValid;
	}

	public static void setUserConfirmPasswordValid(boolean userConfirmPasswordValid) {
		Register.userConfirmPasswordValid = userConfirmPasswordValid;
	}
	
	public static String getRole() {
		return role;
	}

	public static void main(String[] args) {
		Register register = new Register();
		register.registration();
	}
}
