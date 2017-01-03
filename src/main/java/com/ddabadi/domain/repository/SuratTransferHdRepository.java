package com.ddabadi.domain.repository;

import com.ddabadi.domain.SuratTransferHd;
import org.hibernate.exception.DataException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * Created by deddy on 10/4/16.
 */
public interface SuratTransferHdRepository extends JpaRepository<SuratTransferHd, Long> {

    @Query(value = "select h from SuratTransferHd h where h.tglSurat between :tgl1 and :tgl2 and ( h.noApprove like :cariApprove or h.noApprove is null ) ")
    Page<SuratTransferHd> findByTglSuratNoApprove(@Param("tgl1") Date tgl1,
                                                  @Param("tgl2") Date tgl2,
                                                  @Param("cariApprove") String noApprove, Pageable pageable);

    //SERVICE getHdByTanggalSuratNoApprove
    Page<SuratTransferHd> findByTglSuratBetweenAndNoApproveLikeOrNoApproveIsNull(Date tgl1, Date tgl2, String noApprove, Pageable pageable);
    Page<SuratTransferHd> findByTglSuratBetween(Date tgl1, Date tgl2, Pageable pageable);
    Page<SuratTransferHd> findByNoApproveLikeOrNoApproveIsNull(String noApprove, Pageable pageable);
}
