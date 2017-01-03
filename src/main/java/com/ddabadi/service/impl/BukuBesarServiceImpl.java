package com.ddabadi.service.impl;

import com.ddabadi.domain.*;
import com.ddabadi.domain.repository.AccrualConfigRepository;
import com.ddabadi.domain.repository.BukuBesarRepository;
import com.ddabadi.enumer.StatusVoucher;
import com.ddabadi.exception.VoucherBelumPostingException;
import com.ddabadi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by deddy on 7/8/16.
 */

@Service
public class BukuBesarServiceImpl implements BukuBesarService {

    @Autowired private BukuBesarRepository repository;
    @Autowired private BukuBesarSaldoService bukuBesarSaldoService;

    @Autowired private CoaDtlService coaDtlService;
    @Autowired private CoaHdrService coaHdrService;
    @Autowired private JurnalHdrService jurnalHdrService;
    @Autowired private JurnalDetilService jurnalDetilService;
    @Autowired private AccrualConfigService accrualConfigService;

    @Override
    public BukuBesar save(BukuBesar bukuBesar) {
        return repository.saveAndFlush(bukuBesar);
    }

    @Override
    public void prosesBulanan(boolean trial) {

        // 1. cek jurnal HDR apakah bulan tahun ada yg belum posting
        // 2. cek di BUKU BESAR SALDO apakah sudah pernah proses, kalo sudah CLEAR ALL dan buat saldo awal
        // 3  iterate COA DTL
        // 4. list semua jurnal DTL berdasar COA DTL dan TANGGAL ISSUE
        // 5. create LR berdasar GROUP ACCOUNT di coa HDR
        // 6. input LR bulan berjalan
        //  - group per rel, cust
        // 7. create buku besar
        // 8. input buku besar SALDO
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

        // bulan tahun proses ->bulan lalu utk ambil saldo bulan lalu
        // tanggal awal -1 dari tgl awal
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(tglAwal);
        cal1.add(Calendar.DATE,-1);
        SimpleDateFormat sdfBulanLalu = new SimpleDateFormat("MMyyyy");
        String tglPosesBulanLalu = sdfBulanLalu.format(cal1.getTime());

        // TUTUP BULAN NEXT
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(tglAkhir);
        cal2.add(Calendar.DATE, 1);
        SimpleDateFormat sdf2 = new SimpleDateFormat("MMyyyy");
        String tglProsesNext = sdf2.format(cal2.getTime());

        System.out.println("tgl awal = " + tglAwal.toString() + " tgl akhir = " + tglAkhir.toString() + " proses bulan lalu " + tglPosesBulanLalu);

        // 1. cek jurnal HDR apakah bulan tahun ada yg belum posting
        List<JurnalHeader > jurnalHeaders = jurnalHdrService.getAllCoaHdrByStatusVoucher(StatusVoucher.UNPOSTING,tglAwal, tglAkhir);

        if(jurnalHeaders.size()>0){
            System.out.println("jumlah voucher belum posting = " + jurnalHeaders.size());
            //throw new VoucherBelumPostingException();
        };

        // 2. cek di BUKU BESAR SALDO apakah sudah pernah proses, kalo sudah CLEAR ALL dan buat saldo awal
        cekBukuBesarSaldo(bulanTahun, tglAwal);



        // 3  iterate COA DTL
        Iterator<CoaDtl> coaDtlIt = coaDtlService.getAllCoaNeraca().iterator();
        while (coaDtlIt.hasNext()){
            CoaDtl coaDtl = coaDtlIt.next();

            // 7. create buku besar utk saldo awal
            // create saldo awal ambil dari bulan tahun lalu
            BukuBesar bukuBesarAwal = new BukuBesar();
            Double total = bukuBesarSaldoService.findSaldoByCoaBulanTahun(coaDtl, tglPosesBulanLalu);
            bukuBesarAwal.setCoaDtl(coaDtl);
            bukuBesarAwal.setBulanTahun(bulanTahun);
            bukuBesarAwal.setGroupAccount(coaDtl.getAccountHeader().getGroupAccount());
            bukuBesarAwal.setJurnalDetil(null);
            if(coaDtl.getIsDebet()){
                bukuBesarAwal.setTotalDebet(total);
                bukuBesarAwal.setTotalKredit(0D);
            }else{
                bukuBesarAwal.setTotalDebet(0D);
                bukuBesarAwal.setTotalKredit(total);
            }
            bukuBesarAwal.setTotal_berjalan(total);
            bukuBesarAwal.setKeterangan("saldo bulan lalu");
            repository.save(bukuBesarAwal);

            // ambil jurnal detil
            // 4. list semua jurnal DTL berdasar COA DTL dan TANGGAL ISSUE
            Iterator<JurnalDetil> jurnalDetilIt = jurnalDetilService.getByCoaTglIssue(coaDtl, tglAwal, tglAkhir).iterator();
            while (jurnalDetilIt.hasNext()){

                JurnalDetil jurnalDetil = jurnalDetilIt.next();

                Double totalDK;
                if(jurnalDetil.getAccountDetil().getIsDebet()){
                    totalDK = jurnalDetil.getDebet() - jurnalDetil.getKredit();
                }else{
                    totalDK = jurnalDetil.getKredit() - jurnalDetil.getDebet()  ;
                }
                total = total + totalDK;

                // 7. create buku besar
                BukuBesar bukuBesar = new BukuBesar();
                bukuBesar.setBulanTahun(bulanTahun);
                bukuBesar.setCoaDtl(coaDtl);
                bukuBesar.setGroupAccount(coaDtl.getAccountHeader().getGroupAccount());
                bukuBesar.setJurnalDetil(jurnalDetil);
                bukuBesar.setTotalDebet(jurnalDetil.getDebet());
                bukuBesar.setTotalKredit(jurnalDetil.getKredit());
                bukuBesar.setTotal_berjalan(total);
                bukuBesar.setKeterangan(jurnalDetil.getKeterangan().trim());
                repository.save(bukuBesar);

            }

            // 8. input buku besar SALDO
            BukuBesarSaldo bukuBesarSaldoBaru = new BukuBesarSaldo();
            bukuBesarSaldoBaru.setCoaDtl(coaDtl);
            bukuBesarSaldoBaru.setBulanTahun(bulanTahun);
            bukuBesarSaldoBaru.setNilai(total);
            bukuBesarSaldoService.save(bukuBesarSaldoBaru);


        }

        // 10. update accrual config
        if(trial==false){
            accrualConfig.setBulanTahunBerjalan(tglProsesNext);
            accrualConfigService.updateConfig(accrualConfig);
        }


//        Iterator<JurnalHeader> itJurnal = jurnalHeaders.iterator();
//        while (itJurnal.hasNext()){
//            JurnalHeader jurnalHeader = itJurnal.next();
//            Iterator<JurnalDetil> jurnalDetils = jurnalDetilService.getByJurnalHdrId(jurnalHeader.getId()).iterator();
//            while (jurnalDetils.hasNext()){
//                JurnalDetil jurnalDetil = jurnalDetils.next();
//
//            }
//
//        }


    }

