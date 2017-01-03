package com.ddabadi.domain.repository;

import com.ddabadi.domain.BukuBesar;
import com.ddabadi.domain.CoaDtl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by deddy on 7/8/16.
 */
public interface BukuBesarRepository extends JpaRepository<BukuBesar,Long> {

    @Modifying
    @Transactional
    void deleteByBulanTahun(String bulanTahun);

    Page<BukuBesar> findByBulanTahunAndKeteranganLike(String bulanTahun, String keterangan, Pageable pageable);
    Page<BukuBesar> findByBulanTahunAndCoaDtlAndKeteranganLike(String bulanTahun, CoaDtl coaDtl, String keterangan, Pageable pageable);
}
