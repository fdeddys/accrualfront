package com.ddabadi.service.impl;

import com.ddabadi.domain.IdxNoPiutangUsaha;
import com.ddabadi.domain.repository.IdxNoPiutangUsahaRepository;
import com.ddabadi.service.IdxNoPiutangUsahaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by deddy on 1/20/17.
 */

@Service
public class IdxNoPiutangUsahaServiceImpl implements IdxNoPiutangUsahaService {

    @Autowired
    private IdxNoPiutangUsahaRepository repository;

    @Override
    public IdxNoPiutangUsaha save(IdxNoPiutangUsaha idxNoPiutangUsaha) {
        return repository.save(idxNoPiutangUsaha);
    }

    @Override
    public IdxNoPiutangUsaha getByBulanTahun(String bulan, String tahun) {
        return repository.findByBulanAndTahun(bulan, tahun);
    }
}
