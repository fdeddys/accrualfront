package com.ddabadi.service;

import com.ddabadi.domain.IdxNoVoucher;
import com.ddabadi.enumer.JenisVoucher;

/**
 * Created by deddy on 5/15/16.
 */
public interface IdxNoVoucherService {

    IdxNoVoucher save(IdxNoVoucher idxNoVoucher);
    IdxNoVoucher getByJenisVoucherBulanTahun(JenisVoucher jenisVoucher, String bulan, String tahun);

}
