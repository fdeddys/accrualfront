package com.ddabadi.domain.repository;

import com.ddabadi.domain.JurnalHeader;
import com.ddabadi.enumer.JenisVoucher;
import com.ddabadi.enumer.Status;
import com.ddabadi.enumer.StatusVoucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

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


    Page<JurnalHeader> findByJenisVoucherAndIssueDateBetween(JenisVoucher jenisVoucher, Date tgl1, Date tgl2, Pageable pageable);

    //list voucher pengeluaran yg sudah isTarik di surat transfer
    Page<JurnalHeader> findByJenisVoucherAndIssueDateBetweenAndIsTarikPembayaranTrue(JenisVoucher jenisVoucher, Date tgl1, Date tgl2, Pageable pageable);

    //untuk cek di buku besar
    List<JurnalHeader> findByStatusVoucherAndIssueDateBetween(StatusVoucher statusVoucher, Date tgl1, Date tgl2);

    Page<JurnalHeader> findByIssueDateBetweenAndUserIdAndStatusVoucher(Date tanggal1,
                                                                Date tanggal2,
                                                                Long userId,
                                                                StatusVoucher statusVoucher,
                                                                Pageable pageable);

    // untuk posting per voucher yang sudah un posting
    Page<JurnalHeader> findByIssueDateBetweenAndStatusVoucherAndJenisVoucher(Date tgl1,
                                                                             Date tgl2,
                                                                             StatusVoucher statusVoucher,
                                                                             JenisVoucher jenisVoucher,
                                                                             Pageable pageable);

    // untuk posting per voucher yang sudah un posting VOUCHER PEMBAYARAN
    Page<JurnalHeader> findByIssueDateBetweenAndStatusVoucherAndJenisVoucherAndIsValidasiPembayaranIsFalse(Date tgl1,
                                                                                                        Date tgl2,
                                                                                                        StatusVoucher statusVoucher,
                                                                                                        JenisVoucher jenisVoucher,
                                                                                                        Pageable pageable);

    //list jurnal belum posting VOUCHER PEMBAYARAN
    Page<JurnalHeader> findByIssueDateBetweenAndNoUrutIsNotNullAndStatusVoucherAndJenisVoucherAndIsValidasiPembayaranIsFalse(Date tgl1,
                                                                                                           Date tgl2,
                                                                                                           StatusVoucher statusVoucher,
                                                                                                           JenisVoucher jenisVoucher,
                                                                                                           Pageable pageable);

    //list jurnal belum posting VOUCHER PEMBAYARAN REVISI tarik pembayaran
    Page<JurnalHeader> findByIssueDateBetweenAndNoUrutIsNotNullAndStatusVoucherAndJenisVoucherAndIsTarikPembayaranIsFalse(Date tgl1,
                                                                                                                             Date tgl2,
                                                                                                                             StatusVoucher statusVoucher,
                                                                                                                             JenisVoucher jenisVoucher,
                                                                                                                             Pageable pageable);

    // List Jurnal untuk posting per voucher yang sudah un posting
    Page<JurnalHeader> findByIssueDateBetweenAndNoUrutIsNotNullAndStatusVoucherAndJenisVoucher(Date tgl1,
                                                                             Date tgl2,
                                                                             StatusVoucher statusVoucher,
                                                                             JenisVoucher jenisVoucher,
                                                                             Pageable pageable);




}
