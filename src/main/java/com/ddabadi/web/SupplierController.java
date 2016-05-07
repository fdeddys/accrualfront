package com.ddabadi.web;

import com.ddabadi.domain.Supplier;
import com.ddabadi.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by deddy on 4/23/16.
 */

@RestController
@RequestMapping(value = "api", produces ="application/json")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @RequestMapping("tes")
    public String tes(){
        return "tesss";
    }

    @RequestMapping("supplier/{id}")
    public Supplier getById(@PathVariable("id")Long id){
        return supplierService.getById(id) ;
    }

    @RequestMapping("supplier/nama/{cariNama}/hal/{hal}/jumlah/{jumlah}")
    public Page<Supplier> getByNamaPage(@PathVariable("cariNama")String cariNama,
                                        @PathVariable("hal")int hal,
                                        @PathVariable("jumlah")int jumlah){
        return supplierService.getByNamaPage(cariNama,hal,jumlah);
    }



}
