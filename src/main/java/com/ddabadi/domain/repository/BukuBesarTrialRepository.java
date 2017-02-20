package com.ddabadi.domain.repository;

import com.ddabadi.domain.BukuBesarTrial;
import com.ddabadi.domain.CoaDtl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by deddy on 12/18/16.
 */
public interface BukuBesarTrialRepository extends JpaRepository<BukuBesarTrial, Long> {

    //untuk posting masuk buku besar trial, ambil data terakhir => ambil saldo
    List<BukuBesarTrial> findByBulanTahunAndCoaDtlAndIdBankLikeAndRelLikeAndIdCustomerLikeOrderByIdDesc(
            String bulanTahun,
            CoaDtl coaDtl,
            Long idBank,
            String rel,
            Long idCustomer
    );

    //view buku besar trial -> FILTER
    Page<BukuBesarTrial> findByBulanTahunAndCoaDtlAndRelLikeAndCustomerKodeLike(
            String bulanTahun,
            CoaDtl coaDtl,
            String rel,
            String kodeCustomer,
            Pageable pageable
    );

//    //view ALL DATA --Buku besar
//    Page<BukuBesarTrial> findByBulanTahun(String bulanTahun, Pageable pageable);

    //untuk laporan BB trial
    List<BukuBesarTrial> findByBulanTahun(String bulanTahu, Sort sort);
    List<BukuBesarTrial> findByBulanTahunAndCoaDtl(String bulanTahu, CoaDtl coaDtl,Sort  sort);
}
