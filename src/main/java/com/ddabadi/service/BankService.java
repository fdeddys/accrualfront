package com.ddabadi.service;

import com.ddabadi.domain.Bank;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by deddy on 4/25/16.
 */
public interface BankService {

    public List<Bank> getAll();
    public Page<Bank> getAllNonKas(int hal, int jumlah);
    public boolean isKodeExist(String kode);
    public Bank getById(Long id);
    public Page<Bank> getByNamaPage(String nama, int hal, int jumlah);
    public Page<Bank> getByKodeNamaPage(String kode, String nama, int hal, int jumlah);
    public Bank save(Bank bank);
    public Bank update(Long idEdit, Bank bank);

}
