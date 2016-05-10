package com.ddabadi.service.impl;

import com.ddabadi.domain.Bagian;
import com.ddabadi.domain.CoaDtl;
import com.ddabadi.domain.JurnalDetil;
import com.ddabadi.domain.JurnalHeader;
import com.ddabadi.domain.repository.JurnalDetilRepository;
import com.ddabadi.domain.repository.JurnalHdrRepository;
import com.ddabadi.dto.JurnalDetilDto;
import com.ddabadi.enumer.DebetKredit;
import com.ddabadi.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    @Autowired private JurnalHdrService jurnalHdrService;
    @Autowired private CoaDtlService coaDtlService;
    @Autowired private BagianService bagianService;
    @Autowired private CustomerService customerService;
    @Autowired private BankService bankService;

    @Override
    public JurnalDetil getById(Long id) {
        return jurnalDetilRepository.findOne(id);
    }

    @Override
    public List<JurnalDetil> getByJurnalHdrId(Long jurnalHdrId) {

        return jurnalDetilRepository.findByJurnalHdrId(jurnalHdrId);
    }

    @Override
    public Page<JurnalDetil> getByJurnalHdrIdPage(Long jurnalHdrId, int hal, int jumlah) {
        PageRequest pageRequest=new PageRequest(hal-1,jumlah, Sort.Direction.ASC,"id");
        return jurnalDetilRepository.findByJurnalHdrIdPage(jurnalHdrId, pageRequest);
    }

    @Override
    public JurnalDetil save(JurnalDetilDto jurnalDetilDto) {
        logger.info("save");

        // if id = 0 -> add new
        // else
        //    id = jurnalDetil.id
        JurnalDetil jurnalDetil = null;
        if(jurnalDetilDto.getId()==0L){
            //new record
            jurnalDetil = new JurnalDetil();
        }else{
            //update record
            jurnalDetil = jurnalDetilRepository.findOne(jurnalDetilDto.getId());
        }
        JurnalHeader jurnalHeader = jurnalHdrService.getById(jurnalDetilDto.getJurnalHeaderId());
        jurnalDetil.setJurnalHeader(jurnalHeader);

        CoaDtl coaDtl = coaDtlService.getById(jurnalDetilDto.getAccountDetilId());
        jurnalDetil.setAccountDetil(coaDtl);

        Bagian bagian = bagianService.getByKode(jurnalDetilDto.getBagian());
        jurnalDetil.setBagian(bagian);

        jurnalDetil.setKeterangan(jurnalDetilDto.getKeterangan());
        jurnalDetil.setDebet(jurnalDetilDto.getDebet());
        jurnalDetil.setKredit(jurnalDetilDto.getKredit());
        if(jurnalDetilDto.getDk()=="D"){
            jurnalDetil.setDk(DebetKredit.D);
        }else{
            jurnalDetil.setDk(DebetKredit.K);
        }
        jurnalDetil.setRel(jurnalDetilDto.getRel());

        if(jurnalDetilDto.getCustomerId()==0){
            jurnalDetil.setCustomer(null);
        }else{
            jurnalDetil.setCustomer(customerService.getById(jurnalDetilDto.getCustomerId()));
        }

        if(jurnalDetilDto.getBankId()==0){
            jurnalDetil.setBank(null);
        }else{
            jurnalDetil.setBank(bankService.getById(jurnalDetilDto.getBankId()));
        }

        return jurnalDetilRepository.saveAndFlush(jurnalDetil);
    }

    @Override
    public Integer delete(Long idJurnalDetil) {
        int  hasil=0;

        try {
            jurnalDetilRepository.delete(idJurnalDetil);
            hasil   =1;
        }catch (Exception ex){
            logger.info("error delete : " + ex.getMessage());
        }

        return hasil;
    }
}
