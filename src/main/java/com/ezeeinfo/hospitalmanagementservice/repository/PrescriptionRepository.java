package com.ezeeinfo.hospitalmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezeeinfo.hospitalmanagementservice.model.Appointment;
import com.ezeeinfo.hospitalmanagementservice.model.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

//	send the prescription to the billService for calculate the bill amount because prescription have quantity +
//	medicine
	Prescription findByAppointment(Appointment appointment);

}
