package com.covidmain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ward {

    static Dbhelper db = new Dbhelper();
    static ResultSet rs = null;

    String wardname; // A, B, C, D
    String wardType; // E/N
    int wardCapacity; // total
    int noBeds_full; // engage
    String wardDoctorID;//
    String nurse1;
    String nurse2;
    String wardboy1;
    String wardboy2;

    // constructor
    ward() {
        this.wardname = "";
        this.wardType = "";
        this.wardCapacity = 0;
        this.noBeds_full = 0;
        this.wardDoctorID = "";
        this.nurse1 = "";
        this.nurse2 = "";
        this.wardboy1 = "";
        this.wardboy2 = "";

    }

    static Scanner sc = new Scanner(System.in);

    // function to the wardname aA B C D
    void set_ward_name() {
        System.out.println("\nEnter the Wardname: ");
        String wardABCD = sc.nextLine();
        this.wardname = wardABCD;
    }

    // function to the wardtype
    void set_ward_type() {
        System.out.println("\nEnter the type of ward: ");
        String wardEN = sc.nextLine();
        this.wardType = wardEN;
    }

    // function to set the capacity of the ward
    void set_ward_capacity() {
        System.out.println("\nEnter th ecapacity of ward: ");
        int capacity = sc.nextInt();
        this.wardCapacity = capacity;
    }

    void setDoctor() {
        System.out.println("\nEnter doctor ID: ");
        String wardABCD = sc.nextLine();
        this.wardDoctorID = wardABCD;
    }

    // function to find the how many beds are full or not
    int Beds_full() {
        return noBeds_full;
    }

    void setNurse1() {
        System.out.println("\nEnter nurse 1 ID: ");
        String nid = sc.nextLine();
        this.nurse1 = nid;
    }

    // function to add nurse
    void setNurse2() {
        System.out.println("\nEnter nurse 2 ID: ");
        String nid = sc.nextLine();
        this.nurse2 = nid;
    }

    void setWardboy1() {
        System.out.println("\nEnter wardboy 1 ID: ");
        String nid = sc.nextLine();
        this.wardboy1 = nid;
    }

    void setWardboy2() {
        System.out.println("\nEnter wardboy 2 ID: ");
        String nid = sc.nextLine();
        this.wardboy2 = nid;
    }

    // function to add the new ward to the database

    static void create_ward() throws SQLException {
        ward W = new ward();
        W.set_ward_name();
        W.set_ward_type();
        W.set_ward_capacity();
        W.setDoctor();
        W.setNurse1();
        W.setNurse2();
        W.setWardboy1();
        W.setWardboy2();
        String statement = "INSERT INTO ward(wardname, wardType, wardcapacity, wardDoctorID,nurse1,nurse2,wardboy1,wardboy2) VALUES('"
                + W.wardname + "','" + W.wardType + "'," + W.wardCapacity + ",'" + W.wardDoctorID + "','" + W.nurse1
                + "','" + W.nurse2 + "','" + W.wardboy1 + "','" + W.wardboy2 + "')";
        db.startstatement();
        db.update(statement);

        System.out.println("\n---------New ward added!---------");
    }

    public static void main(String[] args) {
        create_ward();
    }
}
