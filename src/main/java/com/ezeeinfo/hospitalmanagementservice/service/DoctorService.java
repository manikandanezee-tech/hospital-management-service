package com.ezeeinfo.hospitalmanagementservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ezeeinfo.hospitalmanagementservice.ActiveStatus;
import com.ezeeinfo.hospitalmanagementservice.model.Doctor;
import com.ezeeinfo.hospitalmanagementservice.repository.DoctorRepository;
import com.ezeeinfo.hospitalmanagementservice.validation.MobileNumberValidation;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private MobileNumberValidation mobileNumberValidation;

	public List<Doctor> getDoctorsList() {
		return doctorRepository.findByActiveStatus(ActiveStatus.ACTIVE);
	}

	public ResponseEntity<?> getDoctorById(int id) {
		if (doctorRepository.existsById(id)) {
			return ResponseEntity.ok(doctorRepository.findById(id));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor Not found");
	}

	public ResponseEntity<?> addDoctor(Doctor doctor) {
		if (mobileNumberValidation.validateMobile(doctor.getPhone())) {
			doctor.setActiveStatus(ActiveStatus.ACTIVE);
			return ResponseEntity.ok(doctorRepository.save(doctor));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Mobile Number");
	}

	public ResponseEntity<?> updateDoctor(int id, Doctor doctor) {
		Doctor existingDoctor = doctorRepository.findById(id).orElse(null);
		if (existingDoctor != null) {
			existingDoctor.setConsultationFee(doctor.getConsultationFee());
			existingDoctor.setDoctorId(id);
			existingDoctor.setDoctorName(doctor.getDoctorName());
			
			if (mobileNumberValidation.validateMobile(doctor.getPhone())) {
				existingDoctor.setPhone(doctor.getPhone());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Mobile Number");
			}
			existingDoctor.setSpecialization(doctor.getSpecialization());
			return ResponseEntity.ok(doctorRepository.save(existingDoctor));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("doctor not found");
	}

	public ResponseEntity<?> deleteDoctor(int id) {
		if (doctorRepository.existsById(id)) {
			Doctor doctor=doctorRepository.findById(id).orElseThrow();
			doctor.setActiveStatus(ActiveStatus.DELETE);
			doctorRepository.save(doctor);
			return ResponseEntity.ok("Deleted");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor Not Found");
	}

}
