package com.ddabadi.service.impl;

import com.ddabadi.domain.IdxNoApproveSuratTansfer;
import com.ddabadi.domain.repository.IdxNoApproveSTRepository;
import com.ddabadi.service.IdxNoApproveSTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by deddy on 1/15/17.
 */

@Service
public class IdxNoApproveSTServiceImpl implements IdxNoApproveSTService {

    @Autowired private IdxNoApproveSTRepository repository;

    @Override
    public IdxNoApproveSuratTansfer save(IdxNoApproveSuratTansfer idxNoApproveSuratTansfer) {
        return repository.save(idxNoApproveSuratTansfer);
    }

    @Override
    public IdxNoApproveSuratTansfer getByBulanTahun(String bulan, String tahun) {
        IdxNoApproveSuratTansfer idx = repository.findByBulanAndTahun(bulan, tahun);
        return idx;
    }

}
