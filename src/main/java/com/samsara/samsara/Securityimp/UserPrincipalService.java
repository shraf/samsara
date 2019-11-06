/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.Securityimp;

import com.samsara.samsara.entities.User;
import com.samsara.samsara.repositories.UserRepository;
import java.io.Serializable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author eldee
 */
@Service
public class UserPrincipalService implements UserDetailsService,Serializable {
    UserRepository userrepo;
    public UserPrincipalService(UserRepository userrepo){
        this.userrepo=userrepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username)  {
        System.out.println("loadbuserbyusername");
        User user=userrepo.findByUserName(username);
        if(user==null)
            throw new UsernameNotFoundException(username);
        return new UserPrincipal(user);
    }
    
}
