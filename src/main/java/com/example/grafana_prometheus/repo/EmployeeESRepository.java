package com.example.grafana_prometheus.repo;

import com.example.grafana_prometheus.entity.EmployeeES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EmployeeESRepository extends ElasticsearchRepository<EmployeeES, Long> {

    List<EmployeeES> findByFirstNameContaining(String firstName);
}
