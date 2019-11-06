/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.controllers;

import com.samsara.samsara.Securityimp.UserPrincipal;
import com.samsara.samsara.Securityimp.UserPrincipalService;
import com.samsara.samsara.entities.User;
import com.samsara.samsara.services.UserService;
import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author eldee
 */
@Controller
@RequestMapping("/sign")
public class signup {

    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userserv;

    /**
     *
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public String signup(@ModelAttribute User user, @RequestParam String repassword, HttpServletRequest request) {
        new File(Paths.get("src\\main\\resources\\static\\images\\" + user.getUserName()).toAbsolutePath().toString()).mkdir();
        if (validSignup(user.getUserName(), user.getPassword(), repassword, user.getEmail())) {
            user.setPassword(passwordEncoder.encode(repassword));
            System.out.println("pass hash is " + user.getPassword());
            user.setActive(1);

            userserv.addUser(user);
            authenticateUserAndSetSession(user.getUserName(), repassword,request);

            System.out.println("gat true");

            System.out.println(user.getActive());
        }
        return "redirect:/";
    }

    private void authenticateUserAndSetSession(String username, String password, HttpServletRequest request) {
        System.out.println("ksom el password aho" + password);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        request.getSession().setAttribute("user",username);
    }

    @RequestMapping(value = "/validsignup", method = RequestMethod.POST)
    @ResponseBody
    public boolean validSignup(@RequestParam String username, @RequestParam String password, @RequestParam String repassword, @RequestParam String email) {
        System.out.println("elvalidation:" + username + ":::" + password + "::" + repassword);
        if (userserv.findUserByUserName(username) != null || !password.equals(repassword) || password.isEmpty() || !isValid(email)) {
            System.out.println("after valid:" + password.equals(repassword) + "::" + userserv.findUserByUserName(username));
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/validlogin", method = RequestMethod.POST)
    @ResponseBody
    public boolean validlogin(@RequestParam String username, @RequestParam String password) {
        User user = userserv.findUserByUserName(username);
        System.out.println(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(HttpSession session) {
        session.getAttributeNames().asIterator().forEachRemaining(x -> System.out.println(x));
        System.out.println(session.getAttribute("SPRING_SECURITY_CONTEXT"));
        SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        System.out.println(context.getAuthentication());
        System.out.println(context.getAuthentication().getName());
        return "hello";
    }

    public boolean isValid(String email) {
        return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
    }

}
