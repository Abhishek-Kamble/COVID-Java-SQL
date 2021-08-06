package com.covidmain;

import java.io.*;
import java.sql.*;
import java.util.*;

class helper {
	static Scanner sc = new Scanner(System.in);

	// Reset
	public static final String RESET = "\033[0m"; // Text Reset

	// Regular Colors
	public static final String BLACK = "\033[0;30m"; // BLACK
	public static final String RED = "\033[0;31m"; // RED
	public static final String GREEN = "\033[0;32m"; // GREEN
	public static final String YELLOW = "\033[0;33m"; // YELLOW
	public static final String BLUE = "\033[0;34m"; // BLUE
	public static final String PURPLE = "\033[0;35m"; // PURPLE
	public static final String CYAN = "\033[0;36m"; // CYAN
	public static final String WHITE = "\033[0;37m"; // WHITE

	// Bold
	public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
	public static final String RED_BOLD = "\033[1;31m"; // RED
	public static final String GREEN_BOLD = "\033[1;32m"; // GREEN
	public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
	public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
	public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
	public static final String CYAN_BOLD = "\033[1;36m"; // CYAN
	public static final String WHITE_BOLD = "\033[1;37m"; // WHITE

	static void red_alert(String alertString) {
		int i = 0;
		System.out.println();
		while (i < 3) {
			System.out.print(RED + alertString + RESET);
			try {
				Thread.sleep(500);
			} catch (Exception E) {
				System.out.println(RED + "Error!" + RESET);
			}
			System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
			System.out.print("                         ");
			try {
				Thread.sleep(500);
			} catch (Exception E) {
				System.out.println("Error!");
			}
			i++;
		}

	}

	static void green_alert(String alertString) {
		int i = 0;
		System.out.println();
		while (i < 3) {
			System.out.print(GREEN + alertString + RESET);
			try {
				Thread.sleep(500);
			} catch (Exception E) {
				System.out.println("Error!");
			}
			System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
			System.out.print("                         ");
			try {
				Thread.sleep(500);
			} catch (Exception E) {
				System.out.println("Error!");
			}
			i++;
		}
	}

	static boolean adminLogin(){
		clearScreen();
		System.out.println(BLUE_BOLD + "***			Admin Login Prompt		 ***" + RESET);
		String sysUser = "admin", sysPass = "admin";
		System.out.print("\n			Enter USERNAME: ");
		String user = sc.nextLine();

		Console console = System.console();
		console.printf("			Enter PASSWORD: ");
		char[] passwordChars = console.readPassword();
		String passwordString = new String(passwordChars);

		if (sysUser.equals(user) && passwordString.equals(sysPass)) {
			green_alert("Login Successful!!");
			// Dbhelper.setDbCred();
			enterToContinue();
			return true;
		} else {
			red_alert("Invalid Credentials!!");
			enterToContinue();
			return false;
		}
	}

	static boolean recLogin() {
		clearScreen();
		String sysUser = "reception", sysPass = "reception";
		System.out.println(BLUE_BOLD + "***			Reception Login Prompt		 ***" + RESET);
		System.out.print("			Enter USERNAME: ");
		String user = sc.nextLine();

		Console console = System.console();
		console.printf("			Enter PASSWORD: ");
		char[] passwordChars = console.readPassword();
		String passwordString = new String(passwordChars);

		if (sysUser.equals(user) && passwordString.equals(sysPass)) {
			green_alert("Login Successful!!");
			enterToContinue();
			return true;
		} else {
			red_alert("Invalid Credentials!!");
			enterToContinue();
			return false;
		}
	}

	static int login(){
		Scanner sc = new Scanner(System.in);
		int opt = 0;
		while (opt != 4) {
			System.out.println(CYAN_BOLD + "---------------   Login Dashboard   ---------------\n" + RESET);
			System.out.println("		1. Admin");
			System.out.println("		2. Receptionist");
			System.out.println("		3. Guest");
			System.out.println("		4. Exit");

			System.out.print(YELLOW + "\n		Select opt to LOG IN: " + RESET);
			char tmp = sc.next().charAt(0);
			opt = Character.getNumericValue(tmp);
			switch (opt) {
			case 1:
				clearScreen();
				if (adminLogin()) {
					enterToContinue();
					return 1;
				}
				break;

			case 2:
				clearScreen();
				if (recLogin()) {
					enterToContinue();
					return 2;
				}
				break;

			case 3:
				clearScreen();
				enterToContinue();
				return 3;

			case 4:
				break;
			}

		}
		return 0;
	}

