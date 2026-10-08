package data;

import java.sql.SQLException;

public class DataAccessException extends Exception {
    private static final long serialVersionUID = 1L;

    public DataAccessException(String what, Exception e) {
        super(what, e);
    }

    public DataAccessException(String what) {
        super(what);
    }

	public DataAccessException(SQLException e, String string) {
		// TODO Auto-generated constructor stub
	}
}