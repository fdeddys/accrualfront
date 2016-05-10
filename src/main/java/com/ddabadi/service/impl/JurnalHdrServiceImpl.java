package com.ddabadi.service.impl;

import com.ddabadi.domain.JurnalHeader;
import com.ddabadi.domain.repository.JurnalHdrRepository;
import com.ddabadi.service.JurnalHdrService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by deddy on 5/3/16.
 */

@Service
public class JurnalHdrServiceImpl implements JurnalHdrService {

    @Autowired
    private JurnalHdrRepository repository;

    private Logger logger = Logger.getLogger(JurnalHdrService.class);

    @Override
    public Page<JurnalHeader> getByIdUserTanggal(Long idUser, Date tgl1, Date tgl2, int hal, int jumlah) {
        logger.info("get by id User");
        PageRequest pageRequest = new PageRequest(hal-1, jumlah, Sort.Direction.ASC,"id");
        return repository.findByIdfindByIdTanggal(tgl1, tgl2,idUser, pageRequest);
    }

    @Override
    public JurnalHeader getByIdJurnalByIdUser(Long idJurnal, Long idUser) {
        logger.info("get by id jurnal - id User");
        JurnalHeader jurnalHeader = null;
        jurnalHeader= repository.findOne(idJurnal);
        if(jurnalHeader==null){
            // jurnal tidak ketemu
        }else   {
            if(jurnalHeader.getUser().getId()==idUser){
                //jurnal header milik id user
            }else   {
                // jurnal header milik id user LAIN
                jurnalHeader=null;
            }
        }
        return jurnalHeader;
    }

    @Override
    public JurnalHeader getById(Long idJurnal) {
        logger.info("get by id jurnal ");
        return repository.findOne(idJurnal);
    }

    @Override
    public Page<JurnalHeader> getByTanggalIssuePage(Date tgl1, Date tgl2, int hal, int jumlah) {
        logger.info("get by tanggal issue");
        PageRequest pageRequest = new PageRequest(hal-1, jumlah, Sort.Direction.ASC,"id");
        return repository.findByTanggalIssue(tgl1,tgl1,pageRequest);

    }

    @Override
    public JurnalHeader save(JurnalHeader jurnalHeader) {
        return repository.saveAndFlush(jurnalHeader);
    }

    @Override
    public JurnalHeader approve(Long id) {
        return null;
    }
}
