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

    @Query(value = "WITH date_diff_cte AS (\n" +
            "    SELECT \n" +
            "        DATEDIFF(CURRENT_DATE, delivery_date) AS days_diff,\n" +
            "        outstanding_payment\n" +
            "    FROM iv_to_parties_of_challan\n" +
            "    WHERE outstanding_payment > 0\n" +
            "),\n" +
            "groups_cte AS (\n" +
            "    SELECT 'a1' AS outstanding_days_group, 0 AS min_days, 8 AS max_days UNION ALL\n" +
            "    SELECT 'a2', 9, 15 UNION ALL\n" +
            "    SELECT 'a3', 16, 21 UNION ALL\n" +
            "    SELECT 'a4', 22, 9999\n" +
            ")\n" +
            "SELECT \n" +
            "    g.outstanding_days_group,\n" +
            "    COALESCE(SUM(CASE WHEN d.days_diff BETWEEN g.min_days AND g.max_days THEN d.outstanding_payment ELSE 0 END), 0.00) AS total_outstanding_payment\n" +
            "FROM groups_cte g\n" +
            "LEFT JOIN date_diff_cte d \n" +
            "    ON d.days_diff BETWEEN g.min_days AND g.max_days\n" +
            "GROUP BY g.outstanding_days_group, g.min_days\n" +
            "ORDER BY g.min_days;", nativeQuery = true)
    List[] findOutstandingGroupedByDays();

    @Query(value = "SELECT \n" +
            "    p.product_brand,\n" +
            "    p.product_name, \n" +
            "    SUM(ctp.challan_to_parties_qty) AS total_quantity\n" +
            "FROM \n" +
            "    iv.iv_to_parties_of_challan ctp\n" +
            "JOIN \n" +
            "    iv.iv_challan c ON ctp.challan_id = c.challan_id\n" +
            "JOIN \n" +
            "    iv.iv_product p ON c.product_id = p.product_id\n" +
            "WHERE \n" +
            "    p.product_name IN ('C+', 'Suraksha', 'Shakti')\n" +
            "GROUP BY \n" +
            "    p.product_brand, p.product_name;", nativeQuery = true)
    List<Object[]> getTotalQuantityByProductName();
}
