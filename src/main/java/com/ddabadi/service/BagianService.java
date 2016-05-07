package com.ddabadi.service;

import com.ddabadi.domain.Bagian;
import org.hibernate.mapping.Bag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by deddy on 4/25/16.
 */
public interface BagianService {

    public Page<Bagian> getByKodeByNamaPage(String kode, String nama,int hal, int jumlah);
    public Bagian getById(Long id);
    public Bagian save(Bagian bagian);
    public Bagian update(Long idEdit, Bagian bagian);

}