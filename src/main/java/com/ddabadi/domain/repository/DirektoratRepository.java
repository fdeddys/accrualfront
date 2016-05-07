package com.ddabadi.domain.repository;

import com.ddabadi.domain.Direktorat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by deddy on 4/25/16.
 */
public interface DirektoratRepository extends JpaRepository<Direktorat,Long> {

    @Query(value = "select  d from Direktorat d where d.nama like :cariNama")
    List<Direktorat> findByNama(@Param("cariNama")String cariNama);

}
