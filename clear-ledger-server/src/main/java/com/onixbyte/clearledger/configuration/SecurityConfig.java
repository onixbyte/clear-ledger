package com.onixbyte.clearledger.configuration;

import com.onixbyte.clearledger.security.UserAuthenticationFilter;
import com.onixbyte.clearledger.security.UsernamePasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * Configuration class for security settings.
 * <p>
 * This class configures security settings for a Spring application, enabling web security through
 * the {@link EnableWebSecurity} annotation. It defines a security filter chain, password encoder,
 * and authentication manager to secure endpoints, manage user authentication, and integrate CORS
 * support. The configuration ensures a stateless session policy and custom authentication logic.
 *
 * @author zihluwang
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for HTTP requests.
     * <p>
     * This method defines a {@link SecurityFilterChain} to secure the application's HTTP endpoints.
     * It configures CORS, disables CSRF protection, sets a stateless session policy, and specifies
     * authorisation rules. Key configurations include:
     * <ul>
     *     <li>CORS support using the provided {@link CorsConfigurationSource}</li>
     *     <li>Stateless session management</li>
     *     <li>Permitting all requests to "/error/**" and "/auth/**"</li>
     *     <li>Requiring authentication for "/auth/logout" and all other requests</li>
     *     <li>Adding a custom {@link UserAuthenticationFilter} before the default filter</li>
     * </ul>
     *
     * @param httpSecurity the {@link HttpSecurity} object to configure security settings
     * @param corsConfigurationSource the source for CORS configuration
     * @param userAuthenticationFilter the custom filter for user authentication
     * @return a configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   CorsConfigurationSource corsConfigurationSource,
                                                   UserAuthenticationFilter userAuthenticationFilter) throws Exception {
        return httpSecurity
                .cors((customiser) -> customiser
                        .configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((customiser) -> customiser
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((customiser) -> customiser
                        .requestMatchers("/error", "/error/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/auth/logout").authenticated()
                        .anyRequest().authenticated())
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Provides a password encoder for hashing passwords.
     * <p>
     * This method creates a {@link PasswordEncoder} bean using the {@link BCryptPasswordEncoder}
     * implementation. It is used to securely hash passwords for storage and verification within
     * the application's security framework.
     *
     * @return a {@link PasswordEncoder} instance for password hashing
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication manager with a custom provider.
     * <p>
     * This method creates an {@link AuthenticationManager} bean by configuring an
     * {@link AuthenticationManagerBuilder} with a custom
     * {@link UsernamePasswordAuthenticationProvider}. It integrates the provider into the security
     * framework to handle user authentication logic.
     *
     * @param httpSecurity the {@link HttpSecurity} object providing access to shared security objects
     * @param usernamePasswordAuthenticationProvider the custom authentication provider
     * @return a configured {@link AuthenticationManager}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider) throws Exception {
        var authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(usernamePasswordAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }
}