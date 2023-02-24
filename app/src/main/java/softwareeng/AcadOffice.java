package softwareeng;

import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

public class AcadOffice {


    public static void main(Connection con) throws Exception {

        Scanner sc = new Scanner(System.in);
        String query = "";
        Statement st;
        ResultSet rs;
        int x;

        query = "Select * from logs;";

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        String email = "", role = "", logged_in = "";

        while (rs.next()) {
            email = rs.getString("email");
            role = rs.getString("role");
            logged_in = rs.getString("logged_in");
        }

        while (true) {

            System.out.println("\n==================================================");

            System.out.println("Select Operation : ");
            System.out.println("1. Edit Course Catalog");
            System.out.println("2. View Grade of All Students");
            System.out.println("3. Generate Transcript");
            System.out.println("4. Update Profile");
            System.out.println("5. View Logs");
            System.out.println("6. Update Event Calendar");
            System.out.println("7. Logout");

            int option = 0;

            option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                System.out.println("================= COURSE CATALOG =================");
                catalog(con);
            } else if (option == 2) {
                System.out.println("================= VIEW GRADES =================");
                view_grades(con);
            } else if (option == 3) {
                System.out.println("================= GENERATE TRANSCRIPT =================");
                transcript(con);
            } else if (option == 4) {
                System.out.println("================= UPDATE PROFILE =================");
                update_profile(con);
            } else if (option == 5) {
                System.out.println("================= VIEW LOGS =================");
                view_logs(con);
            } else if (option == 6) {
                System.out.println("================= UPDATE CALENDAR =================");
                update_calendar(con);
            } else if (option == 7) {
                System.out.println("LOGGING OUT ... ");
                Timestamp logged_out = new Timestamp(System.currentTimeMillis());
                
                query = "update logs set logged_out = '" + logged_out + "' where email = '" + email + "' and role = '"
                + role
                + "' and logged_in = '" + logged_in + "';";
                st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                x = st.executeUpdate(query);
                
                System.out.println("LOGGED OUT SUCCESSFULLY ");
                return;
            } else if (option == 5098) {
                break;
            } else {
                System.out.println("Select a valid option \n");
            }

            System.out.println("\n**************************************************");
        }

    }

    public static void catalog(Connection con) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {

            System.out.println("\n Select Operation : ");
            System.out.println("1. View Course Catalog");
            System.out.println("2. Add a course");
            System.out.println("3. Update a course");
            System.out.println("4. Delete a course");
            System.out.println("5. Go Back");

            int option = 0;
            option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                String query = "SELECT * FROM pre_reqs,course_catalog where course_catalog.course_code=pre_reqs.course_code;";
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
                // Scanner sc1 = new Scanner(System.in);
                course_code = sc.nextLine();

                System.out.println("\nEnter Lecture hours \n");
                l = Float.parseFloat(sc.nextLine());

                System.out.println("\nEnter Tutorial hours \n");
                t = Float.parseFloat(sc.nextLine());

                System.out.println("\nEnter Practical hours \n");
                p = Float.parseFloat(sc.nextLine());

                System.out.println("\nEnter Credits \n");
                credits = Float.parseFloat(sc.nextLine());

                System.out.println(
                        "\nEnter Department ('CSE' , 'MA' , 'EE' , 'ME' , 'CE' , 'CH' , 'MME' , 'HS' , 'PH' , 'BME') \n");
                department = sc.nextLine();

                String query = String.format(
                        "insert into course_catalog (course_code,L,T,P,credits,department) values ('%s', %f, %f , %f , %f , '%s');",
                        course_code, l, t, p, credits, department);

                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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

                System.out.println("Course Added Successfully. \n");

            }

            else if (option == 3) {

                String course_code, pre_req, department;
                Float l, t, p, credits;

                System.out.println("Enter Course Code To Update \n");
                course_code = sc.nextLine();

                String query = "SELECT * FROM pre_reqs,course_catalog where course_catalog.course_code=pre_reqs.course_code and course_catalog.course_code='"
                        + course_code + "' ;";
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
                l = sc.nextFloat();
                sc.nextLine();

                System.out.println("\nEnter Tutorial hours \n");
                t = sc.nextFloat();
                sc.nextLine();

                System.out.println("\nEnter Practical hours \n");
                p = sc.nextFloat();
                sc.nextLine();

                System.out.println("\nEnter Credits \n");
                credits = sc.nextFloat();
                sc.nextLine();

                System.out.println(
                        "\nEnter Department ('CSE' , 'MA' , 'EE' , 'ME' , 'CE' , 'CH' , 'MME' , 'HS' , 'PH' , 'BME')\n");
                department = sc.nextLine();

                query = "update course_catalog set l=" + l + ", t= " + t + ", p=" + p + ", credits=" + credits
                        + ", department='" + department
                        + "' where course_code='" + course_code + "';";

                // System.out.println(query);

                Statement st1 = con.createStatement();
                int x = st1.executeUpdate(query);

                while (true) {
                    int option_pre = 0;
                    System.out.println("\nSelect Option \n");

                    System.out.println("\n1. Add new pre-requisite \n");
                    System.out.println("\n2. Delete pre-requisite \n");
                    System.out.println("\n3. No change \n");

                    option_pre = sc.nextInt();
                    sc.nextLine();

                    if (option_pre == 1) {
                        System.out.println("\nEnter pre-requisite to be added ");
                        pre_req = sc.nextLine();

                        String query_ = "insert into pre_reqs (course_code,pre_req) values ('"
                                + course_code + "','" + pre_req + "');";
                        Statement st_ = con.createStatement();
                        x = st_.executeUpdate(query_);

                        if (x == 1) {
                            System.out.println("Pre-requisite added successfully \n");
                        } 

                    } else if (option_pre == 2) {
                        System.out.println("\nEnter pre-requisite to be deleted ");
                        pre_req = sc.nextLine();

                        String query_ = "delete from pre_reqs where course_code='" + course_code + "' and pre_req='"
                                + pre_req + "';";
                        Statement st_ = con.createStatement();
                        x = st_.executeUpdate(query_);

                        if (x == 1) {
                            System.out.println("Pre-requisite deleted successfully \n");
                        }

                    } 
                    else{
                        break;
                    }

                }
                System.out.println("Course Updated Successfully. \n");

            } else if (option == 4) {

                String course_code;
                int option_;
                System.out.println("Enter course code to be deleted \n");
                course_code = sc.nextLine();

                System.out.println("Press 1 to confirm deletion \n");
                option_ = sc.nextInt();
                sc.nextLine();

                if (option_ == 1) {
                    String query_ = "delete from pre_reqs where course_code='" + course_code
                            + "'; delete from course_catalog where course_code='" + course_code + "';";
                    Statement st_ = con.createStatement();
                    int x = st_.executeUpdate(query_);

                }

                System.out.println("Course Deleted Successfully");

            } else if (option == 5) {
                return;
            } else if (option == 5098) {
                break;
            } else {
                System.out.println("Select a valid option \n");
            }
        }

        sc.close();

    }

    public static void view_grades(Connection con) throws Exception {

        Scanner sc = new Scanner(System.in);
        while (true) {

            System.out.println("\n Select Operation : ");
            System.out.println("1. View grades of all students in their enrollments");
            System.out.println("2. View grades of students in a particular course");
            System.out.println("3. View grades of a particular student ");
            System.out.println("4. Go Back");

            int option = 0;
            option = sc.nextInt();
            sc.nextLine();

            String query = "";
            Statement st;

            if (option == 1) {
                query = "select * from enrollments where status!='RUNNING' and status != 'INSTRUCTOR WITHDREW' and status!='DROPPED'";

            } else if (option == 2) {
                String course_code;

                System.out.println("Enter Course Code  \n");
                course_code = sc.nextLine();

                query = "select * from enrollments where course_code = '" + course_code + "'";

            } else if (option == 3) {
                String entry_no;

                System.out.println("Enter student entry no.  \n");
                entry_no = sc.nextLine();

                query = "select * from enrollments where entry_no = '" + entry_no + "'";

            } else if (option == 5098) {
                break;
            } else {
                return;
            }

            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
        sc.close();

    }

    public static void transcript(Connection con) throws Exception {

        String entry_no = "";
        int batch = 0;
        String department = "";

        String query = "";
        Statement st;
        ResultSet rs;

        System.out.println("Enter student entry no.  \n");
        Scanner sc = new Scanner(System.in);
        entry_no = sc.nextLine();

        query = "select * from auth where entry_no = '" + entry_no + "'";
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        if (rs.next()) {
            batch = rs.getInt("batch");
            department = rs.getString("department");
        }

        String filename = String.format("transcripts/%s_transcript.txt", entry_no);

        File fileobj = new File(filename);
        fileobj.createNewFile();

        FileWriter writer = new FileWriter(filename);
        writer.write(String.format(
                "===================================== TRANSCRIPT - %s ===================================== \n\n",
                entry_no));
        writer.write(
                "                               INDIAN INSTITUTE OF TECHNOLOGY, ROPAR                            \n\n");
        writer.write(
                String.format("Entry Number : %s \nBatch : %d \nDepartment : %s \n\n", entry_no, batch, department));
        writer.write(String.format(
                "========================================== COURSES UNDERTAKEN ========================================== \n\n"));

        query = String.format(
                "select * from enrollments where entry_no = '%s' and status != 'RUNNING' and status != 'INSTRUCTOR WITHDREW' and status!='DROPPED' ;",
                entry_no);
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        String text = String.format("\n %20s | %20s | %20s| %20s | %20s\n", "COURSE CODE", "ACAD YEAR", "SEMESTER",
                "GRADE", "STATUS");

        while (rs.next()) {
            String course_code = rs.getString("course_code");
            String grade = rs.getString("grade");
            String status = rs.getString("status");
            String acad_year = rs.getString("start_acad_year");
            String semester = rs.getString("semester");

            text = String.format("\n %20s | %20s | %20s | %20s | %20s \n", course_code, acad_year, semester, grade,
                    status);
            writer.write(text);

        }

        float cgpa = get_cgpa(entry_no, con);
        writer.write("CGPA : " + cgpa);

        // Fetch Calendar

        int current_start_acad_year = 0;
        int current_semester = 0;

        query = "Select * from calendar where status='RUNNING' ;";

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        while (rs.next()) {
            current_start_acad_year = Integer.parseInt(rs.getString("start_acad_year"));
            current_semester = Integer.parseInt(rs.getString("semester"));
        }

        int prev_acad_year = 0;
        int prev_semester = 0;

        if (current_semester == 2) {
            prev_acad_year = current_start_acad_year;
            prev_semester = 1;
        } else {
            prev_acad_year = current_start_acad_year - 1;
            prev_semester = 2;
        }

        writer.write(String.format("\n\nThis transcript is generated for all courses till YEAR : %d , SEMESTER : %d",
                prev_acad_year, prev_semester));
        writer.close();

        System.out.println("Transcript generated successfully. \n");

    }

    public static float get_cgpa(String entry_no, Connection con) throws Exception {

        String query = "";

        Statement st;
        ResultSet rs;
        int x;

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
                "select enrollments.course_code,grade,status,type,credits from enrollments,offered_to,course_catalog where course_catalog.course_code = enrollments.course_code and entry_no = '%s' and enrollments.course_code = offered_to.course_code and enrollments.start_acad_year = offered_to.start_acad_year and enrollments.semester = offered_to.semester and status!='RUNNING' and status!='INSTRUCTOR_WITHDREW' and status!='DROPPED' ;",
                entry_no);
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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

        return cgpa;

    }

    public static void update_profile(Connection con) throws Exception {
        
        Scanner sc = new Scanner(System.in);

        while (true) {

            String phone_number = "phone_number";
            String name_field = "name";
            String password_field = "password";

            System.out.println(" \n Select field to update : ");
            System.out.println("1. " + phone_number);
            System.out.println("2. " + name_field);
            System.out.println("3. " + password_field);
            System.out.println("4. Go Back ");

            int option = 0;
            option = sc.nextInt();
            sc.nextLine();

            String email = "";
            String query = "";
            Statement st;
            ResultSet rs;
            int x;

            query = "Select email from logs;";

            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(query);

            rs.last();
            email = rs.getString("email");

            if (option == 1) {
                System.out.println("Enter new " + phone_number);
                String new_number = sc.nextLine();

                query = String.format("update auth set phone_number = '%s' where email = '%s' ", new_number, email);
                st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                x = st.executeUpdate(query);

                if (x == 1) {
                    System.out.println("Phone Number updated successfully");
                }

            } else if (option == 2) {
                System.out.println("Enter new " + name_field);
                String new_name = sc.nextLine();

                query = String.format("update auth set name = '%s' where email = '%s'", new_name, email);
                st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                x = st.executeUpdate(query);

                if (x == 1) {
                    System.out.println("Name updated successfully");
                }

            } else if (option == 3) {
                System.out.println("Enter new " + password_field);
                String new_pass = sc.nextLine();

                query = String.format("update auth set password = '%s' where email = '%s' ", new_pass, email);
                st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                x = st.executeUpdate(query);

                if (x == 1) {
                    System.out.println("Password updated successfully");
                }

            }else if(option==5098){
                break;
            } else {
                System.out.println("Invalid option");
            }
        }
    }

    public static void view_logs(Connection con) throws Exception {

        String query = "";
        Statement st;
        ResultSet rs;
        int x;

        query = "Select * from logs;";

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        String email = "", role = "", logged_in = "", logged_out = "";

        Formatter fmt = new Formatter();
        fmt.format("\n %30s | %15s | %30s | %30s \n", "EMAIL", "ROLE", "LOGGED IN TIME", "LOGGED OUT TIME");

        while (rs.next()) {
            email = rs.getString("email");
            role = rs.getString("role");
            logged_in = rs.getString("logged_in");
            logged_out = rs.getString("logged_out");

            fmt.format("\n %30s | %15s | %30s | %30s \n", email, role, logged_in, logged_out);
        }

        System.out.println(fmt);

    }

    public static void update_calendar(Connection con) throws Exception {
        String query = "";
        Statement st;
        ResultSet rs;
        Scanner sc = new Scanner(System.in);

        query = "select * from calendar;";
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        String start_acad_year = "", semester = "", status = "";

        Formatter fmt = new Formatter();
        fmt.format("\n %30s | %15s | %20s \n", "ACADEMIC YEAR", "SEMESTER", "STATUS");

        while (rs.next()) {
            start_acad_year = rs.getString("start_acad_year");
            semester = rs.getString("semester");
            status = rs.getString("status");

            int acad_year = Integer.parseInt(start_acad_year);
            int next_acad_year = acad_year + 1;

            String academic_year = start_acad_year + "-" + next_acad_year;

            fmt.format("\n %30s | %15s | %20s \n", academic_year, semester, status);
        }

        System.out.println(fmt);

        System.out.println("Enter new start academic year");
        int new_start_acad_year = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter new semester");
        int new_semester = sc.nextInt();
        sc.nextLine();

        query = String.format(
                "update calendar set status='COMPLETED' where status='RUNNING' ; insert into calendar values (%d,%d,'RUNNING'); ",
                new_start_acad_year, new_semester);

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        int x = st.executeUpdate(query);

        if (x == 1) {
            System.out.println("Calendar updated successfully");
        }

    }

}
