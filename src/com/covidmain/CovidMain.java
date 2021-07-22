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
	
	//TODO testing console password
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
	
	static void addNewPatient()
	{
		//TODO
	}
	
	static void dischargePatient()
	{
		//TODO
	}
	
	static void patientSec()
	{
		
	}
	
	static void doctorSec()
	{
		
	}
	
	static void nurseSec()
	{
		int opt = 0;
        while (opt != 4) {
    		System.out.println("--------------\n Nurse Section --------------");   
    		System.out.println("1. Add new Nurse");
    		System.out.println("2. ");
    		System.out.println("3. WardBoy Sec.");
    		System.out.println("4. Back to Main Menu ");
            System.out.print("\nEnter opt: ");
            Scanner sc = new Scanner(System.in);
            opt = sc.nextInt();
            switch (opt) {
            	case 1:
            		doctorSec();
            		break;
            		
            	case 2:
            		nurseSec();
            		break;
            		
            	case 3:
            		wardBoySec();
            		break;
            		
            	case 4:
            		break;
            		
            	default:
                    System.out.println("\n!Caution: Invalid option! \nHandle with care!\n");
                    break;
                    
            }
        }
	}
	
	static void wardBoySec()
	{
		
	}
	
	static void employeeSec()
	{
		int opt = 0;
        while (opt != 4) {
    		System.out.println("--------------\n Employee Section --------------");   
    		System.out.println("1. Doctors Sec.");
    		System.out.println("2. Nurse Sec.");
    		System.out.println("3. WardBoy Sec.");
    		System.out.println("4. Back to Main Menu ");
            System.out.print("\nEnter opt: ");
            Scanner sc = new Scanner(System.in);
            opt = sc.nextInt();
            switch (opt) {
            	case 1:
            		doctorSec();
            		break;
            		
            	case 2:
            		nurseSec();
            		break;
            		
            	case 3:
            		wardBoySec();
            		break;
            		
            	case 4:
            		System.out.println("Back to menu..");
            		break;
            		
            	default:
                    System.out.println("\n!Caution: Invalid option! \nHandle with care!\n");
                    break;
                    
            }
        }
		
	}
	
	static void wardSec()
	{
		
	}
	
	
}

public class CovidMain extends helper
{
	
	public static void main(String[] args) {
		System.out.println("----- Welcome to COVID Center management system -----\n");
//		int user = login();
		
//		System.out.println("User: " + user);
//		passwordExample();
//		getCurrDate();
		
		int opt = 0;
        while (opt != 6) {
            System.out.println("*********** Menu ***********\n");
            System.out.println("1. Add new patient.");
            System.out.println("2. Discharge patient.");
            System.out.println("3. Go to Patient Section");
            System.out.println("4. Go to Employee Section");
            System.out.println("5. Go to Wards Section");
            System.out.println("6. Exit");
            System.out.print("\nEnter opt: ");
            Scanner sc = new Scanner(System.in);
            opt = sc.nextInt();
            switch (opt) {
                case 1:
                    addNewPatient();
                    break;

                case 2:
                    dischargePatient();
                    break;

                case 3:
                    patientSec();
                    break;

                case 4:
                	employeeSec();
                    break;
                    
                case 5:
                	wardSec();
                	
                case 6:
                	System.out.println("\nThank You for using COVID management system.");
                	break;

                default:
                    System.out.println("\n!Caution: Invalid option! \nHandle with care!\n");
                    break;
            }
        }
	}   
        
    
}
