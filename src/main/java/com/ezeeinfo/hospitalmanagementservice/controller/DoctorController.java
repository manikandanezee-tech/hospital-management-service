package com.ezeeinfo.hospitalmanagementservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.hospitalmanagementservice.model.Doctor;
import com.ezeeinfo.hospitalmanagementservice.model.Patient;
import com.ezeeinfo.hospitalmanagementservice.service.DoctorService;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
	@Autowired
	private DoctorService doctorService;
	
	@GetMapping
	public List<Doctor> getDoctorsList(){
		return doctorService.getDoctorsList();
	}
	@GetMapping("/{id}")
	public ResponseEntity<?>getDoctorById(@PathVariable int id){
		return doctorService.getDoctorById(id);
	}
	@PostMapping
	public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor) {
		return doctorService.addDoctor(doctor);
	}
	@PutMapping("/{id}")
	public ResponseEntity<?>updateDoctor(@PathVariable int id, @RequestBody Doctor doctor){
		return doctorService.updateDoctor(id,doctor);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDoctor(@PathVariable int id){
		return doctorService.deleteDoctor(id);
	}
}
