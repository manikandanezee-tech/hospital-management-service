package com.ezeeinfo.hospitalmanagementservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;
@Data
public class AppointmentAddDto {
	private LocalDate appointmentDate;
	private LocalTime appointmentTime;
	private Integer patientId;
	private Integer doctorId;

}
