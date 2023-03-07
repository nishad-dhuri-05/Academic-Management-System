package softwareeng;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    static DaoI dao = new Dao();

    static String query = "";
    static ResultSet rs;
    static int x;

    public static void update_profile(Scanner sc) throws Exception {

        System.out.println("================= UPDATE PROFILE =================");

        while (true) {

            String phone_number_field = "phone_number";
            String name_field = "name";
            String department_field = "department";
            String password_field = "password";
            String joining_data_field = "joining_date";

            System.out.println(" \n Select field to update : ");
            System.out.println("1. " + phone_number_field);
            System.out.println("2. " + name_field);
            System.out.println("3. " + password_field);
            System.out.println("4. " + joining_data_field);
            System.out.println("5. Go Back ");

            int option = 0;
            option = sc.nextInt();
            sc.nextLine();

            String email = "";

            query = "Select email from logs;";
            rs = dao.readquery(query);

            rs.last();
            email = rs.getString("email");

            if (option == 1) {
                System.out.println("Enter new " + phone_number_field);
                String new_number = sc.nextLine();

                Pattern ptrn = Pattern.compile("(0/91)?[0-9]{10}");
                Matcher match = ptrn.matcher(new_number);

                boolean b = match.find() && match.group().equals(new_number);

                if (b) {

                    query = String.format("update auth set phone_number = '%s' where email = '%s' ", new_number, email);
                    x = dao.updatequery(query);

                    System.out.println("Phone number updated successfully");
                } else {
                    System.out.println("Invalid Phone Number");
                }

            } else if (option == 2) {
                System.out.println("Enter new " + name_field);
                String new_name = sc.nextLine();

                query = String.format("update auth set name = '%s' where email = '%s'", new_name, email);
                x = dao.updatequery(query);

                System.out.println("Name updated successfully");

            } else if (option == 3) {
                System.out.println("Enter new " + password_field);
                String new_pass = sc.nextLine();

                query = String.format("update auth set password = '%s' where email = '%s' ", new_pass, email);
                x = dao.updatequery(query);

                System.out.println("Password updated successfully");

            } else if (option == 4) {
                System.out.println("Enter new " + joining_data_field + " ( Format : YYYY-MM-DD)");
                String new_doj = sc.nextLine();

                query = String.format("update auth set joining_date = '%s' where email = '%s' ", new_doj, email);
                x = dao.updatequery(query);

                System.out.println("Joining date updated successfully");

            } else {
                break;
            }

        }

    }
}
