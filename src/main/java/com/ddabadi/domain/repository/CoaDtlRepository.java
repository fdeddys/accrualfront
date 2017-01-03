package com.ddabadi.domain.repository;

import com.ddabadi.domain.CoaDtl;
import com.ddabadi.enumer.GroupAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by deddy on 5/3/16.
 */
public interface CoaDtlRepository extends JpaRepository<CoaDtl,Long> {

    @Query(value = "select d from CoaDtl d where d.kodePerkiraan like :cariKode and d.namaPerkiraan like :cariNama")
    Page<CoaDtl> getByKodeByNamaPage(@Param("cariKode")String cariKode,
                                     @Param("cariNama")String cariNama,
                                     Pageable pageable);

    Page<CoaDtl> findByNamaPerkiraanLikeAndKodePerkiraanLikeAndCashBankIsTrue(String namaPerkiraan,
                                                                              String kodePerkiraan,
                                                                              Pageable pageable);

    List<CoaDtl> findByAccountHeaderGroupAccountOrderByKodePerkiraan(GroupAccount groupAccount);
}
