package com.ddabadi.service.impl;

import com.ddabadi.domain.Bank;
import com.ddabadi.domain.JurnalDetil;
import com.ddabadi.domain.JurnalHeader;
import com.ddabadi.domain.User;
import com.ddabadi.domain.repository.JurnalHdrRepository;
import com.ddabadi.dto.JurnalHdrDto;
import com.ddabadi.enumer.JenisVoucher;
import com.ddabadi.enumer.StatusVoucher;
import com.ddabadi.service.*;
import com.ddabadi.service.util.FungsiUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by deddy on 5/3/16.
 */

@Service
public class JurnalHdrServiceImpl implements JurnalHdrService {

    @Autowired private JurnalHdrRepository repository;
    @Autowired private JurnalDetilService jurnalDetilService;
    @Autowired private UserService userService;
    @Autowired private BankService bankService;
    @Autowired private BukuBesarTrialService bukuBesarTrialService;
    @Autowired private FungsiUtil fungsi;
    @Autowired private AccrualConfigService accrualConfigService;

    private Logger logger = Logger.getLogger(JurnalHdrService.class);

    @Override
    public Page<JurnalHeader> getByIdUserTanggal(Long idUser, Date tgl1, Date tgl2, int hal, int jumlah) {
        logger.info("get by id User");
        PageRequest pageRequest = new PageRequest(hal-1, jumlah, Sort.Direction.ASC,"id");
        return repository.findByIdfindByIdTanggal(tgl1, tgl2, idUser, pageRequest);
    }

    @Override
    public Page<JurnalHeader> getByIdUserTanggalStatus(Long idUser, Date tgl1, Date tgl2, StatusVoucher statusVoucher, int hal, int jumlah) {
        logger.info("get by id User");
        PageRequest pageRequest = new PageRequest(hal-1, jumlah, Sort.Direction.ASC,"id");
        return repository.findByIssueDateBetweenAndUserIdAndStatusVoucher(tgl1, tgl2, idUser, statusVoucher, pageRequest);
    }

    @Override
    public JurnalHeader getByIdJurnalByIdUser(Long idJurnal, Long idUser) {
        logger.info("get by id jurnal - id User");
        JurnalHeader jurnalHeader = null;
        jurnalHeader= repository.findOne(idJurnal);
        if(jurnalHeader==null){
            // jurnal tidak ketemu
        }else   {
            if(jurnalHeader.getUser().getId()==idUser){
                //jurnal header milik id user
            }else   {
                // jurnal header milik id user LAIN
                jurnalHeader=null;
            }
        }
        return jurnalHeader;
    }

    @Override
    public JurnalHeader getById(Long idJurnal) {
        logger.info("get by id jurnal ");
        return repository.findOne(idJurnal);
    }

    @Override
    public Page<JurnalHeader> getByTanggalIssuePage(Date tgl1, Date tgl2, int hal, int jumlah) {
        logger.info("get by tanggal issue");
        PageRequest pageRequest = new PageRequest(hal-1, jumlah, Sort.Direction.ASC,"id");
        return repository.findByTanggalIssue(tgl1, tgl1, pageRequest);

    }

    @Override
    public JurnalHeader insertRec(JurnalHdrDto jurnalHeaderDto) throws ParseException {

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date tglIssue = sdf1.parse(jurnalHeaderDto.getIssueDate());


        User user = userService.getById(jurnalHeaderDto.getUser());
        JurnalHeader jurnalHeader = new JurnalHeader();
        if(jurnalHeaderDto.getJenisVoucher().equals(JenisVoucher.PENGELUARAN)){
            jurnalHeader.setBookingDate(null);
        }else{
            jurnalHeader.setBookingDate(tglIssue);
        }
        jurnalHeader.setNoVoucher(null);
        jurnalHeader.setNoUrut(null);
        jurnalHeader.setDiBayar(jurnalHeaderDto.getDiBayar());
        jurnalHeader.setId(jurnalHeaderDto.getId());
        jurnalHeader.setIssueDate(tglIssue);
        jurnalHeader.setJenisVoucher(jurnalHeaderDto.getJenisVoucher());
        jurnalHeader.setStatusVoucher(StatusVoucher.UNPOSTING);
        jurnalHeader.setUser(user);

        return repository.saveAndFlush(jurnalHeader);
    }

    @Override
    public Page<JurnalHeader> getVoucherPengeluaranByIssueDate(Date tgl1, Date tgl2, int hal, int jumlah) {
        PageRequest pageRequest = new PageRequest(hal -1, jumlah, Sort.Direction.ASC,"id" );
        return repository.findByJenisVoucherAndIssueDateBetween(JenisVoucher.PENGELUARAN, tgl1, tgl2, pageRequest);
    }

