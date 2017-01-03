package com.ddabadi.domain.repository;

import com.ddabadi.domain.IdxNoVoucher;
import com.ddabadi.enumer.JenisVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by deddy on 5/15/16.
 */
public interface IdxNoVoucherRepository extends JpaRepository<IdxNoVoucher,Long> {

    IdxNoVoucher findByJenisVoucherAndBulanAndTahun(JenisVoucher jenisVoucher, String bulan, String tahun);

}
