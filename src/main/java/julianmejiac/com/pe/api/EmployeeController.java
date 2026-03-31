package julianmejiac.com.pe.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        try{
            DaoEmployees dao =DaoEmployees.getInstance();
            List<Employee> employees=dao.findAll();
            return ResponseEntity.ok(employees);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id){
        try{
            DaoEmployees dao =DaoEmployees.getInstance();
            Employee employee = dao.findById(id);
            if (employee==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(employee);

        }
        catch (SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee){
        try{
            DaoEmployees dao=DaoEmployees.getInstance();
            dao.insert(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee created succesfully.");

        } catch (SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating employee");

        }
    }
    @PutMapping("/{id}/salary")
    public ResponseEntity<String> updateSalary (@PathVariable int id,@RequestBody Map<String,Float> body){
        try{
            DaoEmployees dao=DaoEmployees.getInstance();
            Employee employee=dao.findById(id);
            if (employee==null){
                return ResponseEntity.notFound().build();
            }
            Float newSalary=body.get("salary");
            dao.editSalary(employee,newSalary);
            return ResponseEntity.ok("Salary updated succesfully.");
        }catch(SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating salary");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id){
        try{
            DaoEmployees dao= DaoEmployees.getInstance();
            boolean deleted=dao.deleteById(id);
            if(!deleted){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok("Employee deleted succesfully");
        }catch(SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting employee");

        }
    }
}
