package com.ddabadi.domain;

import javax.persistence.*;

/**
 * Created by deddy on 5/6/16.
 */

@Entity
@Table(name = "tb_accural_config")
public class AccrualConfig {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "header_penerimaan", length = 10)
    private String headerPenerimaan;

    @Column(name = "header_pembayaran", length = 10)
    private String headerPembayaran;

    @Column(name = "header_pemindahan", length = 10)
    private String headerPemindahan;

    @Column(name = "coa_bank", length = 10)
    private String coaBank;

    @Column(name = "coa_kas", length = 10)
    private String coaKas;

    @Column(name = "coa_jurnal_balik", length = 10)
    private String coaJurnalBalik;

    @Column(name="bulanTahunBerjalan", length = 6)
    private String bulanTahunBerjalan;

    public String getBulanTahunBerjalan() {
        return bulanTahunBerjalan;
    }

    public void setBulanTahunBerjalan(String bulanTahunBerjalan) {
        this.bulanTahunBerjalan = bulanTahunBerjalan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeaderPenerimaan() {
        return headerPenerimaan;
    }

    public void setHeaderPenerimaan(String headerPenerimaan) {
        this.headerPenerimaan = headerPenerimaan;
    }

    public String getHeaderPembayaran() {
        return headerPembayaran;
    }

    public void setHeaderPembayaran(String headerPembayaran) {
        this.headerPembayaran = headerPembayaran;
    }

    public String getHeaderPemindahan() {
        return headerPemindahan;
    }

    public void setHeaderPemindahan(String headerPemindahan) {
        this.headerPemindahan = headerPemindahan;
    }

    public String getCoaBank() {
        return coaBank;
    }

    public void setCoaBank(String coaBank) {
        this.coaBank = coaBank;
    }

    public String getCoaKas() {
        return coaKas;
    }

    public void setCoaKas(String coaKas) {
        this.coaKas = coaKas;
    }

    public String getCoaJurnalBalik() {
        return coaJurnalBalik;
    }

    public void setCoaJurnalBalik(String coaJurnalBalik) {
        this.coaJurnalBalik = coaJurnalBalik;
    }
}
