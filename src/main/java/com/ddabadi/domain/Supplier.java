package com.ddabadi.domain;

import javax.persistence.*;

/**
 * Created by deddy on 4/23/16.
 */

@Entity
@Table(name = "tb_supplier",
        indexes = @Index(name = "ix_nama", columnList = "nama"))
public class Supplier {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nama", length = 100)
    private String nama;

    public Long getId() {
        return id;
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
}
