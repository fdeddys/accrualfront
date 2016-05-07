package com.ddabadi.service;

import com.ddabadi.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by deddy on 4/25/16.
 */
public interface UserService {

    public List<User> getAll();
    public Page<User> getAllPage(String urut, int hal, int jumlah);
    public Page<User> getByNama(String nama, int hal, int jumlah);
    public User getOneByNama(String nama);
    public Boolean getAuthByUserPass(String nama, String password);
    public User getById(Long id);
    public User save(User user);
    public User update(Long idEdit, User user);

}
