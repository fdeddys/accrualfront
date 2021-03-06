package com.ddabadi.domain.repository;

import com.ddabadi.domain.Bagian;
import com.ddabadi.enumer.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by deddy on 4/25/16.
 */
public interface BagianRepository extends JpaRepository<Bagian, Long> {

    Page<Bagian> findByNamaLikeAndKodeLike(String nama, String kode, Pageable pageable);

    Page<Bagian> findByNamaLikeAndKodeLikeAndStatus(String nama, String kode, Status status, Pageable pageable);

    @Query(value = "select b from Bagian b where b.nama like :cariNama and b.kode like :cariKode ")
    Page<Bagian> findByNamaKodePage(@Param("cariNama")String cariNama,
                                    @Param("cariKode")String cariKode,
                                    Pageable pageable);

    @Query(value = "select b from Bagian b where b.kode = :cariKode ")
    List<Bagian> findByKode(@Param("cariKode")String cariKode);





}
