package com.covidmain;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class patientHelper {
    static Dbhelper db = new Dbhelper();
    static ResultSet rs = null;

    static long getPatientCount(){
        long patient_id = 0;
        String statement = "SELECT COUNT(P_ID) as Count FROM patient_at_entry";

        db.startstatement();
        rs = db.execstatement(statement);
        try {
        rs.next();
        patient_id = rs.getInt("Count");
        }
        catch(SQLException ex) {
        	System.out.println(ex.getMessage());
        }
        return patient_id;
    }

    static String patientID = "";

    static String patientIDgenerator(){
        long patientCount = getPatientCount() + 1;

        String patientCountStr = "";
        if (patientCount >= 0 && patientCount < 10) {
            patientCountStr = "0000" + String.valueOf(patientCount);
        } else if (patientCount >= 10 && patientCount < 100) {
            patientCountStr = "000" + String.valueOf(patientCount);
        } else if (patientCount >= 100 && patientCount < 1000) {
            patientCountStr = "00" + String.valueOf(patientCount);
        } else if (patientCount >= 1000 && patientCount < 10000) {
            patientCountStr = "0" + String.valueOf(patientCount);
        } else if (patientCount >= 10000 && patientCount < 100000) {
            patientCountStr = " " + String.valueOf(patientCount);
        }

        if (patientCount == -1) {
            System.out.println("Error in fetching database!");
        } else {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            patientID = 'P' + String.valueOf(year) + patientCountStr;
        }

        return patientID;
    }
    
    static boolean isPatientExists(String tempPatientId) throws SQLException
    {
    	long cnt = 0;
    	String statement = "SELECT COUNT(1) AS Count FROM dual WHERE EXISTS (SELECT 1 FROM patient_at_entry WHERE p_id = '" + tempPatientId + "')";
        
    	db.startstatement();
    	rs = db.execstatement(statement);
        rs.next();
		cnt=rs.getInt("Count");
		
		if(cnt>0)
			return true;
		else
			return false;
    }   

    static boolean isPatientExistsandRemoved(String tempPatientId) throws SQLException
    {
    	long cnt = 0;
    	String statement = "SELECT COUNT(1) AS Count FROM dual WHERE EXISTS (SELECT 1 FROM patient_at_entry WHERE p_id = '" + tempPatientId + "' AND isremoved = 'N')";
        
    	db.startstatement();
    	rs = db.execstatement(statement);
        rs.next();
		cnt=rs.getInt("Count");
		
		if(cnt>0)
			return true;
		else
			return false;
    }
}

public class patient_at_entry extends patientHelper 
{
    String p_id;
    String p_name;
    int p_age;
    String p_phone;
    String p_add;
    String p_adhar;
    char isremoved;
    char p_status; //C/U

    patient_at_entry() {
        this.p_id = "";
        this.p_name = "";
        this.p_age = 0;
        this.p_phone = "";
        this.p_add = "";
        this.p_adhar = "";
        this.p_status = 'U';
        this.isremoved = 'N';
    }

    static Scanner sc = new Scanner(System.in);

    void setPatientID()
    {
        String tempP_ID = patientIDgenerator();
        if (tempP_ID == "") {
            System.out.println("Error!! Can't set Patient ID.");
        } else {
            this.p_id = tempP_ID;
        }
    }

    void setPatientName()
    {
        System.out.print("\nEnter Name        : ");
        String patientname = sc.nextLine();
        this.p_name = patientname;
    }

    void setPatientAge() 
    {
        System.out.print("\nEnter Age of patient     : ");
        int PAge = sc.nextInt();
        this.p_age = PAge;
    }
    
    void setPatientStatus() 
    {
    	System.out.print("\nEnter Patient status (A/N)   : ");
        char patientstatus = sc.next().charAt(0);
        if(patientstatus == 'A' || patientstatus == 'N')
        {
            this.p_status = patientstatus;
        }
        else
        {
            System.out.print("\n!Re-");
            setPatientStatus();
        }
    }

    void setPatientPhone() 
    {
        sc.nextLine();
        System.out.print("\nEnter Mobile No    : ");
        String patientMob = sc.nextLine();
        if (checkers.mobileChecker(patientMob)) {
            this.p_phone = patientMob;
        } else {
            System.out.println("Invalid Mobile No.!!!");
            System.out.print("Press ENTER!!");
            setPatientPhone();
        }
    }

