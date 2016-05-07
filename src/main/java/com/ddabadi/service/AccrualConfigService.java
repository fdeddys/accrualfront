package com.ddabadi.service;

import com.ddabadi.domain.AccrualConfig;

/**
 * Created by deddy on 5/6/16.
 */
public interface AccrualConfigService {

    AccrualConfig getConfig();
    AccrualConfig updateConfig(AccrualConfig accrualConfig);

}
