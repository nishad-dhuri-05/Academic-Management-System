package softwareeng;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Auth {
    public static String main(Connection con) throws Exception {

        String role = "";
        String email = "";
        String email_pass = "";
        String user_name = "";

        System.out.print("Enter Email : ");
        Scanner sc = new Scanner(System.in);
        email = sc.nextLine();
        System.out.print("Enter Password : ");
        String user_pass = sc.nextLine();

        String query = "select name,role,password from auth where email='" + email + "'";
        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            role = rs.getString("role");
            email_pass = rs.getString("password");
            user_name = rs.getString("name");
        }

        if (user_pass.equals(email_pass)) {

            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = "truncate table logs; insert into logs(email,role) values ('" + email + "','" + role + "');";
            int m = st.executeUpdate(query);

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
