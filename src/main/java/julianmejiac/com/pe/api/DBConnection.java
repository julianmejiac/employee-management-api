
package julianmejiac.com.pe.api;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.sql.Connection;

import java.sql.SQLException;

@Component

public class DBConnection {
	private final DataSource datasource;

	public DBConnection(DataSource datasource) {
		this.datasource = datasource;
	}
	public Connection getConnection() throws SQLException{
		return datasource.getConnection();

	}
}
