package com.ddabadi.domain;

import javax.persistence.*;

/**
 * Created by deddy on 7/8/16.
 */

@Entity
@Table(name = "tb_buku_besar_saldo")
public class BukuBesarSaldo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "bulanTahun", length = 6)
    private String bulanTahun;

    @OneToOne
    @JoinColumn(name = "coa")
    private CoaDtl coaDtl;

    @Column(name = "nilai")
    private Double nilai;

    @OneToOne
    @JoinColumn(name = "bank")
    private Bank bank;

    @Column(name = "rel")
    private String rel;

    @OneToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBulanTahun() {
        return bulanTahun;
    }

    public void setBulanTahun(String bulanTahun) {
        this.bulanTahun = bulanTahun;
    }

    public CoaDtl getCoaDtl() {
        return coaDtl;
    }

    public void setCoaDtl(CoaDtl coaDtl) {
        this.coaDtl = coaDtl;
    }

    public Double getNilai() {
        return nilai;
    }

    public void setNilai(Double nilai) {
        this.nilai = nilai;
    }
}
