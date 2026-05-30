package com.ezeeinfo.hospitalmanagementservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ezeeinfo.hospitalmanagementservice.model.Medicine;
import com.ezeeinfo.hospitalmanagementservice.repository.MedicineRepository;

@Service
public class MedicineService {
	@Autowired
	private MedicineRepository medicineRepository;

	public List<Medicine> getAllMedicineStock() {
		return medicineRepository.findAll();
	}

	public Medicine addMedicine(Medicine medicine) {
		return medicineRepository.save(medicine);
	}

	public ResponseEntity<?> updateMedicine(int id, Medicine medicine) {
		Medicine existingMedicine = medicineRepository.findById(id).orElse(null);
		if (existingMedicine != null) {
			existingMedicine.setExpiryDate(medicine.getExpiryDate());
			existingMedicine.setMedicineId(id);
			existingMedicine.setMedicineName(medicine.getMedicineName());
			existingMedicine.setPrice(medicine.getPrice());
			existingMedicine.setStockQuantity(medicine.getStockQuantity());
			existingMedicine.setSupplierName(medicine.getSupplierName());
			return ResponseEntity.ok(medicineRepository.save(existingMedicine));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medicine Not found");
	}

}
