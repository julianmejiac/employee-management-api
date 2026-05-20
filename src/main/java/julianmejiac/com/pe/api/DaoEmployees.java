package julianmejiac.com.pe.api;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class DaoEmployees {
	//Connection
	private final DBConnection dbConnection;
	public DaoEmployees(DBConnection dbConnection){
		this.dbConnection=dbConnection;
	}
	//own methods from DAO class
	public Employee insert(Employee e) throws SQLException{
		Connection con = dbConnection.getConnection();
		PreparedStatement ps=con.prepareStatement(
				"INSERT INTO empleado (nombre,apellido, fechaNacimiento,sueldo)"
				+"VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, e.getName());
				ps.setString(2, e.getLastname());
				ps.setDate(3, Date.valueOf(e.getDob()));
				ps.setFloat(4, e.getSalary());
				ps.executeUpdate();
				ResultSet generatedKeys=ps.getGeneratedKeys();
				if(generatedKeys.next()){
					e.setId(generatedKeys.getInt(1));
				}
				generatedKeys.close();
				ps.close();
				return e;
							
				
	}
	//metodo que trae todos los empleados
	public List<Employee> findAll() throws SQLException {
		Connection con = dbConnection.getConnection();
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
		Connection con = dbConnection.getConnection();
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
		Connection con = dbConnection.getConnection();
		PreparedStatement ps=con.prepareStatement("UPDATE empleado SET sueldo = ? WHERE id = ?");
				ps.setFloat(1, newSalary);
				ps.setInt(2, e.getId());
				ps.executeUpdate();
				ps.close();
									
	}
	// delete by id
    public boolean deleteById(int id) throws SQLException {
 
        String sql = "DELETE FROM empleado WHERE id = ?";
		Connection con = dbConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, id);
 
            int affectedRows= ps.executeUpdate();
 
            return affectedRows > 0;
        }
    }
    
    // delete all employees
    public int deleteAll() throws SQLException {
 
        String sql = "DELETE FROM empleado";
		Connection con = dbConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
 
            return ps.executeUpdate();
        }
	
	
		
	
}
}
