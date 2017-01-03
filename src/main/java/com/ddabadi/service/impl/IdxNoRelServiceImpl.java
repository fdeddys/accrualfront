package com.ddabadi.service.impl;

import com.ddabadi.domain.CoaDtl;
import com.ddabadi.domain.IdxNoRel;
import com.ddabadi.domain.repository.IdxNoRelRepository;
import com.ddabadi.service.IdxNoRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by deddy on 7/3/16.
 */
@Service
public class IdxNoRelServiceImpl implements IdxNoRelService {

    @Autowired private IdxNoRelRepository idxNoRelRepository;

    @Override
    public IdxNoRel save(IdxNoRel idxNoRel) {
        return idxNoRelRepository.saveAndFlush(idxNoRel);
    }

    @Override
    public IdxNoRel getByCoaDtlBulanTahun(CoaDtl coaDtl, String bulan, String tahun) {
        return idxNoRelRepository.findByCoaDtlAndBulanAndTahun(coaDtl, bulan, tahun);
    }
}
