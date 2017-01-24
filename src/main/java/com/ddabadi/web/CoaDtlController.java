package com.ddabadi.web;

/**
 * Created by deddy on 5/3/16.
 */

import com.ddabadi.Report.ReportController;
import com.ddabadi.domain.CoaDtl;
import com.ddabadi.domain.Parameter;
import com.ddabadi.service.CoaDtlService;
import com.ddabadi.service.ParameterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/accountDtl",produces = "application/json")
public class CoaDtlController {

    @Autowired
    private CoaDtlService coaDtlService;
    @Autowired private ParameterService parameterService;
    private Logger logger = Logger.getLogger(CoaDtlService.class);

    @RequestMapping(value = "hal/{hal}/jumlah/{jumlah}")
    public Page<CoaDtl> getAllPage(@PathVariable("hal")int hal,
                                   @PathVariable("jumlah")int jumlah){

        logger.info("get all page");
        return coaDtlService.getByKodeByNamaPage("%","%",hal,jumlah);
    }

    @RequestMapping(value = "/kode/{kode}/hal/{hal}/jumlah/{jumlah}")
    public Page<CoaDtl> getByKodePage(@PathVariable("kode")String kode,
                                      @PathVariable("hal")int hal,
                                      @PathVariable("jumlah")int jumlah){

        logger.info("get by kode page");
        if(kode.trim().equals("--")){
            return getAllPage(hal,jumlah);
        }else{
            return coaDtlService.getByKodeByNamaPage(kode+"%","%",hal,jumlah);
        }
    }

    @RequestMapping(value = "/nama/{nama}/hal/{hal}/jumlah/{jumlah}")
    public Page<CoaDtl> getByNamaPage(@PathVariable("nama")String nama,
                                      @PathVariable("hal")int hal,
                                      @PathVariable("jumlah")int jumlah){

        logger.info("get by nama page");
        return coaDtlService.getByKodeByNamaPage("%","%"+nama+"%",hal,jumlah);
    }

    @RequestMapping(value = "/kode/{kode}/nama/{nama}/hal/{hal}/jumlah/{jumlah}")
    public Page<CoaDtl> getByNamaPage(@PathVariable("kode")String kode,
                                      @PathVariable("nama")String nama,
                                      @PathVariable("hal")int hal,
                                      @PathVariable("jumlah")int jumlah){

        logger.info("get by kode+nama page");
        String kriteriaKode=kode;
        String kriteriaNama=nama;
        if(kode.equals("-")||kode.equals("--")){
            kriteriaKode="%";
        }
        if(nama.equals("-")||nama.equals("--")){
            kriteriaNama="%";
        }

        return coaDtlService.getByKodeByNamaPage(kriteriaKode+"%","%"+kriteriaNama+"%",hal,jumlah);
    }

    @RequestMapping(value = "/cashbank/kode/{kode}/nama/{nama}/hal/{hal}/jumlah/{jumlah}")
    public Page<CoaDtl> getByKodeNamaCASHBANKPage(@PathVariable("kode")String kode,
                                                  @PathVariable("nama")String nama,
                                                  @PathVariable("hal")int hal,
                                                  @PathVariable("jumlah")int jumlah){

        String kriteriaKode=kode;
        String kriteriaNama=nama;
        if(kode.equals("-")){
            kriteriaKode="%";
        }
        if(nama.equals("-")){
            kriteriaNama="%";
        }

        return coaDtlService.getByKodeByNamaCashBankPage("%" + kriteriaKode + "%", "%" + kriteriaNama + "%", hal, jumlah);
    }

    @RequestMapping(value = "/id/{id}")
    public CoaDtl getById(@PathVariable("id")Long id){

        logger.info("get by id ");
        return coaDtlService.getById(id);
    }

    @RequestMapping(value = "/isKode/{kode}")
    public boolean isKodeExis(@PathVariable("kode")String kode){

        logger.info("is kode exis");
        return coaDtlService.isKodeExis(kode);
    }

    @RequestMapping(method = RequestMethod.POST)
    public CoaDtl simpan(@RequestBody CoaDtl coaDtl){

        logger.info("simpan");



        return coaDtlService.save(coaDtl);
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "id/{id}")
    public CoaDtl simpan(@PathVariable("id")Long id, @RequestBody CoaDtl coaDtl){

        logger.info("update");
        return coaDtlService.update(id, coaDtl);
    }

    @RequestMapping(value = "laporan",
            method = RequestMethod.GET)
    public void laporanMasterBagian(HttpServletResponse response){

        Parameter parameter = parameterService.get();
        List<CoaDtl> coas = coaDtlService.getAllCoa();
        //new ArrayList<Bagian>();
        Map<String,Object> maps=new HashMap<String, Object>();
        maps.put("h1",parameter.getH1().trim());
        maps.put("h2",parameter.getH2().trim());
        maps.put("h3",parameter.getH3().trim());
        maps.put("h4",parameter.getH4().trim());

        ReportController report= new ReportController();
        report.previewReport("/report/master/MstCoa.jasper", maps, coas, "laporan", response);
    }

}
