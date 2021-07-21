package com.covidmain;
import java.io.Console;
import java.util.*;

class helper{
	static void getCurrDate()
	{
		long millis=System.currentTimeMillis();  
		java.sql.Date date=new java.sql.Date(millis);  
		System.out.println(date);
	}
	
	static void passwordExample() {        
        // Create the console object
        Console cnsl = System.console();
  
        if (cnsl == null) {
            System.out.println("No console available");
            return;
        }
  
        // Read line
        String str = cnsl.readLine("Enter username : ");
  
        // Print username
        System.out.println("Username : " + str);
  
        // Read password
        // into character array
        char[] ch = cnsl.readPassword("Enter password : ");
  
        // Print password
        System.out.println("Password : " + ch);

    }
	
	static int login()
    {
        Scanner sc = new Scanner(System.in);
        int opt=0;
        while (opt != 4)
        {
        	System.out.println("---------Login Dashboard--------\n");
            System.out.println("1. Admin");
            System.out.println("2. Receptionist");
            System.out.println("3. Guest");
            System.out.println("4. Exit");

            System.out.println("---------------------\nPlease Select option to LOG IN: ");
            opt = sc.nextInt();

            switch(opt)
            {
            	case 1:
            		System.out.print("Enter PASSWORD: ");
            		String pass = sc.nextLine();
            		return 0;
            }
            
        }
        return 0;
    }
}

public class CovidMain extends helper
{
	
	public static void main(String[] args) {
		System.out.println("----- Welcome to COVID Center management system -----\n");
//		int user = login();
		
//		System.out.println("User: " + user);
//		passwordExample();
		getCurrDate();
		
		
		
	}   
        
    
}
