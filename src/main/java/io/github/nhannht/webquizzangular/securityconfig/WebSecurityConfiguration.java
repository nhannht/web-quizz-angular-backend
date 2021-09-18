package io.github.nhannht.webquizzangular.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    UserDetailServiceImpl userDetailsService;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.ACTUATOR).permitAll()
                .anyRequest().authenticated().and().httpBasic();

        http.formLogin()
                .defaultSuccessUrl("/home", true)
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout-success")
                .permitAll();
    }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuggestHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
        auth.inMemoryAuthentication()
                .withUser("user").password(encoder.encode("password")).roles("USER");
    }



    @Override
    public  void configure(WebSecurity webSecurity){
        webSecurity.ignoring()
                .antMatchers("/h2-console/**");
    }

}
