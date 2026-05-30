package com.ezeeinfo.hospitalmanagementservice.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medicine_data")
public class Medicine {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer medicineId;

	    private String medicineName;

	    private Integer stockQuantity;

	    private Double price;

	    private LocalDate expiryDate;

	    private String supplierName;
}
