package com.ddabadi.domain.repository;

import com.ddabadi.domain.IdxNoUrut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;

/**
 * Created by deddy on 5/15/16.
 */
public interface IdxNoUrutRepository extends JpaRepository<IdxNoUrut,Long> {

    IdxNoUrut findByIdUserAndTahun(Long idUser, String tahun);

    @Query(value = "select i from IdxNoUrut i where i.idUser = :cariIdUser and i.tahun =:cariTahun ")
    IdxNoUrut findByIdUserTahun(@Param("cariIdUser ") Long cariIdUser ,
                                @Param("cariTahun") String cariTahun);



}
