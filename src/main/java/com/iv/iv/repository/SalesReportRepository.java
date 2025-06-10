package com.iv.iv.repository;

import com.iv.iv.entity.Challan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.iv.iv.dto.SalesReportDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesReportRepository extends CrudRepository<Challan, Long> {

    @Query(value = "SELECT tp.tp_customer_name AS customerName, tpc.delivery_date AS saleDate, " +
            "tpc.challan_to_parties_qty AS quantity, c.pf_rate AS purchaseBillingRate, " +
            "tpc.challan_to_parties_rate AS saleBillingRate, tpc.gross_amount AS totalAmount " +
            "FROM iv_to_parties_of_challan tpc " +
            "JOIN iv_challan c ON tpc.challan_id = c.challan_id " +
            "JOIN iv_product p ON c.product_id = p.product_id " +
            "JOIN iv_to_party tp ON tpc.tp_customer_id = tp.tp_customer_id " +
            "WHERE (:productBrand IS NULL OR p.product_brand = :productBrand) " +
            "AND tpc.delivery_date BETWEEN :startDate AND :endDate " +
            "AND (:customerId IS NULL OR tp.tp_customer_id = :customerId) " +
            "ORDER BY tpc.delivery_date ASC", nativeQuery = true )
    List<SalesReportDTO> findSalesReport(
            @Param("productBrand") String productBrand,
            @Param("customerId") Long customerId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate

    );




    @Query(value = "SELECT SUM(tpc.gross_amount) FROM iv_to_parties_of_challan tpc " +
            "JOIN iv_challan c ON tpc.challan_id = c.challan_id " +
            "JOIN iv_product p ON c.product_id = p.product_id " +
            "JOIN iv_to_party tp ON tpc.tp_customer_id = tp.tp_customer_id " +
            "WHERE p.product_brand = :productBrand " +
            "AND tpc.delivery_date BETWEEN :startDate AND :endDate " +
            "AND (:customerId IS NULL OR tp.tp_customer_id = :customerId)", nativeQuery = true)
    BigDecimal getTotalOfSaleBillingAmount(
            @Param("productBrand") String productBrand,
            @Param("customerId") Long customerId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


}


