package dev.cryss.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("dev.cryss.springboot.model")
@ComponentScan("dev.cryss.*")
//@ComponentScan("dev.cryss.security.*")
@EnableJpaRepositories("dev.cryss.springboot.repository")
@EnableTransactionManagement
public class SpringbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
		
		BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
		String result = enconder.encode("123");
		System.out.println(result);
	}

}
