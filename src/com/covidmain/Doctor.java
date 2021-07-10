package com.covidmain;
import java.sql.*;
import java.util.*;


class DoctorID{
	long Doctor_id;
	
	long DoctorIDgenerator(){
		
		try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver"); //class loader function
			System.out.print("Driver Loaded Successfully.. ");
    
			//connection call to oracle server
			//port:: C:\oraclexe\app\oracle\product\11.2.0\server\network\ADMIN
			Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "shital2901"); 
			System.out.println("Database connected Successfully!\n");

			//Creates a Statement object for sendingSQL statements to the database.
			Statement stmt = connect.createStatement();

			//fetching data and storing to variable
			ResultSet rs = stmt.executeQuery("SELECT COUNT(D_ID) FROM Doctor");

            
            while (rs.next()) 
            {
                Doctor_id = rs.getInt(1);
            }
            connect.close();
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }

        return Doctor_id;
	}
}

public class Doctor extends DoctorID {
    long D_id;
    String D_name;
    int D_slot;
    String D_phone;
    String D_add;
    String D_mail;
    char D_status;
    String D_dept;
    

    Doctor()
    {
        this.D_id = 0;
        this.D_name = "";
        this.D_slot = 0;
        this.D_phone = "";
        this.D_add = "";
        this.D_mail = "";
        this.D_status = 'N';
        this.D_dept = "";
    }

    Scanner sc = new Scanner(System.in);

    void setDoctorID()
    {
        this.D_id = DoctorIDgenerator();
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
        int Dslot_no = sc.nextInt();
        this.D_slot = Dslot_no;
    }

    void setDoctorDept()
    {
        sc.nextLine();
        System.out.print("\nEnter Department        : ");
        String Dept = sc.nextLine();
        this.D_dept = Dept;
    }
    void setDoctorPhone()
    {
        sc.nextLine();
        System.out.print("\nEnter Mobile No    : ");
        String DocMob = sc.nextLine();
        if(checkers.mobileChecker(DocMob))
        {
            this.D_phone = DocMob;
        }
        else
        {
            System.out.println("Invalid Mobile No.!!");
            System.out.print("Please Re Enter -");
            setDoctorPhone();
        }
        
    }

    void setDoctorAdd()
    {
        sc.nextLine();
        System.out.print("\nEnter Address     : ");
        String Docadd = sc.nextLine();
        this.D_add = Docadd;
    }

    void setDoctorMail()
    {
        sc.nextLine();
        System.out.print("\nEnter E-Mail     : ");
        String Docemail = sc.nextLine();
        if(checkers.emailChecker(Docemail))
        {
            this.D_mail = Docemail;
        }
        else
        {
            System.out.println("Invalid mail!!");
            System.out.print("Please Re enter your mail -");
            setDoctorMail();
        }
        
    }

    void setDoctorStatus()
    {
        sc.nextLine();
        System.out.print("\nEnter Doctor status (trainee(T)/temp(t)/permanent(p)  : ") ;
        char Docstatus = sc.next().charAt(0);
        this.D_status = Docstatus;
        
    }

    void addDoctor()
    {
        Doctor D1 = new Doctor();
        D1.setDoctorID();
        D1.setDoctorName();
        D1.setDoctorSlot();
        D1.setDoctorPhone();
        D1.setDoctorAdd();
        D1.setDoctorMail();
        D1.setDoctorStatus();
        D1.setDoctorDept();

        if(DoctorSQL.addNewDoctor(D1))
        {
            System.out.println("New Doctor added successfully!!");
        }
        else
        {
            System.out.println("Some error occured!!");
        }
    }
}
