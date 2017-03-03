package com.ddabadi.web;

import com.ddabadi.Report.ReportController;
import com.ddabadi.domain.CoaHdr;
import com.ddabadi.domain.Parameter;
import com.ddabadi.service.CoaHdrService;
import com.ddabadi.service.ParameterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by deddy on 5/3/16.
 */
@RestController
@RequestMapping(value = "api/accountHdr",produces = "application/json")
public class CoaHdrController {

    @Autowired private CoaHdrService coaHdrService;
    @Autowired private ParameterService parameterService;

    private Logger logger = Logger.getLogger(CoaHdrService.class);

    @RequestMapping(value = "hal/{hal}/jumlah/{jumlah}")
    public Page<CoaHdr> getAllPage(@PathVariable("hal")int hal,
                                   @PathVariable("jumlah")int jumlah){

        logger.info("get all page");
        return coaHdrService.getByKodeByNamaPage("%","%",hal,jumlah);
    }

    @RequestMapping(value = "/kode/{kode}/hal/{hal}/jumlah/{jumlah}")
    public Page<CoaHdr> getByKodePage(@PathVariable("kode")String kode,
                                      @PathVariable("hal")int hal,
                                      @PathVariable("jumlah")int jumlah){

        logger.info("get by kode page");
        if(kode.trim().equals("--")){
            return getAllPage(hal,jumlah);
        }else{
            return coaHdrService.getByKodeByNamaPage("%"+kode+"%","%",hal,jumlah);
        }
    }

    @RequestMapping(value = "/nama/{nama}/hal/{hal}/jumlah/{jumlah}")
    public Page<CoaHdr> getByNamaPage(@PathVariable("nama")String nama,
                                      @PathVariable("hal")int hal,
                                      @PathVariable("jumlah")int jumlah){

        logger.info("get by nama page");
        return coaHdrService.getByKodeByNamaPage("%","%"+nama+"%",hal,jumlah);
    }

    @RequestMapping(value = "/kode/{kode}/nama/{nama}/hal/{hal}/jumlah/{jumlah}")
    public Page<CoaHdr> getByNamaPage(@PathVariable("kode")String kode,
                                      @PathVariable("nama")String nama,
                                      @PathVariable("hal")int hal,
                                      @PathVariable("jumlah")int jumlah){

        String kriteriaKode ;
        String kriteriaNama ;
        logger.info("get by kode+nama page");
        if(kode.equals("--")){
            kriteriaKode="%";
        }else{
            kriteriaKode="%"+kode+"%";
        }
        if(nama.equals("--")){
            kriteriaNama="%";
        }else{
            kriteriaNama="%"+nama+"%";
        }
        return coaHdrService.getByKodeByNamaPage(kriteriaKode,kriteriaNama,hal,jumlah);
    }

    @RequestMapping(value = "/id/{id}")
    public CoaHdr getById(@PathVariable("id")Long id){

        logger.info("get by id ");
        return coaHdrService.getById(id);
    }

    @RequestMapping(value = "/isKodeExis/{kode}")
    public boolean isKodeExis(@PathVariable("kode")String kode){

        logger.info("is kode exis");
        return coaHdrService.isKodeExis(kode);
    }

    @RequestMapping(method = RequestMethod.POST)
    public CoaHdr simpan(@RequestBody CoaHdr coaHdr){

        logger.info("simpan");
        return coaHdrService.save(coaHdr);
    }

    @RequestMapping(method = RequestMethod.PUT,
                    value = "id/{id}")
    public CoaHdr simpan(@PathVariable("id")Long id, @RequestBody CoaHdr coaHdr){

        logger.info("update");
        return coaHdrService.update(id, coaHdr);
    }


    @RequestMapping(value = "/GetBagian/id/{id}")
    public String getKodeBagianByCoaDetil(@PathVariable("id")Long id){
        logger.info("cek bagian by ");
        return coaHdrService.getBagianById(id);
    }
    @RequestMapping(value = "laporan",
            method = RequestMethod.GET)
    public void laporanMasterBagian(HttpServletResponse response){

        Parameter parameter = parameterService.get();
        List<CoaHdr> coas = coaHdrService.getAllCoa();
        //new ArrayList<Bagian>();
        Map<String,Object> maps=new HashMap<String, Object>();
        maps.put("h1",parameter.getH1().trim());
        maps.put("h2",parameter.getH2().trim());
        maps.put("h3",parameter.getH3().trim());
        maps.put("h4",parameter.getH4().trim());

        ReportController report= new ReportController();
        report.previewReport("/report/master/MstCoaHdr.jasper", maps, coas, "laporan", response);
    }
}
