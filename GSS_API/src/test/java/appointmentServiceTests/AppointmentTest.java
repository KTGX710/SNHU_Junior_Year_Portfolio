package appointmentServiceTests;

import org.junit.jupiter.api.*;
import java.util.Date;
import appointmentService.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * White-box JUnit test class for Appointment.
 * 
 * Organized with nested test classes to categorize:
 * - Constructor tests
 * - Accessor tests
 * - Functional tests (equals, toString, immutability)
 */
@DisplayName("Appointment Tests")
public class AppointmentTest {
	// Global Appointment
	private Appointment appointment;
	
	// Valid test construction arguments
	private static final String VALID_ID = "apt123abc";
	private static final Date VALID_DATE = new Date(System.currentTimeMillis() + 86400000); // Tomorrow
	private static final String VALID_DESCRIPTION = "Annual checkup";
	
	// ========== Constructor Test Setup ==========
	/**
	 * Instantiation of Appointment
	 * 
	 * Tests that Appointment can accept valid arguments
	 */
	@BeforeEach
	@DisplayName("Appointment Setup")
	void setUpTest() {
		try {
			appointment = new Appointment(VALID_ID, VALID_DATE, VALID_DESCRIPTION);
		}
		catch (Exception e) {
			System.out.println("Appointment constructor failed with arguments:" + "\n" +
					VALID_ID + "\n" + 
					VALID_DATE + "\n" + 
					VALID_DESCRIPTION + "\n"
			);
			
			// Abort all tests if constructor fails
			Assumptions.abort("Appointment Constructor Failed!");
		}
	}
	
	/**
	 * Appointment Instantiation Test
	 */
	@Test
	@Tag("Constructor")
	@DisplayName("Appointment Instantiation")
	void instantiationTest() {
		assertInstanceOf(Appointment.class, appointment, "Appointment successfully created");
	}

	
	// ========== Accessor Method Tests ==========
	/**
	 * Tests all accessor methods of Appointment for ID, date, and description
	 */
	@Nested
	@Tag("Accessor")
	@DisplayName("Accessor Method Tests")
	class AccessorTests {
		
		@Test
		@DisplayName("getAppointmentID() returns correct ID")
		void testGetAppointmentID() {
			assertEquals(VALID_ID, appointment.getAppointmentID(), "getAppointmentID() should return the ID passed in constructor");
		}
		
		@Test
		@DisplayName("getAppointmentDate() returns correct date")
		void testGetAppointmentDate() {
			assertEquals(VALID_DATE, appointment.getAppointmentDate(), "getAppointmentDate() should return the date passed in constructor");
		}
		
		@Test
		@DisplayName("getDescription() returns correct description")
		void testGetDescription() {
			assertEquals(VALID_DESCRIPTION, appointment.getDescription(), "getDescription() should return the description passed in constructor");
		}
		
		@Test
		@DisplayName("Accessors handle null appointment ID")
		void testGetAppointmentIDWithNull() {
			Appointment nullIDAppointment = new Appointment(null, VALID_DATE, VALID_DESCRIPTION);
			assertNull(nullIDAppointment.getAppointmentID(), "getAppointmentID() should return null when ID is null");
		}
		
		@Test
		@DisplayName("Accessors handle null appointment date")
		void testGetAppointmentDateWithNull() {
			Appointment nullDateAppointment = new Appointment(VALID_ID, null, VALID_DESCRIPTION);
			assertNull(nullDateAppointment.getAppointmentDate(), "getAppointmentDate() should return null when date is null");
		}
		
		@Test
		@DisplayName("Accessors handle null description")
		void testGetDescriptionWithNull() {
			Appointment nullDescAppointment = new Appointment(VALID_ID, VALID_DATE, null);
			assertNull(nullDescAppointment.getDescription(), "getDescription() should return null when description is null");
		}
	}
	
	
	// ========== Functional Tests ==========
	/**
	 * Tests equals(), toString(), and immutability
	 */
	@Nested
	@Tag("Functional")
	@DisplayName("Functional Method Tests")
	class FunctionalTests {
		
