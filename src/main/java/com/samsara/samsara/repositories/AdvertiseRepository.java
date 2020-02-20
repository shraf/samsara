/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.repositories;

import com.samsara.samsara.entities.Advertise;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author eldee
 */
public interface AdvertiseRepository extends CrudRepository<Advertise,Long>{
    public List<Advertise> findAllByTitle(String title);
        @Modifying
    @Query(value="DELETE FROM user_ads WHERE ads_id=?",nativeQuery=true)
        @Transactional
    public void deleteFromuser_ads(long id);
}
