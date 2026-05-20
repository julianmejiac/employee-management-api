package julianmejiac.com.pe.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class EjemploSelect implements CommandLineRunner {

	private final DBConnection dbConnection;

	public EjemploSelect(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	@Override
	public void run(String... args) throws Exception {

		try (
				Connection con = dbConnection.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM empleado")
		) {

			while (rs.next()) {
				System.out.println(
						rs.getString("nombre")
				);
			}

		}
	}
}