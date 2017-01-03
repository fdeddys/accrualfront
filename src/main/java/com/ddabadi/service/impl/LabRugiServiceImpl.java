package com.ddabadi.service.impl;

import com.ddabadi.domain.*;
import com.ddabadi.domain.repository.LabaRugiRepository;
import com.ddabadi.domain.repository.LabaRugiSaldoRepository;
import com.ddabadi.enumer.GroupAccount;
import com.ddabadi.enumer.StatusVoucher;
import com.ddabadi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by deddy on 7/14/16.
 */

@Service
public class LabRugiServiceImpl implements LabaRugiService {

    @Autowired private LabaRugiRepository labaRugiRepository;
    @Autowired private LabaRugiSaldoService labaRugiSaldoService;
    @Autowired private CoaDtlService coaDtlService;
    @Autowired private CoaHdrService coaHdrService;
    @Autowired private JurnalHdrService jurnalHdrService;
    @Autowired private JurnalDetilService jurnalDetilService;
    @Autowired private AccrualConfigService accrualConfigService;
    @Autowired private BagianService bagianService;

    @Override
    public LabaRugi save(LabaRugi labaRugi) {
        return labaRugiRepository.save(labaRugi);
    }

    @Override
    public void prosesBulanan(boolean trial) {


        // 1. cek jurnal HDR apakah bulan tahun ada yg belum posting
        // 2. cek di LR SALDO apakah sudah pernah proses, kalo sudah CLEAR ALL dan buat saldo awal
        // 3  iterate COA DTL
        // 4. list semua jurnal DTL berdasar COA DTL dan TANGGAL ISSUE
        // 5.   iterate BIAYA berdasar bagian
        // 5.   create LR berdasar GROUP ACCOUNT di coa HDR
        // 6.   input LR bulan berjalan
        // 7.   create LR
        // 8. input LR SALDO
        // 9. set posting di HDR
        // 10. update accrual config

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.DATE, 1);

        AccrualConfig accrualConfig = accrualConfigService.getConfig();
        String bulanTahun = accrualConfig.getBulanTahunBerjalan();

        int bulan= Integer.parseInt(bulanTahun.substring(0, 2)) ;
        int tahun = Integer.parseInt(bulanTahun.substring(2, 6));

        // TANGGAL AWAL
        calendar.set(tahun,bulan -1,1);
        Date tglAwal;
        tglAwal = calendar.getTime();

        // TANGGAL AKHIR
        calendar.add(Calendar.MONTH, +1);
        calendar.add(Calendar.DATE, -1);
        Date tglAkhir=calendar.getTime();


        // 2. cek di BUKU BESAR SALDO apakah sudah pernah proses, kalo sudah CLEAR ALL dan buat saldo awal
        cekLRSaldo(bulanTahun, tglAwal);


        // 3  iterate COA DTL
        Iterator<CoaDtl> coaDtlIt = coaDtlService.getAllCoaLR().iterator();
        while (coaDtlIt.hasNext()){
            CoaDtl coaDtl = coaDtlIt.next();

            // if acc biaya iterate bagian
//            if (coaDtl.getAccountHeader().getGroupAccount().equals(GroupAccount.LABARUGI_BIAYA)){
//                // group by idcoa, bagian, get SUM()
//                List<CoaDtl> coaDtls = jurnalDetilService.groupByCoaBagian
//            }else{
//                // group by idcoa, get SUM()
//            }

            // else bukan biaya lgsg coa dtl

            // ambil jurnal detil
            // 4. list semua jurnal DTL berdasar COA DTL dan TANGGAL ISSUE


            //iterate bagian
            Iterator<Bagian> bagianIterator = bagianService.getAll().iterator();
            while (bagianIterator.hasNext()){
                Bagian bagian = bagianIterator.next();
                //JurnalDetil jurnalDetil1 = jurnalDetilService.getByCoaBagianTglIssue(coaDtl,bagian,tglAwal,tglAkhir);
                Double total=0D;
                Iterator<JurnalDetil> jurnalDetilIt = jurnalDetilService.getByCoaBagianTglIssue(coaDtl,bagian, tglAwal, tglAkhir).iterator();
                while (jurnalDetilIt.hasNext()){

                    //group by bagian
                    JurnalDetil jurnalDetil = jurnalDetilIt.next();

                    Double totalDK;
                    if(jurnalDetil.getAccountDetil().getIsDebet()){
                        totalDK = jurnalDetil.getDebet() - jurnalDetil.getKredit();
                    }else{
                        totalDK = jurnalDetil.getKredit() - jurnalDetil.getDebet()  ;
                    }
                    total = total + totalDK;
                    LabaRugi labaRugi = new LabaRugi();
                    labaRugi.setCoaDtl(coaDtl);
                    labaRugi.setTotalDebet(jurnalDetil.getDebet());
                    labaRugi.setBulanTahun(bulanTahun);
                    labaRugi.setJurnalDetil(jurnalDetil);
                    labaRugi.setBagian(bagian);
                    labaRugi.setGroupAccount(coaDtl.getAccountHeader().getGroupAccount());
                    labaRugi.setKeterangan(jurnalDetil.getKeterangan());
                    labaRugi.setTotal_berjalan(0D);
                    labaRugi.setTotalKredit(jurnalDetil.getKredit());
                    labaRugiRepository.save(labaRugi);

                }

                if(total==0D){
                    // dak usah input LR soalnyo dak ado
                }else{
                    LabaRugiSaldo labaRugiSaldo = new LabaRugiSaldo();
                    labaRugiSaldo.setBagian(bagian);
                    labaRugiSaldo.setBulanTahun(bulanTahun);
                    labaRugiSaldo.setCoaDtl(coaDtl);
                    labaRugiSaldo.setTotal(total);
                    labaRugiSaldoService.save(labaRugiSaldo);

                }

            }

            // 8. input buku besar SALDO


        }

        // 10. update accrual config
//        if(trial==false){
//            accrualConfig.setBulanTahunBerjalan(tglProsesNext);
//            accrualConfigService.updateConfig(accrualConfig);
//        }


    }

    private void cekLRSaldo(String bulanTahun, Date tglAwal){

        List<LabaRugiSaldo> labaRugiSaldoList = labaRugiSaldoService.findByBulanTahun(bulanTahun);

        if(labaRugiSaldoList.size()>0){
            // clear all
            labaRugiSaldoService.deleteByBulanTahun(bulanTahun);
            deleteBulanan(bulanTahun);
            System.out.println("delete all bulan tahun = " + bulanTahun);
        }


    }

    @Override
    public void deleteBulanan(String bulanTahun) {

    }
}
