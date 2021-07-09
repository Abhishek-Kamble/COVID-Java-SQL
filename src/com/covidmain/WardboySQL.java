package com.covidmain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class WardboySQL {

	public static boolean addNewWardboy(Wardboy W1) 
	 {
        try
        {
        	Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "siddhi123");
        	Statement stmt = connect.createStatement();
        	ResultSet rs = stmt.executeQuery("INSERT INTO warddboy VALUES(" + W1.W_id + "," + W1.W_name + "," + W1.W_slot + "," + W1.W_phone + "," + W1.W_add + "," + W1.W_mail + ")");
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