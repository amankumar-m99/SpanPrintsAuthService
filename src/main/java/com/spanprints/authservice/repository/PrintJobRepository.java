package com.spanprints.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.PrintJob;

@Repository
public interface PrintJobRepository extends JpaRepository<PrintJob, Long>{
}
