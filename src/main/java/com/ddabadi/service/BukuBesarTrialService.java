package com.ddabadi.service;

import com.ddabadi.domain.BukuBesar;
import com.ddabadi.domain.BukuBesarTrial;
import com.ddabadi.domain.JurnalHeader;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by deddy on 12/18/16.
 */
public interface BukuBesarTrialService {

    BukuBesarTrial save(BukuBesarTrial bukuBesarTrial);
    public void postingJurnalTrial(Long jurnalHeaderId);
    public void setPosting(Long id);

    public void unPostingJurnalTrial(Long jurnalHeaderId);

    Page<BukuBesarTrial> getBBTrialAll(int hal, int jumlah);
    Page<BukuBesarTrial> getBBTrialByIdCoaIdBankRelIdCust(Long idCoa, String rel,Long idCust, int hal, int jumlah);

    //untuk laporan BB trial
    List<BukuBesarTrial> getBBTrialAll();
    List<BukuBesarTrial> getBBTrialCoa(Long idCoa);


}
