package com.covidmain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class wardHelper{
	static Dbhelper db = new Dbhelper();
    static ResultSet rs = null;
    
    static int getWardCount()
    {
    	int wardcount = 0;
    	String statement = "SELECT COUNT(wardname) as Count FROM ward";
        
    	db.startstatement();
    	rs = db.execstatement(statement);
        try {
			rs.next();
	        wardcount = rs.getInt("Count");
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
    
    static boolean isWardExists(int wardname)
    {
    	long cnt = 0;
    	String statement = "SELECT COUNT(1) AS Count FROM dual WHERE EXISTS (SELECT 1 FROM ward WHERE wardname = " + wardname + ")";
        
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

    void set_ward_name(){
        this.wardname = getWardCount() + 1;
    }

    void set_ward_capacity() {
        System.out.print(CovidMain.YELLOW + "\n		Enter capacity of ward: " + CovidMain.RESET);
        int capacity = sc.nextInt();
        this.W_Capacity = capacity;
    }

    void setDoctor(){
    	sc.nextLine();
        System.out.print(CovidMain.YELLOW + "\n		Enter doctor ID for ward: " + CovidMain.RESET);
        String wardABCD = sc.nextLine();
        this.Doctor1 = wardABCD;
        if(Nurse.isNurseExists(wardABCD))
        {
            this.nurse1 = wardABCD;
        }
        else
        {
        	System.out.println(CovidMain.RED + "\n		Invalid Doctor ID! Please Renter Doctor Id" + CovidMain.RESET);
        	setDoctor();
        }        
    }

    void setNurse1(){
        System.out.print(CovidMain.YELLOW + "\n		Enter Nurse 1 ID: " + CovidMain.RESET);
        String nid = sc.nextLine();
        if(Nurse.isNurseExists(nid))
        {
            this.nurse1 = nid;
        }
        else
        {
        	System.out.println(CovidMain.RED + "\n		Invalid Nurse ID Please Renter Nurse Id" + CovidMain.RESET);
        	setNurse1();
        }
    }

    void setWardboy1(){
        System.out.print("\n		Enter wardboy 1 ID: ");
        String nid = sc.nextLine();
        
        if(Wardboy.isWardboyExists(nid))
        {
        	this.wardboy1 = nid;
        }
        else
        {
        	System.out.println(CovidMain.RED + "\n		Invalid Wardboy ID! Please Renter Doctor Id!" + CovidMain.RESET);
        	setWardboy1();
        }
    }

    static int checkWardAvailibility() 
    {
		int res = -1;
		String statement = "SELECT wardname FROM ward WHERE w_capacity > no_of_beds_full";
		db.startstatement();
		rs = db.execstatement(statement);
		try {
			while(rs.next())
			{
				res = rs.getInt("wardname");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(res > 0)
			return res;
    	return -1;
    }
    
    static void displayAllWardStatus()
    {
    	System.out.println(CovidMain.PURPLE_BOLD + "\n-------------------- Display All Ward Status --------------------" + CovidMain.RESET);
        String statement = "SELECT wardname AS W_No, W_type AS Type, W_Capacity AS Cap, No_Of_Beds_Full AS Full_Beds FROM ward";  
    	db.startstatement();
    	db.printDataList(statement);
        db.endstatement();
        
        System.out.println("---------------------------------------------------------------------");
    }
    
    static void displayWardDetails(){
		System.out.println(CovidMain.PURPLE_BOLD + "\n-------------------- Display Ward Details --------------------" + CovidMain.RESET);
        System.out.print("\nEnter Ward No.    : ");
        int tempNurseID = sc.nextInt();

        if(!isWardExists(tempNurseID))
        {
        	System.out.println(CovidMain.RED + "		Invalid Ward No.!!!" + CovidMain.RESET);
        	return;
        }
        
        String statement = "SELECT wardname, W_type, W_Capacity, No_Of_Beds_Full, Doctor1, Nurse1, Wardboy1 FROM ward WHERE wardname = " + tempNurseID;

    	db.startstatement();
    	rs = db.execstatement(statement);
    	try {
			while(rs.next()){
			    System.out.println("Ward No.		: " + rs.getInt("wardname"));
			    System.out.println("Ward Capacity	: " + rs.getString("W_Capacity"));
			    System.out.println("No Of Beds Full	: " + rs.getString("No_Of_Beds_Full"));
			    System.out.println("Ward Doctor     : " + rs.getString("Doctor1"));
			    System.out.println("Ward Nurse 1	: " + rs.getString("Nurse1"));
			    System.out.println("Wardboy 1       : " + rs.getString("Wardboy1"));
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        db.endstatement(); 
		
        System.out.println("------------------------------------------------------------------");
	}

    static void create_ward(){
    	System.out.println(CovidMain.PURPLE_BOLD + "\n-------------------- Create a New Ward --------------------" + CovidMain.RESET);
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

        System.out.println(CovidMain.GREEN + "\n--------- New ward No."+ CovidMain.BLUE_BOLD + W.wardname + CovidMain.PURPLE_BOLD + CovidMain.RESET + " created successfully! ---------");
    }
}
