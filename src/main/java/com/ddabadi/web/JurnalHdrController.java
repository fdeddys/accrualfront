package com.ddabadi.web;

import com.ddabadi.domain.JurnalHeader;
import com.ddabadi.service.JurnalHdrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by deddy on 5/5/16.
 */

@RestController
@RequestMapping(value = "api/transaksi/jurnalHeader",produces ="application/json")
public class JurnalHdrController {

    @Autowired private JurnalHdrService jurnalHdrService;

    @RequestMapping("/idUser/{idUser}/hal/{hal}/jumlah/{jumlah}/tgl1/{tgl1}/tgl2/{tgl2}/statusV/{statusV}")
    public Page<JurnalHeader>   getByIdTanggalStatusPage(@PathVariable("idUser")Long idUser,
                                                         @PathVariable("hal")int hal,
                                                         @PathVariable("jumlah")int jumlah,
                                                         @PathVariable("tgl1")String tgl1,
                                                         @PathVariable("tgl2")String tgl2,
                                                         @PathVariable("statusV")String statusV){


        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date tglAwal=null;
        Date tglAkhir=null;
        try {
            tglAwal= sdf1.parse(tgl1);
            tglAkhir= sdf1.parse(tgl2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  jurnalHdrService.getByIdUserTanggal(idUser, tglAwal, tglAkhir, hal,jumlah);
    }




}
