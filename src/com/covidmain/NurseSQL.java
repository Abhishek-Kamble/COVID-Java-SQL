package com.covidmain;
import java.sql.*;

public class NurseSQL 
{
    static Dbhelper db = new Dbhelper();
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
		
	static final String USER = "system";
	static final String PASS = "oracle123";

	static ResultSet rs = null;
	
    static long getNurseCount()
    {
    	long nurse_id = 0;
        try
        {
            String statement = "SELECT COUNT(N_ID) as Count FROM nurse";
            try 
            {
            	db.startstatement();
            	rs = db.execstatement(statement);
            	rs.next();
    		    nurse_id=rs.getInt("Count");
            	
//    			Class.forName(JDBC_DRIVER);
//    		    System.out.println("Connecting to database...");
//    		    Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
//    		    Statement st=conn.createStatement();
//    		    ResultSet rs=st.executeQuery(statement);
//    		    rs.next();
//    		    nurse_id=rs.getInt("Count");
    		    return nurse_id;
    		}
            catch(SQLException se){
    		      //Handle errors for JDBC
    		      se.printStackTrace();
    		      return -1;    		      
            }
        }
        // catch(Exception e){
    	// 	      //Handle errors for Class.forName
    	// 	 e.printStackTrace();
        // }
        catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
 

    public static void addNewNurse(Nurse N) 
    {
        try
        {
//            String statement = "INSERT INTO nurse VALUES(" + N.N_id + "," + N.N_name + "," + N.N_slot + "," + N.N_phone + "," + N.N_add + "," + N.N_mail + ")";
//            db.startstatement();
//            db.update(statement);
//			db.endupdate();
//            
        	Class.forName("oracle.jdbc.driver.OracleDriver"); //class loader function
            Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle123"); 
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
    
    public static void removeNurseRec(String nurseID)
    {
        try
        {
            String statement = "UPDATE nurse SET N_removed = 'N' WHERE N_ID = '" + nurseID +"'";
            db.startstatement();
            db.update(statement);
			db.endupdate();

        	// Class.forName("oracle.jdbc.driver.OracleDriver"); //class loader function
            // Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle123"); 
            // Statement stmt = connect.createStatement();
            // ResultSet rs = stmt.executeQuery("UPDATE nurse SET N_removed = 'N' WHERE N_ID = '" + nurseID +"'");
            // connect.close();
            // System.out.println("Records removed succefully!!");        
        }
        catch(Exception e)
        {
            System.out.println("Error! or Records not exist");
            System.out.println(e);
        }
    }

	public static void displayNurseDetails(String nurseID) {
		try
        {
            String statement = "SELECT * FROM nurse WHERE N_ID = '" + nurseID +"'";
            db.startstatement();
            ResultSet rs = db.execstatement(statement);
            while(rs.next()){
                System.out.println("Nurse ID		: " + rs.getString("N_ID"));
                System.out.println("Nurse Name		: " + rs.getString("N_name"));
                System.out.println("Nurse Slot 		: " + rs.getInt("N_slot"));
                System.out.println("Nurse Phone No. : " + rs.getString("N_phone"));
                System.out.println("Nurse Add. 		: " + rs.getString("N_add"));
                System.out.println("Nurse E-mail	: " + rs.getString("N_mail"));
                System.out.println("Nurse Removed?	: " + rs.getString("N_removed"));
                System.out.println("Nurse Status    : " + rs.getString("N_status"));
             }        
            
             db.endstatement();
            // Class.forName("oracle.jdbc.driver.OracleDriver"); //class loader function
            // Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle123"); 
            // Statement stmt = connect.createStatement();
            // ResultSet rs = stmt.executeQuery("SELECT N_ID, N_name, N_slot, N_phone, N_add, N_mail, N_status FROM nurse WHERE N_ID = '" + nurseID +"'");   
        }
        catch(Exception e)
        {
            System.out.println("Error! or Records not exist");
            System.out.println(e);
        }
	}

}
