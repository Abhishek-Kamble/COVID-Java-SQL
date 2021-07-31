package com.covidmain;
import java.io.*;
import java.sql.*;
import java.util.*;

class helper{
    static Scanner sc = new Scanner(System.in);
    
    static boolean adminLogin()
    {
    	System.out.println("***			Admin Login Prompt		 ***");
    	String sysUser = "admin", sysPass = "adminPass";
    	System.out.print("\n			Enter USERNAME: ");
		String user = sc.nextLine();
		
		Console console = System.console();
        console.printf("			Enter PASSWORD: ");
        char[] passwordChars = console.readPassword();
        String passwordString = new String(passwordChars);
        	
		if(sysUser.equals(user) && passwordString.equals(sysPass))
		{
			System.out.println("\n		Login Successful!!\n");
			enterToContinue();
			return true;
		}
		else
		{
			System.out.println("\n		Invalid Credentials!!\n");
			enterToContinue();
			return false;
		}
    }
    
    static boolean recLogin()
    {
    	String sysUser = "reception", sysPass = "receptionPass";
    	System.out.println("***			Reception Login Prompt		 ***");
    	System.out.print("			Enter USERNAME: ");
		String user = sc.nextLine();
		
		Console console = System.console();
        console.printf("			Enter PASSWORD: ");
        char[] passwordChars = console.readPassword();
        String passwordString = new String(passwordChars);
        	
		if(sysUser.equals(user) && passwordString.equals(sysPass))
		{
			System.out.println("\n		Login Successful!!\n");
			enterToContinue();
			return true;
		}
		else
		{
			System.out.println("\n		Invalid Credentials!!\n");
			enterToContinue();
			return false;
		}
    }
    
