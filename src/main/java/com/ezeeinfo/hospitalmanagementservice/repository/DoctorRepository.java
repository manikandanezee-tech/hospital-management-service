package com.ezeeinfo.hospitalmanagementservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezeeinfo.hospitalmanagementservice.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

// its used to show the all records except soft deleted records
	List<Doctor> findByActiveStatus(int i);

}
