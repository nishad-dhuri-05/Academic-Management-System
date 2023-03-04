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
        String path = "C:\\Users\\subha\\OneDrive\\Desktop\\Acads\\CS305\\software-eng\\CS305-miniproject\\app\\grades\\grades_CSTEST.csv";
        input = String.format("4\nCSTEST\n%s\n5098\n\n\n",path);
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

}
