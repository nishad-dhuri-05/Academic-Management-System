package softwareeng;

import java.sql.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Student {
    public static void main() throws Exception {

        while (true) {

            System.out.println("\n==================================================");

            System.out.println("Select Operation : ");
            System.out.println("1. Register in a course");
            System.out.println("2. Deregister in a course");
            System.out.println("3. View Grades");
            System.out.println("4. Compute CGPA");
            System.out.println("5. Track your Graduation");
            System.out.println("6. Update Profile");
            System.out.println("7. Logout");

            int option = 0;

            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                coursereg();
            } else if (option == 2) {
                coursedereg();
            } else if (option == 3) {
                view_grades();
            } else if (option == 4) {
                get_cgpa();
            } else if (option == 5) {
                track_grad();
            } else if (option == 6) {
                update_profile();
            } else if (option == 7) {
                return;
            } else {
                System.out.println("Select a valid option \n");
            }

            System.out.println("\n**************************************************");

        }

    }

    public static void coursereg() throws Exception {

    }

    public static void coursedereg() throws Exception {

    }

    public static void view_grades() throws Exception {

        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        String email = "";
        String query = "";
        String entry_no = "";
        String department = "";
        int batch = 0;

        Statement st;
        ResultSet rs;
        int x;

        query = "Select email from logs;";

        st = con.createStatement();
        rs = st.executeQuery(query);

        while (rs.next()) {
            email = rs.getString("email");
        }

        query = "Select * from auth where email= '" + email + "';";
        st = con.createStatement();
        rs = st.executeQuery(query);

        while (rs.next()) {
            entry_no = rs.getString("entry_no");
            department = rs.getString("department");
            batch = Integer.parseInt(rs.getString("batch"));
        }

        query = String.format(
                "select enrollments.course_code,grade,status,type from enrollments,offered_to where entry_no = '%s' and enrollments.course_code = offered_to.course_code and enrollments.start_acad_year = offered_to.start_acad_year and enrollments.semester = offered_to.semester ;",
                entry_no);
        st = con.createStatement();
        rs = st.executeQuery(query);

        Formatter fmt = new Formatter();
        fmt.format("\n %20s | %20s | %20s | %20s \n", "COURSE CODE", "GRADE", "status", "type");

        while (rs.next()) {
            String course_code = rs.getString("course_code");
            String grade = rs.getString("grade");
            String status = rs.getString("status");
            String type = rs.getString("type");

            fmt.format("\n %20s | %20s | %20s | %20s \n", course_code, grade, status, type);

        }

        System.out.println(fmt);

    }

    public static void get_cgpa() throws Exception {

        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        String email = "";
        String query = "";
        String entry_no = "";
        String department = "";
        int batch = 0;

        Statement st;
        ResultSet rs;
        int x;

        query = "Select email from logs;";

        st = con.createStatement();
        rs = st.executeQuery(query);

        while (rs.next()) {
            email = rs.getString("email");
        }

        query = "Select * from auth where email= '" + email + "';";
        st = con.createStatement();
        rs = st.executeQuery(query);

        while (rs.next()) {
            entry_no = rs.getString("entry_no");
            department = rs.getString("department");
            batch = Integer.parseInt(rs.getString("batch"));
        }

        HashMap<String, Integer> grade_map = new HashMap<String, Integer>();
        grade_map.put("A", 10);
        grade_map.put("A-", 9);
        grade_map.put("B", 8);
        grade_map.put("B-", 7);
        grade_map.put("C", 6);
        grade_map.put("C-", 5);
        grade_map.put("D", 4);

        query = String.format(
                "select enrollments.course_code,grade,status,type,credits from enrollments,offered_to,course_catalog where course_catalog.course_code = enrollments.course_code and entry_no = '%s' and enrollments.course_code = offered_to.course_code and enrollments.start_acad_year = offered_to.start_acad_year and enrollments.semester = offered_to.semester and status!='RUNNING' and grade!='W' ;",
                entry_no);
        st = con.createStatement();
        rs = st.executeQuery(query);

        String course_code = "", grade = "", status = "", type = "";
        float total_credits = 0, credits = 0;
        float total_points = 0;

        while (rs.next()) {
            course_code = rs.getString("course_code");
            grade = rs.getString("grade");
            status = rs.getString("status");
            type = rs.getString("type");
            credits = Float.parseFloat(rs.getString("credits"));

            total_credits = total_credits + credits;
            total_points = total_points + credits * (grade_map.get(grade));
        }
        float cgpa = total_points /  total_credits;

        System.out.println(String.format(" \n ====================== Entry Num : %s YOUR CGPA IS : ====================== \n ",entry_no));
        System.out.println(cgpa);
        System.out.println(" \n ==================================================================================== \n ");

        

    }

    public static void track_grad() throws Exception {
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        String email = "";
        String query = "";
        String entry_no = "";
        String department = "";
        int batch = 0;

        Statement st;
        ResultSet rs;
        int x;

        query = "Select email from logs;";

        st = con.createStatement();
        rs = st.executeQuery(query);

        while (rs.next()) {
            email = rs.getString("email");
        }

        query = "Select * from auth where email= '" + email + "';";
        st = con.createStatement();
        rs = st.executeQuery(query);

        while (rs.next()) {
            entry_no = rs.getString("entry_no");
            department = rs.getString("department");
            batch = Integer.parseInt(rs.getString("batch"));
        }

        query = String.format(
                "select enrollments.course_code,grade,status,type from enrollments,offered_to where entry_no = '%s' and enrollments.course_code = offered_to.course_code and enrollments.start_acad_year = offered_to.start_acad_year and enrollments.semester = offered_to.semester ;",
                entry_no);
        st = con.createStatement();
        rs = st.executeQuery(query);

        Formatter fmt = new Formatter();
        fmt.format("\n %20s | %20s | %20s | %20s \n", "COURSE CODE", "GRADE", "status", "type");

        while (rs.next()) {
            String course_code = rs.getString("course_code");
            String grade = rs.getString("grade");
            String status = rs.getString("status");
            String type = rs.getString("type");

            fmt.format("\n %20s | %20s | %20s | %20s \n", course_code, grade, status, type);

        }

        System.out.println(fmt);

    }

    public static void update_profile() throws Exception {
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        while (true) {

            String email_field = "email";
            String name_field = "name";
            String department_field = "department";
            String password_field = "password";
            String joining_data_field = "joining_date";

            System.out.println(" \n Select field to update : ");
            System.out.println("1. " + email_field);
            System.out.println("2. " + name_field);
            System.out.println("3. " + department_field);
            System.out.println("4. " + password_field);
            System.out.println("5. " + joining_data_field);
            System.out.println("6. Go Back ");

            int option = 0;
            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();
            sc.nextLine();

            String email = "";
            String query = "";
            Statement st;
            ResultSet rs;
            int x;

            query = "Select email from logs;";

            st = con.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                email = rs.getString("email");
            }

            if (option == 1) {
                System.out.println("Enter new " + email_field);
                String new_email = sc.nextLine();

                query = String.format("update auth set email = '%s' where email = '%s' ", new_email, email);
                st = con.createStatement();
                x = st.executeUpdate(query);

            } else if (option == 2) {
                System.out.println("Enter new " + name_field);
                String new_name = sc.nextLine();

                query = String.format("update auth set name = '%s' where email = '%s'", new_name, email);
                st = con.createStatement();
                x = st.executeUpdate(query);
            } else if (option == 3) {
                System.out.println("Enter new " + department_field);
                String new_dept = sc.nextLine();

                query = String.format("update auth set department = '%s' where email = '%s'", new_dept, email);
                st = con.createStatement();
                x = st.executeUpdate(query);
            } else if (option == 4) {
                System.out.println("Enter new " + password_field);
                String new_pass = sc.nextLine();

                query = String.format("update auth set password = '%s' where email = '%s' ", new_pass, email);
                st = con.createStatement();
                x = st.executeUpdate(query);
            } else if (option == 5) {
                System.out.println("Enter new " + joining_data_field + " ( Format : YYYY-MM-DD)");
                String new_doj = sc.nextLine();

                query = String.format("update auth set joining_date = '%s' where email = '%s' ", new_doj, email);
                st = con.createStatement();
                x = st.executeUpdate(query);
            } else {
                break;
            }

        }
    }

}
