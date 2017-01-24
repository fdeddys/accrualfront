package com.ddabadi.service;

import com.ddabadi.domain.IdxNoPiutangUsaha;

/**
 * Created by deddy on 1/20/17.
 */
public interface IdxNoPiutangUsahaService {
    public IdxNoPiutangUsaha save(IdxNoPiutangUsaha idxNoPiutangUsaha);
    public IdxNoPiutangUsaha getByBulanTahun(String bulan, String tahun);
}
