package com.ddabadi.service;

import com.ddabadi.domain.BukuBesarSaldo;
import com.ddabadi.domain.CoaDtl;

import java.util.List;

/**
 * Created by deddy on 7/8/16.
 */
public interface BukuBesarSaldoService {

    BukuBesarSaldo save(BukuBesarSaldo bukuBesarSaldo);
    List<BukuBesarSaldo> findByBulanTahun(String bulanTahun);
    Double findSaldoByCoaBulanTahun(CoaDtl coaDtl, String bulanTahun);
    public void deleteByBulanTahun(String bulanTahun);
    BukuBesarSaldo getByBulanTahunAndCoaDtlAndBankKodeLikeAndRelLikeAndCustomerKodeLike(String blnThn,
                                                                                        CoaDtl coaDtl,
                                                                                        String kodeBank,
                                                                                        String rel,
                                                                                        String kodeCust);
}
