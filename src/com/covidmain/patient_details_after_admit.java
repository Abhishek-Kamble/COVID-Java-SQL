

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

class patient {

    long patient_id;

    long patientIDgenerator() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // class loader function
            System.out.print("Driver Loaded Successfully.. ");

            // connection call to oracle server
            // port:: C:\oraclexe\app\oracle\product\11.2.0\server\network\ADMIN
            Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system",
                    "muskan786");
            System.out.println("Database connected Successfully!\n");

            // Creates a Statement object for sendingSQL statements to the database.
            Statement stmt = connect.createStatement();

            // fetching data and storing to variable
            ResultSet rs = stmt.executeQuery("SELECT COUNT(P_ID) FROM patient_Entry_details");

            // new code
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
    String p_adm_date;
    String p_dis_date;
    String p_bg;
    double p_temp;
    double p_weight;
    String p_O2level;
    String p_covlevel;

    patient_details_after_admit() {
        this.p_adm_date = "";
        this.p_dis_date = "";
        this.p_bg = "";
        this.p_temp = 0.0;
        this.p_weight = 0.0;
        this.p_O2level = "";
        this.p_covlevel = "";
    }

    Scanner sc = new Scanner(System.in);

    void setpatientadmitdate() {
        sc.nextLine();
        System.out.print("\nEnter admit date of patient..   : ");
        String admdate = sc.nextLine();
        this.p_adm_date = admdate;
    }

    void setpatientdischargedate() {
        sc.nextLine();
        System.out.print("\nEnter discharge date of patient..   : ");
        String disdate = sc.nextLine();
        this.p_dis_date = disdate;
    }

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

    void setpatient_weight() {
        sc.nextLine();
        System.out.print("\nEnter Weight of patient in KG   : ");
        double patientweight = sc.nextDouble();
        this.p_weight = patientweight;
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
}
