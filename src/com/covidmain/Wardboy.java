package com.covidmain;

import java.sql.*;
import java.util.*;
class wardboyHelper {
    static Dbhelper db = new Dbhelper();
    static ResultSet rs = null;

    static long getWardboyCount() throws SQLException
    {
    	long wardboy_id = 0;
    	String statement = "SELECT COUNT(W_ID) as Count FROM wardboy";
        
    	db.startstatement();
    	rs = db.execstatement(statement);
        rs.next();
        wardboy_id = rs.getInt("Count");
		return wardboy_id;
    }

    static String wardboyID = "";

    static String wardboyIDgenerator() throws SQLException {
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
            System.out.println("Error in fetching database!");
        } else {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            wardboyID = 'W' + String.valueOf(year) + wardboyCountStr;
        }

        return wardboyID;
    }
    
    
    static boolean isWardboyExists(String tempWardboyId) throws SQLException
    {
    	long cnt = 0;
    	String statement = "SELECT COUNT(1) AS Count FROM dual WHERE EXISTS (SELECT 1 FROM wardboy WHERE w_id = '" + tempWardboyId + "' AND isremoved = 'N')";
        
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

    void setWardboyID() throws SQLException {
        String tempW_ID = wardboyIDgenerator();
        if (tempW_ID == "") {
            System.out.println("Error!! Can't set Wardboy ID");
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
        if (checkers.mobileChecker(wardboyMob)) {
            this.W_phone = wardboyMob;
        } else {
        	System.out.println("Invalid Mobile No.!!!");
            System.out.print("Press ENTER!!");
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
            System.out.println("Invalid mail!!");
            System.out.print("Please Re-");
            setWardboyMail();
        }
    }
    
    

    void setWardboyStatus() {
        System.out.print("\nEnter Wardboy status (A/N)   : ");
        char wardboystatus = sc.next().charAt(0);
        if (wardboystatus == 'A' || wardboystatus == 'N') {
            this.W_status = wardboystatus;
        } else {
            System.out.print("\n ! Re-");
            setWardboyStatus();
        }
    }

   
    
    
    
    
    static void addWardboy() throws SQLException {
        Wardboy W = new Wardboy();
        W.setWardboyID();
        W.setWardboyName();
        W.setWardboySlot();
        W.setWardboyPhone();
        W.setWardboyAdd();
//        W.setWardboyMail();
        W.setWardboyStatus();

        String statement = "INSERT INTO wardboy(W_id, W_name, W_slot, W_phone, W_add, isremoved, W_status,W_add_date) VALUES('"
                + W.W_id + "','" + W.W_name + "'," + W.W_slot + ",'" + W.W_phone + "','" + W.W_add + "','" + W.isRemoved + "','" + W.W_status + "'," + " sysdate)";
        db.startstatement();
        db.update(statement);

        System.out.println("New wardboy added successfully!! ID: " + W.W_id);

    }

    static void removeWardboy() throws SQLException {
        System.out.println("***			Remove Wardboy		 ***");
        System.out.print("\nEnter Wardboy ID    : ");
        String tempWardboyID = sc.nextLine();
        
        if(!isWardboyExists(tempWardboyID))
        {
        	System.out.println("Invalid Wardboy ID!!!");
        	return;
        }
        
        String statement = "UPDATE wardboy SET isremoved = 'Y' WHERE W_ID = '" + tempWardboyID + "'";
        db.startstatement();
        db.update(statement);
        db.endupdate();
        System.out.println("\n---------------------Wardboy Removed--------------------");

    }

    
    public static void displayWardboyDetails() throws SQLException {
        System.out.println("** Display Wardboy Details **");
        System.out.print("\nEnter Wardboy ID    : ");
        String tempWardboyID = sc.nextLine();
        
        if(!isWardboyExists(tempWardboyID))
        {
        	System.out.println("Invalid Wardboy ID!!!");
        	return;
        }
        
        String statement = "SELECT w_id, w_name, w_slot, w_phone, w_add, isremoved, w_status, W_add_date FROM wardboy WHERE W_ID = '" + tempWardboyID + "'";
        db.startstatement();
    	rs = db.execstatement(statement);
        while (rs.next()) {
            System.out.println("Wardboy ID			: " + rs.getString("W_ID"));
            System.out.println("Wardboy Name		: " + rs.getString("W_name"));
            System.out.println("Wardboy Slot 		: " + rs.getInt("W_slot"));
            System.out.println("Wardboy Phone No. 	: " + rs.getString("W_phone"));
            System.out.println("Wardboy Add. 		: " + rs.getString("W_add"));
//            System.out.println("Wardboy E-mail		: " + rs.getString("W_mail"));
            System.out.println("Wardboy Removed?	: " + rs.getString("isremoved"));
            System.out.println("Wardboy Status    	: " + rs.getString("W_status"));
            System.out.println("Wardboy Added Date    : " + rs.getString("W_add_date"));
        }
        db.endstatement();
        System.out.println("-----------------------------------------------");
    }

    static void detailWardboySlotWise() {
    	System.out.println("\n***Slot wise wardboy list***");
        System.out.print("\nEnter Slot No.    : ");
        int tempSlot = sc.nextInt();
        
        if(tempSlot>3 || tempSlot<0)
        {
        	System.out.println("\nInvalid slot no.\n");
        	return;
        }

        String statement = "SELECT W_id, W_name FROM wardboy WHERE W_slot = '" + tempSlot + "'";
        
    	db.startstatement();
    	db.printDataList(statement);
        db.endstatement();
        
        System.out.println("-----------------------------------------------");
    }
    
    
    
    static void changeWardboySlot() throws SQLException
    {
    	System.out.println("***Change Wardboy Slot***");
    	System.out.print("Enter Wardboy ID: ");
        String tempWardboyID = sc.nextLine();
        
        if(!isWardboyExists(tempWardboyID))
        {
        	System.out.println("Invalid Wardboy ID!!!");
        	return;
        }
        
        System.out.print("\nEnter new Slot No. for "+ tempWardboyID + " : ");
        int tempSlot = sc.nextInt();
        
        //TODO fixing exceptions
        String statement = "UPDATE wardboy SET W_slot = " + tempSlot + " WHERE W_ID = '" + tempWardboyID +"'";
        
        db.startstatement();
        db.update(statement);
        db.endstatement();
        
        System.out.println("Slot No. changed successfully for Wardboy ID: " + tempWardboyID);
    }
    
    static void displayAllActiveWardboy()
    {
    	String statement = "SELECT w_id, w_slot, w_name FROM wardboy WHERE isremoved = 'N' AND n_status = 'A' ";  
    	db.startstatement();
    	db.printDataList(statement);
        db.endstatement();
        
        System.out.println("-----------------------------------------------");

    }

    static void changeWardboyStat() throws SQLException
    {
    	System.out.println("\n---------------------Change Wardboy Status---------------------");
    	System.out.print("Enter wardboy ID: ");
        String tempWardboyID = sc.nextLine();
        
        if(!isWardboyExists(tempWardboyID))
        {
        	System.out.println("Invalid Wardboy ID!!!");
        	return;
        }
        
        System.out.print("\nEnter new status for ID: "+ tempWardboyID + " : ");
        char tempStat = sc.nextLine().charAt(0);
        
        if(tempStat!='A' || tempStat!='N')
        {
        	System.out.println("\nInvalid status entered!!\n");
        	return;
        }
        
        String statement = "UPDATE wardboy SET w_status = " + tempStat + "WHERE w_ID = '" + tempWardboyID +"'";        
        db.startstatement();
        db.update(statement);
        db.endstatement();

        System.out.println("Slot No. changed successfully for wardboy ID: " + tempWardboyID);
    }
}
    
