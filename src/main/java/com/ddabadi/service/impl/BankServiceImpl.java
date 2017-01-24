package com.ddabadi.service.impl;

import com.ddabadi.domain.AccrualConfig;
import com.ddabadi.domain.Bank;
import com.ddabadi.domain.repository.BankRepository;
import com.ddabadi.service.AccrualConfigService;
import com.ddabadi.service.BankService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Created by deddy on 4/26/16.
 */

@Service
public class BankServiceImpl implements BankService {

    private Logger logger = Logger.getLogger(BankService.class);

    @Autowired private BankRepository repository;
    @Autowired private AccrualConfigService accrualConfigService;

    @Override
    public List<Bank> getAll() {
        Sort sort = new Sort(Sort.Direction.ASC,"kode");
        return repository.findAll(sort);
    }

    @Override
    public Page<Bank> getAllNonKas(int hal, int jumlah) {
        String kode = accrualConfigService.getConfig().getKodeKasTableBank();
        Sort sort = new Sort(Sort.Direction.ASC,"kode");
        PageRequest pageRequest = new PageRequest(hal-1, jumlah,sort);
        return repository.findAllNotKas(kode, pageRequest);
    }

    private Bank getByKode(String kode){
        Bank bank;
        Iterator<Bank> banks = repository.findByKode(kode).iterator();
        if(banks.hasNext()){
            bank=banks.next();
        }else{
            bank=null;
        }

        return  bank;
    }

    @Override
    public boolean isKodeExist(String kode) {
        Bank bank = new Bank();

        bank= getByKode(kode);
        boolean hasil;
        if(bank==null){
            hasil=false;
        }else{
           hasil=true;
        }
        return hasil;
    }

    @Override
    public Bank getById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public Page<Bank> getByNamaPage(String nama, int hal, int jumlah) {
        PageRequest pageRequest = new PageRequest(hal-1, jumlah, Sort.Direction.ASC,"nama");
        return repository.findByNama("%"+nama+"%",pageRequest);
    }

    @Override
    public Page<Bank> getByKodeNamaPage(String kode, String nama, int hal, int jumlah) {
        PageRequest pageRequest = new PageRequest(hal-1, jumlah, Sort.Direction.ASC,"nama");
        return repository.findByKodeNama("%" + kode + "%", "%" + nama + "%",pageRequest);
    }

    @Override
    public Bank save(Bank bank) {
        return repository.saveAndFlush(bank);
    }

    @Override
    public Bank update(Long idEdit, Bank bank) {

        Bank bankUpdate = repository.findOne(idEdit);
        bankUpdate.setNama(bank.getNama());
        //bankUpdate.setKode(bank.getKode());
        bankUpdate.setNamaCabangBank(bank.getNamaCabangBank());
        bankUpdate.setNoAccount(bank.getNoAccount());
        bankUpdate.setStatus(bank.getStatus());
        bankUpdate.setKota(bank.getKota());
        return repository.save(bankUpdate);
    }


}
