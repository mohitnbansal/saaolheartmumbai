package com.saaolheart.mumbai.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.saaolheart.mumbai.service.service.UserService;





@Configuration
@EnableAuthorizationServer
//@Order(value=100)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{ 
	
	/*
	 * 
	 * 
	 * 
	 * @Value("${security.jwt.client-id}") private String clientId;
	 * 
	 * @Value("${security.jwt.client-secret}") private String clientSecret;
	 * 
	 * @Value("${security.jwt.grant-type}") private String grantType;
	 * 
	 * @Value("${security.jwt.grant-types}") private String grantTypes;
	 * 
	 * @Value("${security.jwt.scope-read}") private String scopeRead;
	 * 
	 * @Value("${security.jwt.scope-write}") private String scopeWrite = "write";
	 * 
	 * @Value("${security.jwt.resource-ids}") private String resourceIds;
	 */
	
	@Value("${security.signing-key}") private String signingKey;
	@Value("${security.jwt.client-id}")
	   private String clientId;

	   @Value("${security.jwt.client-secret}")
	   private String clientSecret;

	   @Value("${security.jwt.grant-type}")
	   private String grantType;

	   @Value("${security.jwt.scope-read}")
	   private String scopeRead;

	   @Value("${security.jwt.scope-write}")
	   private String scopeWrite = "write";

	   @Value("${security.jwt.resource-ids}")
	   private String resourceIds;

	   @Value("${security.encoding-strength}")
	   private Integer encodingStrength;
	   
	   @Autowired
	   private TokenStore tokenStore;

	   @Autowired
	   private JwtAccessTokenConverter accessTokenConverter; 
	   
	   @Autowired
	    private UserService userDetailsService;
		
		

	    @Autowired
	    @Qualifier("authenticationManagerBean")
	    private AuthenticationManager authenticationManager;
	 
	    @Bean
	    public BCryptPasswordEncoder encoder(){
	        return new BCryptPasswordEncoder(encodingStrength);
	    }
	    
	    @Override
	    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	 
	    	clients
            .inMemory()
            .withClient(clientId)
            .secret(encoder().encode(clientSecret))
            .authorizedGrantTypes(grantType)
           // .authorities("ROLE_USER")
            .scopes(scopeRead, scopeWrite).accessTokenValiditySeconds(500)
            .resourceIds(resourceIds).refreshTokenValiditySeconds(Integer.MAX_VALUE);
	        
	    }
	 
	    @Override
	    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	       TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
	       enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(),accessTokenConverter));
	       endpoints.tokenStore(tokenStore())
	               .accessTokenConverter(accessTokenConverter())
	               .tokenEnhancer(enhancerChain)
	               .authenticationManager(authenticationManager).userDetailsService(userDetailsService);
	    }
	    @Bean
	    public TokenEnhancer tokenEnhancer() {
	        return new CustomTokenEnhancer();
	    }
	    @Bean
	    public TokenStore tokenStore() {
	        return new JwtTokenStore(accessTokenConverter());
	    }
	 
	    @Bean
	    public JwtAccessTokenConverter accessTokenConverter() {
	        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	        converter.setSigningKey(signingKey);
	        return converter;
	    }
	 
	    @Bean
	    @Primary
	    public DefaultTokenServices tokenServices() {
	        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	        defaultTokenServices.setTokenStore(tokenStore());
	        defaultTokenServices.setSupportRefreshToken(true);
	        
	        return defaultTokenServices;
	    }
}
