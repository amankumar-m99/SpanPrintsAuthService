package com.spanprints.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.PersonalDetails;

@Repository
public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, Long> {
}
