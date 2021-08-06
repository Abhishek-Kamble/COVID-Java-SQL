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
        catch(SQLException se)
        {     
		      se.printStackTrace();
		      return -1;    		      
        }
    }
	static String doctorID = "";
	
    static String DoctorIDgenerator() 
    {
        long doctorCount = getDoctorCount() + 1;
        
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
            System.out.println(CovidMain.RED + "		Error in fetching database!" + CovidMain.RESET);
        } 
        else {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            doctorID = 'D' + String.valueOf(year) + doctorCountStr;
        }

        return doctorID;
    }
    
    static boolean isDoctorExists(String tempDoctorId)
    {
    	long cnt = 0;
    	String statement = "SELECT COUNT(1) AS Count FROM dual WHERE EXISTS (SELECT 1 FROM doctor WHERE D_id = '" + tempDoctorId + "' AND d_status = 'A')";
        
    	db.startstatement();
    	rs = db.execstatement(statement);
        try {
			rs.next();
			cnt=rs.getInt("Count");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(cnt>0)
			return true;
		else
			return false;
    }
}

public class Doctor extends Doctorhelper 
{
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
		        System.out.println(CovidMain.RED + "		Error!! Can't set Doctor ID" + CovidMain.RESET);
		    } 
		    else 
		    {
		    	this.D_id = tempD_ID;
		    }
		}

	    void setDoctorName()
	    {
	        System.out.print("\n		Enter Doctor Name        : " + CovidMain.RESET);
	        String Dname = sc.nextLine();
	        this.D_name = Dname;
	    }

	    void setDoctorSlot() 
	    {
	        System.out.print("\n		Enter slot no.    : " );
	        int slot_no = sc.nextInt();
	        this.D_slot = slot_no;
	    }

	    void setDoctorPhone() 
	    {
	    	sc.nextLine();
	        System.out.print("\n		Enter Mobile No      : ");
	        String doctorMob = sc.nextLine();
	        if (checkers.mobileChecker(doctorMob))
	        {
	            this.D_phone = doctorMob;
	        } 
	        else 
	        {
	            System.out.println("	Invalid Mobile No.!!! Press Re-Enter");
	            setDoctorPhone();
	        }
	    }

	    void setDoctorAdd() 
	    {
	        System.out.print("\n		Enter Address     : ");
	        String doctoradd = sc.nextLine();
	        this.D_add = doctoradd;
	    }

	    void setDoctorMail() 
	    {
	    	System.out.print("\n		Enter E-mail     : ");
	    	String doctormail = sc.nextLine();
	    	if (checkers.emailChecker(doctormail)) {
	    		this.D_mail = doctormail;
	    	} 
	    	else 
	    	{
	    		System.out.println("		Invalid mail!! Please Re-Enter");
	    		setDoctorMail();
	    	}
	    }
	    
	    void setDoctorStatus() {
	        System.out.print("\n		Enter Doctor status (A/N)   : ");
	        char doctorstatus = sc.next().charAt(0);
	        if(doctorstatus == 'A' || doctorstatus == 'N')
	        {
	            this.D_status = doctorstatus;
	        }
	        else
	        {
	            System.out.print(CovidMain.RED + "\n	Invalid Input! Please re-enter!!" + CovidMain.RESET);
	            setDoctorStatus();
	        }
	    }

	    static void addDoctor()
	    {
	    	System.out.println(CovidMain.CYAN_BOLD + "------------------ Add a New Doctor ------------------" + CovidMain.RESET);
	        Doctor D = new Doctor();
	        D.setDoctorID();
	        D.setDoctorName();
	        D.setDoctorSlot() ;
	        D.setDoctorPhone();
	        D.setDoctorAdd();
	        D.setDoctorMail();
	        D.setDoctorStatus();
	        
	        String statement = "INSERT INTO Doctor(d_id, d_name, d_slot ,d_phone, d_add, d_mail, d_status, isRemoved, d_add_date) VALUES('" + D.D_id + "','" + D.D_name +  "','" + D.D_slot + "','" + D.D_phone + "','" + D.D_add + "','" + D.D_mail + "','" + D.D_status + "','"+ D.isRemoved + "'," + "sysdate)";
	        db.startstatement();
	        db.update(statement);

	        System.out.println(CovidMain.GREEN + "New Doctor added successfully!!" + CovidMain.RESET + " ID: " + CovidMain.YELLOW + D.D_id + CovidMain.RESET);

	    }
	    
	    static void removeDoctor()
	    {
	    	System.out.println(CovidMain.YELLOW_BOLD + "\n--------------------- Remove Doctor ---------------------" + CovidMain.RESET);
	        System.out.print(CovidMain.YELLOW + "\n		Enter Doctor ID    : " + CovidMain.RESET);
	        String tempDoctorID = sc.nextLine();
	        
	        if(!isDoctorExists(tempDoctorID))
	        {
	        	System.out.println(CovidMain.RED + "		Invalid Doctor ID!!!" + CovidMain.RESET);
	        	return;
	        }
	        
	        String statement = "UPDATE Doctor SET isremoved = 'Y' WHERE D_ID = '" + tempDoctorID +"'";
	        
	        db.startstatement();
	        db.update(statement);
			db.endupdate();
			
			System.out.println("-------------------------------------------------------");
	    }
	    
	    static void displayDoctorDetails()
	    {
            sc.nextLine();
			System.out.println(CovidMain.PURPLE_BOLD + "\n------------------- Display Doctor Details ------------------" + CovidMain.RESET);
	        System.out.print(CovidMain.YELLOW + "\n		Enter Doctor ID    : " + CovidMain.RESET);
	        String tempDoctorID = sc.nextLine();

	        if(!isDoctorExists(tempDoctorID))
	        {
	        	System.out.println(CovidMain.RED + "		Invalid Doctor ID!!!" + CovidMain.RESET);
	        	return;
	        }
	        
	        String statement = "SELECT d_id, d_name, d_slot, d_phone, d_add, d_mail, isremoved, d_status, D_add_date FROM Doctor WHERE D_ID = '" + tempDoctorID + "'";

	    	db.startstatement();
	    	rs = db.execstatement(statement);
	    	try {
				while(rs.next()){
				    System.out.println("Doctor ID		: " + CovidMain.BLUE_BOLD + rs.getString("D_ID") + CovidMain.RESET);
				    System.out.println("Doctor Name		: " + rs.getString("D_name"));
				    System.out.println("Doctor Slot 		: " + rs.getInt("D_slot"));
				    System.out.println("Doctor Phone No. : " + rs.getString("D_phone"));
				    System.out.println("Doctor Add. 		: " + rs.getString("D_add"));
				    System.out.println("Doctor E-mail	: " + rs.getString("D_mail"));
				    System.out.println("Doctor Removed?	: " + rs.getString("isremoved"));
				    System.out.println("Doctor Status    : " + rs.getString("D_status"));
				    System.out.println("Doctor Added Date    : " + rs.getString("D_add_date"));
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        db.endstatement(); 
			
	        System.out.println("-----------------------------------------------");
		}
	    
	    static void changeDoctorSlot()
	    {
	    	System.out.println(CovidMain.PURPLE_BOLD + "\n---------------------Change Doctor Slot---------------------" + CovidMain.RESET);
	    	System.out.print(CovidMain.YELLOW + "\n 		Enter Doctor ID: " + CovidMain.RESET);
	        String tempDoctorID = sc.nextLine();
	        
	        if(!isDoctorExists(tempDoctorID))
	        {
	        	System.out.println(CovidMain.RED + "		Invalid Doctor ID!!!" + CovidMain.RESET);
	        	return;
	        }
	        
	        
	        System.out.print("\n		Enter new Slot No. for "+ CovidMain.BLUE_BOLD + tempDoctorID + CovidMain.RESET + " : ");
	        int tempSlot = sc.nextInt();
	        
	        String statement = "UPDATE Doctor SET D_slot = " + tempSlot + "WHERE D_ID = '" + tempDoctorID +"'";
	        
	        db.startstatement();
	        db.update(statement);
	        db.endstatement();
	        System.out.println(CovidMain.GREEN + "		Slot No. changed successfully for Doctor ID: " + CovidMain.BLUE_BOLD + tempDoctorID + CovidMain.RESET);
	    }
	    
	    static void detailDoctorSlotWise()
	    {
	    	 System.out.println(CovidMain.PURPLE_BOLD + "\n------------------- Slot wise Doctor list -------------------" + CovidMain.RESET);
	         System.out.print(CovidMain.YELLOW + "\n		Enter Slot No.    : " + CovidMain.RESET);
	         int tempSlot = sc.nextInt();
	         String statement = "SELECT D_id AS Doctor_ID , D_name AS Doctor_Name FROM Doctor WHERE D_slot = '" + tempSlot + "'";
	         
	         if(tempSlot>3 || tempSlot<0)
	         {
	         	System.out.println(CovidMain.RED + "\n		Invalid slot no.!\n" + CovidMain.RESET);
	         	return;
	         }
		        
	     	db.startstatement();
	     	db.printDataList(statement);
	        db.endstatement();
	         
	        System.out.println("----------------------------------------------------------------");
	     }  
	  
	    static void displayAllActivedoctors()
	    {
	    	System.out.println(CovidMain.CYAN_BOLD + "----------------- Displaying Active Doctor's List -----------------" + CovidMain.RESET);
	    	String statement = "SELECT d_id AS Doctor_ID, d_slot AS SLOT, d_name AS Doctor_Name FROM Doctor WHERE isremoved = 'N' AND d_status = 'A'";  
	    	db.startstatement();
	    	db.printDataList(statement);
	        db.endstatement();
	        
	        System.out.println("----------------------------------------------------------------");

	    }
}
