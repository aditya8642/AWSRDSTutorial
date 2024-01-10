package com.awsrds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.awsrds.domain.EmployeeDomain;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDomain, Long> {

}
