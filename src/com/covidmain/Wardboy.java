package com.covidmain;
import java.sql.ResultSet;

//function to generate wardboy id
class wardboyHelper
{
	static Dbhelper db = new Dbhelper();
	static ResultSet rs = null;
	
	static long getWardboyCount()
    {
    	long wardboy_id = 0;
    	String statement = "SELECT COUNT(W_id) as Count FROM wardboy";
        try 
        {
        	db.startstatement();
        	rs = db.execstatement(statement);
        	rs.next();
		    wardboy_id=rs.getInt("Count");
		    return wardboy_id;
		}
        catch(SQLException se){
		      se.printStackTrace();
		      return -1;    		      
        }
    }
	
	//wardboy ID
	
	static String wardboyID = "";

    static String wardboyIDgenerator() {
        long wardboyCount = WardboySQL.getWardboyCount() + 1;
        System.out.println("Wardboy Count: " + wardboyCount);
        String wardboyCountStr = "";
        if (wardboyCount >= 0 && wardboyCount < 10) {
            wardboyCountStr = "00" + String.valueOf(wardboyCount);
        } 
        else if(wardboyCount >= 10 && wardboyCount < 100) {
        	wardboyCountStr = "0" + String.valueOf(wardboyCount);
        }
        else if(wardboyCount >= 100 && wardboyCount < 1000) {
        	wardboyCountStr = "" + String.valueOf(wardboyCount);
        }
        
        if (wardboyCount == -1) {
            System.out.println("Error in fetching database!");
        } 
        else {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            wardboyID = 'W' + String.valueOf(year) + wardboyCountStr;
        }

        return wardboyID;
    }
	
	
}

public class wardboy extends wardboyHelper {
    String W_id;
    String W_name;
    int W_slot;
    String W_phone;
    String W_add;
    String W_mail;
    char isRemoved;
    char W_status;

    wardboy() {
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

    
    void setWardboyID() {
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
            this.W_name = wardboyMob;
        } else {
            System.out.println("Invalid Mobile No.!!!");
            System.out.print("Press Re-");
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
        if (checkers.emailChecker(wardboymail)) {
            this.W_mail = wardboymail;
        } else {
            System.out.println("Invalid mail!!");
            System.out.print("Please Re-");
            setWardboyMail();
        }
    }

    void setWardboyStatus() {
        System.out.print("\nEnter Wardboy status (A/N)   : ");
        char wardboystatus = sc.next().charAt(0);
        if(wardboystatus == 'A' || wardboystatus == 'N')
        {
            this.W_status = wardboystatus;
        }
        else
        {
            System.out.print("\n ! Re-");
            setWardboyStatus();
        }
    }

    static void addWardboy() {
        wardboy W = new wardboy();
        W.setWardboyID();
        W.setWardboyName();
        W.setWardboySlot();
        W.setWardboyPhone();
        W.setWardboyAdd();
        W.setWardboyMail();
        W.setWardboyStatus();
        
        String statement = "INSERT INTO wardboy(W_id, W_name, W_slot, W_phone, W_add, W_mail, isremoved, W_status) VALUES('" + W.W_id + "','" + W.W_name + "'," + W.W_slot + ",'" + W.W_phone + "','" + W.W_add + "','" + W.W_mail + "','" + W.isRemoved + "','" + W.W_status + "')";
        db.startstatement();
        db.update(statement);
        
        System.out.println("New wardboy added successfully!! ID: " + W.W_id);
        
    }
    
    static void removeNurse() {
        System.out.println("**** Remove Wardboy ****");
        System.out.print("\nEnter Wardboy ID    : ");
        String tempWardboyID = sc.nextLine();
        String statement = "UPDATE wardboy SET isremoved = 'Y' WHERE W_ID = '" + tempWardboyID +"'";
        db.startstatement();
        db.update(statement);

    }
    
    
    public static void displayWardboyDetails() throws SQLException {
		System.out.println("** Display Wardboy Details **");
        System.out.print("\nEnter Wardboy ID    : ");
        String tempWardboyID = sc.nextLine();

        String statement = "SELECT * FROM wardboy WHERE W_ID = '" + tempWardboyID +"'";
        try 
        {
        	db.startstatement();
        	rs = db.execstatement(statement);
		}
        catch(Exception e) { 
            e.printStackTrace();
            System.out.println("Error! or Records not exist");
            System.out.println(e);
        }      
        while(rs.next()){
            System.out.println("Wardboy ID			: " + rs.getString("W_ID"));
            System.out.println("Wardboy Name		: " + rs.getString("W_name"));
            System.out.println("Wardboy Slot 		: " + rs.getInt("W_slot"));
            System.out.println("Wardboy Phone No. 	: " + rs.getString("W_phone"));
            System.out.println("Wardboy Add. 		: " + rs.getString("W_add"));
            System.out.println("Wardboy E-mail		: " + rs.getString("W_mail"));
            System.out.println("Wardboy Removed?	: " + rs.getString("W_removed"));
            System.out.println("Wardboy Status    	: " + rs.getString("W_status"));
         }
		 db.endstatement();
		        
			}
		    
		    static void detailWardboySlotWise()
		    {
		        System.out.println("\nSlot wise wardboy details");
		        
		    }
		    public static void main(String[] args) throws SQLException {
		        System.out.println("\nRunning ");
		        	wardboy.addWardboy();
		        
		    }
		
		}






















	    

