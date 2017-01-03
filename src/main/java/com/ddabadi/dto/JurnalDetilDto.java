package com.ddabadi.dto;

/**
 * Created by deddy on 5/8/16.
 */
public class JurnalDetilDto {



    // if id = 0 -> add new
    // else
    //    id = jurnalDetil.id
    private Long id;
    private Long jurnalHeaderId;
    private Long accountDetilId;

    // string 6000
    private String bagian;
    private String keterangan;
    private Double total;

    //D atau K
    private String dk;
    private String rel;

    // jika = "-" maka server -> null
    // else
    //    diisi cust ID
    private Long customerId;

    // jika = "-" maka server -> null
    // else
    //    diisi bank ID
    private Long bankId;

    private Long userId;

    // GETTER - SETTER


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getJurnalHeaderId() {
        return jurnalHeaderId;
    }

    public void setJurnalHeaderId(Long jurnalHeaderId) {
        this.jurnalHeaderId = jurnalHeaderId;
    }

    public Long getAccountDetilId() {
        return accountDetilId;
    }

    public void setAccountDetilId(Long accountDetilId) {
        this.accountDetilId = accountDetilId;
    }

    public String getBagian() {
        return bagian;
    }

    public void setBagian(String bagian) {
        this.bagian = bagian;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getDk() {
        return dk;
    }

    public void setDk(String dk) {
        this.dk = dk;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
}
