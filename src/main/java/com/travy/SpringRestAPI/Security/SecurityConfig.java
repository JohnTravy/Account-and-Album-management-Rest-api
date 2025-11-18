package com.travy.SpringRestAPI.Security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private RSAKey rsaKey;

    public static final String[] WHITE_LIST = {

            "/**",
            "/api/v1/Auth/token",
            "/api/v1/Auth/users/add",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/test/**",
            
           

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .csrf(csrf -> csrf.disable()) // Single clean CSRF config
    .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers("/api/v1/album/albums/add").authenticated()
                        .requestMatchers("/api/v1/Auth/users").hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN")
                        .requestMatchers("/api/v1/Auth/users/{user_id}/update_auth").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers("/api/v1/Auth/profile").hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN")
                        .requestMatchers("/api/v1/profile/delete_profile").authenticated()
                        .requestMatchers("/api/v1/Auth/profile/update_password").hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN")
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(Session -> Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }

    //@Bean
    //public InMemoryUserDetailsManager users() {

       // return new InMemoryUserDetailsManager(
        //        User.withUsername("Tomiwa")
        //                .password("{noop}password")
         //               .authorities("read")
         //               .build());

   // }

   @Bean
   public static PasswordEncoder passwordEncoder(){

    return new BCryptPasswordEncoder();

   }


    @Bean
    public JWKSource <SecurityContext> jwkSource(){

        rsaKey = Jwks.generaRsaKey();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, SecurityContext) -> jwkSelector.select(jwkSet);

    }

   


    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {

        var authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);

    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource <SecurityContext> Jwks){

        return new NimbusJwtEncoder(Jwks);

    }

    @Bean
    JwtDecoder jwtDecoder()throws JOSEException{

        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();

    }

}
