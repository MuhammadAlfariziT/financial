package com.assessment.financial.repository;

import com.assessment.financial.dao.MemberDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberDao, Long> {

}
