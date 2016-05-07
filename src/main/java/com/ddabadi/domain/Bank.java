package com.ddabadi.domain;

import com.ddabadi.enumer.Status;

import javax.persistence.*;

/**
 * Created by deddy on 4/25/16.
 */
@Entity
@Table(name="tb_bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Long id;

    @Column(name = "kode", length = 20)
    private String kode;

    @Column(name = "nama", length = 50)
    private String nama;

    @Column(name = "nama_cabang_bank",length = 100)
    private String namaCabangBank;

    @Column(name = "no_account", length = 50)
    private String noAccount;

    @Column(name = "status")
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNamaCabangBank() {
        return namaCabangBank;
    }

    public void setNamaCabangBank(String namaCabangBank) {
        this.namaCabangBank = namaCabangBank;
    }

    public String getNoAccount() {
        return noAccount;
    }

    public void setNoAccount(String noAccount) {
        this.noAccount = noAccount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
