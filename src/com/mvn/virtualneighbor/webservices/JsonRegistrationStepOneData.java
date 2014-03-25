package com.mvn.virtualneighbor.webservices;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

public class JsonRegistrationStepOneData {

	private String message;
	private List<String> type;

	public JsonRegistrationStepOneData() {
		setType(new ArrayList<String>());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

}
