package com.ddabadi.web;

import com.ddabadi.Report.ReportController;
import com.ddabadi.domain.JurnalDetil;
import com.ddabadi.domain.Parameter;
import com.ddabadi.dto.JurnalDetilDto;
import com.ddabadi.exception.BankNotFoundException;
import com.ddabadi.service.JurnalDetilService;
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
 * Created by deddy on 5/7/16.
 */

@RestController
@RequestMapping(value = "api/transaksi/jurnalDetil", produces= "application/json")
public class JurnalDetilController {

    private Logger logger= Logger.getLogger(JurnalDetilController.class);
    @Autowired private JurnalDetilService jurnalDetilService;
    @Autowired private ParameterService parameterService;

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

    @RequestMapping(value = "{idHdr}/kredit/hal/{hal}/jumlah/{jumlah}")
    public Page<JurnalDetil> getJurnalKreditByIdJurnalHdrPage(@PathVariable("idHdr")Long idHdr,
                                                              @PathVariable("hal")int hal,
                                                              @PathVariable("jumlah")int jumlah){
        return jurnalDetilService.getByJurnalKreditHdrIdPage(idHdr, hal, jumlah);
    }

    @RequestMapping(value = "{idHdr}/debet")
    public JurnalDetil getJurnalKreditByIdJurnalHdrPage(@PathVariable("idHdr")Long idHdr){
        return jurnalDetilService.getByJurnalDebetFirst(idHdr);
    }

    @RequestMapping(consumes = "application/json",
                    method = RequestMethod.POST)
    public JurnalDetil save(@RequestBody JurnalDetilDto jurnalDetilDto){

        logger.info("save");
        return jurnalDetilService.save(jurnalDetilDto);
    }

    @RequestMapping(value = "id/{id}",
                    method = RequestMethod.DELETE)
    public int deleteById(@PathVariable("id")Long id){
        logger.info("delete by id");
        return jurnalDetilService.delete(id);
    }

    @RequestMapping(value = "listVoucST/hal/{hal}/jumlah/{jumlah}",
                    method = RequestMethod.GET)
    Page<JurnalDetil> getVoucherUntukSuratTransfer(@PathVariable("hal")int hal,
                                                   @PathVariable("jumlah")int jumlah) {

        return jurnalDetilService.getVoucherSuratTransfer(hal, jumlah);
    }

    @RequestMapping(value = "listVoucST/bank/{idBank}/noUrut/{noUrut}/hal/{hal}/jumlah/{jumlah}",
            method = RequestMethod.GET)
    Page<JurnalDetil> getVoucherUntukSuratTransferByBank(   @PathVariable("idBank")Long idBank,
                                                            @PathVariable("noUrut")String noUrut,
                                                            @PathVariable("hal")int hal,
                                                            @PathVariable("jumlah")int jumlah) {

        return jurnalDetilService.getVoucherSuratTransferBankNoUrut(idBank,noUrut, hal, jumlah);
    }


    @RequestMapping(value = "voucher/{idHd}",
            method = RequestMethod.GET)
    public void laporanMasterBagian(@PathVariable("idHd")Long idHd,
                                    HttpServletResponse response){

        Parameter parameter = parameterService.get();
        List<JurnalDetil> jurnalDetils = jurnalDetilService.getByJurnalHdrId(idHd);
        //new ArrayList<Bagian>();
        Map<String,Object> maps=new HashMap<String, Object>();
        maps.put("h1",parameter.getH1().trim());
        maps.put("h2",parameter.getH2().trim());
        maps.put("h3",parameter.getH3().trim());
        maps.put("h4",parameter.getH4().trim());

        ReportController report= new ReportController();
        report.previewReport("/report/transaksi/jurnal.jasper", maps, jurnalDetils, "laporan", response);
    }


}
