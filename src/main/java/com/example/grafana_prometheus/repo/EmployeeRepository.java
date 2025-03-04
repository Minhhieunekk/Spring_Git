package com.example.grafana_prometheus.repo;


import com.example.grafana_prometheus.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByLastName(String lastName);

    Employee findByEmail(String email);
}