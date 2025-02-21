package com.iv.iv.repository;

import com.iv.iv.entity.PurchaseFrom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface PurchaseFromRepository extends CrudRepository<PurchaseFrom, Long> {
}
