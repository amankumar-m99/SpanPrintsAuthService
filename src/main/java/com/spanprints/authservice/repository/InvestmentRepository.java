package com.spanprints.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.Investment;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long>, JpaSpecificationExecutor<Investment> {

	Optional<Investment> findByUuid(String uuid);
}
