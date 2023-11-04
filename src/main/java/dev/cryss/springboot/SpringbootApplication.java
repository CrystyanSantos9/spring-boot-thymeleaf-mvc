package dev.cryss.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("dev.cryss.springboot.model")
@ComponentScan("dev.cryss.*")
@ComponentScan("dev.cryss.security.UserDetailsServiceImpl")
@EnableJpaRepositories("dev.cryss.springboot.repository")
@EnableTransactionManagement
//@PropertySources({
//    @PropertySource(value = "classpath:application.properties"),
//    @PropertySource(value = "D:\\java_spring\\springboot\\src\\main\\resources\\linux-application.properties", ignoreResourceNotFound = true)
//})
public class SpringbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
		
		System.out.println(new BCryptPasswordEncoder().encode("senha123"));
	}

}
