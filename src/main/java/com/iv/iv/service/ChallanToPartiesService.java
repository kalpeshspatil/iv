package com.iv.iv.service;

import com.iv.iv.dto.BrandTotalQuantityDTO;
import com.iv.iv.dto.HomePageStatisticsDTO;
import com.iv.iv.dto.OutstandingGroupByDaysDTO;
import com.iv.iv.dto.ProductTotalQuantityDTO;
import com.iv.iv.entity.ChallanToParties;
import com.iv.iv.repository.ChallanToPartiesRepository;
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






}
