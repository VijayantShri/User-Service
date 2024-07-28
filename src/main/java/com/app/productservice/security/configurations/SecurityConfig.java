package com.app.productservice.security.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/products/{id}").hasAuthority("CUSTOMER")
                        .requestMatchers("/products").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .cors().disable()
                .csrf().disable();
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .antMatchers("/public/**").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .oauth2Login()  // for OAuth2 login
//                .and()
//                .oauth2ResourceServer()
//                .jwt();  // for OAuth2 resource server with JWT support

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("ScalerRole");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

}
