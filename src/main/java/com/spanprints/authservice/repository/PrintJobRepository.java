package com.spanprints.authservice.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.PrintJob;

@Repository
public interface PrintJobRepository extends JpaRepository<PrintJob, Long>{

	Optional<PrintJob> findByUuid(String uuid);

	List<PrintJob> findByDateOfPlacedBetween(Instant startOfDay, Instant endOfDay);

    // or if you prefer >= start and < end (avoids double-boundary edge cases)
    List<PrintJob> findByDateOfPlacedGreaterThanEqualAndCreatedAtLessThan(Instant start, Instant end);
}
