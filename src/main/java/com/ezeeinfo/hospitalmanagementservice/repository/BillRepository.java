package com.ezeeinfo.hospitalmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezeeinfo.hospitalmanagementservice.model.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

}
