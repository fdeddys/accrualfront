package com.ddabadi.domain;

import com.itextpdf.text.io.StreamUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by deddy on 10/3/16.
 */

@Entity
@Table(name = "tb_surat_transfer_hd",
       indexes = @Index(name = "ix_no_approve", columnList = "no_approve"))
public class SuratTransferHd {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "customer")
//    private Customer customer;

    @OneToOne
    @JoinColumn(name = "bank")
    private Bank bank;

    @Temporal(TemporalType.DATE)
    @Column(name = "tgl_surat")
    private Date tglSurat;

    @Column(name = "no_approve", length = 50)
    private String noApprove;

    @Column(name = "no_cek", length = 50)
    private String noCek;

    @Column(name = "is_approve")
    private Boolean isApprove;

    @Column(name = "user_update", length = 100)
    private String userUpdate;

    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @Column(name = "total")
    private Double total;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public SuratTransferHd() {
        this.lastUpdate = new Date();
        this.isApprove=false;
        this.total=0D;
    }

    public String getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Date getTglSurat() {
        return tglSurat;
    }

    public void setTglSurat(Date tglSurat) {
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


    public Boolean getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(Boolean isApprove) {
        this.isApprove = isApprove;
    }
}
