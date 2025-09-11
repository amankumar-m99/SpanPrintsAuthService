package com.spanprints.authservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spanprints.authservice.jwt.JwtAccessDeniedHandler;
import com.spanprints.authservice.jwt.JwtAuthenticationEntryPoint;
import com.spanprints.authservice.jwt.JwtRequestFilter;

@Configuration
public class SecurityConfiguration {

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, JwtRequestFilter filter,
			JwtAuthenticationEntryPoint authEntryPoint, JwtAccessDeniedHandler accessDeniedHandler) throws Exception {
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/login").permitAll().requestMatchers("/register")
				.permitAll().requestMatchers("/role/**").permitAll().requestMatchers("/roles").permitAll()
				.requestMatchers("/admin-context").hasRole("ADMIN").anyRequest().permitAll() // /person/** is
																								// authenticated
//				.anyRequest().authenticated() // /person/** is authenticated
		).httpBasic(Customizer.withDefaults())
				.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(
						e -> e.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(accessDeniedHandler))
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).csrf(csrf -> csrf.disable());
		return http.build();
	}
}
