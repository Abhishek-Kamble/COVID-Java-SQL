package com.covidmain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DoctorSQL {

	public static boolean addNewDoctor(Doctor d1) {
		// TODO Auto-generated method stub
		
		try
        {
            Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "shital2901"); 
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("INSERT INTO Doctor VALUES(" + d1.D_id + "," + d1.D_name + "," + d1.D_slot + "," + d1.D_phone + "," + d1.D_add + "," + d1.D_mail + "," + d1.D_dept + ")");
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