    @Override
    public void deleteBulanan(String bulanTahun) {
        repository.deleteByBulanTahun(bulanTahun);
    }

    @Override
    public String getTglProsesBulanan() {

        AccrualConfig accrualConfig = accrualConfigService.getConfig();
        String bulanTahun = accrualConfig.getBulanTahunBerjalan();

        int bulan= Integer.parseInt(bulanTahun.substring(0, 2)) ;
        int tahun = Integer.parseInt(bulanTahun.substring(2, 6));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM  yyyy");

        // TANGGAL AWAL
        calendar.set(tahun,bulan -1,1);
        Date tglAwal;
        tglAwal = calendar.getTime();
        return sdf.format(tglAwal);
    }

    @Override
    public String getTglProsesBulanLalu() {
        AccrualConfig accrualConfig = accrualConfigService.getConfig();
        String bulanTahun = accrualConfig.getBulanTahunBerjalan();

        int bulan= Integer.parseInt(bulanTahun.substring(0, 2)) ;
        int tahun = Integer.parseInt(bulanTahun.substring(2, 6));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMyyyy");

        // TANGGAL AWAL
        calendar.set(tahun,bulan -2,1);
        Date tglAwal;
        tglAwal = calendar.getTime();
        return sdf.format(tglAwal);
    }

    @Override
    public Page<BukuBesar> findByBulanTahunBerjalanKeterangan(String bulanTahun, String keterangan, int hal, int jumlah) {
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        PageRequest pageRequest = new PageRequest(hal-1,jumlah,sort);
        return repository.findByBulanTahunAndKeteranganLike(bulanTahun, keterangan, pageRequest);
    }

    @Override
    public Page<BukuBesar> findByBulanTahunBerjalanKeteranganCoaDtl(String bulanTahun, String keterangan, Long idCoaDtl, int hal, int jumlah) {
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        PageRequest pageRequest = new PageRequest(hal-1,jumlah,sort);
        CoaDtl coaDtl = coaDtlService.getById(idCoaDtl);
        return repository.findByBulanTahunAndCoaDtlAndKeteranganLike(bulanTahun, coaDtl, keterangan, pageRequest);
    }


    private void cekBukuBesarSaldo(String bulanTahun, Date tglAwal){
        // 2. cek di BUKU BESAR SALDO apakah sudah pernah proses, kalo sudah CLEAR ALL

        List<BukuBesarSaldo> bukuBesarSaldoList = bukuBesarSaldoService.findByBulanTahun(bulanTahun);

        if(bukuBesarSaldoList.size()>0){
            // clear all
            bukuBesarSaldoService.deleteByBulanTahun(bulanTahun);
            deleteBulanan(bulanTahun);
            System.out.println("delete all bulan tahun = " + bulanTahun);
        }


    }


}
