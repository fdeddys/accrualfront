package com.ddabadi.domain;

import com.ddabadi.enumer.GroupAccount;

import javax.persistence.*;

/**
 * Created by deddy on 7/8/16.
 */

@Entity
@Table(name = "tb_buku_besar")
public class BukuBesar {

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
    @JoinColumn(name = "id_jurnal_detil")
    private JurnalDetil jurnalDetil;

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

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Double getTotal_berjalan() {
        return total_berjalan;
    }

    public void setTotal_berjalan(Double total_berjalan) {
        this.total_berjalan = total_berjalan;
    }

    public GroupAccount getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(GroupAccount groupAccount) {
        this.groupAccount = groupAccount;
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

    public JurnalDetil getJurnalDetil() {
        return jurnalDetil;
    }

    public void setJurnalDetil(JurnalDetil jurnalDetil) {
        this.jurnalDetil = jurnalDetil;
    }
}
