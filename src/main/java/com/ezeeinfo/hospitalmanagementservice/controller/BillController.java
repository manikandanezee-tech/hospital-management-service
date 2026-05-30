package com.ezeeinfo.hospitalmanagementservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezeeinfo.hospitalmanagementservice.dto.BillDto;
import com.ezeeinfo.hospitalmanagementservice.model.Bill;
import com.ezeeinfo.hospitalmanagementservice.service.BillService;

@RestController
@RequestMapping("/bill")
public class BillController {
	@Autowired
	private BillService billService;
	@GetMapping
	public List<Bill>getAllBills(){
		return billService.getAllBills();
	}
	@PostMapping()
	public ResponseEntity<?>addBill(@RequestBody BillDto billDto){
		return billService.addBill(billDto);
	}
	

}
