package com.ddabadi.domain.repository;

import com.ddabadi.domain.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by deddy on 4/23/16.
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    @Query(value = "select s from Supplier s where s.nama like :cariNama")
    Page<Supplier> findByNamaPage(@Param("cariNama")String cariNama, Pageable pageable);

    @Query(value = "select s from Supplier s ")
    Page<Supplier> findAllPage(Pageable pageable);

}
