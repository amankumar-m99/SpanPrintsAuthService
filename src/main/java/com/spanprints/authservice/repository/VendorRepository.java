package com.spanprints.authservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

	public Optional<List<Vendor>> findAllByEmail(String email);

	public Optional<Vendor> findByName(String username);

	public Optional<Vendor> findByUuid(String uuid);

	public Optional<List<Vendor>> findAllByPrimaryPhoneNumber(String primaryPhoneNumber);
}
