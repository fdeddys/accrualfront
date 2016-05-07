package com.ddabadi.service;

import com.ddabadi.domain.Direktorat;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by deddy on 4/25/16.
 */
public interface DirektoratService {

    public List<Direktorat> getAll(String urut);
    public Direktorat getById(Long id);
    public List<Direktorat> getByNama(String nama);
    public Direktorat save(Direktorat direktorat);
    public Direktorat update(Long idEdit, Direktorat direktorat);

}
