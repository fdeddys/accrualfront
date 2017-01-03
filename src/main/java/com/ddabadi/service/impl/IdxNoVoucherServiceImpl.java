package com.ddabadi.service.impl;

import com.ddabadi.domain.IdxNoVoucher;
import com.ddabadi.domain.repository.IdxNoUrutRepository;
import com.ddabadi.domain.repository.IdxNoVoucherRepository;
import com.ddabadi.enumer.JenisVoucher;
import com.ddabadi.service.IdxNoVoucherService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by deddy on 5/20/16.
 */

@Service
public class IdxNoVoucherServiceImpl implements IdxNoVoucherService {

    @Autowired private IdxNoVoucherRepository repository;
    private Logger logger = Logger.getLogger(IdxNoVoucherService.class);


    @Override
    public IdxNoVoucher save(IdxNoVoucher idxNoVoucher) {
        logger.info("Save idx No Voucher");
        return repository.save(idxNoVoucher);
    }

    @Override
    public IdxNoVoucher getByJenisVoucherBulanTahun(JenisVoucher jenisVoucher, String bulan, String tahun) {
        logger.info("get idx No Voucher bulan = " + bulan + " tahun " + tahun);
        return repository.findByJenisVoucherAndBulanAndTahun(jenisVoucher, bulan, tahun);
    }
}
