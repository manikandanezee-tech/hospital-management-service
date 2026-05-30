package com.ezeeinfo.hospitalmanagementservice.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PrescriptionDto {
	private String notes;
	private Integer quantity;
	private LocalDate prescribeDate;
	private Integer appointmentId;
	private Integer medicineId;

}
