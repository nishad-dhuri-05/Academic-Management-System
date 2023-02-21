package softwareeng;

import java.sql.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

public class Student {
    public static void main(Connection con) throws Exception {

        while (true) {

            System.out.println("\n==================================================");

            System.out.println("Select Operation : ");
            System.out.println("1. Register in a course");
            System.out.println("2. Deregister in a course");
            System.out.println("3. View Enrollments and Grades");
            System.out.println("4. Compute CGPA");
            System.out.println("5. Track your Graduation");
            System.out.println("6. Update Profile");
            System.out.println("7. Logout");

            int option = 0;

            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                coursereg(con);
            } else if (option == 2) {
                coursedereg(con);
            } else if (option == 3) {
                view_grades(con);
            } else if (option == 4) {
                float x = get_cgpa(con);
            } else if (option == 5) {
                track_grad(con);
            } else if (option == 6) {
                update_profile(con);
            } else if (option == 7) {
                return;
            } else {
                System.out.println("Select a valid option \n");
            }

            System.out.println("\n**************************************************");

        }

    }

    public static void coursereg(Connection con) throws Exception {

        String email = "";
        String query = "";
        String entry_no = "";
        String department = "";
        int batch = 0;

        Statement st;
        ResultSet rs;
        int x;

        // Fetch Calendar

        int current_start_acad_year = 0;
        int current_semester = 0;

        query = "Select * from calendar ;";

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

        int prev_prev_acad_year = 0, prev_prev_semester = 0;
        if (prev_semester == 2) {
            prev_prev_acad_year = prev_acad_year;
            prev_prev_semester = 1;
        } else {
            prev_prev_acad_year = prev_acad_year - 1;
            prev_prev_semester = 2;
        }

        // Get All Student Info

        query = "Select email from logs;";

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        rs.last();
        email = rs.getString("email");

        query = "Select * from auth where email= '" + email + "';";
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        while (rs.next()) {
            entry_no = rs.getString("entry_no");
            department = rs.getString("department");
            batch = Integer.parseInt(rs.getString("batch"));
        }

        float student_cgpa = get_cgpa(con);
        int eligible = -1;

        view_offerings(con);

        // Get Credit Limit

        float credit_limit = 0;
        credit_limit = (float) (1.25
                * ((get_credits(prev_acad_year, prev_semester, con)
                        + get_credits(prev_prev_acad_year, prev_prev_semester, con))
                        / 2));

        System.out.println(String.format(
                "Previous two semesters credits : \n YEAR : %d SEM : %d Credits : %f \n YEAR : %d SEM : %d Credits : %f ",
                prev_acad_year, prev_semester, get_credits(prev_acad_year, prev_semester, con), prev_prev_acad_year,
                prev_prev_semester, get_credits(prev_prev_acad_year, prev_prev_semester, con)));

        System.out.println("Credit Limit :" + credit_limit);

        // Calculate current credits

        float current_credits = 0;

        query = String.format(
                "select credits from enrollments,course_catalog where entry_no = '%s' and start_acad_year= %d and semester = %d and enrollments.course_code = course_catalog.course_code and status = 'RUNNING';",
                entry_no, current_start_acad_year, current_semester);

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        while (rs.next()) {
            current_credits += Float.parseFloat(rs.getString("credits"));
        }

        System.out.println("Current Credits :" + current_credits);

        // Select Course for Enrollment
        System.out.println("Enter course code you want to register in: ");
        String course_code = "";
        Scanner sc = new Scanner(System.in);
        course_code = sc.nextLine();

        float course_credit = 0;
        query = "Select credits from course_catalog where course_code = '" + course_code + "';";

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        while (rs.next()) {
            course_credit = Float.parseFloat(rs.getString("credits"));
        }

        // Check Eligibility

        if (current_credits + course_credit > credit_limit) {
            System.out.println("Credit Limit Exceeding ! Course Registration Failed");
        } else {

            query = String.format(
                    "select * from offered_to where course_code = '%s' and start_acad_year = %d and semester = %d and offered_dept = '%s' and batch = %d ;",
                    course_code, current_start_acad_year, current_semester, department, batch);

            // System.out.println(query);
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(query);

            if (!rs.isBeforeFirst()) {
                System.out.println("Course not offered to your batch ! Course Registration Failed");
                eligible = 0;
            }

            else {

                // Check CGPA

                int min_cgpa = 0;
                while (rs.next()) {
                    min_cgpa = Integer.parseInt(rs.getString("min_cgpa"));
                }

                if (student_cgpa >= min_cgpa) {

                    // Check pre-reqs

                    query = String.format("select * from pre_reqs where course_code = '%s';", course_code);
                    st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    rs = st.executeQuery(query);

                    int flag = 0;
                    while (rs.next()) {

                        String pre_req_course_code = rs.getString("pre_req");

                        if (pre_req_course_code.equals("NIL")) {
                            break;
                        }

                        query = String.format(
                                "select * from enrollments where entry_no = '%s' and course_code = '%s' and status = 'PASSED';",
                                entry_no, pre_req_course_code);

                        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        ResultSet rs2 = st.executeQuery(query);

                        if (!rs2.isBeforeFirst()) {
                            flag = 1;
                            System.out.println(String.format("Pre-requisite %s not met ! Course Registration Failed",
                                    pre_req_course_code));
                            break;
                        }
                    }

                    if (flag == 0) {
                        eligible = 1;
                    } else {
                        eligible = 0;
                    }

                } else {
                    System.out.println("CGPA not met ! Course Registration Failed");
                    eligible = 0;
                }
            }

            if (eligible == 1) {

                query = String.format(
                        "insert into enrollments (entry_no,course_code,status,start_acad_year,semester) values ( '%s' , '%s' , 'RUNNING' , %d, %d);",
                        entry_no, course_code, current_start_acad_year, current_semester);

                st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                x = st.executeUpdate(query);

                if (x == 1) {
                    System.out.println("Course Registered Successfully");
                } else {
                    System.out.println("Course Registration Failed");
                }
            } else {
                System.out.println("Eligibility criteria not met ! Course Registration Failed");
            }
        }
    }

    public static void coursedereg(Connection con) throws Exception {

        String email = "";
        String query = "";
        String entry_no = "";
        String department = "";
        int batch = 0;

        Statement st;
        ResultSet rs;
        int x;

        // Fetch Calendar

        int current_start_acad_year = 0;
        int current_semester = 0;

        query = "Select * from calendar ;";

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        while (rs.next()) {
            current_start_acad_year = Integer.parseInt(rs.getString("start_acad_year"));
            current_semester = Integer.parseInt(rs.getString("semester"));
        }

        // Get All Student Info

        query = "Select email from logs;";

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        rs.last();
        email = rs.getString("email");

        query = "Select * from auth where email= '" + email + "';";
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        while (rs.next()) {
            entry_no = rs.getString("entry_no");
            department = rs.getString("department");
            batch = Integer.parseInt(rs.getString("batch"));
        }

        // Select Course for De-Enrollment

        System.out.println("Enter course code you want to de-register from: ");
        String course_code = "";
        Scanner sc = new Scanner(System.in);
        course_code = sc.nextLine();

        query = String.format(
                "select * from enrollments where entry_no = '%s' and course_code = '%s' and start_acad_year = %d and semester = %d and status = 'RUNNING';",
                entry_no, course_code, current_start_acad_year, current_semester);

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        if (!rs.isBeforeFirst()) {
            System.out.println("Course not registered ! Course De-Registration Failed");
        } else {

            query = String.format(
                    "update enrollments set status = 'DROPPED' where entry_no = '%s' and course_code = '%s' and start_acad_year = %d and semester = %d and status = 'RUNNING';",
                    entry_no, course_code, current_start_acad_year, current_semester);

            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            x = st.executeUpdate(query);

            if (x == 1) {
                System.out.println("Course De-Registered Successfully");
            } else {
                System.out.println("Course De-Registration Failed");
            }

        }
    }

    public static void view_grades(Connection con) throws Exception {

        String email = "";
        String query = "";
        String entry_no = "";
        String department = "";
        int batch = 0;

        Statement st;
        ResultSet rs;
        int x;

        query = "Select email from logs;";

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        rs.last();
        email = rs.getString("email");

        query = "Select * from auth where email= '" + email + "';";
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        while (rs.next()) {
            entry_no = rs.getString("entry_no");
            department = rs.getString("department");
            batch = Integer.parseInt(rs.getString("batch"));
        }

        query = String.format(
                "select enrollments.course_code,grade,status,type from enrollments,offered_to where entry_no = '%s' and batch = %d and offered_dept= '%s' and enrollments.course_code = offered_to.course_code and enrollments.start_acad_year = offered_to.start_acad_year and enrollments.semester = offered_to.semester ;",
                entry_no, batch, department);
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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

    public static float get_cgpa(Connection con) throws Exception {

        String email = "";
        String query = "";
        String entry_no = "";
        String department = "";
        int batch = 0;

        Statement st;
        ResultSet rs;
        int x;

        query = "Select email from logs;";

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        rs.last();
        email = rs.getString("email");

        query = "Select * from auth where email= '" + email + "';";
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
                "select enrollments.course_code,grade,status,type,credits from enrollments,offered_to,course_catalog where course_catalog.course_code = enrollments.course_code and entry_no = '%s' and enrollments.course_code = offered_to.course_code and enrollments.start_acad_year = offered_to.start_acad_year and enrollments.semester = offered_to.semester and status!='RUNNING' and status!='INSTRUCTOR_WITHDREW' and status!='DROPPED';",
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

        System.out.println(String.format(
                " \n ==================================================================================== \n "));
        System.out
                .println(String.format("                          YOUR CGPA IS %s                             ", cgpa));
        System.out.println(
                " \n ==================================================================================== \n ");

        return cgpa;

    }

    public static void track_grad(Connection con) throws Exception {

        String email = "";
        String query = "";
        String entry_no = "";
        String department = "";
        int batch = 0;

        Statement st;
        ResultSet rs;
        int x;

        query = "Select email from logs;";

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        rs.last();
        email = rs.getString("email");

        query = "Select * from auth where email= '" + email + "';";
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
                "select enrollments.course_code,grade,status,type,credits from enrollments,offered_to,course_catalog where course_catalog.course_code = enrollments.course_code and entry_no = '%s' and enrollments.course_code = offered_to.course_code and enrollments.start_acad_year = offered_to.start_acad_year and enrollments.semester = offered_to.semester and status!='RUNNING' and status!='INSTRUCTOR_WITHDREW' and status!='DROPPED' ;",
                entry_no);
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        String course_code = "", grade = "", status = "", type = "";
        float total_credits = 0, credits = 0;
        float total_points = 0;
        int pc_count = 0;
        int pe_count = 0;
        int btp_count = 0;

        Formatter fmt = new Formatter();
        fmt.format("\n %20s | %20s | %20s | %20s \n", "COURSE CODE", "GRADE", "status", "type");

        while (rs.next()) {
            course_code = rs.getString("course_code");
            grade = rs.getString("grade");
            status = rs.getString("status");
            type = rs.getString("type");
            credits = Float.parseFloat(rs.getString("credits"));

            fmt.format("\n %20s | %20s | %20s | %20s \n", course_code, grade, status, type);

            total_credits = total_credits + credits;
            total_points = total_points + credits * (grade_map.get(grade));

            if (course_code.equalsIgnoreCase("CP301") || course_code.equalsIgnoreCase("CP302")) {
                btp_count = btp_count + 1;
            }

            if (type.equals("PC")) {
                pc_count = pc_count + 1;
            }

            if (type.equals("PE")) {
                pe_count = pe_count + 1;
            }
        }
        float cgpa = total_points / total_credits;

        if (pc_count >= 20 && pe_count >= 20 && btp_count == 2 && cgpa >= 5) {

            System.out.println(
                    " \n ==================================================================================== \n ");
            System.out.println(
                    "                      CONGRATULATIONS! YOU ARE ELIGIBLE FOR GRADUATION !                    ");
            System.out.println(
                    " \n ==================================================================================== \n ");

        } else {

            System.out.println(
                    " \n ==================================================================================== \n ");
            System.out.println(
                    "                                   YOU ARE NOT ELIGIBLE FOR GRADUATION !                     ");
            System.out.println(
                    " \n ==================================================================================== \n ");

            System.out.println("Here is your list of completed courses");

            System.out.println(fmt);

        }

    }

    public static void update_profile(Connection con) throws Exception {

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
            Scanner sc = new Scanner(System.in);
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

            } else {
                break;
            }

        }

    }

    public static void view_offerings(Connection con) throws Exception {

        String query = "";
        Statement st;
        ResultSet rs;

        query = "select course_offering.course_code,course_offering.start_acad_year,course_offering.semester,instructor_email,offered_dept, batch, min_cgpa, type "
                +
                "FROM course_offering " +
                "inner join offered_to on course_offering.course_code=offered_to.course_code and course_offering.start_acad_year=offered_to.start_acad_year and course_offering.semester=offered_to.semester;";
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        Formatter fmt = new Formatter();
        fmt.format("\n %15s | %10s | %10s | %30s | %15s | %10s | %10s | %10s \n", "COURSE_CODE", "ACAD_YEAR",
                "SEMESTER", "INSTRUCTOR_EMAIL", "OFFERED_DEPT", "BATCH", "MIN CGPA", "TYPE");

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

            fmt.format("\n %15s | %10s | %10s | %30s | %15s | %10s | %10s | %10s", course_code, start_acad_year,
                    semester, instructor_email, offered_dept, batch, min_cgpa, type);
        }
        System.out.println(fmt);

    }

    public static float get_credits(int acad_year, int semester, Connection con) throws Exception {

        String email = "";
        String query = "";
        String entry_no = "";
        String department = "";
        int batch = 0;

        Statement st;
        ResultSet rs;
        int x;

        query = "Select email from logs;";

        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        rs.last();
        email = rs.getString("email");

        query = "Select * from auth where email= '" + email + "';";
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
                "select enrollments.course_code,grade,status,type,credits from enrollments,offered_to,course_catalog where enrollments.start_acad_year =%d and enrollments.semester=%d and course_catalog.course_code = enrollments.course_code and entry_no = '%s' and enrollments.course_code = offered_to.course_code and enrollments.start_acad_year = offered_to.start_acad_year and enrollments.semester = offered_to.semester and status!='RUNNING' and status!='DROPPED' and status!='INSTRUCTOR WITHDREW' ;",
                acad_year, semester, entry_no);
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = st.executeQuery(query);

        String course_code = "", grade = "", status = "", type = "";
        float total_credits = 0, credits = 0;

        while (rs.next()) {
            course_code = rs.getString("course_code");
            grade = rs.getString("grade");
            status = rs.getString("status");
            type = rs.getString("type");
            credits = Float.parseFloat(rs.getString("credits"));

            total_credits = total_credits + credits;
        }
        return total_credits;
    }

}
