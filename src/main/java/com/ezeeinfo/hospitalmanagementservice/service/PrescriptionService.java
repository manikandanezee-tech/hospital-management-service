package com.ezeeinfo.hospitalmanagementservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ezeeinfo.hospitalmanagementservice.dto.PrescriptionDto;
import com.ezeeinfo.hospitalmanagementservice.model.Appointment;
import com.ezeeinfo.hospitalmanagementservice.model.Doctor;
import com.ezeeinfo.hospitalmanagementservice.model.Medicine;
import com.ezeeinfo.hospitalmanagementservice.model.Prescription;
import com.ezeeinfo.hospitalmanagementservice.repository.AppointmentRepository;
import com.ezeeinfo.hospitalmanagementservice.repository.DoctorRepository;
import com.ezeeinfo.hospitalmanagementservice.repository.MedicineRepository;
import com.ezeeinfo.hospitalmanagementservice.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

	@Autowired
	private PrescriptionRepository prescriptionRepository;
	@Autowired
	private MedicineRepository medicineRepository;
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private MedicineService medicineService;
	@Autowired
	private DoctorRepository doctorRepository;

	public List<Prescription> getAllPrescription() {
		return prescriptionRepository.findAll();
	}

	public ResponseEntity<?> addPrescription(PrescriptionDto prescriptionDto) {
		
		Medicine medicine = medicineRepository.findById(prescriptionDto.getMedicineId()).orElse(null);
		Appointment appointment = appointmentRepository.findById(prescriptionDto.getAppointmentId()).orElse(null);
		Doctor doctor=doctorRepository.findById(appointment.getDoctor().getDoctorId()).orElse(null);
//		for check previous appointment are completed or not
		boolean isAllPrescribe=true;
//		for get the same date and same doctors appointment list
		List<Appointment>appointmentList=appointmentRepository.findByDoctorAndAppointmentDate(doctor,appointment.getAppointmentDate());
		System.err.println(appointmentList.size());
		for(Appointment appointmentCheck:appointmentList) {
			if(appointmentCheck.getTokenNumber()<appointment.getTokenNumber() && !appointmentCheck.getStatus().equals("COMPLETED")) {
				isAllPrescribe=false;
			}
		}
		
		if (medicine != null && appointment != null) {
			if(isAllPrescribe){
			Prescription prescription = new Prescription();
			prescription.setPrescribeDate(prescriptionDto.getPrescribeDate());
			prescription.setAppointment(appointment);
			prescription.setMedicine(medicine);
			prescription.setNotes(prescriptionDto.getNotes());
			prescription.setQuantity(prescriptionDto.getQuantity());
// for update the medicine stock 
			Medicine updateMedicineStock = new Medicine();
			updateMedicineStock.setExpiryDate(medicine.getExpiryDate());
			updateMedicineStock.setMedicineName(medicine.getMedicineName());
			updateMedicineStock.setPrice(medicine.getPrice());
			if (medicine.getStockQuantity() > prescription.getQuantity()) {
				updateMedicineStock.setStockQuantity(medicine.getStockQuantity() - prescription.getQuantity());
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("out of stock");
			}
			updateMedicineStock.setSupplierName(medicine.getSupplierName());
//			call the medicine update method for stock update
			medicineService.updateMedicine(medicine.getMedicineId(), updateMedicineStock);
			
			Appointment appointmentUpdateStatus=new Appointment();
			appointmentUpdateStatus.setAppointmentDate(appointment.getAppointmentDate());
			appointmentUpdateStatus.setAppointmentTime(appointment.getAppointmentTime());
			appointmentUpdateStatus.setDoctor(appointment.getDoctor());
			appointmentUpdateStatus.setPatient(appointment.getPatient());
			appointmentUpdateStatus.setStatus("COMPLETED");
			appointmentUpdateStatus.setTokenNumber(appointment.getTokenNumber());
			appointmentUpdateStatus.setAppointmentId(appointment.getAppointmentId());
//			update the appointment for change the status.
			appointmentRepository.save(appointmentUpdateStatus);

			return ResponseEntity.ok(prescriptionRepository.save(prescription));
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please wait. Previous appointments are not completed.");
			}
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medicine or appointment not found");

	}
}
