package softwareeng;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class UserTest {

    DaoI dao = new Dao();

    public UserTest() throws Exception {

        Timestamp logged_in = new Timestamp(System.currentTimeMillis());
        String query = "";

        String email = "facultytest@iitrpr.ac.in";
        String role = "faculty";

        System.out.println("LOGGED IN TIME : " + logged_in);

        query = "insert into logs(email,role,logged_in) values ('" + email + "','" + role + "', '" + logged_in
                + "');";

        dao.updatequery(query);
    }

    @Test
    public void update_phone_number() throws Exception {

        String input = "1\n7008257139\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        User.update_profile(sc);

        String output = out.toString();
        assertTrue(output.contains("Phone number updated successfully"));

    }
    @Test
    public void valid_phone_number_check() throws Exception {

        String input = "1\n70082571399\n6\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        User.update_profile(sc);

        String output = out.toString();
        assertTrue(output.contains("Invalid"));

    }

    @Test
    public void update_name() throws Exception {

        String input = "\n2\nfacultytest\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.update_profile(sc);

        String output = out.toString();
        assertTrue(output.contains("Name updated successfully"));

    }
    @Test
    public void update_password() throws Exception {

        String input = "\n3\nfacultytestpass\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.update_profile(sc);

        String output = out.toString();
        assertTrue(output.contains("Password updated successfully"));

    }
    @Test
    public void update_DOJ() throws Exception {

        String input = "\n4\n2018-03-04\n7\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);

        Scanner sc = new Scanner(System.in);
        Faculty.sc = sc;
        Faculty.update_profile(sc);

        String output = out.toString();
        assertTrue(output.contains("Joining date updated successfully"));

    }
}
