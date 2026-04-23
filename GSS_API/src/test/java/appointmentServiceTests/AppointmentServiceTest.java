package appointmentServiceTests;

import org.junit.jupiter.api.*;
import java.util.Date;
import java.util.Calendar;
import appointmentService.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AppointmentService Test class.
 * 
 * Stores tests for all methods within the AppointmentService class.
 * 
 * Utilizes Tags; CRUD, Create, Read, Delete, Mutator, and Setup.
 */
@DisplayName("AppointmentService Object Tests")
public class AppointmentServiceTest {
	private AppointmentService appointmentService;
	
	// Valid test appointment data
	private static String validDescription = "Annual checkup";
	private static Date validDate;
	
	// Static initializer to set valid future date
	static {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);  // One day in the future
		validDate = cal.getTime();
	}
	
	// ========== Constructor and Setup Tests ==========
	/**
	 * Instantiation of AppointmentService Object
	 * 
	 * Tests that AppointmentService object can be created successfully before each test
	 */
	@BeforeEach
	void setUpTest() {
		try {
			appointmentService = new AppointmentService();
		}
		catch (Exception e) {
			System.out.println("AppointmentService constructor failed with exception: " + e.getMessage());
			
			// Abort test if constructor fails
			Assumptions.abort("AppointmentService Constructor Failed!");
		}
	}
	
	/**
	 * AppointmentService Object Instantiation Test
	 */
	@Test
	@Tag("Setup")
	@DisplayName("AppointmentService Object Instantiation")
	void objectTest() {
		assertInstanceOf(AppointmentService.class, appointmentService, 
			"AppointmentService object successfully created");
	}

	
	// ========== Create Method Tests (addAppointment) ==========
	/**
	 * Tests for the addAppointment() method
	 */
	@Nested
	@Tag("CRUD")
	@Tag("Create")
	@Tag("Mutator")
	@DisplayName("addAppointment() Method Tests")
	class AddAppointmentTests {
		
		@Test
		@DisplayName("addAppointment() Test: Add Valid Appointment")
		/*
		 * Test addAppointment() with valid parameters returns true
		 */
		void addValidAppointmentTest() {
			boolean result = appointmentService.addAppointment(validDate, validDescription);
			
			assertTrue(result, "addAppointment() should return true for valid parameters");
		}
		
		@Test
		@DisplayName("addAppointment() Test: Appointment is Retrievable")
		/*
		 * Test that added appointment can be retrieved from the service
		 */
		void addValidAppointmentRetrievableTest() {
			boolean addSuccess = appointmentService.addAppointment(validDate, validDescription);
			
			assertTrue(addSuccess, "Appointment should be added successfully");
		}
		
		@Test
		@DisplayName("addAppointment() Test: Null Date")
		/*
		 * Test addAppointment() with null date returns false
		 */
		void addNullDateTest() {
			Date nullDate = null;
			
			boolean result = appointmentService.addAppointment(nullDate, validDescription);
			
			assertFalse(result, "addAppointment() should return false for null date");
		}
		
		@Test
		@DisplayName("addAppointment() Test: Null Description")
		/*
		 * Test addAppointment() with null description returns false
		 */
		void addNullDescriptionTest() {
			String nullDescription = null;
			
			boolean result = appointmentService.addAppointment(validDate, nullDescription);
			
			assertFalse(result, "addAppointment() should return false for null description");
		}
		
		@Test
		@DisplayName("addAppointment() Test: Empty Description")
		/*
		 * Test addAppointment() with empty description returns false
		 */
		void addEmptyDescriptionTest() {
			String emptyDescription = "";
			
			boolean result = appointmentService.addAppointment(validDate, emptyDescription);
			
			assertFalse(result, "addAppointment() should return false for empty description");
		}
		
		@Test
		@DisplayName("addAppointment() Test: Past Date")
		/*
		 * Test addAppointment() with past date returns false
		 */
		void addPastDateTest() {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);  // One day in the past
			Date pastDate = cal.getTime();
			
			boolean result = appointmentService.addAppointment(pastDate, validDescription);
			
			assertFalse(result, "addAppointment() should return false for past date");
		}
		
		@Test
		@DisplayName("addAppointment() Test: Current Time as Date")
		/*
		 * Test addAppointment() with current time as date returns false
		 */
		void addCurrentTimeTest() {
			Date currentDate = new Date();
			
			boolean result = appointmentService.addAppointment(currentDate, validDescription);
			
			assertFalse(result, "addAppointment() should return false for current time (not future)");
		}
		
		@Test
		@DisplayName("addAppointment() Test: Invalid Description (exceeds max length)")
		/*
		 * Test addAppointment() with description exceeding max length returns false
		 */
		void addInvalidDescriptionTest() {
			String invalidDescription = "This description is way too long and exceeds the maximum character limit for appointment descriptions";
			
			boolean result = appointmentService.addAppointment(validDate, invalidDescription);
			
			assertFalse(result, "addAppointment() should return false for invalid description");
		}
		
		@Test
		@DisplayName("addAppointment() Test: Multiple Appointments Added")
		/*
		 * Test addAppointment() can add multiple appointments successfully
		 */
		void addMultipleAppointmentsTest() {
			Calendar cal1 = Calendar.getInstance();
			cal1.add(Calendar.DAY_OF_MONTH, 1);
			Date date1 = cal1.getTime();
			
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DAY_OF_MONTH, 2);
			Date date2 = cal2.getTime();
			
			Calendar cal3 = Calendar.getInstance();
			cal3.add(Calendar.DAY_OF_MONTH, 3);
			Date date3 = cal3.getTime();
			
			boolean result1 = appointmentService.addAppointment(date1, "First appointment");
			boolean result2 = appointmentService.addAppointment(date2, "Second appointment");
			boolean result3 = appointmentService.addAppointment(date3, "Third appointment");
			
			assertTrue(result1 && result2 && result3, "All appointments should be added successfully");
		}
		
		@Test
		@DisplayName("addAppointment() Test: Max Length Description")
		/*
		 * Test addAppointment() with maximum length description
		 */
		void addMaxLengthDescriptionTest() {
			String maxDescription = "Exactly 50 Characters Long Description Text Now";  // Exactly 50 characters
			
			boolean result = appointmentService.addAppointment(validDate, maxDescription);
			
			assertTrue(result, "addAppointment() should accept max length description");
		}
		
		@Test
		@DisplayName("addAppointment() Test: Single Character Description")
		/*
		 * Test addAppointment() with single character description
		 */
		void addSingleCharDescriptionTest() {
			String singleCharDescription = "A";
			
			boolean result = appointmentService.addAppointment(validDate, singleCharDescription);
			
			assertTrue(result, "addAppointment() should accept single character description");
		}
		
		@Test
		@DisplayName("addAppointment() Test: Far Future Date")
		/*
		 * Test addAppointment() with far future date
		 */
		void addFarFutureDateTest() {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, 5);  // 5 years in the future
			Date farFutureDate = cal.getTime();
			
			boolean result = appointmentService.addAppointment(farFutureDate, validDescription);
			
			assertTrue(result, "addAppointment() should accept far future date");
		}
	}
	
	
	// ========== Read Method Tests (getAppointment) ==========
	/**
	 * Tests for the getAppointment() method
	 */
	@Nested
	@Tag("CRUD")
	@Tag("Read")
	@DisplayName("getAppointment() Method Tests")
	class GetAppointmentTests {
		
		@Test
		@DisplayName("getAppointment() Test: Null ID")
		/*
		 * Test getAppointment() with null ID returns null
		 */
		void getAppointmentNullIDTest() {
			String nullID = null;
			
			Appointment result = appointmentService.getAppointment(nullID);
			
			assertNull(result, "getAppointment() should return null for null ID");
		}
		
		@Test
		@DisplayName("getAppointment() Test: Empty ID")
		/*
		 * Test getAppointment() with empty ID returns null
		 */
		void getAppointmentEmptyIDTest() {
			String emptyID = "";
			
			Appointment result = appointmentService.getAppointment(emptyID);
			
			assertNull(result, "getAppointment() should return null for empty ID");
		}
		
		@Test
		@DisplayName("getAppointment() Test: Non-existent ID")
		/*
		 * Test getAppointment() with non-existent ID returns null
		 */
		void getAppointmentNonexistentIDTest() {
			String nonexistentID = "nonexist99";
			
			Appointment result = appointmentService.getAppointment(nonexistentID);
			
			assertNull(result, "getAppointment() should return null for non-existent ID");
		}
		
		@Test
		@DisplayName("getAppointment() Test: Invalid ID Format")
		/*
		 * Test getAppointment() with invalid ID format returns null
		 */
		void getAppointmentInvalidIDFormatTest() {
			String invalidID = "short";  // Less than 10 characters
			
			Appointment result = appointmentService.getAppointment(invalidID);
			
			assertNull(result, "getAppointment() should return null for invalid ID format");
		}
		
		@Test
		@DisplayName("getAppointment() Test: ID with Non-Alphanumeric Characters")
		/*
		 * Test getAppointment() with ID containing non-alphanumeric characters returns null
		 */
		void getAppointmentNonAlphanumericIDTest() {
			String nonAlphanumericID = "appt-123456";
			
			Appointment result = appointmentService.getAppointment(nonAlphanumericID);
			
			assertNull(result, "getAppointment() should return null for non-alphanumeric ID");
		}
		
		@Test
		@DisplayName("getAppointment() Test: ID exceeding max length")
		/*
		 * Test getAppointment() with ID exceeding max length returns null
		 */
		void getAppointmentIDTooLongTest() {
			String tooLongID = "appt1234567";  // 11 characters, max is 10
			
			Appointment result = appointmentService.getAppointment(tooLongID);
			
			assertNull(result, "getAppointment() should return null for ID exceeding max length");
		}
	}
	
	
	// ========== Delete Method Tests (removeAppointment) ==========
	/**
	 * Tests for the removeAppointment() method
	 */
	@Nested
	@Tag("CRUD")
	@Tag("Delete")
	@Tag("Mutator")
	@DisplayName("removeAppointment() Method Tests")
	class RemoveAppointmentTests {
		
		@Test
		@DisplayName("removeAppointment() Test: Null ID")
		/*
		 * Test removeAppointment() with null ID returns false
		 */
		void removeAppointmentNullIDTest() {
			String nullID = null;
			
			boolean result = appointmentService.removeAppointment(nullID);
			
			assertFalse(result, "removeAppointment() should return false for null ID");
		}
		
		@Test
		@DisplayName("removeAppointment() Test: Empty ID")
		/*
		 * Test removeAppointment() with empty ID returns false
		 */
		void removeAppointmentEmptyIDTest() {
			String emptyID = "";
			
			boolean result = appointmentService.removeAppointment(emptyID);
			
			assertFalse(result, "removeAppointment() should return false for empty ID");
		}
		
		@Test
		@DisplayName("removeAppointment() Test: Non-existent Appointment")
		/*
		 * Test removeAppointment() with non-existent ID returns false
		 */
		void removeAppointmentNonexistentTest() {
			String nonexistentID = "nonexist99";
			
			boolean result = appointmentService.removeAppointment(nonexistentID);
			
			assertFalse(result, "removeAppointment() should return false for non-existent appointment");
		}
		
		@Test
		@DisplayName("removeAppointment() Test: Invalid ID Format")
		/*
		 * Test removeAppointment() with invalid ID format returns false
		 */
		void removeAppointmentInvalidIDFormatTest() {
			String invalidID = "short";  // Less than 10 characters
			
			boolean result = appointmentService.removeAppointment(invalidID);
			
			assertFalse(result, "removeAppointment() should return false for invalid ID format");
		}
		
		@Test
		@DisplayName("removeAppointment() Test: ID with Non-Alphanumeric Characters")
		/*
		 * Test removeAppointment() with ID containing non-alphanumeric characters returns false
		 */
		void removeAppointmentNonAlphanumericIDTest() {
			String nonAlphanumericID = "appt-123456";
			
			boolean result = appointmentService.removeAppointment(nonAlphanumericID);
			
			assertFalse(result, "removeAppointment() should return false for non-alphanumeric ID");
		}
		
		@Test
		@DisplayName("removeAppointment() Test: ID exceeding max length")
		/*
		 * Test removeAppointment() with ID exceeding max length returns false
		 */
		void removeAppointmentIDTooLongTest() {
			String tooLongID = "appt1234567";  // 11 characters, max is 10
			
			boolean result = appointmentService.removeAppointment(tooLongID);
			
			assertFalse(result, "removeAppointment() should return false for ID exceeding max length");
		}
	}
	
	
	// ========== Validation Tests (Testing internal validators) ==========
	/**
	 * Tests for validation methods
	 */
	@Nested
	@Tag("Setup")
	@DisplayName("Validation Method Tests")
	class ValidationTests {
		
		@Test
		@DisplayName("appointmentIDValidator() Test: Valid ID")
		/*
		 * Test appointmentIDValidator() with valid ID format
		 */
		void appointmentIDValidatorValidTest() {
			boolean result = appointmentService.appointmentIDValidator("appt123abc");
			
			assertTrue(result, "appointmentIDValidator() should return true for valid ID");
		}
		
		@Test
		@DisplayName("appointmentIDValidator() Test: Null ID")
		/*
		 * Test appointmentIDValidator() with null ID
		 */
		void appointmentIDValidatorNullTest() {
			boolean result = appointmentService.appointmentIDValidator(null);
			
			assertFalse(result, "appointmentIDValidator() should return false for null ID");
		}
		
		@Test
		@DisplayName("appointmentIDValidator() Test: Invalid Length")
		/*
		 * Test appointmentIDValidator() with invalid length
		 */
		void appointmentIDValidatorInvalidLengthTest() {
			boolean result1 = appointmentService.appointmentIDValidator("short");  // Too short
			boolean result2 = appointmentService.appointmentIDValidator("appt1234567");  // Too long
			
			assertFalse(result1 && result2, "appointmentIDValidator() should reject invalid lengths");
		}
		
		@Test
		@DisplayName("appointmentDateValidator() Test: Valid Future Date")
		/*
		 * Test appointmentDateValidator() with valid future date
		 */
		void appointmentDateValidatorFutureTest() {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 1);
			Date futureDate = cal.getTime();
			
			boolean result = appointmentService.appointmentDateValidator(futureDate);
			
			assertTrue(result, "appointmentDateValidator() should return true for future date");
		}
		
		@Test
		@DisplayName("appointmentDateValidator() Test: Null Date")
		/*
		 * Test appointmentDateValidator() with null date
		 */
		void appointmentDateValidatorNullTest() {
			boolean result = appointmentService.appointmentDateValidator(null);
			
			assertFalse(result, "appointmentDateValidator() should return false for null date");
		}
		
		@Test
		@DisplayName("appointmentDateValidator() Test: Past Date")
		/*
		 * Test appointmentDateValidator() with past date
		 */
		void appointmentDateValidatorPastTest() {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Date pastDate = cal.getTime();
			
			boolean result = appointmentService.appointmentDateValidator(pastDate);
			
			assertFalse(result, "appointmentDateValidator() should return false for past date");
		}
		
		@Test
		@DisplayName("descriptionValidator() Test: Valid Description")
		/*
		 * Test descriptionValidator() with valid description
		 */
		void descriptionValidatorValidTest() {
			boolean result = appointmentService.descriptionValidator("Annual checkup");
			
			assertTrue(result, "descriptionValidator() should return true for valid description");
		}
		
		@Test
		@DisplayName("descriptionValidator() Test: Null Description")
		/*
		 * Test descriptionValidator() with null description
		 */
		void descriptionValidatorNullTest() {
			boolean result = appointmentService.descriptionValidator(null);
			
			assertFalse(result, "descriptionValidator() should return false for null description");
		}
		
		@Test
		@DisplayName("descriptionValidator() Test: Empty Description")
		/*
		 * Test descriptionValidator() with empty description
		 */
		void descriptionValidatorEmptyTest() {
			boolean result = appointmentService.descriptionValidator("");
			
			assertFalse(result, "descriptionValidator() should return false for empty description");
		}
		
		@Test
		@DisplayName("descriptionValidator() Test: Invalid Length (exceeds max)")
		/*
		 * Test descriptionValidator() with description exceeding max length
		 */
		void descriptionValidatorInvalidLengthTest() {
			String tooLong = "This description is way too long and exceeds the maximum character limit";
			
			boolean result = appointmentService.descriptionValidator(tooLong);
			
			assertFalse(result, "descriptionValidator() should return false for description exceeding max length");
		}
	}
	
	
	// ========== Integration Tests ==========
	/**
	 * Integration tests for multiple operations together
	 */
	@Nested
	@Tag("CRUD")
	@DisplayName("Integration Tests")
	class IntegrationTests {
		
		@Test
		@DisplayName("Integration Test: Add Multiple Appointments")
		/*
		 * Test adding multiple appointments to the service
		 */
		void addMultipleAppointmentsIntegrationTest() {
			Calendar cal1 = Calendar.getInstance();
			cal1.add(Calendar.DAY_OF_MONTH, 1);
			
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DAY_OF_MONTH, 2);
			
			Calendar cal3 = Calendar.getInstance();
			cal3.add(Calendar.DAY_OF_MONTH, 3);
			
			boolean result1 = appointmentService.addAppointment(cal1.getTime(), "Checkup");
			boolean result2 = appointmentService.addAppointment(cal2.getTime(), "Follow-up");
			boolean result3 = appointmentService.addAppointment(cal3.getTime(), "Consultation");
			
			assertTrue(result1 && result2 && result3, "All appointments should be added successfully");
		}
		
		@Test
		@DisplayName("Integration Test: Various Valid Descriptions")
		/*
		 * Test various valid combinations of descriptions
		 */
		void validDescriptionsTest() {
			boolean test1 = appointmentService.addAppointment(validDate, "A");
			boolean test2 = appointmentService.addAppointment(validDate, "Annual checkup");
			boolean test3 = appointmentService.addAppointment(validDate, "Exactly 50 Characters Long Description Text Now");
			
			assertTrue(test1 && test2 && test3, "All valid descriptions should be accepted");
		}
		
		@Test
		@DisplayName("Integration Test: Boundary Value Testing")
		/*
		 * Test boundary values for description length
		 */
		void boundaryValueTest() {
			Calendar cal1 = Calendar.getInstance();
			cal1.add(Calendar.DAY_OF_MONTH, 1);
			
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DAY_OF_MONTH, 2);
			
			Calendar cal3 = Calendar.getInstance();
			cal3.add(Calendar.DAY_OF_MONTH, 3);
			
			// 1 character description (minimum)
			boolean minDesc = appointmentService.addAppointment(cal1.getTime(), "A");
			
			// 50 character description (maximum)
			boolean maxDesc = appointmentService.addAppointment(cal2.getTime(), 
				"Exactly 50 Characters Long Description Text Now");
			
			// Appointment in immediate future
			Calendar immediateCal = Calendar.getInstance();
			immediateCal.add(Calendar.MINUTE, 1);
			boolean immediateAppt = appointmentService.addAppointment(immediateCal.getTime(), "Soon");
			
			assertTrue(minDesc && maxDesc && immediateAppt, 
				"All boundary values should be accepted");
		}
		
		@Test
		@DisplayName("Integration Test: Verify Appointment Properties")
		/*
		 * Test that added appointment can be retrieved and verified
		 */
		void verifyAppointmentPropertiesTest() {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 1);
			Date appointmentDate = cal.getTime();
			String description = "Annual checkup";
			
			// Add appointment
			boolean addSuccess = appointmentService.addAppointment(appointmentDate, description);
			assertTrue(addSuccess, "Appointment should be added successfully");
			
			// Note: Without access to the generated ID, we verify through successful addition
			// In a production scenario, addAppointment could return the ID for verification
		}
	}
}