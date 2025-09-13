package com.module.Financial_Management;

import com.module.Financial_Management.model.dtos.FinancialManagementContactsDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @EnableJpaAuditing(auditorAwareRef = "auditorProvider") is used to enable JPA auditing features
 * and auditorProvider is the bean that provides the auditor.
 */
@SpringBootApplication
@Import(com.module.Common.config.JpaAuditingConfig.class)
//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableConfigurationProperties(value = {FinancialManagementContactsDto.class})
//@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class FinancialManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialManagementApplication.class, args);
	}

}
