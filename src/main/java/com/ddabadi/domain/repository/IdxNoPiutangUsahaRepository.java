package com.ddabadi.domain.repository;

import com.ddabadi.domain.IdxNoPiutangUsaha;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by deddy on 1/20/17.
 */
public interface IdxNoPiutangUsahaRepository extends JpaRepository<IdxNoPiutangUsaha,Long> {

    IdxNoPiutangUsaha findByBulanAndTahun(String bulan, String tahun);
}
