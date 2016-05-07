package com.ddabadi.domain;

import com.ddabadi.enumer.Status;

import javax.persistence.*;

/**
 * Created by deddy on 4/25/16.
 */
@Entity
@Table(name = "tb_customer",
        indexes = { @Index(name = "ix_nama", columnList = "nama"),
                    @Index(name = "ix_kode", columnList = "kode")})
public class Customer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nama", length = 100)
    private String nama;

    @Column(name = "kode", length = 100)
    private String kode;

    @Column(name = "alamat", length = 200)
    private String alamat;

    @Column(name = "kota", length = 100)
    private String kota;

    @Column(name = "telp", length = 30)
    private String telp;

    @Column(name = "fax", length = 30)
    private String fax;

    @Column(name = "npwp", length = 50)
    private String npwp;

    @Column(name = "nama_bank", length = 50)
    private String namaBank;

    @Column(name = "no_rekening", length = 50)
    private String noRekening;

    @Column(name = "nama_cabang_bank", length = 100)
    private String namaCabangBank;

    @Column(name = "kontak_person", length = 100)
    private String kontakPerson;

    @Column(name = "Status")
    private Status  status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }

    public String getNamaCabangBank() {
        return namaCabangBank;
    }

    public void setNamaCabangBank(String namaCabangBank) {
        this.namaCabangBank = namaCabangBank;
    }

    public String getKontakPerson() {
        return kontakPerson;
    }

    public void setKontakPerson(String kontakPerson) {
        this.kontakPerson = kontakPerson;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
