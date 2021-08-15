package com.covidmain;

import java.sql.*;
import java.util.*;
class wardboyHelper {
    static Dbhelper db = new Dbhelper();
    static ResultSet rs = null;

    static long getWardboyCount()
    {
    	long wardboy_id = 0;
    	String statement = "SELECT COUNT(W_ID) as Count FROM wardboy";
        
    	db.startstatement();
    	rs = db.execstatement(statement);
        try {
			rs.next();
	        wardboy_id = rs.getInt("Count");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wardboy_id;
    }

    static String wardboyID = "";

    static String wardboyIDgenerator(){
        long wardboyCount = getWardboyCount() + 1;
        String wardboyCountStr = "";
        if (wardboyCount >= 0 && wardboyCount < 10) {
            wardboyCountStr = "00" + String.valueOf(wardboyCount);
        } else if (wardboyCount >= 10 && wardboyCount < 100) {
            wardboyCountStr = "0" + String.valueOf(wardboyCount);
        } else if (wardboyCount >= 100 && wardboyCount < 1000) {
            wardboyCountStr = "" + String.valueOf(wardboyCount);
        }

        if (wardboyCount == -1) {
            System.out.println(CovidMain.RED + "		Error in fetching database!" + CovidMain.RESET);
        } else {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            wardboyID = 'W' + String.valueOf(year) + wardboyCountStr;
        }

        return wardboyID;
    }
    
    
    static boolean isWardboyExists(String tempWardboyId)
    {
    	long cnt = 0;
    	String statement = "SELECT COUNT(1) AS Count FROM dual WHERE EXISTS (SELECT 1 FROM wardboy WHERE w_id = '" + tempWardboyId + "' AND w_status = 'A')";
        
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

public class Wardboy extends wardboyHelper {
    String W_id;
    String W_name;
    int W_slot;
    String W_phone;
    String W_add;
    String W_mail;
    char isRemoved;
    char W_status;

    Wardboy() {
        this.W_id = "";
        this.W_name = "";
        this.W_slot = 0;
        this.W_phone = "";
        this.W_add = "";
        this.W_mail = "";
        this.W_status = 'N';
        this.isRemoved = 'N';
    }

    static Scanner sc = new Scanner(System.in);

    void setWardboyID(){
        String tempW_ID = wardboyIDgenerator();
        if (tempW_ID == "") {
            System.out.println( CovidMain.RED + "		Error!! Can't set Wardboy ID" + CovidMain.RESET);
        } else {
            this.W_id = tempW_ID;
        }
    }

    void setWardboyName() {
        System.out.print("\nEnter Wardboy Name        : ");
        String wardboyname = sc.nextLine();
        this.W_name = wardboyname;
    }

    void setWardboySlot() {
        System.out.print("\nEnter slot no.    : ");
        int slot_no = sc.nextInt();
        this.W_slot = slot_no;
    }

    void setWardboyPhone() {
        sc.nextLine();
        System.out.print("\nEnter Mobile No    : ");
        String wardboyMob = sc.nextLine();
        if(wardboyMob.equals(""))
        {
        	wardboyMob = sc.nextLine();
        }
        if (checkers.mobileChecker(wardboyMob)) {
            this.W_phone = wardboyMob;
        } else {
        	System.out.println( CovidMain.RED + "		Invalid Mobile No.!!! Press ENTER!!" +  CovidMain.RESET);
            setWardboyPhone();
        }
    }

    
    void setWardboyAdd() {
        System.out.print("\nEnter Address     : ");
        String wardboyadd = sc.nextLine();
        this.W_add = wardboyadd;
    }

    void setWardboyMail() {
        System.out.print("\nEnter E-mail     : ");
        String wardboyemail = sc.nextLine();
        if (checkers.emailChecker(wardboyemail)) {
            this.W_mail = wardboyemail;
        } else {
            System.out.println(CovidMain.RED + "Invalid mail!! PRESS ENTER.." + CovidMain.RESET);
            setWardboyMail();
        }
    }
    
    

    void setWardboyStatus() {
        System.out.print("\nEnter Wardboy status (A/N)   : ");
        char wardboystatus = sc.next().charAt(0);
        if (wardboystatus == 'A' || wardboystatus == 'N') {
            this.W_status = wardboystatus;
        } else {
            System.out.print(CovidMain.RED + "\n		Invalid Input! Please RENTER" + CovidMain.RESET);
            setWardboyStatus();
        }
    } 
    
    static void addWardboy(){
        System.out.println(CovidMain.CYAN_BOLD + "----------------------- Add Wardboy -----------------------" + CovidMain.RESET);
        Wardboy W = new Wardboy();
        W.setWardboyID();
        W.setWardboyName();
        W.setWardboySlot();
        W.setWardboyPhone();
        W.setWardboyAdd();
        W.setWardboyStatus();

        String statement = "INSERT INTO wardboy(W_id, W_name, W_slot, W_phone, W_add, isremoved, W_status,W_add_date) VALUES('"
                + W.W_id + "','" + W.W_name + "'," + W.W_slot + ",'" + W.W_phone + "','" + W.W_add + "','" + W.isRemoved + "','" + W.W_status + "'," + " sysdate)";
        db.startstatement();
        db.update(statement);

        System.out.println(CovidMain.GREEN + "\n		New wardboy added successfully!! ID: " + CovidMain.BLUE_BOLD +  W.W_id + CovidMain.RESET);

    }

    static void removeWardboy(){
        System.out.println(CovidMain.CYAN_BOLD + "----------------------- Remove Wardboy -----------------------" + CovidMain.RESET);
        System.out.print("\nEnter Wardboy ID    : ");
        String tempWardboyID = sc.nextLine();
        if(tempWardboyID.equals(""))
        	tempWardboyID = sc.nextLine();
        
        if(!isWardboyExists(tempWardboyID))
        {
        	System.out.println(CovidMain.RED + "		Invalid Wardboy ID!!!" + CovidMain.RESET);
        	return;
        }
        
        String statement = "UPDATE wardboy SET isremoved = 'Y' WHERE W_ID = '" + tempWardboyID + "'";
        db.startstatement();
        db.update(statement);
        db.endupdate();
        System.out.println(CovidMain.RED + "\n--------------------- Wardboy Removed --------------------" + CovidMain.RESET);

    }

    public static void displayWardboyDetails(){
        System.out.println(CovidMain.CYAN_BOLD + "----------------------- Display Wardboy Details -----------------------" + CovidMain.RESET);
        System.out.print(CovidMain.YELLOW + "\n		Enter Wardboy ID    : " + CovidMain.RESET);
        String tempWardboyID = sc.nextLine();
        
        if(!isWardboyExists(tempWardboyID))
        {
        	System.out.println(CovidMain.RED + "		Invalid Wardboy ID!!!" + CovidMain.RESET);
        	return;
        }
        
        String statement = "SELECT w_id, w_name, w_slot, w_phone, w_add, isremoved, w_status, W_add_date FROM wardboy WHERE W_ID = '" + tempWardboyID + "'";
        db.startstatement();
    	rs = db.execstatement(statement);
        try {
			while (rs.next()) {
			    System.out.println("Wardboy ID			: " + CovidMain.BLUE_BOLD + rs.getString("W_ID") + CovidMain.RESET);
			    System.out.println("Wardboy Name		: " + rs.getString("W_name"));
			    System.out.println("Wardboy Slot 		: " + rs.getInt("W_slot"));
			    System.out.println("Wardboy Phone No. 	: " + rs.getString("W_phone"));
			    System.out.println("Wardboy Add. 		: " + rs.getString("W_add"));
			    System.out.println("Wardboy Removed?	: " + rs.getString("isremoved"));
			    System.out.println("Wardboy Status    	: " + rs.getString("W_status"));
			    System.out.println("Wardboy Added Date    : " + rs.getString("W_add_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        db.endstatement();
        System.out.println("----------------------------------------------------");
    }

    static void detailWardboySlotWise() {
    	System.out.println(CovidMain.CYAN_BOLD + "\n----------------------- Slot wise wardboy list -----------------------" + CovidMain.RESET);
        System.out.print(CovidMain.YELLOW + "\n		Enter Slot No.    : " + CovidMain.RESET);
        int tempSlot = sc.nextInt();
        
        if(tempSlot>3 || tempSlot<0)
        {
        	System.out.println(CovidMain.RED + "\nInvalid slot no.\n" + CovidMain.RESET);
        	return;
        }

        String statement = "SELECT W_id AS W_Boy_ID, W_name FROM wardboy WHERE W_slot = '" + tempSlot + "'";
        
    	db.startstatement();
    	db.printDataList(statement);
        db.endstatement();
        
        System.out.println("-----------------------------------------------------------");
    }
   
    static void changeWardboySlot()
    {
    	System.out.println(CovidMain.CYAN_BOLD + "----------------------- Change Wardboy Slot -----------------------" + CovidMain.RESET);
    	System.out.print(CovidMain.YELLOW + "		Enter Wardboy ID: "  + CovidMain.RESET);
        String tempWardboyID = sc.nextLine();
        if(tempWardboyID.equals(""))
        	tempWardboyID = sc.nextLine();
        
        if(!isWardboyExists(tempWardboyID))
        {
        	System.out.println(CovidMain.RED + "		Invalid Wardboy ID!!!" + CovidMain.RESET);
        	return;
        }
        
        System.out.print(CovidMain.YELLOW + "\n		Enter new Slot No. for "+ CovidMain.BLUE_BOLD + tempWardboyID + CovidMain.RESET + " : ");
        int tempSlot = sc.nextInt();
        
        //TODO fixing exceptions
        String statement = "UPDATE wardboy SET W_slot = " + tempSlot + " WHERE W_ID = '" + tempWardboyID +"'";
        
        db.startstatement();
        db.update(statement);
        db.endstatement();
        
        System.out.println(CovidMain.GREEN + "Slot No. changed successfully for Wardboy ID: " + CovidMain.BLUE_BOLD + tempWardboyID + CovidMain.RESET);
    }
    
    static void displayAllActiveWardboy()
    {
    	System.out.println(CovidMain.CYAN_BOLD + "\n--------------------- Active Wardboys List ---------------------" + CovidMain.RESET);
    	String statement = "SELECT w_id AS W_Boy_ID, w_slot, w_name FROM wardboy WHERE isremoved = 'N' AND w_status = 'A' ";  
    	db.startstatement();
    	db.printDataList(statement);
        db.endstatement();
        
        System.out.println("---------------------------------------------------------");

    }

    static void changeWardboyStat()
    {
    	System.out.println(CovidMain.CYAN_BOLD + "\n--------------------- Change Wardboy Status ---------------------" + CovidMain.RESET);
    	System.out.print(CovidMain.YELLOW + "		Enter wardboy ID: " + CovidMain.RESET);
        String tempWardboyID = sc.nextLine();
        if(tempWardboyID.equals(""))
        	tempWardboyID = sc.nextLine();
        
        if(!isWardboyExists(tempWardboyID))
        {
        	System.out.println(CovidMain.RED + "		Invalid Wardboy ID!!!" + CovidMain.RESET);
        	return;
        }
        
        System.out.print(CovidMain.YELLOW + "\nEnter new status for ID: "+ CovidMain.BLUE_BOLD + tempWardboyID + " : " + CovidMain.RESET);
        char tempStat = sc.nextLine().charAt(0);
        
        if(tempStat!='A' || tempStat!='N')
        {
        	String statement = "UPDATE wardboy SET w_status = " + tempStat + "WHERE w_ID = '" + tempWardboyID +"'";        
            db.startstatement();
            db.update(statement);
            db.endstatement();

            System.out.println(CovidMain.GREEN + "Slot No. changed successfully for wardboy ID: " + CovidMain.BLUE_BOLD + tempWardboyID + CovidMain.RESET);
        }
        else
        {
        	System.out.println(CovidMain.RED + "\n		Invalid status entered!!" + CovidMain.RESET);
        	return;
        }
        
        
    }
}
    
