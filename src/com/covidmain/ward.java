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
    
}

public class ward extends wardHelper{
    int wardname; 
    String W_type; // E/N
    int W_Capacity; // total
    int No_Of_Beds_Full; // engage
    String Doctor1;//
    String nurse1;
    String nurse2;
    String wardboy1;
    String wardboy2;

    // constructor
    ward() {
        this.wardname = 0;
        this.W_type = "";
        this.W_Capacity = 0;
        this.No_Of_Beds_Full = 0;
        this.Doctor1 = "";
        this.nurse1 = "";
        this.nurse2 = "";
        this.wardboy1 = "";
        this.wardboy2 = "";

    }

    static Scanner sc = new Scanner(System.in);

    void set_ward_name() throws SQLException {
        this.wardname = getWardCount() + 1;
    }

    void set_ward_type() {
        System.out.print("\nEnter the (E/N) type of ward: ");
        String wardEN = sc.nextLine();
        this.W_type = wardEN;
    }

    void set_ward_capacity() {
        System.out.print("\nEnter capacity of ward: ");
        int capacity = sc.nextInt();
        this.W_Capacity = capacity;
    }

  //TODO Adding isExist Checkers
    void setDoctor() {
    	sc.nextLine();
        System.out.print("\nEnter doctor ID for ward: ");
        String wardABCD = sc.nextLine();
        this.Doctor1 = wardABCD;
    }

    void setNurse1() {
        System.out.print("\nEnter nurse 1 ID: ");
        String nid = sc.nextLine();
        this.nurse1 = nid;
    }

    void setNurse2() {
        System.out.print("\nEnter nurse 2 ID: ");
        String nid = sc.nextLine();
        this.nurse2 = nid;
    }

    void setWardboy1() {
        System.out.print("\nEnter wardboy 1 ID: ");
        String nid = sc.nextLine();
        this.wardboy1 = nid;
    }

    void setWardboy2() {
        System.out.print("\nEnter wardboy 2 ID: ");
        String nid = sc.nextLine();
        this.wardboy2 = nid;
    }

    static int checkWardAvailibility() throws SQLException
    {
    	int wardcount = getWardCount();
    	int W_cap=-1, beds_full=0;
    	for(int i = 1; i<=wardcount; i++)
    	{
    		String statement = "SELECT w_capacity, no_of_beds_full FROM ward";
    		db.startstatement();
    		rs = db.execstatement(statement);
    		while(rs.next())
    		{
    			W_cap = rs.getInt("w_capacity");
    			beds_full = rs.getInt("no_of_beds_full");
    		}
    		
    		if(beds_full < W_cap)
    		{    			
    			return i;
    		}
    	}
    	
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

        //TODO checker
//        if()
//        {
//        	System.out.println("Invalid Ward No.!!!");
//        	return;
//        }
        
        String statement = "SELECT wardname, W_type, W_Capacity, No_Of_Beds_Full, Doctor1, Nurse1, Nurse2, Wardboy1, Wardboy2 FROM ward WHERE wardname =" + tempNurseID;

    	db.startstatement();
    	rs = db.execstatement(statement);
    	while(rs.next()){
            System.out.println("Ward No.		: " + rs.getInt("wardname"));
            System.out.println("Ward Type		: " + rs.getString("W_type"));
            System.out.println("Ward Capacity	: " + rs.getString("W_Capacity"));
            System.out.println("No Of Beds Full	: " + rs.getString("No_Of_Beds_Full"));
            System.out.println("Ward Doctor     : " + rs.getString("Doctor1"));
            System.out.println("Ward Nurse 1	: " + rs.getString("Nurse1"));
            System.out.println("Ward Nurse 2    : " + rs.getString("Nurse2"));
            System.out.println("Wardboy 1       : " + rs.getString("Wardboy1"));
            System.out.println("Wardboy 2       : " + rs.getString("Wardboy2"));
         }
        db.endstatement(); 
		
        System.out.println("-------------------------------------------------");
	}

    static void create_ward() throws SQLException {
    	System.out.println("\n------------ Create a New Ward ------------");
        ward W = new ward();
        W.set_ward_name();
        W.set_ward_type();
        W.set_ward_capacity();
        W.setDoctor();
        W.setNurse1();
        W.setNurse2();
        W.setWardboy1();
        W.setWardboy2();
        String statement = "INSERT INTO ward(wardname, W_type, W_Capacity, No_Of_Beds_Full, Doctor1, nurse1, nurse2, wardboy1, wardboy2) VALUES("
                + W.wardname + ",'" + W.W_type + "'," + W.W_Capacity + "," + W.No_Of_Beds_Full + ",'" + W.Doctor1 + "','" + W.nurse1
                + "','" + W.nurse2 + "','" + W.wardboy1 + "','" + W.wardboy2 + "')";
        db.startstatement();
        db.update(statement);

        System.out.println("\n---------New ward No."+ W.wardname + " created successfully!---------");
    }

    
}
