package com.ddabadi.service;

import com.ddabadi.domain.CoaDtl;
import com.ddabadi.domain.LabaRugiSaldo;

import java.util.List;

/**
 * Created by deddy on 7/14/16.
 */
public interface LabaRugiSaldoService {

    LabaRugiSaldo save(LabaRugiSaldo labaRugiSaldo);
    List<LabaRugiSaldo> findByBulanTahun(String bulanTahun);
    public void deleteByBulanTahun(String bulanTahun);

}
