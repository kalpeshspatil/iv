package com.iv.iv.service;

import com.iv.iv.dto.ToPartyLedgerDto;
import com.iv.iv.entity.ToPartiesLedgerEntries;
import com.iv.iv.repository.ToPartiesIndividualLedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToPartiesLedgerService {

    @Autowired
    ToPartiesIndividualLedgerRepository toPartiesIndividualLedgerRepository;

    public List<ToPartyLedgerDto> getAllToPartiesLedger() {
        return toPartiesIndividualLedgerRepository.getAllToPartiesLedger();
    }

    public List<ToPartiesLedgerEntries> getLedgerByToPartyCustomer(Long id) {
        return toPartiesIndividualLedgerRepository.findByTpCustomerId(id);

    }
}
