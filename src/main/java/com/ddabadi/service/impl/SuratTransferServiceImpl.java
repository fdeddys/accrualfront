package com.ddabadi.service.impl;

import com.ddabadi.domain.*;
import com.ddabadi.domain.repository.SuratTransferDtRepository;
import com.ddabadi.domain.repository.SuratTransferHdRepository;
import com.ddabadi.dto.SuratTransferDtDto;
import com.ddabadi.dto.SuratTransferHdDto;
import com.ddabadi.exception.InvalidDateException;
import com.ddabadi.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.xml.parsers.SAXParser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by deddy on 10/4/16.
 */

@Service
public class SuratTransferServiceImpl implements SuratTransferService {

    @Autowired private SuratTransferHdRepository suratTransferHdRepository;
    @Autowired private SuratTransferDtRepository suratTransferDtRepository;
    @Autowired private BankService bankService;
//    @Autowired private CustomerService customerService;
    @Autowired private JurnalDetilService jurnalDetilService;
    @Autowired private JurnalHdrService jurnalHdrService;

    private Logger logger = Logger.getLogger(SuratTransferService.class);

    @Override
    public SuratTransferHd saveHd(SuratTransferHdDto suratTransferHdDto) {

        logger.info("Save hd");
        SuratTransferHd suratTransferHd = new SuratTransferHd();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date tglProses = sdf.parse(suratTransferHdDto.getTglSurat());
            suratTransferHd.setTglSurat(tglProses);
        }catch (Exception ex){
            throw new InvalidDateException();
        }

        Bank bank = bankService.getById(suratTransferHdDto.getBankId());
//        Customer customer=customerService.getById(suratTransferHdDto.getCustomerId());

        suratTransferHd.setUserUpdate(suratTransferHdDto.getUserUpdate());
        suratTransferHd.setNoCek(suratTransferHdDto.getNoCek());
        suratTransferHd.setTotal(0D);
        suratTransferHd.setBank(bank);
//        suratTransferHd.setCustomer(customer);
        suratTransferHd.setIsApprove(false);
        suratTransferHd.setLastUpdate(new Date());

        return suratTransferHdRepository.save(suratTransferHd);
    }

    @Override
    public SuratTransferHd editHd(Long idEdit, SuratTransferHd suratTransferHd) {
        SuratTransferHd suratTransferHdUpd = suratTransferHdRepository.findOne(idEdit);

//        suratTransferHdUpd.setCustomer(suratTransferHd.getCustomer());
        suratTransferHdUpd.setTotal(suratTransferHd.getTotal());
        suratTransferHdUpd.setBank(suratTransferHd.getBank());
        suratTransferHdUpd.setIsApprove(suratTransferHd.getIsApprove());
        suratTransferHdUpd.setLastUpdate(new Date());
        suratTransferHdUpd.setNoApprove(suratTransferHd.getNoApprove());
        suratTransferHdUpd.setNoCek(suratTransferHd.getNoCek());
        suratTransferHdUpd.setTglSurat(suratTransferHd.getTglSurat());
        suratTransferHdUpd.setUserUpdate(suratTransferHd.getUserUpdate());
        return suratTransferHdRepository.saveAndFlush(suratTransferHdUpd);
    }

    @Override
    public SuratTransferHd getHdById(Long id) {
        logger.info("get by id hd");
        return suratTransferHdRepository.findOne(id);
    }

    @Override
    public SuratTransferHd ApproveById(Long id) {
        return null;
    }

    @Override
    public Page<SuratTransferHd> getHdByTanggalSuratNoApprove(Date tgl1, Date tgl2, String noApprove, int hal, int jumlah) {
        logger.info("get Hd By Tanggal " + tgl1.toString() + " sd " + tgl2.toString() + " Surat NoApprove " + noApprove);
        Sort sorting = new Sort(Sort.Direction.ASC,"id");
        PageRequest pageRequest = new PageRequest(hal-1, jumlah, sorting);
        //return suratTransferHdRepository.findByTglSuratBetweenAndNoApproveLikeOrNoApproveIsNull(tgl1, tgl2, "%" + noApprove + "%", pageRequest);
        return suratTransferHdRepository.findByTglSuratNoApprove(tgl1, tgl2, "%" + noApprove + "%", pageRequest);
        //return suratTransferHdRepository.findByNoApproveLikeOrNoApproveIsNull("%", pageRequest);
    }

    @Override
    public SuratTransferDt saveDt(SuratTransferDtDto suratTransferDtDto) {
        logger.info("save dt");
        SuratTransferHd suratTransferHd = suratTransferHdRepository.findOne(suratTransferDtDto.getSuratTransferHdId());
        JurnalDetil jurnalDetil= jurnalDetilService.getById(suratTransferDtDto.getJurnalDetilId());

        JurnalHeader jurnalHeader = jurnalHdrService.getById(jurnalDetil.getJurnalHeader().getId());
        jurnalHeader.setIsTarikPembayaran(true);
        jurnalHdrService.save(jurnalHeader);

        SuratTransferDt suratTransferDt = new SuratTransferDt();
        suratTransferDt.setJurnalDetil(jurnalDetil);
        suratTransferDt.setSuratTransferHd(suratTransferHd);



        return suratTransferDtRepository.saveAndFlush(suratTransferDt);
    }

    @Override
    public SuratTransferDt getDtById(Long id) {
        logger.info("get by id dt");
        return suratTransferDtRepository.findOne(id);
    }

    @Override
    public void delDtById(Long id) {
        logger.info("delete dt by id");
        suratTransferDtRepository.delete(id);
    }

    @Override
    public Page<SuratTransferDt> getDtByHd(Long idHd, int hal, int jumlah) {
        logger.info("find dt by hd Id");
        SuratTransferHd suratTransferHd=suratTransferHdRepository.findOne(idHd);
        PageRequest pageRequest = new PageRequest(hal-1,jumlah);
        return suratTransferDtRepository.findBySuratTransferHdOrderById(suratTransferHd,pageRequest);
    }

    @Override
    public List<SuratTransferDt> getDtByHd(Long idHd) {
        logger.info("find dt by hd Id");
        SuratTransferHd suratTransferHd=suratTransferHdRepository.findOne(idHd);
        return suratTransferDtRepository.findBySuratTransferHdOrderById(suratTransferHd);
    }

}
