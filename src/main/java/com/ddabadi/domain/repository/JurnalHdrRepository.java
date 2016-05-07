package com.ddabadi.domain.repository;

import com.ddabadi.domain.JurnalHeader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * Created by deddy on 5/3/16.
 */
public interface JurnalHdrRepository extends JpaRepository<JurnalHeader,Long> {

    @Query(value = "select h from JurnalHeader h where h.user.id= :cariId and h.issueDate between :tanggal1 and :tanggal2 ")
    Page<JurnalHeader> findByIdfindByIdTanggal(@Param("tanggal1")Date tanggal1,
                                               @Param("tanggal2")Date tanggal2,
                                               @Param("cariId")Long cariId,
                                               Pageable pageable);

    @Query(value = "select h from JurnalHeader h where h.issueDate between :tanggal1 and :tanggal2 ")
    Page<JurnalHeader> findByTanggalIssue(@Param("tanggal1")Date tanggal1,
                                          @Param("tanggal2")Date tanggal2,
                                          Pageable pageable);
}
