package com.example.grafana_prometheus.service;

import com.example.grafana_prometheus.entity.Employee;
import com.example.grafana_prometheus.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> findEmployeesByLastName(String lastName) {
        return employeeRepository.findByLastName(lastName);
    }

    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
}
