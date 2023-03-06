package softwareeng;

import org.junit.Test;
import static org.junit.Assert.*;

// import java.sql.;
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

public class AcadOfficeTest {

    static Connection con;
  
    public AcadOfficeTest() throws Exception {

        
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");
        
        Class.forName("org.postgresql.Driver");
        this.con = DriverManager.getConnection(url, username, password);

        Timestamp logged_in = new Timestamp(System.currentTimeMillis());
        String query = "";  

        String email = "acadtest@iitrpr.ac.in";
        String role = "acad";
        
        System.out.println("LOGGED IN TIME : " + logged_in);
        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
        query = "insert into logs(email,role,logged_in) values ('" + email + "','" + role + "', '" + logged_in
        + "');";
        int m = st.executeUpdate(query);
    }

    // @Test
    // public void main_test_7() throws Exception {

    //     String input = "";
    //     ByteArrayInputStream in;

    //     input = "7\n\n";
    //     in = new ByteArrayInputStream(input.getBytes());
    //     System.setIn(in);

    //     ByteArrayOutputStream out = new ByteArrayOutputStream();
    //     PrintStream ps = new PrintStream(out);
    //     System.setOut(ps);

    //     Scanner sc = new Scanner(System.in);
    //     AcadOffice.sc = sc;
    //     AcadOffice.main(con);

    //     String output = out.toString();
    //     assertTrue(output.contains("LOGGED OUT SUCCESSFULLY"));

    // }
 

    @Test
    public void catalog_test() throws Exception {

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
        AcadOffice.main(con);

        String output = out.toString();
        assertTrue(output.contains("COURSE CODE"));

    }

   
    @Test
    public void catalog_2_test() throws Exception {

        String input = "\n2\nTEST\n9\n9\n9\n9\nTEST\nNIL\nq\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Course Added Successfully."));

    }

    @Test
    public void catalog_3_test() throws Exception {

        String input = "\n3\nTEST\n9\n9\n9\n9\nTEST\n3\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Course Updated Successfully."));

    }

    @Test
    public void catalog_3_test_add() throws Exception {

        String input = "3\nTEST\n9\n9\n9\n9\nTEST\n1\nCSTEST\n3\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Pre-requisite added successfully"));

    }

    @Test
    public void catalog_3_test_delete() throws Exception {

        String input = "\n3\nTEST\n9\n9\n9\n9\nTEST\n2\nCSTEST\n3\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Pre-requisite deleted successfully"));

    }

    @Test
    public void catalog_4_test() throws Exception {

        String input = "\n4\nTEST\n1\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Course Deleted Successfully"));

    }
    @Test
    public void catalog_4_test_fail() throws Exception {

        String input = "\n4\nTEST\n0\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Deletion Cancelled"));

    }

    @Test
    public void catalog_5_test() throws Exception {

        String input = "\n5\n\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Select Operation"));

    }

    @Test
    public void catalog_6_test() throws Exception {

        String input = "\n99\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.catalog(con);

        String output = out.toString();
        assertTrue(output.contains("Select Operation"));

    }


    @Test
    public void view_grade_test_1() throws Exception {

        String input = "2\n1\n4\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main(con);

        String output = out.toString();
        assertTrue(output.contains("GRADE"));

    }

    @Test
    public void view_grade_test_2() throws Exception {

        String input = "\n2\nCS305\n4\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.view_grades(con);

        String output = out.toString();
        assertTrue(output.contains("GRADE"));

    }

    @Test
    public void view_grade_test_3() throws Exception {

        String input = "\n3\n2020csb1317\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.view_grades(con);

        String output = out.toString();
        assertTrue(output.contains("GRADE"));

    }

    @Test
    public void transcript_test() throws Exception {

        String input = "3\n2020csb1317\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main(con);

        String output = out.toString();
        assertTrue(output.contains("Transcript generated successfully."));

    }
    @Test
    public void transcript_test_fail() throws Exception {

        String input = "3\n2020csb131799\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main(con);

        String output = out.toString();
        assertTrue(output.contains("Invalid entry number."));

    }

    @Test
    public void logs_test() throws Exception {

        String input = "5\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main(con);

        String output = out.toString();
        assertTrue(output.contains("LOGGED IN TIME"));

    }

    @Test
    public void calendar_test() throws Exception {

        String input = "6\n2022\n2\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.main(con);

        String output = out.toString();
        assertTrue(output.contains("Calendar updated successfully"));

    }

    @Test
    public void update_profile_test_1() throws Exception {

        String input = "4\n1\n7008257139\n4\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;

        AcadOffice.main(con);

        String output = out.toString();
        assertTrue(output.contains("Phone Number updated successfully"));

    }

    @Test
    public void update_profile_test_1_fail() throws Exception {

        String input = "4\n1\n70082571399\n4\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;

        AcadOffice.main(con);

        String output = out.toString();
        assertTrue(output.contains("Invalid"));

    }

    @Test
    public void update_profile_test_2() throws Exception {

        String input = "\n2\nStaff\sDean\soffice\n4\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.update_profile(con);

        String output = out.toString();
        assertTrue(output.contains("Name updated successfully"));

    }

    @Test
    public void update_profile_test_3() throws Exception {

        String input = "\n3\nacadtestpass\n4\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        AcadOffice.sc = sc;
        AcadOffice.update_profile(con);

        String output = out.toString();
        assertTrue(output.contains("Password updated successfully"));

    }

    @Test
    public void main_test_99() throws Exception {

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
        AcadOffice.main(con);

        String output = out.toString();
        assertTrue(output.contains("Select a valid option"));

    }

}
