package softwareeng;

import java.sql.ResultSet;

public interface DaoI {
    public int updatequery(String query);

    public ResultSet readquery(String query);
}