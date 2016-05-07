package com.ddabadi.service.impl;

import com.ddabadi.domain.Customer;
import com.ddabadi.domain.repository.CustomerRepository;
import com.ddabadi.service.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by deddy on 4/26/16.
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    private Logger logger= Logger.getLogger(CustomerService.class);

    @Autowired private CustomerRepository repository;

    @Override
    public List<Customer> getAll() {
        logger.info("get all");
        return repository.findAll();
    }

    @Override
    public Page<Customer> getByKodeByNamaPage(String kode, String nama, int hal, int jumlah) {
        logger.info("get by kode, nama Page " );
        PageRequest pageRequest= new PageRequest(hal-1, jumlah, Sort.Direction.ASC,"nama");
        return repository.findByNamaKodePage("%"+nama+"%","%"+kode+"%",pageRequest);
    }

    @Override
    public Customer getByKode(String kode) {
        PageRequest pageRequest= new PageRequest(0, 1, Sort.Direction.ASC,"kode");
        Customer customer = repository.findByNamaKodePage("%","%"+kode+"%",pageRequest).iterator().next();
        return  customer;
    }

    @Override
    public List<Customer> getListByKode(String kode) {
        PageRequest pageRequest= new PageRequest(0, 1000, Sort.Direction.ASC,"kode");
        List<Customer> customers = repository.findByNamaKodeList("%","%"+kode+"%");
        return  customers;
    }

    @Override
    public Customer getById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public Boolean isKodeExist(String kode) {
        PageRequest pageRequest= new PageRequest(0, 1, Sort.Direction.ASC,"kode");
        return repository.findByNamaKodePage("%","%"+kode+"%",pageRequest).iterator().hasNext();
    }

    @Override
    public Customer save(Customer customer) {
        return repository.saveAndFlush(customer);
    }

    @Override
    public Customer update(Long idEdit, Customer customer) {
        Customer customerEdit = repository.findOne(idEdit);
        customerEdit.setNama(customer.getNama());
        customerEdit.setStatus(customer.getStatus());
        customerEdit.setAlamat(customer.getAlamat());
        customerEdit.setKode(customer.getKode());
        customerEdit.setNamaCabangBank(customer.getNamaCabangBank());
        customerEdit.setFax(customer.getFax());
        customerEdit.setKontakPerson(customer.getKontakPerson());
        customerEdit.setKota(customer.getKota());
        customerEdit.setNamaBank(customer.getNamaBank());
        customerEdit.setNoRekening(customer.getNoRekening());
        customerEdit.setNpwp(customer.getNpwp());
        customerEdit.setKode(customer.getKode());
        customerEdit.setTelp(customer.getTelp());

        return repository.saveAndFlush(customerEdit);
    }


}