	static void enterToContinue() {
		// TODO
		System.out.println("\nPress ENTER to continue!!!");
		try {
			System.in.read();
			// clears screen
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception e) {
			System.out.println("An error occured!");
		}
	}

	static void clearScreen() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	static void addNewPatient() {
		int wardEnquiry = ward.checkWardAvailibility();
		if (wardEnquiry == -1) {
			System.out.println(RED + "Sorry! No bed is available at current situation." + RESET);
			return;
		}

		patient_at_entry.addPatient(wardEnquiry);
		ward.incrNoOfFullBeds(wardEnquiry);
		System.out.println("\n-------------------------------------------------------");
	}

	static void dischargePatient() {
		System.out.println(CYAN_BOLD + "\n-----------------  Discharge Patient  -----------------" + RESET);
		System.out.print("\nEnter Patient ID    : ");
		String tempPatientID = sc.nextLine();
		if (tempPatientID.isEmpty()) {
			tempPatientID = sc.nextLine();
		}
		if (!patient_at_entry.isPatientExistsandRemoved(tempPatientID)) {
			System.out.println(RED + "\nInvalid Patient ID or Patient may already discharged!!!" + RESET);
			return;
		}

		patient_at_entry.dischargePatient(tempPatientID);
		ward.decrNoOfFullBeds(tempPatientID);

		System.out.println(GREEN + "\nPatient discharged successfully!! ID: " + tempPatientID + RESET);
	}

