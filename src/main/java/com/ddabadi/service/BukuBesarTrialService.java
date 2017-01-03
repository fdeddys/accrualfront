package com.ddabadi.service;

import com.ddabadi.domain.BukuBesarTrial;
import com.ddabadi.domain.JurnalHeader;
import org.springframework.data.domain.Page;

/**
 * Created by deddy on 12/18/16.
 */
public interface BukuBesarTrialService {

    BukuBesarTrial save(BukuBesarTrial bukuBesarTrial);
    public void postingJurnalTrial(Long jurnalHeaderId);
    Page<BukuBesarTrial> getBBTrialAll(int hal, int jumlah);
    Page<BukuBesarTrial> getBBTrialByIdCoaIdBankRelIdCust(Long idCoa,String idBank, String rel,String idCust, int hal, int jumlah);

}
