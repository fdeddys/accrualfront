package com.ddabadi.service;

import com.ddabadi.domain.JurnalDetil;
import com.ddabadi.domain.JurnalHeader;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by deddy on 5/6/16.
 */
public interface JurnalDetilService {

    JurnalDetil getById(Long id);
    List<JurnalDetil> getByJurnalHdrId(Long jurnalHdrId);
    Page<JurnalDetil> getByJurnalHdrIdPage(Long jurnalHdrId);
    JurnalDetil save(JurnalDetil jurnalDetil);
    public Integer delete(Long idJurnalDetil);

}
