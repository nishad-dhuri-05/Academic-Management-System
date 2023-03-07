package softwareeng;

import java.sql.ResultSet;

public interface DaoI {
    public int updatequery(String query) throws Exception;
    public ResultSet readquery(String query) throws Exception;
}