package softwareeng;

import java.sql.*;
import java.util.ResourceBundle;

public class Dao implements DaoI {

    static Connection con;

    public Dao(){

        try {
            ResourceBundle rd = ResourceBundle.getBundle("config");
            String url = rd.getString("url"); // localhost:5432
            String username = rd.getString("username");
            String password = rd.getString("password");

            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, username, password);
            
        } catch (Exception e) {
        }        

    }

    @Override
    public int updatequery(String query) throws Exception {

        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        int x = st.executeUpdate(query);

        return x;
    }

    @Override
    public ResultSet readquery(String query) throws Exception {
        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = st.executeQuery(query);

        return rs;
    }

}
