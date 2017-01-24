package com.ddabadi.service.impl;

import com.ddabadi.domain.*;
import com.ddabadi.domain.repository.JurnalDetilRepository;
import com.ddabadi.dto.JurnalDetilDto;
import com.ddabadi.enumer.JenisVoucher;
import com.ddabadi.enumer.StatusVoucher;
import com.ddabadi.exception.BankNotFoundException;
import com.ddabadi.service.*;
import com.ddabadi.service.util.FungsiUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by deddy on 5/6/16.
 */

@Service
public class JurnalDetilServiceImpl implements JurnalDetilService {

    private Logger logger=Logger.getLogger(JurnalDetilService.class);

    @Autowired private JurnalDetilRepository jurnalDetilRepository;
    @Autowired private JurnalHdrService jurnalHdrService;
    @Autowired private CoaDtlService coaDtlService;
    @Autowired private BagianService bagianService;
    @Autowired private CustomerService customerService;
    @Autowired private BankService bankService;
    @Autowired private FungsiUtil fungsiUtil;
    @Autowired private AccrualConfigService accrualConfigService;


    @Override
    public JurnalDetil getById(Long id) {
        return jurnalDetilRepository.findOne(id);
    }

    @Override
    public List<JurnalDetil> getByJurnalHdrId(Long jurnalHdrId) {

        return jurnalDetilRepository.findByJurnalHdrId(jurnalHdrId);
    }

    @Override
    public Page<JurnalDetil> getByJurnalHdrIdPage(Long jurnalHdrId, int hal, int jumlah) {
        PageRequest pageRequest=new PageRequest(hal-1,jumlah, Sort.Direction.ASC,"id");
        return jurnalDetilRepository.findByJurnalHdrIdPage(jurnalHdrId, pageRequest);
    }

    @Override
    public Page<JurnalDetil> getByJurnalKreditHdrIdPage(Long jurnalHdrId, int hal, int jumlah) {
        PageRequest pageRequest=new PageRequest(hal-1,jumlah, Sort.Direction.ASC,"id");
        return jurnalDetilRepository.findByJurnalHeaderIdAndDebet(jurnalHdrId,0D, pageRequest);
    }

    @Override
    public JurnalDetil getByJurnalDebetFirst(Long jurnalHdrId) {
        List<JurnalDetil> jurnalDetils = jurnalDetilRepository.findByJurnalHeaderIdAndKreditOrderById(jurnalHdrId,0D);
        if(jurnalDetils.size()>0){
            return  jurnalDetils.iterator().next();
        }else{
            return null;
        }
    }

    @Override
    public JurnalDetil save(JurnalDetilDto jurnalDetilDto) {
        logger.info("save");

        String noGeneratedPiutUsaha;

        AccrualConfig accrualConfig= accrualConfigService.getConfig();

        String relNo;
        // if id = 0 -> add new
        // else
        //    id = jurnalDetil.id
        JurnalDetil jurnalDetil = null;

        if(jurnalDetilDto.getId()==null){
            //new record
            jurnalDetil = new JurnalDetil();
        }else{
            if(jurnalDetilDto.getId()==0L){
                //new record
                jurnalDetil = new JurnalDetil();
            }else{
                //update record
                jurnalDetil = jurnalDetilRepository.findOne(jurnalDetilDto.getId());
            }

        }
        JurnalHeader jurnalHeader = jurnalHdrService.getById(jurnalDetilDto.getJurnalHeaderId());
        jurnalDetil.setJurnalHeader(jurnalHeader);

        CoaDtl coaDtl = coaDtlService.getById(jurnalDetilDto.getAccountDetilId());
        jurnalDetil.setAccountDetil(coaDtl);
        if(accrualConfig.getCoaPiutangUsaha().trim().equals(coaDtl.getKodePerkiraan().toString()) ){
            noGeneratedPiutUsaha = fungsiUtil.createNoPiutangUsaha(jurnalHeader.getIssueDate());
            jurnalDetil.setRel(noGeneratedPiutUsaha);
        }else{
            jurnalDetil.setRel(jurnalDetilDto.getRel());
        }

        Bagian bagian = bagianService.getByKode(jurnalDetilDto.getBagian());
        jurnalDetil.setBagian(bagian);

        jurnalDetil.setKeterangan(jurnalDetilDto.getKeterangan());

        if(jurnalDetilDto.getDk().trim().toUpperCase().equals("D")){
            jurnalDetil.setDebet(jurnalDetilDto.getTotal());
            jurnalDetil.setKredit(0D);
            //jurnalDetil.setDk(DebetKredit.D);
        }else{
            jurnalDetil.setDebet(0D);
            jurnalDetil.setKredit(jurnalDetilDto.getTotal());
            //jurnalDetil.setDk(DebetKredit.K);
        }

//        if(coaDtl.getAutoGenerateNo()==null){
//            relNo=jurnalDetilDto.getRel();
//        }else{
//            if (coaDtl.getAutoGenerateNo().equals(true)) {
//                if(jurnalDetilDto.getRel()=="" ||jurnalDetilDto.getRel()== null){
//                    //FungsiUtil fungsiUtil = new FungsiUtil();
//                    relNo = fungsiUtil.createNoRel(coaDtl,jurnalHeader.getIssueDate());
//                }else{
//                    relNo=jurnalDetilDto.getRel();
//                }
//            }else{
//                relNo=jurnalDetilDto.getRel();
//            }
//
//        }
//
//        jurnalDetil.setRel(relNo);

        if(jurnalDetilDto.getCustomerId()==0){
            jurnalDetil.setCustomer(null);
        }else{
            jurnalDetil.setCustomer(customerService.getById(jurnalDetilDto.getCustomerId()));
        }

        if(jurnalDetilDto.getBankId()==0){
            jurnalDetil.setBank(null);
        }else{
            jurnalDetil.setBank(bankService.getById(jurnalDetilDto.getBankId()));
        }

        return jurnalDetilRepository.saveAndFlush(jurnalDetil);
    }

