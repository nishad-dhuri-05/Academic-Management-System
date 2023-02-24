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
    public void catalog_1_test() throws Exception {

        String input = "acad@iitrpr.ac.in\nacadpass\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
       
        Auth.main(con);

        input = "\n1\n1\n" ;
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.catalog(con);
        AcadOffice.catalog_1(con);

        String output = out.toString();
        assertTrue(output.contains("COURSE CODE"));

    }

    @Test
    public void catalog_2_test() throws Exception {

        String input = "acad@iitrpr.ac.in\nacadpass\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
       
        Auth.main(con);

        input = "\n1\n2\n" ;
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        // System.setOut(ps);

        AcadOffice.catalog(con);

        input = "TEST\n9\n9\n9\n9\nTEST\nNIL\nq\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        out = new ByteArrayOutputStream();
        ps = new PrintStream(out);
        System.setOut(ps);

        AcadOffice.catalog_2(con);

        String output = out.toString();
        assertTrue(output.contains("Successfully"));

    }

    

}
