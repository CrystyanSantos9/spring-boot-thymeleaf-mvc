package dev.cryss.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan(basePackages="dev.cryss.model")
//ComponentScan(basePackages={"dev.cryss.*"})
//@EnableJpaRepositories(basePackages={"dev.cryss.springboot.repository"})
//@EnableTransactionManagement
public class SpringbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
