package com.ddabadi.domain;

import javax.persistence.*;

/**
 * Created by deddy on 7/14/16.
 */

@Entity
@Table(name = "tb_laba_rugi_saldo")
public class LabaRugiSaldo {

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

    @Column(name = "jumlah")
    private Double total;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public Bagian getBagian() {
        return bagian;
    }

    public void setBagian(Bagian bagian) {
        this.bagian = bagian;
    }
}
