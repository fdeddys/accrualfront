package com.ddabadi.domain.repository;

import com.ddabadi.domain.JurnalDetil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by deddy on 5/6/16.
 */
public interface JurnalDetilRepository extends JpaRepository<JurnalDetil,Long> {

    @Query(value = "Select d from JurnalDetil d where d.jurnalHeader.id = :cari ")
    List<JurnalDetil> findByJurnalHdrId(@Param("cari")Long cari);

    @Query(value = "Select d from JurnalDetil d where d.jurnalHeader.id = :cari ")
    Page<JurnalDetil> findByJurnalHdrIdPage(@Param("cari")Long cari, Pageable pageable);

}
