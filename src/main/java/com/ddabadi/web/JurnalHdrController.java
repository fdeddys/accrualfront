package com.ddabadi.web;

import com.ddabadi.domain.JurnalHeader;
import com.ddabadi.domain.User;
import com.ddabadi.service.JurnalHdrService;
import com.ddabadi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    @Autowired private UserService userService;

    @RequestMapping("/idUser/{idUser}/hal/{hal}/jumlah/{jumlah}/tgl1/{tgl1}/tgl2/{tgl2}/statusV/{statusV}")
    public Page<JurnalHeader>   getByIdTanggalStatusPage(@PathVariable("idUser")Long idUser,
                                                         @PathVariable("hal")int hal,
                                                         @PathVariable("jumlah")int jumlah,
                                                         @PathVariable("tgl1")String tgl1,
                                                         @PathVariable("tgl2")String tgl2,
                                                         @PathVariable("statusV")String statusV){


        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date tglAwal=null;
        Date tglAkhir=null;
        try {
            tglAwal= sdf1.parse(tgl1);
            tglAkhir= sdf2.parse(tgl2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  jurnalHdrService.getByIdUserTanggal(idUser, tglAwal, tglAkhir, hal,jumlah);
    }

    @RequestMapping(value = "add/idUser/{idUser}",method = RequestMethod.POST, consumes = "application/json")
    JurnalHeader save(@PathVariable("idUser")Long idUser, @RequestBody JurnalHeader jurnalHeader){
        User user = userService.getById(idUser);
        jurnalHeader.setUser(user);
        return jurnalHdrService.save(jurnalHeader);
    }

    @RequestMapping(value = "id/{idHdr}/user/{idUser}",
                    method = RequestMethod.GET)
    JurnalHeader getByIdByUserId(@PathVariable("idHdr")Long idHdr,
                                 @PathVariable("idUser")Long idUser){
        return jurnalHdrService.getByIdJurnalByIdUser(idHdr,idUser);
    }


}
