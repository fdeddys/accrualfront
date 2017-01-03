package com.ddabadi.web;

import com.ddabadi.Report.ReportController;
import com.ddabadi.domain.Direktorat;
import com.ddabadi.domain.Parameter;
import com.ddabadi.domain.User;
import com.ddabadi.service.DirektoratService;
import com.ddabadi.service.ParameterService;
import com.ddabadi.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by deddy on 4/30/16.
 */
//@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping(value = "api/direktorat", produces = "application/json")
//@CrossOrigin
public class DirektoratController {

    @Autowired private DirektoratService direktoratService;
    @Autowired private UserService userService;
    @Autowired private ParameterService parameterService;

    private Logger logger = Logger.getLogger(DirektoratController.class);

    @RequestMapping(method = RequestMethod.GET)
    public List<Direktorat> getAll(@RequestParam(required=false, defaultValue="kode",name = "urut") String order){
        logger.info("get all");
        return direktoratService.getAll(order);
    }

    @RequestMapping(method = RequestMethod.GET, value = "nama/{nama}")
    public List<Direktorat> getByNama(@PathVariable("nama")String nama){
        logger.info("get by nama [ " + nama +" ] ");
        return direktoratService.getByNama(nama);
    }

    @RequestMapping(method = RequestMethod.GET, value = "id/{id}")
    public Direktorat getById(@PathVariable("id")Long id){
        logger.info("get by id [ " + id.toString() +" ] ");
        return direktoratService.getById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "isKodeExist/{kode}")
    public boolean isKodeSudahAda(@PathVariable("kode")String kode){
        logger.info("is kode exist " + kode.toString());
        return direktoratService.isKodeExist(kode.trim());
    }

    @RequestMapping(method = RequestMethod.POST, value = "tesss")
    public Direktorat save2(@RequestBody Direktorat direktorat,
                            @RequestHeader(value = "id-user")Long idUser,
                            @RequestHeader(value = "auth")String auth){

        // Cek auth dari user
        Boolean isValid =false;
        Direktorat direktoratSave = null;


        // cek apakah user id terdaftar
        User user = userService.getById(idUser);
        if(idUser==null){
            // invalid user ID
        }else   {
            // cek apakah id ada auth save table direktorat
            if(isValid){
                // valid -- save

                direktoratSave = direktoratService.save(direktorat);
            }else{
                // auth save direktorat tidak valid


            }

        }




//            if(user.getPassword().equals(auth)){
//
//
//                // cek auth save direktorat
//                if(isValid){
//                    direktoratSave = direktoratService.save(direktorat);
//                }else{
//                    // auth save direktorat salah
//
//                }
//
//            }else{
//                //salah pass
//            }

        return  direktoratSave;
    }


    @RequestMapping(method = RequestMethod.POST)
    public Direktorat save( @RequestBody Direktorat direktorat){


        return  direktoratService.save(direktorat);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "id2/{id}")
    public Direktorat update2(@PathVariable("id")Long id,
                             @RequestBody Direktorat direktorat,
                             @RequestHeader(value = "id-user")Long idUser){

        // Cek auth dari user
        Boolean isValid =false;
        Direktorat direktoratUpdate= null;

        // cek apakah user id terdaftar
        User user = userService.getById(idUser);
        if(idUser==null){
            // invalid user ID
        }else   {
            // cek apakah id ada auth save/update table direktorat
            if(isValid){
                // valid -- save

                direktoratUpdate = direktoratService.save(direktorat);
            }else{
                // auth save direktorat tidak valid

            }
        }


        return  direktoratUpdate;
    }


    @RequestMapping(method = RequestMethod.PUT, value = "updateid/{id}")
    public Direktorat updateDB(@PathVariable("id")Long id,
                               @RequestBody Direktorat direktorat){

        return  direktoratService.update(id,direktorat);
    }

    @RequestMapping(value = "laporan",
            method = RequestMethod.GET)
    public void laporanMasterBagian(HttpServletResponse response){

        Parameter parameter = parameterService.get();
        List<Direktorat> direktorats = direktoratService.getAll("nama");
        //new ArrayList<Bagian>();
        Map<String,Object> maps=new HashMap<String, Object>();
        maps.put("h1",parameter.getH1().trim());
        maps.put("h2",parameter.getH2().trim());
        maps.put("h3",parameter.getH3().trim());
        maps.put("h4",parameter.getH4().trim());

        ReportController report= new ReportController();
        report.previewReport("/report/master/MstDirektorat.jasper",maps,direktorats,"laporan", response);
    }


}
