package com.ddabadi.service;

import com.ddabadi.domain.IdxNoUrut;

import java.util.Date;

/**
 * Created by deddy on 5/15/16.
 */
public interface IdxNoUrutService {
    public IdxNoUrut save(IdxNoUrut idxNoUrut);
    public IdxNoUrut getByIdUserTahun(Long cariIdUser, String tahun);
}
