package com.covidmain;

import java.util.*;

//function to generate nurse id
class nurseID {
    static String nurseID = "";

    static String nurseIDgenerator() {
        long nurseCount = NurseSQL.getNurseCount();
        System.out.println("NurseCount: " + nurseCount);

        String nurseCountStr = "";
        if (nurseCount >= 0 && nurseCount < 10) {
            nurseCountStr = "0000" + String.valueOf(nurseCount);
        } 
        else if(nurseCount >= 10 && nurseCount < 100) {
            nurseCountStr = "000" + String.valueOf(nurseCount);
        }
        else if(nurseCount >= 100 && nurseCount < 1000) {
            nurseCountStr = "00" + String.valueOf(nurseCount);
        }
        else if(nurseCount >= 1000 && nurseCount < 10000) {
            nurseCountStr = "0" + String.valueOf(nurseCount);
        }
        
        if (nurseCount == -1) {
            System.out.println("Error in fetching database!");
        } else {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            nurseID = 'N' + String.valueOf(year) + nurseCountStr;
        }

        return nurseID;
    }
}

public class Nurse extends nurseID {
    String N_id;
    String N_name;
    int N_slot;
    String N_phone;
    String N_add;
    String N_mail;
    char N_status;

    // default constructor
    Nurse() {
        this.N_id = "";
        this.N_name = "";
        this.N_slot = 0;
        this.N_phone = "";
        this.N_add = "";
        this.N_mail = "";
        this.N_status = 'N';
    }

    Scanner sc = new Scanner(System.in);

    // setters
    void setNurseID() {
        String tempN_ID = nurseIDgenerator();
        if (tempN_ID == "") {
            System.out.println("Error!! Can't set Nurse ID");
        } else {
            this.N_id = tempN_ID;
        }
    }

    void setNurseName() {
        sc.nextLine();
        System.out.print("\nEnter Name        : ");
        String nursename = sc.nextLine();
        this.N_name = nursename;
    }

    void setNurseSlot() {
        System.out.print("\nEnter slot no.    : ");
        int slot_no = sc.nextInt();
        this.N_slot = slot_no;
    }

    void setNursePhone() {
        sc.nextLine();
        System.out.print("\nEnter Mobile No    : ");
        String nurseMob = sc.nextLine();
        if (checkers.mobileChecker(nurseMob)) {
            this.N_name = nurseMob;
        } else {
            System.out.println("Invalid Mobile No.!!!");
            System.out.print("Please Re-");
            setNursePhone();
        }
    }

    void setNurseAdd() {
        sc.nextLine();
        System.out.print("\nEnter Address     : ");
        String nurseadd = sc.nextLine();
        this.N_add = nurseadd;
    }

    void setNurseMail() {
        sc.nextLine();
        System.out.print("\nEnter E-mail     : ");
        String nursemail = sc.nextLine();
        if (checkers.emailChecker(nursemail)) {
            this.N_mail = nursemail;
        } else {
            System.out.println("Invalid mail!!");
            System.out.print("Please Re-");
            setNurseMail();
        }
    }

    void setNurseStatus() {
        sc.nextLine();
        System.out.print("\nEnter Nursstatus     : ");
        char nursestatus = sc.next().charAt(0);
        this.N_status = nursestatus;
        // TODO Adding checker for entered status
    }

    // TODO -getters

    // function to add new nurse
    void addNurse() {
        Nurse N1 = new Nurse();
        N1.setNurseID();
        N1.setNurseName();
        N1.setNurseSlot();
        N1.setNursePhone();
        N1.setNurseAdd();
        N1.setNurseMail();
        N1.setNurseStatus();

        if (NurseSQL.addNewNurse(N1)) {
            System.out.println("New nurse added successfully!!");
        } else {
            System.out.println("Caution! Some error occured!!");
        }
    }

    // function to remove nurse
    void removeNurse() {
        // here we don't have to remove nurse details from database;
        // only change status to n

    }

    void detailNurse() {
    }

    // void attendenceNurse(){}

    public static void main(String[] args) {
        System.out.print("\nTemp ID: " + nurseIDgenerator());
    }

}
