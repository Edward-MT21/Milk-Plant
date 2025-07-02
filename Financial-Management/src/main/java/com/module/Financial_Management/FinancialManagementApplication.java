package com.module.Financial_Management;

import com.module.Financial_Management.model.dtos.FinancialManagementContactsDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableConfigurationProperties(value = {FinancialManagementContactsDto.class})
@EnableDiscoveryClient
public class FinancialManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialManagementApplication.class, args);
	}

}
