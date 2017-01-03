package com.ddabadi.dto;

/**
 * Created by deddy on 10/5/16.
 */
public class SuratTransferHdDto {

    private Long id;
//    private Long customerId;
    private Long bankId;
    private String tglSurat;
    private String noApprove;
    private String noCek;
    private String userUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getCustomerId() {
//        return customerId;
//    }
//
//    public void setCustomerId(Long customerId) {
//        this.customerId = customerId;
//    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getTglSurat() {
        return tglSurat;
    }

    public void setTglSurat(String tglSurat) {
        this.tglSurat = tglSurat;
    }

    public String getNoApprove() {
        return noApprove;
    }

    public void setNoApprove(String noApprove) {
        this.noApprove = noApprove;
    }

    public String getNoCek() {
        return noCek;
    }

    public void setNoCek(String noCek) {
        this.noCek = noCek;
    }

    public String getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }
}
