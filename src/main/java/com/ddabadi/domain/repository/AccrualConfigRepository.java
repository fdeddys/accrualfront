package com.ddabadi.domain.repository;

import com.ddabadi.domain.AccrualConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by deddy on 5/6/16.
 */
public interface AccrualConfigRepository extends JpaRepository<AccrualConfig,Long> {
     Page<AccrualConfig> findByCoaBank(Pageable pageable);
}
