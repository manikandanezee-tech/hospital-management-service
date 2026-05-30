package com.ezeeinfo.hospitalmanagementservice.validation;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MobileNumberValidation {

	public Boolean validateMobile(String mobile) {
		String regExp = "^[6-9][0-9]{9}$";
		return mobile.matches(regExp);
	}

}
