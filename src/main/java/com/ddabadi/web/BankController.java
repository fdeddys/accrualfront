package com.ddabadi.web;


import com.ddabadi.Report.ReportController;
import com.ddabadi.domain.Bagian;
import com.ddabadi.domain.Bank;
import com.ddabadi.domain.Parameter;
import com.ddabadi.dto.bankData;
import com.ddabadi.service.BankService;
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
 * Created by deddy on 4/29/16.
 */
@RestController
@RequestMapping(value = "api/bank", produces = "application/json" )
public class BankController {

    private Logger logger = Logger.getLogger(BankController.class);

    @Autowired private BankService bankService;
    @Autowired private ParameterService parameterService;

    @RequestMapping(value = "hal/{hal}/jumlah/{jumlah}",
                    method = RequestMethod.GET)
    public Page<Bank> getAll(@PathVariable("hal")int hal,
                             @PathVariable("jumlah")int jumlah){

        logger.info("get all");
        return bankService.getByNamaPage("%", hal,jumlah);
    }

    // untuk surat transfer NON CASH
    @RequestMapping(value = "nonKas/hal/{hal}/jumlah/{jumlah}",
            method = RequestMethod.GET)
    public Page<Bank> getAllNonKas(@PathVariable("hal")int hal,
                             @PathVariable("jumlah")int jumlah){

        logger.info("get all");
        return bankService.getAllNonKas(hal,jumlah);
    }

    @RequestMapping(value = "isKodeExis/{kode}", method = RequestMethod.GET)
    public Boolean getAll(@PathVariable("kode")String kode){

        logger.info("is kode exist ");
        return bankService.isKodeExist(kode.trim());
    }

    @RequestMapping(value = "id/{id}", method = RequestMethod.GET)
    public Bank getById(@PathVariable("id")Long id){

        logger.info("get by id");
        return bankService.getById(id);
    }

    @RequestMapping(value = "/kode/{kode}/nama/{nama}/hal/{hal}/jumlah/{jumlah}",method = RequestMethod.GET)
    public Page<Bank> getByKodeNama(@PathVariable("kode")String kode,
                                    @PathVariable("nama")String nama,
                                    @PathVariable("hal")int hal,
                                    @PathVariable("jumlah")int jumlah){

        logger.info("get by kode nama");
        String cariKode;
        String cariNama;
        if(kode.equals("--")){
            cariKode="%";
        }else{
            cariKode=kode;
        }
        if(nama.equals("--")){
            cariNama="%";
        }else{
            cariNama=nama;
        }

        return bankService.getByKodeNamaPage(cariKode, cariNama, hal, jumlah);
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = "application/json")
    public Bank save(@RequestBody bankData bankData){

        Bank bank = bankData.getBank();
        String userData = bankData.getAuthData();

        logger.info("save");
        return bankService.save(bank);
    };

    @RequestMapping(method = RequestMethod.PUT,
                    consumes = "application/json",
                    value = "id/{id}")
    public Bank update(@PathVariable("id")Long id,  @RequestBody Bank bank){

        logger.info("update");
        return bankService.update(id, bank);
    };

    @RequestMapping(value = "laporan",
            method = RequestMethod.GET)
    public void laporanMasterBagian(HttpServletResponse response){

        Parameter parameter = parameterService.get();
        List<Bank> banks = bankService.getAll();
        //new ArrayList<Bagian>();
        Map<String,Object> maps=new HashMap<String, Object>();
        maps.put("h1",parameter.getH1().trim());
        maps.put("h2",parameter.getH2().trim());
        maps.put("h3",parameter.getH3().trim());
        maps.put("h4",parameter.getH4().trim());

        ReportController report= new ReportController();
        report.previewReport("/report/master/MstBank.jasper", maps, banks, "laporan", response);
    }



}
