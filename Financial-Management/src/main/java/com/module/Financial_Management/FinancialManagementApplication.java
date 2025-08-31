package com.module.Financial_Management;

import com.module.Financial_Management.model.dtos.FinancialManagementContactsDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableConfigurationProperties(value = {FinancialManagementContactsDto.class})
//@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class FinancialManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialManagementApplication.class, args);
	}

}
