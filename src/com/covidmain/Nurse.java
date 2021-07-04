package com.covidmain;
import java.sql.*;
import java.util.*;


class nurseID{
    long nurse_id;
    //function to generate nurse id
    long nurseIDgenerator()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver"); //class loader function
			System.out.print("Driver Loaded Successfully.. ");
    
			//connection call to oracle server
			//port:: C:\oraclexe\app\oracle\product\11.2.0\server\network\ADMIN
			Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle123"); 
			System.out.println("Database connected Successfully!\n");

			//Creates a Statement object for sendingSQL statements to the database.
			Statement stmt = connect.createStatement();

			//fetching data and storing to variable
			ResultSet rs = stmt.executeQuery("SELECT COUNT(N_ID) FROM nurse");

            //new code
            while (rs.next()) 
            {
                nurse_id = rs.getInt(1);
            }
            connect.close();
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }

        return nurse_id;
    }
}

public class Nurse extends nurseID {
    long N_id;
    String N_name;
    int N_slot;
    long N_phone;
    String N_add;
    String N_mail;

    //default constructor
    void Nurse()
    {
        this.N_id = nurseIDgenerator();
        this.N_name = "NewNurse";
        this.N_slot = 0;
        this.N_phone = 0;
        this.N_add = "NewNurse";
    }

    Scanner sc = new Scanner(System.in);

    //setter
    void setNurseID()
    {
        this.N_id = nurseIDgenerator();
    }

    void setNurseName()
    {

    }

    //getter

    //function to add new nurse
    void addNurse(){}

    //function to remove nurse
    void removeNurse(){}

    void detailNurse(){}


    // void attendenceNurse(){}


}
