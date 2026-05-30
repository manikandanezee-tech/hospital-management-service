package com.ezeeinfo.hospitalmanagementservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.hospitalmanagementservice.model.Medicine;
import com.ezeeinfo.hospitalmanagementservice.service.MedicineService;

@RestController
@RequestMapping("/medicine")
public class MedicineController {
	@Autowired
	private MedicineService medicineService;
	
	@GetMapping
	public List<Medicine> getAllMedicineStock(){
		return medicineService.getAllMedicineStock();
	}
	@PostMapping
	public Medicine addMedicine(@RequestBody Medicine medicine) {
		return medicineService.addMedicine(medicine);
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> updateMedicine(@PathVariable int id,@RequestBody Medicine medicine){
		return medicineService.updateMedicine(id,medicine);
	}

}
