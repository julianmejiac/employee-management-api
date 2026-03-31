package julianmejiac.com.pe.api;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	private static Connection instance=null;
	
	private DBConnection() {
		
	}
	public static Connection getConnection() throws SQLException{
		if (instance==null|| instance.isClosed()) {
			Properties props=new Properties();
			try(InputStream input=DBConnection.class.getClassLoader().getResourceAsStream("application.properties")){
				if(input==null){
					throw new SQLException("application.properties file not found");
				}
				props.load(input);
				String url=props.getProperty("db.url");
				String username=props.getProperty("db.username");
				String password=props.getProperty("db.password");
				instance=DriverManager.getConnection(url,username,password);
			} catch(Exception e){
				throw new SQLException("Error loading database configuration.",e);
			}
			
		}
		return instance;
	}
}
