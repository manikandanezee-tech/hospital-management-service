package com.ezeeinfo.hospitalmanagementservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezeeinfo.hospitalmanagementservice.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

// its used to show the all records except soft deleted records
	List<Patient> findByActiveStatus(Integer active);

}
