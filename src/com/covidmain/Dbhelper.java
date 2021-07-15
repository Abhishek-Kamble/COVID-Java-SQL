package com.covidmain;
import java.sql.*;

public class Dbhelper {
    //SQL Initializations
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
		
	static final String USER = "system";
	static final String PASS = "oracle123";
	
	// SQL Environment Setup
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	
	public void startconnect()
	{
		try {
			Class.forName(JDBC_DRIVER);

		    //STEP 3: Open a connection
		    System.out.println("Connecting to database...");
		    this.conn = DriverManager.getConnection(DB_URL,USER,PASS);
			}catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
			}catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
			}
	}
	
	public void endconnect()
	{
		try
		{
			System.out.println("Closing Connection...");
			if(this.conn!=null)
	           this.conn.close();
	    }
		catch(SQLException se)
		{
	         se.printStackTrace();
	    }//end finally try
	}
	
	public void startstatement()
	{
		try {
//			System.out.println(this.conn);
			startconnect();
			this.stmt = this.conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void endstatement()
	{
		try {
			this.rs.close();
			this.stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet execstatement(String statement)
	{
		try 
		{
			Dbhelper.rs = this.stmt.executeQuery(statement);
		} 
		catch (SQLException e) {
			e.printStackTrace();
			Dbhelper.rs = null;
		}
		return Dbhelper.rs;
	}
	
	public void update(String statement)
	{
		try {
			this.stmt.executeUpdate(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			endstatement();
		}
	}
	public void endupdate()
	{
		try {
			this.stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}