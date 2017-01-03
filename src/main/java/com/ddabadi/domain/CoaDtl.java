package com.ddabadi.domain;

import com.ddabadi.enumer.Status;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;

/**
 * Created by deddy on 5/3/16.
 */

@Entity
@Table(name = "tb_coa_dtl")
public class    CoaDtl {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long idAccountDtl;

    @Column(name = "nama", length = 100)
    private String namaPerkiraan;

    @Column(name = "kode", length = 20)
    private String kodePerkiraan;

    @Column(name = "rel")
    private Boolean rel;

    @Column(name = "cust")
    private Boolean cust;

    @Column(name = "cash_bank")
    private Boolean cashBank;

    @Column(name = "is_Debet")
    private Boolean isDebet;

    public Boolean getIsDebet() {
        return isDebet;
    }

    public void setIsDebet(Boolean isDebet) {
        this.isDebet = isDebet;
    }

    //    accountHeader: {
//        idAccountHdr: 0,
//                namaAccount: "",
//                kodeAccount: ""
//    },

    @ManyToOne
    @JoinColumn(name = "coaHdr")
    private CoaHdr accountHeader;

    @Column(name = "Status")
    private Status status;

    @Column(name = "auto_generate_no")
    private Boolean autoGenerateNo;

    @Column(name = "header_auto")
    private String headerAutoGenerateNo;

    public Long getIdAccountDtl() {
        return idAccountDtl;
    }

    public void setIdAccountDtl(Long idAccountDtl) {
        this.idAccountDtl = idAccountDtl;
    }

    public String getNamaPerkiraan() {
        return namaPerkiraan;
    }

    public void setNamaPerkiraan(String namaPerkiraan) {
        this.namaPerkiraan = namaPerkiraan;
    }

    public String getKodePerkiraan() {
        return kodePerkiraan;
    }

    public void setKodePerkiraan(String kodePerkiraan) {
        this.kodePerkiraan = kodePerkiraan;
    }

    public Boolean getRel() {
        return rel;
    }

    public void setRel(Boolean rel) {
        this.rel = rel;
    }

    public Boolean getCust() {
        return cust;
    }

    public void setCust(Boolean cust) {
        this.cust = cust;
    }

    public Boolean getCashBank() {
        return cashBank;
    }

    public void setCashBank(Boolean cashBank) {
        this.cashBank = cashBank;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getAutoGenerateNo() {
        return autoGenerateNo;
    }

    public void setAutoGenerateNo(Boolean autoGenerateNo) {
        this.autoGenerateNo = autoGenerateNo;
    }

    public String getHeaderAutoGenerateNo() {
        return headerAutoGenerateNo;
    }

    public void setHeaderAutoGenerateNo(String headerAutoGenerateNo) {
        this.headerAutoGenerateNo = headerAutoGenerateNo;
    }

    public CoaHdr getAccountHeader() {
        return accountHeader;
    }

    public void setAccountHeader(CoaHdr accountHeader) {
        this.accountHeader = accountHeader;
    }
}
