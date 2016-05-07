package com.ddabadi.service;

import com.ddabadi.domain.CoaDtl;
import org.springframework.data.domain.Page;

/**
 * Created by deddy on 5/3/16.
 */
public interface CoaDtlService {

    Page<CoaDtl> getByKodeByNamaPage(String kode, String nama, int hal, int jumlah);
    CoaDtl getById(Long id);
    boolean isKodeExis(String kode);
    CoaDtl save(CoaDtl coaDtl);
    CoaDtl update(Long id,CoaDtl coaDtl);

}
