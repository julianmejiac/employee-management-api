/**
 * TODO:
 * Refactor database configuration to support Docker and multiple environments.
 * Current implementation relies on a hardcoded database URL.
 */
package julianmejiac.com.pe.api;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
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
