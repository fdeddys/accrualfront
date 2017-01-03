package com.ddabadi.service.impl;

import com.ddabadi.domain.Parameter;
import com.ddabadi.domain.repository.ParameterRepository;
import com.ddabadi.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by deddy on 5/21/16.
 */

@Service
public class ParameterServiceImpl implements ParameterService {

    @Autowired private ParameterRepository repository;

    @Override
    public Parameter get() {
        Parameter parameter = null;
        if (repository.findAll().iterator().hasNext()){
            parameter = repository.findAll().iterator().next();
        }else{
            parameter =new Parameter();
            parameter.setH1("header 1");
            parameter.setH2("header 2");
            parameter.setH3("header 3");
            parameter.setH4("header 4");
            parameter=repository.saveAndFlush(parameter);
        }

        return  parameter;
    }

    @Override
    public Parameter update(Parameter parameterUpate) {

        Parameter parameter = new Parameter();
        //boolean databaru =false;

        if (repository.findAll().iterator().hasNext()){
            parameter = repository.findAll().iterator().next();
        }else{
            parameter = new Parameter();
            //databaru=true;
        }
        parameter.setH1(parameterUpate.getH1());
        parameter.setH2(parameterUpate.getH2());
        parameter.setH3(parameterUpate.getH3());
        parameter.setH4(parameterUpate.getH4());

        //if(databaru==true){
        parameter=repository.saveAndFlush(parameter);
        //}
        return  parameter;
    }

}
