package softwareeng;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AcadOffice {
    public static int main() throws Exception {

        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        System.out.println("\n==================================================");

        System.out.println("Select Operation : ");
        System.out.println("1. Edit Course Catalog");
        System.out.println("2. View Grade of All Students");
        System.out.println("3. Generate Transcript");
        System.out.println("4. Logout");

        int option = 0;
        Scanner sc = new Scanner(System.in);
        option = sc.nextInt();
        sc.close();

        if (option == 1) {
            catalog();
        } else if (option == 2) {
            grade();
        } else if (option == 3) {
            transcript();
        } else if (option == 4) {
            return option;
        } else {
            System.out.println("Select a number among 1,2,3 \n");
        }

        System.out.println("\n==================================================");
        return option;

    }

    public static void catalog() {

    }

    public static void grade() {

    }

    public static void transcript() {

    }
}
