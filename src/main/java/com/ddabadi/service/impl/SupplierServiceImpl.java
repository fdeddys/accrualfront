package com.ddabadi.service.impl;

import com.ddabadi.domain.Supplier;
import com.ddabadi.domain.repository.SupplierRepository;
import com.ddabadi.service.SupplierService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by deddy on 4/23/16.
 */

@Service
public class SupplierServiceImpl implements SupplierService {

    private Logger logger = Logger.getLogger(SupplierService.class);
    @Autowired private SupplierRepository repository;

    @Override
    public Supplier getById(Long id) {
        logger.info("find One id = " + id.toString());
        return repository.findOne(id);
    }

    @Override
    public Page<Supplier> getByNamaPage(String nama , int hal, int jumlah) {

        String cariNama = nama;
        Page<Supplier> pages;
        PageRequest pageRequest = new PageRequest(hal -1, jumlah, Sort.Direction.ASC,"nama");
        if(nama.equals("--")||nama.equals("")){
            logger.info("find all page  " );
            pages= repository.findAllPage(pageRequest);
        }else   {
            logger.info("find by nama page [" + nama  + "] " );
            pages = repository.findByNamaPage('%'+nama+'%',pageRequest);
        }

        return pages;
    }


    @Override
    public Supplier save(Supplier supplier) {
        logger.info("Save");
        return repository.save(supplier);
    }

    @Override
    public Supplier update(Long idEdit, Supplier supplier) {
        logger.info("update id [" + idEdit.toString() + "]");
        Supplier supplierUpdate = repository.findOne(idEdit);
        Supplier supplierHasil=null;
        if(supplierUpdate==null){
            ;
        }else {
            supplierUpdate.setNama(supplier.getNama());
            supplierHasil= repository.saveAndFlush(supplierUpdate);
        }
        return  supplierHasil;
    }

    ;
}
