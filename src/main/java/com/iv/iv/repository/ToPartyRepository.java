package com.iv.iv.repository;

import com.iv.iv.entity.ToParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ToPartyRepository extends CrudRepository<ToParty, Long> {
}
