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

public class StudentTest {
    static Connection con;

    public StudentTest() throws Exception {

        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        this.con = DriverManager.getConnection(url, username, password);

        Timestamp logged_in = new Timestamp(System.currentTimeMillis());
        String query = "";

        String email = "2020csbtest@iitrpr.ac.in";
        String role = "student";

        System.out.println("LOGGED IN TIME : " + logged_in);
        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        query = "insert into logs(email,role,logged_in) values ('" + email + "','" + role + "', '" + logged_in+ "');";
        int m = st.executeUpdate(query);

    }

    @Test
    public void register_course_test() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "1\nCS305\n1\nCS306\n7\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.main(con);

        String output = out.toString();
        assertTrue(output.contains("Course Registered Successfully"));

    }

    @Test
    public void deregister_course_test() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "2\nCS305\n2\nCS306\n2\nHS301\n7\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.main(con);

        String output = out.toString();
        assertTrue(output.contains("Course De-Registered Successfully"));

    }

    // @Test
    // public void register_course_test_CSTEST() throws Exception {

    //     String input = "";
    //     ByteArrayInputStream in;

    //     input = "1\nCSTEST\n7\n";
    //     in = new ByteArrayInputStream(input.getBytes());
    //     System.setIn(in);

    //     ByteArrayOutputStream out = new ByteArrayOutputStream();
    //     PrintStream ps = new PrintStream(out);
    //     System.setOut(ps);

    //     Scanner sc = new Scanner(System.in);
    //     Student.sc = sc;
    //     Student.main(con);

    //     String output = out.toString();
    //     assertTrue(output.contains("Pre-requisite CSPRETEST not met"));

    // }
    @Test
    public void register_course_test_CSCGTEST() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "1\nCSCGTEST\n7\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.main(con);

        String output = out.toString();
        assertTrue(output.contains("CGPA criteria not met"));

    }
    @Test
    public void register_course_test_HS301() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "1\nHS301\n2\nHS301\n7\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.main(con);

        String output = out.toString();
        assertTrue(output.contains("Course Registered Successfully"));

    }
    @Test
    public void register_course_test_HS202() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "1\nHS202\n7\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.main(con);

        String output = out.toString();
        assertTrue(output.contains("Course not offered to your batch"));

    }

    @Test
    public void register_course_test_fail() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "1\nCS301\n7\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.main(con);

        String output = out.toString();
        assertTrue(output.contains("Course Registration Failed"));

    }
   

    @Test
    public void view_grade_test() throws Exception {

        String input = "3\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.main(con);

        String output = out.toString();
        assertTrue(output.contains("VIEW GRADES"));

    }
    @Test
    public void view_cgpa_test() throws Exception {

        String input = "4\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.main(con);

        String output = out.toString();
        assertTrue(output.contains("VIEW CGPA"));

    }
    @Test
    public void track_grad_test() throws Exception {

        String input = "5\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.main(con);

        String output = out.toString();
        assertTrue(output.contains("TRACK GRADUATION"));

    }

    @Test
    public void update_profile_test_1() throws Exception {

        String input = "6\n1\n7008257139\n6\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.main(con);

        String output = out.toString();
        assertTrue(output.contains("Phone Number updated successfully"));

    }

    @Test
    public void update_profile_test_2() throws Exception {

        String input = "\n2\nstudent\stest\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.update_profile(con);

        String output = out.toString();
        assertTrue(output.contains("Name updated successfully"));

    }

   @Test
    public void update_profile_test_3() throws Exception {

        String input = "\n3\n2020csbtest\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.update_profile(con);

        String output = out.toString();
        assertTrue(output.contains("Password updated successfully"));

    }

  
    @Test
    public void valid_option_test() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "89\n7\n\n\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Student.sc = sc;
        Student.main(con);

        String output = out.toString();
        assertTrue(output.contains("Select a valid option"));

    }
}
