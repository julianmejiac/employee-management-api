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
				"INSERT INTO employees (first_name,last_name, date_of_birth,salary)"
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
	//method that retrieves all employees
	public List<Employee> findAll() throws SQLException {
		String sql="SELECT * FROM employees";
		try(
		Connection con = dbConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {

				List<Employee> result = new ArrayList<>();

				while (rs.next()) {
					result.add(new Employee(
							rs.getInt("id"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getDate("date_of_birth").toLocalDate(),
							rs.getFloat("salary")));
				}
				return result;
			}
		}


	}

	//method that retrieves an employee by ID
	public Employee findById(int id) throws SQLException {

		String sql = "SELECT * FROM employees WHERE id=?";

		try (
				Connection con = dbConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)
		) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					return new Employee(
							rs.getInt("id"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getDate("date_of_birth").toLocalDate(),
							rs.getFloat("salary")
					);
				}

				return null;
			}
		}
	}
	//method to find employees by name
	public List<Employee> findByFirstName(String name) throws SQLException{
		String sql="SELECT * FROM employees WHERE first_name=?";
		try(
				Connection con= dbConnection.getConnection();
				PreparedStatement ps=con.prepareStatement(sql)
		){
			ps.setString(1, name);
			try(ResultSet rs=ps.executeQuery()){
				List<Employee> result=new ArrayList<>();
				while (rs.next()){
					result.add(new Employee(
							rs.getInt("id"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getDate("date_of_birth").toLocalDate(),
							rs.getFloat("salary")

					));


				}
				return 	result;

			}

		}
	}


	// method that updates an employee's salary
	public void editSalary(Employee e, Float newSalary) throws SQLException {

		String sql = "UPDATE employees SET salary = ? WHERE id = ?";

		try (
				Connection con = dbConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)
		) {
			ps.setFloat(1, newSalary);
			ps.setInt(2, e.getId());

			ps.executeUpdate();
		}
	}
	// delete by id
	public boolean deleteById(int id) throws SQLException {

		String sql = "DELETE FROM employees WHERE id = ?";

		try (
				Connection con = dbConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)
		) {
			ps.setInt(1, id);

			int affectedRows = ps.executeUpdate();

			return affectedRows > 0;
		}
	}
    
    // delete all employees
    public int deleteAll() throws SQLException {
 
        String sql = "DELETE FROM employees";
		Connection con = dbConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
 
            return ps.executeUpdate();
        }
	
	
		
	
}
}
