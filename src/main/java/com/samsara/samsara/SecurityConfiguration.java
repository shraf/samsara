/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara;

import com.samsara.samsara.Securityimp.UserPrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author eldee
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandlerImpl onsuccess;

    private UserPrincipalService userPrincipalDetailsService;

    public SecurityConfiguration(UserPrincipalService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/styles/**").permitAll()
                .antMatchers("/scripts/**").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("**/register**").permitAll()
                .antMatchers("/validsignup").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/faq").permitAll()
                .antMatchers("whoare").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new FilterLogout("/login",authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .formLogin().loginPage("/sign-in")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .successHandler(onsuccess)
                .permitAll()
                .and()    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessHandler(getLogoutHandler())
                .and()
                .rememberMe().tokenValiditySeconds(100).key("mySecret!").rememberMeParameter("checkRememberMe").userDetailsService(userPrincipalDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/validsignup")
                .antMatchers("/sign/**"); // #3
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
@Bean
public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
}
@Bean
    public LogoutHandler getLogoutHandler(){
        return new LogoutHandler();
    }
    /*  @Bean
    public AuthenticationSuccessHandlerImpl onsuccess(){
        return new AuthenticationSuccessHandlerImpl();
    }*/
}
