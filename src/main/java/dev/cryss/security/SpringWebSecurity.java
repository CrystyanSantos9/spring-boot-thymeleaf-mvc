package dev.cryss.security;

import org.springframework.beans.factory.annotation.Autowired;
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

//@Configuration
public class SpringWebSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http.csrf().disable()
//		.httpBasic() 
//		.and()
//		.authorizeHttpRequests()
//		.anyRequest().authenticated();
		http.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/remover/{\\+d}").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/**").permitAll()
		.antMatchers(HttpMethod.POST, "/salvarpessoa").hasAnyRole("USER", "ADMIN")	
		.anyRequest().authenticated()
		.and().formLogin().permitAll()
		.and().logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
		{
		    auth.userDetailsService(userDetailsService)
		    .passwordEncoder(passwordEnconder());
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
