package com.ddabadi.domain.repository;

import com.ddabadi.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Iterator;
import java.util.List;

/**
 * Created by deddy on 4/25/16.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select u from User u where u.nama like :cari ")
    Page<User> findByNama(@Param("cari")String cari, Pageable pageable);

    @Query(value = "select u from User u where u.nama like :cari ")
    List<User> findOneByNama(@Param("cari")String cari);

}
