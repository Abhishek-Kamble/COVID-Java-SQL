package com.covidmain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class wardHelper{
	static Dbhelper db = new Dbhelper();
    static ResultSet rs = null;
    
    static int getWardCount() throws SQLException
    {
    	int wardcount = 0;
    	String statement = "SELECT COUNT(wardname) as Count FROM ward";
        
    	db.startstatement();
    	rs = db.execstatement(statement);
        rs.next();
        wardcount = rs.getInt("Count");
		return wardcount;
    }
    
    static void incrNoOfFullBeds(int wardname)
    {
    	String statement = "UPDATE ward SET No_Of_Beds_Full = No_Of_Beds_Full + 1 where wardname = " + wardname;
        db.startstatement();
    	db.update(statement);
    }
    
    static void decrNoOfFullBeds(String tempPatientID)
    {
    	String statement = "UPDATE ward SET No_Of_Beds_Full = No_Of_Beds_Full - 1 WHERE wardname = (SELECT p_ward FROM Patient_at_entry WHERE p_id = '" + tempPatientID + "')";
        db.startstatement();
    	db.update(statement);
    	db.endupdate();
    }
}

public class ward extends wardHelper{
    int wardname; 
    String W_type; // E/N
    int W_Capacity; // total
    int No_Of_Beds_Full; // engage
    String Doctor1;//
    String nurse1;
    String wardboy1;

    // constructor
    ward() {
        this.wardname = 0;
        this.W_type = "";
        this.W_Capacity = 0;
        this.No_Of_Beds_Full = 0;
        this.Doctor1 = "";
        this.nurse1 = "";
        this.wardboy1 = "";
    }

    static Scanner sc = new Scanner(System.in);

    void set_ward_name() throws SQLException {
        this.wardname = getWardCount() + 1;
    }

    void set_ward_capacity() {
        System.out.print("\n		Enter capacity of ward: ");
        int capacity = sc.nextInt();
        this.W_Capacity = capacity;
    }

    void setDoctor() throws SQLException {
    	sc.nextLine();
        System.out.print("\n		Enter doctor ID for ward: ");
        String wardABCD = sc.nextLine();
        this.Doctor1 = wardABCD;
        if(Nurse.isNurseExists(wardABCD))
        {
            this.nurse1 = wardABCD;
        }
        else
        {
        	System.out.println("\n		Invalid Doctor ID or Nurse doesn't exitst! Please Renter Doctor Id");
        	setDoctor();
        }        
    }

    void setNurse1() throws SQLException {
        System.out.print("\nEnter Nurse 1 ID: ");
        String nid = sc.nextLine();
        if(Nurse.isNurseExists(nid))
        {
            this.nurse1 = nid;
        }
        else
        {
        	System.out.println("\n		Invalid Nurse ID or Nurse doesn't exitst! Please Renter Nurse Id");
        	setNurse1();
        }
    }

    void setWardboy1() throws SQLException {
        System.out.print("\nEnter wardboy 1 ID: ");
        String nid = sc.nextLine();
        
        if(Wardboy.isWardboyExists(nid))
        {
        	this.wardboy1 = nid;
        }
        else
        {
        	System.out.println("\n		Invalid Wardboy ID or Wardboy doesn't exitst! Please Renter Doctor Id");
        	setWardboy1();
        }
    }

    static int checkWardAvailibility() throws SQLException
    {
		int res = -1;
		String statement = "SELECT wardname FROM ward WHERE w_capacity > no_of_beds_full";
		db.startstatement();
		rs = db.execstatement(statement);
		while(rs.next())
		{
			res = rs.getInt("wardname");
		}
		
		if(res > 0)
			return res;
    	return -1;
    }
    
    static void displayAllWardStatus()
    {
    	System.out.println("\n-------------- Display All Ward Status --------------");
        String statement = "SELECT wardname AS W_No, W_type AS Type, W_Capacity AS Cap, No_Of_Beds_Full AS Full_Beds FROM ward";  
    	db.startstatement();
    	db.printDataList(statement);
        db.endstatement();
        
        System.out.println("-------------------------------------------------------");
    }
    
    static void displayWardDetails() throws SQLException {
		System.out.println("\n----------- Display Ward Details -----------");
        System.out.print("\nEnter Ward No.    : ");
        int tempNurseID = sc.nextInt();

//        if()
//        {
//        	System.out.println("Invalid Ward No.!!!");
//        	return;
//        }
        
        String statement = "SELECT wardname, W_type, W_Capacity, No_Of_Beds_Full, Doctor1, Nurse1, Wardboy1 FROM ward WHERE wardname =" + tempNurseID;

    	db.startstatement();
    	rs = db.execstatement(statement);
    	while(rs.next()){
            System.out.println("Ward No.		: " + rs.getInt("wardname"));
            System.out.println("Ward Capacity	: " + rs.getString("W_Capacity"));
            System.out.println("No Of Beds Full	: " + rs.getString("No_Of_Beds_Full"));
            System.out.println("Ward Doctor     : " + rs.getString("Doctor1"));
            System.out.println("Ward Nurse 1	: " + rs.getString("Nurse1"));
            System.out.println("Wardboy 1       : " + rs.getString("Wardboy1"));
         }
        db.endstatement(); 
		
        System.out.println("-------------------------------------------------");
	}

    static void create_ward() throws SQLException {
    	System.out.println("\n------------ Create a New Ward ------------");
        ward W = new ward();
        W.set_ward_name();
        W.set_ward_capacity();
        W.setDoctor();
        W.setNurse1();
        W.setWardboy1();
        String statement = "INSERT INTO ward(wardname, W_Capacity, No_Of_Beds_Full, Doctor1, nurse1, wardboy1) VALUES("
                + W.wardname + "," + W.W_Capacity + "," + W.No_Of_Beds_Full + ",'" + W.Doctor1 + "','" + W.nurse1
                + "','" + W.wardboy1 + "',)";
        db.startstatement();
        db.update(statement);

        System.out.println("\n---------New ward No."+ W.wardname + " created successfully!---------");
    }

    void editWard()
    {
    	
    }
}
