package com.ddabadi.service.impl;

import com.ddabadi.domain.LabaRugi;
import com.ddabadi.domain.LabaRugiSaldo;
import com.ddabadi.domain.repository.LabaRugiSaldoRepository;
import com.ddabadi.service.LabaRugiSaldoService;
import com.ddabadi.service.LabaRugiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by deddy on 7/14/16.
 */

@Service
public class LabaRugiSaldoServiceImpl implements LabaRugiSaldoService {

    @Autowired private LabaRugiSaldoRepository labaRugiSaldoRepository;


    @Override
    public LabaRugiSaldo save(LabaRugiSaldo labaRugiSaldo) {
        return labaRugiSaldoRepository.save(labaRugiSaldo);
    }

    @Override
    public List<LabaRugiSaldo> findByBulanTahun(String bulanTahun) {
        return labaRugiSaldoRepository.getByBulanTahun(bulanTahun);
    }


    @Override
    public void deleteByBulanTahun(String bulanTahun) {
        labaRugiSaldoRepository.deleteByBulanTahun(bulanTahun);
    }
}