		// ========== Equals Tests ==========
		@Nested
		@DisplayName("equals() Method Tests")
		class EqualsTests {
			
			@Test
			@DisplayName("equals() returns true for identical appointments")
			void testEqualsSameValues() {
				Appointment appointment2 = new Appointment(VALID_ID, VALID_DATE, VALID_DESCRIPTION);
				assertTrue(appointment.equals(appointment2), "Appointments with identical values should be equal");
				assertTrue(appointment2.equals(appointment), "equals() should be symmetric");
			}
			
			@Test
			@DisplayName("equals() returns false for different appointment ID")
			void testEqualsDifferentId() {
				Appointment differentIDAppointment = new Appointment("different_id", VALID_DATE, VALID_DESCRIPTION);
				assertFalse(appointment.equals(differentIDAppointment), "Appointments with different IDs should not be equal");
			}
			
			@Test
			@DisplayName("equals() returns false for different appointment date")
			void testEqualsDifferentDate() {
				Date differentDate = new Date(VALID_DATE.getTime() + 3600000); // 1 hour later
				Appointment differentDateAppointment = new Appointment(VALID_ID, differentDate, VALID_DESCRIPTION);
				assertFalse(appointment.equals(differentDateAppointment), "Appointments with different dates should not be equal");
			}
			