    @Override
    public Page<JurnalHeader> getVoucherPengeluaranIsTarikByIssueDate(Date tgl1, Date tgl2, int hal, int jumlah) {
        PageRequest pageRequest = new PageRequest(hal -1, jumlah, Sort.Direction.ASC, "id");
        return repository.findByJenisVoucherAndIssueDateBetweenAndIsTarikPembayaranTrue(JenisVoucher.PENGELUARAN, tgl1, tgl2, pageRequest);
    }

    @Override
    public JurnalHeader approve(Long idHdr, Long idUser, Date tglBook) {


        JurnalHeader jurnalHeader = repository.findOne(idHdr);
        jurnalHeader.setBookingDate(tglBook);
        jurnalHeader.setNoVoucher(fungsi.createNoVoucher(jurnalHeader.getJenisVoucher(), tglBook));
        jurnalHeader.setIsIsiBookDate(true);
        repository.saveAndFlush(jurnalHeader);

        bukuBesarTrialService.postingJurnalTrial(idHdr);
        return jurnalHeader;
    }

    @Override
    public JurnalHeader save(JurnalHdrDto jurnalHeaderDto) throws ParseException {

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date tglIssue = sdf1.parse(jurnalHeaderDto.getIssueDate());

        User user = userService.getById(jurnalHeaderDto.getUser());

        JurnalHeader jurnalHeader = repository.findOne(jurnalHeaderDto.getId());
        if(jurnalHeader.getJenisVoucher()== JenisVoucher.PENGELUARAN){
            // jika no voucher sudah ada tidak boleh create no urut lagi
            if(jurnalHeader.getNoUrut()=="" ||jurnalHeader.getNoUrut()==(null)  ){
                jurnalHeader.setNoUrut(fungsi.createNoUrut(jurnalHeaderDto.getUser(), tglIssue));
            }
        }else{

            // jika no urut sudah ada tidak boleh create no urut lagi
            if(jurnalHeader.getNoUrut()=="" ||jurnalHeader.getNoUrut()==null  ){
                jurnalHeader.setNoUrut(fungsi.createNoUrut(jurnalHeaderDto.getUser(), tglIssue));

            }

            // jika no voucher sudah ada tidak boleh create no vouch lagi
            // tanggal booking bisa ganti sesuai tgl issue
            jurnalHeader.setBookingDate(tglIssue);


            if(jurnalHeader.getNoVoucher()=="" ||jurnalHeader.getNoVoucher()==null) {
                jurnalHeader.setNoVoucher(fungsi.createNoVoucher(jurnalHeaderDto.getJenisVoucher(), tglIssue));
            }

        }

        jurnalHeader.setDiBayar(jurnalHeaderDto.getDiBayar());
        jurnalHeader.setIssueDate(tglIssue);
        jurnalHeader.setJenisVoucher(jurnalHeaderDto.getJenisVoucher());
        jurnalHeader.setStatusVoucher(StatusVoucher.UNPOSTING);
        jurnalHeader.setUser(user);

        return repository.saveAndFlush(jurnalHeader);
    }

    @Override
    public JurnalHeader save(JurnalHeader jurnalHeader) {
        return repository.saveAndFlush(jurnalHeader);
    }

    @Override
    public JurnalHeader saveOtomatis(JurnalHeader jurnalHeader) {


        // tanggal booking bisa ganti sesuai tgl issue
        JurnalHeader hdr=new JurnalHeader();
        hdr.setIssueDate(jurnalHeader.getIssueDate());
        hdr.setBookingDate(jurnalHeader.getIssueDate());
        hdr.setDiBayar("");
        hdr.setJenisVoucher(JenisVoucher.PENGELUARAN);
        hdr.setStatusVoucher(StatusVoucher.UNPOSTING);
        hdr.setUser(jurnalHeader.getUser());

        JurnalHeader hd=repository.saveAndFlush(hdr);
        hd.setNoUrut(fungsi.createNoUrut(hd.getUser().getId(), hd.getIssueDate()));
        hd.setNoVoucher(fungsi.createNoVoucher(hd.getJenisVoucher(), hd.getIssueDate()));
        return save(hd);
    }

