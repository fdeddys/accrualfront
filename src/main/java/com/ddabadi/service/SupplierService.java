package com.ddabadi.service;

import com.ddabadi.domain.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by deddy on 4/23/16.
 */
//@Repository
public interface SupplierService {

    public Supplier getById(Long id);
    public Page<Supplier> getByNamaPage(String nama, int hal, int jumlah);
    public Supplier save(Supplier supplier);
    public Supplier update(Long idEdit, Supplier supplier);


}
