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

import com.ezeeinfo.hospitalmanagementservice.dto.AppointmentAddDto;
import com.ezeeinfo.hospitalmanagementservice.model.Appointment;
import com.ezeeinfo.hospitalmanagementservice.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@GetMapping
	public List<Appointment> appointmentList(){
		return appointmentService.appointmentList();
	}
	@PostMapping
	public ResponseEntity<?> addAppointment(@RequestBody AppointmentAddDto appointmentAddDto) {
		return appointmentService.addAppointment(appointmentAddDto);
		}
	@GetMapping("/{id}")
	public ResponseEntity<?>getAppointmentById(@PathVariable int id){
		return appointmentService.getAppointmentById(id);
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAppointment(@PathVariable int id,@RequestBody AppointmentAddDto appointmentAddDto){
		return appointmentService.updateAppointment(id,appointmentAddDto);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?>deleteAppointment(@PathVariable int id){
		return appointmentService.deleteAppointment(id);
	}
}
