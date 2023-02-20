package softwareeng;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.google.common.util.concurrent.Service.State;

public class Faculty {
    public static void main() throws Exception {

        while (true) {

            System.out.println("\n==================================================");

            System.out.println("Select Operation : ");
            System.out.println("1. Register a Course Offering");
            System.out.println("2. Deregister a Course Offering");
            System.out.println("3. Upload Grades");
            System.out.println("4. View Grades");
            System.out.println("5. Logout");

            int option = 0;

            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                register_course_offering();
            } else if (option == 2) {
                deregister_course_offering();
            } else if (option == 3) {
                upload_grades();
            } else if (option == 4) {
                view_grades();
            } else if (option == 5) {
                return;
            } else {
                System.out.println("Select a valid option \n");
            }

            System.out.println("\n==================================================");
        }

    }

    public static void register_course_offering() throws Exception {

        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        String query = "";
        Statement st;
        ResultSet rs;

        // Fetch Calendar

        int current_start_acad_year;
        int current_semester;

        query = "Select * from calendar ;";

        st = con.createStatement();
        rs = st.executeQuery(query);

        while (rs.next()) {
            current_start_acad_year = Integer.parseInt(rs.getString("start_acad_year"));
            current_semester = Integer.parseInt(rs.getString("semester"));
        }

        // Fetch Email

        String email = "";
        query = "Select email from logs;";

        st = con.createStatement();
        rs = st.executeQuery(query);

        // Department Check

        String course_dept = "", faculty_dept = "";
        query = "Select department from ";

        System.out.println("Enter the course code you want to offer");
        System.out.println();

    }

    public static void deregister_course_offering() {

    }

    public static void upload_grades() {

    }

    public static void view_grades() throws Exception {
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        while (true) {

            System.out.println("\n Select Operation : ");
            System.out.println("1. View grades of all students in their enrollments");
            System.out.println("2. View grades of students in a particular course");
            System.out.println("3. View grades of a particular student ");
            System.out.println("4. Go Back");

            int option = 0;
            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();
            sc.nextLine();

            String query = "";
            Statement st;

            if (option == 1) {
                query = "select * from enrollments where status='COMPLETED'";

            } else if (option == 2) {
                String course_code;

                System.out.println("Enter Course Code  \n");
                sc = new Scanner(System.in);
                course_code = sc.nextLine();

                query = "select * from enrollments where course_code = '" + course_code + "'";

            } else if (option == 3) {
                String entry_no;

                System.out.println("Enter student entry no.  \n");
                sc = new Scanner(System.in);
                entry_no = sc.nextLine();

                query = "select * from enrollments where entry_no = '" + entry_no + "'";

            } else {
                return;
            }

            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println(" Entry_No | Course_code | grade | status ");
            while (rs.next()) {
                String entry_no = rs.getString("entry_no");
                String course_code = rs.getString("course_code");
                String grade = rs.getString("grade");
                String status = rs.getString("status");

                System.out.println("  " + entry_no + "  " + "|" + course_code + "|" + grade + "|" + status);

            }
        }

    }
}
