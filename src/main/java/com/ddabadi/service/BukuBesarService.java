package com.ddabadi.service;

import com.ddabadi.domain.BukuBesar;
import com.ddabadi.domain.CoaDtl;
import org.springframework.data.domain.Page;

/**
 * Created by deddy on 7/8/16.
 */
public interface BukuBesarService {

    BukuBesar save(BukuBesar bukuBesar);
    public void prosesBulanan(boolean trial);

    public void deleteBulanan(String bulanTahun);

    //proses bulanan
    String getTglProsesBulanan();
    String getTglProsesBulanLalu();

    Page<BukuBesar> findByBulanTahunBerjalanKeterangan(String bulanTahun, String keterangan, int hal, int jumlah);
    Page<BukuBesar> findByBulanTahunBerjalanKeteranganCoaDtl(String bulanTahun, String keterangan, Long idCoaDtl, int hal, int jumlah);

}
