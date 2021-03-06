package com.ddabadi.domain;

import com.ddabadi.enumer.JenisVoucher;
import com.ddabadi.enumer.StatusVoucher;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by deddy on 5/3/16.
 */
@Entity
@Table(name = "tb_jurnal_hdr")
public class JurnalHeader {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    // per tahun
    // per user
    @Column(name = "no_urut", length = 100)
    private String noUrut;

    @Column(name = "issue_date")
    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Column(name = "booking_date")
    @Temporal(TemporalType.DATE)
    private Date bookingDate;

    // per bulan
    // sesuai jenis voucher
    // B00001 Bayar
    // T00001 Penerimaan
    // P00001 Pemindahan
    @Column(name = "no_voucher", length = 100)
    private String noVoucher;

    @Column(name = "di_bayar", length = 100)
    private String diBayar;

    @Column(name = "status_voucher")
    private StatusVoucher statusVoucher;

    @Column(name = "jenis_voucher")
    private JenisVoucher jenisVoucher;

    @OneToOne
    private User user;

    @Column(name = "is_tarik_pembayaran", columnDefinition = "boolean default false")
    private boolean isTarikPembayaran = false;

    @Column(name = "is_validasi_pembayaran", columnDefinition = "boolean default false")
    private boolean isValidasiPembayaran = false;

    @Column(name = "is_isi_book_date", columnDefinition = "boolean default false")
    private boolean isIsiBookDate= false;

    public boolean isIsiBookDate() {
        return isIsiBookDate;
    }

    public void setIsIsiBookDate(boolean isIsiBookDate) {
        this.isIsiBookDate = isIsiBookDate;
    }

    public boolean isValidasiPembayaran() {
        return isValidasiPembayaran;
    }

    public void setIsValidasiPembayaran(boolean isValidasiPembayaran) {
        this.isValidasiPembayaran = isValidasiPembayaran;
    }

    public boolean isTarikPembayaran() {
        return isTarikPembayaran;
    }

    public void setIsTarikPembayaran(boolean isTarikPembayaran) {
        this.isTarikPembayaran = isTarikPembayaran;
    }

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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
