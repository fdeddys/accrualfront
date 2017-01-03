package com.ddabadi.domain;

import javax.persistence.*;

/**
 * Created by deddy on 7/3/16.
 */
@Entity
@Table(name = "tb_idx_no_rel")
public class IdxNoRel {

    @Id
    @Column(name="id")
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "coa_dtl")
    private CoaDtl coaDtl;

    @Column(name = "bulan", length = 2)
    private String bulan;

    @Column(name = "tahun", length = 4)
    private String tahun;

    @Column(name = "urut")
    private Long urut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoaDtl getCoaDtl() {
        return coaDtl;
    }

    public void setCoaDtl(CoaDtl coaDtl) {
        this.coaDtl = coaDtl;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public Long getUrut() {
        return urut;
    }

    public void setUrut(Long urut) {
        this.urut = urut;
    }
}
