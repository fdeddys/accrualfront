package com.ddabadi.web;

import com.ddabadi.domain.Bagian;
import com.ddabadi.service.BagianService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by deddy on 4/29/16.
 */

@RestController
@RequestMapping(value = "api/bagian",produces = "application/json")
public class BagianController {

    @Autowired private BagianService bagianService;
    private Logger logger = Logger.getLogger(BagianController.class);

    @RequestMapping(value = "new")
    public Bagian newValue(){
        return new Bagian();
    }

    @RequestMapping(value = "hal/{hal}/jumlah/{jumlah}")
    public Page<Bagian> getAllPage(@PathVariable("hal")int hal,
                                   @PathVariable("jumlah")int jumlah){

        return bagianService.getByKodeByNamaPage("%","%",hal,jumlah);
    }

    @RequestMapping(value = "/kode/{kode}/nama/{nama}/hal/{hal}/jumlah/{jumlah}")
    public Page<Bagian> getAllPage(@PathVariable("kode")String kode,
                                   @PathVariable("nama")String nama,
                                   @PathVariable("hal")int hal,
                                   @PathVariable("jumlah")int jumlah){

        return bagianService.getByKodeByNamaPage("kode","nama",hal,jumlah);
    }

    @RequestMapping(value = "id/{id}")
    public Bagian getById(@PathVariable("id")Long id){

        return bagianService.getById(id);
    }

    @RequestMapping(value = "kode/{kode}/hal/{hal}/jumlah/{jumlah}")
    public Page<Bagian> getByKode(@PathVariable("kode")String kode,
                            @PathVariable("hal")int hal,
                            @PathVariable("jumlah")int jumlah){

        return bagianService.getByKodeByNamaPage(kode, "%", hal, jumlah);
    }

    @RequestMapping(value = "nama/{nama}/hal/{hal}/jumlah/{jumlah}")
    public Page<Bagian> getByNama(@PathVariable("nama")String nama,
                            @PathVariable("hal")int hal,
                            @PathVariable("jumlah")int jumlah){

        return bagianService.getByKodeByNamaPage("%",nama,hal,jumlah);
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

}
