package dev.cryss.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebConfigSecurity {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		// InMemoryUserDetailsManager (see below)
		
		UserDetails user = 
				User.withUsername("user")
				.password(passwordEncoder().encode("pass123"))
				.roles("USER")
				.build();
				
	        return new InMemoryUserDetailsManager(user);

//		UserDetails user1 = User.withUsername("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
//				.build();
//		UserDetails user2 = User.withUsername("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
//				.build();
//		UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(user1, user2, admin);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// http builder configurations for authorize requests and form login (see below)
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						authorize -> authorize
						.requestMatchers(HttpMethod.GET, "/**") 
						.permitAll()
						.anyRequest()
						.authenticated())
				
				.formLogin(formLogin -> 
				formLogin.loginPage("/login")
				.defaultSuccessUrl("/")
				.permitAll());
		return http.build();

	}
	
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/materialize/**");
    }

}