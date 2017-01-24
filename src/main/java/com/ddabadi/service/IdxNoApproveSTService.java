package com.ddabadi.service;

import com.ddabadi.domain.IdxNoApproveSuratTansfer;

/**
 * Created by deddy on 1/15/17.
 */
public interface IdxNoApproveSTService {

    public IdxNoApproveSuratTansfer save(IdxNoApproveSuratTansfer idxNoApproveSuratTansfer);
    public IdxNoApproveSuratTansfer getByBulanTahun(String bulan, String tahun);
}
