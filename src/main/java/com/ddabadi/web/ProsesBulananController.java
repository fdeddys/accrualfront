package com.ddabadi.web;

import com.ddabadi.Report.ReportController;
import com.ddabadi.domain.BukuBesar;
import com.ddabadi.domain.BukuBesarTrial;
import com.ddabadi.domain.JurnalHeader;
import com.ddabadi.domain.Parameter;
import com.ddabadi.enumer.JenisVoucher;
import com.ddabadi.service.*;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by deddy on 7/8/16.
 */

@RestController
@RequestMapping(value = "api/proses")
public class ProsesBulananController {

    @Autowired BukuBesarService bukuBesarService;
    @Autowired
    BukuBesarTrialService bukuBesarTrialService;
    @Autowired private ParameterService parameterService;
    @Autowired private JurnalHdrService jurnalHdrService;

    @RequestMapping(value = "tutupBulan")
    Boolean prosesBulanan(){
        Boolean hasil = false;
        bukuBesarService.prosesBulanan(true);
        return true;
    }

    @RequestMapping(value = "postingTrial/idHd/{idHd}",
                    method = RequestMethod.POST)
    public void postingTrial(@PathVariable("idHd")long idHd){
        JurnalHeader jurnalHeader = jurnalHdrService.getById(idHd);
        if(jurnalHeader.getJenisVoucher().equals(JenisVoucher.PENGELUARAN)){
            bukuBesarTrialService.setPosting(idHd);
        }else{
            bukuBesarTrialService.postingJurnalTrial(idHd);
        }
    }

    @RequestMapping(value = "unPostingTrial/idHd/{idHd}",
            method = RequestMethod.POST)
    public void unPostingTrial(@PathVariable("idHd")long idHd){
        bukuBesarTrialService.unPostingJurnalTrial(idHd);
    }


    @RequestMapping(value = "getBulanTahunBerjalan")
    public Map<String,Object> getProsesBulanan(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("hasil",bukuBesarService.getTglProsesBulanan());
        return map;
        //bukuBesarService.getTglProsesBulanan();
    }

    @RequestMapping(value = "bukuBesar/{bulanTahun}/keterangan/{keterangan}/idCoa/{idCoa}/hal/{hal}/jumlah/{jumlah}",
                    produces = "application/json",
                    method = RequestMethod.GET)
    Page<BukuBesar> getAllBukuBesar(@PathVariable("bulanTahun")String bulanTahun,
                                    @PathVariable("keterangan")String keterangan,
                                    @PathVariable("idCoa")Long idCoa,
                                    @PathVariable("hal")int hal,
                                    @PathVariable("jumlah")int jumlah){

        String ket;
        if (keterangan.equals("--")){
            ket="%";
        }else{
            ket = "%"+keterangan.trim()+"%";
        }
        if(idCoa==0){
            return bukuBesarService.findByBulanTahunBerjalanKeterangan(bulanTahun, ket, hal, jumlah);
        }else{
            return bukuBesarService.findByBulanTahunBerjalanKeteranganCoaDtl(bulanTahun, ket,idCoa, hal, jumlah);
        }
    }


    @RequestMapping(value = "bukuBesarTrial/idCoa/{idCoa}/idCust/{idCust}/rel/{rel}/hal/{hal}/jumlah/{jumlah}",
            method = RequestMethod.GET)
    public Page<BukuBesarTrial> getBBTriak(@PathVariable("idCoa")Long idCoa,
                                           @PathVariable("idCust")Long idCust,
                                           @PathVariable("rel")String rel,
                                           @PathVariable("hal")int hal,
                                           @PathVariable("jumlah")int jumlah){

        if(idCoa == -1){
            return bukuBesarTrialService.getBBTrialAll(hal, jumlah);
        }else{
            return bukuBesarTrialService.getBBTrialByIdCoaIdBankRelIdCust(idCoa, rel, idCust, hal, jumlah);
        }
    }

    @RequestMapping(value = "laporan/BBTrial",
                    method = RequestMethod.GET)
    public void laporanBBTrial(HttpServletResponse response){

        Parameter parameter = parameterService.get();
        List<BukuBesarTrial> bbTrials= bukuBesarTrialService.getBBTrialAll();
        //new ArrayList<Bagian>();
        Map<String,Object> maps=new HashMap<String, Object>();
        maps.put("h1",parameter.getH1().trim());
        maps.put("h2",parameter.getH2().trim());
        maps.put("h3",parameter.getH3().trim());
        maps.put("h4",parameter.getH4().trim());

        ReportController report= new ReportController();
        report.previewReport("/report/transaksi/bukuBesarTrialGroupBy.jasper", maps, bbTrials, "laporan", response);
    }


    @RequestMapping(value = "laporan/BBTrial/idCoaDtl/{idCoaDtl}",
            method = RequestMethod.GET)
    public void laporanBBTrialCoa(@PathVariable Long idCoaDtl,
                                  HttpServletResponse response){

        Parameter parameter = parameterService.get();
        List<BukuBesarTrial> bbTrials= bukuBesarTrialService.getBBTrialCoa(idCoaDtl);
        //new ArrayList<Bagian>();
        Map<String,Object> maps=new HashMap<String, Object>();
        maps.put("h1",parameter.getH1().trim());
        maps.put("h2",parameter.getH2().trim());
        maps.put("h3",parameter.getH3().trim());
        maps.put("h4",parameter.getH4().trim());

        ReportController report= new ReportController();
        report.previewReport("/report/transaksi/bukuBesarTrial.jasper", maps, bbTrials, "laporan", response);
    }

}
