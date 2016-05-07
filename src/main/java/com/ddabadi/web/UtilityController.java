package com.ddabadi.web;

import com.ddabadi.domain.AccrualConfig;
import com.ddabadi.enumer.StatusVoucher;
import com.ddabadi.service.AccrualConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by deddy on 4/30/16.
 */
@RestController
@RequestMapping(value = "api", produces = "application/json")
public class UtilityController {

    @Autowired private AccrualConfigService accrualConfigService;

    @RequestMapping(value = "statusVoucher")
    public List<StatusVoucher> getAllStatus(){

        List<StatusVoucher> statusVouchers = new ArrayList<StatusVoucher>();
        for(StatusVoucher statusA: StatusVoucher.values() ){
            StatusVoucher stat =(StatusVoucher) statusA;
            statusVouchers.add(stat);
            //System.out.println(stat  );
        }
        return statusVouchers;
    }


    @RequestMapping(value = "accrualConfig")
    public AccrualConfig getConfig(){

        return accrualConfigService.getConfig();
    }

    @RequestMapping(value = "accrualConfig",
                    method = RequestMethod.PUT)
    public AccrualConfig updateConfig(@RequestBody AccrualConfig accrualConfig){

        return accrualConfigService.updateConfig(accrualConfig);
    }




}