	static void patientSec(){
		int opt = 0;
		while (opt != 7) {
			System.out.println(CYAN_BOLD + "\n  -------------- Patient Section --------------" + RESET);
			System.out.println("		1. Add a new Patient ");
			System.out.println("		2. Discharge Patient");
			System.out.println("		3. Add a physical test");
			System.out.println("		4. Search a patient");
			System.out.println("		5. Find a Patient details");
			System.out.println("		6. Get patient report");
			System.out.println("		7. Back to Employee Sec");
			System.out.print(YELLOW + "\n		Enter opt: " + RESET);
			char tmp = sc.next().charAt(0);
			opt = Character.getNumericValue(tmp);
			;
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
				System.out.println(YELLOW + "\n		Returning to Employee Section..." + RESET);
				enterToContinue();
				break;

			default:
				clearScreen();
				System.out.println(RED + "\n		Caution: Invalid option! Handle with care!\n" + RESET);
				enterToContinue();
				break;
			}
		}
	}

	static void doctorSec(){
		int opt = 0;
		while (opt != 7) {
			System.out.println(CYAN_BOLD + "\n  -------------- Doctor Section --------------" + RESET);
			System.out.println("		1. Add a new Doctor ");
			System.out.println("		2. Display Doctor List Slotwise");
			System.out.println("		3. Display Active Doctor's List");
			System.out.println("		4. Change Working slot of a Doctor");
			System.out.println("		5. Find a Doctor details");
			System.out.println("		6. Remove a Doctor");
			System.out.println("		7. Back to Employee Section ");
			System.out.print(YELLOW + "\n		Enter opt: " + RESET);
			char tmp = sc.next().charAt(0);
			opt = Character.getNumericValue(tmp);
			switch(opt){
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
				System.out.println(YELLOW + "\n		Returning to Employee Section..." + RESET);
				enterToContinue();
				break;

			default:
				clearScreen();
				System.out.println(RED + "\n		Caution: Invalid option! \n 	Handle with care!\n" + RESET);
				enterToContinue();
				break;
			}
		}
	}

	static void nurseSec(){
		int opt = 0;
		while (opt != 8) {
			System.out.println(CYAN_BOLD + "\n----------------- Nurse Section -----------------" + RESET);
			System.out.println("		1. Add a new Nurse");
			System.out.println("		2. Display Nurse List Slotwise");
			System.out.println("		3. Display Active Nurse's List");
			System.out.println("		4. Change Working slot of a Nurse");
			System.out.println("		5. Find a Nurse details");
			System.out.println("		6. Remove a Nurse");
			System.out.println("		7. Change Nurse staus");
			System.out.println("		8. Back to Employee Section ");
			System.out.print(YELLOW + "\n		Enter opt: " + RESET);
			char tmp = sc.next().charAt(0);
			opt = Character.getNumericValue(tmp);
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
				Nurse.changeNurseStatus();
				enterToContinue();
				break;

			case 8:
				clearScreen();
				System.out.println(YELLOW + "\n		Returning to Employee Section..." + RESET);
				enterToContinue();
				break;

			default:
				clearScreen();
				System.out.println(RED + "\n		Caution: Invalid option! Handle with care!\n" + RESET);
				enterToContinue();
				break;

			}
		}
	}

	static void wardBoySec(){
		int opt = 0;
		while (opt != 7) {
			System.out.println(CYAN_BOLD + "\n  -------------- Wardboy Section --------------" + RESET);
			System.out.println("		1. Add a new Wardboy ");
			System.out.println("		2. Display Wardboy List Slotwise");
			System.out.println("		3. Display Active Wardboy's List");
			System.out.println("		4. Change Working slot of a Wardboy");
			System.out.println("		5. Find a Wardboy details");
			System.out.println("		6. Remove a Wardboy");
			System.out.println("		7. Back to Employee Section ");
			System.out.print(YELLOW + "\n		Enter opt: " + RESET);
			char tmp = sc.next().charAt(0);
			opt = Character.getNumericValue(tmp);
			;
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
				System.out.println(YELLOW + "\n		Returning to Employee Section..." + RESET);
				enterToContinue();
				break;

			default:
				clearScreen();
				System.out.println(RED + "\n		Caution: Invalid option! \n 	Handle with care!\n" + RESET);
				enterToContinue();
				break;
			}
		}
	}

	static void employeeSec(){
		int opt = 0;
		while (opt != 4) {
			System.out.println(CYAN_BOLD + "\n  -------------- Employee Section --------------" + RESET);
			System.out.println("		1. Doctors Sec.");
			System.out.println("		2. Nurse Sec.");
			System.out.println("		3. WardBoy Sec.");
			System.out.println("		4. Back to Main Menu ");
			System.out.print(YELLOW + "\nEnter opt: " + RESET);
			char tmp = sc.next().charAt(0);
			opt = Character.getNumericValue(tmp);
			;
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
				System.out.println(YELLOW + "		Returning to menu.." + RESET);
				enterToContinue();
				break;

			default:
				clearScreen();
				System.out.println(RED + "\n		Caution: Invalid option! \nHandle with care!\n" + RESET);
				enterToContinue();
				break;

			}
		}

	}

	static void wardSec(){
		int opt = 0;
		while (opt != 5) {
			System.out.println(CYAN_BOLD + "\n  ------------ Welcome to Ward Section ------------\n" + RESET);
			System.out.println("		1. Create New Ward");
			System.out.println("		2. Display all ward status");
			System.out.println("		3. Find an available ward");
			System.out.println("		4. Display a Ward Details");
			System.out.println("		5. Back to Main menu");
			System.out.print(YELLOW + "\n		Enter opt: " + RESET);

			char tmp = sc.next().charAt(0);
			opt = Character.getNumericValue(tmp);
			;
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
				if (temp != -1) {
					System.out.println(GREEN + "\n		Bed is available Ward No. " + temp + " !!!\n" + RESET);
				} else {
					System.out.println(RED + "\n		No beds are available currently!\n" + RESET);
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
				System.out.println(YELLOW + "\n		Returning to Menu...\n" + RESET);
				enterToContinue();
				break;

			default:
				clearScreen();
				System.out.println(RED + "\n		Caution: Invalid option! \n 	Handle with care!\n" + RESET);
				enterToContinue();
				break;
			}
		}
	}

	static void adminMenu(){
		int opt = 0;
		while (opt != 7) {
			System.out.println(CYAN + "\n-------------- Welcome to Admin Panel --------------\n" + RESET);
			System.out.println("		1. Add new patient.");
			System.out.println("		2. Discharge patient.");
			System.out.println("		3. Go to Patient Section");
			System.out.println("		4. Go to Employee Section");
			System.out.println("		5. Go to Wards Section");
			System.out.println("		6. Change/view database credentials");
			System.out.println("		7. Exit");
			System.out.print(YELLOW + "\n		Enter opt: " + RESET);
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

				enterToContinue();

			case 7:
				clearScreen();
				System.out.println(GREEN + "\n      You are logged out successfully!!" + RESET);
				System.out.println(CYAN_BOLD + "\n		Thank You for using COVID management system." + RESET);
				break;

			default:
				clearScreen();
				System.out.println(RED + "\n		Caution: Invalid option! \n 	Handle with care!\n" + RESET);
				enterToContinue();
				break;
			}
		}
	}

	static void recMenu(){
		int opt = 0;
		while (opt != 9) {
			System.out.println(CYAN_BOLD + "\n------------ Welcome to Receptionist Panel ------------\n" + RESET);
			System.out.println("		1. Add new patient.");
			System.out.println("		2. Discharge patient.");
			System.out.println("		3. Go to Patient Section");
			System.out.println("		4. Show patient report");
			System.out.println("		5. Check bed availibility");
			System.out.println("		6. Display all ward status");
			System.out.println("		7. Search a patient");
			System.out.println("		8. Change Nurse Status");
			System.out.println("		9. Exit");
			System.out.print(YELLOW + "\n		Enter opt: " + RESET);
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
				patient_details_after_admit.displaypatientreport();
				enterToContinue();
				break;

			case 5:
				clearScreen();
				int temp = ward.checkWardAvailibility();
				if (temp == -1) {
					System.out.println(RED + "\n		Sorry! No bed is available at this moment." + RESET);
					break;
				} else {
					System.out.println(GREEN + "\n		Bed is available in ward No. " + temp);
					System.out.print(YELLOW + "			Do you want to add new patient(Y/N)?: " + RESET);
					char res = sc.next().charAt(0);
					if (res == 'Y')
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
				Nurse.changeNurseStatus();
				enterToContinue();

			case 9:
				clearScreen();
				System.out.println(GREEN + "\n      You are logged out successfully!!" + RESET);
				System.out.println(CYAN_BOLD + "		Thank You for using COVID management system :)" + RESET);
				break;

			default:
				clearScreen();
				System.out.println(RED + "\n		Caution: Invalid option! \n 	Handle with care!\n" + RESET);
				break;
			}
		}
	}

	static void guestMenu(){
		int opt = 0;
		while (opt != 4) {
			System.out.println(CYAN_BOLD + "\n  ------------- Welcome to Guest Panel -------------\n" + RESET);
			System.out.println("		1. Check bed availability.");
			System.out.println("		2. Show active Doctors list");
			System.out.println("		3. Wards details in centre");
			System.out.println("		4. Exit");
			System.out.print(YELLOW + "\n		Enter opt: " + RESET);
			char tmp = sc.next().charAt(0);
			opt = Character.getNumericValue(tmp);

			switch (opt) {
			case 1:
				clearScreen();
				int temp = ward.checkWardAvailibility();
				if (temp == -1) {
					System.out.println(RED + "\nSorry! No bed is available at this moment." + RESET);
				} else {
					System.out.println(GREEN + "\nBed is available in ward No. " + temp + RESET);
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
				System.out.println(CYAN_BOLD + "\n 	  Thank You for using COVID management system." + RESET);
				break;

			default:
				clearScreen();
				System.out.println(RED + "\nCaution: Invalid option! \n     Handle with care!\n" + RESET);
				enterToContinue();
				break;
			}
		}
	}

}

public class CovidMain extends helper {

	public static void main(String[] args){
		clearScreen();
		System.out.println(CYAN_BOLD + "------------ Welcome to COVID Center management system ------------\n" + RESET);
		int user = login();

		if (user == 1) {
			adminMenu();
		} else if (user == 2) {
			recMenu();
		} else {
			System.out.println(YELLOW + "      You are logged in as Guest!" + RESET);
			guestMenu();
		}

	}

}