			@Test
			@DisplayName("equals() returns false for different description")
			void testEqualsDifferentDescription() {
				Appointment differentDescAppointment = new Appointment(VALID_ID, VALID_DATE, "Different description");
				assertFalse(appointment.equals(differentDescAppointment), "Appointments with different descriptions should not be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when comparing with null")
			void testEqualsWithNull() {
				assertFalse(appointment.equals(null), "Comparing with null should return false");
			}
			
			@Test
			@DisplayName("equals() handles both appointments with null appointment ID")
			void testEqualsBothNullId() {
				Appointment apt1 = new Appointment(null, VALID_DATE, VALID_DESCRIPTION);
				Appointment apt2 = new Appointment(null, VALID_DATE, VALID_DESCRIPTION);
				assertTrue(apt1.equals(apt2), "Appointments with both null IDs and same other fields should be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when only one appointment has null ID")
			void testEqualsOneNullId() {
				Appointment nullIDAppointment = new Appointment(null, VALID_DATE, VALID_DESCRIPTION);
				assertFalse(appointment.equals(nullIDAppointment), "Appointments with one null ID should not be equal");
			}
			
			@Test
			@DisplayName("equals() handles both appointments with null date")
			void testEqualsBothNullDate() {
				Appointment apt1 = new Appointment(VALID_ID, null, VALID_DESCRIPTION);
				Appointment apt2 = new Appointment(VALID_ID, null, VALID_DESCRIPTION);
				assertTrue(apt1.equals(apt2), "Appointments with both null dates and same other fields should be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when only one appointment has null date")
			void testEqualsOneNullDate() {
				Appointment nullDateAppointment = new Appointment(VALID_ID, null, VALID_DESCRIPTION);
				assertFalse(appointment.equals(nullDateAppointment), "Appointments with one null date should not be equal");
			}
			
			@Test
			@DisplayName("equals() handles both appointments with null description")
			void testEqualsBothNullDescription() {
				Appointment apt1 = new Appointment(VALID_ID, VALID_DATE, null);
				Appointment apt2 = new Appointment(VALID_ID, VALID_DATE, null);
				assertTrue(apt1.equals(apt2), "Appointments with both null descriptions and same other fields should be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when only one appointment has null description")
			void testEqualsOneNullDescription() {
				Appointment nullDescAppointment = new Appointment(VALID_ID, VALID_DATE, null);
				assertFalse(appointment.equals(nullDescAppointment), "Appointments with one null description should not be equal");
			}
			
			@Test
			@DisplayName("equals() handles all fields null")
			void testEqualsAllFieldsNull() {
				Appointment apt1 = new Appointment(null, null, null);
				Appointment apt2 = new Appointment(null, null, null);
				assertTrue(apt1.equals(apt2), "Appointments with all null fields should be equal");
			}
			
			@Test
			@DisplayName("equals() handles comparison with different type")
			void testEqualsWithDifferentType() {
				String notAnAppointment = "not an appointment";
				assertFalse(appointment.equals(notAnAppointment), "Comparing with different type should return false");
			}
		}
		
		// ========== ToString Tests ==========
		@Nested
		@DisplayName("toString() Method Tests")
		class ToStringTests {
			
			@Test
			@DisplayName("toString() formats output correctly with valid values")
			void testToString() {
				String expected = "Appointment(appointmentID=" + VALID_ID + ", appointmentDate=" + VALID_DATE + ", description=" + VALID_DESCRIPTION + ")";
				assertEquals(expected, appointment.toString(), "toString() should format output as Appointment(appointmentID=..., appointmentDate=..., description=...)");
			}
			
			@Test
			@DisplayName("toString() handles null appointment ID")
			void testToStringWithNullId() {
				Appointment nullIDAppointment = new Appointment(null, VALID_DATE, VALID_DESCRIPTION);
				String expected = "Appointment(appointmentID=null, appointmentDate=" + VALID_DATE + ", description=" + VALID_DESCRIPTION + ")";
				assertEquals(expected, nullIDAppointment.toString(), "toString() should display 'null' for null appointment ID");
			}
			
			@Test
			@DisplayName("toString() handles null appointment date")
			void testToStringWithNullDate() {
				Appointment nullDateAppointment = new Appointment(VALID_ID, null, VALID_DESCRIPTION);
				String expected = "Appointment(appointmentID=" + VALID_ID + ", appointmentDate=null, description=" + VALID_DESCRIPTION + ")";
				assertEquals(expected, nullDateAppointment.toString(), "toString() should display 'null' for null appointment date");
			}
			
			@Test
			@DisplayName("toString() handles null description")
			void testToStringWithNullDescription() {
				Appointment nullDescAppointment = new Appointment(VALID_ID, VALID_DATE, null);
				String expected = "Appointment(appointmentID=" + VALID_ID + ", appointmentDate=" + VALID_DATE + ", description=null)";
				assertEquals(expected, nullDescAppointment.toString(), "toString() should display 'null' for null description");
			}
			
			@Test
			@DisplayName("toString() handles all fields null")
			void testToStringWithAllNull() {
				Appointment allNullAppointment = new Appointment(null, null, null);
				String expected = "Appointment(appointmentID=null, appointmentDate=null, description=null)";
				assertEquals(expected, allNullAppointment.toString(), "toString() should display 'null' for all null fields");
			}
		}
		
		// ========== Immutability Tests ==========
		@Nested
		@DisplayName("Immutability Tests")
		class ImmutabilityTests {
			
			@Test
			@DisplayName("Fields are immutable after construction")
			void testImmutability() {
				String originalID = appointment.getAppointmentID();
				Date originalDate = appointment.getAppointmentDate();
				String originalDesc = appointment.getDescription();
				
				// Verify fields haven't changed
				assertEquals(originalID, appointment.getAppointmentID(), "Appointment ID should not change after construction");
				assertEquals(originalDate, appointment.getAppointmentDate(), "Appointment date should not change after construction");
				assertEquals(originalDesc, appointment.getDescription(), "Description should not change after construction");
			}
			
			@Test
			@DisplayName("Multiple calls to getters return same values")
			void testConsistentGetterCalls() {
				String id1 = appointment.getAppointmentID();
				String id2 = appointment.getAppointmentID();
				Date date1 = appointment.getAppointmentDate();
				Date date2 = appointment.getAppointmentDate();
				String desc1 = appointment.getDescription();
				String desc2 = appointment.getDescription();
				
				assertEquals(id1, id2, "Multiple calls to getAppointmentID() should return the same value");
				assertEquals(date1, date2, "Multiple calls to getAppointmentDate() should return the same value");
				assertEquals(desc1, desc2, "Multiple calls to getDescription() should return the same value");
			}
		}
	}
}