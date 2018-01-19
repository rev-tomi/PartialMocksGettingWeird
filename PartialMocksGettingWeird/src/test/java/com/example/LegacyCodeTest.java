package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class LegacyCodeTest {

	@Test
	public void demonstrateEmulatedSetter() {
		// GIVEN
		// do some mocking
		LegacyCode legacyCode = Mockito.mock(LegacyCode.class);
		when(legacyCode.getOtherField()).thenReturn("asdf");
		
		// pretend there's a real setter and getter
		// The following reference pretends to be the actual field
		// An AtomicReference is much more than a reference. Anyway, it's a simple drop-in for this case, so it's fine.
		// It's good practice to mark it final. The requirement is that it has to be effectively final.
		final AtomicReference<String> fieldRef = new AtomicReference<>();
		// then we mock the getter: an Answer can contain logic to retrieve the value from fieldRef
		when(legacyCode.getField()).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				return fieldRef.get();
			}
		});
		// then we mock the setter: an Answer can contain logic to set value to fieldRef
		// this comes with different syntax: first we define the answer, then comes the when() part
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				String str = (String) invocation.getArguments()[0];
				fieldRef.set(str);
				// generally we return null for Void type
				return null;
			}
		}).when(legacyCode).setField(anyString());
		
		// WHEN
		// imagine real code here that calls this setter below
		legacyCode.setField("asdfasdf");
		
		// THEN
		// a usual assert
		assertEquals("asdfasdf", legacyCode.getField());
		// this is here to double-check that the value is really in the field
		assertSame(fieldRef.get(), legacyCode.getField());
	}
	
	@Test
	public void demonstrateBarebonePartialMock() {
		// GIVEN
		LegacyCode sut = new TestLegacyCode("asdf");
		
		// WHEN
		sut.setOtherField("ghij");
		
		//THEN
		// testing the partial mock - i.e. no exception
		assertEquals("asdf", sut.getField());
		// testing the setter-getter
		assertEquals("ghij", sut.getOtherField());
	}
	
	@Test
	public void demonstrateMockitoPartialMock() {
		//GIVEN
		LegacyCode sut = spy(LegacyCode.class);
		doReturn("asdf").when(sut).getField();
		
		// WHEN
		sut.setOtherField("ghij");
		
		//THEN
		// testing the partial mock - i.e. no exception
		assertEquals("asdf", sut.getField());
		// testing the setter-getter
		assertEquals("ghij", sut.getOtherField());
	}
	
	
}
