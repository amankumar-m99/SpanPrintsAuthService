package com.spanprints.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.PrintJobHistory;

@Repository
public interface PrintJobHistoryRepository extends JpaRepository<PrintJobHistory, Long>{

	public Optional<PrintJobHistory> findByUuid(String uuid);

}
