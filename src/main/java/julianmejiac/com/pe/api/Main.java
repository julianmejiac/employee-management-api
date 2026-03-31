package julianmejiac.com.pe.api;
 
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
 
public class Main {
 
    public static void main(String[] args) {
 
        try (Scanner sc = new Scanner(System.in)) {
 
            DaoEmployees dao = DaoEmployees.getInstance();
            int option;
 
            do {
                System.out.println("\n===== MENU EMPLOYEES=====");
                System.out.println("1) Insert Employee");
                System.out.println("2) List Employees");
                System.out.println("3) Find Employee by ID");
                System.out.println("4) Update Salary of Employee");
                System.out.println("5) Delete Employee by ID");
                System.out.println("6) Delete All Employees");
                System.out.println("0) Exit");
                System.out.print("Choose an option: ");
 
                option = sc.nextInt();
                sc.nextLine();
 
                switch (option) {
                    case 1 -> insertEmployee(sc, dao);
                    case 2 -> listEmployees(dao);
                    case 3 -> findById(sc, dao);
                    case 4 -> updateEmployee(sc, dao);
                    case 5 -> eliminarPorId(sc, dao);
                    case 6 -> eliminarTodos(sc, dao);
                    case 0 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid Option.");
                }
 
            } while (option != 0);
 
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void findById(Scanner sc, DaoEmployees dao) throws SQLException{
    	System.out.println("\n---Insert ID---");
    	int id=sc.nextInt();
    	Employee emp=dao.findById(id);
    	if(emp != null) {
            System.out.println(emp);
        } 
    	else {
            System.out.println("Employee with ID: " + id+ " NOT FOUND");
        }
    
    }
    
    
    private static void updateEmployee(Scanner sc, DaoEmployees dao) throws SQLException{
    	System.out.println("\n---Insert ID that needs to be updated---");
    	int id=sc.nextInt();
    	Employee emp=dao.findById(id);
    	if (emp != null) {
    		System.out.println("\n---Insert new Salary---");
        	float nuevoSueldo=sc.nextFloat();
        	dao.editSalary(emp,nuevoSueldo);
        	System.out.println("Salary succesfully updated");
    	}
    	else{
    		System.out.println("Employee with ID: " + id+ " NOT FOUND");
    	}
    
    
    }
    
 
    private static void insertEmployee(Scanner sc, DaoEmployees dao) throws SQLException {
        System.out.println("\n--- Insert Employee ---");
 
        System.out.print("Name: ");
        String name = sc.nextLine();
 
        System.out.print("Last Name: ");
        String lastname = sc.nextLine();
 
        System.out.print("Date of Birth(YYYY-MM-DD): ");
        String dateStr = sc.nextLine();
        LocalDate dob = LocalDate.parse(dateStr);
 
        System.out.print("Salary: ");
        while (!sc.hasNextFloat()) {
            System.out.print("Insert valid Salary(ej: 1500.50): ");
            sc.next();
        }
        float salary = sc.nextFloat();
        sc.nextLine();
 
        Employee e = new Employee(0, name, lastname, dob, salary);
 
        dao.insert(e);
        System.out.println("Employee Inserted successfully.");
    }
 
    private static void listEmployees(DaoEmployees dao) throws SQLException {
        System.out.println("\n--- Employee List ---");
 
        List<Employee> employees = dao.findAll();
 
        if (employees.isEmpty()) {
            System.out.println("No employees registered.");
            return;
        }
 
        for (Employee e : employees) {
            System.out.println(e);
        }
    }
 
    private static void eliminarPorId(Scanner sc, DaoEmployees dao) throws SQLException {
        System.out.println("\n--- Delete employee by  ID ---");
 
        System.out.print("Input ID: ");
        
        int id = sc.nextInt();
        sc.nextLine();
 
        boolean eliminated = dao.deleteById(id);
 
        if (eliminated) {
            System.out.println("Employee eliminated.");
        } else {
            System.out.println("There is no Employee with that ID.");
        }
    }
 
    private static void eliminarTodos(Scanner sc, DaoEmployees dao) throws SQLException {
        System.out.println("\n--- Delete ALL Employees---");
 
        int total = dao.deleteAll();
        System.out.println("Eliminated " + total + " employees.");
    }
}