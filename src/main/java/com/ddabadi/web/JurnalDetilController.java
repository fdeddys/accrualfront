package com.ddabadi.web;

import com.ddabadi.domain.JurnalDetil;
import com.ddabadi.dto.JurnalDetilDto;
import com.ddabadi.service.JurnalDetilService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by deddy on 5/7/16.
 */

@RestController
@RequestMapping(value = "api/transaksi/jurnalDetil", produces= "application/json")
public class JurnalDetilController {

    private Logger logger= Logger.getLogger(JurnalDetilController.class);
    @Autowired private JurnalDetilService jurnalDetilService;

    @RequestMapping(value = "id/{id}")
    public JurnalDetil getById(@PathVariable("id")Long id){
        logger.info("get by id");
        return jurnalDetilService.getById(id);
    }


    @RequestMapping(value = "{idHdr}")
    public List<JurnalDetil> getByIdJurnalHdr(@PathVariable("idHdr")Long idHdr){
        logger.info("get by id Header");
        return jurnalDetilService.getByJurnalHdrId(idHdr);
    }

    @RequestMapping(value = "{idHdr}/hal/{hal}/jumlah/{jumlah}")
    public Page<JurnalDetil> getByIdJurnalHdrPage(@PathVariable("idHdr")Long idHdr,
                                                  @PathVariable("hal")int hal,
                                                  @PathVariable("jumlah")int jumlah){
        logger.info("get by id Header page");
        return jurnalDetilService.getByJurnalHdrIdPage(idHdr, hal, jumlah);
    }

    @RequestMapping(consumes = "application/json",
                    method = RequestMethod.POST)
    public JurnalDetil save(@RequestBody JurnalDetilDto jurnalDetilDto){

        logger.info("save");
        return jurnalDetilService.save(jurnalDetilDto);
    }

}
