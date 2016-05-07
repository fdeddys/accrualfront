package com.ddabadi.service.impl;

import com.ddabadi.domain.CoaHdr;
import com.ddabadi.domain.repository.CoaHdrRepository;
import com.ddabadi.service.CoaHdrService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by deddy on 5/3/16.
 */
@Service
public class CoaHdrServiceImpl implements CoaHdrService {

    @Autowired private CoaHdrRepository repository;
    private Logger logger = Logger.getLogger(CoaHdrService.class);

    @Override
    public Page<CoaHdr> getByKodeByNamaPage(String kode, String nama, int hal, int jumlah) {
        logger.info("get by kode by nama page");
        PageRequest pageRequest=new PageRequest(hal-1, jumlah, Sort.Direction.ASC,"kodeAccount");
        return repository.getByKodeByNamaPage(kode,nama,pageRequest);
    }

    @Override
    public CoaHdr getById(Long id) {
        logger.info("get by id");
        return repository.findOne(id);
    }

    @Override
    public boolean isKodeExis(String kode) {
        logger.info("is kode exis");
        PageRequest pageRequest=new PageRequest(0, 1);
        return repository.getByKodeByNamaPage("%","%",pageRequest).hasNext();
    }

    @Override
    public CoaHdr save(CoaHdr coaHdr) {
        logger.info("save");
        return repository.saveAndFlush(coaHdr);
    }

    @Override
    public CoaHdr update(Long id, CoaHdr coaHdr) {
        logger.info("update");
        CoaHdr coaHdrUpdate = repository.findOne(id);
        coaHdrUpdate.setKodeAccount(coaHdr.getKodeAccount());
        coaHdrUpdate.setNamaAccount(coaHdr.getNamaAccount());
        return repository.saveAndFlush(coaHdrUpdate);
    }

}