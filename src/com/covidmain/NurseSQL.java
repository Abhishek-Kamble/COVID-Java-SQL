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
        	Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle123");
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
        	Class.forName("oracle.jdbc.driver.OracleDriver"); //class loader function
            Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle123"); 
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
    
    public static void removeNurseRec(String nurseID)
    {
        try
        {
        	Class.forName("oracle.jdbc.driver.OracleDriver"); //class loader function
            Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle123"); 
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("UPDATE nurse SET N_removed = 'N' WHERE N_ID = '" + nurseID +"'");
            connect.close();
            System.out.println("Records removed succefully!!");        
        }
        catch(Exception e)
        {
            System.out.println("Error! or Records not exist");
            System.out.println(e);
        }
    }

}
