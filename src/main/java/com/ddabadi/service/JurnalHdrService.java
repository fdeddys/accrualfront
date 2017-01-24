package com.ddabadi.service;

import com.ddabadi.domain.CoaHdr;
import com.ddabadi.domain.JurnalHeader;
import com.ddabadi.dto.JurnalHdrDto;
import com.ddabadi.enumer.JenisVoucher;
import com.ddabadi.enumer.StatusVoucher;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by deddy on 5/3/16.
 */
public interface JurnalHdrService {

    Page<JurnalHeader> getByIdUserTanggal(Long idUser, Date tgl1, Date tgl2, int hal, int jumlah);
    Page<JurnalHeader> getByIdUserTanggalStatus(Long idUser, Date tgl1, Date tgl2, StatusVoucher statusVoucher, int hal, int jumlah);
    JurnalHeader getByIdJurnalByIdUser(Long idJurnal, Long idUser);
    JurnalHeader getById(Long idJurnal);
    Page<JurnalHeader> getByTanggalIssuePage(Date tgl1, Date tgl2, int hal, int jumlah);
    JurnalHeader insertRec(JurnalHdrDto jurnalHeaderDto) throws ParseException;
    Page<JurnalHeader> getVoucherPengeluaranByIssueDate(Date tgl1, Date tgl2, int hal, int jumlah);
    Page<JurnalHeader> getVoucherPengeluaranIsTarikByIssueDate(Date tgl1, Date tgl2, int hal, int jumlah);
    JurnalHeader approve(Long idHdr, Long idUser, Date tglBook);

    JurnalHeader save(JurnalHdrDto jurnalHeaderDto) throws ParseException;
    JurnalHeader save(JurnalHeader jurnalHeader) ;

    boolean posting(Long idHdr);
    String deleteFisikVoucher(Long idHdr);

    Long prosesJurnalBalik(Long idDebet, Long idKredit, Long idUser, Long idBank);

    //untuk cek pas create bulanan apakah ada yg blm posting
    List<JurnalHeader> getAllCoaHdrByStatusVoucher(StatusVoucher statusVoucher, Date tglIssueAwal, Date tglIssueAkhir);

    //untuk p0sting
    Page<JurnalHeader> getByIssueDateBetweenAndStatusVoucherAndJenisVoucher(Date tgl1, Date tgl2, StatusVoucher statusVoucher, JenisVoucher jenisVoucher, int hal, int jumlah);

    //utk posting jurnal penerimaan pemindahan -UPDATE
    Page<JurnalHeader> getJurnalBelumPostingByIssueDateBetweenAndStatusVoucherAndJenisVoucher(Date tgl1, Date tgl2, StatusVoucher statusVoucher, JenisVoucher jenisVoucher, int hal, int jumlah);


    //validasi voucher pengeluaran untuk ke surat transfer
    public String validasiPembayaran(Long idHdr);

}
