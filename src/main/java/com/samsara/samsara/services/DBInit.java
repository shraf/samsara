/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.services;

import com.samsara.samsara.entities.User;
import com.samsara.samsara.repositories.UserRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author eldee
 */
@Service
public class DBInit implements CommandLineRunner {

    private UserRepository userrepo;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public DBInit(UserRepository userrepo,PasswordEncoder passwordEncoder){
        this.userrepo=userrepo;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

    }
    
    
}
