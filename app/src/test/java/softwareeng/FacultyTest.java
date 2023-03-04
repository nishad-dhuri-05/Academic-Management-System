package softwareeng;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.junit.Test;
import static org.junit.Assert.*;

public class FacultyTest {

    static Connection con;

    public FacultyTest() throws Exception {

        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        this.con = DriverManager.getConnection(url, username, password);

        Timestamp logged_in = new Timestamp(System.currentTimeMillis());
        String query = "";

        String email = "facultytest@iitrpr.ac.in";
        String role = "faculty";

        System.out.println("LOGGED IN TIME : " + logged_in);
        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        query = "insert into logs(email,role,logged_in) values ('" + email + "','" + role + "', '" + logged_in
                + "');";
        int m = st.executeUpdate(query);

    }

    @Test
    public void register_course_offering_test() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "1\nCSTEST\nCSE\n2020\n7.5\nPC\nNo\n5098\n\n\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.main(con);

        String output = out.toString();
        assertTrue(output.contains("Course offering successfully registered !"));

    }

    @Test
    public void grade_upload_test() throws Exception {

        String input = "";
        ByteArrayInputStream in;
        String path1 = "C:\\Users\\subha\\OneDrive\\Desktop\\Acads\\CS305\\software-eng\\CS305-miniproject\\app\\grades\\grades_CSTEST.csv";
        input = String.format("4\nCSTEST\n%s\n5098\n\n\n", path1);
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.main(con);

        String output = out.toString();
        assertTrue(output.contains("Grades Uploaded Successfully !"));

    }
   
    @Test
    public void grade_upload_test_fail() throws Exception {

        String input = "";
        ByteArrayInputStream in;
        input = String.format("4\nCS305\n5098\n\n\n");
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.main(con);

        String output = out.toString();
        assertTrue(output.contains("You are not the instructor for this course offering !"));

    }

    @Test
    public void deregister_course_offering_test() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "2\nCSTEST\n5098\n\n\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.main(con);

        String output = out.toString();
        assertTrue(output.contains("Course Offering De-Registered Successfully !"));

    }
    @Test
    public void deregister_course_offering_test_fail() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "2\nCSXYZ\n5098\n\n\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.main(con);

        String output = out.toString();
        assertTrue(output.contains("Course Offering De-Registration Failed"));

    }

    @Test
    public void view_catalog_test() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "3\n5098\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.main(con);

        String output = out.toString();
        assertTrue(output.contains("COURSE CATALOG"));

    }

    @Test
    public void view_grade_test_1() throws Exception {

        String input = "5\n1\n4\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.main(con);

        String output = out.toString();
        assertTrue(output.contains("VIEW GRADES"));

    }

    @Test
    public void view_grade_test_2() throws Exception {

        String input = "\n2\nCSTEST\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.view_grades(con);

        String output = out.toString();
        assertTrue(output.contains("GRADE"));

    }

    @Test
    public void view_grade_test_3() throws Exception {

        String input = "\n3\n2020csbtest\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.view_grades(con);

        String output = out.toString();
        assertTrue(output.contains("GRADE"));

    }
    
    @Test
    public void update_profile_test_1() throws Exception {

        String input = "6\n1\n7008257139\n6\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.main(con);

        String output = out.toString();
        assertTrue(output.contains("Phone number updated successfully"));

    }

    @Test
    public void update_profile_test_2() throws Exception {

        String input = "\n2\nfaculty\stest\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.update_profile(con);

        String output = out.toString();
        assertTrue(output.contains("Name updated successfully"));

    }
    @Test
    public void update_profile_test_3() throws Exception {

        String input = "\n3\nCSE\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.update_profile(con);

        String output = out.toString();
        assertTrue(output.contains("Department updated successfully"));

    }

    @Test
    public void update_profile_test_4() throws Exception {

        String input = "\n4\nfacultytestpass\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.update_profile(con);

        String output = out.toString();
        assertTrue(output.contains("Password updated successfully"));

    }
    @Test
    public void update_profile_test_5() throws Exception {

        String input = "\n5\n2018-03-04\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.update_profile(con);

        String output = out.toString();
        assertTrue(output.contains("Joining date updated successfully"));

    }

    @Test
    public void valid_option_test() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "89\n5098\n\n\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.main(con);

        String output = out.toString();
        assertTrue(output.contains("Select a valid option"));

    }

}
