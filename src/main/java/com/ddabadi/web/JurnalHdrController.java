package com.ddabadi.web;

import com.ddabadi.domain.JurnalHeader;
import com.ddabadi.domain.User;
import com.ddabadi.dto.JurnalBooking;
import com.ddabadi.dto.JurnalHdrDto;
import com.ddabadi.enumer.JenisVoucher;
import com.ddabadi.enumer.StatusVoucher;
import com.ddabadi.service.JurnalHdrService;
import com.ddabadi.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by deddy on 5/5/16.
 */

@RestController
@RequestMapping(value = "api/transaksi/jurnalHeader",produces ="application/json")
public class JurnalHdrController {

    @Autowired private JurnalHdrService jurnalHdrService;
    @Autowired private UserService userService;
    private Logger logger = Logger.getLogger(JurnalHdrController.class);

    @RequestMapping(value = "new",
                    method = RequestMethod.GET)
    JurnalHeader newRec(){
        return new JurnalHeader();
    }

    @RequestMapping("/idUser/{idUser}/hal/{hal}/jumlah/{jumlah}/tgl1/{tgl1}/tgl2/{tgl2}/statusV/{statusV}")
    public Page<JurnalHeader>   getByIdTanggalStatusPage(@PathVariable("idUser")Long idUser,
                                                         @PathVariable("hal")int hal,
                                                         @PathVariable("jumlah")int jumlah,
                                                         @PathVariable("tgl1")String tgl1,
                                                         @PathVariable("tgl2")String tgl2,
                                                         @PathVariable("statusV")String statusV){


        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date tglAwal=null;
        Date tglAkhir=null;
        try {
            tglAwal= sdf1.parse(tgl1);
            tglAkhir= sdf2.parse(tgl2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(statusV.equals("ALL")){
            return  jurnalHdrService.getByIdUserTanggal(idUser, tglAwal, tglAkhir, hal,jumlah);
        }else{
            return  jurnalHdrService.getByIdUserTanggalStatus(idUser, tglAwal, tglAkhir, StatusVoucher.valueOf(statusV),hal,jumlah);
        }
    }

    @RequestMapping(value = "insert/idUser/{idUser}",
                    method = RequestMethod.POST,
                    consumes = "application/json")
    JurnalHeader insert(@RequestBody JurnalHdrDto jurnalHeaderDto, @PathVariable("idUser")Long idUser) throws ParseException {

        return jurnalHdrService.insertRec(jurnalHeaderDto);
    }

    @RequestMapping(value = "id/{idHdr}",
                    method = RequestMethod.DELETE,
                    produces = "application/json")
    Map<String,Object> deleteFisikVoucher(@PathVariable("idHdr")Long idHdr){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("hasil",jurnalHdrService.deleteFisikVoucher(idHdr));
        return map ;
    }

    @RequestMapping(value = "save",
            method = RequestMethod.POST,
            consumes = "application/json")
    JurnalHeader save(@RequestBody JurnalHdrDto jurnalHeaderDto) throws ParseException {
        return jurnalHdrService.save(jurnalHeaderDto);
    }

    @RequestMapping(value = "id/{idHdr}/user/{idUser}",
                    method = RequestMethod.GET)
    JurnalHeader getByIdByUserId(@PathVariable("idHdr")Long idHdr,
                                 @PathVariable("idUser")Long idUser){
        return jurnalHdrService.getByIdJurnalByIdUser(idHdr, idUser);
    }

    @RequestMapping(value = "id/{idHdr}",
            method = RequestMethod.GET)
    JurnalHeader getById(@PathVariable("idHdr")Long idHdr){
        return jurnalHdrService.getById(idHdr);
    }

    @RequestMapping(value = "isiBookingDate/id/{idHdr}",
                    method = RequestMethod.POST)
    JurnalHeader isiBooking(@RequestBody JurnalBooking jurnalBooking,
                            @PathVariable("idHdr")Long idHdr){

        logger.info("isi booking date"+ idHdr.toString());
        JurnalHeader jurnalHeader = jurnalHdrService.getById(idHdr);

        if(jurnalBooking==null){
            logger.info("Jurnal booking tidak ketemu " );
            return null;
        }else{
            if(jurnalHeader.getJenisVoucher().equals(JenisVoucher.PENGELUARAN)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date tglBook = null;
                try {
                    tglBook = sdf.parse(jurnalBooking.getTglBook());
                    return jurnalHdrService.approve(idHdr,jurnalBooking.getUserId(),tglBook);
                } catch (ParseException e) {
                    e.printStackTrace();
                    logger.info("Gagal parse tanggal book "+jurnalBooking.getTglBook());
                    return null;
                }
            }else{
                logger.info("Bukan jurnal pengeluaran");
                return null;
            }
        }
    };

    @RequestMapping(value = "jurnalPengeluaran/hal/{hal}/jumlah/{jumlah}/tgl1/{tgl1}/tgl2/{tgl2}",
                    method = RequestMethod.GET)
    Page<JurnalHeader> getJurnalPengeluaranByTglPage(@PathVariable("hal")int hal,
                                                     @PathVariable("jumlah")int jumlah,
                                                     @PathVariable("tgl1")String tgl1,
                                                     @PathVariable("tgl2")String tgl2){

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date tglAwal = sdf1.parse(tgl1);
            Date tglAkhir = sdf1.parse(tgl2);
            return jurnalHdrService.getVoucherPengeluaranByIssueDate(tglAwal,tglAkhir, hal, jumlah);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "byJenisVoucher/issueDate/{tgl1}/{tgl2}/jenisVouc/{jenisVouc}/page/{hal}/{jumlah}",
                    method = RequestMethod.GET)
    Page<JurnalHeader> getJurnalByJenis(@PathVariable("tgl1")String tgl1,
                                        @PathVariable("tgl2")String tgl2,
                                        @PathVariable("jenisVouc")int jenisVouch,
                                        @PathVariable("hal")int hal,
                                        @PathVariable("jumlah")int jumlah){
        // 0=PENERIMAA
        // 1=PENGELUARAN,
        // 2=PEMINDAHAN
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date tglAwal = sdf1.parse(tgl1);
            Date tglAkhir = sdf1.parse(tgl2);
            JenisVoucher jenisVoucher =null ;
            switch (jenisVouch){
                case 0 : jenisVoucher= JenisVoucher.PENERIMAAN;
                        break;
                case 1: jenisVoucher = JenisVoucher.PENGELUARAN;
                        break;
                case 2: jenisVoucher=JenisVoucher.PEMINDAHAN;
                        break;
            }
            StatusVoucher statusVoucher = StatusVoucher.UNPOSTING;
            return jurnalHdrService.getByIssueDateBetweenAndStatusVoucherAndJenisVoucher(tglAwal, tglAkhir, statusVoucher, jenisVoucher, hal, jumlah);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "listVoucherBelumPosting/issueDate/{tgl1}/{tgl2}/jenisVouc/{jenisVouc}/page/{hal}/{jumlah}",
            method = RequestMethod.GET)
    Page<JurnalHeader> getJurnalBelumPostingByJenis(@PathVariable("tgl1")String tgl1,
                                        @PathVariable("tgl2")String tgl2,
                                        @PathVariable("jenisVouc")int jenisVouch,
                                        @PathVariable("hal")int hal,
                                        @PathVariable("jumlah")int jumlah){
        // 0=PENERIMAA
        // 1=PENGELUARAN,
        // 2=PEMINDAHAN
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date tglAwal = sdf1.parse(tgl1);
            Date tglAkhir = sdf1.parse(tgl2);
            JenisVoucher jenisVoucher =null ;
            switch (jenisVouch){
                case 0 : jenisVoucher= JenisVoucher.PENERIMAAN;
                    break;
                case 1: jenisVoucher = JenisVoucher.PENGELUARAN;
                    break;
                case 2: jenisVoucher=JenisVoucher.PEMINDAHAN;
                    break;
            }
            StatusVoucher statusVoucher = StatusVoucher.UNPOSTING;
            if(jenisVouch==1){
                return jurnalHdrService.getJurnalBelumPostingByIssueDateBetweenAndStatusVoucherAndJenisVoucher(tglAwal, tglAkhir, statusVoucher, jenisVoucher, hal, jumlah);
            }else{
                return jurnalHdrService.getJurnalBelumPostingByIssueDateBetweenAndStatusVoucherAndJenisVoucher(tglAwal, tglAkhir, statusVoucher, jenisVoucher, hal, jumlah);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping(value = "/isiBookingDate/jurnalPengeluaranIsTarik/{tgl1}/{tgl2}/{hal}/{jumlah}",
                    method = RequestMethod.GET)
    Page<JurnalHeader> getJurnalPengeluaranIsTarikTglPage(@PathVariable String tgl1,
                                                          @PathVariable String tgl2,
                                                          @PathVariable int hal,
                                                          @PathVariable int jumlah){

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date tglAwal = sdf1.parse(tgl1);
            Date tglAkhir = sdf1.parse(tgl2);
            return jurnalHdrService.getVoucherPengeluaranIsTarikByIssueDate(tglAwal, tglAkhir, hal, jumlah);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping(value = "jurnalBalik/idUser/{idUser}/debet/{idDebet}/kredit/{idKredit}/bank/{idBank}",
            method = RequestMethod.POST,
            consumes = "application/json")
    public Map<String,Object>  prosesJurnalBalik(@PathVariable("idUser")Long idUser,
                           @PathVariable("idDebet")Long idDebet,
                           @PathVariable("idKredit")Long idKredit,
                           @PathVariable("idBank")Long idBank)  {

        Map<String,Object> map = new HashMap<String, Object>();
        Long idHasil = jurnalHdrService.prosesJurnalBalik(idDebet,idKredit,idUser,idBank);
        map.put("idBaru",idHasil);
        return map;
    }

    @RequestMapping(value = "postingVoucher/idHdr/{idHdr}",
                    method = RequestMethod.POST,
                    consumes = "application/json")
    public boolean  postingVoucher(@PathVariable("idHdr")Long idHdr)  {

        return jurnalHdrService.posting(idHdr);
    }

    @RequestMapping(value = "validasiPembayaran/idHdr/{idHdr}",
                    method = RequestMethod.GET)
    public Map<String, Object> validasiPembayaran(@PathVariable("idHdr")Long idHdr){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("status", jurnalHdrService.validasiPembayaran(idHdr));
        return map;
    }



}
