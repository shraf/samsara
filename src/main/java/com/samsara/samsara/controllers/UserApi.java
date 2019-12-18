/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.controllers;

import com.samsara.samsara.Securityimp.UserPrincipal;
import com.samsara.samsara.entities.User;
import com.samsara.samsara.services.UserService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author eldee
 */
@RestController
@RequestMapping("/users")
public class UserApi {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserService userservice;

    @RequestMapping(value = "/updpassword", method = RequestMethod.POST)
    public boolean updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        String username = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userservice.findUserByUserName(username);
        String currentPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        if (passwordEncoder.matches(oldPassword, currentPassword)) {
            userservice.updateUser(user);
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public boolean uploadImage(HttpSession session, @RequestParam MultipartFile file) {
        System.out.println("upload method has been accessed");
        String username = (String) session.getAttribute("user");
        User user = userservice.findUserByUserName(username);
        System.out.println("file is:" + file);
        System.out.println("file content type is:" + file.getContentType());
        if (!isFileValid(file)) 
            return false;
        saveFile(file, user);
        return true;

    }

    private boolean isFileValid(MultipartFile file) {
        if (file == null) {
            return false;
        }
        return (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png"));
    }

    private void saveFile(MultipartFile file, User user) {
        try {
            String filename = file.getOriginalFilename();
            File image = new File(Paths.get("src\\main\\upload\\profilepic\\" + user.getUserName()).toAbsolutePath().toString(), filename);
            System.out.println("file has been saved");
            file.transferTo(image);
            user.setImageUrl(file.getOriginalFilename());
            userservice.updateUser(user);
        } catch (IOException ex) {
            Logger.getLogger(UserApi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(UserApi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @RequestMapping("isSameProfile")
    public boolean isSameProfile(@RequestParam String username,HttpSession session){
        return username.equals((String)session.getAttribute("user"));
    }
}
