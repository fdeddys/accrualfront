package com.ddabadi.service.impl;

import com.ddabadi.domain.CoaDtl;
import com.ddabadi.domain.repository.CoaDtlRepository;
import com.ddabadi.enumer.GroupAccount;
import com.ddabadi.service.CoaDtlService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by deddy on 5/3/16.
 */

@Service
public class CoaDtlServiceImpl implements CoaDtlService {

    @Autowired
    private CoaDtlRepository repository;
    private Logger logger = Logger.getLogger(CoaDtlService.class);

    @Override
    public Page<CoaDtl> getByKodeByNamaPage(String kode, String nama, int hal, int jumlah) {
        logger.info("get by kode by nama page");
        PageRequest pageRequest=new PageRequest(hal-1, jumlah, Sort.Direction.ASC,"kodePerkiraan");
        return repository.getByKodeByNamaPage(kode, nama, pageRequest);
    }

    @Override
    public Page<CoaDtl> getByKodeByNamaCashBankPage(String kode, String nama, int hal, int jumlah) {
        logger.info("get by kode by nama CASH BANK page");
        PageRequest pageRequest=new PageRequest(hal-1, jumlah, Sort.Direction.ASC,"kodePerkiraan");
        return repository.findByNamaPerkiraanLikeAndKodePerkiraanLikeAndCashBankIsTrue(nama, kode, pageRequest);
    }

    @Override
    public CoaDtl getById(Long id) {
        logger.info("get by id");
        return repository.findOne(id);
    }

    @Override
    public boolean isKodeExis(String kode) {
        logger.info("is kode exis");
        PageRequest pageRequest=new PageRequest(0, 1);
        return repository.getByKodeByNamaPage(kode,"%",pageRequest).hasNext();
    }

    @Override
    public CoaDtl save(CoaDtl coaDtl) {
        logger.info("save");
        return repository.saveAndFlush(coaDtl);
    }

    @Override
    public CoaDtl update(Long id, CoaDtl coaDtl) {
        logger.info("update");
        CoaDtl coaUpdate = repository.findOne(id);
        coaUpdate.setStatus(coaDtl.getStatus());
        coaUpdate.setAutoGenerateNo(coaDtl.getAutoGenerateNo());
        coaUpdate.setCashBank(coaDtl.getCashBank());
        coaUpdate.setAccountHeader(coaDtl.getAccountHeader());
        coaUpdate.setCust(coaDtl.getCust());
        coaUpdate.setHeaderAutoGenerateNo(coaDtl.getHeaderAutoGenerateNo());
        //coaUpdate.setKodePerkiraan(coaDtl.getKodePerkiraan());
        coaUpdate.setNamaPerkiraan(coaDtl.getNamaPerkiraan());
        coaUpdate.setRel(coaDtl.getRel());
        coaUpdate.setIsDebet(coaDtl.getIsDebet());
        return repository.saveAndFlush(coaUpdate);
    }

    @Override
    public List<CoaDtl> getAllCoaNeraca() {
        Sort sort=new Sort(Sort.Direction.ASC, "kodePerkiraan");
        return repository.findByAccountHeaderGroupAccountOrderByKodePerkiraan(GroupAccount.NERACA);
    }

    @Override
    public List<CoaDtl> getAllCoaLR() {
        Sort sort=new Sort(Sort.Direction.ASC, "kodePerkiraan");
        return repository.findByAccountHeaderGroupAccountOrderByKodePerkiraan(GroupAccount.LABARUGI_BIAYA);
    }

    @Override
    public List<CoaDtl> getAllCoa() {
        Sort sort=new Sort(Sort.Direction.ASC, "kodePerkiraan");
        return repository.findAll(sort);
    }

}
