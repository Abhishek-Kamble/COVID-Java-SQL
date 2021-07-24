package com.covidmain;

import java.sql.*;
import java.util.*;

class patientHelper {
    static Dbhelper db = new Dbhelper();
    static ResultSet rs = null;

    static long getPatientCount() throws SQLException {
        long patient_id = 0;
        String statement = "SELECT COUNT(P_ID) as Count FROM patient_at_entry";

        db.startstatement();
        rs = db.execstatement(statement);
        rs.next();
        patient_id = rs.getInt("Count");
        return patient_id;
    }

    static String patientID = "";

    static String patientIDgenerator() throws SQLException {
        long patientCount = getPatientCount() + 1;

        String patientCountStr = "";
        if (patientCount >= 0 && patientCount < 10) {
            patientCountStr = "0000" + String.valueOf(patientCount);
        } else if (patientCount >= 10 && patientCount < 100) {
            patientCountStr = "000" + String.valueOf(patientCount);
        } else if (patientCount >= 100 && patientCount < 1000) {
            patientCountStr = "00" + String.valueOf(patientCount);
        } else if (patientCount >= 1000 && patientCount < 10000) {
            patientCountStr = "0" + String.valueOf(patientCount);
        } else if (patientCount >= 10000 && patientCount < 100000) {
            patientCountStr = " " + String.valueOf(patientCount);
        }

        if (patientCount == -1) {
            System.out.println("Error in fetching database!");
        } else {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            patientID = 'P' + String.valueOf(year) + patientCountStr;
        }

        return patientID;
    }

}

public class patient_at_entry extends patientHelper {
    String p_id;
    String p_name;
    int p_age;
    String p_add;
    String p_phone;
    String p_mail;
    String p_adhar;
    char p_Status;


    patient_at_entry() {
        this.p_id = "";
        this.p_name = "";
        this.p_age = 0;
        this.p_phone = "";
        this.p_add = "";
        this.p_adhar = "";
        this.p_Status = 'N';

    }

    static Scanner sc = new Scanner(System.in);

    void setPatientID() throws SQLException {
        String tempP_ID = patientIDgenerator();
        if (tempP_ID == "") {
            System.out.println("Error!! Can't set Patient ID.");
        } else {
            this.p_id = tempP_ID;
        }
    }

    void setPatientName() {
        System.out.print("\nEnter Name        : ");
        String patientname = sc.nextLine();
        this.p_name = patientname;
    }

    void setPatientAge() {
        System.out.print("\nEnter Age of patient     : ");
        int PAge = sc.nextInt();
        this.p_age = PAge;
    }
    void setPatientStatus() {
    	System.out.print("\nEnter Patient status (Y/N)   : ");
        char patientstatus = sc.next().charAt(0);
        if(patientstatus == 'Y' || patientstatus == 'N')
        {
            this.p_Status = patientstatus;
        }
        else
        {
            System.out.print("\n!Re-");
            setPatientStatus();
        }
    }

    void setPatientPhone() {
        sc.nextLine();
        System.out.print("\nEnter Mobile No    : ");
        String patientMob = sc.nextLine();
        if (checkers.mobileChecker(patientMob)) {
            this.p_phone = patientMob;
        } else {
            System.out.println("Invalid Mobile No.!!!");
            System.out.print("Press ENTER!!");
            setPatientPhone();
        }
    }

    void setPatientAdd() {
        System.out.print("\nEnter Address     : ");
        String patientadd = sc.nextLine();
        this.p_add = patientadd;
    }

    void setPatientAdhar() {
        System.out.print("\nEnter Adhar No.     : ");
        String patientadhar = sc.nextLine();
        this.p_adhar = patientadhar;
    }

    static void addPatient() throws SQLException {
        patient_at_entry P = new patient_at_entry();
        P.setPatientID();
        P.setPatientName();
        P.setPatientAge();
        P.setPatientPhone();
        P.setPatientAdd();
        P.setPatientAdhar();
        P.setPatientStatus();
        // P.setAdmitDate();
        // P.setDischargeDate();
        String statement = "INSERT INTO patient_at_entry(p_id, p_name, p_age, p_phone, p_add, p_status,p_adhar) VALUES('" + P.p_id + "','" + P.p_name + "','" + P.p_age + "','" + P.p_phone + "','" + P.p_add + "','" + P.p_Status + "','"+ P.p_adhar  +"')";
        db.startstatement();
        db.update(statement);

        System.out.println("New Patient added successfully!! ID: " + P.p_id);

    }

    public static void displayPatientDetails() throws SQLException {
        System.out.println("** Display Patient Details **");
        System.out.print("\nEnter Patient ID    : ");
        String tempPatientID = sc.nextLine();

        String statement = "SELECT p_id, p_name, p_age ,p_phone, p_add , p_Status ,p_adhar FROM patient_at_entry WHERE P_ID = '"
                + tempPatientID + "'";

        db.startstatement();
        rs = db.execstatement(statement);
        while (rs.next()) {
            System.out.println("Patient ID		: " + rs.getString("p_ID"));
            System.out.println("Patient Name		: " + rs.getString("p_name"));
            System.out.println("Patient Age.     : " + rs.getString("p_age"));
            System.out.println("Patient Phone No. : " + rs.getString("p_phone"));
            System.out.println("Patient Add. 		: " + rs.getString("p_add"));
             System.out.println("Patient Status : " + rs.getString("p_Status"));
             System.out.println("Patient Adhar	: " + rs.getString("p_adhar"));
        }
        db.endstatement();

        System.out.println("-----------------------------------------------");
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("\nRunning ");
        // System.out.println(patientIDgenerator());
        //addPatient();
        displayPatientDetails();

    }

}
