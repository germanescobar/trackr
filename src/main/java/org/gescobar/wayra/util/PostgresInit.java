package org.gescobar.wayra.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * Helper class to initialize the PostgreSQL database.
 * 
 * @author German Escobar
 */
public class PostgresInit {
	
	private DataSource dataSource;
	
	public void init() throws SQLException {
		
		checkCreateTable(dataSource, "trackr", "usser", getUserCreateScript());
		checkCreateTable(dataSource, "trackr", "user_service", getServiceCreateScript());
		
	}
	
	private String getUserCreateScript() {
		String ret = "CREATE TABLE usser ("
			+ "id bigint PRIMARY KEY, "
			+ "name varchar(200) NOT NULL, " 
			+ "creation_time date NOT NULL); ";
		
		return ret;
	}
	
	private String getServiceCreateScript() {
		String ret = "CREATE TABLE user_service ("
			+ "id SERIAL PRIMARY KEY,"
			+ "user_id bigint NOT NULL," 
			+ "name varchar(40) NOT NULL,"
			+ "data varchar(100),"
			+ "creation_time date NOT NULL);";
		
		return ret;
	}

	/**
	 * Checks if the table exists. If it doesn't, it creates it using the
	 * supplied script.
	 * 
	 * @param dataSource the DataSource used to create the connection.
	 * @param schema usually the database name, but can be null.
	 * @param tableName the name of the table to create.
	 * @param creationScript the script to create the exception
	 * @throws SQLException if something goes wrong.
	 */
	public void checkCreateTable(DataSource dataSource, String schema, 
			String tableName, String creationScript) throws SQLException {
		
		Connection connection = null;
		ResultSet rs = null;
		Statement statement = null;
		
		try {
			// create the connection and the statement
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			// check if the outbound message table exists or create it
			DatabaseMetaData metadata = connection.getMetaData();
			rs = metadata.getTables(null, null, tableName, null);
			if (!rs.next() && creationScript != null) {
				statement.executeUpdate(creationScript);
			}

		} finally {
			if (rs != null) {
				try { rs.close(); } catch (Exception e) { }
			}
			if (statement != null) {
				try { statement.close(); } catch(Exception e) { }
			}
			if (connection != null) {
				try { connection.close(); } catch(Exception e) { }
			}
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
}
