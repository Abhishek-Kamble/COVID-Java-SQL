package com.covidmain;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

//function to generate nurse id
class nurseHelper
{
	static Dbhelper db = new Dbhelper();
	static ResultSet rs = null;
	
	static long getNurseCount()
    {
    	long nurse_id = 0;
    	String statement = "SELECT COUNT(N_ID) as Count FROM nurse";
        try 
        {
        	db.startstatement();
        	rs = db.execstatement(statement);
        	rs.next();
		    nurse_id=rs.getInt("Count");
		    return nurse_id;
		}
        catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		      return -1;    		      
        }
    }
	
	//NURSE_ID
    static String nurseID = "";

    static String nurseIDgenerator() {
        long nurseCount = NurseSQL.getNurseCount() + 1;
        System.out.println("NurseCount: " + nurseCount);
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
            System.out.println("Error in fetching database!");
        } 
        else {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            nurseID = 'N' + String.valueOf(year) + nurseCountStr;
        }

        return nurseID;
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
    void setNurseID() {
        String tempN_ID = nurseIDgenerator();
        if (tempN_ID == "") {
            System.out.println("Error!! Can't set Nurse ID");
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
            this.N_name = nurseMob;
        } else {
            System.out.println("Invalid Mobile No.!!!");
            System.out.print("Press Re-");
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
        System.out.print("\nEnter Nurse status (A/N)   : ");
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

     // TODO -getters

    // function to add new nurse
    static void addNurse() {
        Nurse N = new Nurse();
        N.setNurseID();
        N.setNurseName();
        N.setNurseSlot();
        N.setNursePhone();
        N.setNurseAdd();
        N.setNurseMail();
        N.setNurseStatus();
        
        String statement = "INSERT INTO nurse(n_id, n_name, n_slot, n_phone, n_add, n_mail, isremoved, n_status) VALUES('" + N.N_id + "','" + N.N_name + "'," + N.N_slot + ",'" + N.N_phone + "','" + N.N_add + "','" + N.N_mail + "','" + N.isRemoved + "','" + N.N_status + "')";
        db.startstatement();
        db.update(statement);
        
        System.out.println("New nurse added successfully!! ID: " + N.N_id);
        
    }

    // function to remove nurse TODO testing
    static void removeNurse() {
        // here we don't have to remove nurse details from database;
        // only change status to N
        System.out.println("**** Remove Nurse ****");
        System.out.print("\nEnter Nurse ID    : ");
        String tempNurseID = sc.nextLine();
        
        String statement = "UPDATE nurse SET isremoved = 'Y' WHERE N_ID = '" + tempNurseID +"'";
        
        //TODO fixing exceptions
        db.startstatement();
        db.update(statement);
		db.endupdate();
		
		System.out.println("-------------------------------------------------");
    }

    public static void displayNurseDetails() throws SQLException {
		System.out.println("** Display Nurse Details **");
        System.out.print("\nEnter Nurse ID    : ");
        String tempNurseID = sc.nextLine();

        String statement = "SELECT n_id, n_name, n_slot, n_phone, n_add, n_mail, isremoved, N_status FROM nurse WHERE N_ID = '" + tempNurseID + "'";

    	db.startstatement();
    	rs = db.execstatement(statement);
    	while(rs.next()){
            System.out.println("Nurse ID		: " + rs.getString("N_ID"));
            System.out.println("Nurse Name		: " + rs.getString("N_name"));
            System.out.println("Nurse Slot 		: " + rs.getInt("N_slot"));
            System.out.println("Nurse Phone No. : " + rs.getString("N_phone"));
            System.out.println("Nurse Add. 		: " + rs.getString("N_add"));
            System.out.println("Nurse E-mail	: " + rs.getString("N_mail"));
            System.out.println("Nurse Removed?	: " + rs.getString("isremoved"));
            System.out.println("Nurse Status    : " + rs.getString("N_status"));
         }
        db.endstatement(); 
		
        System.out.println("-----------------------------------------------");
	}
    
    static void detailNurseSlotWise()
    {
        System.out.println("\n***Slot wise nurse list***");
        System.out.print("\nEnter Slot No.    : ");
        int tempSlot = sc.nextInt();

        String statement = "SELECT n_id, n_name FROM nurse WHERE N_slot = '" + tempSlot + "'";
        
    	db.startstatement();
    	db.printDataList(statement);
        db.endstatement();
        
        System.out.println("-----------------------------------------------");
    }
    
    static void changeNurseSlot()
    {
    	System.out.println("***Change Nurse Slot***");
    	System.out.print("Enter Nurse ID: ");
        String tempNurseID = sc.nextLine();
        System.out.print("\nEnter new Slot No. for"+ tempNurseID + " : ");
        int tempSlot = sc.nextInt();
        
        //TODO fixing exceptions
        String statement = "UPDATE nurse SET n_slot = " + tempSlot + "WHERE N_ID = '" + tempNurseID +"'";
        
        db.startstatement();
        db.update(statement);
        db.endstatement();

        System.out.println("Slot No. changed successfully for nurse ID: " + tempNurseID);
    }

    //main function for testing 
    public static void main(String[] args) throws SQLException {
        System.out.println("\nRunning ");
//        System.out.println(nurseIDgenerator());
//        Nurse.displayNurseDetails();
//        Nurse.removeNurse();
//        Nurse.addNurse();
//        Nurse.detailNurseSlotWise();
//        Nurse.changeNurseSlot();
        
    }

}
