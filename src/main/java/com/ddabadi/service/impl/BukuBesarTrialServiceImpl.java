package com.ddabadi.service.impl;

import com.ddabadi.domain.*;
import com.ddabadi.domain.repository.BukuBesarTrialRepository;
import com.ddabadi.enumer.GroupAccount;
import com.ddabadi.enumer.StatusVoucher;
import com.ddabadi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by deddy on 12/18/16.
 */

@Service
public class BukuBesarTrialServiceImpl implements BukuBesarTrialService {

    @Autowired private BukuBesarTrialRepository repository;
    @Autowired private BukuBesarService bukuBesarService;
    @Autowired private JurnalHdrService jurnalHdrService;
    @Autowired private JurnalDetilService jurnalDetilService;
    @Autowired private BukuBesarSaldoService bukuBesarSaldoService;
    @Autowired private AccrualConfigService accrualConfigService;
    @Autowired private CoaDtlService coaDtlService;
    @Autowired private CustomerService customerService;

    @Override
    public BukuBesarTrial save(BukuBesarTrial bukuBesarTrial) {
        return  repository.save(bukuBesarTrial);
    }

    @Override
    public void postingJurnalTrial(Long jurnalHeaderId) {
        //1. iterate jurnal detil

        //2. cek di buku besar trial ada transaksi lom??
        //   2.1 jika ada ambil saldo isi untuk proses jumlah berjalan LAST REC
        //   2.2 jika tidak ada cek di buku besar saldo
        //      2.2.1 jika ada ambil nilai total Berjalan dan isi di buku besar trial
        //      2.2.2 jika tidak ada isi total Berjalan =0 dan isi di buku besar trial
        // 3 isi buku besar trial
        // 4.set posting true.



        AccrualConfig accrualConfig = accrualConfigService.getConfig();
        String blnTahunBerjalan  = accrualConfig.getBulanTahunBerjalan();

        String blnTahunLalu = bukuBesarService.getTglProsesBulanLalu();
        //String blnTahunBerjalan = bukuBesarService.getTglProsesBulanan();

        //1. iterate jurnal detil
        Iterator<JurnalDetil> jurnalDetilIterator = jurnalDetilService.getByJurnalHdrId(jurnalHeaderId).iterator();
        while (jurnalDetilIterator.hasNext()){
            JurnalDetil jurnalDetil = jurnalDetilIterator.next();

            //2. cek di buku besar trial ada transaksi lom??
            //   2.1 jika ada ambil saldo isi untuk proses jumlah berjalan LAST REC
            //   2.2 jika tidak ada cek di buku besar saldo
            //      2.2.1 jika ada ambil nilai total Berjalan dan isi di buku besar trial
            //      2.2.2 jika tidak ada isi total Berjalan =0 dan isi di buku besar trial
            // 3 isi buku besar trial .

            Long kriteriaBank;
            String kriteriaBank1;

            Long kriteriaCust;
            String kriteriaCust1;

            String kriteriaRel;

            Double totalBerjalan;


            if(jurnalDetil.getBank()==null || jurnalDetil.getBank().equals("")){
                kriteriaBank = 0l;
                kriteriaBank1="%";
            }else{
                kriteriaBank = jurnalDetil.getBank().getId();
                kriteriaBank1 = jurnalDetil.getBank().getKode();
            }

            if(jurnalDetil.getRel()==null || jurnalDetil.getRel().equals("-")){
                kriteriaRel= "%";
            }else{
                kriteriaRel= jurnalDetil.getRel().trim();
            }

            if(jurnalDetil.getCustomer()==null || jurnalDetil.getCustomer().equals("")){
                kriteriaCust= 0L;
                kriteriaCust1="%";
            }else{
                kriteriaCust= jurnalDetil.getCustomer().getId();
                kriteriaCust1= jurnalDetil.getCustomer().getKode();
            }

            ///2. cek di buku besar trial ada transaksi lom??
            List<BukuBesarTrial> bukuBesarTrials = repository.findByBulanTahunAndCoaDtlAndIdBankLikeAndRelLikeAndIdCustomerLikeOrderByIdDesc(blnTahunBerjalan, jurnalDetil.getAccountDetil(), kriteriaBank, kriteriaRel, kriteriaCust);
            if(bukuBesarTrials.size()>0){
                //  2.1 jika ada ambil saldo isi untuk proses jumlah berjalan LAST REC
                BukuBesarTrial bukuBesarTrial1 = bukuBesarTrials.iterator().next();
                totalBerjalan = bukuBesarTrial1.getTotal_berjalan();

            }else{
                //  2.2 jika tidak ada, cek di buku besar saldo
                BukuBesarSaldo bukuBesarSaldo = bukuBesarSaldoService.getByBulanTahunAndCoaDtlAndBankKodeLikeAndRelLikeAndCustomerKodeLike
                        (blnTahunLalu, jurnalDetil.getAccountDetil(), kriteriaBank1, kriteriaRel, kriteriaCust1);

                BukuBesarTrial bukuBesarTrial = new BukuBesarTrial();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date tglBook=null;
                try {
                    tglBook = sdf.parse(blnTahunBerjalan+ "01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(kriteriaBank==0L){
                    bukuBesarTrial.setBank(null);
                    bukuBesarTrial.setIdBank(0L);
                }else{
                    bukuBesarTrial.setBank(jurnalDetil.getBank());
                    bukuBesarTrial.setIdBank(jurnalDetil.getBank().getId());
                }
                if(kriteriaRel=="%"){
                    bukuBesarTrial.setRel("-");
                }else{
                    bukuBesarTrial.setRel(jurnalDetil.getRel());
                }
                if(kriteriaCust==0L){
                    bukuBesarTrial.setIdCustomer(0L);
                    bukuBesarTrial.setCustomer(null);
                }else{
                    bukuBesarTrial.setIdCustomer(jurnalDetil.getCustomer().getId());
                    bukuBesarTrial.setCustomer(jurnalDetil.getCustomer());
                }
                bukuBesarTrial.setCoaDtl(jurnalDetil.getAccountDetil());
                bukuBesarTrial.setBulanTahun(blnTahunBerjalan);
                bukuBesarTrial.setGroupAccount(jurnalDetil.getAccountDetil().getAccountHeader().getGroupAccount());
                bukuBesarTrial.setJurnalDetilId(null);
                bukuBesarTrial.setKeterangan("( Saldo Bulan Lalu )");
                bukuBesarTrial.setTotalDebet(0D);
                bukuBesarTrial.setTotalKredit(0D);
                bukuBesarTrial.setBagian(jurnalDetil.getBagian());
                bukuBesarTrial.setNoUrut("");
                bukuBesarTrial.setNoVoucher("");
                bukuBesarTrial.setTglBooking(tglBook);
                bukuBesarTrial.setPenerima("");

                if(bukuBesarSaldo==null){
                    //  2.2.2 jika tidak ada isi total Berjalan =0 dan isi di buku besar trial
                    totalBerjalan=0D;
                    bukuBesarTrial.setTotal_berjalan(0D);
                }else{
                    // 2.2.1 jika ada ambil nilai total Berjalan dan isi di buku besar trial
                    totalBerjalan=bukuBesarSaldo.getNilai();
                    bukuBesarTrial.setTotal_berjalan(bukuBesarSaldo.getNilai());
                }
                repository.save(bukuBesarTrial);
            }//END - 2.cek di buku besar trial ada transaksi lom??

            // 3 isi buku besar trial .
            BukuBesarTrial bkBesarTrial = new BukuBesarTrial();

            Double totalDK;
            if(jurnalDetil.getAccountDetil().getIsDebet()){
                totalDK = jurnalDetil.getDebet() - jurnalDetil.getKredit();
            }else{
                totalDK = jurnalDetil.getKredit() - jurnalDetil.getDebet();
            }
            totalBerjalan = totalBerjalan + totalDK;

            bkBesarTrial.setBulanTahun(blnTahunBerjalan);
            bkBesarTrial.setCoaDtl(jurnalDetil.getAccountDetil());
            bkBesarTrial.setGroupAccount(jurnalDetil.getAccountDetil().getAccountHeader().getGroupAccount());
            bkBesarTrial.setJurnalDetilId(jurnalDetil.getId());
            bkBesarTrial.setTotalDebet(jurnalDetil.getDebet());
            bkBesarTrial.setTotalKredit(jurnalDetil.getKredit());
            bkBesarTrial.setTotal_berjalan(totalBerjalan);
            bkBesarTrial.setKeterangan(jurnalDetil.getKeterangan().trim());
            bkBesarTrial.setBagian(jurnalDetil.getBagian());
            bkBesarTrial.setNoUrut(jurnalDetil.getJurnalHeader().getNoUrut());
            bkBesarTrial.setNoVoucher(jurnalDetil.getJurnalHeader().getNoVoucher());
            bkBesarTrial.setTglBooking(jurnalDetil.getJurnalHeader().getBookingDate());
            bkBesarTrial.setPenerima(jurnalDetil.getJurnalHeader().getDiBayar());
            if(kriteriaBank==0L){
                bkBesarTrial.setBank(null);
                bkBesarTrial.setIdBank(0L);
            }else{
                bkBesarTrial.setBank(jurnalDetil.getBank());
                bkBesarTrial.setIdBank(jurnalDetil.getBank().getId());
            }
            if (kriteriaRel == "%") {
                bkBesarTrial.setRel("-");
            }else{
                bkBesarTrial.setRel(jurnalDetil.getRel());
            }
            if(kriteriaCust==0L){
                bkBesarTrial.setIdCustomer(0L);
                bkBesarTrial.setCustomer(null);
            }else{
                bkBesarTrial.setIdCustomer(jurnalDetil.getCustomer().getId());
                bkBesarTrial.setCustomer(jurnalDetil.getCustomer());
            }
            repository.save(bkBesarTrial);
        }

        setPosting(jurnalHeaderId);
    }

    @Override
    public void setPosting(Long id){
        JurnalHeader jurnalHeader = jurnalHdrService.getById(id);
        jurnalHeader.setStatusVoucher(StatusVoucher.POSTING);
        jurnalHdrService.save(jurnalHeader);
    }

    @Override
    public void unPostingJurnalTrial(Long jurnalHeaderId) {
        // 1. iterate jurnal detil
        // 2. cek di buku besar trial transaksi
        //   ambil saldo isi untuk proses jumlah berjalan LAST REC
        // 3 isi buku besar trial
        // 4.set posting true.



        AccrualConfig accrualConfig = accrualConfigService.getConfig();
        String blnTahunBerjalan  = accrualConfig.getBulanTahunBerjalan();
        String blnTahunLalu = bukuBesarService.getTglProsesBulanLalu();

        //1. iterate jurnal detil
        Iterator<JurnalDetil> jurnalDetilIterator = jurnalDetilService.getByJurnalHdrId(jurnalHeaderId).iterator();
        while (jurnalDetilIterator.hasNext()){
            JurnalDetil jurnalDetil = jurnalDetilIterator.next();

            Long kriteriaBank;
            String kriteriaBank1;

            Long kriteriaCust;
            String kriteriaCust1;

            String kriteriaRel;

            Double totalBerjalan;


            if(jurnalDetil.getBank()==null || jurnalDetil.getBank().equals("")){
                kriteriaBank = 0l;
                kriteriaBank1="%";
            }else{
                kriteriaBank = jurnalDetil.getBank().getId();
                kriteriaBank1 = jurnalDetil.getBank().getKode();
            }

            if(jurnalDetil.getRel()==null || jurnalDetil.getRel().equals("-")){
                kriteriaRel= "%";
            }else{
                kriteriaRel= jurnalDetil.getRel().trim();
            }

            if(jurnalDetil.getCustomer()==null || jurnalDetil.getCustomer().equals("")){
                kriteriaCust= 0L;
                kriteriaCust1="%";
            }else{
                kriteriaCust= jurnalDetil.getCustomer().getId();
                kriteriaCust1= jurnalDetil.getCustomer().getKode();
            }

            ///2. cek di buku besar trial ada transaksi
            List<BukuBesarTrial> bukuBesarTrials = repository.findByBulanTahunAndCoaDtlAndIdBankLikeAndRelLikeAndIdCustomerLikeOrderByIdDesc(blnTahunBerjalan, jurnalDetil.getAccountDetil(), kriteriaBank, kriteriaRel, kriteriaCust);

            BukuBesarTrial bukuBesarTrial1 = bukuBesarTrials.iterator().next();
            totalBerjalan = bukuBesarTrial1.getTotal_berjalan();


            // 3 isi buku besar trial .
            BukuBesarTrial bkBesarTrial = new BukuBesarTrial();

            Double totalDK;
            if(jurnalDetil.getAccountDetil().getIsDebet()){
                totalDK = (jurnalDetil.getDebet() - jurnalDetil.getKredit())*-1;
            }else{
                totalDK = (jurnalDetil.getKredit() - jurnalDetil.getDebet())*-1;
            }
            totalBerjalan = totalBerjalan + totalDK;

            bkBesarTrial.setBulanTahun(blnTahunBerjalan);
            bkBesarTrial.setCoaDtl(jurnalDetil.getAccountDetil());
            bkBesarTrial.setGroupAccount(jurnalDetil.getAccountDetil().getAccountHeader().getGroupAccount());
            bkBesarTrial.setJurnalDetilId(jurnalDetil.getId());
            bkBesarTrial.setTotalDebet(jurnalDetil.getDebet());
            bkBesarTrial.setTotalKredit(jurnalDetil.getKredit());
            bkBesarTrial.setTotal_berjalan(totalBerjalan);
            bkBesarTrial.setKeterangan("BATAL POSTING " +jurnalDetil.getKeterangan().trim());
            bkBesarTrial.setBagian(jurnalDetil.getBagian());
            bkBesarTrial.setNoUrut(jurnalDetil.getJurnalHeader().getNoUrut());
            bkBesarTrial.setNoVoucher(jurnalDetil.getJurnalHeader().getNoVoucher());
            bkBesarTrial.setTglBooking(jurnalDetil.getJurnalHeader().getBookingDate());
            bkBesarTrial.setPenerima(jurnalDetil.getJurnalHeader().getDiBayar());
            if(kriteriaBank==0L){
                bkBesarTrial.setBank(null);
                bkBesarTrial.setIdBank(0L);
            }else{
                bkBesarTrial.setBank(jurnalDetil.getBank());
                bkBesarTrial.setIdBank(jurnalDetil.getBank().getId());
            }
            if (kriteriaRel == "%") {
                bkBesarTrial.setRel("-");
            }else{
                bkBesarTrial.setRel(jurnalDetil.getRel());
            }
            if(kriteriaCust==0L){
                bkBesarTrial.setIdCustomer(0L);
                bkBesarTrial.setCustomer(null);
            }else{
                bkBesarTrial.setIdCustomer(jurnalDetil.getCustomer().getId());
                bkBesarTrial.setCustomer(jurnalDetil.getCustomer());
            }
            repository.save(bkBesarTrial);
        }

        JurnalHeader jurnalHeader = jurnalHdrService.getById(jurnalHeaderId);
        jurnalHeader.setStatusVoucher(StatusVoucher.UNPOSTING);
        jurnalHdrService.save(jurnalHeader);
    }


    @Override
    public Page<BukuBesarTrial> getBBTrialAll(int hal, int jumlah) {
        Sort sort = new Sort(Sort.Direction.ASC,"coaDtl.kodePerkiraan")
                .and(new Sort(Sort.Direction.ASC, "bagian.kode"))
                .and(new Sort(Sort.Direction.ASC, "idCustomer"))
                .and(new Sort(Sort.Direction.ASC,"rel"))
                .and(new Sort(Sort.Direction.ASC,"idBank"))
                .and(new Sort(Sort.Direction.ASC,"id"));
        PageRequest pageRequest = new PageRequest(hal-1, jumlah,sort);
        return repository.findAll(pageRequest);

    }

    @Override
    public Page<BukuBesarTrial> getBBTrialByIdCoaIdBankRelIdCust(Long idCoa, String rel,Long idCust, int hal, int jumlah) {
        Sort sort = new Sort(Sort.Direction.ASC,"coaDtl.kodePerkiraan")
                .and(new Sort(Sort.Direction.ASC, "bagian.kode"))
                .and(new Sort(Sort.Direction.ASC, "idCustomer"))
                .and(new Sort(Sort.Direction.ASC,"rel"))
                .and(new Sort(Sort.Direction.ASC,"idBank"))
                .and(new Sort(Sort.Direction.ASC,"id"));
        PageRequest pageRequest = new PageRequest(hal-1, jumlah,sort);

        AccrualConfig accrualConfig = accrualConfigService.getConfig();
        String blnTahunBerjalan  = accrualConfig.getBulanTahunBerjalan();

        CoaDtl coaDtl = coaDtlService.getById(idCoa);

        String kriteriaBank;
        String kriteriaCust;
        String kriteriaRel;

//        if(kodeBank.equals("0") ){
//            kriteriaBank = "%";
//        }else{
//            kriteriaBank = kodeBank.toString();
//        }

        if(rel.equals("--")){
            kriteriaRel= "%";
        }else{
            kriteriaRel= rel;
        }

        if(idCust ==0L ){
            kriteriaCust= "%";
        }else{
            Customer customer = customerService.getById(idCust);
            kriteriaCust= customer.getKode();
        }
//        kriteriaCust= idCust;
        return repository.findByBulanTahunAndCoaDtlAndRelLikeAndCustomerKodeLike(blnTahunBerjalan, coaDtl, kriteriaRel, kriteriaCust, pageRequest);
    }

    @Override
    public List<BukuBesarTrial> getBBTrialAll() {

        AccrualConfig accrualConfig = accrualConfigService.getConfig();
        String blnTahunBerjalan  = accrualConfig.getBulanTahunBerjalan();

        Sort sort = new Sort(Sort.Direction.ASC,"coaDtl.kodePerkiraan")
                .and(new Sort(Sort.Direction.ASC, "bagian.kode"))
                .and(new Sort(Sort.Direction.ASC, "idCustomer"))
                .and(new Sort(Sort.Direction.ASC,"rel"))
                .and(new Sort(Sort.Direction.ASC,"idBank"))
                .and(new Sort(Sort.Direction.ASC,"id"));
        return repository.findByBulanTahun(blnTahunBerjalan, sort);
    }

    @Override
    public List<BukuBesarTrial> getBBTrialCoa(Long idCoa) {
        AccrualConfig accrualConfig = accrualConfigService.getConfig();
        CoaDtl coaDtl = coaDtlService.getById(idCoa);
        String blnTahunBerjalan  = accrualConfig.getBulanTahunBerjalan();

        Sort sort = new Sort(Sort.Direction.ASC,"coaDtl.kodePerkiraan")
                .and(new Sort(Sort.Direction.ASC, "bagian.kode"))
                .and(new Sort(Sort.Direction.ASC, "idCustomer"))
                .and(new Sort(Sort.Direction.ASC,"rel"))
                .and(new Sort(Sort.Direction.ASC,"idBank"))
                .and(new Sort(Sort.Direction.ASC,"id"));
        return repository.findByBulanTahunAndCoaDtl(blnTahunBerjalan,coaDtl, sort);
    }


}
