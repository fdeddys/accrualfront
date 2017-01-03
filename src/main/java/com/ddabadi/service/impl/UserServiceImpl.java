package com.ddabadi.service.impl;

import com.ddabadi.domain.User;
import com.ddabadi.domain.repository.UserRepository;
import com.ddabadi.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Created by deddy on 4/29/16.
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired private UserRepository repository;

    private Logger logger = Logger.getLogger(UserService.class);

    @Override
    public List<User> getAll() {
        logger.info("getall");
        return repository.findAll();
    }

    @Override
    public Page<User> getAllPage(String urut, int hal, int jumlah) {
        PageRequest pageRequest = new PageRequest(hal-1,jumlah, Sort.Direction.ASC,urut);
        return repository.findAll(pageRequest);
    }

    @Override
    public Page<User> getByNama(String nama, int hal, int jumlah) {
        logger.info("get by nama [ "+nama+" ] page");

        PageRequest pageRequest=new PageRequest(hal-1, jumlah, Sort.Direction.ASC,"nama");
        return  repository.findByNama("%"+nama+"%", pageRequest);
    }

    @Override
    public User getOneByNama(String nama) {

        Iterator<User> users = repository.findOneByNama(nama).iterator();
        User user=null;
        if(users.hasNext()){
            user = users.next();
        }
        return user;
    }

    @Override
    public Boolean getAuthByUserPass(String nama, String password) {
        Iterator<User> users = repository.findOneByNama(nama).iterator();
        User user=null;
        Boolean isSuccess = Boolean.FALSE;
        if(users.hasNext()){
            user = users.next();
            if(user.getPassword().equals(password)){
                // auth success
                logger.info("login success [" + user.getNama() + "]");
                isSuccess=true;
            }else{
                user=null;
            }
        }
        return isSuccess;
    }

    @Override
    public User getById(Long id) {
        logger.info("get by id [ "+id.toString()+" ]");
        return repository.findOne(id);
    }

    @Override
    public User save(User user) {
        logger.info("save");
        return repository.saveAndFlush(user);
    }

    @Override
    public User update(Long idEdit, User user) {
        logger.info("Edit id [ " + idEdit.toString() + " ]");
        User userEdit = repository.findOne(idEdit);
        userEdit.setNama(user.getNama());
        userEdit.setInitial(user.getInitial());
        userEdit.setIsAdmin(user.getIsAdmin());
        userEdit.setPassword(user.getPassword());
        userEdit.setStatus(user.getStatus());
        return repository.saveAndFlush(userEdit);
    }

    @Override
    public Boolean isAdmin(String nama) {
        Boolean hasil = false;
        Iterator<User> users = repository.findOneByNama(nama).iterator();
        if (users.hasNext()){
            User user = users.next();
            hasil = user.getIsAdmin();
        }

        return hasil;
    }
}
