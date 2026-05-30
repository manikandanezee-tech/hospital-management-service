package com.ezeeinfo.hospitalmanagementservice.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="prescription_data")
public class Prescription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer prescriptionId;
	
	private String notes;
	
	private int quantity;
	
	private LocalDate prescribeDate;
	
	@ManyToOne
	@JoinColumn(name = "appointment_id")
	private Appointment appointment;
	
	@ManyToOne
	@JoinColumn(name = "medicine_id")
	private Medicine medicine;
	
}
