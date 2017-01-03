package com.ddabadi.service;

import com.ddabadi.domain.LabaRugi;
import org.springframework.data.domain.Page;

/**
 * Created by deddy on 7/14/16.
 */
public interface LabaRugiService {

    LabaRugi save(LabaRugi labaRugi);
    public void prosesBulanan(boolean trial);

    public void deleteBulanan(String bulanTahun);

//    Page<LabaRugi> findByBulanTahunBerjalanKeterangan(String bulanTahun, String keterangan, int hal, int jumlah);
//    Page<LabaRugi> findByBulanTahunBerjalanKeteranganCoaDtl(String bulanTahun, String keterangan, Long idCoaDtl, int hal, int jumlah);

}
