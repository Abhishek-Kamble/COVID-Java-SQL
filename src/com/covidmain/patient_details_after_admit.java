package com.covidmain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class Record{

	static Dbhelper db = new Dbhelper();
	static ResultSet rs = null;
	
	static long getRecCount() throws SQLException
    {
    	long rec_no = 0;
    	String statement = "SELECT COUNT(rec_no) as Count FROM patient_phy";
        
    	db.startstatement();
    	rs = db.execstatement(statement);
        rs.next();
		rec_no=rs.getInt("Count");
		return rec_no;
    }

}

public class patient_details_after_admit extends Record {
    String rec_no;
    String p_id;
    String p_bg;
    double p_temp;
    int p_O2level;
    int p_covlevel;
    char p_status;
    
    patient_details_after_admit() 
    {
    	this.p_id = "";
        this.p_bg = "";
        this.p_temp = 0.0;
        this.p_O2level = 0;
        this.p_covlevel = 0;
        this.p_status = 'U';
    }

    static Scanner sc = new Scanner(System.in);

    void setRecordNo() throws SQLException
    {
    	this.rec_no = String.valueOf(getRecCount()+1);
    }
    
    void setpatientbloodgroup()
    {
        System.out.print("\nEnter patients Blood group.    : ");
        String patientbg = sc.nextLine();
        this.p_bg = patientbg;
    }

    void setpatient_temp() 
    {
        System.out.print("\nEnter Temperature in degree_celsius..   : ");
        double patienttemp = sc.nextDouble();
        this.p_temp = patienttemp;
    }


    void setpatietO2level() 
    {
        System.out.print("\nEnter O2 level of patient..   : ");
        int O2lev = sc.nextInt();
        this.p_O2level = O2lev;

    }

    void setpatientcovidlevel() 
    {
        System.out.print("\nEnter percent covid level of patient..   : ");
        int covlev = sc.nextInt();
        this.p_covlevel = covlev;

    }
    void setPatientStatus() 
    {
        System.out.print("\nEnter Patient status (C/U)   : ");
        char Patientstatus = sc.next().charAt(0);
        if (Patientstatus == 'C' || Patientstatus == 'U') {
            this.p_status = Patientstatus;
        } else {
            System.out.print("\n ! Re-");
            setPatientStatus();
        }
    }
    
    static void addRecord() throws SQLException
    {
    	System.out.println("\nAdd New Record");
    	System.out.println("\nEnter Patient ID : ");
    	String tempID = sc.nextLine();
//    	if(!patient_at_entry.isPatientExists(tempID))
//    	{
//    		System.out.println("Invalid Patient ID!!");
//    	}
    	patient_details_after_admit P = new patient_details_after_admit();
    	P.setRecordNo();
    	P.p_id = tempID ;
    	P.setpatientbloodgroup();
    	P.setpatient_temp();
    	P.setpatietO2level();
    	P.setpatientcovidlevel();
    	P.setPatientStatus();
    	String statement = "INSERT INTO patient_phy(rec_no, p_id, p_bg , p_temp , p_O2level , p_covlevel , p_status , p_add_date) VALUES('" + P.rec_no + "','" + P.p_id + "','" + P.p_bg  + "'," + P.p_temp + ","  + P.p_O2level + "," + P.p_covlevel+ ",'" + P.p_status + "'," + " sysdate)";
    	db.startstatement();
    	db.update(statement);
    	db.endstatement();
    	System.out.println("\nNew Record Added Successfully !!");
    }
    
    static void displaypatientphy()
    {
    	System.out.println("\nDisplay physical details of Patient");
    	System.out.println("\nEnter Patient ID : ");
    	String tempID = sc.nextLine();
    	
    	if(!isPatientExists(tempPatientID))
        {
        	System.out.println("Invalid Patient ID!!!");
        	return;
        }
    	
    	
    }
    
//    static void displaycovlist()
//    {
//        System.out.println("\n***Covid List***");
//        System.out.print("\nEnter Slot No.    : ");
//        int tempSlot = sc.nextInt();

//        String statement = "SELECT P_id, P_temp, P_O2level, P_covlevel FROM patient_phy WHERE p_covlevel > 50";
//        
//    	db.startstatement();
//    	db.printDataList(statement);
//        db.endstatement();
//        
//        System.out.println("-----------------------------------------------");
//    }
    
    public static void main(String[] args) throws SQLException {
//    	displaycovlist();
    	addRecord();
    	
	}
}
