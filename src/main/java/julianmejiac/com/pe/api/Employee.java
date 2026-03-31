package julianmejiac.com.pe.api;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {
	private Integer id;
	private String name;
	private String lastname;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;
	private float salary;
	public Employee(){

	}
	public Employee(Integer id, String name, String lastname, LocalDate dob, float salary) {
		super();
		this.id=id;
		this.name = name;
		this.lastname = lastname;
		this.dob = dob;
		this.salary = salary;
	}
	@Override
	public int hashCode() {
		return Objects.hash(lastname, dob, id, name, salary);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(lastname, other.lastname) && Objects.equals(dob, other.dob)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Float.floatToIntBits(salary) == Float.floatToIntBits(other.salary);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "ID: " + id
        + " | Nombre: " + name
        + " | Apellido: " + lastname
        + " | Nacimiento: " + dob
        + " | Sueldo: S/ " + salary;
	}
	
	
	
	

}
