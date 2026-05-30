package com.ezeeinfo.hospitalmanagementservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ezeeinfo.hospitalmanagementservice.ActiveStatus;
import com.ezeeinfo.hospitalmanagementservice.model.Patient;
import com.ezeeinfo.hospitalmanagementservice.repository.PatientRepository;
import com.ezeeinfo.hospitalmanagementservice.validation.MobileNumberValidation;

@Service
public class PatientService {
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private MobileNumberValidation mobileNumberValidation;

	public List<Patient> getPatientsList() {
		return patientRepository.findByActiveStatus(ActiveStatus.ACTIVE);
	}

	public ResponseEntity<?> addPatient(Patient patient) {
		if (mobileNumberValidation.validateMobile(patient.getPhone())) {
			patient.setActiveStatus(ActiveStatus.ACTIVE);
			return ResponseEntity.ok(patientRepository.save(patient));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Mobile Number");
	}

	public ResponseEntity<?> getPatientById(int id) {
		if (patientRepository.existsById(id)) {
			return ResponseEntity.ok(patientRepository.findById(id));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient Not found");
	}

	public ResponseEntity<?> updatePatient(int id, Patient patient) {
		Patient existingPatient = patientRepository.findById(id).orElse(null);
		if (existingPatient != null) {

			existingPatient.setPatientName(patient.getPatientName());
			existingPatient.setAddress(patient.getAddress());
			existingPatient.setAge(patient.getAge());
			existingPatient.setGender(patient.getGender());
			existingPatient.setPatientId(id);
			if (mobileNumberValidation.validateMobile(patient.getPhone())) {
				existingPatient.setPhone(patient.getPhone());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Mobile Number");
			}
			return ResponseEntity.ok(patientRepository.save(existingPatient));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient Not found");
	}

	public ResponseEntity<?> deletePatient(int id) {
		if (patientRepository.existsById(id)) {
			Patient patient=patientRepository.findById(id).orElseThrow();
			patient.setActiveStatus(ActiveStatus.DELETE);
			patientRepository.save(patient);
			return ResponseEntity.ok("Deleted");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Patient not found");
	}

}
