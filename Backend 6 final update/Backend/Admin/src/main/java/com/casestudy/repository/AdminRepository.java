package com.casestudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.casestudy.model.AdminModel;

@Repository
public interface AdminRepository extends JpaRepository<AdminModel, Long> {
	AdminModel findByUsername(String username);
}
