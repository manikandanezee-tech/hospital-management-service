package com.ezeeinfo.hospitalmanagementservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.hospitalmanagementservice.dto.PrescriptionDto;
import com.ezeeinfo.hospitalmanagementservice.model.Prescription;
import com.ezeeinfo.hospitalmanagementservice.service.PrescriptionService;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

	@Autowired
	private PrescriptionService prescriptionService;
	@GetMapping
	private List<Prescription>getAllPrescription(){
		return prescriptionService.getAllPrescription();
	}
	
	@PostMapping
	public ResponseEntity<?> addPrescription(@RequestBody PrescriptionDto prescriptionDto) {
		return prescriptionService.addPrescription(prescriptionDto);
	}
}