	static int login() throws FileNotFoundException
    {
        Scanner sc = new Scanner(System.in);
        int opt=0;
        while (opt != 4)
        {
        	System.out.println("---------------   Login Dashboard   ---------------\n");
            System.out.println("		1. Admin");
            System.out.println("		2. Receptionist");
            System.out.println("		3. Guest");
            System.out.println("		4. Exit");

            System.out.print("\nSelect opt to LOG IN: ");
            char tmp = sc.next().charAt(0);
            opt = Character.getNumericValue(tmp);
            switch(opt)
            {
            	case 1:
            	    if(adminLogin())
            	    {
            	    	enterToContinue();
            	    	return 1;
            	    }
            	    break;
            	
            	case 2:
            		if(recLogin())
            		{
            			enterToContinue();
            			return 2;
            		}
            		break;
            		
            	case 3:
            		enterToContinue();
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
		System.out.println("\nPress ENTER to continue!!!");
		try
		{
			System.in.read();
			//clears screen
	        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}
        catch(Exception e){
        	System.out.println("An error occured!");
        }
	}
	
	static void clearScreen() throws InterruptedException, IOException
	{
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}
	
	static void addNewPatient() throws SQLException
	{
		int wardEnquiry = ward.checkWardAvailibility();
		if(wardEnquiry == -1)
		{
			System.out.println("Sorry! No bed is available at current situation.");
			return;
		}
		
		patient_at_entry.addPatient(wardEnquiry);
		ward.incrNoOfFullBeds(wardEnquiry);
		System.out.println("\n--------------------------------------------------");
	}
	
	static void dischargePatient() throws SQLException
	{
		System.out.println("\n--------------- Discharge Patient---------------");
        System.out.print("\nEnter Patient ID    : ");
        String tempPatientID = sc.nextLine();
        
        if(!patient_at_entry.isPatientExistsandRemoved(tempPatientID))
        {
        	System.out.println("\nInvalid Patient ID or Patient may already discharged!!!");
        	return;
        }
        
        patient_at_entry.dischargePatient(tempPatientID);
        ward.decrNoOfFullBeds(tempPatientID);
        
		System.out.println("\nPatient discharged successfully!! ID: " + tempPatientID);
	}
	
	static void patientSec() throws SQLException, InterruptedException, IOException
	{
		int opt = 0;
        while (opt != 7) {
    		System.out.println("\n-------------- Patient Section --------------");   
    		System.out.println("		1. Add a new Patient ");
    		System.out.println("		2. Discharge Patient");
    		System.out.println("		3. Add a physical test");
    		System.out.println("		4. Search a patient");
    		System.out.println("		5. Find a Patient details");
    		System.out.println("		6. Get patient report");
    		System.out.println("		7. Back to Employee Sec");
            System.out.print("\n		Enter opt: ");
            char tmp = sc.next().charAt(0);
            opt = Character.getNumericValue(tmp);;
            switch (opt) {
            	case 1:
            		clearScreen();
            		addNewPatient();
            		enterToContinue();
            		break;
            		
            	case 2:
            		clearScreen();
            		dischargePatient();
            		enterToContinue();
            		break;
            		
            	case 3:
            		clearScreen();
            		patient_details_after_admit.addRecord();
            		enterToContinue();
            		break;
            		
            	case 4:
            		clearScreen();
            		patient_at_entry.searchPatient();
            		enterToContinue();
            		break;
            		
            	case 5:
            		clearScreen();
            		patient_at_entry.displayPatientDetails();
            		enterToContinue();
            		break;
            		
            	case 6:
            		clearScreen();
                	patient_details_after_admit.displaypatientreport();
                	enterToContinue();
                	break;
            	
            	case 7:
            		clearScreen();
            		System.out.println("\n		Returning to Employee Section...");
            		enterToContinue();
            		break;

            	default:
            		clearScreen();
                    System.out.println("\n		Caution: Invalid option! Handle with care!\n");
                    enterToContinue();
                    break;
            }
        }
	}
	
	static void doctorSec() throws SQLException, InterruptedException, IOException
	{
		int opt = 0;
        while (opt != 7) {
    		System.out.println("\n-------------- Doctor Section --------------");   
    		System.out.println("		1. Add a new Doctor ");
    		System.out.println("		2. Display Doctor List Slotwise");
    		System.out.println("		3. Display Active Doctor's List");
    		System.out.println("		4. Change Working slot of a Doctor");
    		System.out.println("		5. Find a Doctor details");
    		System.out.println("		6. Remove a Doctor");
    		System.out.println("		7. Back to Employee Section ");
            System.out.print("\n		Enter opt: ");
            char tmp = sc.next().charAt(0);
            opt = Character.getNumericValue(tmp);
            switch (opt) {
            	case 1:
            		clearScreen();
            		Doctor.addDoctor();
            		enterToContinue();
            		break;
            		
            	case 2:
            		clearScreen();
            		Doctor.detailDoctorSlotWise();
            		enterToContinue();
            		break;
            		
            	case 3:
            		clearScreen();
            		Doctor.displayAllActivedoctors();
            		enterToContinue();
            		break;
            		
            	case 4:
            		clearScreen();
            		Doctor.changeDoctorSlot();
            		enterToContinue();
            		break;
            		
            	case 5:
            		clearScreen();
            		Doctor.displayDoctorDetails();
            		enterToContinue();
            		break;
            		
            	case 6:
            		clearScreen();
            		Doctor.removeDoctor();
            		enterToContinue();
            		break;
            		
            	case 7:
            		clearScreen();
            		System.out.println("\n		Returning to Employee Section...");
            		enterToContinue();
            		break;
            		
            	default:
            		clearScreen();
                    System.out.println("\n		Caution: Invalid option! \nHandle with care!\n");
                    enterToContinue();
                    break;
            }
        }
	}
	
	static void nurseSec() throws SQLException, InterruptedException, IOException
	{
		int opt = 0;
        while (opt != 7) {
    		System.out.println("\n-------------- Nurse Section --------------");   
    		System.out.println("		1. Add a new Nurse");
    		System.out.println("		2. Display Nurse List Slotwise");
    		System.out.println("		3. Display Active Nurse's List");
    		System.out.println("		4. Change Working slot of a Nurse");
    		System.out.println("		5. Find a Nurse details");
    		System.out.println("		6. Remove a Nurse");
    		System.out.println("		7. Back to Employee Section ");
            System.out.print("\n		Enter opt: ");
            char tmp = sc.next().charAt(0);
            opt = Character.getNumericValue(tmp);;
            switch (opt) {
            	case 1:
            		clearScreen();
            		Nurse.addNurse();
            		enterToContinue();
            		break;
            		
            	case 2:
            		clearScreen();
            		Nurse.detailNurseSlotWise();
            		enterToContinue();
            		break;
            		
            	case 3:
            		clearScreen();
            		Nurse.displayAllActiveNurse();
            		enterToContinue();
            		break;
            		
            	case 4:
            		clearScreen();
            		Nurse.changeNurseSlot();
            		enterToContinue();
            		break;
            		
            	case 5:
            		clearScreen();
            		Nurse.displayNurseDetails();
            		enterToContinue();
            		break;
            		
            	case 6:
            		clearScreen();
            		Nurse.removeNurse();
            		enterToContinue();
            		break;
            		
            	case 7:
            		clearScreen();
            		System.out.println("\n		Returning to Employee Section...");
            		enterToContinue();
            		break;
            		
            	default:
            		clearScreen();
                    System.out.println("\n		Caution: Invalid option! Handle with care!\n");
                    enterToContinue();
                    break;
                    
            }
        }
	}
	
	static void wardBoySec() throws SQLException, InterruptedException, IOException
	{
		int opt = 0;
        while (opt != 7) {
    		System.out.println("\n-------------- Wardboy Section --------------");   
    		System.out.println("		1. Add a new Wardboy ");
    		System.out.println("		2. Display Wardboy List Slotwise");
    		System.out.println("		3. Display Active Wardboy's List");
    		System.out.println("		4. Change Working slot of a Wardboy");
    		System.out.println("		5. Find a Wardboy details");
    		System.out.println("		6. Remove a Wardboy");
    		System.out.println("		7. Back to Employee Section ");
            System.out.print("\n		Enter opt: ");
            char tmp = sc.next().charAt(0);
            opt = Character.getNumericValue(tmp);;
            switch (opt) {
            	case 1:
            		clearScreen();
            		Wardboy.addWardboy();
            		enterToContinue();
            		break;
            		
            	case 2:
            		clearScreen();
            		Wardboy.detailWardboySlotWise();
            		enterToContinue();
            		break;
            		
            	case 3:
            		clearScreen();
            		Wardboy.displayAllActiveWardboy();
            		enterToContinue();
            		break;
            		
            	case 4:
            		clearScreen();
            		Wardboy.changeWardboySlot();
            		enterToContinue();
            		break;
            		
            	case 5:
            		clearScreen();
            		Wardboy.displayWardboyDetails();
            		enterToContinue();
            		break;
            		
            	case 6:
            		clearScreen();
            		Wardboy.removeWardboy();
            		enterToContinue();
            		break;
            		
            	case 7:
            		clearScreen();
            		System.out.println("\n		Returning to Employee Section...");
            		enterToContinue();
            		break;
            		
            	default:
            		clearScreen();
                    System.out.println("\n		Caution: Invalid option! \nHandle with care!\n");
                    enterToContinue();
                    break;
            }
        }
	}
	
	static void employeeSec() throws SQLException, InterruptedException, IOException
	{
		int opt = 0;
        while (opt != 4) {
    		System.out.println("\n-------------- Employee Section --------------");   
    		System.out.println("		1. Doctors Sec.");
    		System.out.println("		2. Nurse Sec.");
    		System.out.println("		3. WardBoy Sec.");
    		System.out.println("		4. Back to Main Menu ");
            System.out.print("\nEnter opt: ");
            char tmp = sc.next().charAt(0);
            opt = Character.getNumericValue(tmp);;
            switch (opt) {
            	case 1:
            		clearScreen();
            		doctorSec();
            		enterToContinue();
            		break;
            		
            	case 2:
            		clearScreen();
            		nurseSec();
            		enterToContinue();
            		break;
            		
            	case 3:
            		clearScreen();
            		wardBoySec();
            		enterToContinue();
            		break;
            		
            	case 4:
            		clearScreen();
            		System.out.println("		Back to menu..");
            		enterToContinue();
            		break;
            		
            	default:
            		clearScreen();
                    System.out.println("\n		Caution: Invalid option! \nHandle with care!\n");
                    enterToContinue();
                    break;
                    
            }
        }
		
	}
	
	static void wardSec() throws SQLException, InterruptedException, IOException
	{
		int opt = 0;
        while (opt != 5) 
        {
            System.out.println("\n------------ Welcome to Ward Section ------------\n");
            System.out.println("		1. Create New Ward");
            System.out.println("		2. Display all ward status");
            System.out.println("		3. Find an available ward");
            System.out.println("		4. Display a Ward Details");
            System.out.println("		5. Back to Main menu");
            System.out.print("\nEnter opt: ");
            
            char tmp = sc.next().charAt(0);
            opt = Character.getNumericValue(tmp);;
            switch (opt) {
                case 1:
                	clearScreen();
                    ward.create_ward();
                    enterToContinue();
                    break;

                case 2:
                	clearScreen();
                	ward.displayAllWardStatus();
                	enterToContinue();
                    break;

                case 3:
                	clearScreen();
                    int temp = ward.checkWardAvailibility();
                    if(temp != -1) {
                    	System.out.println("\n		Bed is available Ward No. " + temp + " !!!\n");
                    }else {
                    	System.out.println("\n		No beds are available currently!\n");
                    } 
                    enterToContinue();
                    break;

                case 4:
                	clearScreen();
                	ward.displayWardDetails();
                	enterToContinue();
                    break;
                    
                case 5:
                	clearScreen();
                	System.out.println("\n		Returning back...\n");
                	enterToContinue();
                	break;

                default:
                	clearScreen();
                    System.out.println("\n		Caution: Invalid option! \nHandle with care!\n");
                    enterToContinue();
                    break;
            }
        }
	}
	
	static void adminMenu() throws SQLException, InterruptedException, IOException
	{
		int opt = 0;
        while (opt != 6) 
        {
            System.out.println("\n------------ Welcome to Admin Panel ------------\n");
            System.out.println("		1. Add new patient.");
            System.out.println("		2. Discharge patient.");
            System.out.println("		3. Go to Patient Section");
            System.out.println("		4. Go to Employee Section");
            System.out.println("		5. Go to Wards Section");
            System.out.println("		6. Exit");
            System.out.print("\n		Enter opt: ");
            char tmp = sc.next().charAt(0);
            opt = Character.getNumericValue(tmp);
            switch (opt) {
                case 1:
                	clearScreen();
                    addNewPatient();
                    enterToContinue();
                    break;

                case 2:
                	clearScreen();
                    dischargePatient();
                    enterToContinue();
                    break;

                case 3:
                	clearScreen();
                    patientSec();
                    enterToContinue();
                    break;

                case 4:
                	clearScreen();
                	employeeSec();
                	enterToContinue();
                    break;
                    
                case 5:
                	clearScreen();
                	wardSec();
                	enterToContinue();
                	break;
                case 6:
                	clearScreen();
                	System.out.println("\n      You are logged out successfully!!");
                	System.out.println("\n		Thank You for using COVID management system.");
                	break;

                default:
                	clearScreen();
                    System.out.println("\n		Caution: Invalid option! \nHandle with care!\n");
                    enterToContinue();
                    break;
            }
        }
	}
	
	static void recMenu() throws SQLException, InterruptedException, IOException
	{
		int opt = 0;
        while (opt != 8) 
        {
            System.out.println("\n------------ Welcome to Receptionist Panel ------------\n");
            System.out.println("		1. Add new patient.");
            System.out.println("		2. Discharge patient.");
            System.out.println("		3. Go to Patient Section");
            System.out.println("		4. Show patient report");
            System.out.println("		5. Check bed availibility");
            System.out.println("		6. Display all ward status");
            System.out.println("		7. Search a patient");
            System.out.println("		8. Exit");
            System.out.print("\n		Enter opt: ");
            char tmp = sc.next().charAt(0);
            opt = Character.getNumericValue(tmp);
            opt = sc.nextInt();
            switch (opt) {
                case 1:
                	clearScreen();
                    addNewPatient();
                    enterToContinue();
                    break;

                case 2:
                	clearScreen();
                    dischargePatient();
                    enterToContinue();
                    break;

                case 3:
                	clearScreen();
                    patientSec();
                    enterToContinue();
                    break;
                    
                case 4:
                	clearScreen();
                	patient_details_after_admit.displaypatientreport();
                	enterToContinue();
                	break;

                case 5:
                	clearScreen();
                	int temp = ward.checkWardAvailibility();
                	if(temp == -1)
                	{
                		System.out.println("\n		Sorry! No bed is available at this moment.");
                		break;
                	}
                	else
                	{
                		System.out.println("\n		Bed is available in ward No. " + temp);
                		System.out.print("			Do you want to add new patient(Y/N)?: ");
                		char res = sc.next().charAt(0); 
                		if(res == 'Y')
                			addNewPatient();
                	}
                	enterToContinue();
                	break;
                	
                case 6:
                	clearScreen();
                	ward.displayAllWardStatus();
                	enterToContinue();
                	break;
                	
                case 7:
                	clearScreen();
                	patient_at_entry.searchPatient();
                	enterToContinue();
                	break;
                
                case 8:
                	clearScreen();
                	System.out.println("\n      You are logged out successfully!!");
                	System.out.println("		Thank You for using COVID management system :)");
                	break;

                default:
                	clearScreen();
                    System.out.println("\n		Caution: Invalid option! \nHandle with care!\n");
                    break;
            }
        }
	}
	
	static void guestMenu() throws SQLException, InterruptedException, IOException 
	{
		int opt=0;
		while(opt!=4) 
		{
	        System.out.println("\n------------- Welcome to Guest Panel -------------\n");
	        System.out.println("		1. Check bed availability.");
	        System.out.println("		2. Show active Doctors list");
	        System.out.println("		3. Wards details in centre");
	        System.out.println("		4. Exit");
	        System.out.print("\n		Enter opt: ");
	        char tmp = sc.next().charAt(0);
            opt = Character.getNumericValue(tmp);

	        switch (opt) {
	            case 1:
	            	clearScreen();
	            	int temp = ward.checkWardAvailibility();
	            	if(temp == -1)
	            	{
	            		System.out.println("\nSorry! No bed is available at this moment.");
	            	}
	            	else
	            	{
	            		System.out.println("\nBed is available in ward No. " + temp);
	            	}
	            	enterToContinue();
	            	break;
	            	
	            case 2:
	            	clearScreen();
	            	Doctor.displayAllActivedoctors();
	            	enterToContinue();
	        		break;
	
	            case 3:
	            	clearScreen();
	               ward.displayWardDetails();
	               enterToContinue();
	               break;
	            	
	            case 4:
	            	clearScreen();
	            	System.out.println("\nThank You for using COVID management system.");
	            	break;
	
	            default:
	            	clearScreen();
	                System.out.println("\nCaution: Invalid option! \nHandle with care!\n");
	                enterToContinue();
	                break;
	        }
        }
    }
		
}

public class CovidMain extends helper
{
	
	public static void main(String[] args) throws SQLException, InterruptedException, IOException {
		clearScreen();
		System.out.println("------------ Welcome to COVID Center management system ------------\n");
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
			System.out.println("You are logged in as Guest!");
			guestMenu();
		}
		
	}
         
}
