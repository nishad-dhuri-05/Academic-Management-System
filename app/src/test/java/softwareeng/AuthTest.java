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
import java.util.Scanner;

public class AuthTest { 
    
    static Connection con ;

    public AuthTest() throws Exception {
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        this.con = DriverManager.getConnection(url, username, password);
    }


    // @Test 
    public void login_acad_success() throws Exception {

        // Scanner sc = new Scanner(System.in);

        String input = "acadtest@iitrpr.ac.in\nacadtestpass\n\n\n\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        // System.setOut(ps);
        // Auth.sc = sc;
        String role = Auth.main(con);

        String output = out.toString(); 
        assertEquals(role, "acad");
        // assertTrue(output.contains("acad"));

    }
    // @Test 
    public void LoginFail() throws Exception {

        String input = "acadtest@iitrpr.ac.in\nacadpasswrong\n\n\n\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        System.setOut(ps);
        Auth.main(con);

        String output = out.toString();
        assertTrue(output.contains("Failed"));   

    }


}
