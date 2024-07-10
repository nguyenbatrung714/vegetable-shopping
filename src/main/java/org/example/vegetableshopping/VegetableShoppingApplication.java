package org.example.vegetableshopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VegetableShoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(VegetableShoppingApplication.class, args);
	}

}
