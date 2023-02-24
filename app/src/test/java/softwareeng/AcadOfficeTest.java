package softwareeng;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class AcadOfficeTest {

    static Connection con;

    public AcadOfficeTest() throws Exception {
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        this.con = DriverManager.getConnection(url, username, password);
    }

    

    @Test
    public void catalog_test() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "\n5098\n\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Operation"));

    }

    @Test
    public void catalog_1_test() throws Exception {

        String input = "\n1\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("COURSE CODE"));

    }

    @Test
    public void catalog_2_test() throws Exception {

        String input = "\n2\nTEST\n9\n9\n9\n9\nTEST\nNIL\nq\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Course Added Successfully."));

    }

    @Test
    public void catalog_3_test() throws Exception {

        String input = "\n3\nTEST\n9\n9\n9\n9\nTEST\n5098\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Course Updated Successfully."));

    }

    @Test
    public void catalog_3_test_add() throws Exception {

        String input = "3\nTEST\n9\n9\n9\n9\nTEST\n1\nCSTEST\n5098\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Pre-requisite added successfully"));

    }

    @Test
    public void catalog_3_test_delete() throws Exception {

        String input = "\n3\nTEST\n9\n9\n9\n9\nTEST\n2\nCSTEST\n5098\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Pre-requisite deleted successfully"));

    }

    @Test
    public void catalog_4_test() throws Exception {

        String input = "\n4\nTEST\n1\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Course Deleted Successfully"));

    }

    @Test
    public void catalog_5_test() throws Exception {

        String input = "\n5\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Select Operation"));

    }

    @Test
    public void catalog_6_test() throws Exception {

        String input = "\n99\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Select Operation"));

    }

    @Test
    public void view_grade_test() throws Exception {

        String input = "\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.view_grades(con);

        String output = out.toString();
        assertTrue(output.contains("grades"));

    }

    @Test
    public void view_grade_test_1() throws Exception {

        String input = "\n1\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.view_grades(con);

        String output = out.toString();
        assertTrue(output.contains("GRADE"));

    }

    @Test
    public void view_grade_test_2() throws Exception {

        String input = "\n2\nCS305\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.view_grades(con);

        String output = out.toString();
        assertTrue(output.contains("GRADE"));

    }

    @Test
    public void view_grade_test_3() throws Exception {

        String input = "\n3\n2020csb1317\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.view_grades(con);

        String output = out.toString();
        assertTrue(output.contains("GRADE"));

    }

    @Test
    public void transcript_test() throws Exception {

        String input = "2020csb1317\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.transcript(con);

        String output = out.toString();
        assertTrue(output.contains("Transcript generated successfully."));

    }

    @Test
    public void logs_test() throws Exception {

        String input = "\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.view_logs(con);

        String output = out.toString();
        assertTrue(output.contains("LOGGED IN TIME"));

    }

    @Test
    public void calendar_test() throws Exception {

        String input = "\n2022\n2\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.update_calendar(con);

        String output = out.toString();
        assertTrue(output.contains("Calendar updated successfully"));

    }

    @Test
    public void update_profile_test() throws Exception {

        String input = "\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.update_profile(con);

        String output = out.toString();
        assertTrue(output.contains("Select field to update"));

    }

    @Test
    public void update_profile_test_1() throws Exception {

        String input = "\n1\n7008257139\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.update_profile(con);

        String output = out.toString();
        assertTrue(output.contains("Phone Number updated successfully"));

    }

    @Test
    public void update_profile_test_2() throws Exception {

        String input = "\n2\nStaff\sDean\soffice\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.update_profile(con);

        String output = out.toString();
        assertTrue(output.contains("Name updated successfully"));

    }

    @Test
    public void update_profile_test_3() throws Exception {

        String input = "\n3\nacadpass\n5098\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.update_profile(con);

        String output = out.toString();
        assertTrue(output.contains("Password updated successfully"));

    }

}
