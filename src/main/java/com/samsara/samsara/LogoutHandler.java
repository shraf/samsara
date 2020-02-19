/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 *
 * @author Sharaf
 */
public class LogoutHandler implements LogoutSuccessHandler{

    @Override
    public void onLogoutSuccess(HttpServletRequest hsr, HttpServletResponse hsr1, Authentication a) throws IOException, ServletException {
        System.out.println("logout filter");
        for(Cookie cookie:hsr.getCookies())
            if(cookie.getName().equals("logged")){
                cookie.setMaxAge(0);
                cookie.setValue(null);
                hsr1.addCookie(cookie);
                
            }
        hsr1.sendRedirect("/");
    }
    
}
