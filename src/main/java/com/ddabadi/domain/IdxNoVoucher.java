package com.ddabadi.domain;

import com.ddabadi.enumer.JenisVoucher;

import javax.persistence.*;

/**
 * Created by deddy on 5/15/16.
 */

@Entity
@Table(name = "tb_idx_no_voucher")
public class IdxNoVoucher {

    @Id
    @Column(name="id")
    @GeneratedValue
    private Long id;

    //jenisVoucher bulan tahun  noUrut

    @Column(name = "jenis_voucher")
    private JenisVoucher jenisVoucher;

    @Column(name = "bulan", length = 2)
    private String bulan;

    @Column(name = "tahun", length = 4)
    private String tahun;

    @Column(name = "urut")
    private Long urut;

    public Long getUrut() {
        return urut;
    }

    public void setUrut(Long urut) {
        this.urut = urut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JenisVoucher getJenisVoucher() {
        return jenisVoucher;
    }

    public void setJenisVoucher(JenisVoucher jenisVoucher) {
        this.jenisVoucher = jenisVoucher;
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
}
