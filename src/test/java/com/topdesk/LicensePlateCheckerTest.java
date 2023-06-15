package com.topdesk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LicensePlateCheckerTest {
	
	private static final LicensePlateSpecification SPECIFICATION_1 = new LicensePlateSpecification(Arrays.asList("KL", "SB", "F", "KO", "MN"), 1, 3, 7);
	private static final LicensePlateSpecification SPECIFICATION_2 = new LicensePlateSpecification(Arrays.asList("SB", "HH", "D", "M"), 2, 2, 6);

	private ILicensePlateChecker checker;
	
	@BeforeEach
	void init() {
		checker = new MyLicensePlateChecker();
	}
	
	@Test
	void testValid() {
		checker.setSpecification(SPECIFICATION_1);
		assertEquals(true, checker.isValid(new LicensePlate("KL", "AB", "222")));
	}
	
	@Test
	void testInvalidNull() {
		checker.setSpecification(SPECIFICATION_1);
		assertEquals(false, checker.isValid(null));
	}
	
	@Test
	void testInvalidTown() {
		checker.setSpecification(SPECIFICATION_1);
		assertEquals(false, checker.isValid(new LicensePlate(null, "AB", "2222")));
		assertEquals(false, checker.isValid(new LicensePlate("K", "AB", "2222")));
		assertEquals(false, checker.isValid(new LicensePlate("kl", "AB", "222")));
	}
	
	@Test
	void testInvalidLetters() {
		checker.setSpecification(SPECIFICATION_1);
		assertEquals(false, checker.isValid(new LicensePlate("KL", null, "222")));
		assertEquals(false, checker.isValid(new LicensePlate("KL", "", "222")));
		assertEquals(false, checker.isValid(new LicensePlate("KL", "A1", "222")));
	}
	
	@Test
	void testInvalidDigits() {
		checker.setSpecification(SPECIFICATION_1);
		assertEquals(false, checker.isValid(new LicensePlate("KL", "AB", null)));
		assertEquals(false, checker.isValid(new LicensePlate("KL", "AB", "abcd")));
		assertEquals(false, checker.isValid(new LicensePlate("KL", "AB", "2")));
		assertEquals(false, checker.isValid(new LicensePlate("KL", "AB", "22")));
		assertEquals(false, checker.isValid(new LicensePlate("KL", "AB", "22a")));
	}
	
	@Test
	void testInvalidTotalLength() {
		checker.setSpecification(SPECIFICATION_1);
		assertEquals(false, checker.isValid(new LicensePlate("F", "AB", "222")));
		assertEquals(false, checker.isValid(new LicensePlate("F", "A", "222")));
		assertEquals(false, checker.isValid(new LicensePlate("KL", "AB", "2222")));
		assertEquals(false, checker.isValid(new LicensePlate("KL", "ABC", "222")));
	}
	
	@Test
	void testReAssignment() {
		checker.setSpecification(SPECIFICATION_1);
		assertEquals(true, checker.isValid(new LicensePlate("KL", "AB", "222")));
		assertEquals(true, checker.isValid(new LicensePlate("SB", "A", "2222")));
		
		checker.setSpecification(SPECIFICATION_2);
		assertEquals(false, checker.isValid(new LicensePlate("KL", "AB", "222")));
		assertEquals(false, checker.isValid(new LicensePlate("SB", "A", "2222")));
	}
	
	@Test
	void testNoSpecification() {
		assertEquals(false, checker.isValid(new LicensePlate("F", "AB", "222")));
	}
	
	@Test
	void testNullSpecification() {
		checker.setSpecification(null);
		assertEquals(false, checker.isValid(new LicensePlate("F", "AB", "222")));
	}
	
}
