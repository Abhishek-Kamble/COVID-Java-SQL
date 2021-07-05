package com.covidmain;
import java.sql.*;


public class NurseSQL {

    public static boolean addNewNurse(Nurse N) 
    {
        try
        {
            Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle123"); 
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("INSERT INTO nurse VALUES(" + N.N_id + "," + N.N_name + "," + N.N_slot + "," + N.N_phone + "," + N.N_add + "," + N.N_mail + ")");
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
