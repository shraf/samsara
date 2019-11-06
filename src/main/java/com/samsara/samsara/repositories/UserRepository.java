/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.repositories;

import com.samsara.samsara.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author eldee
 */
public interface UserRepository extends CrudRepository<User,Long> {
    public User findByUserName(String userName);
}
