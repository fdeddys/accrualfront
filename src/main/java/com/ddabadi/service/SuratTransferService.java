package com.ddabadi.service;

import com.ddabadi.domain.SuratTransferDt;
import com.ddabadi.domain.SuratTransferHd;
import com.ddabadi.dto.SuratTransferDtDto;
import com.ddabadi.dto.SuratTransferHdDto;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by deddy on 10/4/16.
 */
public interface SuratTransferService {

    //HD
    SuratTransferHd saveHd(SuratTransferHdDto suratTransferHdDto);

    SuratTransferHd editHd(Long idEdit, SuratTransferHd suratTransferHd);
    SuratTransferHd getHdById(Long id);
    SuratTransferHd ApproveById(Long id);
    Page<SuratTransferHd> getHdByTanggalSuratNoApprove(Date tgl1, Date tgl2,String noApprove, int hal, int jumlah);

    //DT
    SuratTransferDt saveDt(SuratTransferDtDto suratTransferDtDto);
    SuratTransferDt getDtById(Long id);
    public void delDtById(Long id);
    Page<SuratTransferDt> getDtByHd(Long idHd, int hal, int jumlah);
    List<SuratTransferDt> getDtByHd(Long idHd);
}
