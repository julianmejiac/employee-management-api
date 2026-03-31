package julianmejiac.com.pe.api;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoEmployees {
	//properties and methods
	private Connection con =null;
	private static DaoEmployees instance=null;
	private DaoEmployees()throws SQLException{
		con =DBConnection.getConnection();
		
	}
	public static DaoEmployees getInstance() throws SQLException{
		if (instance==null) {
			instance=new DaoEmployees();
					}
		return instance;
	}
	//own methods from DAO class
	public void insert(Employee e) throws SQLException{
		PreparedStatement ps=con.prepareStatement(
				"INSERT INTO empleado (nombre,apellido, fechaNacimiento,sueldo)"
				+"VALUES(?,?,?,?)");
				ps.setString(1, e.getName());
				ps.setString(2, e.getLastname());
				ps.setDate(3, Date.valueOf(e.getDob()));
				ps.setFloat(4, e.getSalary());
				ps.executeUpdate();
				ps.close();
							
				
	}
	//metodo que trae todos los empleados
	public List<Employee> findAll() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM empleado");
		ResultSet rs = ps.executeQuery();

		List<Employee> result = new ArrayList<>();

		while (rs.next()) {
			result.add(new Employee(
					rs.getInt("id"),
					rs.getString("nombre"),
					rs.getString("apellido"),
					rs.getDate("fechaNacimiento").toLocalDate(),
					rs.getFloat("sueldo")));
		}

		rs.close();
		ps.close();

		return result;
	}

	//imethod that gives an employee by ID
	public Employee findById(int id) throws SQLException{
		PreparedStatement ps=con.prepareStatement("SELECT * FROM empleado WHERE id=?");
		ps.setInt(1, id);
		ResultSet rs=ps.executeQuery();
		Employee emp = null;

	    if (rs.next()) {
	    	emp=new Employee(rs.getInt("id"),rs.getString("nombre"), rs.getString("apellido"),rs.getDate("fechaNacimiento").toLocalDate(),rs.getFloat("sueldo"));
		}
	    

	    rs.close();
	    ps.close();

	    return emp;
	}
	
	// method that updates and employee
	public void editSalary(Employee e, Float newSalary) throws SQLException{
		PreparedStatement ps=con.prepareStatement("UPDATE empleado SET sueldo = ? WHERE id = ?");
				ps.setFloat(1, newSalary);
				ps.setInt(2, e.getId());
				ps.executeUpdate();
				ps.close();
									
	}
	// delete by id
    public boolean deleteById(int id) throws SQLException {
 
        String sql = "DELETE FROM empleado WHERE id = ?";
 
        try (PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, id);
 
            int filasAfectadas = ps.executeUpdate();
 
            return filasAfectadas > 0;
        }
    }
    
    // delete all employees
    public int deleteAll() throws SQLException {
 
        String sql = "DELETE FROM empleado";
 
        try (PreparedStatement ps = con.prepareStatement(sql)) {
 
            return ps.executeUpdate();
        }
	
	
		
	
}
}
