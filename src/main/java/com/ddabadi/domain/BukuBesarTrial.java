package com.ddabadi.domain;

import com.ddabadi.enumer.GroupAccount;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by deddy on 12/17/16.
 */

@Entity
@Table(name = "tb_buku_besar_trial")
public class BukuBesarTrial  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "bulanTahun", length = 6)
    private String bulanTahun;

    @OneToOne
    @JoinColumn(name = "coa")
    private CoaDtl coaDtl;

    @OneToOne
    @JoinColumn(name = "bagian")
    private Bagian bagian;

    @Column(name = "id_jurnal_detil")
    private Long jurnalDetilId;

    @Column(name = "group_coa")
    private GroupAccount groupAccount;

    @Column(name = "total_Debet")
    private Double totalDebet;

    @Column(name = "total_kredit")
    private Double totalKredit;

    @Column(name = "total_berjalan")
    private Double total_berjalan;

    @Column(name="keterangan")
    private String keterangan;

    @OneToOne
    @JoinColumn(name = "bank")
    private Bank bank;

    @Column(name = "id_bank")
    private Long idBank;

    @Column(name = "rel")
    private String rel;

    @OneToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @Column(name = "id_customer")
    private Long idCustomer;

    @Temporal(TemporalType.DATE)
    @Column(name = "tgl_Booking")
    private Date tglBooking;

    @Column(name = "no_voucher")
    private String noVoucher;

    @Column(name = "no_urut")
    private String noUrut;

    @Column(name = "penerima")
    private String penerima;

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }

    public Date getTglBooking() {
        return tglBooking;
    }

    public void setTglBooking(Date tglBooking) {
        this.tglBooking = tglBooking;
    }

    public String getNoVoucher() {
        return noVoucher;
    }

    public void setNoVoucher(String noVoucher) {
        this.noVoucher = noVoucher;
    }

    public String getNoUrut() {
        return noUrut;
    }

    public void setNoUrut(String noUrut) {
        this.noUrut = noUrut;
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

    public Long getIdBank() {
        return idBank;
    }

    public void setIdBank(Long idBank) {
        this.idBank = idBank;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public void setCoaDtl(CoaDtl coaDtl) {
        this.coaDtl = coaDtl;
    }

    public Bagian getBagian() {
        return bagian;
    }

    public void setBagian(Bagian bagian) {
        this.bagian = bagian;
    }

    public Long getJurnalDetilId() {
        return jurnalDetilId;
    }

    public void setJurnalDetilId(Long jurnalDetilId) {
        this.jurnalDetilId = jurnalDetilId;
    }

    public GroupAccount getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(GroupAccount groupAccount) {
        this.groupAccount = groupAccount;
    }

    public Double getTotalDebet() {
        return totalDebet;
    }

    public void setTotalDebet(Double totalDebet) {
        this.totalDebet = totalDebet;
    }

    public Double getTotalKredit() {
        return totalKredit;
    }

    public void setTotalKredit(Double totalKredit) {
        this.totalKredit = totalKredit;
    }

    public Double getTotal_berjalan() {
        return total_berjalan;
    }

    public void setTotal_berjalan(Double total_berjalan) {
        this.total_berjalan = total_berjalan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
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
}
