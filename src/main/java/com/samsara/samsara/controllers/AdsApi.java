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
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author eldee
 */

@RestController
@RequestMapping("/ads")
public class AdsApi {

    @Autowired
    private final AdvertiseRepository adrepo;

    @Autowired
    private AdvertiseService adservice;

    private UserService userservice;

    public AdsApi(AdvertiseRepository adrepo, UserService userservice, AdvertiseService adservice) {
        this.adrepo = adrepo;
        this.userservice = userservice;
        this.adservice = adservice;
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Advertise> getAds() {
        List ads = new ArrayList<Advertise>();
        adrepo.findAll().forEach(x -> ads.add(x));
        return ads;
    }

    @PostMapping("/add")
    public String addAd(HttpSession session, Advertise ad, HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        List<String> fileNames = new ArrayList<String>();
        User user = userservice.findUserByUserName((String) session.getAttribute("user"));
        user.addAdd(ad);
        ad.setUser(user);
        ad = adservice.saveAdvertise(ad);
        if (files != null && files.length > 0) {
            saveAdImages(files, ad);
        }
        /*System.out.println("files are"+request.getParameter("files"));
        System.out.println("length is"+mpf.length);*/

        return "redirect:/";
    }

    private void saveAdImages(MultipartFile[] files, Advertise ad) {
        for (MultipartFile mpf : files) {
            try {
                String filename = mpf.getOriginalFilename();
                new File(Paths.get("src\\main\\upload\\images\\" + ad.getId()).toAbsolutePath().toString()).mkdir();
                File file = new File(Paths.get("src\\main\\upload\\images\\" + ad.getId()).toAbsolutePath().toString(), filename);

                mpf.transferTo(file);
                ad.addImage(filename);
                adservice.updateAdvertise(ad);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("file:" + mpf.getOriginalFilename());
        }
    }

    @GetMapping("/")
    public Advertise getAd(@RequestParam long id) {
        return (Advertise) adrepo.findById(id).orElse(null);
    }

    @DeleteMapping("/deletead")
    public String deleteAd(@RequestParam long id) {
        System.out.println("deleting");
        Advertise ad = adservice.findAdvertiseById(id);
        if (ad != null) {
            adservice.deleteAdvertise(ad);
            return"advertise has been deleted";
        }
        return "advertise doesn't exist";
        
    }

    public void setPagintation(Model model, int pageNumber) {
        int totalPages;
        adservice.findAllAdvertise().forEach(x -> System.out.println(x));

        ArrayList<Advertise> ads = (ArrayList) adservice.findAllAdvertise();
        Collections.reverse(ads);
        ArrayList<Advertise> currentPageAds = new ArrayList<Advertise>();

        totalPages = ads.size() % 10 == 0 ? ads.size() / 10 : ads.size() / 10 + 1;

        for (int i = pageNumber * 10 - 10; i < pageNumber * 10; i++) {
            if (ads.size() == i) {
                break;
            }
            currentPageAds.add(ads.get(i));
        }

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("ads", currentPageAds);
    }

  

    @GetMapping(value = "/getadsprofile")
    public List<Advertise> test(@RequestParam long id) {
        User user = userservice.findUserById(id);
        return user.getAds();
    }
 

}