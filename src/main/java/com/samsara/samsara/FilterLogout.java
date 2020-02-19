/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara;

import com.samsara.samsara.entities.User;
import com.samsara.samsara.repositories.UserRepository;
import com.samsara.samsara.services.UserService;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Sharaf
 */
public class FilterLogout extends AbstractAuthenticationProcessingFilter {

    public FilterLogout(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url, HttpMethod.POST.name()));
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) sr;
        UserService userservice;
        ServletContext servletContext = request.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        userservice = webApplicationContext.getBean(UserService.class);
        System.out.println("log fucking out filter");
        System.out.println("referer is" + request.getHeader("referer"));
        HttpServletResponse response = (HttpServletResponse) sr1;
        if (request.getSession().getAttribute("SPRING_SECURITY_CONTEXT") == null) {
            System.out.println("security is null");
            if (request.getRequestURI().equals("/") || request.getRequestURI().contains("ads")
                    || request.getRequestURI().contains("profile") || request.getRequestURI().contains("ad")) {
                if (request.getCookies() != null) {

                    if ((Arrays.asList(request.getCookies()).stream()
                            .filter(cookie -> cookie.getName().equals("logged"))
                            .findAny().orElse(null) != null)) {
                        System.out.println("cookie is here");
//                 Authentication auth=new UsernamePasswordAuthenticationToken(userservice.findUserByUserName("sharafre"),null);
//                 SecurityContextHolder.getContext().setAuthentication(auth);
                        User user = userservice.findUserByUserName("sharafre");
                        System.out.println(user.getUserName());
                        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

                        request.setAttribute("userurl", request.getRequestURL());
                    }

                    System.out.println("url is fucking" + request.getRequestURI());
                }
            }
        }
        fc.doFilter(sr, sr1);
        System.out.println("after chanining");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest hsr, HttpServletResponse hsr1) throws AuthenticationException, IOException, ServletException {
        return null;
    }

}
