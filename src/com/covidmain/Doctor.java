package com.covidmain;
import java.sql.*;
import java.util.*;

class Doctorhelper{
	static Dbhelper db = new Dbhelper();
	static ResultSet rs = null;
	
	static long getDoctorCount()
    {
    	long doctor_id = 0;
    	String statement = "SELECT COUNT(D_ID) as Count FROM Doctor";
        try 
        {
        	db.startstatement();
        	rs = db.execstatement(statement);
        	rs.next();
		    doctor_id=rs.getInt("Count");
		    return doctor_id;
		}
        catch(SQLException se){
		      
		      se.printStackTrace();
		      return -1;    		      
        }
    }
	static String doctorID = "";
	
    static String DoctorIDgenerator() 
    {
        long doctorCount = DoctorSQL.getDoctorCount() + 1;
//        System.out.println("doctorCount: " + doctorCount);
        
        String doctorCountStr = "";
        
        if (doctorCount >= 0 && doctorCount < 10) {
            doctorCountStr = "00" + String.valueOf(doctorCount);
        }else 
        	if(doctorCount >= 10 && doctorCount < 100) {
            doctorCountStr = "0" + String.valueOf(doctorCount);
        }else 
        	if(doctorCount >= 100 && doctorCount < 1000) {
            doctorCountStr = "" + String.valueOf(doctorCount);
        }
        
        if (doctorCount == -1) {
            System.out.println("Error in fetching database!");
        } 
        else {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            doctorID = 'D' + String.valueOf(year) + doctorCountStr;
        }

        return doctorID;
    }
    
}

public class Doctor extends Doctorhelper {
    String D_id;
    String D_name;
    int D_slot;
    String D_phone;
    String D_add;
    String D_mail;
    char D_status;
    char isRemoved;

    Doctor() 
	{
		this.D_id = "";
		this.D_name = "";
		this.D_slot = 0;
		this.D_phone = "";
		this.D_add = "";
		this.D_mail = "";
		this.D_status = 'N';
        this.isRemoved = 'N';
	}

    static Scanner sc = new Scanner(System.in);

	void setDoctorID() 
	{
	    String tempD_ID = DoctorIDgenerator();
	    if (tempD_ID == "") 
	    {
	        System.out.println("Error!! Can't set Doctor ID");
	    } 
	    else 
	    {
	    	this.D_id = tempD_ID;
	    }
	}

    void setDoctorName()
    {
        sc.nextLine();
        System.out.print("\nEnter Doctor Name        : ");
        String Dname = sc.nextLine();
        this.D_name = Dname;
    }

    void setDoctorSlot() 
    {
        System.out.print("\nEnter slot no.    : ");
        int slot_no = sc.nextInt();
        this.D_slot = slot_no;
    }

    void setDoctorPhone() 
    {
    	sc.nextLine();
        System.out.print("\nEnter Mobile No    : ");
        String doctorMob = sc.nextLine();
        if (checkers.mobileChecker(doctorMob))
        {
            this.D_phone = doctorMob;
        } 
        else 
        {
            System.out.println("Invalid Mobile No.!!!");
            System.out.print("Press Re-");
            setDoctorPhone();
        }
    }

	    void setDoctorAdd() 
	    {
	        System.out.print("\nEnter Address     : ");
	        String doctoradd = sc.nextLine();
	        this.D_add = doctoradd;
	    }

	    void setDoctorMail() 
	    {
	    	System.out.print("\nEnter E-mail     : ");
	    	String doctormail = sc.nextLine();
	    	if (checkers.emailChecker(doctormail)) {
	    		this.D_mail = doctormail;
	    	} 
	    	else 
	    	{
	    		System.out.println("Invalid mail!!");
	    		System.out.print("Please Re-");
	    		setDoctorMail();
	    	}
	    }
	    void setDoctorStatus() {
	        System.out.print("\nEnter Doctor status (A/N)   : ");
	        char doctorstatus = sc.next().charAt(0);
	        if(doctorstatus == 'A' || doctorstatus == 'N')
	        {
	            this.D_status = doctorstatus;
	        }
	        else
	        {
	            System.out.print("\n!Re-");
	            setDoctorStatus();
	        }
	    }

