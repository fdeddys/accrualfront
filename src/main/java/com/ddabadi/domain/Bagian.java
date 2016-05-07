package com.ddabadi.domain;

import com.ddabadi.enumer.Status;

import javax.persistence.*;

/**
 * Created by deddy on 4/25/16.
 */

@Entity
@Table(name = "tb_bagian",
        indexes =@Index(name = "ix_nama", columnList = "nama"))
public class Bagian {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "kode")
    private String kode;

    @Column(name = "aktif")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "direktorat")
    private Direktorat direktorat;

    public Direktorat getDirektorat() {
        return direktorat;
    }

    public void setDirektorat(Direktorat direktorat) {
        this.direktorat = direktorat;
    }

    public Long getId() {
        return id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
