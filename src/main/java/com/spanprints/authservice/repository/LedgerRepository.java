package com.spanprints.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.Ledger;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long>{
}
