package com.ddabadi.service.impl;

import com.ddabadi.domain.Direktorat;
import com.ddabadi.domain.repository.DirektoratRepository;
import com.ddabadi.service.DirektoratService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by deddy on 4/29/16.
 */
@Service

public class DirektoratServiceImpl implements DirektoratService {

    @Autowired private DirektoratRepository repository;

    private Logger logger = Logger.getLogger(DirektoratService.class);

    @Override
    public List<Direktorat> getAll(String urut) {
        logger.info("getall");

        Sort sort = new Sort(Sort.Direction.ASC,urut.trim());

        return repository.findAll(sort);
    }

    @Override
    public Direktorat getById(Long id) {
        logger.info("get by id [ "+id.toString()+" ]");
        return repository.findOne(id);
    }

    @Override
    public List<Direktorat> getByNama(String nama) {
        logger.info("get by nama [ "+ nama +" ]");
        return repository.findByNama("%"+nama+"%");
    }

    @Override
    public Direktorat save(Direktorat direktorat) {
        logger.info("save");
        return repository.saveAndFlush(direktorat);
    }

    @Override
    public Direktorat update(Long idEdit, Direktorat direktorat) {
        logger.info("update id [ "+idEdit.toString()+" ]");
        Direktorat direktoratEdit = repository.findOne(idEdit);
        direktoratEdit.setNama(direktorat.getNama());
        direktoratEdit.setKode(direktorat.getKode());
        return repository.saveAndFlush(direktoratEdit);
    }

}
