package com.example.grafana_prometheus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeES {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "firstName")
    private String firstName;

    @Field(type = FieldType.Text, name = "lastName")
    private String lastName;

    @Field(type = FieldType.Text, name = "email")
    private String email;


    public static EmployeeES converToModel(Employee employee) {
        return new EmployeeES( employee.getId().toString(), employee.getFirstName(), employee.getLastName(), employee.getEmail());
    }
}
