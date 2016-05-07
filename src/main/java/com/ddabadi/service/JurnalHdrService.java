package com.ddabadi.service;

import com.ddabadi.domain.JurnalHeader;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * Created by deddy on 5/3/16.
 */
public interface JurnalHdrService {

    Page<JurnalHeader> getByIdUserTanggal(Long idUser, Date tgl1, Date tgl2, int hal, int jumlah);
    JurnalHeader getByIdJurnalByIdUser(Long idJurnal, Long idUser);
    JurnalHeader getById(Long idJurnal);
    Page<JurnalHeader> getByTanggalIssuePage(Date tgl1, Date tgl2, int hal, int jumlah);
    JurnalHeader save(JurnalHeader jurnalHeader);

    JurnalHeader approve(Long id);
}
