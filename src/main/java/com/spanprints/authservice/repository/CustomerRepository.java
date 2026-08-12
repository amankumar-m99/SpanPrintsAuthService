package com.spanprints.authservice.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

	public Optional<List<Customer>> findAllByEmail(String email);

	public Optional<Customer> findByUuid(String uuid);

	public Optional<Customer> findByName(String username);

	public List<Customer> findByNameContainingIgnoreCase(String name);

	public List<Customer> findByPrimaryPhoneNumberContainingIgnoreCase(String name);

	public Optional<List<Customer>> findAllByPrimaryPhoneNumber(String primaryPhoneNumber);

	@Query(value = """
	        SELECT c.* FROM customer c
	        WHERE
	            (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND
	            (:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND
	            (:address IS NULL OR LOWER(c.address) LIKE LOWER(CONCAT('%', :address, '%'))) AND
	            (:phone IS NULL OR c.primary_phone_number LIKE CONCAT('%', :phone, '%') OR c.alternate_phone_number LIKE CONCAT('%', :phone, '%')) AND
	            -- Filter by Order Count
	            (:orderCountMin IS NULL OR (SELECT COUNT(*) FROM print_job p WHERE p.customer_id = c.id) >= :orderCountMin) AND
	            (:orderCountMax IS NULL OR (SELECT COUNT(*) FROM print_job p WHERE p.customer_id = c.id) <= :orderCountMax) AND
	            -- Filter by Outstanding Balance Amounts
	            (:outstandingMin IS NULL OR (SELECT COALESCE(SUM(pj.pending_amount), 0) FROM print_job pj WHERE pj.customer_id = c.id) >= :outstandingMin) AND
	            (:outstandingMax IS NULL OR (SELECT COALESCE(SUM(pj.pending_amount), 0) FROM print_job pj WHERE pj.customer_id = c.id) <= :outstandingMax)
	    """,
	    countQuery = """
	        SELECT COUNT(*) FROM customer c
	        WHERE
	            (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND
	            (:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND
	            (:address IS NULL OR LOWER(c.address) LIKE LOWER(CONCAT('%', :address, '%'))) AND
	            (:phone IS NULL OR c.primary_phone_number LIKE CONCAT('%', :phone, '%') OR c.alternate_phone_number LIKE CONCAT('%', :phone, '%')) AND
	            (:orderCountMin IS NULL OR (SELECT COUNT(*) FROM print_job p WHERE p.customer_id = c.id) >= :orderCountMin) AND
	            (:orderCountMax IS NULL OR (SELECT COUNT(*) FROM print_job p WHERE p.customer_id = c.id) <= :orderCountMax) AND
	            (:outstandingMin IS NULL OR (SELECT COALESCE(SUM(pj.pending_amount), 0) FROM print_job pj WHERE pj.customer_id = c.id) >= :outstandingMin) AND
	            (:outstandingMax IS NULL OR (SELECT COALESCE(SUM(pj.pending_amount), 0) FROM print_job pj WHERE pj.customer_id = c.id) <= :outstandingMax)
	    """,
	    nativeQuery = true)
	    Page<Customer> filteredCustomers(
	        @Param("name") String name,
	        @Param("email") String email,
	        @Param("address") String address,
	        @Param("phone") String phone,
	        @Param("orderCountMin") Long orderCountMin,
	        @Param("orderCountMax") Long orderCountMax,
	        @Param("outstandingMin") BigDecimal outstandingMin,
	        @Param("outstandingMax") BigDecimal outstandingMax,
	        Pageable pageable
	    );
}
