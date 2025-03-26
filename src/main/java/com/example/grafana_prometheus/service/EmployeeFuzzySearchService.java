package com.example.grafana_prometheus.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.util.ObjectBuilder;
import com.example.grafana_prometheus.entity.Employee;
import com.example.grafana_prometheus.entity.EmployeeES;
import com.example.grafana_prometheus.repo.EmployeeESRepository;
import com.example.grafana_prometheus.repo.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeFuzzySearchService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private EmployeeESRepository employeeESRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public void saveAllEmployees(List<Employee> employees) {
        List<EmployeeES> employeesES = employees.stream()
                .map(EmployeeES::converToModel) // Chuyển đổi Employee -> EmployeeES
                .toList();
        employeeESRepository.saveAll(employeesES);
    }

    @PostConstruct
    public void syncDataOnStartup() {
        List<Employee> employees = employeeRepository.findAll(); // Lấy dữ liệu từ database
        saveAllEmployees(employees); // Đẩy lên Elasticsearch
        System.out.println("✅ Dữ liệu đã được đồng bộ vào Elasticsearch!");
    }

    // 🔍 Fuzzy Search với độ sai lệch tự động
    public List<EmployeeES> fuzzySearchByFirstName(String firstName) {
        FuzzyQueryBuilder fuzzyQuery = QueryBuilders.fuzzyQuery("firstName", firstName)
                .fuzziness(Fuzziness.AUTO);

        NativeQuery searchQuery = new NativeQueryBuilder()
                .withQuery((Function<Query.Builder, ObjectBuilder<Query>>) fuzzyQuery)
                .build();

        SearchHits<EmployeeES> searchHits = elasticsearchOperations.search(searchQuery, EmployeeES.class);

        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    // 🔍 Match Phrase Search với độ lệch `slop`
    public List<EmployeeES> fuzzyPhraseSearchByFirstName(String firstName, int slop) {
        MatchPhraseQueryBuilder phraseQuery = QueryBuilders.matchPhraseQuery("firstName", firstName)
                .slop(slop);

        NativeQuery searchQuery = new NativeQueryBuilder()
                .withQuery((Function<Query.Builder, ObjectBuilder<Query>>) phraseQuery)
                .build();

        SearchHits<EmployeeES> searchHits = elasticsearchOperations.search(searchQuery, EmployeeES.class);

        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
