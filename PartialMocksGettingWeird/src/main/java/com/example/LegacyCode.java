package com.example;

public class LegacyCode {
	
	private String otherField;

	public String getField() {
		throw new RuntimeException("Ooops");
	}

	public void setField(String field) {
		throw new RuntimeException("Ooops");
	}

	public String getOtherField() {
		return otherField;
	}

	public void setOtherField(String otherField) {
		this.otherField = otherField;
	}
	
}
