package com.covidmain;
import java.sql.*;

public class CovidMain 
{
	void demoDisplay
	{
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver"); //class loader function
			System.out.print("Driver Loaded Successfully.. ");
    
			//connection call to oracle server
			//port:: C:\oraclexe\app\oracle\product\11.2.0\server\network\ADMIN
			Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system",
            "oracle123"); 
			System.out.println("Database connected Successfully!\n");

			//Creates a Statement object for sendingSQL statements to the database.
			Statement stmt = connect.createStatement();

			//fetching data and storing to variable
			ResultSet rs = stmt.executeQuery("select * from students");
			
			// information about the typesand properties of the columns in a ResultSet object.
			ResultSetMetaData rsmd = rs.getMetaData();
    
			for (int i = 1; i <= rsmd.getColumnCount(); i++) //Returns the number of columns in this ResultSet object.
			{
				System.out.print(rsmd.getColumnName(i) + "\t");
			}
	    
			while (rs.next()) 
			{
				for (int j = 1; j <= rsmd.getColumnCount(); j++) {
					System.out.print(rs.getString(j) + "\t");
				}
				System.out.println();
			}
			
			//closing sql connection
			connect.close();
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}

		System.out.println("\n");
	}

}
