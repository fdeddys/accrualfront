package com.ddabadi.service;

import com.ddabadi.domain.CoaHdr;
import org.jfree.chart.LegendItemSource;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by deddy on 5/3/16.
 */
public interface CoaHdrService {

    Page<CoaHdr> getByKodeByNamaPage(String kode, String nama, int hal, int jumlah);
    CoaHdr getById(Long id);
    boolean isKodeExis(String kode);
    CoaHdr save(CoaHdr coaHdr);
    CoaHdr update(Long id,CoaHdr coaHdr);
    String getBagianById(Long id);



}
