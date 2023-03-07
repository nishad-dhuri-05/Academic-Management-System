package softwareeng;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Auth {
    static Scanner sc = new Scanner(System.in);
    static DaoI dao = new Dao();

    static String query = "";
    static ResultSet rs;
    static int x;

    public static String main() throws Exception{

        String role = "";
        String email = "";
        String email_pass = "";
        String user_name = "";

        System.out.print("Enter Email : ");
        email = sc.nextLine();
        System.out.print("Enter Password : ");
        String user_pass = sc.nextLine();

        String query = "select name,role,password from auth where email='" + email + "'";
        rs = dao.readquery(query);

        while (rs.next()) {
            role = rs.getString("role");
            email_pass = rs.getString("password");
            user_name = rs.getString("name");
        }

        if (user_pass.equals(email_pass)) {

            Timestamp logged_in = new Timestamp(System.currentTimeMillis());

            System.out.println("LOGGED IN TIME : " + logged_in);

            query = "insert into logs(email,role,logged_in) values ('" + email + "','" + role + "', '" + logged_in
                    + "');";

            x = dao.updatequery(query);

            System.out.println("\n==================================================\n Welcome " + user_name);
            System.out.println("You are logged in as " + role);
            System.out.println("==================================================");
        } else {
            System.out.println("Login Failed");
            role = "";
        }
        return role;
    }
}
