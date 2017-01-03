package com.ddabadi.domain.repository;

import com.ddabadi.domain.SuratTransferDt;

import com.ddabadi.domain.SuratTransferHd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by deddy on 10/4/16.
 */
public interface SuratTransferDtRepository extends JpaRepository<SuratTransferDt, Long> {


    //getDtByHd
    Page<SuratTransferDt> findBySuratTransferHdOrderById(SuratTransferHd suratTransferHd,Pageable pageable);

    //utk report surat transfer
    List<SuratTransferDt> findBySuratTransferHdOrderById(SuratTransferHd suratTransferHd);
}
