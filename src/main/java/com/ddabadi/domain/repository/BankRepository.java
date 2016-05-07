package com.ddabadi.domain.repository;

import com.ddabadi.domain.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by deddy on 4/25/16.
 */
public interface BankRepository extends JpaRepository<Bank, Long> {


    @Query("select  b from Bank b where b.kode = :cari ")
    List<Bank> findByKode(@Param("cari")String cari);

    @Query("Select b from Bank b where b.nama like :cari ")
    Page<Bank> findByNama(@Param("cari")String cari, Pageable pageable);

    @Query("Select b from Bank b where b.kode like :cariKode and b.nama like :cariNama ")
    Page<Bank> findByKodeNama(@Param("cariKode")String cariKode, @Param("cariNama")String cariNama, Pageable pageable);

}
