package com.ddabadi.domain.repository;

import com.ddabadi.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by deddy on 4/25/16.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select c from Customer c where c.nama like :cariNama and c.kode like :cariKode and c.isSupplier <> true ")
    Page<Customer> findByNamaKodePage(@Param("cariNama")String cariNama,
                                      @Param("cariKode")String cariKode,
                                      Pageable pageable);

    @Query(value = "select c from Customer c where c.nama like :cariNama and c.kode like :cariKode  and c.isSupplier <> true ")
    List<Customer> findByNamaKodeList(@Param("cariNama")String cariNama,
                                      @Param("cariKode")String cariKode);

}
