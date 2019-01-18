package com.saaolheart.mumbai.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	 @Value("${security.signing-key}")
	   private String signingKey;
	 
	 @Autowired
	    private ResourceServerTokenServices tokenServices;
	 

	    @Value("${security.jwt.resource-ids}")
	    private String resourceIds;
     
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(resourceIds).tokenServices(tokenServices);
    }
	    
	    /*@Override
	    public void configure(ResourceServerSecurityConfigurer config) {
	        config.tokenServices(tokenServices());
	    }*/
   /* @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
       JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
       converter.setSigningKey(signingKey);
       return converter;
    }

    @Bean
    public TokenStore tokenStore() {
       return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary //Making this primary to avoid any accidental duplication with another token service instance of the same name
    public DefaultTokenServices tokenServices() {
       DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
       defaultTokenServices.setTokenStore(tokenStore());
       defaultTokenServices.setSupportRefreshToken(true);
       defaultTokenServices.setReuseRefreshToken(true);
       return defaultTokenServices;
    }*/
    @Override
    public void configure(HttpSecurity http) throws Exception {
                http
                .requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**", "/api-docs/**","/user/signup/**").permitAll()
                .antMatchers("/customer/**" ).authenticated();
    }
}
