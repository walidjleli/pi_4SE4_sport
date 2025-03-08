package tn.esprit.sporty.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())  // ✅ Désactiver CSRF pour éviter les erreurs 403
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // ✅ Appliquer CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ✅ OPTIONS pour éviter blocage CORS
                        .requestMatchers(HttpMethod.POST, "/rest/auth/request-reset").permitAll()
                        .requestMatchers(HttpMethod.POST, "/rest/auth/reset-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/rest/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/rest/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/rest/auth/users").permitAll()    
                        .requestMatchers(HttpMethod.PUT, "/RestTeam/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/RestTeam/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/RestTeam/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/RestTeam/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/RestTeam/{{teamId}}").permitAll()


                        .requestMatchers("/rest/stats/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/rest/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/rest/stat/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/rest/stat/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/rest/stat/**").permitAll()



                        .anyRequest().authenticated() // ✅ Tout le reste nécessite authentification
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        System.out.println("✅ Configuration de sécurité appliquée !");
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // ✅ Autoriser Angular
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // ✅ Méthodes HTTP autorisées
        configuration.setAllowedHeaders(List.of("*")); // ✅ Autoriser tous les headers
        configuration.setAllowCredentials(true); // ✅ Autoriser les cookies et tokens

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
