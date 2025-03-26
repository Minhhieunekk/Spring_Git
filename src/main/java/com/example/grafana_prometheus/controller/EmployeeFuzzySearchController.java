package com.example.grafana_prometheus.controller;


import com.example.grafana_prometheus.entity.EmployeeES;
import com.example.grafana_prometheus.service.EmployeeFuzzySearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search/fuzzy")
public class EmployeeFuzzySearchController {

    @Autowired
    private EmployeeFuzzySearchService employeeFuzzySearchService;

    @GetMapping("/search/fuzzy")
    public List<EmployeeES> searchFuzzy(@RequestParam String firstName) {
        return employeeFuzzySearchService.fuzzySearchByFirstName(firstName);
    }

    // 🔍 Tìm kiếm nhân viên bằng fuzzy search (chỉ định độ sai lệch)
    @GetMapping("/search/fuzzy/custom")
    public List<EmployeeES> searchFuzzyCustom(@RequestParam String firstName) {
        return employeeFuzzySearchService.fuzzySearchByFirstName(firstName);
    }

    // 🔍 Tìm kiếm theo cụm từ với độ lệch (Match Phrase Query)
    @GetMapping("/search/phrase")
    public List<EmployeeES> searchPhrase(@RequestParam String firstName, @RequestParam int slop) {
        return employeeFuzzySearchService.fuzzyPhraseSearchByFirstName(firstName, slop);
    }
}
