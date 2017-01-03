package com.ddabadi.domain.repository;

import com.ddabadi.domain.CoaHdr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by deddy on 5/3/16.
 */
public interface CoaHdrRepository extends JpaRepository<CoaHdr,Long> {


    @Query(value = "select h from CoaHdr h where h.kodeAccount like :cariKode and h.namaAccount like :cariNama")
    Page<CoaHdr> getByKodeByNamaPage(@Param("cariKode")String cariKode,
                                     @Param("cariNama")String cariNama,
                                     Pageable pageable);


}
