package com.ddabadi.service;

import com.ddabadi.domain.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by deddy on 4/25/16.
 */
public interface CustomerService {

    public List<Customer> getAll();
    public Page<Customer> getByKodeByNamaPage(String kode, String nama, int hal, int jumlah);
    public Customer getByKode(String kode);
    public List<Customer> getListByKode(String kode);
    public Customer getById(Long id);
    public Boolean isKodeExist(String kode);
    public Customer save(Customer customer);
    public Customer update(Long idEdit, Customer customer);

    public Page<Customer> getByNamaActivePage(String nama, int hal, int jumlah);

}
