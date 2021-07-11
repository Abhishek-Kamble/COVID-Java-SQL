package com.covidmain;
import java.sql.*;


public class NurseSQL 
{
    static long getNurseCount()
    {
    	long nurse_id = 0;
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

    public static boolean addNewNurse(Nurse N) 
    {
        try
        {
            Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "shital2901"); 
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("INSERT INTO nurse VALUES(" + N.N_id + "," + N.N_name + "," + N.N_slot + "," + N.N_phone + "," + N.N_add + "," + N.N_mail + ")");
            connect.close();
            return true;
        }
        catch(Exception e)
        {
            System.out.println("Error!!");
            System.out.println(e);
            return false;
        }
    }
    

}
