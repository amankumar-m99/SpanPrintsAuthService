package com.spanprints.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.ProfilePic;

@Repository
public interface ProfilePicRepository extends JpaRepository<ProfilePic, Long> {
	Optional<ProfilePic> findByUuid();
}
