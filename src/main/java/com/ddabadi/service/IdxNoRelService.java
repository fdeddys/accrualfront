package com.ddabadi.service;


import com.ddabadi.domain.CoaDtl;
import com.ddabadi.domain.IdxNoRel;
import com.ddabadi.enumer.JenisVoucher;

/**
 * Created by deddy on 7/3/16.
 */
public interface IdxNoRelService {
    IdxNoRel save(IdxNoRel idxNoRel);
    IdxNoRel getByCoaDtlBulanTahun(CoaDtl coaDtl, String bulan, String tahun);

}
