package com.ddabadi.domain;

import com.ddabadi.enumer.DebetKredit;
import com.ddabadi.enumer.JenisVoucher;

import javax.persistence.*;

/**
 * Created by deddy on 5/6/16.
 */

@Entity
@Table(name = "tb_jurnal_detil")
public class JurnalDetil {

    @Id
    @Column(name="id")
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jurnalHeader")
    private JurnalHeader jurnalHeader;

    @OneToOne
    @JoinColumn(name = "account_detil")
    private CoaDtl  accountDetil;

    @OneToOne
    @JoinColumn(name = "bagian")
    private Bagian bagian;

    @Column(name = "keterangan")
    private String keterangan;

    @Column(name = "debet")
    private Double debet;

    @Column(name = "kredit")
    private Double kredit;

    @OneToOne
    @JoinColumn(name = "bank")
    private Bank bank;

    @Column(name = "rel")
    private String rel;

    @OneToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @Column(name = "d_k")
    private DebetKredit dk;

    @Column(name = "jumlah")
    private Double jumlah;

    @Column(name = "tipe_voucher")
    private JenisVoucher tipeVoucher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JurnalHeader getJurnalHeader() {
        return jurnalHeader;
    }

    public void setJurnalHeader(JurnalHeader jurnalHeader) {
        this.jurnalHeader = jurnalHeader;
    }

    public CoaDtl getAccountDetil() {
        return accountDetil;
    }

    public void setAccountDetil(CoaDtl accountDetil) {
        this.accountDetil = accountDetil;
    }

    public Bagian getBagian() {
        return bagian;
    }

    public void setBagian(Bagian bagian) {
        this.bagian = bagian;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Double getDebet() {
        return debet;
    }

    public void setDebet(Double debet) {
        this.debet = debet;
    }

    public Double getKredit() {
        return kredit;
    }

    public void setKredit(Double kredit) {
        this.kredit = kredit;
    }

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

    public DebetKredit getDk() {
        return dk;
    }

    public void setDk(DebetKredit dk) {
        this.dk = dk;
    }

    public Double getJumlah() {
        return jumlah;
    }

    public void setJumlah(Double jumlah) {
        this.jumlah = jumlah;
    }

    public JenisVoucher getTipeVoucher() {
        return tipeVoucher;
    }

    public void setTipeVoucher(JenisVoucher tipeVoucher) {
        this.tipeVoucher = tipeVoucher;
    }
}
