package com.ddabadi.domain;

import javax.persistence.*;

/**
 * Created by deddy on 5/15/16.
 */

@Entity
@Table(name = "tb_idx_no_urut")
public class IdxNoUrut {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    // UserId
    // tahun
    // noUrut

    @Column(name = "id_user")
    private Long idUser;

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

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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
