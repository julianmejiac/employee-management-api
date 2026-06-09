package julianmejiac.com.pe.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final DaoEmployees dao;

    public EmployeeController(DaoEmployees dao) {
        this.dao = dao;
    }
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        try{
            List<Employee> employees=dao.findAll();
            return ResponseEntity.ok(employees);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id){
        try{

            Employee employee = dao.findById(id);
            if (employee==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }
            return ResponseEntity.ok(employee);

        }
        catch (SQLException e){
            e.printStackTrace(); // TODO: replace with Logger
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database operation failed");
        }
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getEmployeeByName(@PathVariable String name){
        try{
            List<Employee> employees=dao.findByFirstName(name);
            if (employees==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with that name not found");
            }
            return ResponseEntity.ok(employees);

        }
        catch (SQLException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database operation failed");
        }
    }
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {

            if (employee.getName() == null || employee.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Name is required.");
            }
            if (employee.getLastname()==null || employee.getLastname().trim().isEmpty()){
                return ResponseEntity.badRequest().body("Last Name is required.");
            }
            if (employee.getDob()==null){
                return ResponseEntity.badRequest().body("Date of Birth is required");
            }
            if (employee.getSalary()<=0){
                return ResponseEntity.badRequest().body("Salary has to be positive");
            }
            if (employee.getSalary()>=1000000){
                return ResponseEntity.badRequest().body("Salary is too high");
            }

            Employee createdEmployee = dao.insert(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO: Replace printStackTrace() with proper Logger implementation
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database operation failed");
        }
    }
    @PutMapping("/{id}/salary")
    public ResponseEntity<?> updateSalary(@PathVariable int id, @RequestBody Map<String,Float> body){
        try{

            Employee employee=dao.findById(id);
            if (employee==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found");
            }
            Float newSalary=body.get("salary");
            if (newSalary==null){
                return ResponseEntity.badRequest().body("Need to introduce new salary");
            }
            if (newSalary<=0){
                return ResponseEntity.badRequest().body("New salary has to be positive");
            }
            dao.editSalary(employee,newSalary);
            return ResponseEntity.ok("Salary updated successfully.");
        }catch(SQLException e){
            e.printStackTrace();//TODO replace using Logger
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database operation failed");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id){
        try{

            boolean deleted=dao.deleteById(id);
            if(!deleted){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee id not found");
            }
            return ResponseEntity.ok("Employee deleted succesfully");
        }catch(SQLException e){
            e.printStackTrace();//TODO replace with Looger
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database operation failed");

        }
    }
}
