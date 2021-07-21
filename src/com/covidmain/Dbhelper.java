package com.covidmain;
import java.sql.*;

public class Dbhelper {
    //SQL Initializations
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
		
	static final String USER = "system";
	static final String PASS = "siddhi123";
	
	// SQL Environment Setup
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	
	public void startconnect()
	{
		try {
			Class.forName(JDBC_DRIVER);

		    //STEP 3: Open a connection
		    System.out.println("Connecting to database...\n");
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
			if(rs != null)
			{
				Dbhelper.rs.close();
			}
			Dbhelper.stmt.close();
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
			Dbhelper.stmt.executeUpdate(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			endstatement();
		}
	}
	public void endupdate()
	{
		try {
			Dbhelper.stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printDataList(String statement)
	{
		try 
		{
			rs = execstatement(statement);
			ResultSetMetaData rsmd = rs.getMetaData();
        	for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.print(rsmd.getColumnName(i) + "\t\t");
            }
        	System.out.println("\n");
        	while (rs.next()) {
                for (int j = 1; j <= rsmd.getColumnCount(); j++) {
                    System.out.print(rs.getString(j) + "\t");
                }
                System.out.println();
            }
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
