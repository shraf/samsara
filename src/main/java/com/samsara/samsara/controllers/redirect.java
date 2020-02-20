/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.controllers;

import com.samsara.samsara.entities.Advertise;
import com.samsara.samsara.entities.User;
import com.samsara.samsara.repositories.AdvertiseRepository;
import com.samsara.samsara.services.AdvertiseService;
import com.samsara.samsara.services.UserService;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author eldee
 */
@Controller
public class redirect {
    
    @Autowired
    private AdvertiseService adservice;
    
    @Autowired
    private UserService userservice;
    
    @Autowired
    private AdvertiseRepository adrepo;
    
    @GetMapping("/faq")
        public String getFaq(){
    return "faq.html";
}
        @GetMapping("/cwu")
        public String getCWU(){
            return "cwa.html";
        }
    @RequestMapping(value={"/","/welcome"} )
    public String welcomepage(Model model,HttpSession session,HttpServletRequest request,Principal user) {
        System.out.println("hi");
        System.out.println("referre"+request.getHeader("referer"));
        if(session.getAttribute("SPRING_SECURITY_CONTEXT")==null){
            if(user!=null)
                System.out.println("user is not null");
        else
                System.out.println("user is null");
            return "welcome.html";
        }
        else
        {
            new AdsApi(adrepo,userservice,adservice).setPagintation(model, 1);
            return "ads.html";
        }
    }
    
    @RequestMapping("/signup")
    public String signuppage(HttpSession session){
        
        return session.getAttribute("SPRING_SECURITY_CONTEXT")==null?"signup.html":"redirect:/";
    }
    
    @RequestMapping("/sign-in")
    public String signinpage(HttpSession session,HttpServletRequest request){
        System.out.println(request.getHeader("referer"));
        return session.getAttribute("SPRING_SECURITY_CONTEXT")==null? "sign-in.html" :"redirect:/";
    }
    
    @RequestMapping("/ad")
    public String getAdPage(@RequestParam long id,HttpServletRequest request,Model model){
        Advertise ad=adservice.findAdvertiseById(id);
        System.out.println(ad.getImagesurl().size());
        model.addAttribute("currentAd",ad);
        model.addAttribute("imgs",ad.getImagesurl());
        System.out.println(adservice.findAdvertiseById(id));
        System.out.println(id);
        System.out.println("hi ad");
        request.setAttribute("currentAd",adservice.findAdvertiseById(id));
        return"ad.html";
    }
    
    @RequestMapping("/adform")
    public String getAdFormPage(){
        return "adform.html";
    }
    @RequestMapping("/ads")
    public String viewAds(Model model,@RequestParam int pagenumber){
        System.out.println("pagenumber is:"+pagenumber);
       adservice.findAllAdvertise().forEach(x-> System.out.println(x));
        new AdsApi(adrepo,userservice,adservice).setPagintation(model, pagenumber);
        return"ads.html";
    }

    /*@RequestMapping("/profile")
    public String redirectToProfile(Model model,@RequestParam int id){
        User user=userservice.findUserById(id);
        model.addAttribute("profile",id);
        model.addAttribute("userads",user.getAds());
        return "profile.html";
        
    }*/
    @RequestMapping("/profile")
    public String redirectToProfile(Model model,@RequestParam String username){
        User user=userservice.findUserByUserName(username);
        model.addAttribute("profile",username);
        model.addAttribute("userads",user.getAds());
        return "profile.html";
        
    }
    @RequestMapping("edit")
    public String redirectToEditPage(){
        return "edit.html";
    }
    @RequestMapping("whoare")
    public String redirectToWhoAreWePage(){
        return"whoare.html";
    }
    }

