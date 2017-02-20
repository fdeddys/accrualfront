package com.ddabadi.web;

import com.ddabadi.Report.ReportController;
import com.ddabadi.domain.Bagian;
import com.ddabadi.domain.Parameter;
import com.ddabadi.exception.BagianNotFoundException;
import com.ddabadi.exception.InvalidKarakterException;
import com.ddabadi.message.MessageByLocaleService;
import com.ddabadi.service.BagianService;
import com.ddabadi.service.ParameterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by deddy on 4/29/16.
 */

@RestController
@RequestMapping(value = "api/bagian",produces = "application/json")
public class BagianController {

    @Autowired private BagianService bagianService;
    private Logger logger = Logger.getLogger(BagianController.class);
    @Autowired
    MessageByLocaleService messageByLocaleService;
    @Autowired private ParameterService parameterService;

    @RequestMapping(value = "new")
    public Bagian newValue(){
        if(true){
            throw new BagianNotFoundException(0);
        }else{
            return new Bagian();
        }
    }

    @RequestMapping(value = "hal/{hal}/jumlah/{jumlah}")
    public Page<Bagian> getAllPage(@PathVariable("hal")int hal,
                                   @PathVariable("jumlah")int jumlah){

        return bagianService.getByKodeByNamaPage("%","%",hal,jumlah);
    }

    @RequestMapping(value = "aktif/hal/{hal}/jumlah/{jumlah}")
    public Page<Bagian> getAllAktifPage(@PathVariable("hal")int hal,
                                        @PathVariable("jumlah")int jumlah){

        return bagianService.getByKodeByNamaAktifPage("%", "%", hal, jumlah);
    }

    @RequestMapping(value = "/kode/{kode}/nama/{nama}/hal/{hal}/jumlah/{jumlah}")
    public Page<Bagian> getAllPage(@PathVariable("kode")String kode,
                                   @PathVariable("nama")String nama,
                                   @PathVariable("hal")int hal,
                                   @PathVariable("jumlah")int jumlah){

        return bagianService.getByKodeByNamaPage("kode", "nama", hal, jumlah);
    }

    @RequestMapping(value = "aktif/kode/{kode}/nama/{nama}/hal/{hal}/jumlah/{jumlah}")
    public Page<Bagian> getAllAktifPage(@PathVariable("kode")String kode,
                                   @PathVariable("nama")String nama,
                                   @PathVariable("hal")int hal,
                                   @PathVariable("jumlah")int jumlah){

        return bagianService.getByKodeByNamaAktifPage("kode", "nama", hal, jumlah);
    }

    @RequestMapping(value = "id/{id}")
    public Bagian getById(@PathVariable("id")Long id) {


        if( id==0){
            //throw new BagianNotFoundException(id.intValue());
            String pesan = messageByLocaleService.getMessage("bagian.tidak.ditemukan");
            throw new InvalidKarakterException(pesan);
        }else{
            return bagianService.getById(id);
        }
    }


    @RequestMapping(value = "kode/{kode}/hal/{hal}/jumlah/{jumlah}")
    public Page<Bagian> getByKode(@PathVariable("kode")String kode,
                            @PathVariable("hal")int hal,
                            @PathVariable("jumlah")int jumlah){

        return bagianService.getByKodeByNamaPage(kode, "%", hal, jumlah);
    }

    @RequestMapping(value = "aktif/kode/{kode}/hal/{hal}/jumlah/{jumlah}")
    public Page<Bagian> getByKodeAktif(@PathVariable("kode")String kode,
                                  @PathVariable("hal")int hal,
                                  @PathVariable("jumlah")int jumlah){

        return bagianService.getByKodeByNamaAktifPage(kode, "%", hal, jumlah);
    }


    @RequestMapping(value = "isKodeExist/{kode}")
    public boolean isKodeBagianAda(@PathVariable("kode")String kode){
        return bagianService.isKodeExist(kode.trim());
    }


    @RequestMapping(value = "nama/{nama}/hal/{hal}/jumlah/{jumlah}")
    public Page<Bagian> getByNama(@PathVariable("nama")String nama,
                            @PathVariable("hal")int hal,
                            @PathVariable("jumlah")int jumlah){

        return bagianService.getByKodeByNamaPage("%", nama, hal, jumlah);
    }

    @RequestMapping(value = "aktif/nama/{nama}/hal/{hal}/jumlah/{jumlah}")
    public Page<Bagian> getByNamaAktif( @PathVariable("nama")String nama,
                                        @PathVariable("hal")int hal,
                                        @PathVariable("jumlah")int jumlah){

        return bagianService.getByKodeByNamaAktifPage("%",nama,hal,jumlah);
    }

    @RequestMapping(method = RequestMethod.POST,
                    consumes = "application/json")
    public Bagian save(@RequestBody Bagian bagian){

        return bagianService.save(bagian);
    };

    @RequestMapping(value = "id/{id}",
                    method = RequestMethod.PUT,
                    consumes = "application/json")
    public Bagian update(@PathVariable("id")Long idEdit,
                         @RequestBody Bagian bagian){

        return bagianService.update(idEdit, bagian);
    };

    @RequestMapping(value = "laporan",
                    method = RequestMethod.GET)
    public void laporanMasterBagian(HttpServletResponse response){

        Parameter parameter = parameterService.get();
        List<Bagian> bagians = bagianService.getAll();
                //new ArrayList<Bagian>();
        Map<String,Object> maps=new HashMap<String, Object>();
        maps.put("h1",parameter.getH1().trim());
        maps.put("h2",parameter.getH2().trim());
        maps.put("h3",parameter.getH3().trim());
        maps.put("h4",parameter.getH4().trim());

        ReportController report= new ReportController();
        report.previewReport("/report/master/MstBagian.jasper",maps,bagians,"laporan", response);
    }

}
