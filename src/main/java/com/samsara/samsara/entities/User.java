/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

/**
 *
 * @author eldee
 */
@Entity
@Table(name="users")
public class User {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Basic(fetch=FetchType.LAZY)
    private String password;
    
    private String email;
    
    private String userName;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Advertise> ads;
    
    private String ImageUrl;
    
    private int active;

    private String roles = "";

    private String permissions = "";
    
    
    public User(String userName, String password, String roles, String permissions){
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
    }
    public User(){
        
    }
    public List<Advertise> getAds() {
        return ads;
    }

    public void setAds(List<Advertise> ads) {
        this.ads = ads;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
    
    public void addAdd(Advertise ads){
        this.ads.add(ads);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

     public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @PrePersist
    public void checkProfilepic(){
         ImageUrl="\\blankhead.jpg";
         try(InputStream in=new URL("https://independentsector.org/wp-content/uploads/2016/12/blankhead.jpg").openStream()){
             Files.copy(in,Paths.get("src\\main\\upload\\profilepic\\"+this.userName+ImageUrl));
         } catch (MalformedURLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public String toString() {
        return "com.samsara.samsara.entities.User[ id=" + id + " ]";
    }
    
}
