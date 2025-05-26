package com.module.Gateway_Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/milk-plant/milk-collection/**")
						.filters( f -> f.rewritePath("/milk-plant/milk-collection/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time-LDT", LocalDateTime.now().toString()))
						.uri("lb://MILK-COLLECTION"))
				.route(p -> p
						.path("/milk-plant/persons/**")
						.filters( f -> f.rewritePath("/milk-plant/persons/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time-LDT", LocalDateTime.now().toString()))
						.uri("lb://PERSONS"))
				.route(p -> p
						.path("/milk-plant/financial-management/**")
						.filters( f -> f.rewritePath("/milk-plant/financial-management/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time-LDT", LocalDateTime.now().toString()))
						.uri("lb://FINANCIAL-MANAGEMENT")).build();
	}

}
