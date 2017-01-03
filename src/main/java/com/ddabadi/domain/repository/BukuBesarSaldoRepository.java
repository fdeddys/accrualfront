package com.ddabadi.domain.repository;

import com.ddabadi.domain.BukuBesarSaldo;
import com.ddabadi.domain.CoaDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by deddy on 7/8/16.
 */
public interface BukuBesarSaldoRepository extends JpaRepository<BukuBesarSaldo, Long> {

    List<BukuBesarSaldo> getByBulanTahun(String bulanTahun);
    BukuBesarSaldo getByCoaDtlAndBulanTahun(CoaDtl coaDtl, String bulanTahun);

    @Modifying
    @Transactional
    //@Query(value = "delete from BukuBesarSaldo b where b.bulanTahun =:bulanTahun ")
    void deleteByBulanTahun(String bulanTahun);

    BukuBesarSaldo findByBulanTahunAndCoaDtlAndBankKodeLikeAndRelLikeAndCustomerKodeLike(String bulanTahun,
                                                                                         CoaDtl coaDtl,
                                                                                         String kodeBank,
                                                                                         String rel,
                                                                                         String kodeCust);

}