    @Override
    public JurnalDetil saveJurnalDetil(JurnalDetil jurnalDetil) {
        return jurnalDetilRepository.saveAndFlush(jurnalDetil);
    }

    @Override
    public Integer delete(Long idJurnalDetil) {
        int  hasil=0;

        try {
            jurnalDetilRepository.delete(idJurnalDetil);
            hasil   =1;
        }catch (Exception ex){
            logger.info("error delete : " + ex.getMessage());
        }

        return hasil;
    }

    @Override
    public List<JurnalDetil> getByCoaTglIssue(CoaDtl coaDtl, Date tgl1, Date tgl2) {
        return jurnalDetilRepository.findByAccountDetilAndJurnalHeaderIssueDateBetweenOrderById(coaDtl, tgl1, tgl2);
    }

    @Override
    public List<JurnalDetil> getByCoaBagianTglIssue(CoaDtl coaDtl, Bagian bagian, Date tgl1, Date tgl2) {
        return jurnalDetilRepository.findByAccountDetilAndBagianAndJurnalHeaderIssueDateBetweenOrderById(coaDtl, bagian, tgl1, tgl2);
    }

    @Override
    public Page<JurnalDetil> getVoucherSuratTransfer(int hal, int jumlah) {

//        Bank bank = bankService.getById(idBank);
//        if(bank==null){
//            throw new BankNotFoundException();
//        }
//        Customer customer = customerService.getById(idCustomer);
        PageRequest pageRequest = new PageRequest(hal-1, jumlah);

        return jurnalDetilRepository.findByJurnalHeaderJenisVoucherAndJurnalHeaderIsValidasiPembayaranIsTrueAndJurnalHeaderStatusVoucherAndJurnalHeaderIsTarikPembayaranFalseAndDebetOrderByJurnalHeaderIssueDate(JenisVoucher.PENGELUARAN,StatusVoucher.UNPOSTING,0D, pageRequest) ;
    }

    @Override
    public Page<JurnalDetil> getVoucherSuratTransferBankNoUrut(Long idBank, String noUrut,int hal, int jumlah) {
        PageRequest pageRequest = new PageRequest(hal-1, jumlah);

        String kriteriaNoUrut ;

        if(noUrut.equals("--")){
            kriteriaNoUrut="%";
        }else{
            kriteriaNoUrut=noUrut.trim()+"%";
        }
        Bank bank = bankService.getById(idBank);

            return jurnalDetilRepository.findByJurnalHeaderJenisVoucherAndJurnalHeaderStatusVoucherAndDebetAndBankAndJurnalHeaderNoUrutLikeAndJurnalHeaderIsTarikPembayaranIsFalseOrderByJurnalHeaderIssueDate(JenisVoucher.PENGELUARAN, StatusVoucher.UNPOSTING, 0D, bank,kriteriaNoUrut, pageRequest);
    }

    @Override
    public Double getTotalDebet(Long idHdr) {
        Double hasil =0D;
        hasil = jurnalDetilRepository.findTotalDebet(idHdr);
        return hasil;
    }

    @Override
    public Double getTotalKredit(Long idHdr) {
        Double hasil =0D;
        hasil = jurnalDetilRepository.findTotalKredit(idHdr);
        return hasil;
    }

}
