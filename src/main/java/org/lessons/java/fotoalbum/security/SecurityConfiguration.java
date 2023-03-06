package org.lessons.java.fotoalbum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// GENERA IN CONSOLE LA PAROLA HASHATA CHE GLI PASSO 
		System.out.println(passwordEncoder().encode("user"));
		
		http.authorizeHttpRequests()
//			.requestMatchers("/admin/photos/create", "/admin/photos/edit/**").hasAuthority("ADMIN")
//			.requestMatchers(HttpMethod.POST, "/admin/photos/delete/**").hasAuthority("ADMIN")
//			.requestMatchers("/admin/categories", "/admin/categories/**").hasAuthority("ADMIN")
//			.requestMatchers("/admin/photos", "/admin/photos/**").hasAnyAuthority("USER","ADMIN")
			.requestMatchers("/admin/**").hasAnyAuthority("USER","ADMIN")		
			.requestMatchers("/**").permitAll()
			.and()
		    .csrf()
		    .ignoringRequestMatchers(request ->
		    	request.getRequestURI().startsWith("/api") && // use .equals instead of .startsWith to indicate a specific URI
		    	request.getMethod().equals("POST"))
		    .ignoringRequestMatchers(request ->
				request.getRequestURI().startsWith("/api") &&
				request.getMethod().equals("DELETE"))
			.and().formLogin()
			.defaultSuccessUrl("/admin/photos", true) // redirect after success login
	        .and().logout()		//abilita il supporto al form logout (auto generato)
	    	.and().exceptionHandling()
	    	.accessDeniedPage("/access-denied.html"); //pagina personalizzata in caso di accesso negato
		return http.build();	
	}
	
	@Bean
	DatabaseUserDetailsService userDetailsService() {
		return new DatabaseUserDetailsService();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
	    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}  
	  
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	 
	    authProvider.setUserDetailsService(userDetailsService());
	    authProvider.setPasswordEncoder(passwordEncoder());
	 
	    return authProvider;
	}  
}