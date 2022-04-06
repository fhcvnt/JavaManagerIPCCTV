package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnectSQL {
	private Connection connection;
	private Statement statement;
	// private String db_url =
	// "jdbc:sqlserver://192.168.30.123;databaseName=IPManagerCCTV;user=sa;password=Killua21608";
	private String db_url = "jdbc:sqlserver://192.168.30.7;databaseName=IPManagerCCTV;user=camera;password=ManagerIPcctv!20201016";

	public ConnectSQL() {
		connection = null;
		statement = null;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection() {
		if (connection == null) {
			// ket noi SQL
			try {
				connection = DriverManager.getConnection(db_url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getChuoiKetNoi() {
		return db_url;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement() {
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// use for insert, delete, update
	public int execUpdateQuery(String stringQuery) {
		try {
			statement = connection.createStatement();
			int result = statement.executeUpdate(stringQuery);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public ArrayList<String> getArraySeclect(String select) {
		ArrayList<String> arraylist = new ArrayList<>();
		try {
			if (connection != null) {
				statement = connection.createStatement();
				ResultSet result = statement.executeQuery(select);
				while (result.next()) {
					arraylist.add(result.getString(1));
				}
				result.close();
				statement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arraylist;
	}

	public void closeConnection() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	public void closeStatement() throws SQLException {
		if (statement != null) {
			statement.close();
		}
	}
}
