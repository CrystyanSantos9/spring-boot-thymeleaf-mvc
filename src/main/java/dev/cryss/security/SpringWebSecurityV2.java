package dev.cryss.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SpringWebSecurityV2 {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		 		.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/remover/{\\+d}").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/**").permitAll()
				.antMatchers(HttpMethod.POST, "/salvarpessoa").hasAnyRole("USER", "ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin().permitAll()
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/materialize/**");
	}

}
