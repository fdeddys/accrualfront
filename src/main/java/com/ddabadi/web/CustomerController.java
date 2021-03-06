package com.ddabadi.web;

import com.ddabadi.Report.ReportController;
import com.ddabadi.domain.Customer;
import com.ddabadi.domain.Parameter;
import com.ddabadi.service.CustomerService;
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
 * Created by deddy on 4/30/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "api/customer",produces = "application/json")
public class CustomerController {

    private Logger logger = Logger.getLogger(CustomerController.class);

    @Autowired private CustomerService customerService;
    @Autowired private ParameterService parameterService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getAll(){
        logger.info("get all List");
        return  customerService.getAll();
    }

    @RequestMapping(value = "hal/{hal}/jumlah/{jumlah}" ,
                    method = RequestMethod.GET)
    public Page<Customer> getAllPage(@PathVariable("hal") int hal,
                                     @PathVariable("jumlah")int jumlah){
        logger.info("get all page");
        return  customerService.getByKodeByNamaPage("%", "%", hal, jumlah);
    }

    @RequestMapping(value = "id/{id}" ,
            method = RequestMethod.GET)
    public Customer getById(@PathVariable("id")Long id){
        logger.info("get by id");
        return  customerService.getById(id);
    }

    // 1 customer by kode
    @RequestMapping(value = "oneKode/{kode}" ,
                    method = RequestMethod.GET)
    public Customer getOneByKode(@PathVariable("kode")String kode){
        logger.info("get one by kode");
        return  customerService.getByKode(kode);
    }

    // list customer by kode
    @RequestMapping(value = "kode/{kode}" ,
                    method = RequestMethod.GET)
    public List<Customer> getByKodeList(@PathVariable("kode")String kode){
        logger.info("get by kode");
        return  customerService.getListByKode(kode);
    }

    // page customer by nama
    @RequestMapping(value = "nama/{nama}/hal/{hal}/jumlah/{jumlah}" ,
                    method = RequestMethod.GET)
    public Page<Customer> getByNamaPage(@PathVariable("nama")String nama,
                                        @PathVariable("hal")int hal,
                                        @PathVariable("jumlah")int jumlah){
        logger.info("get by nama");
        return  customerService.getByKodeByNamaPage("%", nama, hal, jumlah);
    }

    // page customer by kode
    @RequestMapping(value = "active/nama/{nama}/hal/{hal}/jumlah/{jumlah}" ,
            method = RequestMethod.GET)
    public Page<Customer> getByKodePage(@PathVariable("nama")String nama,
                                        @PathVariable("hal")int hal,
                                        @PathVariable("jumlah")int jumlah){
        logger.info("get by nama active");
        return  customerService.getByNamaActivePage(nama, hal, jumlah);
    }

    @RequestMapping(value = "isKodeExis/{kode}" ,
                    method = RequestMethod.GET)
    public Boolean isKodeExist(@PathVariable("kode")String kode){
        logger.info("is kode exist");
        return  customerService.isKodeExist(kode);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Customer simpan(@RequestBody Customer customer){
        logger.info("simpan");
        return  customerService.save(customer);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public Customer edit(@PathVariable("id")Long id, @RequestBody Customer customer){
        logger.info("simpan");
        return  customerService.update(id, customer);
    }

    @RequestMapping(value = "laporan",
            method = RequestMethod.GET)
    public void laporanMasterCustomer(HttpServletResponse response){

        Parameter parameter = parameterService.get();
        List<Customer> customers = customerService.getAll();

        Map<String,Object> maps=new HashMap<String, Object>();
        maps.put("h1",parameter.getH1().trim());
        maps.put("h2",parameter.getH2().trim());
        maps.put("h3",parameter.getH3().trim());
        maps.put("h4",parameter.getH4().trim());

        ReportController report= new ReportController();
        report.previewReport("/report/master/MstCustomer.jasper", maps, customers, "laporan", response);
    }


}
