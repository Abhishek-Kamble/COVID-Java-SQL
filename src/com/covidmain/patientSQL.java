package com.covidmain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class patientSQL {

	public static boolean addNewPatient(patient_at_entry p1) {
		try
        {
            Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "shital2901"); 
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("INSERT INTO patient_entry_details VALUES (" + p1.p_id + "," + p1.p_name + "," + p1.p_age + "," + p1.p_add + "," + p1.p_phone + "," + p1.p_mail + "," + p1.p_app + "," + p1.p_adhar + ")");
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
