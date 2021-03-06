/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.controllers;

import com.samsara.samsara.Securityimp.UserPrincipal;
import com.samsara.samsara.Securityimp.UserPrincipalService;
import com.samsara.samsara.entities.Advertise;
import com.samsara.samsara.entities.User;
import com.samsara.samsara.services.UserService;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
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
            user.setActive(1);
            userserv.addUser(user);
            System.out.println("authintecated success");
            authenticateUserAndSetSession(user.getUserName(), repassword,request);            
            new File(Paths.get("src\\main\\upload\\profilepic\\"+user.getUserName()).toAbsolutePath().toString()).mkdir();
        }
                    System.out.println("not authintecated success");

        return "redirect:/";
    }

    private void authenticateUserAndSetSession(String username, String password, HttpServletRequest request) {
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
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/validlogin", method = RequestMethod.POST)
    @ResponseBody
    public boolean validlogin(@RequestParam String username, @RequestParam String password) {
        User user = userserv.findUserByUserName(username);
        System.out.println(username);
        System.out.println(password);
        if (user != null) {
            System.out.println("first ste[");
            System.out.println(user.getPassword());
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("checking");
                return true;
            }
        }
        if(user==null)
            System.out.println("what??");
        System.out.println("non valid");
        return false;
    }

    @GetMapping("/test1")
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
    /*@GetMapping(value="/getadsprofile")
    @ResponseBody
    public List<Advertise> test(@RequestParam long id){
        User user=userserv.findUserById(id);
    return user.getAds();
    }*/
    @GetMapping(value="/getadsprofile")
    @ResponseBody
    public List<Advertise> test1(@RequestParam String username){
        User user=userserv.findUserByUserName(username);
    return user.getAds();
    }

}
