package com.assessment.financial.repository;

import com.assessment.financial.dao.TransactionDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionDao, Long> {

}
