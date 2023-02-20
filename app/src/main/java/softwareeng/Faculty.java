package softwareeng;

import java.sql.*;
import java.util.Formatter;
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
            System.out.println("3. View All Course Offerings");
            System.out.println("4. Upload Grades");
            System.out.println("5. View Grades");
            System.out.println("6. Logout");

            int option = 0;

            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                register_course_offering();
            } else if (option == 2) {
                deregister_course_offering();
            } else if (option == 3) {
                view_offerings();
            } else if (option == 4) {
                upload_grades();
            } else if (option == 5) {
                view_grades();
            } else if (option == 6) {
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

        int current_start_acad_year = 0;
        int current_semester = 0;

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

        while (rs.next()) {
            email = rs.getString("email");
        }

        // Department Check

        String course_dept = "", faculty_dept = "";
        query = "Select department from auth where email='" + email + "';";
        st = con.createStatement();
        rs = st.executeQuery(query);

        while (rs.next()) {
            faculty_dept = rs.getString("department");
        }

        System.out.println("Enter the course code you want to offer");
        Scanner sc = new Scanner(System.in);
        String course_code = sc.nextLine();

        query = "Select department from course_catalog where course_code='" + course_code + "';";
        st = con.createStatement();
        rs = st.executeQuery(query);

        while (rs.next()) {
            course_dept = rs.getString("department");
        }

        if (!course_dept.equalsIgnoreCase(faculty_dept)) {
            System.out.println("Please offer a course from your department only");
        } else {
            query = String.format(
                    "Insert into course_offering(course_code,start_acad_year,semester,instructor_email,offering_dept,status) values ('%s','%d','%d','%s','%s','%s');",
                    course_code, current_start_acad_year, current_semester, email, course_dept, "RUNNING");
            System.out.println(query);

            st = con.createStatement();
            int m = st.executeUpdate(query);

            System.out.println(
                    "Enter Restrictions (Offered Department,Batch,Minimum CGPA,Type) \n ");
            String offered_dept = "";
            int batch = 0;
            String type = "";
            Float min_cgpa = (float) 0;
            System.out.println(
                    "\n====================================================================================\nIf there are multiple restrictions, please enter one by one for each offered department (Batch also has to be specified 2019 CSE is different from 2020 CSE, so multiple restrictions.)\n");
            while (true) {

                System.out.println("\n NEW RESTRICTION \n");

                System.out.println("Enter offered Department");
                offered_dept = sc.nextLine();

                System.out.println("Enter corresponding offered department's batch");
                batch = sc.nextInt();
                sc.nextLine();

                System.out.println("Enter Minimum CGPA ");
                min_cgpa = sc.nextFloat();
                sc.nextLine();

                System.out.println("Enter course type (PC/PE/BTP) ");
                type = sc.nextLine();

                query = String.format(
                        "insert into offered_to (course_code,start_acad_year, semester, offered_dept,batch,min_cgpa,type) values('%s',%d,%d,'%s',%d,%f,'%s');",
                        course_code, current_start_acad_year, current_semester, offered_dept, batch, min_cgpa, type);

                System.out.println(query);
                st = con.createStatement();
                int x = st.executeUpdate(query);

                String confirmation = "";
                System.out.println("Do you want to add another restriction ? (Yes/No)");
                confirmation = sc.nextLine();

                if (confirmation.equalsIgnoreCase("No")) {
                    break;
                }
            }

            System.out.println(
                    "\n====================================================================================\n");

        }

    }

    public static void deregister_course_offering() {

    }

    public static void view_offerings() throws Exception {

        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        String query = "";
        Statement st;
        ResultSet rs;

        query = "select course_offering.course_code,course_offering.start_acad_year,course_offering.semester,instructor_email,offered_dept, batch, min_cgpa, type "
                +
                "FROM course_offering " +
                "inner join offered_to on course_offering.course_code=offered_to.course_code and course_offering.start_acad_year=offered_to.start_acad_year and course_offering.semester=offered_to.semester;";
        st = con.createStatement();
        rs = st.executeQuery(query);

        Formatter fmt = new Formatter();
        fmt.format("\n %30s | %30s | %30s | %30s | %30s | %30s | %30s | %30s \n", "COURSE_CODE", "ACAD_YEAR",
                "SEMESTER", "INSTRUCTOR_Email", "OFFERED_DEPT", "BATCH", "MIN CGPA", "TYPE");

        while (rs.next()) {
            // course_dept = rs.getString("department");
            String course_code = rs.getString("course_code");
            String start_acad_year = rs.getString("start_acad_year");
            String semester = rs.getString("semester");
            String instructor_email = rs.getString("instructor_email");
            String offered_dept = rs.getString("offered_dept");
            String batch = rs.getString("batch");
            String min_cgpa = rs.getString("min_cgpa");
            String type = rs.getString("type");

            fmt.format("\n %30s | %30s | %30s | %30s | %30s | %30s | %30s | %30s", course_code, start_acad_year,
                    semester, instructor_email, offered_dept, batch, min_cgpa, type);
        }
        System.out.println(fmt);

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
