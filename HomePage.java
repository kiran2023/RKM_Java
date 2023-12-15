package com.aspiresys.supermarket;

import java.util.HashMap;
import java.util.Scanner;

public class HomePage {
	
	static String welcomeMessage = "Welcome to " + ReusableColors.red + "RK " + ReusableColors.defaultColor + ReusableColors.blue + "MART" + ReusableColors.defaultColor ;
		
	public void displayWelcomeData() {
        System.out.println(welcomeMessage);
        ReusablePages reusablePages = new ReusablePages();
		reusablePages.userOptionSelection();
    }
	
	public static void main(String[] args) {
		HomePage homePage = new HomePage();
		homePage.displayWelcomeData();
    }
}
