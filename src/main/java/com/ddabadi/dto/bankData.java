package com.ddabadi.dto;

import com.ddabadi.domain.Bank;

/**
 * Created by deddy on 4/29/16.
 */
public class bankData {
    private Bank bank;
    private String authData ;

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getAuthData() {
        return authData;
    }

    public void setAuthData(String authData) {
        this.authData = authData;
    }
}
