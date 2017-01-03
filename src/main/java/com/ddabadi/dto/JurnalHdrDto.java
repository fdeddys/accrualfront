package com.ddabadi.dto;

import com.ddabadi.domain.User;
import com.ddabadi.enumer.JenisVoucher;
import com.ddabadi.enumer.StatusVoucher;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by deddy on 6/17/16.
 */
public class JurnalHdrDto {

    private Long id;
    private String noUrut;
    private String issueDate;
    private String bookingDate;
    private String noVoucher;
    private String diBayar;
    private StatusVoucher statusVoucher;
    private JenisVoucher jenisVoucher;
    private Long user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoUrut() {
        return noUrut;
    }

    public void setNoUrut(String noUrut) {
        this.noUrut = noUrut;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getNoVoucher() {
        return noVoucher;
    }

    public void setNoVoucher(String noVoucher) {
        this.noVoucher = noVoucher;
    }

    public String getDiBayar() {
        return diBayar;
    }

    public void setDiBayar(String diBayar) {
        this.diBayar = diBayar;
    }

    public StatusVoucher getStatusVoucher() {
        return statusVoucher;
    }

    public void setStatusVoucher(StatusVoucher statusVoucher) {
        this.statusVoucher = statusVoucher;
    }

    public JenisVoucher getJenisVoucher() {
        return jenisVoucher;
    }

    public void setJenisVoucher(JenisVoucher jenisVoucher) {
        this.jenisVoucher = jenisVoucher;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }
}
