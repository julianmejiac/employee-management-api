package julianmejiac.com.pe.api;
 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
 
public class EjemploSelect {
 
	public static void main(String[] args) {
		
		Connection con = null;
		
		try {
			
			// obtenemos la conexión
			con = DBConnection.getConnection();
			
			// Creamos el objeto para enviar sentencias
			
			Statement st = con.createStatement();
			
			// Enviamos una sentencia para rescatar todos los datos de una tabla
			
			ResultSet rs = st.executeQuery("SELECT * FROM empleado");
			
	while(rs.next()) {
		String nombre = rs.getString("nombre"); // equivalente a rs.getString(1);
		String apellido = rs.getString("apellido"); // equivalente a rs.getString(2);
		java.sql.Date fechaNacimiento = rs.getDate("fechaNacimiento");//equivalente a rs.getDate(3);
		LocalDate fechaNacimientoLD= fechaNacimiento.toLocalDate();
		float sueldo = rs.getFloat("sueldo"); //equivalente a rs.getFloat(4);
	    System.out.printf("%s %s \t\t (%s) - %.2f€  %n",nombre, apellido,
	    fechaNacimientoLD.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),sueldo);
	    
		//€  Alt + 0128
	    
			}
			// Cerramos Resulset y Statement
	       rs.close();
	       st.close();
 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con != null)
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
				
			}
			
		}
 
	}
 
}
 