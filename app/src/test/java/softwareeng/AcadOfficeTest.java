package softwareeng;

import org.junit.Test;
import static org.junit.Assert.*;

// import java.sql.;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;

public class AcadOfficeTest {

    DaoI dao = new Dao();
  
    public AcadOfficeTest() throws Exception {

        Timestamp logged_in = new Timestamp(System.currentTimeMillis());
        String query = "";  

        String email = "acadtest@iitrpr.ac.in";
        String role = "acad";
        
        System.out.println("LOGGED IN TIME : " + logged_in);

        query = "insert into logs(email,role,logged_in) values ('" + email + "','" + role + "', '" + logged_in
        + "');";
        dao.updatequery(query);
        
    }

    @Test
    public void view_catalog() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "1\n1\n5\n7\n\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main();

        String output = out.toString();
        assertTrue(output.contains("COURSE CODE"));

    }

   
    @Test
    public void add_course() throws Exception {

        String input = "\n2\nTEST\n9\n9\n9\n9\nTEST\nNIL\nq\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog();

        String output = out.toString();
        assertTrue(output.contains("Course Added Successfully."));

    }

    @Test
    public void update_course() throws Exception {

        String input = "\n3\nTEST\n9\n9\n9\n9\nTEST\n3\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog();

        String output = out.toString();
        assertTrue(output.contains("Course Updated Successfully."));

    }

    @Test
    public void add_prereq() throws Exception {

        String input = "3\nTEST\n9\n9\n9\n9\nTEST\n1\nCSTEST\n3\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog();

        String output = out.toString();
        assertTrue(output.contains("Pre-requisite added successfully"));

    }

    @Test
    public void delete_prereq() throws Exception {

        String input = "\n3\nTEST\n9\n9\n9\n9\nTEST\n2\nCSTEST\n3\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog();

        String output = out.toString();
        assertTrue(output.contains("Pre-requisite deleted successfully"));

    }

    @Test
    public void delete_course() throws Exception {

        String input = "\n4\nTEST\n1\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog();

        String output = out.toString();
        assertTrue(output.contains("Course Deleted Successfully"));

    }
    @Test
    public void delete_course_cancellation() throws Exception {

        String input = "\n4\nTEST\n0\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog();

        String output = out.toString();
        assertTrue(output.contains("Deletion Cancelled"));

    }

    @Test
    public void invalid_option() throws Exception {

        String input = "\n99\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog();

        String output = out.toString();
        assertTrue(output.contains("Select Operation"));

    }


    @Test
    public void view_grade_all() throws Exception {

        String input = "2\n1\n4\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main();

        String output = out.toString();
        assertTrue(output.contains("GRADE"));

    }

    @Test
    public void view_grade_course() throws Exception {

        String input = "\n2\nCS305\n4\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.view_grades();

        String output = out.toString();
        assertTrue(output.contains("GRADE"));

    }

    @Test
    public void view_grade_student() throws Exception {

        String input = "\n3\n2020csb1317\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.view_grades();

        String output = out.toString();
        assertTrue(output.contains("GRADE"));

    }

    @Test
    public void generate_transcript() throws Exception {

        String input = "3\n2020csb1317\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main();

        String output = out.toString();
        assertTrue(output.contains("Transcript generated successfully."));

    }
    @Test
    public void generate_transcript_invalid() throws Exception {

        String input = "3\n2020csb131799\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main();

        String output = out.toString();
        assertTrue(output.contains("Invalid entry number."));

    }

    @Test
    public void view_logs() throws Exception {

        String input = "5\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main();

        String output = out.toString();
        assertTrue(output.contains("LOGGED IN TIME"));

    }

    @Test
    public void update_calendar() throws Exception {

        String input = "6\n2022\n2\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main();

        String output = out.toString();
        assertTrue(output.contains("Calendar updated successfully"));

    }
    
    @Test
    public void update_profile() throws Exception {

        String input = "4\n1\n7008257139\n5\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main();

        String output = out.toString();
        assertTrue(output.contains("Phone number updated successfully"));

    }
    @Test
    public void valid_option_test() throws Exception {

        String input = "";
        ByteArrayInputStream in;

        input = "99\n7\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main();

        String output = out.toString();
        assertTrue(output.contains("Select a valid option"));

    }

}
