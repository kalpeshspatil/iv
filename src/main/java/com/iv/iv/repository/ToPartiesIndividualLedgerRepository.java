package com.iv.iv.repository;

import com.iv.iv.dto.ToPartyLedgerDto;
import com.iv.iv.entity.ToPartiesLedgerEntries;
import com.iv.iv.entity.ToParty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ToPartiesIndividualLedgerRepository extends CrudRepository<ToPartiesLedgerEntries, Long> {
   @Query(value = "select * from iv.iv_tp_customer_ledger where tp_customer_id=?1 and status='ACTIVE'", nativeQuery = true)
    List<ToPartiesLedgerEntries> findByTpCustomerId(Long tpCustomerId);

    @Query(value = "select * from iv.iv_tp_customer_ledger where challan_tp_pkid=?1 and status='ACTIVE'", nativeQuery = true)
    Optional<ToPartiesLedgerEntries> findByChallanToPartiesPkid(Long pkid);

    @Query(value = "select * from iv.iv_tp_customer_ledger_view", nativeQuery = true)
    List<ToPartyLedgerDto> getAllToPartiesLedger();


}
