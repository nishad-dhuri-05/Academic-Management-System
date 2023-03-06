/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package softwareeng;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AppTest {

    static Connection con;

    public AppTest() throws Exception {
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        this.con = DriverManager.getConnection(url, username, password);
    }

    @Test
    public void login_fail() throws Exception {

        String input = "acadtest@iitrpr.ac.in\nacadtestpasswrong\n1\n\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        String[] args = {"arg1"};
        App.main(args);

        String output = out.toString();
        assertTrue(output.contains("Thank you for using our application"));

    }

    @Test
    public void login_acad() throws Exception {

        //

        String input = "acadtest@iitrpr.ac.in\nacadtestpass\n7\n1\n\n\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        String[] args = { "arg1" };
        App.main(args);

        String output = out.toString();
        assertTrue(output.contains("Thank you for using our application"));

    }
    @Test
    public void login_fac() throws Exception {

        String input = "facultytest@iitrpr.ac.in\nfacultytestpass\n7\n1\n\n\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        String[] args = { "arg1" };
        App.main(args);

        String output = out.toString();
        assertTrue(output.contains("Thank you for using our application"));

    }
    @Test
    public void login_stu() throws Exception {

        String input = "2020csbtest@iitrpr.ac.in\n2020csbtest\n7\n1\n\n\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        String[] args = { "arg1" };
        App.main(args);

        String output = out.toString();
        assertTrue(output.contains("Thank you for using our application"));

    }
    @Test
    public void login_stu_1() throws Exception {

        String input = "2020csbtest@iitrpr.ac.in\n2020csbtest\n7\n0\n2020csbtest@iitrpr.ac.in\n2020csbtest\n7\n1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        String[] args = { "arg1" };
        App.main(args);

        String output = out.toString();
        assertTrue(output.contains("Thank you for using our application"));

    }
   
}