	    static void addDoctor() 
	    {
			    Doctor D = new Doctor();
			    D.setDoctorID();
			    D.setDoctorName();
			    D.setDoctorSlot();
			    D.setDoctorPhone();
			    D.setDoctorAdd();
			    D.setDoctorMail();
			    D.setDoctorStatus();
		    
			    String statement = "INSERT INTO Doctor(d_id, d_name, d_slot, d_phone, d_add, d_mail,isRemoved, d_status) VALUES ('" + D.D_id + "','" + D.D_name + "'," + D.D_slot + ",'" + D.D_phone + "','" + D.D_add + "','" + D.D_mail + "','" + D.isRemoved + "','" + D.D_status + "')";
			    db.startstatement();
			    db.update(statement);
			    
			    System.out.println("New Doctor added successfully!! ID: " + D.D_id);
	    }
	    
	    static void removeDoctor() 
	    {
	        System.out.println("**** Remove Doctor ****");
	        System.out.print("\nEnter Doctor ID    : ");
	        String tempDoctorID = sc.nextLine();
	        
	        String statement = "UPDATE Doctor SET isremoved = 'Y' WHERE D_ID = '" + tempDoctorID +"'";
	        
	        db.startstatement();
	        db.update(statement);
			db.endupdate();
			
			System.out.println("-------------------------------------------------");
	    }
	    
	    public static void displayDoctorDetails() throws SQLException
	    {
			System.out.println("** Display Doctor Details **");
	        System.out.print("\nEnter Doctor ID    : ");
	        String tempDoctorID = sc.nextLine();

	        String statement = "SELECT d_id, d_name, d_slot, d_phone, d_add, d_mail , isremoved, d_status FROM Doctor WHERE D_ID = '" + tempDoctorID + "'";

	    	db.startstatement();
	    	rs = db.execstatement(statement);
	    	while(rs.next()){
	            System.out.println("Doctor ID		: " + rs.getString("D_ID"));
	            System.out.println("Doctor Name		: " + rs.getString("D_name"));
	            System.out.println("Doctor Slot 		: " + rs.getInt("D_slot"));
	            System.out.println("Doctor Phone No. : " + rs.getString("D_phone"));
	            System.out.println("Doctor Add. 		: " + rs.getString("D_add"));
	            System.out.println("Doctor E-mail	: " + rs.getString("D_mail"));
	            System.out.println("Doctor Removed?	: " + rs.getString("isremoved"));
	            System.out.println("Doctor Status    : " + rs.getString("D_status"));
	         }
	        db.endstatement(); 
			
	        System.out.println("-----------------------------------------------");
		}
	    
	    
	    static void changeDoctorSlot()
	    {
	    	System.out.println("***Change Doctor Slot***");
	    	System.out.print("Enter Doctor ID: ");
	        String tempDoctorID = sc.nextLine();
	        System.out.print("\nEnter new Slot No. for"+ tempDoctorID + " : ");
	        int tempSlot = sc.nextInt();
	        
	        String statement = "UPDATE Doctor SET D_slot = " + tempSlot + "WHERE D_ID = '" + tempDoctorID +"'";
	        
	        db.startstatement();
	        db.update(statement);
	        db.endstatement();
	        System.out.println("Slot No. changed successfully for Doctor ID: " + tempDoctorID);
	    }
	    static void detailDoctorSlotWise()
	    {
	    	 System.out.println("\n***Slot wise Doctor list***");
	         System.out.print("\nEnter Slot No.    : ");
	         int tempSlot = sc.nextInt();
	         String statement = "SELECT D_id, D_name FROM Doctor WHERE D_slot = '" + tempSlot + "'";
	         
	     	db.startstatement();
	     	db.printDataList(statement);
	         db.endstatement();
	         
	         System.out.println("-----------------------------------------------");
	     }   
	     public static void main(String[] args) throws SQLException
	     {
	         System.out.println("\nRunning ");
//	       System.out.println(DoctorIDgenerator());
//	        Doctor.displayDoctorDetails();
            //Doctor.removeDoctor();
	    	Doctor.addDoctor();
	       // Doctor.detailDoctorSlotWise();
	       // Doctor.changeDoctorSlot();
	    }
}
