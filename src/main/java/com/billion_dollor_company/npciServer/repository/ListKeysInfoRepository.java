package com.billion_dollor_company.npciServer.repository;


import com.billion_dollor_company.npciServer.models.ListKeysInfo;
import com.billion_dollor_company.npciServer.models.projections.ListKeysInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListKeysInfoRepository extends JpaRepository<ListKeysInfo, Integer> {

    List<ListKeysInfo> findAll();

    @Query("SELECT l.id , l.publicKey FROM ListKeysInfo l")
    List<ListKeysInfoProjection> getListKeys();



}
