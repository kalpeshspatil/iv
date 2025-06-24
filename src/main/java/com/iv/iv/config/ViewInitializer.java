package com.iv.iv.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ViewInitializer {

    @Bean
    public CommandLineRunner createCustomerLedgerView(JdbcTemplate jdbcTemplate) {
        return args -> {
            String sql = """
                        CREATE OR REPLACE VIEW iv_tp_customer_ledger_view AS
                                        SELECT
                                          c.tp_customer_id AS tpCustomerId,
                                          c.tp_customer_name AS customerName,
                                          SUM(l.debit) AS debit,
                                          SUM(l.credit) AS credit,
                                          SUM(l.qty) AS qty,
                                          SUM(l.debit) - IFNULL(SUM(l.credit), 0) AS balance
                                        FROM
                                          iv_tp_customer_ledger l
                                        JOIN
                                          iv_to_party c ON l.tp_customer_id = c.tp_customer_id
                                        WHERE
                                          l.status = 'ACTIVE'  
                                        GROUP BY
                                          c.tp_customer_id;
                    """;

            jdbcTemplate.execute(sql);
            System.out.println("✅ View 'v_tp_customer_summary' created or replaced.");
        };
    }
}
