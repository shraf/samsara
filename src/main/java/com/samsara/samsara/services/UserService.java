/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.services;

import com.samsara.samsara.entities.User;
import com.samsara.samsara.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 *
 * @author eldee
 */
@Service
@ComponentScan("com.samsara.samsara.controllers")

public class UserService {

    private UserRepository userrepo;

    @Autowired
    public UserService(UserRepository userrepo) {
        this.userrepo = userrepo;
    }

    public User findUserById(long id) {
        return userrepo.findById(id).orElse(null);
    }
    
    public User findUserByUserName(String username){
        return userrepo.findByUserName(username);
    }
    
    public void addUser(User user){
        userrepo.save(user);
    }
    
    public void deleteUser(User user){
        userrepo.delete(user);
    }
    
    public void updateUser(User user){
        userrepo.save(user);
    }
}
