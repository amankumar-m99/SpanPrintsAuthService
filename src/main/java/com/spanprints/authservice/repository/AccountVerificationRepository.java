package com.spanprints.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.VerificationToken;

@Repository
public interface AccountVerificationRepository extends JpaRepository<VerificationToken, Long>{

	public Optional<VerificationToken> findByToken(String token);

}
