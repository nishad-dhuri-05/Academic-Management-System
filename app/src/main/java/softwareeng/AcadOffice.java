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
                String query = "SELECT * FROM pre_reqs,course_catalog where course_catalog.course_code=pre_reqs.course_code;";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    String course_code = rs.getString("course_code");
                    String l = rs.getString("l");
                    String t = rs.getString("t");
                    String p = rs.getString("p");
                    String credits = rs.getString("credits");
                    String pre_req = rs.getString("pre_req");

                    System.out.println("  " + course_code + "  " + "|" + l + "-" + t + "-" + p + "-" + credits
                            + "|" + pre_req);

                }
            }

            else if (option == 2) {

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

                String query = "insert into course_catalog (course_code,L,T,P,Credits) values ('"
                        + course_code + "'," + l + "," + t + "," + p + "," + credits + ");";

                Statement st = con.createStatement();
                int x = st.executeUpdate(query);

                while (true) {
                    System.out.println("\nEnter pre-requisites (Enter NIL if none). Once done, enter q \n");
                    pre_req = sc.nextLine();
                    if (pre_req.equalsIgnoreCase("q")) {
                        break;
                    } else {
                        String query_ = "insert into pre_reqs (course_code,pre_req) values ('"
                                + course_code + "','" + pre_req + "');";
                        Statement st_ = con.createStatement();
                        x = st_.executeUpdate(query_);
                    }
                }

            }

            else if (option == 3) {
                String course_code, pre_req;
                Float l, t, p, credits;

                System.out.println("Select Course Code To Update \n");
                Scanner sc1 = new Scanner(System.in);
                course_code = sc1.nextLine();

                String query = "SELECT * FROM pre_reqs,course_catalog where course_catalog.course_code=pre_reqs.course_code and course_catalog.course_code='"
                        + course_code + "' ;";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    course_code = rs.getString("course_code");
                    String display_l = rs.getString("l");
                    String display_t = rs.getString("t");
                    String display_p = rs.getString("p");
                    String display_credits = rs.getString("credits");
                    pre_req = rs.getString("pre_req");

                    System.out.println("  " + course_code + "  " + "|" + display_l + "-" + display_t + "-" + display_p
                            + "-" + display_credits
                            + "|" + pre_req);
                }

                System.out.println("\nEnter Lecture hours \n");
                l = sc1.nextFloat();
                sc1.nextLine();

                System.out.println("\nEnter Tutorial hours \n");
                t = sc1.nextFloat();
                sc1.nextLine();

                System.out.println("\nEnter Practical hours \n");
                p = sc1.nextFloat();
                sc1.nextLine();

                System.out.println("\nEnter Credits \n");
                credits = sc1.nextFloat();
                sc1.nextLine();

                query = "update course_catalog set l=" + l + ", t= " + t + ", p=" + p + ", credits=" + credits
                        + " where course_code='" + course_code + "';";

                System.out.println(query);

                Statement st1 = con.createStatement();
                int x = st1.executeUpdate(query);

                while (true) {
                    int option_pre = 0;
                    System.out.println("\nSelect Option \n");

                    System.out.println("\n1. Add new pre-requisite \n");
                    System.out.println("\n2. Delete pre-requisite \n");
                    System.out.println("\n3. No change \n");

                    option_pre = sc1.nextInt();
                    sc1.nextLine();

                    if (option_pre == 1) {
                        System.out.println("\nEnter pre-requisite to be added ");
                        pre_req = sc1.nextLine();

                        String query_ = "insert into pre_reqs (course_code,pre_req) values ('"
                                + course_code + "','" + pre_req + "');";
                        Statement st_ = con.createStatement();
                        x = st_.executeUpdate(query_);
                    } else if (option_pre == 2) {
                        System.out.println("\nEnter pre-requisite to be deleted ");
                        pre_req = sc1.nextLine();

                        String query_ = "delete from pre_reqs where course_code='" + course_code + "' and pre_req='"
                                + pre_req + "';";
                        Statement st_ = con.createStatement();
                        x = st_.executeUpdate(query_);
                    } else {
                        return;
                    }

                }
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
