package com.covidmain;
import java.io.*;
import java.sql.*;
import java.util.*;

class helper{
    static Scanner sc = new Scanner(System.in);
    
    static boolean adminLogin()
    {
    	String sysUser = "admin", sysPass = "adminPass";
    	
    	
    	System.out.print("Enter USERNAME: ");
		String user = sc.nextLine();
    	
    	System.out.print("Enter PASSWORD: ");
		String passwordString = sc.nextLine();
		
//		Console console = System.console();
//        console.printf("Please enter your password: ");
//        char[] passwordChars = console.readPassword();
//        String passwordString = new String(passwordChars);
//
//        console.printf(passwordString + "\n");
		
		//TODO string comparator
		if(sysUser.equals(user) && passwordString.equals(sysPass))
		{
			System.out.println("\nLogin Successful!!\n");
			return true;
		}
		else
		{
			System.out.println("\nInvalid Credentials!!\n");
			return false;
		}
    }
    
    static boolean recLogin()
    {
    	String sysUser = "reception", sysPass = "reception";
    	
    	
    	System.out.print("Enter USERNAME: ");
		String user = sc.nextLine();
    	
    	System.out.print("Enter PASSWORD: ");
		String pass = sc.nextLine();
		
		if(sysUser.equals(user) && sysPass.equals(pass))
		{
			System.out.println("\nLogin Successful!!\n");
			return true;
		}
		else
		{
			System.out.println("\nInvalid Credentials\n");
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
            	    if(adminLogin())
            	    	return 1;
            	    	break;
            	
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
		System.out.println("Press Enter to continue!!!");
		try{System.in.read();}
        catch(Exception e){}
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
	
	static void patientSec() throws SQLException
	{
		int opt = 0;
        while (opt != 7) {
    		System.out.println("\n-------------- Patient Section --------------");   
    		System.out.println("1. Add a new Patient ");
    		System.out.println("2. Discharge Patient");
    		System.out.println("3. Add a physical test");
    		System.out.println("4. Search a patient");
    		System.out.println("5. Find a Patient details");
    		System.out.println("6. Get patient report");
    		System.out.println("7. Back to Employee Sec");
            System.out.print("\nEnter opt: ");
            opt = sc.nextInt();
            switch (opt) {
            	case 1:
            		addNewPatient();
            		break;
            		
            	case 2:
            		dischargePatient();
            		break;
            		
            	case 3:
            		patient_details_after_admit.addRecord();
            		break;
            		
            	case 4:
            		patient_at_entry.searchPatient();
            		break;
            		
            	case 5:
            		patient_at_entry.displayPatientDetails();
            		break;
            		
            	case 6:
                	patient_details_after_admit.displaypatientreport();
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
	
	static void doctorSec() throws SQLException
	{
		int opt = 0;
        while (opt != 7) {
    		System.out.println("\n-------------- Doctor Section --------------");   
    		System.out.println("1. Add a new Doctor ");
    		System.out.println("2. Display Doctor List Slotwise");
    		System.out.println("3. Display Active Doctor's List");
    		System.out.println("4. Change Working slot of a Doctor");
    		System.out.println("5. Find a Doctor details");
    		System.out.println("6. Remove a Doctor");
    		System.out.println("7. Back to Employee Section ");
            System.out.print("\nEnter opt: ");
            opt = sc.nextInt();
            switch (opt) {
            	case 1:
            		Doctor.addDoctor();
            		break;
            		
            	case 2:
            		Doctor.detailDoctorSlotWise();
            		break;
            		
            	case 3:
            		Doctor.displayAllActivedoctors();
            		break;
            		
            	case 4:
            		Doctor.changeDoctorSlot();
            		break;
            		
            	case 5:
            		Doctor.displayDoctorDetails();
            		break;
            		
            	case 6:
            		Doctor.removeDoctor();
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
	
	static void wardBoySec() throws SQLException
	{
		int opt = 0;
        while (opt != 7) {
    		System.out.println("\n-------------- Wardboy Section --------------");   
    		System.out.println("1. Add a new Wardboy ");
    		System.out.println("2. Display Wardboy List Slotwise");
    		System.out.println("3. Display Active Wardboy's List");
    		System.out.println("4. Change Working slot of a Wardboy");
    		System.out.println("5. Find a Wardboy details");
    		System.out.println("6. Remove a Wardboy");
    		System.out.println("7. Back to Employee Section ");
            System.out.print("\nEnter opt: ");
            opt = sc.nextInt();
            switch (opt) {
            	case 1:
            		Wardboy.addWardboy();
            		break;
            		
            	case 2:
            		Wardboy.detailWardboySlotWise();
            		break;
            		
            	case 3:
            		Wardboy.displayAllActiveWardboy();
            		break;
            		
            	case 4:
            		Wardboy.changeWardboySlot();
            		break;
            		
            	case 5:
            		Wardboy.displayWardboyDetails();
            		break;
            		
            	case 6:
            		Wardboy.removeWardboy();
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
	
	static void wardSec() throws SQLException
	{
		int opt = 0;
        while (opt != 5) 
        {
            System.out.println("\n------------ Welcome to Ward Section ------------\n");
            System.out.println("1. Create New Ward");
            System.out.println("2. Display all ward status");
            System.out.println("3. Find an available ward");
            System.out.println("4. Display a Ward Details");
            System.out.println("5. Exit");
            System.out.print("\nEnter opt: ");
            Scanner sc = new Scanner(System.in);
            opt = sc.nextInt();
            switch (opt) {
                case 1:
                    ward.create_ward();
                    break;

                case 2:
                	ward.displayAllWardStatus();
                    break;

                case 3:
                    int temp = ward.checkWardAvailibility();
                    if(temp != -1) {
                    	System.out.println("\nBed is available Ward No. " + temp + " !!!\n");
                    }else {
                    	System.out.println("\nNo beds are available currently!\n");
                    }                	
                    break;

                case 4:
                	ward.displayWardDetails();
                    break;
                    
                case 5:
                	System.out.println("\nReturning back...\n");
                	break;

                default:
                    System.out.println("\nCaution: Invalid option! \nHandle with care!\n");
                    break;
            }
        }
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
                	break;
                case 6:
                	System.out.println("\nThank You for using COVID management system.");
                	break;

                default:
                    System.out.println("\nCaution: Invalid option! \nHandle with care!\n");
                    break;
            }
        }
	}
	
	static void recMenu() throws SQLException
	{
		int opt = 0;
        while (opt != 6) 
        {
            System.out.println("\n------------ Welcome to Receptionist Panel ------------\n");
            System.out.println("1. Add new patient.");
            System.out.println("2. Discharge patient.");
            System.out.println("3. Go to Patient Section");
            System.out.println("4. Show patient report");
            System.out.println("5. Check bed availibility");
            System.out.println("6. Display all ward status");
            System.out.println("7. Search a patient");
            System.out.println("8. Back to dashboard");
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
                	patient_details_after_admit.displaypatientreport();
                	break;

                case 5:
                	int temp = ward.checkWardAvailibility();
                	if(temp == -1)
                	{
                		System.out.println("\nSorry! No bed is available at this moment.");
                		break;
                	}
                	else
                	{
                		System.out.println("\nBed is available in ward No. " + temp);
                		System.out.print("Do you want to add new patient(Y/N)?: ");
                		char res = sc.nextLine().charAt(0); 
                		if(res == 'Y')
                			addNewPatient();
                		else
                			break;
                	}
                	break;
                	
                case 6:
                	ward.displayAllWardStatus();
                	break;
                	
                case 7:
                	patient_at_entry.searchPatient();
                	break;
                
                case 8:
                	System.out.println("\nThank You for using COVID management system.");
                	break;

                default:
                    System.out.println("\nCaution: Invalid option! \nHandle with care!\n");
                    break;
            }
        }
	}
	
	static void guestMenu() throws SQLException {
		int opt=0;
        System.out.println("\n------------- Welcome to Guest Panel -------------\n");
        System.out.println("1. Display beds availability.");
        System.out.println("2. Show active Doctors list");
        System.out.println("3. Wards details in centre");
        System.out.println("4. Exit");
        System.out.print("\nEnter opt: ");
        Scanner sc = new Scanner(System.in);
        opt = sc.nextInt();
        switch (opt) {
            case 1:
            	int temp = ward.checkWardAvailibility();
            	if(temp == -1)
            	{
            		System.out.println("\nSorry! No bed is available at this moment.");
            	}
            	else
            	{
            		System.out.println("\nBed is available in ward No. " + temp);
            	}
            	break;
            case 2:
            	Doctor.displayAllActivedoctors();
        		break;

            case 3:
               ward.displayWardDetails();
               break;
            	
            case 4:
            	System.out.println("\nThank You for using COVID management system.");
            	break;

            default:
                System.out.println("\nCaution: Invalid option! \nHandle with care!\n");
                break;
        }
    }
		
	
	
	static void nurseMenu()
	{
		//TODO
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
			System.out.println("You are logged in as Guest!");
			guestMenu();
		}
		
	
	}

	 
           
}
