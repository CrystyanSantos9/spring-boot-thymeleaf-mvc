package dev.cryss.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SpringWebSecurity extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/")
		.permitAll()
		.anyRequest().authenticated()
		.and().formLogin().permitAll()
		.and().logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
		{
		    auth.inMemoryAuthentication()
		    .withUser("admin")
		    .password(passwordEnconder().encode("123456"))
		    .roles("ADMIN");
		}
	
	 @Override
	 public void configure(WebSecurity web) throws Exception {
		 web.ignoring().antMatchers("/materialize/**");
	 }
	 
	 @Bean
	 public PasswordEncoder passwordEnconder() {
		 return new BCryptPasswordEncoder();
	 }
	 
}