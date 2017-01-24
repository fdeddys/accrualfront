package com.ddabadi.web;

import com.ddabadi.domain.BukuBesar;
import com.ddabadi.domain.BukuBesarTrial;
import com.ddabadi.service.BagianService;
import com.ddabadi.service.BukuBesarService;
import com.ddabadi.service.BukuBesarTrialService;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    @RequestMapping(value = "tutupBulan")
    Boolean prosesBulanan(){
        Boolean hasil = false;
        bukuBesarService.prosesBulanan(true);
        return true;
    }

    @RequestMapping(value = "postingTrial/idHd/{idHd}",
                    method = RequestMethod.POST)
    public void postingTrial(@PathVariable("idHd")long idHd){
        bukuBesarTrialService.postingJurnalTrial(idHd);
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


    @RequestMapping(value = "bukuBesarTrial/idCoa/{idCoa}/idBank/{idBank}/idCust/{idCust}/rel/{rel}/hal/{hal}/jumlah/{jumlah}",
            method = RequestMethod.GET)
    public Page<BukuBesarTrial> getBBTriak(@PathVariable("idCoa")Long idCoa,
                                           @PathVariable("idBank")String idBank,
                                           @PathVariable("idCust")String idCust,
                                           @PathVariable("rel")String rel,
                                           @PathVariable("hal")int hal,
                                           @PathVariable("jumlah")int jumlah){

        if(idCoa == -1){
            return bukuBesarTrialService.getBBTrialAll(hal,jumlah);
        }else{
            return bukuBesarTrialService.getBBTrialByIdCoaIdBankRelIdCust(idCoa,idBank,rel, idCust, hal,jumlah);
        }
    }

}
