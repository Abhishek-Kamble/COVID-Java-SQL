package com.covidmain;

import java.sql.*;
import java.util.Scanner;

class wardboyID{
    long wardboy_id;
    //function to generate wardboy id
    long wardboyIDgenerator()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver"); //class loader function
			System.out.print("Driver Loaded Successfully.. ");
    
			//connection call to oracle server
			Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "siddhi123"); 
			System.out.println("Database connected Successfully!\n");

			//Creates a Statement object for sendingSQL statements to the database.
			Statement stmt = connect.createStatement();

			//fetching data and storing to variable
			ResultSet rs = stmt.executeQuery("SELECT COUNT(W_ID) FROM wardboy");
			
			//new code
            while (rs.next()) 
            {
                wardboy_id = rs.getInt(1);
            }
            connect.close();
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }

        return wardboy_id;
    }
}

public class Wardboy extends wardboyID {
	 long W_id;
	    String W_name;
	    int W_slot;
	    String W_phone;
	    String W_add;
	    String W_mail;
	    char W_status;
	    
	  //default constructor
	    Wardboy()
	    {
	        this.W_id = 0;
	        this.W_name = "";
	        this.W_slot = 0;
	        this.W_phone = "";
	        this.W_add = "";
	        this.W_mail = "";
	    }
	        
	        Scanner sc = new Scanner(System.in);

	        //setters
	        void setWardboyID()
	        {
	            this.W_id= wardboyIDgenerator();
	        }
	        
	        void setWardboyName()
	        {
	            sc.nextLine();
	            System.out.print("\nEnter Name        : ");
	            String wardboyname = sc.nextLine();
	            this.W_name = wardboyname;
	        }
	        
	        void setWardboySlot()
	        {
	            System.out.print("\nEnter slot no.    : ");
	            int Wslot_no = sc.nextInt();
	            this.W_slot = Wslot_no;
	        }
	        
	        void setWardboyPhone()
	        {
	            sc.nextLine();
	            System.out.print("\nEnter Mobile No    : ");
	            String wardboyMob = sc.nextLine();
	            if(checkers.mobileChecker(wardboyMob))
	            {
	                this.W_name = wardboyMob;
	            }
	            else
	            {
	                System.out.println("Invalid Mobile No.!!!");
	                System.out.print("Please ReEnter mobile no -");
	                setWardboyPhone();
	            }
	            
	        }
	        
	        void setWardboyAdd()
	        {
	            sc.nextLine();
	            System.out.print("\nEnter Address     : ");
	            String wardboyadd = sc.nextLine();
	            this.W_add = wardboyadd;
	        }

	        void setWardboyMail()
	        {
	            sc.nextLine();
	            System.out.print("\nEnter E-mail     : ");
	            String wardboymail = sc.nextLine();
	            if(checkers.emailChecker(wardboymail))
	            {
	                this.W_mail = wardboymail;
	            }
	            else
	            {
	                System.out.println("Invalid mail!!");
	                System.out.print("Please ReEnter Mail-");
	                setWardboyMail();
	            }
	            
	        }
	        
	       
	      //TODO -getters

	        //function to add new wardboy
	        void addWardboy()
	        {
	            Wardboy W1 = new Wardboy();
	            W1.setWardboyID();
	            W1.setWardboyName();
	            W1.setWardboySlot();
	            W1.setWardboyPhone();
	            W1.setWardboyAdd();
	            W1.setWardboyMail();

	            if(WardboySQL.addNewWardboy(W1))
	            {
	                System.out.println("New wardboy added successfully!!");
	            }
	            else
	            {
	                System.out.println("Caution! Some error occured!!");
	            }
	        }
	        
	      //function to remove wardboy
	        void removeWardboy(){
	            //here we don't have to remove wardboy details from database;
	            //only change status to n
	        }

	        void detailWardboy() {}

	        


	    }


	    

