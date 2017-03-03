package com.ddabadi.service.impl;

import com.ddabadi.domain.AccrualConfig;
import com.ddabadi.domain.repository.AccrualConfigRepository;
import com.ddabadi.service.AccrualConfigService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by deddy on 5/6/16.
 */

@Service
public class AccrualConfigServiceImpl implements AccrualConfigService {

    @Autowired private AccrualConfigRepository repository;

    private Logger logger = Logger.getLogger(AccrualConfigService.class);


    @Override
    public AccrualConfig getConfig() {

        AccrualConfig accrualConfig = null;
        if (repository.findAll().iterator().hasNext()){
            accrualConfig = repository.findAll().iterator().next();
        }else{
            accrualConfig = new AccrualConfig();
            accrualConfig.setCoaBank("");
            accrualConfig.setCoaJurnalBalik("");
            accrualConfig.setCoaKas("");
            accrualConfig.setHeaderPembayaran("");
            accrualConfig.setHeaderPemindahan("");
            accrualConfig.setHeaderPenerimaan("");
            accrualConfig.setBulanTahunBerjalan("");
            accrualConfig.setKodeKasTableBank("");
            accrualConfig.setCoaPiutangUsaha("");
            accrualConfig=repository.saveAndFlush(accrualConfig);
        }

        return  accrualConfig;
    }

    @Override
    public AccrualConfig updateConfig(AccrualConfig accrualConfigUpdate) {
        AccrualConfig accrualConfig = null;
        boolean databaru =false;

        if (repository.findAll().iterator().hasNext()){
            accrualConfig = repository.findAll().iterator().next();
        }else{
            accrualConfig = new AccrualConfig();
            databaru=true;
        }
        accrualConfig.setCoaBank(accrualConfigUpdate.getCoaBank());
        accrualConfig.setCoaJurnalBalik(accrualConfigUpdate.getCoaJurnalBalik());
        accrualConfig.setCoaKas(accrualConfigUpdate.getCoaKas());
        accrualConfig.setHeaderPembayaran(accrualConfigUpdate.getHeaderPembayaran());
        accrualConfig.setHeaderPemindahan(accrualConfigUpdate.getHeaderPemindahan());
        accrualConfig.setHeaderPenerimaan(accrualConfigUpdate.getHeaderPenerimaan());
        accrualConfig.setBulanTahunBerjalan(accrualConfigUpdate.getBulanTahunBerjalan());
        accrualConfig.setKodeKasTableBank(accrualConfigUpdate.getKodeKasTableBank());
        accrualConfig.setCoaPiutangUsaha(accrualConfigUpdate.getCoaPiutangUsaha());
        //if(databaru==true){
        accrualConfig=repository.saveAndFlush(accrualConfig);
        //}
        return  accrualConfig;
    }


}
