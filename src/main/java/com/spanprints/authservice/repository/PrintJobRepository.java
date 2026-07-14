package com.spanprints.authservice.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.enums.PrintJobStatus;

@Repository
public interface PrintJobRepository extends JpaRepository<PrintJob, Long>{

	Optional<PrintJob> findByUuid(String uuid);

	List<PrintJob> findByDateOfPlacedBetween(Instant startOfDay, Instant endOfDay);

    // or if you prefer >= start and < end (avoids double-boundary edge cases)
    List<PrintJob> findByDateOfPlacedGreaterThanEqualAndCreatedAtLessThan(Instant start, Instant end);

    List<PrintJob> findByPrintJobStatusAndDateOfDeliveryLessThan(PrintJobStatus status, Instant endExclusive);

    @Query("SELECT pJ FROM PrintJob pJ WHERE pJ.printJobStatus = :status AND pJ.dateOfDelivery < :endExclusive")
    List<PrintJob> findDueForStatus(@Param("status") PrintJobStatus status, @Param("endExclusive") Instant endExclusive);
    
    List<PrintJob> findByDateOfDeliveryBetween(Instant startOfDay, Instant endOfDay);
}
