package com.ezeeinfo.hospitalmanagementservice.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ezeeinfo.hospitalmanagementservice.dto.BillDto;
import com.ezeeinfo.hospitalmanagementservice.model.Appointment;
import com.ezeeinfo.hospitalmanagementservice.model.Bill;
import com.ezeeinfo.hospitalmanagementservice.model.Doctor;
import com.ezeeinfo.hospitalmanagementservice.model.Medicine;
import com.ezeeinfo.hospitalmanagementservice.model.Prescription;
import com.ezeeinfo.hospitalmanagementservice.repository.AppointmentRepository;
import com.ezeeinfo.hospitalmanagementservice.repository.BillRepository;
import com.ezeeinfo.hospitalmanagementservice.repository.MedicineRepository;
import com.ezeeinfo.hospitalmanagementservice.repository.PrescriptionRepository;

@Service
public class BillService {
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private PrescriptionRepository prescriptionRepository;

	public ResponseEntity<?> addBill(BillDto billDto) {
		Appointment appointment = appointmentRepository.findById(billDto.getAppointmentId()).orElse(null);

		if (appointment != null) {
			Doctor doctor = appointment.getDoctor();
// get prescription use of appointment(appointment id)
			Prescription prescription = prescriptionRepository.findByAppointment(appointment);
			
			if (prescription != null) {
				Double medicineCost = prescription.getMedicine().getPrice() * prescription.getQuantity();
				System.out.println("medicine cost " + medicineCost);
				
// for calculate actual price include doctor fee + medicine cost * quantity
				Bill bill = new Bill();

				bill.setBillDate(LocalDate.now());
				bill.setAppointment(appointment);
				bill.setTotalAmount(doctor.getConsultationFee() + medicineCost);
				return ResponseEntity.ok(billRepository.save(bill));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("prescripe the doctore first ");
			}
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment  not found");
	}

	public List<Bill> getAllBills() {
		return billRepository.findAll();
	}

}
