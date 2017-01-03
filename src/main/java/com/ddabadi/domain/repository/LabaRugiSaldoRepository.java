package com.ddabadi.domain.repository;

import com.ddabadi.domain.CoaDtl;
import com.ddabadi.domain.LabaRugiSaldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by deddy on 7/14/16.
 */
public interface LabaRugiSaldoRepository extends JpaRepository<LabaRugiSaldo,Long> {

    List<LabaRugiSaldo> getByBulanTahun(String bulanTahun);

    @Modifying
    @Transactional
    void deleteByBulanTahun(String bulanTahun);

}
