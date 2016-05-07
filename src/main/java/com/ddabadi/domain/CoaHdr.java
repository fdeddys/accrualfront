package com.ddabadi.domain;

import javax.persistence.*;

/**
 * Created by deddy on 5/3/16.
 */
@Entity
@Table(name = "tb_coa_hdr")
public class CoaHdr {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long idAccountHdr;

    @Column(name = "nama", length = 100)
    private String namaAccount;

    @Column(name = "kode", length = 20)
    private String kodeAccount;

    public long getIdAccountHdr() {
        return idAccountHdr;
    }

    public void setIdAccountHdr(long idAccountHdr) {
        this.idAccountHdr = idAccountHdr;
    }

    public String getNamaAccount() {
        return namaAccount;
    }

    public void setNamaAccount(String namaAccount) {
        this.namaAccount = namaAccount;
    }

    public String getKodeAccount() {
        return kodeAccount;
    }

    public void setKodeAccount(String kodeAccount) {
        this.kodeAccount = kodeAccount;
    }
}
