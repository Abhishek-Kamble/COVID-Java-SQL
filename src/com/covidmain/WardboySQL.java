package com.covidmain;
import java.sql.*;

public class WardboySQL 
{
    static Dbhelper db = new Dbhelper();
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
		
	static final String USER = "system";
	static final String PASS = "siddhi123";

	static ResultSet rs = null;
	
    static long getWardboyCount()
    {
    	long wardboy_id = 0;
        try
        {
            String statement = "SELECT COUNT(W_ID) as Count FROM wardboy";
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
        catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
    
    
    public static void addNewWardboy(wardboy W) 
    {
        try
        {            
        	Class.forName("oracle.jdbc.driver.OracleDriver"); 
            Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "siddhi123"); 
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
    
    public static void removeWardboyRec(String wardboyID)
    {
        try
        {
            String statement = "UPDATE wardboy SET W_removed = 'N' WHERE W_ID = '" + wardboyID +"'";
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

	public static void displayWardboyDetails(String wardboyID) {
		try
        {
            String statement = "SELECT * FROM wardboy WHERE W_ID = '" + wardboyID +"'";
            db.startstatement();
            ResultSet rs = db.execstatement(statement);
            while(rs.next()){
                System.out.println("Wardboy ID		 : " + rs.getString("W_ID"));
                System.out.println("Wardboy Name	 : " + rs.getString("W_name"));
                System.out.println("Wardboy Slot 	 : " + rs.getInt("W_slot"));
                System.out.println("Wardboy Phone No.: " + rs.getString("W_phone"));
                System.out.println("Wardboy Add. 	 : " + rs.getString("W_add"));
                System.out.println("Wardboy E-mail	 : " + rs.getString("W_mail"));
                System.out.println("Wardboy Removed? : " + rs.getString("W_removed"));
                System.out.println("Wardboy Status   : " + rs.getString("W_status"));
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

 
