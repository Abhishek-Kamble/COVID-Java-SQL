package com.covidmain;
import java.sql.*;
import java.util.*;


class nurseID
{
    static long nurse_id;
    //function to generate nurse id
    static long nurseIDgenerator()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver"); //class loader function
            System.out.println("Driver Loaded!");
			
			Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle123");
			System.out.println("Connection established!");
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(N_ID) FROM nurse");
            while (rs.next()) 
            {
                nurse_id = rs.getInt(1);
            }
            connect.close();

            return nurse_id;
        } 
        catch (Exception e) 
        {
            System.out.println(e);
            return -1;
        }
    }
}

public class Nurse extends nurseID 
{
    long N_id;
    String N_name;
    int N_slot;
    String N_phone;
    String N_add;
    String N_mail;
    char N_status;

    //default constructor
    Nurse()
    {
        this.N_id = 0;
        this.N_name = "";
        this.N_slot = 0;
        this.N_phone = "";
        this.N_add = "";
        this.N_mail = "";
        this.N_status = 'N';
    }

    Scanner sc = new Scanner(System.in);

    //setters
    void setNurseID()
    {
        this.N_id = nurseIDgenerator();
    }

    void setNurseName()
    {
        sc.nextLine();
        System.out.print("\nEnter Name        : ");
        String nursename = sc.nextLine();
        this.N_name = nursename;
    }

    void setNurseSlot()
    {
        System.out.print("\nEnter slot no.    : ");
        int slot_no = sc.nextInt();
        this.N_slot = slot_no;
    }

    void setNursePhone()
    {
        sc.nextLine();
        System.out.print("\nEnter Mobile No    : ");
        String nurseMob = sc.nextLine();
        if(checkers.mobileChecker(nurseMob))
        {
            this.N_name = nurseMob;
        }
        else
        {
            System.out.println("Invalid Mobile No.!!!");
            System.out.print("Please Re-");
            setNursePhone();
        }
        
    }

    void setNurseAdd()
    {
        sc.nextLine();
        System.out.print("\nEnter Address     : ");
        String nurseadd = sc.nextLine();
        this.N_add = nurseadd;
    }

    void setNurseMail()
    {
        sc.nextLine();
        System.out.print("\nEnter E-mail     : ");
        String nursemail = sc.nextLine();
        if(checkers.emailChecker(nursemail))
        {
            this.N_mail = nursemail;
        }
        else
        {
            System.out.println("Invalid mail!!");
            System.out.print("Please ReEnter Mail-");
            setNurseMail();
        }
        
    }

    void setNurseStatus()
    {
        sc.nextLine();
        System.out.print("\nEnter Nursstatus     : ");
        char nursestatus = sc.next().charAt(0);
        this.N_status = nursestatus;
        //TODO Adding checker for entered status
    }

    //TODO -getters

    //function to add new nurse
    void addNurse()
    {
        Nurse N1 = new Nurse();
        N1.setNurseID();
        N1.setNurseName();
        N1.setNurseSlot();
        N1.setNursePhone();
        N1.setNurseAdd();
        N1.setNurseMail();
        N1.setNurseStatus();

        if(NurseSQL.addNewNurse(N1))
        {
            System.out.println("New nurse added successfully!!");
        }
        else
        {
            System.out.println("Caution! Some error occured!!");
        }
    }

    //function to remove nurse
    void removeNurse(){
        //here we don't have to remove nurse details from database;
        //only change status to n
    }

    void detailNurse(){}


    // void attendenceNurse(){}

    public static void main(String[] args) {
        System.out.print("\nTemp. Count: " + nurseIDgenerator());
    }

}
