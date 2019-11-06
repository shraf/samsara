/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.repositories;

import com.samsara.samsara.entities.Advertise;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author eldee
 */
public interface AdvertiseRepository extends CrudRepository<Advertise,Long>{
    public List<Advertise> findAllByTitle(String title);
}
