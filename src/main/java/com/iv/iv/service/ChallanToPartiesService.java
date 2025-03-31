package com.iv.iv.service;

import com.iv.iv.dto.BrandTotalQuantityDTO;
import com.iv.iv.dto.HomePageStatisticsDto;
import com.iv.iv.dto.OutstandingGroupByDaysDTO;
import com.iv.iv.entity.ChallanToParties;
import com.iv.iv.repository.ChallanToPartiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

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

    public HomePageStatisticsDto getHomePageStaistics() {
        HomePageStatisticsDto homePageStatisticsDto = new HomePageStatisticsDto();
        BigDecimal outStanding = challanToPartiesRepository.calculateTotalOutstanding();
        List[] resultOfOutstandingDaysGroup = challanToPartiesRepository.findOutstandingGroupedByDays();
        if (resultOfOutstandingDaysGroup == null) {
            OutstandingGroupByDaysDTO outstandingGroupByDaysDTO = new OutstandingGroupByDaysDTO("0.0", "0.0", "0.0", "0.0", "0.0", "0.0");
            homePageStatisticsDto.setOutstandingGroupByDaysDTO(outstandingGroupByDaysDTO);
            return homePageStatisticsDto;// Default values
        }
        // Parse the result into DTOs
        OutstandingGroupByDaysDTO outstandingGroupByDaysDTO = new OutstandingGroupByDaysDTO(
                resultOfOutstandingDaysGroup[0].get(1).toString(),
                resultOfOutstandingDaysGroup[1].get(1).toString(),
                resultOfOutstandingDaysGroup[2].get(1).toString(),
                resultOfOutstandingDaysGroup[3].get(1).toString(),
                resultOfOutstandingDaysGroup[4].get(1).toString(),
                resultOfOutstandingDaysGroup[5].get(1).toString()
        );
        homePageStatisticsDto.setOutstandingGroupByDaysDTO(outstandingGroupByDaysDTO);

        List<Object[]> results = challanToPartiesRepository.getTotalQuantityByBrand();
        homePageStatisticsDto.setNoOfDuePayments(challanToPartiesRepository.calculateCountOfPendingPayments());

        List<BrandTotalQuantityDTO> dtoList = new ArrayList<>();

        for (Object[] result : results) {
            String brand = (String) result[0];  // assuming first column is the brand (String)
            BigDecimal totalQuantity = (BigDecimal) result[1];  // assuming second column is totalQuantity (Long)
            BigDecimal outstanding = (BigDecimal) result[2];
            // Create a new DTO object and add it to the list
            BrandTotalQuantityDTO dto = new BrandTotalQuantityDTO(brand, totalQuantity, outstanding);
            dtoList.add(dto);
        }

        for (BrandTotalQuantityDTO result : dtoList) {
            String brand = result.getBrand();
            BigDecimal totalQuantity = result.getTotalQuantity();
            BigDecimal outstanding = result.getOutstanding();

            switch (brand) {
                case "A1":
                    homePageStatisticsDto.setTotalSaleOfA1(totalQuantity);
                    homePageStatisticsDto.setA1Outstanding(outstanding);
                    break;
                case "ACC":
                    homePageStatisticsDto.setTotalSaleOfAcc(totalQuantity);
                    homePageStatisticsDto.setAccOutstanding(outstanding);
                    break;
                case "Shakti":
                    homePageStatisticsDto.setTotalSaleOfShakti(totalQuantity);
                    homePageStatisticsDto.setShaktiOutstanding(outstanding);
                    break;
                default:
                    // Handle case where the brand does not match any of the known ones
                    break;
            }

            // Use these variables as needed
            System.out.println("Brand: " + brand + ", Total Quantity: " + totalQuantity);
        }
        return homePageStatisticsDto;
    }

}
