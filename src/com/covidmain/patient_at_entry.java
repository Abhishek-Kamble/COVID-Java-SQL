package com.covidmain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

class patientID {
	
	long patient_id;
	
	long patientIDgenerator()
	{
		try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
			System.out.print("Driver Loaded Successfully.. ");
    
			
			Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "shital2901"); 
			System.out.println("Database connected Successfully!\n");

			
			Statement stmt = connect.createStatement();

			
			ResultSet rs = stmt.executeQuery("SELECT COUNT(P_ID) FROM patient_Entry_details");

            
            while (rs.next()) 
            {
                patient_id = rs.getInt(1);
            }
            connect.close();
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }

        return patient_id;
		
	}
}

public class patient_at_entry extends patientID{
	long p_id;
	String p_name;
	int p_age;
	String p_add;
	String p_phone;
	String p_mail;
	char p_gender;
	long p_adhar;
	String p_app;
	
	patient_at_entry()
	{
		this.p_id = 0;
        this.p_name = "";
        this.p_age = 0;
        this.p_phone = "";
        this.p_add = "";
        this.p_mail = "";
        this.p_gender = 'c';
        this.p_adhar = 0;
        this.p_app = "";
	}
	
	Scanner sc = new Scanner(System.in);
	
	void setpatientID()
	{
        this.p_id = patientIDgenerator();
    }

    void setpatientName()
    {
        sc.nextLine();
        System.out.print("\nEnter Name of the Patient     : ");
        String patientname = sc.nextLine();
        this.p_name = patientname;
       
    }
	
    void setpatientPhone()
    {
        sc.nextLine();
        System.out.print("\nEnter Mobile No    : ");
        String patientMob = sc.nextLine();
        if(checkers.mobileChecker(patientMob))
        {
            this.p_phone = patientMob;
        }
        else
        {
            System.out.println("Invalid Mobile No.!!!");
            System.out.print("Please Re Enter mobile no -");
            setpatientPhone();
        }
    }
        void setpatientAdd()
        {
            sc.nextLine();
            System.out.print("\nEnter Address     : ");
            String patientadd = sc.nextLine();
            this.p_add = patientadd;
        }
        
        void setpatientgen()
        {
            sc.nextLine();
            System.out.print("\nEnter gender F/M    : ");
            char patientGen = sc.next().charAt(0);
            this.p_gender = patientGen;
        }
        
        void setpatientMail()
        {
            sc.nextLine();
            System.out.print("\nEnter Email     : ");
            String patientmail = sc.nextLine();
            if(checkers.emailChecker(patientmail))
            {
                this.p_mail = patientmail;
            }
            else
            {
                System.out.println("Invalid mail!!");
                System.out.print("Please Re Enter-");
                setpatientMail();
            }
        }
        void setpatientAge()
        {
            sc.nextLine();
            System.out.print("\nEnter age of the patient     : ");
            int patientage= sc.nextInt();
            this.p_age = patientage; 
        }
        void setAppointment()
        {
        	sc.nextLine();
            System.out.print("\nEnter name of doctor for appointment : ");
            String pAppoint= sc.nextLine();
            this.p_app = pAppoint;
        }
       
        void setpatientAdhar()
        {
            sc.nextLine();
            System.out.print("\nEnter adhar card no. of the patient     : ");
            int patientadhar= sc.nextInt();
            this.p_adhar= patientadhar; 
        }
        
        void Newpatient()
        {
        	patient_at_entry p1 = new patient_at_entry();
            p1.setpatientID();
            p1.setpatientName();
            p1.setpatientAge();
            p1.setpatientPhone();
            p1.setpatientAdd();
            p1.setpatientMail();
            p1.setAppointment();
            p1.setpatientgen();
            p1.setpatientAdhar();
            
            if(patientSQL.addNewPatient(p1))
            {
                System.out.println("New patient added !!");
            }
            else
            {
                System.out.println("Error occured !! ");
            }
        }
    }

