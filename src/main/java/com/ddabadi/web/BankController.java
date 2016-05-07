package com.ddabadi.web;


import com.ddabadi.domain.Bank;
import com.ddabadi.dto.bankData;
import com.ddabadi.service.BankService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by deddy on 4/29/16.
 */
@RestController
@RequestMapping(value = "api/bank", produces = "application/json" )
public class BankController {

    private Logger logger = Logger.getLogger(BankController.class);

    @Autowired private BankService bankService;

    @RequestMapping(value = "hal/{hal}/jumlah/{jumlah}",
                    method = RequestMethod.GET)
    public Page<Bank> getAll(@PathVariable("hal")int hal,
                             @PathVariable("jumlah")int jumlah){

        logger.info("get all");
        return bankService.getByNamaPage("%", hal,jumlah);
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
        return bankService.getByKodeNamaPage(kode, nama, hal, jumlah);
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
        return bankService.update(id,bank);
    };



}
