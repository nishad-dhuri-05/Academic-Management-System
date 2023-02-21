package softwareeng;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.google.common.io.Files;

public class AcadOffice {

    public static void main() throws Exception {

        while (true) {

            System.out.println("\n==================================================");

            System.out.println("Select Operation : ");
            System.out.println("1. Edit Course Catalog");
            System.out.println("2. View Grade of All Students");
            System.out.println("3. Generate Transcript");
            System.out.println("4. Logout");

            int option = 0;

            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                catalog();
            } else if (option == 2) {
                view_grades();
            } else if (option == 3) {
                transcript();
            } else if (option == 4) {
                return;
            } else {
                System.out.println("Select a valid option \n");
            }

            System.out.println("\n**************************************************");
        }

    }

    public static void catalog() throws Exception {
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        while (true) {

            System.out.println("\n Select Operation : ");
            System.out.println("1. View Course Catalog");
            System.out.println("2. Add a course");
            System.out.println("3. Update a course");
            System.out.println("4. Delete a course");
            System.out.println("5. Go Back");

            int option = 0;
            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                String query = "SELECT * FROM pre_reqs,course_catalog where course_catalog.course_code=pre_reqs.course_code;";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);

                Formatter fmt = new Formatter();
                fmt.format("\n %20s | %20s | %20s | %20s \n", "COURSE CODE", "L-T-P-C", "PRE-REQUISITES", "DEPARTMENT");

                while (rs.next()) {
                    String course_code = rs.getString("course_code");
                    String l = rs.getString("l");
                    String t = rs.getString("t");
                    String p = rs.getString("p");
                    String credits = rs.getString("credits");
                    String department = rs.getString("department");
                    String pre_req = rs.getString("pre_req");

                    String ltpc = l + "-" + t + "-" + p + "-" + credits;
                    fmt.format("\n %20s | %20s | %20s  | %20s\n", course_code, ltpc, pre_req, department);

                }

                System.out.println(fmt);
            }

            else if (option == 2) {

                String course_code, pre_req, department;
                float l, t, p;
                float credits;

                System.out.println("\nEnter Course Code \n");
                Scanner sc1 = new Scanner(System.in);
                course_code = sc1.nextLine();

                System.out.println("\nEnter Lecture hours \n");
                l = Float.parseFloat(sc1.nextLine());

                System.out.println("\nEnter Tutorial hours \n");
                t = Float.parseFloat(sc1.nextLine());

                System.out.println("\nEnter Practical hours \n");
                p = Float.parseFloat(sc1.nextLine());

                System.out.println("\nEnter Credits \n");
                credits = Float.parseFloat(sc1.nextLine());

                System.out.println("\nEnter Department \n");
                department = sc1.nextLine();

                String query = String.format(
                        "insert into course_catalog (course_code,L,T,P,credits,department) values ('%s', %f, %f , %f , %f , '%s');",
                        course_code, l, t, p, credits, department);

                Statement st = con.createStatement();
                int x = st.executeUpdate(query);

                while (true) {
                    System.out.println("\nEnter pre-requisites (Enter NIL if none). Once done, enter q \n");
                    pre_req = sc.nextLine();
                    if (pre_req.equalsIgnoreCase("q")) {
                        break;
                    } else {
                        String query_ = "insert into pre_reqs (course_code,pre_req) values ('"
                                + course_code + "','" + pre_req + "');";
                        Statement st_ = con.createStatement();
                        x = st_.executeUpdate(query_);
                    }
                }

            }

            else if (option == 3) {
                String course_code, pre_req, department;
                Float l, t, p, credits;

                System.out.println("Enter Course Code To Update \n");
                Scanner sc1 = new Scanner(System.in);
                course_code = sc1.nextLine();

                String query = "SELECT * FROM pre_reqs,course_catalog where course_catalog.course_code=pre_reqs.course_code and course_catalog.course_code='"
                        + course_code + "' ;";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);

                Formatter fmt = new Formatter();
                fmt.format("\n %20s | %20s | %20s | %20s \n", "COURSE CODE", "L-T-P-C", "PRE-REQUISITES", "DEPARTMENT");

                while (rs.next()) {
                    course_code = rs.getString("course_code");
                    String display_l = rs.getString("l");
                    String display_t = rs.getString("t");
                    String display_p = rs.getString("p");
                    String display_credits = rs.getString("credits");
                    pre_req = rs.getString("pre_req");
                    department = rs.getString("department");

                    String ltpc = display_l + "-" + display_t + "-" + display_p + "-" + display_credits;
                    fmt.format("\n %20s | %20s | %20s  | %20s\n", course_code, ltpc, pre_req, department);

                }

                System.out.println("\nEnter Lecture hours \n");
                l = sc1.nextFloat();
                sc1.nextLine();

                System.out.println("\nEnter Tutorial hours \n");
                t = sc1.nextFloat();
                sc1.nextLine();

                System.out.println("\nEnter Practical hours \n");
                p = sc1.nextFloat();
                sc1.nextLine();

                System.out.println("\nEnter Credits \n");
                credits = sc1.nextFloat();
                sc1.nextLine();

                System.out.println("\nEnter Department \n");
                department = sc1.nextLine();

                query = "update course_catalog set l=" + l + ", t= " + t + ", p=" + p + ", credits=" + credits
                        + ", department='" + department
                        + "' where course_code='" + course_code + "';";

                System.out.println(query);

                Statement st1 = con.createStatement();
                int x = st1.executeUpdate(query);

                while (true) {
                    int option_pre = 0;
                    System.out.println("\nSelect Option \n");

                    System.out.println("\n1. Add new pre-requisite \n");
                    System.out.println("\n2. Delete pre-requisite \n");
                    System.out.println("\n3. No change \n");

                    option_pre = sc1.nextInt();
                    sc1.nextLine();

                    if (option_pre == 1) {
                        System.out.println("\nEnter pre-requisite to be added ");
                        pre_req = sc1.nextLine();

                        String query_ = "insert into pre_reqs (course_code,pre_req) values ('"
                                + course_code + "','" + pre_req + "');";
                        Statement st_ = con.createStatement();
                        x = st_.executeUpdate(query_);
                    } else if (option_pre == 2) {
                        System.out.println("\nEnter pre-requisite to be deleted ");
                        pre_req = sc1.nextLine();

                        String query_ = "delete from pre_reqs where course_code='" + course_code + "' and pre_req='"
                                + pre_req + "';";
                        Statement st_ = con.createStatement();
                        x = st_.executeUpdate(query_);
                    } else {
                        break;
                    }

                }
            } else if (option == 4) {
                String course_code;
                int option_;
                System.out.println("Enter course code to be deleted \n");
                Scanner sc1 = new Scanner(System.in);
                course_code = sc1.nextLine();

                System.out.println("Press 1 to confirm deletion \n");
                // Scanner sc1 = new Scanner(System.in);
                option_ = sc1.nextInt();
                sc1.nextLine();

                if (option_ == 1) {
                    String query_ = "delete from pre_reqs where course_code='" + course_code
                            + "'; delete from course_catalog where course_code='" + course_code + "';";
                    Statement st_ = con.createStatement();
                    int x = st_.executeUpdate(query_);

                }

            } else {
                return;
            }
        }

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
                query = "select * from enrollments where status!='RUNNING'";

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

            Formatter fmt = new Formatter();
            fmt.format("\n %30s | %30s | %30s | %30s \n", "ENTRY_NO", "COURSE_CODE", "GRADE", "STATUS");

            while (rs.next()) {
                String entry_no = rs.getString("entry_no");
                String course_code = rs.getString("course_code");
                String grade = rs.getString("grade");
                String status = rs.getString("status");

                fmt.format("\n %30s | %30s | %30s | %30s", entry_no, course_code, grade, status);
            }

            System.out.println(fmt);
        }

    }

    public static void transcript() throws Exception {

        

    }

    public static float get_cgpa() throws Exception {

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
        grade_map.put("E", 2);
        grade_map.put("F", 0);

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
        float cgpa = total_points / total_credits;

        System.out.println(String.format(
                " \n ==================================================================================== \n "));
        System.out
                .println(String.format("                          YOUR CGPA IS %s                             ", cgpa));
        System.out.println(
                " \n ==================================================================================== \n ");

        return cgpa;

    }

}
