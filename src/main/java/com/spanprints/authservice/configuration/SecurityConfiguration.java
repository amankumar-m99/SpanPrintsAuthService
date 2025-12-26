package com.spanprints.authservice.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers("/login").permitAll()
				.requestMatchers("/register").permitAll()
				.requestMatchers("/verify").permitAll()
				.requestMatchers("/role/**").permitAll()
				.requestMatchers("/roles").permitAll()
				.requestMatchers(HttpMethod.GET, "/profile-pic/**").permitAll()
				.requestMatchers("/admin-context").hasRole("ADMIN").anyRequest()
				.authenticated() // /person/** is
								// authenticated
//				.anyRequest().authenticated() // /person/** is authenticated
		).httpBasic(Customizer.withDefaults())
				.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(
						e -> e.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(accessDeniedHandler))
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).csrf(csrf -> csrf.disable())
				.cors(Customizer.withDefaults())
				.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()) // Allow frames from same origin
				);
//		.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
		return http.build();
	}

	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200",  "http://192.168.1.5:4200", "http://localhost:5500",  "http://192.168.1.5:5500", "http://127.0.0.1:5500"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
