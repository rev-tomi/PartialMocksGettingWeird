package com.example;

public class TestLegacyCode extends LegacyCode {
	
	private final String mockFieldValue;
	
	public TestLegacyCode(String mockFieldValue) {
		this.mockFieldValue = mockFieldValue;
	}

	public String getField() {
		return mockFieldValue;
	}

	public void setField(String field) {
		// NOOP
	}
}
