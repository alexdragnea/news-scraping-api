package net.dg.newsscrapingapi.config;

import net.dg.newsscrapingapi.auth.APIAuthKeyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
  @Value("${api.http.auth-token-header-name}")
  private String principalRequestHeader;

  @Value("${api.http.auth-token}")
  private String principalRequestValue;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    APIAuthKeyFilter filter = new APIAuthKeyFilter(principalRequestHeader);
    filter.setAuthenticationManager(
        new AuthenticationManager() {
          @Override
          public Authentication authenticate(Authentication authentication)
              throws AuthenticationException {
            String principal = (String) authentication.getPrincipal();
            if (!principalRequestValue.equals(principal)) {
              throw new BadCredentialsException(
                  "The API key was not found or not the expected value.");
            }
            authentication.setAuthenticated(true);
            return authentication;
          }
        });
    httpSecurity
        .antMatcher("/api/**")
            .csrf()
            .disable()
        .addFilter(filter)
        .authorizeRequests()
        .anyRequest()
        .authenticated();
  }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