    @Override
    public boolean posting(Long idHdr) {
        JurnalHeader jurnalHeader = repository.findOne(idHdr);
        if(jurnalHeader==null){
            return false;
        }
        if (jurnalHeader.getNoVoucher().equals("") ||jurnalHeader.getNoVoucher()==null) {
            return  false;
        }
        jurnalHeader.setStatusVoucher(StatusVoucher.POSTING);
        repository.saveAndFlush(jurnalHeader);
        return true;
    }

    @Override
    public String deleteFisikVoucher(Long idHdr) {


        // cek jika voucher balance debet dan kredit bisa
        String pesan="OK";
        JurnalHeader jurnalHeader = repository.findOne(idHdr);
        if(jurnalHeader.getStatusVoucher()==StatusVoucher.UNPOSTING){

            if(jurnalHeader.isTarikPembayaran()==true){
                pesan = "Transaksi sudah pernah di tarik untuk pembayaran";
            }else{

                // cek ada detil tidak transaksi
                List<JurnalDetil> jurnalDetilss = jurnalDetilService.getByJurnalHdrId(jurnalHeader.getId());

                if(jurnalDetilss.size()>0){
                    Double totDebet = jurnalDetilService.getTotalDebet(idHdr);
                    Double totKredit = jurnalDetilService.getTotalKredit(idHdr);
                    System.out.println("total debet = " + totDebet + "  - " + totKredit);
                    //cek debet sama ga dg kredit
                    if (!(Double.compare(totDebet,totKredit)==0)){
                        //delete
                        Iterator<JurnalDetil> jurnalDetils = jurnalDetilService.getByJurnalHdrId(jurnalHeader.getId()).iterator();
                        while(jurnalDetils.hasNext()){
                            JurnalDetil jurnalDetil = jurnalDetils.next();
                            jurnalDetilService.delete(jurnalDetil.getId());
                        }
                        repository.delete(jurnalHeader.getId());
                        pesan="OK";
                    }else{
                        pesan = "Total Debet sama dengan Kredit, data tidak bisa di hapus! ";
                    }

                }else{
                    //Detil belom ada. hapussss header
                    repository.delete(jurnalHeader.getId());
                }
            }

        }else{
            //STATUS bukan UNPOSTING, tidak boleh hapus
            pesan="Status voucher = " + jurnalHeader.getStatusVoucher().toString();
        }
        return pesan;
    }

    @Override
    public Long prosesJurnalBalik(Long idDebet, Long idKredit, Long idUser, Long idBank) {

        JurnalDetil jurnalDetilDebet = jurnalDetilService.getById(idDebet);
        JurnalDetil jurnalDetilKredit = jurnalDetilService.getById(idKredit);
        User user = userService.getById(idUser);
        Bank bank = bankService.getById(idBank);


        Date tglJurnal = jurnalDetilDebet.getJurnalHeader().getIssueDate();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.setTime(tglJurnal);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH,1);

        Date tglIssueBaru = calendar.getTime();

        String noUrut;

        noUrut = fungsi.createNoUrut(idUser,tglIssueBaru);

        JurnalHeader jurnalHeader = new JurnalHeader();
        jurnalHeader.setUser(user);
        jurnalHeader.setStatusVoucher(StatusVoucher.UNPOSTING);
        jurnalHeader.setIssueDate(tglIssueBaru);
        jurnalHeader.setJenisVoucher(JenisVoucher.PENGELUARAN);
        jurnalHeader.setBookingDate(null);
        jurnalHeader.setDiBayar(jurnalDetilKredit.getCustomer().getNama());
        jurnalHeader.setNoUrut(noUrut);
        jurnalHeader.setNoVoucher(null);
        repository.saveAndFlush(jurnalHeader);

        //id Kredit jadi jurnal debet
        JurnalDetil jurnalDebet = new JurnalDetil();
        jurnalDebet.setAccountDetil(jurnalDetilKredit.getAccountDetil());
        jurnalDebet.setCustomer(jurnalDetilKredit.getCustomer());
        jurnalDebet.setBank(bank);
        jurnalDebet.setRel(jurnalDetilKredit.getRel());
        jurnalDebet.setBagian(jurnalDetilKredit.getBagian());
        jurnalDebet.setDebet(jurnalDetilKredit.getKredit());
        jurnalDebet.setJumlah(0D);
        jurnalDebet.setDebet(jurnalDetilKredit.getKredit());
        jurnalDebet.setKredit(0D);
        jurnalDebet.setJurnalHeader(jurnalHeader);
        jurnalDebet.setKeterangan(jurnalDetilKredit.getKeterangan());
        jurnalDebet.setTipeVoucher(JenisVoucher.PENGELUARAN);
        jurnalDetilService.saveJurnalDetil(jurnalDebet);

