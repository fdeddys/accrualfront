package com.ddabadi.service.impl;

import com.ddabadi.domain.IdxNoUrut;
import com.ddabadi.domain.repository.IdxNoUrutRepository;
import com.ddabadi.service.IdxNoUrutService;
import org.apache.log4j.Logger;
import org.hibernate.jpa.criteria.expression.SelectionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by deddy on 5/15/16.
 */
@Service
public class IdxNoUrutServiceImpl implements IdxNoUrutService {

    @Autowired private IdxNoUrutRepository repository;
    private Logger logger= Logger.getLogger(IdxNoUrutService.class);

    @Override
    public IdxNoUrut save(IdxNoUrut idxNoUrut) {
        return repository.saveAndFlush(idxNoUrut);
    }

    @Override
    public IdxNoUrut getByIdUserTahun(Long cariIdUser, String tahun) {

        //return repository.findByIdUserTahun(cariIdUser,tahun);
        return repository.findByIdUserAndTahun(cariIdUser, tahun);
    }
}
