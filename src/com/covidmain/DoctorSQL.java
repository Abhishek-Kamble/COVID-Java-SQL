package com.covidmain;
import java.sql.*;

public class DoctorSQL 
{
    static Dbhelper db = new Dbhelper();
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
		
	static final String USER = "system";
	static final String PASS = "shital2901";

	static ResultSet rs = null;
	
    static long getDoctorCount()
    {
    	long doctor_id = 0;
        try
        {
            String statement = "SELECT COUNT(D_ID) as Count FROM Doctor";
            try 
            {
            	db.startstatement();
            	rs = db.execstatement(statement);
            	rs.next();
            	doctor_id=rs.getInt("Count");
    		    return doctor_id;
    		}
            catch(SQLException se){
    		      
    		      se.printStackTrace();
    		      return -1;    		      
            }
        }

        catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
 

    public static void addNewDoctor(Doctor D) 
    {
        try
        {
        	Class.forName("oracle.jdbc.driver.OracleDriver"); 
            Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "shital2901"); 
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery();
            connect.close();
        }
        catch(Exception e)
        {
            System.out.println("Error!!");
            System.out.println(e);
        }
    }
    
    public static void removeDoctorRec(String doctorID)
    {
        try
        {
            String statement = "UPDATE Doctor SET D_removed = 'N' WHERE D_ID = '" + doctorID +"'";
            db.startstatement();
            db.update(statement);
			db.endupdate();      
        }
        catch(Exception e)
        {
            System.out.println("Error! or Records not exist");
            System.out.println(e);
        }
    }

	public static void displayDoctorDetails(String doctorID) {
		try
        {
            String statement = "SELECT * FROM Doctor WHERE D_ID = '" + doctorID +"'";
            db.startstatement();
            ResultSet rs = db.execstatement(statement);
            while(rs.next()){
                System.out.println("Doctor ID		: " + rs.getString("D_ID"));
                System.out.println("Doctor Name		: " + rs.getString("D_name"));
                System.out.println("Doctor Slot 		: " + rs.getInt("D_slot"));
                System.out.println("Doctor Phone No. : " + rs.getString("D_phone"));
                System.out.println("Doctor Add. 		: " + rs.getString("D_add"));
                System.out.println("Doctor E-mail	: " + rs.getString("D_mail"));
                System.out.println("Doctor Removed?	: " + rs.getString("D_removed"));
                System.out.println("Doctor Status    : " + rs.getString("D_status"));
             }        
            
             db.endstatement(); 
        }
        catch(Exception e)
        {
            System.out.println("Error! or Records not exist");
            System.out.println(e);
        }
	}

}

 