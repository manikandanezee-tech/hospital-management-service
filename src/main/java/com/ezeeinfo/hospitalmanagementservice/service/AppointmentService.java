package com.ezeeinfo.hospitalmanagementservice.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ezeeinfo.hospitalmanagementservice.ActiveStatus;
import com.ezeeinfo.hospitalmanagementservice.dto.AppointmentAddDto;
import com.ezeeinfo.hospitalmanagementservice.exception.BussinessException;
import com.ezeeinfo.hospitalmanagementservice.exception.ResourceNotFoundException;
import com.ezeeinfo.hospitalmanagementservice.model.Appointment;
import com.ezeeinfo.hospitalmanagementservice.model.Doctor;
import com.ezeeinfo.hospitalmanagementservice.model.Patient;
import com.ezeeinfo.hospitalmanagementservice.repository.AppointmentRepository;
import com.ezeeinfo.hospitalmanagementservice.repository.DoctorRepository;
import com.ezeeinfo.hospitalmanagementservice.repository.PatientRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private DoctorRepository doctorRepository;

	public List<Appointment> appointmentList() {
		return appointmentRepository.findByActiveStatus(ActiveStatus.ACTIVE);
	}

	public ResponseEntity<?> addAppointment(AppointmentAddDto appointmentAddDto) {

		Appointment appointment = new Appointment();
		Patient patient = patientRepository.findById(appointmentAddDto.getPatientId()).orElse(null);
		Doctor doctor = doctorRepository.findById(appointmentAddDto.getDoctorId()).orElse(null);

//	for generate the token number on current appointment object
		if (patient != null && doctor != null) {
			
//			check if the patient already appointed the same doctor at same date
			boolean isAlreadyBooked=appointmentRepository.existsByDoctorAndAppointmentDateAndPatient(doctor,appointmentAddDto.getAppointmentDate(),patient);
	if(!isAlreadyBooked) {
			List<Appointment> appointmentCount = appointmentRepository
					.findByAppointmentDateAndDoctor(appointmentAddDto.getAppointmentDate(), doctor);

			appointment.setAppointmentDate(appointmentAddDto.getAppointmentDate());
			appointment.setAppointmentTime(appointmentAddDto.getAppointmentTime());
			appointment.setTokenNumber(appointmentCount.size() + 1);
			appointment.setStatus("BOOKED");
			appointment.setDoctor(doctor);
			appointment.setPatient(patient);
			appointment.setActiveStatus(ActiveStatus.ACTIVE);
			appointmentRepository.save(appointment);
			return ResponseEntity.ok("success");
		}
		else {
			throw new BussinessException("You already appointed the doctor on that date");
		}
		}
		throw new ResourceNotFoundException("Doctor or Patient not found");
	}

	public ResponseEntity<?> getAppointmentById(int id) {
		if (appointmentRepository.existsById(id)) {
			return ResponseEntity.ok(appointmentRepository.findById(id));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
	}

	public ResponseEntity<?> updateAppointment(int id, AppointmentAddDto appointmentAddDto) {
		Appointment existingAppointment = appointmentRepository.findById(id).orElse(null);
		if (existingAppointment != null) {
//			get the patient and doctor via appointmentDto -> getPatient id or getDoctorId
			Patient patient = patientRepository.findById(appointmentAddDto.getPatientId()).orElse(null);
			Doctor doctor = doctorRepository.findById(appointmentAddDto.getDoctorId()).orElse(null);
			if (patient != null && doctor != null) {
				existingAppointment.setAppointmentId(id);
				existingAppointment.setDoctor(doctor);
				existingAppointment.setAppointmentDate(appointmentAddDto.getAppointmentDate());
				existingAppointment.setAppointmentTime(appointmentAddDto.getAppointmentTime());
				existingAppointment.setPatient(patient);
				existingAppointment.setStatus("BOOKED");

				appointmentRepository.save(existingAppointment);

				return ResponseEntity.ok("updated successfully");
			} else {
				throw new ResourceNotFoundException("invalid doctor or patient id");
			}
		}
		throw new ResourceNotFoundException("Appointment not found");
	}

	public ResponseEntity<?> deleteAppointment(int id) {
		if (appointmentRepository.existsById(id)) {
			Appointment appointment = appointmentRepository.findById(id).orElseThrow();
			appointment.setActiveStatus(ActiveStatus.DELETE);
			appointmentRepository.save(appointment);

//			instead of deleting just change the active status for avoid relationships error on deletion
			return ResponseEntity.ok("Deleted");
		}
		throw new ResourceNotFoundException("Appointment not found");
	}

	public List<Appointment> getAppointmentsByDate(LocalDate date) {
		
		return appointmentRepository.findByAppointmentDate(date);
	}
}
