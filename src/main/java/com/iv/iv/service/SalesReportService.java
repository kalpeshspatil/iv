package com.iv.iv.service;

import com.iv.iv.dto.SalesReportRequest;
import com.iv.iv.dto.SalesReportResponseDTO;
import com.iv.iv.repository.SalesReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iv.iv.dto.SalesReportDTO;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SalesReportService {

    @Autowired
    SalesReportRepository salesReportRepository;

    public SalesReportResponseDTO generateSalesReport(SalesReportRequest request) {
        // Write the logic to fetch filtered sales data
         List<SalesReportDTO> salesReportList = salesReportRepository.findSalesReport(
                request.getProductBrand(),
                request.getCustomerId(),
                request.getStartDate(),
                request.getEndDate()
        );

        BigDecimal totalOfSaleBillingAmount = salesReportRepository.getTotalOfSaleBillingAmount(
                request.getProductBrand(),
                request.getCustomerId(),
                request.getStartDate(),
                request.getEndDate()
        );

        SalesReportResponseDTO salesReportResponseDTO = new SalesReportResponseDTO();
        salesReportResponseDTO.setSalesReportList(salesReportList);
        salesReportResponseDTO.setTotalOfSaleBillingAmount(totalOfSaleBillingAmount);

        return salesReportResponseDTO;
    }
}
