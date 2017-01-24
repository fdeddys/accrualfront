package com.ddabadi.domain.repository;

import com.ddabadi.domain.*;
import com.ddabadi.enumer.JenisVoucher;
import com.ddabadi.enumer.StatusVoucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by deddy on 5/6/16.
 */
public interface JurnalDetilRepository extends JpaRepository<JurnalDetil,Long> {

    @Query(value = "Select d from JurnalDetil d where d.jurnalHeader.id = :cari ")
    List<JurnalDetil> findByJurnalHdrId(@Param("cari")Long cari);

    @Query(value = "Select d from JurnalDetil d where d.jurnalHeader.id = :cari ")
    Page<JurnalDetil> findByJurnalHdrIdPage(@Param("cari")Long cari, Pageable pageable);

    Page<JurnalDetil> findByJurnalHeaderIdAndDebet(Long jurnalHeaderId,Double debet, Pageable pageable);

    List<JurnalDetil> findByJurnalHeaderIdAndKreditOrderById(Long jurnalHeaderId,Double kredit);

    List<JurnalDetil> findByAccountDetilAndJurnalHeaderIssueDateBetweenOrderById(CoaDtl coaDtl, Date tgl1, Date tgl2);

    List<JurnalDetil> findByAccountDetilAndBagianAndJurnalHeaderIssueDateBetweenOrderById(CoaDtl coaDtl,Bagian bagian, Date tgl1, Date tgl2);


    // untuk tarik list jurnal ->proses surat transfer
    Page<JurnalDetil> findByBankAndCustomerAndJurnalHeaderJenisVoucherAndJurnalHeaderStatusVoucherAndJurnalHeaderIsTarikPembayaranFalseOrderByJurnalHeaderIssueDate(
                        Bank bank,
                        Customer customer,
                        JenisVoucher jenisVoucher,
                        StatusVoucher statusVoucher,Pageable pageable);


    // untuk tarik list jurnal ->proses surat transfer --TANPA BANK -SUPPLIER
    Page<JurnalDetil> findByJurnalHeaderJenisVoucherAndJurnalHeaderIsValidasiPembayaranIsTrueAndJurnalHeaderStatusVoucherAndJurnalHeaderIsTarikPembayaranFalseAndDebetOrderByJurnalHeaderIssueDate(
            JenisVoucher jenisVoucher,
            StatusVoucher statusVoucher,
            Double kredit,
            Pageable pageable);

    // untuk tarik list jurnal ->proses surat transfer ---Bank, NO Urut
    Page<JurnalDetil> findByJurnalHeaderJenisVoucherAndJurnalHeaderStatusVoucherAndDebetAndBankAndJurnalHeaderNoUrutLikeAndJurnalHeaderIsTarikPembayaranIsFalseOrderByJurnalHeaderIssueDate(
            JenisVoucher jenisVoucher,
            StatusVoucher statusVoucher,
            Double kredit,
            Bank bank,
            String noUrut,
            Pageable pageable);

    @Query(value = "select sum(d.debet) from JurnalDetil d where d.jurnalHeader.id = :idHd ")
    Double findTotalDebet(@Param("idHd")Long idHd);

    @Query(value = "select sum(d.kredit) from JurnalDetil d where d.jurnalHeader.id = :idHd ")
    Double findTotalKredit(@Param("idHd")Long idHd);

}
