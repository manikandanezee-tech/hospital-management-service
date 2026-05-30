package com.ezeeinfo.hospitalmanagementservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.hospitalmanagementservice.model.Patient;
import com.ezeeinfo.hospitalmanagementservice.service.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientController {
	@Autowired
	private PatientService patientService;
	
	@GetMapping
	public List<Patient> getPatientsList(){
		return patientService.getPatientsList();
	}
	@PostMapping
	public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
		return patientService.addPatient(patient);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getPatientById(@PathVariable int id) {
		return patientService.getPatientById(id);
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePatient(@PathVariable int id,@RequestBody Patient patient){
		return patientService.updatePatient(id,patient);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePatient(@PathVariable int id){
		return patientService.deletePatient(id);
	}

}
