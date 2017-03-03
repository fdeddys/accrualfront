package com.ddabadi.service;

import com.ddabadi.domain.*;
import com.ddabadi.dto.JurnalDetilDto;
import com.ddabadi.dto.JurnalOtomatisDto;
import com.ddabadi.enumer.JenisVoucher;
import com.ddabadi.enumer.StatusVoucher;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by deddy on 5/6/16.
 */
public interface JurnalDetilService {

    JurnalDetil getById(Long id);
    List<JurnalDetil> getByJurnalHdrId(Long jurnalHdrId);
    Page<JurnalDetil> getByJurnalHdrIdPage(Long jurnalHdrId,int hal, int jumlah);

    // untuk jurnal balik
    Page<JurnalDetil> getByJurnalKreditHdrIdPage(Long jurnalHdrId,int hal, int jumlah);
    JurnalDetil getByJurnalDebetFirst(Long jurnalHdrId);

    JurnalDetil save(JurnalDetilDto jurnalDetilDto);
    JurnalDetil saveJurnalDetil(JurnalDetil jurnalDetil);
    void saveOtomatis(JurnalHeader jurnalHeader, JurnalDetil jurnalDetil );

    public Integer delete(Long idJurnalDetil);

    //untuk proses tutup bulan
    List<JurnalDetil> getByCoaTglIssue(CoaDtl coaDtl, Date tgl1, Date tgl2);
    List<JurnalDetil> getByCoaBagianTglIssue(CoaDtl coaDtl, Bagian bagian,Date tgl1, Date tgl2);

    //narik daftar jurnal untuk proses surat transfer
    Page<JurnalDetil> getVoucherSuratTransfer( int hal, int jumlah);
    Page<JurnalDetil> getVoucherSuratTransferBankNoUrut(Long idBank,String noUrut, int hal, int jumlah);

    Double getTotalDebet(Long idHdr);
    Double getTotalKredit(Long idHdr);

    //List Jurnal untuk inpput booking
    Page<JurnalDetil> getAllJurnalForInputBooking(Date tglAwal,Date tglAkhir,int hal, int jumlah);
    //List Jurnal Kredit >0, jurnal pemindahan, coa=coa passiva,2
    List<JurnalDetil>getJurnalHdrIdAndKredit(Long cari);

}
