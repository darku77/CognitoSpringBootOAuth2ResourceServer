package com.darku.security.poc;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * @author Catalin on 11.03.2022
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * A practical example on how to return the roles in an attribute then remap them.
     */
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // roles are kept in this claim
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("cognito:groups");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//
                .antMatchers("/protected/**").authenticated() //
                .antMatchers("/protected-by-super-role/**").hasRole("SUPER_ADMIN") //
                .anyRequest().permitAll() //
                .and() //
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter()); //
    }

}
