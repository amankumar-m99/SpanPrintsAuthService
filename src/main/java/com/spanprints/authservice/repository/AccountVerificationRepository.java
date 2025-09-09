package com.spanprints.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.AccountVerificationToken;

@Repository
public interface AccountVerificationRepository extends JpaRepository<AccountVerificationToken, Long>{

	public Optional<AccountVerificationToken> findByToken(String token);

}
