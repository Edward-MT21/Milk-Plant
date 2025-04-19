package com.module.Milk_Collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class MilkCollectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MilkCollectionApplication.class, args);
	}

}
