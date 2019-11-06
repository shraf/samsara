/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.services;

import com.samsara.samsara.entities.Advertise;
import com.samsara.samsara.repositories.AdvertiseRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author eldee
 */
@Service
public class AdvertiseService {
    
   private AdvertiseRepository advertiserepo;
   
   @Autowired
   public AdvertiseService(AdvertiseRepository advertiserepo){
       this.advertiserepo=advertiserepo;
   }
   
   public List<Advertise> findAllAdvertise(){
       List<Advertise> ads=new ArrayList();
       advertiserepo.findAll().forEach(x->ads.add(x));
       System.out.println(ads.size());
       return ads;
   }
   
   public Advertise findAdvertiseById(long id){
       return advertiserepo.findById(id).orElse(null);
   }
  
   public List<Advertise> findAdvertiseByTitle(String title){
       return advertiserepo.findAllByTitle(title);
   }
   
   public Advertise saveAdvertise(Advertise advertise){
      return advertiserepo.save(advertise);
   }
   
   public void updateAdvertise(Advertise advertise){
       advertiserepo.save(advertise);
   }
   
   public void deleteAdvertise(Advertise Advertise){
       advertiserepo.delete(Advertise);
   }
}
