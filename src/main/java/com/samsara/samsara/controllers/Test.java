/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.controllers;

import com.samsara.samsara.entities.Advertise;
import com.samsara.samsara.services.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


/**
 *
 * @author eldee
 */
@RestController
@RequestMapping("/ad")
public class Test {
 @Autowired
 AdvertiseService adservice;

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
}

