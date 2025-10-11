package com.spanprints.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
}
