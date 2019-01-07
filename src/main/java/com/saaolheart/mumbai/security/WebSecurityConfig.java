package com.saaolheart.mumbai.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.saaolheart.mumbai.service.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(value=101)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
	
	
	 @Value("${security.signing-key}")
	   private String signingKey;

	 

	   @Value("${security.security-realm}")
	   private String securityRealm;
	/*
	 * @Bean public BCryptPasswordEncoder encoder(){ return new
	 * BCryptPasswordEncoder(encodingStrength); }
	 */
	
	/*
	 * @Autowired private UserService userDetailsService;
	 */
	   
	 @Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.userDetailsService(userDetailsService) .passwordEncoder(new
	 * BCryptPasswordEncoder(encodingStrength)); }
	 */
	 
	 @Override
	   protected void configure(HttpSecurity http) throws Exception {
	      http
	              .sessionManagement()
	              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	              .and()
	              .httpBasic()
	              .realmName(securityRealm)
	              .and()
	              .csrf()
	              .disable();
	    

	   }
	 
	   
	    
	    @Bean
	    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.setAllowedOrigins(Collections.singletonList("*"));
	        config.setAllowedMethods(Collections.singletonList("*"));
	        config.setAllowedHeaders(Collections.singletonList("*"));
	        source.registerCorsConfiguration("/**", config);
	        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
	        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	        return bean;
	    }
	
}
