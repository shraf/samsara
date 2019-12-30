/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author eldee
 */
@Entity
public class Advertise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String describtion;
      @ElementCollection
    private List<String>imagesurl;
    @ManyToOne()
    @JsonBackReference
    private User user;
    private String phoneNumber;
    private String city;
    private String type;
    private LocalDateTime  creationDateTime;

    public LocalDateTime  getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime  creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
public Advertise(){
    imagesurl=new ArrayList<String>();
}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public List<String> getImagesurl() {
        return imagesurl;
    }

    public void setImagesurl(List<String> imagesurl) {
        this.imagesurl = imagesurl;
    }


    public void addImage(String imageurl){
        imagesurl.add(imageurl);
    }

}
