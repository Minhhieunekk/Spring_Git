package com.example.grafana_prometheus.controller;

import com.example.grafana_prometheus.entity.Employee;
import com.example.grafana_prometheus.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@Tag(name="Employee")
public class EmployeeController {



    private final EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            description = "Get endpoint for employee",
            summary = "This is a summary for employee ",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"

                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"

                    )
            }
    )

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Optional<Employee> existingEmployee = employeeService.findEmployeeById(id);

        if (existingEmployee.isPresent()) {
            employee.setId(id);
            Employee updatedEmployee = employeeService.saveEmployee(employee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.findEmployeeById(id);

        if (employee.isPresent()) {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/lastName/{lastName}")
    public ResponseEntity<List<Employee>> getEmployeesByLastName(@PathVariable String lastName) {
        List<Employee> employees = employeeService.findEmployeesByLastName(lastName);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/search/email/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeService.findEmployeeByEmail(email);
        return employee != null
                ? new ResponseEntity<>(employee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}