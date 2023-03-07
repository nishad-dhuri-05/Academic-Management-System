package softwareeng;

import java.sql.*;
import java.util.ResourceBundle;

public class Dao implements DaoI {

    static Connection con;

    public Dao() {

        try {
            ResourceBundle rd = ResourceBundle.getBundle("config");
            String url = rd.getString("url"); // localhost:5432
            String username = rd.getString("username");
            String password = rd.getString("password");

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
        }

    }

    @Override
    public int updatequery(String query) {

        Statement st;
        int x = 0;
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            x = st.executeUpdate(query);

        } catch (Exception e) {
        }
        return x;

    }

    @Override
    public ResultSet readquery(String query) {

        Statement st;
        ResultSet rs = null;

        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(query);
        } catch (Exception e) {

        }
        return rs;
    }

}
