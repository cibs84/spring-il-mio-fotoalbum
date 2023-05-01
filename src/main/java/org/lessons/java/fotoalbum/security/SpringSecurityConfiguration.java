package org.lessons.java.fotoalbum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		/*
		CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
		XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
		// set the name of the attribute the CsrfToken will be populated on
		delegate.setCsrfRequestAttributeName("_csrf");
		// Use only the handle() method of XorCsrfTokenRequestAttributeHandler and the
		// default implementation of resolveCsrfTokenValue() from CsrfTokenRequestHandler
		CsrfTokenRequestHandler requestHandler = delegate::handle;
		http
			// ...
		
			.csrf((csrf) -> csrf
				.csrfTokenRepository(tokenRepository)
				.csrfTokenRequestHandler(requestHandler)
			);

		return http.build();
		*/
		
		XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
		CsrfTokenRequestHandler requestHandler = delegate::handle;
		http.cors().and().authorizeHttpRequests()	
			.requestMatchers("/**").permitAll()
			.and()
		    .csrf()
		    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		    .csrfTokenRequestHandler(requestHandler)
		    /*
		    .ignoringRequestMatchers(request ->
		    	request.getRequestURI().startsWith("/api") && // use .equals instead of .startsWith to indicate a specific URI
		    	request.getMethod().equals("POST"))
		    .ignoringRequestMatchers(request ->
				request.getRequestURI().startsWith("/api") &&
				request.getMethod().equals("DELETE"))
			*/
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