package com.ddabadi.service.util;

import com.ddabadi.domain.*;
import com.ddabadi.enumer.JenisVoucher;
import com.ddabadi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by deddy on 5/15/16.
 */

@Service
//@Configuration
public class FungsiUtil {

    @Autowired
    private IdxNoUrutService idxNoUrutService;
    @Autowired
    private IdxNoVoucherService idxNoVoucherService;
    @Autowired
    private AccrualConfigService accrualConfigService;
    @Autowired
    private IdxNoRelService idxNoRelService;
    @Autowired
    private IdxNoApproveSTService idxNoApproveSTService;
    @Autowired
    private IdxNoPiutangUsahaService idxNoPiutangUsahaService;



    @Autowired
    private UserService userService;

    //ADyy99999
    public String createNoUrut(Long userId, Date tglBooking){

        Long noUrut;
        String hasil;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String tahun = sdf.format(tglBooking);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yy");
        String tahunHasil = sdf2.format(tglBooking);

        User user = userService.getById(userId);

        IdxNoUrut idxNoUrut= idxNoUrutService.getByIdUserTahun(userId,tahun);
        if(idxNoUrut==null){
            idxNoUrut=new IdxNoUrut();
            idxNoUrut.setIdUser(userId);
            idxNoUrut.setTahun(tahun);
            idxNoUrut.setUrut(0L);
            idxNoUrut = idxNoUrutService.save(idxNoUrut);
        }
        noUrut= idxNoUrut.getUrut()+1;
        idxNoUrut.setUrut(noUrut);


        hasil = ("00000" + noUrut.toString().trim()).trim();
        hasil = hasil.substring(hasil.length()-5);
        hasil = user.getInitial().toUpperCase() + tahunHasil +hasil;

        idxNoUrutService.save(idxNoUrut);

        return  hasil;
    }


    //ByyMM99999
    public String createNoVoucher(JenisVoucher jenisVoucher,Date tglBooking){

        Long noUrut;
        String hasil;
        String headerVoucher;
        AccrualConfig accrualConfig = accrualConfigService.getConfig();

        if(jenisVoucher.equals(JenisVoucher.PEMINDAHAN)){
            headerVoucher = accrualConfig.getHeaderPemindahan();
        }else{
            if(jenisVoucher.equals(JenisVoucher.PENERIMAAN)){
                headerVoucher = accrualConfig.getHeaderPenerimaan();
            }else{
                headerVoucher = accrualConfig.getHeaderPembayaran();
            }
        }

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        String tahun = sdf1.format(tglBooking);

        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        String bulan = sdf2.format(tglBooking);

        SimpleDateFormat sdf3 = new SimpleDateFormat("yy");
        String thn = sdf3.format(tglBooking);

        IdxNoVoucher idxNoVoucher = idxNoVoucherService.getByJenisVoucherBulanTahun(jenisVoucher, bulan, tahun);

        if(idxNoVoucher==null){
            idxNoVoucher = new IdxNoVoucher();
            idxNoVoucher.setUrut(0L);
            idxNoVoucher.setTahun(tahun);
            idxNoVoucher.setBulan(bulan);
            idxNoVoucher.setJenisVoucher(jenisVoucher);
            idxNoVoucherService.save(idxNoVoucher);
        }
        noUrut= idxNoVoucher.getUrut()+1;
        idxNoVoucher.setUrut(noUrut);

        hasil = ("0000" + noUrut.toString().trim()).trim();
        hasil = hasil.substring(hasil.length() - 4);
        hasil =  headerVoucher.toUpperCase().trim() + thn.trim() + bulan.trim() + hasil;

        idxNoVoucherService.save(idxNoVoucher);

        return  hasil;

    }


    //PUyMM99999
    public String createNoRel(CoaDtl coaDtl,Date tglBooking){

        Long noUrut;
        String hasil;

        //AccrualConfig accrualConfig = accrualConfigService.getConfig();


        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        String tahun = sdf1.format(tglBooking);

        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        String bulan = sdf2.format(tglBooking);

        SimpleDateFormat sdf3 = new SimpleDateFormat("yy");
        String thn = sdf3.format(tglBooking);

        IdxNoRel idxNoRel = idxNoRelService.getByCoaDtlBulanTahun(coaDtl, bulan, tahun);


        if(idxNoRel==null){
            idxNoRel = new IdxNoRel();
            idxNoRel.setUrut(0L);
            idxNoRel.setTahun(tahun);
            idxNoRel.setBulan(bulan);
            idxNoRel.setCoaDtl(coaDtl);
            idxNoRelService.save(idxNoRel);
        }
        noUrut= idxNoRel.getUrut()+1;
        idxNoRel.setUrut(noUrut);

        hasil = ("0000" + noUrut.toString().trim()).trim();
        hasil = hasil.substring(hasil.length() - 4);
        hasil =  coaDtl.getHeaderAutoGenerateNo().toUpperCase().trim() + thn.trim() + bulan.trim() + hasil;

        idxNoRelService.save(idxNoRel);

        return  hasil;

    }


    //APyymm99999
    public String createNoApproveST( Date tglSurat){

        Long noUrut;
        String hasil;

        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        String tahun = sdf.format(tglSurat);

        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        String bulan = sdf2.format(tglSurat);

        IdxNoApproveSuratTansfer idx= idxNoApproveSTService.getByBulanTahun(bulan, tahun);
        if(idx==null){
            idx=new IdxNoApproveSuratTansfer();
            idx.setBulan(bulan);
            idx.setTahun(tahun);
            idx.setUrut(0L);
            idx = idxNoApproveSTService.save(idx);
        }
        noUrut= idx.getUrut()+1;
        idx.setUrut(noUrut);


        hasil = ("00000" + noUrut.toString().trim()).trim();
        hasil = hasil.substring(hasil.length()-5);
        hasil = "AP"+tahun+bulan+hasil;

        idxNoApproveSTService.save(idx);

        return  hasil;
    }

    //PUyymm99999
    public String createNoPiutangUsaha(Date tanggal){

        Long noUrut;
        String hasil;

        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        String tahun = sdf.format(tanggal);

        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        String bulan = sdf2.format(tanggal);

        IdxNoPiutangUsaha idx= idxNoPiutangUsahaService.getByBulanTahun(bulan, tahun);
        if(idx==null){
            idx=new IdxNoPiutangUsaha();
            idx.setBulan(bulan);
            idx.setTahun(tahun);
            idx.setUrut(0L);
            idx = idxNoPiutangUsahaService.save(idx);
        }
        noUrut= idx.getUrut()+1;
        idx.setUrut(noUrut);


        hasil = ("00000" + noUrut.toString().trim()).trim();
        hasil = hasil.substring(hasil.length()-5);
        hasil = "PU"+tahun+bulan+hasil;
        idxNoPiutangUsahaService.save(idx);

        return  hasil;
    }


}
