package com.ddabadi.service.impl;

import com.ddabadi.domain.Bagian;
import com.ddabadi.domain.repository.BagianRepository;
import com.ddabadi.enumer.Status;
import com.ddabadi.service.BagianService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by deddy on 4/28/16.
 */
@Service
public class BagianServiceImpl implements BagianService {

    private Logger logger = Logger.getLogger(BagianService.class);

    @Autowired private BagianRepository repository;

    @Override
    public Page<Bagian> getByKodeByNamaPage(String kode, String nama, int hal, int jumlah) {
        PageRequest pageRequest = new PageRequest(hal-1,jumlah, Sort.Direction.ASC,"kode");
        //return repository.findByNamaKodePage('%'+nama+'%','%'+kode+'%',pageRequest);
        return repository.findByNamaLikeAndKodeLike('%'+nama+'%',kode+'%',pageRequest);
    }

    @Override
    public Page<Bagian> getByKodeByNamaAktifPage(String kode, String nama, int hal, int jumlah) {
        PageRequest pageRequest = new PageRequest(hal-1,jumlah, Sort.Direction.ASC,"kode");
        //return repository.findByNamaKodePage('%'+nama+'%','%'+kode+'%',pageRequest);
        return repository.findByNamaLikeAndKodeLikeAndStatus('%' + nama + '%', kode + '%', Status.ACTIVE, pageRequest);
    }

    @Override
    public Bagian getById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public Bagian save(Bagian bagian) {
        return repository.saveAndFlush(bagian);
    }

    @Override
    public Bagian update(Long idEdit, Bagian bagian) {
        Bagian bagianEdit = repository.findOne(idEdit);
        bagianEdit.setNama(bagian.getNama());
        bagianEdit.setStatus(bagian.getStatus());
        //bagianEdit.setKode(bagian.getKode());
        bagianEdit.setDirektorat(bagian.getDirektorat());
        return repository.saveAndFlush(bagianEdit);
    }

    @Override
    public Bagian getByKode(String kode) {
        Bagian bagian = null;
        List<Bagian> bagians = repository.findByKode(kode);
        if(bagians.size()==0){
            // bagian tidak ada
        }else{
            bagian = bagians.iterator().next();
        }
        return bagian;
    }

    @Override
    public Boolean isKodeExist(String kode) {
        Bagian bagian = this.getByKode(kode);
        if(bagian==null){
            return false;
        }else{
            return true;
        }

    }

    @Override
    public List<Bagian> getAll() {
        Sort sort = new Sort(Sort.Direction.ASC,"kode");
        return repository.findAll(sort);
    }
}
