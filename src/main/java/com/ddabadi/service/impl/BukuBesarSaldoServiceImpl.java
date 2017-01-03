package com.ddabadi.service.impl;

import com.ddabadi.domain.BukuBesarSaldo;
import com.ddabadi.domain.CoaDtl;
import com.ddabadi.domain.repository.BukuBesarSaldoRepository;
import com.ddabadi.service.BukuBesarSaldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by deddy on 7/8/16.
 */

@Service
public class BukuBesarSaldoServiceImpl implements BukuBesarSaldoService {

    @Autowired
    private BukuBesarSaldoRepository repository;

    @Override
    public BukuBesarSaldo save(BukuBesarSaldo bukuBesarSaldo) {
        return repository.saveAndFlush(bukuBesarSaldo);
    }

    @Override
    public List<BukuBesarSaldo> findByBulanTahun(String bulanTahun) {
        return repository.getByBulanTahun(bulanTahun);
    }

    @Override
    public Double findSaldoByCoaBulanTahun(CoaDtl coaDtl, String bulanTahun) {
        BukuBesarSaldo bukuBesarSaldo =repository.getByCoaDtlAndBulanTahun(coaDtl, bulanTahun);
        if(bukuBesarSaldo==null){
            return 0D;
        }else{
            return bukuBesarSaldo.getNilai();
        }
    }

    @Override
    public void deleteByBulanTahun(String bulanTahun) {
        repository.deleteByBulanTahun(bulanTahun);
    }

    @Override
    public BukuBesarSaldo getByBulanTahunAndCoaDtlAndBankKodeLikeAndRelLikeAndCustomerKodeLike(String blnThn, CoaDtl coaDtl, String kodeBank, String rel, String kodeCust) {
        return repository.findByBulanTahunAndCoaDtlAndBankKodeLikeAndRelLikeAndCustomerKodeLike(blnThn, coaDtl, kodeBank, rel, kodeCust);
    }


}
