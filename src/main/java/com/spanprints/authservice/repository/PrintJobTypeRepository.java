package com.spanprints.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.PrintJobType;

@Repository
public interface PrintJobTypeRepository extends JpaRepository<PrintJobType, Long>{

	public Optional<PrintJobType> findByName(String name);

}
