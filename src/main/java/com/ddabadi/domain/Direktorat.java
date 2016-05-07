package com.ddabadi.domain;

import org.hibernate.annotations.CollectionId;

import javax.persistence.*;

/**
 * Created by deddy on 4/25/16.
 */

@Entity
@Table(name = "tb_direktorat",
            indexes = {@Index(name = "ix_kode", columnList = "kode"),
                       @Index(name = "ix_nama", columnList = "nama")})

public class Direktorat {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "kode", length = 100)
    private String  kode;

    @Column(name = "nama", length = 100)
    private String  nama;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
