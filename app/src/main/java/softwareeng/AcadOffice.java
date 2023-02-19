package softwareeng;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AcadOffice {

    public static void main() throws Exception {

        while (true) {

            System.out.println("\n==================================================");

            System.out.println("Select Operation : ");
            System.out.println("1. Edit Course Catalog");
            System.out.println("2. View Grade of All Students");
            System.out.println("3. Generate Transcript");
            System.out.println("4. Logout");

            int option = 0;

            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                catalog();
            } else if (option == 2) {
                grade();
            } else if (option == 3) {
                transcript();
            } else if (option == 4) {
                return;
            } else {
                System.out.println("Select a number among 1,2,3 \n");
            }

            System.out.println("\n==================================================");
        }

    }

    public static void catalog() throws Exception {
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password");

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        while (true) {

            System.out.println("\n Select Operation : ");
            System.out.println("1. View Course Catalog");
            System.out.println("2. Add a course");
            System.out.println("3. Update a course");
            System.out.println("4. Go Back");

            int option = 0;
            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();
            sc.nextLine();

            System.out.println("Course Code | L-T-P-C | Pre_req");

            if (option == 1) {
                String query = "Select * from course_catalog ;";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    String course_code = rs.getString("course_code");
                    String l = rs.getString("l");
                    String t = rs.getString("t");
                    String p = rs.getString("p");
                    String credits = rs.getString("credits");
                    String pre_req = rs.getString("pre_req");

                    System.out.println("       " + course_code + "       " + "|" + l + "-" + t + "-" + p + "-" + credits
                            + "|" + pre_req);

                }
            } else if (option == 2) {

                String course_code, pre_req;
                int l, t, p, credits;

                System.out.println("\nEnter Course Code \n");
                Scanner sc1 = new Scanner(System.in);
                course_code = sc1.nextLine();

                System.out.println("\nEnter Lecture hours \n");
                l = Integer.parseInt(sc1.nextLine());

                System.out.println("\nEnter Tutorial hours \n");
                t = Integer.parseInt(sc1.nextLine());

                System.out.println("\nEnter Practical hours \n");
                p = Integer.parseInt(sc1.nextLine());

                System.out.println("\nEnter Credits \n");
                credits = Integer.parseInt(sc1.nextLine());

                while (true) {
                    System.out.println("\nEnter pre-requisites (Enter NIL if none). Once done, enter q \n");
                    pre_req = sc.nextLine();
                    if (pre_req.equalsIgnoreCase("q")) {
                        break;
                    } else {
                        String query = "insert into course_catalog (course_code,L,T,P,Credits,pre_req) values ('"
                                + course_code + "'," + l + "," + t + "," + p + "," + credits + ",'" + pre_req + "');";
                        System.out.println(query);
                        Statement st = con.createStatement();
                        int x = st.executeUpdate(query);
                    }
                }

            } else if (option == 3) {

            } else {
                return;
            }
        }

    }

    public static void grade() {

    }

    public static void transcript() {

    }

    public static void catalog_opt1() {

    }
}
