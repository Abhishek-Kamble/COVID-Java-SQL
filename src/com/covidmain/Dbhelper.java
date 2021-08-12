package com.covidmain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Dbhelper {
    //SQL Initializations
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";

	static String s1 = "";
	static String s2 = "";
	
	// SQL Environment Setup
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	static void setDbCred() {
		Scanner sc;
		try {
			sc = new Scanner(new File("dbcred.txt"));
			while(sc.hasNext()){
				s1 = sc.next();
				s2 = sc.next();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	static void view_changeDB()
	{
		System.out.println(CovidMain.CYAN_BOLD + "------------------------ Database Credentials Manager ------------------------\n" + CovidMain.RESET);
		Scanner sc;
		try {
			sc = new Scanner(new File("dbcred.txt"));
			while(sc.hasNext()){
				s1 = sc.next();
				s2 = sc.next();
			}
			
			System.out.println(CovidMain.BLUE + "	Current DB user ID: " + CovidMain.RESET + s1);
			System.out.println(CovidMain.BLUE + "	Current DB Password: " + CovidMain.RESET + s2);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Scanner scan = new Scanner(System.in);
		System.out.print(CovidMain.YELLOW + "\n		Do You Want To Update Cred? (Y/N): " + CovidMain.RESET);
		char opt = scan.next().charAt(0);
		if(opt == 'Y' || opt == 'y')
		{
			System.out.print(CovidMain.YELLOW + "\n		Enter New ID: " + CovidMain.RESET);
			String userid = scan.nextLine();
			if(userid.equals(""))
				userid = scan.nextLine();
			System.out.print(CovidMain.YELLOW + "\n 	Enter New Pass: " + CovidMain.RESET);
			String pass = scan.nextLine();
			try {
	            FileWriter myWriter = new FileWriter("dbcred.txt");
	            myWriter.write(userid + " " + pass);
	            myWriter.close();
	            setDbCred();
	            System.out.println(CovidMain.GREEN + "\nDatabse credentials updated successfully!" + CovidMain.RESET);
	        } catch (IOException e) {
	            System.out.println("An error occurred.");
	            e.printStackTrace();
	        }
		}
		
		return;
	}
	
	String USER = s1;
	String PASS = s2;
	
	public void startconnect()
	{
		try {
			Class.forName(JDBC_DRIVER);

			System.out.print(CovidMain.BLUE_BOLD + "Connecting to DB..." + CovidMain.RESET);
	        Thread.sleep(1000); 
	        System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
	        System.out.println("                                                 ");
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
                System.out.print(CovidMain.YELLOW + rsmd.getColumnName(i) + "\t"  + CovidMain.RESET);
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
