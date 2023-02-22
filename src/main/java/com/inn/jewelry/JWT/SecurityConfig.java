package com.inn.jewelry.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

/**

 This class configures Spring Security for the application, including authentication and authorization settings.
 */


/**

 The @Configuration annotation marks the class as a configuration class
 that provides Spring with information on how to configure the application context.

 The @EnableWebSecurity annotation enables Spring Security's web security configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtFilter jwtFilter;

    /**
     The configure() method configures the authentication manager to use the CustomerUserDetailsService,
     sets up HTTP security, and adds the JwtFilter to the filter chain.

     Configures the authentication manager to use the CustomerUserDetailsService.
     @param auth the authentication manager builder
     @throws Exception if an error occurs while configuring the authentication manager
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailsService);
    }

    /**
     The @Bean annotation marks a method that returns an object that should be managed by the Spring container.
     In this case, it defines a password encoder bean and an authentication manager bean.

     The passwordEncoder() method defines a password encoder bean that uses no encoding.

     Defines a password encoder bean that uses no encoding.
     @return a password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     The authenticationManagerBean() method configures the authentication manager bean.

     Configures the authentication manager bean.
     @return an authentication manager bean
     @throws Exception if an error occurs while configuring the authentication manager bean
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**

     Configures HTTP security for the application.
     @param http the HTTP security object
     @throws Exception if an error occurs while configuring HTTP security
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/login","/user/signup","/user/forgotPassword")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
