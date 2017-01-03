package com.ddabadi.domain.repository;

import com.ddabadi.domain.CoaDtl;
import com.ddabadi.domain.IdxNoRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by deddy on 7/3/16.
 */
public interface IdxNoRelRepository extends JpaRepository<IdxNoRel, Long> {

    IdxNoRel findByCoaDtlAndBulanAndTahun(CoaDtl coaDtl, String bulan, String tahun);
}
