package com.ddabadi.domain;

import com.ddabadi.domain.JurnalHeader;
import com.ddabadi.domain.SuratTransferHd;

import javax.persistence.*;

/**
 * Created by deddy on 10/3/16.
 */


@Entity
@Table(name = "tb_surat_transfer_dt")
public class SuratTransferDt {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "surat_Transfer")
    private SuratTransferHd suratTransferHd;

    @OneToOne
    @JoinColumn(name = "jurnal_detil")
    private JurnalDetil jurnalDetil;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SuratTransferHd getSuratTransferHd() {
        return suratTransferHd;
    }

    public void setSuratTransferHd(SuratTransferHd suratTransferHd) {
        this.suratTransferHd = suratTransferHd;
    }


    public JurnalDetil getJurnalDetil() {
        return jurnalDetil;
    }

    public void setJurnalDetil(JurnalDetil jurnalDetil) {
        this.jurnalDetil = jurnalDetil;
    }
}
