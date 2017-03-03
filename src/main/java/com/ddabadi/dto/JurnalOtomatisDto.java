package com.ddabadi.dto;

import com.ddabadi.domain.Bank;

import java.util.Date;

/**
 * Created by cue on 2/23/2017.
 */
public class JurnalOtomatisDto {
    private Long id;
    private String dibayar;
    private Long bank;
    private Long user;
    private String dk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDibayar() {
        return dibayar;
    }

    public void setDibayar(String dibayar) {
        this.dibayar = dibayar;
    }

    public Long getBank() {
        return bank;
    }

    public void setBank(Long bank) {
        this.bank = bank;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public String getDk() {
        return dk;
    }

    public void setDk(String dk) {
        this.dk = dk;
    }
}
