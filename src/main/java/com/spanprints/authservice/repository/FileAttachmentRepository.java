package com.spanprints.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.FileAttachment;

@Repository
public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {
	Optional<FileAttachment> findByUuid(String uuid);
}
