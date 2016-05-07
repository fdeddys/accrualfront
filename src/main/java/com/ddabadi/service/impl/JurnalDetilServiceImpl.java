package com.ddabadi.service.impl;

import com.ddabadi.domain.JurnalDetil;
import com.ddabadi.domain.JurnalHeader;
import com.ddabadi.domain.repository.JurnalDetilRepository;
import com.ddabadi.service.JurnalDetilService;
import com.ddabadi.service.JurnalHdrService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by deddy on 5/6/16.
 */

@Service
public class JurnalDetilServiceImpl implements JurnalDetilService {

    private Logger logger=Logger.getLogger(JurnalDetilService.class);

    @Autowired private JurnalDetilRepository jurnalDetilRepository;


    @Override
    public JurnalDetil getById(Long id) {
        return null;
    }

    @Override
    public List<JurnalDetil> getByJurnalHdrId(Long jurnalHdrId) {
        return null;
    }

    @Override
    public Page<JurnalDetil> getByJurnalHdrIdPage(Long jurnalHdrId) {
        return null;
    }

    @Override
    public JurnalDetil save(JurnalDetil jurnalDetil) {
        return null;
    }

    @Override
    public Integer delete(Long idJurnalDetil) {
        return null;
    }
}
