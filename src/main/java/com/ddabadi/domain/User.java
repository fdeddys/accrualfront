package com.ddabadi.domain;

import com.ddabadi.enumer.Status;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;

/**
 * Created by deddy on 4/25/16.
 */

@Entity
@Table(name = "tb_user",
        indexes = {@Index(name = "ix_nama", columnList = "nama") })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @Column(name = "nama", length = 50)
    private String nama;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "initial", length = 2)
    private String initial;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name="status")
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


}
