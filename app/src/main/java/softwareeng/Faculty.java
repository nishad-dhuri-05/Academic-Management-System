package softwareeng;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Faculty {
    public static void main() throws Exception{
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

    }
}
