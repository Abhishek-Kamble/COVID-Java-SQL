package com.covidmain;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;

class helper{
    static Scanner sc = new Scanner(System.in);

    //TODO masking password system
//    static boolean adminLogin() throws FileNotFoundException {
//        Scanner scan = new Scanner (new File("admin.txt"));
//        Scanner keyboard = new Scanner (System.in);
//        String user = scan.nextLine();
//        String pass = scan.nextLine(); // looks at selected file in scan
//
//        String inpUser = keyboard.nextLine();
//        String inpPass = keyboard.nextLine(); // gets input from user
//
//        if (inpUser.equals(user) && inpPass.equals(pass)) {
//        	System.out.println("\nLogin Successful!!");
//			return true;        
//		} 
//        else {
//			System.out.println("Invalid Credentials");
//			return false;
//		}
//    }
    
//    static boolean adminLogin()
//    {
//    	String sysUser = "admin", sysPass = "adminPass";
//    	
//    	
//    	System.out.print("Enter USERNAME: ");
//		String user = sc.nextLine();
//    	
//    	System.out.print("Enter PASSWORD: ");
//		String pass = sc.nextLine();
//		
//		//TODO string comparator
//		if(sysUser == user && pass == sysPass)
//		{
//			System.out.println("\nLogin Successful!!");
//			return true;
//		}
//		else
//		{
//			System.out.println("Invalid Credentials");
//			return false;
//		}
//    }
    
    static boolean recLogin()
    {
    	String sysUser = "reception", sysPass = "reception";
    	
    	
    	System.out.print("Enter USERNAME: ");
		String user = sc.nextLine();
    	
    	System.out.print("Enter PASSWORD: ");
		String pass = sc.nextLine();
		
		if(sysUser == user)
		{
			System.out.println("\nLogin Successful!!");
			return true;
		}
		else
		{
			System.out.println("Invalid Credentials");
			return false;
		}
    }
    
	static int login() throws FileNotFoundException
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

            System.out.print("\nPlease Select option to LOG IN: ");
            opt = sc.nextInt();
            sc.nextLine();

            switch(opt)
            {
            	case 1:
//            	    if(adminLogin())
            	    	return 1;
//            	    	break;
            	
            	case 2:
            		if(recLogin())
	            		return 2;
            		break;
            		
            	case 3:
            		return 3;
            		
            	case 4:
            		break;
            }
            
        }
        return 0;
    }
	
	static void enterToContinue()
	{
		//TODO
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
		//TODO

	}
	
	static void doctorSec()
	{
		//TODO

	}
	
	static void nurseSec() throws SQLException
	{
		int opt = 0;
        while (opt != 7) {
    		System.out.println("\n-------------- Nurse Section --------------");   
    		System.out.println("1. Add a new Nurse");
    		System.out.println("2. Display Nurse List Slotwise");
    		System.out.println("3. Display Active Nurse's List");
    		System.out.println("4. Change Working slot of a Nurse");
    		System.out.println("5. Find a Nurse details");
    		System.out.println("6. Remove a Nurse");
    		System.out.println("7. Back to Employee Section ");
            System.out.print("\nEnter opt: ");
            opt = sc.nextInt();
            switch (opt) {
            	case 1:
            		Nurse.addNurse();
            		break;
            		
            	case 2:
            		Nurse.detailNurseSlotWise();
            		break;
            		
            	case 3:
            		Nurse.displayAllActiveNurse();
            		break;
            		
            	case 4:
            		Nurse.changeNurseSlot();
            		break;
            		
            	case 5:
            		Nurse.displayNurseDetails();
            		break;
            		
            	case 6:
            		Nurse.removeNurse();
            		break;
            		
            	case 7:
            		System.out.println("\nReturning to Employee Section...");
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
	
	static void employeeSec() throws SQLException
	{
		int opt = 0;
        while (opt != 4) {
    		System.out.println("\n-------------- Employee Section --------------");   
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
                    System.out.println("\nCaution: Invalid option! \nHandle with care!\n");
                    break;
                    
            }
        }
		
	}
	
	static void wardSec()
	{
		
	}
	
	static void adminMenu() throws SQLException
	{
		int opt = 0;
        while (opt != 6) 
        {
            System.out.println("\n------------ Welcome to Admin Panel ------------\n");
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
                    System.out.println("\nCaution: Invalid option! \nHandle with care!\n");
                    break;
            }
        }
	}
	
	static void recMenu()
	{
		int opt = 0;
        while (opt != 4) 
        {
            System.out.println("\n------------ Welcome to Receptionist Panel ------------\n");
            System.out.println("1. Add new patient.");
            System.out.println("2. Discharge patient.");
            System.out.println("3. Go to Patient Section");            
            System.out.println("4. Exit");
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
                	System.out.println("\nThank You for using COVID management system.");
                	break;

                default:
                    System.out.println("\nCaution: Invalid option! \nHandle with care!\n");
                    break;
            }
        }
	}
	
}

public class CovidMain extends helper
{
	
	public static void main(String[] args) throws SQLException, FileNotFoundException {
		System.out.println("----- Welcome to COVID Center management system -----\n");
		int user = login();
		
		if(user == 1)
		{
			adminMenu();
		}
		else if(user == 2)
		{
			recMenu();
		}
		else
		{
			//TODO
			System.out.println("Work in progress for guests");
//			guestMenu();
		}
	
	}   
        
    
}
