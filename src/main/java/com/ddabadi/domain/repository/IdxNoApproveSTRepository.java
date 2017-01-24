package com.ddabadi.domain.repository;

import com.ddabadi.domain.IdxNoApproveSuratTansfer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by deddy on 1/15/17.
 */
public interface IdxNoApproveSTRepository extends JpaRepository<IdxNoApproveSuratTansfer, Long> {

    IdxNoApproveSuratTansfer findByBulanAndTahun(String bulan, String tahun);

}
