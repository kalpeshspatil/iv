package com.iv.iv.service;

import com.iv.iv.dto.*;
import com.iv.iv.repository.SalesReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public RetailerSalesReportResponseDTO generateRetailerSalesReport(RetailerSalesReportRequest request) {
        List<RetailerSalesReportDTO> salesReportList = salesReportRepository.findRetailerSalesReport(
                request.getProductBrand(),
                request.getRetailerId(),
                request.getStartDate(),
                request.getEndDate()
        );

        BigDecimal totalQuantityOfRetailer = salesReportRepository.getTotalRetailerSaleQuantity(
                request.getProductBrand(),
                request.getRetailerId(),
                request.getStartDate(),
                request.getEndDate()
        );

        RetailerSalesReportResponseDTO salesReportResponseDTO = new RetailerSalesReportResponseDTO();
        salesReportResponseDTO.setSalesReportList(salesReportList);
        salesReportResponseDTO.setTotalQuantityOfRetailer(totalQuantityOfRetailer);

        return salesReportResponseDTO;
    }
}
