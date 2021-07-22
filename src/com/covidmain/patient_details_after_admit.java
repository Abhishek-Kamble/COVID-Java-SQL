package com.covidmain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

class patient {

    long patient_id;

    long patientIDgenerator() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            System.out.print("Driver Loaded Successfully.. ");

            
            Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system",
                    "muskan786");
            System.out.println("Database connected Successfully!\n");

            
            Statement stmt = connect.createStatement();

            
            ResultSet rs = stmt.executeQuery("SELECT COUNT(P_ID) FROM patient_Entry_details");

           
            while (rs.next()) {
                patient_id = rs.getInt(1);
            }
            connect.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return patient_id;

    }
}

public class patient_details_after_admit extends patient {
    String p_bg;
    double p_temp;
    String p_O2level;
    String p_covlevel;
    char p_status;
    patient_details_after_admit() {
        this.p_bg = "";
        this.p_temp = 0.0;
        this.p_O2level = "";
        this.p_covlevel = "";
        this.p_status="";
    }

    Scanner sc = new Scanner(System.in);


    void setpatientbloodgroup() {
        sc.nextLine();
        System.out.print("\nEnter patients Blood group.    : ");
        String patientbg = sc.nextLine();
        this.p_bg = patientbg;
    }

    void setpatient_temp() {
        sc.nextLine();
        System.out.print("\nEnter Temperature in degree_celsius..   : ");
        double patienttemp = sc.nextDouble();
        this.p_temp = patienttemp;
    }


    void setpatietO2level() {
        sc.nextLine();
        System.out.print("\nEnter O2 level of patient..   : ");
        String O2lev = sc.nextLine();
        this.p_O2level = O2lev;

    }

    void setpatientcovidlevel() {
        sc.nextLine();
        System.out.print("\nEnter percent covid level of patient..   : ");
        String covlev = sc.nextLine();
        this.p_covlevel = covlev;

    }
    void setPatientStatus() {
        System.out.print("\nEnter Patient status (C/U)   : ");
        char Patientstatus = sc.next().charAt(0);
        if (Patientstatus == 'C' || Patientstatus == 'U') {
            this.p_status = Patientstatus;
        } else {
            System.out.print("\n ! Re-");
            setPatientStatus();
        }
    }
    void displayreport() {
    	system.out.print("Patient blood group:",patientbg);
    	system.out.print("Patient temperature:",patienttemp);
    	system.out.print("Patient O2 level:",O2lev);
    	system.out.print("Patient covid level:",covlev);
    }
    void displaycovlist()
    {
    	if (p_covlevel>20&&p_O2level<92)
    		System.out.print
    }
}
