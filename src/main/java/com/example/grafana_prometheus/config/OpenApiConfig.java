package com.example.grafana_prometheus.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Employee API",
                version = "1.0",
                description = "Documentation Employee API v1.0"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8801",
                        description = "Local dev"
                ),
                @Server(
                        url = "http://localhost:8802",
                        description = "Local test"
                )
        }

)
public class OpenApiConfig {
}
