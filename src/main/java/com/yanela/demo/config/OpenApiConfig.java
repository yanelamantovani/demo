package com.yanela.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "Yanela",
						email = "yanelamantovani@gmail.com"
				),
				title = "OpenApi Specification - Demo Project",
				description = "OpenApi documentation for demo project",
				version = "1.0"
		),
		servers = {
				@Server(
						description = "Local Environment",
						url = "http://localhost:8080"
				)
		}
)
public class OpenApiConfig {

}
