package com.iv.iv.service;

import com.iv.iv.dto.BrandTotalQuantityDTO;
import com.iv.iv.dto.HomePageStatisticsDTO;
import com.iv.iv.dto.OutstandingGroupByDaysDTO;
import com.iv.iv.dto.ProductTotalQuantityDTO;
import com.iv.iv.entity.Challan;
import com.iv.iv.entity.ChallanToParties;
import com.iv.iv.entity.ToPartiesLedgerEntries;
import com.iv.iv.entity.ToParty;
import com.iv.iv.repository.ChallanToPartiesRepository;
import com.iv.iv.repository.ToPartiesIndividualLedgerRepository;
import com.iv.iv.utility.IvConfigUtility;
import com.iv.iv.utility.IvConstants;
import com.iv.iv.utility.LedgerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ChallanToPartiesService {

    @Autowired
    ChallanToPartiesRepository challanToPartiesRepository;

    @Autowired
    IvConfigUtility configUtility;

    @Autowired
    private ToPartiesIndividualLedgerRepository toPartiesIndividualLedgerRepository;

    @Autowired
    private ToPartiesLedgerService toPartiesLedgerService;

    public List<ChallanToParties> getAllChallanToParties() {
        return (List<ChallanToParties>) challanToPartiesRepository.findAll();
    }

    public Optional<ChallanToParties> getPartyById(Long id) {
        return challanToPartiesRepository.findById(id);
    }

    public ChallanToParties saveProduct(ChallanToParties toParties) {
        return challanToPartiesRepository.save(toParties);
    }

    public void deleteChallanToParties(Long id) {
        challanToPartiesRepository.deleteById(id);
    }

    public HomePageStatisticsDTO getHomePageStaistics() {
        HomePageStatisticsDTO dto = new HomePageStatisticsDTO();

        dto.setOutstandingGroupByDaysDTO(fetchOutstandingGroupedByDays());
        dto.setNoOfDuePayments(challanToPartiesRepository.calculateCountOfPendingPayments());

        List<BrandTotalQuantityDTO> brandStats = fetchBrandStats();
        populateBrandData(dto, brandStats);

        List<ProductTotalQuantityDTO> productNameStats = fetchProductNameStats();
        populateProductNameData(dto, productNameStats);

        return dto;
    }


    //Fetch Outstanding by Grouped Days
    private OutstandingGroupByDaysDTO fetchOutstandingGroupedByDays() {
        List[] result = challanToPartiesRepository.findOutstandingGroupedByDays();

        if (result == null) {
            return new OutstandingGroupByDaysDTO("0.0", "0.0", "0.0", "0.0");
        }

        return new OutstandingGroupByDaysDTO(
                result[0].get(1).toString(),
                result[1].get(1).toString(),
                result[2].get(1).toString(),
                result[3].get(1).toString()
        );
    }

    //Convert Raw Result to DTO List
    private List<BrandTotalQuantityDTO> fetchBrandStats() {
        List<Object[]> results = challanToPartiesRepository.getTotalQuantityByBrand();
        List<BrandTotalQuantityDTO> dtoList = new ArrayList<>();

        for (Object[] row : results) {
            String brand = (String) row[0];
            BigDecimal totalQuantity = (BigDecimal) row[1];
            BigDecimal outstanding = (BigDecimal) row[2];
            dtoList.add(new BrandTotalQuantityDTO(brand, totalQuantity, outstanding));
        }

        return dtoList;
    }

    private List<ProductTotalQuantityDTO> fetchProductNameStats() {
        List<Object[]> results = challanToPartiesRepository.getTotalQuantityByProductName();
        List<ProductTotalQuantityDTO> dtoList = new ArrayList<>();

        for (Object[] row : results) {
            String brand = (String) row[0];
            String productName = (String) row[1];
            BigDecimal totalQuantity = (BigDecimal) row[2];

            dtoList.add(new ProductTotalQuantityDTO(brand, productName, totalQuantity));
        }

        return dtoList;
    }

    //Populate DTO with Brand-Specific Data
    private void populateBrandData(HomePageStatisticsDTO dto, List<BrandTotalQuantityDTO> brandStats) {
        for (BrandTotalQuantityDTO stat : brandStats) {
            switch (stat.getBrand()) {
                case "A1":
                    dto.setTotalSaleOfA1(stat.getTotalQuantity());
                    dto.setA1Outstanding(stat.getOutstanding());
                    break;
                case "ACC":
                    dto.setTotalSaleOfAcc(stat.getTotalQuantity());
                    dto.setAccOutstanding(stat.getOutstanding());
                    break;
                case "Shakti":
                    dto.setTotalSaleOfShakti(stat.getTotalQuantity());
                    dto.setShaktiOutstanding(stat.getOutstanding());
                    break;
                default:
                    // Optional: handle unknown brand if needed
                    break;
            }
        }
    }

    private void populateProductNameData(HomePageStatisticsDTO dto, List<ProductTotalQuantityDTO> productNameStats) {
        for (ProductTotalQuantityDTO stat : productNameStats) {
            String brand = stat.getProductBrand();
            String name = stat.getProductName();
            String key = brand + "|" + name;

            switch (key) {
                case "A1|PPC":
                    dto.setTotalSaleOfA1Ppc(stat.getTotalQuantity());
                    break;
                case "ACC|C+":
                    dto.setTotalSaleOfAccConcretePlus(stat.getTotalQuantity());
                    break;
                case "ACC|Suraksha":
                    dto.setTotalSaleOfAccSuraksha(stat.getTotalQuantity());
                    break;
                case "Shakti|PPC":
                    dto.setTotalSaleOfShaktiPpc(stat.getTotalQuantity());
                    break;
                default:
                    // Optional: log or handle unrecognized combinations
                    break;
            }
        }
    }


    public void recordLedgerEntries(ChallanToParties challanToParties) {
        if (challanToParties == null || challanToParties.getChallan() == null) {
            throw new IllegalArgumentException("Challan or ChallanToParties is null");
        }

        Challan challan = challanToParties.getChallan();
        BigDecimal grossAmount = challanToParties.getGrossAmount();
        ToParty customer = challanToParties.getSelectedToParty();

        if (customer == null) {
            throw new IllegalArgumentException("ToParty (customer) is null");
        }

        ToPartiesLedgerEntries ledgerEntry = new ToPartiesLedgerEntries();

        ledgerEntry.setDebit(grossAmount);
        ledgerEntry.setTpCustomerId(customer);
        ledgerEntry.setDate(challan.getOrderDeliveryDate());
        ledgerEntry.setQty(challanToParties.getChallanToPartiesQty());
       // ledgerEntry.setChallanToParty(challanToParties);

        String particular;
        try {
            particular = challan.getProduct().getProductBrand() + " "
                    + challan.getProduct().getProductName() + " "
                    + challanToParties.getChallanToPartiesQty() + " * "
                    + challanToParties.getChallanToPartiesRate();
        } catch (Exception e) {
            particular = "NA";
        }
        ledgerEntry.setParticular(particular);

        // Get current balance and calculate new balance
        BigDecimal currentBalance = configUtility.getCustomerBalance(customer.getTpCustomerId());
        BigDecimal newBalance = (currentBalance == null ? BigDecimal.ZERO : currentBalance).add(grossAmount);

        ledgerEntry.setBalance(newBalance);
        ledgerEntry.setType(IvConstants.DEBIT);

        toPartiesIndividualLedgerRepository.save(ledgerEntry);

    }

    public void markLedgerEntryAsVoided(ChallanToParties challanToParties) {
        Optional<ToPartiesLedgerEntries> ledgerEntries =
                toPartiesLedgerService.getLedgerByToChallanToParty(challanToParties.getChallanTpPkid());

            ledgerEntries.get().setStatus(LedgerStatus.VOIDED);   //If status marked cancelled or pending
            toPartiesIndividualLedgerRepository.save(ledgerEntries.get());
    }
}