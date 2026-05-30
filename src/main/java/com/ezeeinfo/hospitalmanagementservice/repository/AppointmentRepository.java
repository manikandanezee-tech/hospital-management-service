package com.ezeeinfo.hospitalmanagementservice.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezeeinfo.hospitalmanagementservice.model.Appointment;
import com.ezeeinfo.hospitalmanagementservice.model.Doctor;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

//	send list of appointment to the appointmentService for generate the token number
	List<Appointment> findByAppointmentDateAndDoctor(LocalDate appointmentDate, Doctor doctor);

// send list of appointment to the prescriptionService for find all prescription's are completed
	List<Appointment> findByDoctorAndAppointmentDate(Doctor doctor, LocalDate appointmentDate);

// its used to show the all records except soft deleted records
	List<Appointment> findByActiveStatus(Integer active);

	

}