        //id debet jadi jurnal kredit
        JurnalDetil jurnalKredit = new JurnalDetil();
        jurnalKredit.setAccountDetil(jurnalDetilDebet.getAccountDetil());
        jurnalKredit.setCustomer(jurnalDetilKredit.getCustomer());
        jurnalKredit.setBank(bank);
        jurnalKredit.setRel(jurnalDetilKredit.getRel());
        jurnalKredit.setBagian(jurnalDetilKredit.getBagian());
        jurnalKredit.setDebet(jurnalDetilKredit.getKredit());
        jurnalKredit.setJumlah(0D);
        jurnalKredit.setKredit(jurnalDetilKredit.getKredit());
        jurnalKredit.setDebet(0D);
        jurnalKredit.setJurnalHeader(jurnalHeader);
        jurnalKredit.setKeterangan(jurnalDetilKredit.getKeterangan());
        jurnalKredit.setTipeVoucher(JenisVoucher.PENGELUARAN);
        jurnalDetilService.saveJurnalDetil(jurnalKredit);

        return jurnalHeader.getId();
    }

    @Override
    public List<JurnalHeader> getAllCoaHdrByStatusVoucher(StatusVoucher statusVoucher, Date tglIssueAwal, Date tglIssueAkhir) {
        return repository.findByStatusVoucherAndIssueDateBetween(statusVoucher, tglIssueAwal, tglIssueAkhir);
    }

    @Override
    public Page<JurnalHeader> getByIssueDateBetweenAndStatusVoucherAndJenisVoucher(Date tgl1, Date tgl2, StatusVoucher statusVoucher, JenisVoucher jenisVoucher, int hal, int jumlah) {
        Sort sort = new Sort(Sort.Direction.ASC,"issueDate").and( new Sort(Sort.Direction.ASC, "id"));

        PageRequest pageRequest = new PageRequest(hal-1, jumlah, sort);


        if(jenisVoucher.equals(JenisVoucher.PENGELUARAN)){
            // jika voucher pengeluaran di bedakan pakai IS_VALIDASI_PEMBAYARAN
            return repository.findByIssueDateBetweenAndStatusVoucherAndJenisVoucherAndIsValidasiPembayaranIsFalse(tgl1, tgl2, statusVoucher, jenisVoucher, pageRequest);
        }else{
            // jika NON voucher pengeluaran set POSTING = TRUE
            return repository.findByIssueDateBetweenAndStatusVoucherAndJenisVoucher(tgl1, tgl2, statusVoucher, jenisVoucher, pageRequest);
        }


    }

    public Page<JurnalHeader> getJurnalBelumPostingByIssueDateBetweenAndStatusVoucherAndJenisVoucher(Date tgl1, Date tgl2, StatusVoucher statusVoucher, JenisVoucher jenisVoucher, int hal, int jumlah) {
        Sort sort = new Sort(Sort.Direction.ASC,"issueDate").and( new Sort(Sort.Direction.ASC, "id"));

        PageRequest pageRequest = new PageRequest(hal-1, jumlah, sort);


        if(jenisVoucher.equals(JenisVoucher.PENGELUARAN)){
            // jika voucher pengeluaran di bedakan pakai IS_VALIDASI_PEMBAYARAN
            //return repository.findByIssueDateBetweenAndNoUrutIsNotNullAndStatusVoucherAndJenisVoucherAndIsValidasiPembayaranIsFalse(tgl1, tgl2, statusVoucher, jenisVoucher, pageRequest);

            return repository.findByIssueDateBetweenAndNoUrutIsNotNullAndStatusVoucherAndJenisVoucherAndIsTarikPembayaranIsFalse(tgl1, tgl2, statusVoucher, jenisVoucher, pageRequest);
        }else{
            // jika NON voucher pengeluaran set POSTING = TRUE
            return repository.findByIssueDateBetweenAndNoUrutIsNotNullAndStatusVoucherAndJenisVoucher(tgl1, tgl2,statusVoucher,jenisVoucher,pageRequest);
        }


    }


    @Override
    public String validasiPembayaran(Long idHdr) {
        JurnalHeader jurnalHeader= repository.findOne(idHdr);
        if(jurnalHeader.getNoUrut()==null ){
            //|| jurnalHeader.getNoUrut().length()<=0){
            return "Belum ada no Urut";
        }else{
            jurnalHeader.setIsValidasiPembayaran(true);
            repository.save(jurnalHeader);
            return "OK";
        }
    }


}
