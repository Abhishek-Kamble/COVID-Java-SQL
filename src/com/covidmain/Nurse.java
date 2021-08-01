package com.covidmain;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class nurseHelper
{
	static Dbhelper db = new Dbhelper();
	static ResultSet rs = null;
	
	static long getNurseCount()
    {
    	long nurse_id = 0;
    	String statement = "SELECT COUNT(N_ID) as Count FROM nurse";
        
    	db.startstatement();
    	rs = db.execstatement(statement);
        try {
			rs.next();
			nurse_id=rs.getInt("Count");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nurse_id;
    }
	
    static String nurseID = "";

    static String nurseIDgenerator(){
        long nurseCount = getNurseCount() + 1;
        String nurseCountStr = "";
        if (nurseCount >= 0 && nurseCount < 10) {
            nurseCountStr = "00" + String.valueOf(nurseCount);
        } 
        else if(nurseCount >= 10 && nurseCount < 100) {
            nurseCountStr = "0" + String.valueOf(nurseCount);
        }
        else if(nurseCount >= 100 && nurseCount < 1000) {
            nurseCountStr = "" + String.valueOf(nurseCount);
        }
        
        if (nurseCount == -1) {
            System.out.println(CovidMain.RED + "		Error in fetching database!" + CovidMain.RESET);
        } 
        else {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            nurseID = 'N' + String.valueOf(year) + nurseCountStr;
        }

        return nurseID;
    }
    
    static boolean isNurseExists(String tempNurseId)
    {
    	long cnt = 0;
    	String statement = "SELECT COUNT(1) AS Count FROM dual WHERE EXISTS (SELECT 1 FROM nurse WHERE n_id = '" + tempNurseId + "' AND n_status = 'A')";
        
    	db.startstatement();
    	rs = db.execstatement(statement);
        try {
			rs.next();
			cnt=rs.getInt("Count");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(cnt>0)
			return true;
		else
			return false;
    }
    
}

public class Nurse extends nurseHelper {
    String N_id;
    String N_name;
    int N_slot;
    String N_phone;
    String N_add;
    String N_mail;
    char isRemoved;
    char N_status;

    // default constructor
    Nurse() {
        this.N_id = "";
        this.N_name = "";
        this.N_slot = 0;
        this.N_phone = "";
        this.N_add = "";
        this.N_mail = "";
        this.N_status = 'N';
        this.isRemoved = 'N';
    }

    static Scanner sc = new Scanner(System.in);

    // setters
    void setNurseID(){
        String tempN_ID = nurseIDgenerator();
        if (tempN_ID == "") {
            System.out.println(CovidMain.RED + "		Error!! Can't set Nurse ID." + CovidMain.RESET);
        } else {
            this.N_id = tempN_ID;
        }
    }

    void setNurseName() {
        System.out.print("\nEnter Name        : ");
        String nursename = sc.nextLine();
        this.N_name = nursename;
    }

    void setNurseSlot() {
        System.out.print("\nEnter slot no.    : ");
        int slot_no = sc.nextInt();
        this.N_slot = slot_no;
    }

    void setNursePhone() {
    	sc.nextLine();
        System.out.print("\nEnter Mobile No    : ");
        String nurseMob = sc.nextLine();
        if (checkers.mobileChecker(nurseMob)) {
            this.N_phone = nurseMob;
        } else {
            System.out.println("Invalid Mobile No.!!!");
            System.out.print("Press ENTER to input mobile number again!!");
            setNursePhone();
        }
    }

    void setNurseAdd() {
        System.out.print("\nEnter Address     : ");
        String nurseadd = sc.nextLine();
        this.N_add = nurseadd;
    }

    void setNurseMail() {
        System.out.print("\nEnter E-mail     : ");
        String nursemail = sc.nextLine();
        if (checkers.emailChecker(nursemail)) {
            this.N_mail = nursemail;
        } else {
            System.out.println("Invalid mail!!");
            System.out.print("Please Re-");
            setNurseMail();
        }
    }

    void setNurseStatus() {
        System.out.print(CovidMain.YELLOW + "\nEnter Nurse status (A/N)   : " + CovidMain.RESET);
        char nursestatus = sc.next().charAt(0);
        if(nursestatus == 'A' || nursestatus == 'N')
        {
            this.N_status = nursestatus;
        }
        else
        {
            System.out.print("\n!Re-");
            setNurseStatus();
        }
    }

    static void changeNurseStatus()
    {
    	System.out.println(CovidMain.PURPLE_BOLD + "---------------------- Change Nurse Status ----------------------" + CovidMain.RESET);
    	System.out.print(CovidMain.YELLOW + "\nEnter Nurse ID    : " + CovidMain.RESET);
        String tempNurseID = sc.nextLine();
        
        if(!isNurseExists(tempNurseID))
        {
        	System.out.println(CovidMain.RED + "		Invalid Nurse ID!!!" + CovidMain.RESET);
        	return;
        }
        
        String statement = "UPDATE nurse SET n_status = 'Y' WHERE N_ID = '" + tempNurseID +"'";
        db.startstatement();
        db.update(statement);
		db.endupdate();
		
		System.out.println(CovidMain.RED + "\n--------------------- Nurse Status changed --------------------" + CovidMain.RESET);
    }
    
    //FUNCTIONS
    static void addNurse(){
        Nurse N = new Nurse();
        N.setNurseID();
        N.setNurseName();
        N.setNurseSlot();
        N.setNursePhone();
        N.setNurseAdd();
        N.setNurseMail();
        N.setNurseStatus();
        
        String statement = "INSERT INTO nurse(n_id, n_name, n_slot, n_phone, n_add, n_mail, isremoved, n_status, N_add_date) VALUES('" + N.N_id + "','" + N.N_name + "'," + N.N_slot + ",'" + N.N_phone + "','" + N.N_add + "','" + N.N_mail + "','" + N.isRemoved + "','" + N.N_status + "'," + " sysdate)";
        db.startstatement();
        db.update(statement);
        
        System.out.println(CovidMain.GREEN + "New nurse added successfully!! ID: " + CovidMain.BLUE_BOLD + N.N_id + CovidMain.RESET);
        
    }

    static void removeNurse(){
        // here we don't have to remove nurse details from database only change status to N
        System.out.println(CovidMain.PURPLE_BOLD + "\n---------------------- Remove Nurse ----------------------" + CovidMain.RESET);
        System.out.print(CovidMain.YELLOW + "\n		Enter Nurse ID    : " + CovidMain.RESET);
        String tempNurseID = sc.nextLine();
        
        if(!isNurseExists(tempNurseID))
        {
        	System.out.println(CovidMain.RED + "		Invalid Nurse ID!!!" + CovidMain.RESET);
        	return;
        }
        
        String statement = "UPDATE nurse SET isremoved = 'Y' WHERE N_ID = '" + tempNurseID +"'";
        db.startstatement();
        db.update(statement);
		db.endupdate();
		
		System.out.println(CovidMain.GREEN + "\n--------------------- Nurse Removed --------------------" + CovidMain.RESET);
        
    }

    static void displayNurseDetails()
    {
		System.out.println(CovidMain.PURPLE_BOLD + "\n----------------- Display Nurse Details -----------------" + CovidMain.RESET);
        System.out.print(CovidMain.YELLOW + "\nEnter Nurse ID    : " + CovidMain.RESET);
        String tempNurseID = sc.nextLine();

        if(!isNurseExists(tempNurseID))
        {
        	System.out.println(CovidMain.RED + "		Invalid Nurse ID!!!" + CovidMain.RESET);
        	return;
        }
        
        String statement = "SELECT n_id, n_name, n_slot, n_phone, n_add, n_mail, isremoved, N_status, N_add_date FROM nurse WHERE N_ID = '" + tempNurseID + "'";

    	db.startstatement();
    	rs = db.execstatement(statement);
    	try {
			while(rs.next()){
			    System.out.println("Nurse ID		: " + CovidMain.BLUE_BOLD + rs.getString("N_ID") + CovidMain.RESET);
			    System.out.println("Nurse Name		: " + rs.getString("N_name"));
			    System.out.println("Nurse Slot 		: " + rs.getInt("N_slot"));
			    System.out.println("Nurse Phone No. : " + rs.getString("N_phone"));
			    System.out.println("Nurse Add. 		: " + rs.getString("N_add"));
			    System.out.println("Nurse E-mail	: " + rs.getString("N_mail"));
			    System.out.println("Nurse Removed?	: " + rs.getString("isremoved"));
			    System.out.println("Nurse Status    : " + rs.getString("N_status"));
			    System.out.println("Nurse Added Date    : " + rs.getString("N_add_date"));
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        db.endstatement(); 
		
        System.out.println("------------------------------------------------------");
	}
    
    static void detailNurseSlotWise()
    {
        System.out.println(CovidMain.PURPLE_BOLD + "\n------------------ Slot wise nurse list ------------------" + CovidMain.RESET);
        System.out.print("\nEnter Slot No.    : ");
        int tempSlot = sc.nextInt();

        if(tempSlot>3 || tempSlot<0)
        {
        	System.out.println(CovidMain.RED + "\n		Invalid slot no.!" + CovidMain.RESET);
        	return;
        }
        
        String statement = "SELECT n_id AS Nurse_ID, n_name AS NURSE_NAME FROM nurse WHERE isremoved = 'N' AND N_slot = '" + tempSlot + "'";  
    	db.startstatement();
    	db.printDataList(statement);
        db.endstatement();
        
        System.out.println("--------------------------------------------------------");
    }
    
    static void changeNurseSlot()
    {
    	System.out.println(CovidMain.PURPLE_BOLD + "\n--------------------- Change Nurse Slot ---------------------" + CovidMain.RESET);
    	System.out.print(CovidMain.YELLOW + "Enter Nurse ID: ");
        String tempNurseID = sc.nextLine();
        
        if(!isNurseExists(tempNurseID))
        {
        	System.out.println(CovidMain.RED + "		Invalid Nurse ID!!!" + CovidMain.RESET);
        	return;
        }
        
        System.out.print(CovidMain.YELLOW + "\nEnter new Slot No. for ID: " + CovidMain.BLUE_BOLD + tempNurseID + CovidMain.RESET + " : ");
        int tempSlot = sc.nextInt();
        
        String statement = "UPDATE nurse SET n_slot = " + tempSlot + "WHERE N_ID = '" + tempNurseID +"'";        
        db.startstatement();
        db.update(statement);
        db.endstatement();

        System.out.println(CovidMain.GREEN + "Slot No. changed successfully for nurse ID: " + CovidMain.BLUE_BOLD + tempNurseID + CovidMain.RESET);
    }

    static void displayAllActiveNurse()
    {
    	String statement = "SELECT n_id AS Nurse_ID, n_slot AS Slot, n_name AS NURSE_NAME FROM nurse WHERE isremoved = 'N' AND n_status = 'A' ";  
    	db.startstatement();
    	db.printDataList(statement);
        db.endstatement();
        
        System.out.println("--------------------------------------------------------------");

    }
    
    static void changeNurseStat() throws SQLException
    {
    	System.out.println("\n--------------------- Change Nurse Status ---------------------");
    	System.out.print(CovidMain.YELLOW + "		Enter Nurse ID: " + CovidMain.RESET);
        String tempNurseID = sc.nextLine();
        
        if(!isNurseExists(tempNurseID))
        {
        	System.out.println("		Invalid Nurse ID!!!");
        	return;
        }
        
        System.out.print(CovidMain.YELLOW + "\n	Enter new status for ID: "+ CovidMain.BLUE_BOLD + tempNurseID+ CovidMain.RESET + " : ");
        char tempStat = sc.nextLine().charAt(0);
        
        if(tempStat!='A' || tempStat!='N')
        {
        	System.out.println(CovidMain.RED + "\n		Invalid status entered!!\n" + CovidMain.RESET);
        	return;
        }
        
        String statement = "UPDATE nurse SET n_status = " + tempStat + "WHERE N_ID = '" + tempNurseID +"'";        
        db.startstatement();
        db.update(statement);
        db.endstatement();

        System.out.println(CovidMain.GREEN + "Slot No. changed successfully for nurse ID: " + CovidMain.BLUE_BOLD + tempNurseID + CovidMain.RESET);
    }
}