    void setPatientAdd() 
    {
        System.out.print("\nEnter Address     : ");
        String patientadd = sc.nextLine();
        this.p_add = patientadd;
    }

    void setPatientAdhar() 
    {
        System.out.print("\nEnter Adhar No.     : ");
        
        String patientadhar = sc.nextLine();
        this.p_adhar = patientadhar;
    }

    
    static void addPatient(int wardname)
    {
        patient_at_entry P = new patient_at_entry();
        P.setPatientID();
        P.setPatientName();
        P.setPatientAge();
        P.setPatientPhone();
        P.setPatientAdd();
        P.setPatientAdhar();
        
        String statement = "INSERT INTO patient_at_entry(p_id, p_name, p_age, p_phone, p_add, p_status, p_adhar, isremoved, P_ward, p_adm_date) VALUES('" + P.p_id + "','" + P.p_name + "','" + P.p_age + "','" + P.p_phone + "','" + P.p_add + "', 'U', '"+ P.p_adhar + "', 'N'," + wardname + ", sysdate)";
        db.startstatement();
        db.update(statement);
        System.out.println("New Patient added successfully!! \nWard No.: " + wardname + "\nID: " + P.p_id);
        
    }

    static void displayPatientDetails() throws SQLException
    {
    	System.out.println("\n----------- Display Patient Details -----------");
        System.out.print("\nEnter Patient ID    : ");
        String tempPatientID = sc.nextLine();

        if(!isPatientExists(tempPatientID))
        {
        	System.out.println("Invalid Patient ID!!!");
        	return;
        }

        String statement = "SELECT p_id, p_name, p_age ,p_phone, p_add , p_Status ,p_adhar , p_adm_date  FROM patient_at_entry WHERE P_ID = '"
                + tempPatientID + "'";

        db.startstatement();
        rs = db.execstatement(statement);
        while (rs.next())
        {
            System.out.println("Patient ID   : " + rs.getString("p_ID"));
            System.out.println("Patient Name   : " + rs.getString("p_name"));
            System.out.println("Patient Age.   : " + rs.getString("p_age"));
            System.out.println("Patient Phone No.   : " + rs.getString("p_phone"));
            System.out.println("Patient Add.   : " + rs.getString("p_add"));
             System.out.println("Patient Status   : " + rs.getString("p_Status"));
             System.out.println("Patient Adhar   : " + rs.getString("p_adhar"));
             System.out.println("Patient Admit Date   : " + rs.getString("P_adm_date"));
        }
        db.endstatement();

        System.out.println("-------------------------------------------------");
    }

    static void dischargePatient(String tempPatientID) throws SQLException  
    {
        db.startstatement();
        String statement = "UPDATE patient_at_entry SET isremoved = 'Y', p_dis_date = SYSDATE WHERE P_ID = '" + tempPatientID +"'";
        db.update(statement);
		db.endupdate();
    }

	static void searchPatient() {
		// TODO 
		System.out.println("\n-------------------- Search an active patient --------------------");
		System.out.print("Enter first name of patient: ");
		String firstName = sc.nextLine();
		
		String statement = "SELECT p_id AS Patient_ID, p_ward AS WARD, isremoved AS REM, p_name AS Patient_Name FROM patient_at_entry WHERE p_name LIKE '%" + firstName + "%' ORDER BY isremoved, P_ID, p_adm_date";
		try
		{
			db.startstatement();
	    	db.printDataList(statement);
	        db.endstatement();
		}catch(Exception E)
		{
			System.out.println("\nNo Matching Records Found!!");
			return;
		}
        
        System.out.println("-------------------------------------------------------");
	}

	static void wardWisePatientList()
	{
		System.out.println("\n-------------- Wardwise Patient list --------------");
		System.out.print("Enter Ward No.:");
		int wardNo = sc.nextInt();
		
		//TODO add input check
		String statement = "SELECT p_id AS PATIENT_ID, p_age AS AGE, p_name AS PATIENT_NAME FROM patient_at_entry WHERE wardname = " + wardNo + " AND isremoved = 'N'";  
    	db.startstatement();
    	db.printDataList(statement);
        db.endstatement();
        
        System.out.println("----------------------------------------------------------");
	}
}
