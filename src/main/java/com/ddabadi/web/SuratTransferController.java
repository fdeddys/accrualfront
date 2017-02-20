package com.ddabadi.web;

import com.ddabadi.Report.ReportController;
import com.ddabadi.domain.Parameter;
import com.ddabadi.domain.SuratTransferDt;
import com.ddabadi.domain.SuratTransferHd;
import com.ddabadi.dto.SuratTransferDtDto;
import com.ddabadi.dto.SuratTransferHdDto;
import com.ddabadi.exception.InvalidDateException;
import com.ddabadi.exception.SuratTransferHadDetilException;
import com.ddabadi.exception.SuratTransferSudahApproveException;
import com.ddabadi.service.ParameterService;
import com.ddabadi.service.SuratTransferService;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by deddy on 10/4/16.
 */

@RestController
@RequestMapping(value = "api/suratTransfer",
                produces = "application/json")
public class SuratTransferController {


    @Autowired private SuratTransferService suratTransferService;
    @Autowired private ParameterService parameterService;

    // SuratTransferHd saveHd(SuratTransferHd suratTransferHd);
    @RequestMapping(value = "hd",
                    method = RequestMethod.POST)
    SuratTransferHd saveHd(@RequestBody SuratTransferHdDto suratTransferHdDto) throws InvalidDateException{
        return suratTransferService.saveHd(suratTransferHdDto);
    }

    // SuratTransferHd editHd(Long idEdit, SuratTransferHd suratTransferHd);
    @RequestMapping(value = "hd/{id}",
                    method = RequestMethod.PUT)
    SuratTransferHd edidHd(@PathVariable Long id, @RequestBody SuratTransferHd suratTransferHd){
        return suratTransferService.editHd(id, suratTransferHd);
    }

    // SuratTransferHd getHdById(Long id);
    @RequestMapping(value = "hd/{id}",
                    method = RequestMethod.GET)
    SuratTransferHd getHdById(@PathVariable Long id){
        return suratTransferService.getHdById(id);
    }


    //Page<SuratTransferHd> getHdByTanggalSuratNoApprove(Date tgl1, Date tgl2,String noApprove, int hal, int jumlah);
    @RequestMapping(value = "hd/{tgl1}/{tgl2}/{noApprove}/{hal}/{jumlah}",
                    method = RequestMethod.GET)
    Page<SuratTransferHd> getHd(@PathVariable String tgl1,
                                @PathVariable String tgl2,
                                @PathVariable String noApprove,
                                @PathVariable int hal,
                                @PathVariable int jumlah){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date tglAwal = sdf.parse(tgl1);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            Date tglAkhir = sdf2.parse(tgl2);
            if(noApprove.equals( "--")){
                noApprove = "%";
            }
            return suratTransferService.getHdByTanggalSuratNoApprove(tglAwal,tglAkhir,noApprove,hal,jumlah);
        } catch (ParseException e) {
            throw new InvalidDateException();
        }
    }


    //SuratTransferDt saveDt(SuratTransferDt suratTransferDt);
    @RequestMapping(value = "dt",
                    method = RequestMethod.POST)
    SuratTransferDt saveDt(@RequestBody SuratTransferDtDto suratTransferDtDto){
        return suratTransferService.saveDt(suratTransferDtDto);
    }

    //SuratTransferDt getDtById(Long id);
    @RequestMapping(value = "dt/{id}",
                    method = RequestMethod.GET)
    SuratTransferDt getDtById(@PathVariable Long id){
        return suratTransferService.getDtById(id);
    }

    //public void delDtById(Long id);
    @RequestMapping(value = "dt/{id}",
                    method = RequestMethod.DELETE)
    public void delDtById(@PathVariable Long id){
        suratTransferService.delDtById(id);
    }

    //Page<SuratTransferDt> getDtByHd(Long idHd, int hal, int jumlah);
    @RequestMapping(value = "dt/{id}/{hal}/{jumlah}",
                    method = RequestMethod.GET)
    Page<SuratTransferDt> getByIdHd(@PathVariable Long id,
                                    @PathVariable int hal,
                                    @PathVariable int jumlah){
        return suratTransferService.getDtByHd(id,hal,jumlah);
    }

    @RequestMapping(value = "hd/{id}",
            method = RequestMethod.DELETE)
    Map<String,Object> deleteHdr(@PathVariable Long id) {
        SuratTransferHd hd = suratTransferService.getHdById(id);
        List<SuratTransferDt> dts = suratTransferService.getDtByHd(hd.getId());
        if(dts.size()>0){
            throw new SuratTransferHadDetilException();
        }else{
            Map<String,Object> hasil = new HashMap<String, Object>();
            Integer hasilDelete = suratTransferService.deleteHd(id);
            if(hasilDelete==1){
                hasil.put("rseult","ok");
            }else{
                hasil.put("result","not ok");
            }
            return hasil;
        }

    }



    //APPROVE
    @RequestMapping(value = "approve/{idHd}/user/{idUser}",
                    method = RequestMethod.POST)
    SuratTransferHd approve(@PathVariable("idHd")Long idHd,
                            @PathVariable("idUser")Long idUser){

        SuratTransferHd hd = suratTransferService.getHdById(idHd);
        if(hd.getIsApprove()==true){
            throw new SuratTransferSudahApproveException();
        }else{
            return suratTransferService.ApproveById(idHd, idUser);
        }

    }

//    @RequestMapping(value = "dt/{id}",
//                    method = RequestMethod.DELETE)
//    public void deleteDt(@PathVariable Long id){
//        suratTransferService.delDtById(id);
//    }


    @RequestMapping(value = "new",
                    method = RequestMethod.GET)
    SuratTransferHd getNew(){
        return new SuratTransferHd();
    }


    @RequestMapping(value = "newHdDto",
            method = RequestMethod.GET)
    SuratTransferHdDto getNewDto(){
        return new SuratTransferHdDto();
    }

    @RequestMapping(value = "newDt",
            method = RequestMethod.GET)
    SuratTransferDt getNewDt(){
        return new SuratTransferDt();
    }


    @RequestMapping(value = "report/{id}",
            method = RequestMethod.GET)
    public void laporanMasterBagian(@PathVariable("id")Long id,
                                    HttpServletResponse response){

        //List<SuratTransferDt> suratTransferDt= suratTransferService.getDtByHd(id);
        Parameter parameter = parameterService.get();
        List<SuratTransferDt> suratTransferDts = suratTransferService.getDtByHd(id);
        //new ArrayList<Bagian>();
        Map<String,Object> maps=new HashMap<String, Object>();
        maps.put("h1",parameter.getH1().trim());
        maps.put("h2",parameter.getH2().trim());
        maps.put("h3",parameter.getH3().trim());
        maps.put("h4",parameter.getH4().trim());

        ReportController report= new ReportController();
        report.previewReport("/report/keuangan/surat_Transfer.jasper", maps, suratTransferDts, "Surat Transfer", response);
    }


}



