package com.iv.iv.repository;

import com.iv.iv.dto.BrandTotalQuantityDTO;
import com.iv.iv.entity.ChallanToParties;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ChallanToPartiesRepository extends CrudRepository<ChallanToParties, Long> {
    @Query(value = "SELECT COALESCE(SUM(c.outstanding_payment), 0) FROM iv.iv_to_parties_of_challan c", nativeQuery = true)
    BigDecimal calculateTotalOutstanding();

    @Query(value = "SELECT p.product_brand, SUM(ctp.challan_to_parties_qty) AS total_quantity, " +
            "SUM(ctp.outstanding_payment) as outstanding\n" +
            "FROM iv.iv_to_parties_of_challan ctp\n" +
            "JOIN iv.iv_challan c ON ctp.challan_id = c.challan_id\n" +
            "JOIN iv.iv_product p ON c.product_id = p.product_id\n" +
            "WHERE p.product_brand IN ('A1', 'ACC', 'Shakti')\n" +
            "GROUP BY p.product_brand", nativeQuery = true)
    List<Object[]> getTotalQuantityByBrand();

    @Query(value = "SELECT COUNT(*) FROM iv.iv_to_parties_of_challan WHERE payment_status = 'PENDING'", nativeQuery = true)
    int calculateCountOfPendingPayments();

    @Query(value = "SELECT \n" +
            "    CASE \n" +
            "        WHEN DATEDIFF(CURRENT_DATE, delivery_date) BETWEEN 0 AND 4 THEN '0-4 days'\n" +
            "        WHEN DATEDIFF(CURRENT_DATE, delivery_date) BETWEEN 5 AND 10 THEN '5-10 days'\n" +
            "        WHEN DATEDIFF(CURRENT_DATE, delivery_date) BETWEEN 11 AND 15 THEN '11-15 days'\n" +
            "        WHEN DATEDIFF(CURRENT_DATE, delivery_date) BETWEEN 16 AND 20 THEN '16-20 days'\n" +
            "\t\tWHEN DATEDIFF(CURRENT_DATE, delivery_date) BETWEEN 21 AND 30 THEN '21-30 days'\n" +
            "        ELSE '30+ days'\n" +
            "    END AS outstanding_days_group,\n" +
            "    COUNT(*) AS total_count, -- Total entries in each group\n" +
            "    SUM(outstanding_payment) AS total_outstanding_payment -- Total outstanding amount in each group\n" +
            "FROM \n" +
            "    iv_to_parties_of_challan\n" +
            "WHERE \n" +
            "    outstanding_payment > 0 -- Only include rows with pending payments\n" +
            "GROUP BY \n" +
            "    outstanding_days_group\n" +
            "ORDER BY \n" +
            "    MIN(DATEDIFF(CURRENT_DATE, delivery_date));\n" +
            "\n", nativeQuery = true)
    List<Object[]> findOutstandingGroupedByDays();
}
