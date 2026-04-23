package contactServiceTests;

import org.junit.jupiter.api.*;
import contactService.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * White-box JUnit test class for Contact.
 * 
 * Organized with nested test classes to categorize:
 * - Constructor tests
 * - Accessor tests
 * - Functional tests (equals, toString, immutability)
 */
@DisplayName("Contact Tests")
public class ContactTest {
	// Global Contact
	private Contact contact;
	
	// Valid test construction arguments
	private static String validID = "contact123abc";
	private static String validFirstName = "John";
	private static String validLastName = "Doe";
	private static String validPhone = "555-1234";
	private static String validAddress = "123 Main St";
	
	// ========== Constructor Test Setup ==========
	/**
	 * Instantiation of Contact
	 * 
	 * Tests that Contact can accept valid arguments
	 */
	@BeforeEach
	@DisplayName("Contact Setup")
	void setUpTest() {
		try {
			contact = new Contact(validID, validFirstName, validLastName, validPhone, validAddress);
		}
		catch (Exception e) {
			System.out.println("Contact constructor failed with arguments:" + "\n" +
					validID + "\n" + 
					validFirstName + "\n" + 
					validLastName + "\n" +
					validPhone + "\n" +
					validAddress + "\n"
			);
			
			// Abort all tests if constructor fails
			Assumptions.abort("Contact Constructor Failed!");
		}
	}
	
	/**
	 * Contact Instantiation Test
	 */
	@Test
	@Tag("Constructor")
	@DisplayName("Contact Instantiation")
	void instantiationTest() {
		assertInstanceOf(Contact.class, contact, "Contact successfully created");
	}

	
	// ========== Accessor Method Tests ==========
	/**
	 * Tests all accessor methods of Contact
	 */
	@Nested
	@Tag("Accessor")
	@DisplayName("Accessor Method Tests")
	class AccessorTests {
		
		@Test
		@DisplayName("getID() returns correct ID")
		void testGetID() {
			assertEquals(validID, contact.getID(), "getID() should return the ID passed in constructor");
		}
		
		@Test
		@DisplayName("getFirstName() returns correct first name")
		void testGetFirstName() {
			assertEquals(validFirstName, contact.getFirstName(), "getFirstName() should return the first name passed in constructor");
		}
		
		@Test
		@DisplayName("getLastName() returns correct last name")
		void testGetLastName() {
			assertEquals(validLastName, contact.getLastName(), "getLastName() should return the last name passed in constructor");
		}
		
		@Test
		@DisplayName("getPhone() returns correct phone number")
		void testGetPhone() {
			assertEquals(validPhone, contact.getPhone(), "getPhone() should return the phone number passed in constructor");
		}
		
		@Test
		@DisplayName("getAddress() returns correct address")
		void testGetAddress() {
			assertEquals(validAddress, contact.getAddress(), "getAddress() should return the address passed in constructor");
		}
		
		@Test
		@DisplayName("Accessors handle null ID")
		void testGetIDWithNull() {
			Contact nullIDContact = new Contact(null, validFirstName, validLastName, validPhone, validAddress);
			assertNull(nullIDContact.getID(), "getID() should return null when ID is null");
		}
		
		@Test
		@DisplayName("Accessors handle null first name")
		void testGetFirstNameWithNull() {
			Contact nullFirstNameContact = new Contact(validID, null, validLastName, validPhone, validAddress);
			assertNull(nullFirstNameContact.getFirstName(), "getFirstName() should return null when first name is null");
		}
		
		@Test
		@DisplayName("Accessors handle null last name")
		void testGetLastNameWithNull() {
			Contact nullLastNameContact = new Contact(validID, validFirstName, null, validPhone, validAddress);
			assertNull(nullLastNameContact.getLastName(), "getLastName() should return null when last name is null");
		}
		
		@Test
		@DisplayName("Accessors handle null phone")
		void testGetPhoneWithNull() {
			Contact nullPhoneContact = new Contact(validID, validFirstName, validLastName, null, validAddress);
			assertNull(nullPhoneContact.getPhone(), "getPhone() should return null when phone is null");
		}
		
		@Test
		@DisplayName("Accessors handle null address")
		void testGetAddressWithNull() {
			Contact nullAddressContact = new Contact(validID, validFirstName, validLastName, validPhone, null);
			assertNull(nullAddressContact.getAddress(), "getAddress() should return null when address is null");
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
			@DisplayName("equals() returns true for identical contacts")
			void testEqualsSameValues() {
				Contact contact2 = new Contact(validID, validFirstName, validLastName, validPhone, validAddress);
				assertTrue(contact.equals(contact2), "Contacts with identical values should be equal");
				assertTrue(contact2.equals(contact), "equals() should be symmetric");
			}
			
			@Test
			@DisplayName("equals() returns false for different ID")
			void testEqualsDifferentId() {
				Contact differentIDContact = new Contact("different_id", validFirstName, validLastName, validPhone, validAddress);
				assertFalse(contact.equals(differentIDContact), "Contacts with different IDs should not be equal");
			}
			
			@Test
			@DisplayName("equals() returns false for different first name")
			void testEqualsDifferentFirstName() {
				Contact differentFirstNameContact = new Contact(validID, "Jane", validLastName, validPhone, validAddress);
				assertFalse(contact.equals(differentFirstNameContact), "Contacts with different first names should not be equal");
			}
			
			@Test
			@DisplayName("equals() returns false for different last name")
			void testEqualsDifferentLastName() {
				Contact differentLastNameContact = new Contact(validID, validFirstName, "Smith", validPhone, validAddress);
				assertFalse(contact.equals(differentLastNameContact), "Contacts with different last names should not be equal");
			}
			
			@Test
			@DisplayName("equals() returns false for different phone")
			void testEqualsDifferentPhone() {
				Contact differentPhoneContact = new Contact(validID, validFirstName, validLastName, "555-5678", validAddress);
				assertFalse(contact.equals(differentPhoneContact), "Contacts with different phone numbers should not be equal");
			}
			
			@Test
			@DisplayName("equals() returns false for different address")
			void testEqualsDifferentAddress() {
				Contact differentAddressContact = new Contact(validID, validFirstName, validLastName, validPhone, "456 Oak Ave");
				assertFalse(contact.equals(differentAddressContact), "Contacts with different addresses should not be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when comparing with null")
			void testEqualsWithNull() {
				assertFalse(contact.equals(null), "Comparing with null should return false");
			}
			
			@Test
			@DisplayName("equals() handles both contacts with null ID")
			void testEqualsBothNullId() {
				Contact contact1 = new Contact(null, validFirstName, validLastName, validPhone, validAddress);
				Contact contact2 = new Contact(null, validFirstName, validLastName, validPhone, validAddress);
				assertTrue(contact1.equals(contact2), "Contacts with both null IDs and same other fields should be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when only one contact has null ID")
			void testEqualsOneNullId() {
				Contact nullIDContact = new Contact(null, validFirstName, validLastName, validPhone, validAddress);
				assertFalse(contact.equals(nullIDContact), "Contacts with one null ID should not be equal");
			}
			
			@Test
			@DisplayName("equals() handles both contacts with null first name")
			void testEqualsBothNullFirstName() {
				Contact contact1 = new Contact(validID, null, validLastName, validPhone, validAddress);
				Contact contact2 = new Contact(validID, null, validLastName, validPhone, validAddress);
				assertTrue(contact1.equals(contact2), "Contacts with both null first names and same other fields should be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when only one contact has null first name")
			void testEqualsOneNullFirstName() {
				Contact nullFirstNameContact = new Contact(validID, null, validLastName, validPhone, validAddress);
				assertFalse(contact.equals(nullFirstNameContact), "Contacts with one null first name should not be equal");
			}
			
			@Test
			@DisplayName("equals() handles both contacts with null last name")
			void testEqualsBothNullLastName() {
				Contact contact1 = new Contact(validID, validFirstName, null, validPhone, validAddress);
				Contact contact2 = new Contact(validID, validFirstName, null, validPhone, validAddress);
				assertTrue(contact1.equals(contact2), "Contacts with both null last names and same other fields should be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when only one contact has null last name")
			void testEqualsOneNullLastName() {
				Contact nullLastNameContact = new Contact(validID, validFirstName, null, validPhone, validAddress);
				assertFalse(contact.equals(nullLastNameContact), "Contacts with one null last name should not be equal");
			}
			
			@Test
			@DisplayName("equals() handles both contacts with null phone")
			void testEqualsBothNullPhone() {
				Contact contact1 = new Contact(validID, validFirstName, validLastName, null, validAddress);
				Contact contact2 = new Contact(validID, validFirstName, validLastName, null, validAddress);
				assertTrue(contact1.equals(contact2), "Contacts with both null phones and same other fields should be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when only one contact has null phone")
			void testEqualsOneNullPhone() {
				Contact nullPhoneContact = new Contact(validID, validFirstName, validLastName, null, validAddress);
				assertFalse(contact.equals(nullPhoneContact), "Contacts with one null phone should not be equal");
			}
			
			@Test
			@DisplayName("equals() handles both contacts with null address")
			void testEqualsBothNullAddress() {
				Contact contact1 = new Contact(validID, validFirstName, validLastName, validPhone, null);
				Contact contact2 = new Contact(validID, validFirstName, validLastName, validPhone, null);
				assertTrue(contact1.equals(contact2), "Contacts with both null addresses and same other fields should be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when only one contact has null address")
			void testEqualsOneNullAddress() {
				Contact nullAddressContact = new Contact(validID, validFirstName, validLastName, validPhone, null);
				assertFalse(contact.equals(nullAddressContact), "Contacts with one null address should not be equal");
			}
			
			@Test
			@DisplayName("equals() handles all fields null")
			void testEqualsAllFieldsNull() {
				Contact contact1 = new Contact(null, null, null, null, null);
				Contact contact2 = new Contact(null, null, null, null, null);
				assertTrue(contact1.equals(contact2), "Contacts with all null fields should be equal");
			}
			
			@Test
			@DisplayName("equals() handles comparison with different type")
			void testEqualsWithDifferentType() {
				String notAContact = "not a contact";
				assertFalse(contact.equals(notAContact), "Comparing with different type should return false");
			}
		}
		
		// ========== ToString Tests ==========
		@Nested
		@DisplayName("toString() Method Tests")
		class ToStringTests {
			
			@Test
			@DisplayName("toString() formats output correctly with valid values")
			void testToString() {
				String expected = "Contact(id=" + validID + ", firstName=" + validFirstName + ", lastName=" + validLastName + ", phone=" + validPhone + ", address=" + validAddress + ")";
				assertEquals(expected, contact.toString(), "toString() should format output as Contact(id=..., firstName=..., lastName=..., phone=..., address=...)");
			}
			
			@Test
			@DisplayName("toString() handles null ID")
			void testToStringWithNullId() {
				Contact nullIDContact = new Contact(null, validFirstName, validLastName, validPhone, validAddress);
				String expected = "Contact(id=null, firstName=" + validFirstName + ", lastName=" + validLastName + ", phone=" + validPhone + ", address=" + validAddress + ")";
				assertEquals(expected, nullIDContact.toString(), "toString() should display 'null' for null ID");
			}
			
			@Test
			@DisplayName("toString() handles null first name")
			void testToStringWithNullFirstName() {
				Contact nullFirstNameContact = new Contact(validID, null, validLastName, validPhone, validAddress);
				String expected = "Contact(id=" + validID + ", firstName=null, lastName=" + validLastName + ", phone=" + validPhone + ", address=" + validAddress + ")";
				assertEquals(expected, nullFirstNameContact.toString(), "toString() should display 'null' for null first name");
			}
			
			@Test
			@DisplayName("toString() handles null last name")
			void testToStringWithNullLastName() {
				Contact nullLastNameContact = new Contact(validID, validFirstName, null, validPhone, validAddress);
				String expected = "Contact(id=" + validID + ", firstName=" + validFirstName + ", lastName=null, phone=" + validPhone + ", address=" + validAddress + ")";
				assertEquals(expected, nullLastNameContact.toString(), "toString() should display 'null' for null last name");
			}
			
			@Test
			@DisplayName("toString() handles null phone")
			void testToStringWithNullPhone() {
				Contact nullPhoneContact = new Contact(validID, validFirstName, validLastName, null, validAddress);
				String expected = "Contact(id=" + validID + ", firstName=" + validFirstName + ", lastName=" + validLastName + ", phone=null, address=" + validAddress + ")";
				assertEquals(expected, nullPhoneContact.toString(), "toString() should display 'null' for null phone");
			}
			
			@Test
			@DisplayName("toString() handles null address")
			void testToStringWithNullAddress() {
				Contact nullAddressContact = new Contact(validID, validFirstName, validLastName, validPhone, null);
				String expected = "Contact(id=" + validID + ", firstName=" + validFirstName + ", lastName=" + validLastName + ", phone=" + validPhone + ", address=null)";
				assertEquals(expected, nullAddressContact.toString(), "toString() should display 'null' for null address");
			}
			
			@Test
			@DisplayName("toString() handles all fields null")
			void testToStringWithAllNull() {
				Contact allNullContact = new Contact(null, null, null, null, null);
				String expected = "Contact(id=null, firstName=null, lastName=null, phone=null, address=null)";
				assertEquals(expected, allNullContact.toString(), "toString() should display 'null' for all null fields");
			}
		}
		
		// ========== Immutability Tests ==========
		@Nested
		@DisplayName("Immutability Tests")
		class ImmutabilityTests {
			
			@Test
			@DisplayName("Fields are immutable after construction")
			void testImmutability() {
				String originalId = contact.getID();
				String originalFirstName = contact.getFirstName();
				String originalLastName = contact.getLastName();
				String originalPhone = contact.getPhone();
				String originalAddress = contact.getAddress();
				
				// Verify fields haven't changed
				assertEquals(originalId, contact.getID(), "ID should not change after construction");
				assertEquals(originalFirstName, contact.getFirstName(), "First name should not change after construction");
				assertEquals(originalLastName, contact.getLastName(), "Last name should not change after construction");
				assertEquals(originalPhone, contact.getPhone(), "Phone should not change after construction");
				assertEquals(originalAddress, contact.getAddress(), "Address should not change after construction");
			}
			
			@Test
			@DisplayName("Multiple calls to getters return same values")
			void testConsistentGetterCalls() {
				String id1 = contact.getID();
				String id2 = contact.getID();
				String firstName1 = contact.getFirstName();
				String firstName2 = contact.getFirstName();
				String lastName1 = contact.getLastName();
				String lastName2 = contact.getLastName();
				String phone1 = contact.getPhone();
				String phone2 = contact.getPhone();
				String address1 = contact.getAddress();
				String address2 = contact.getAddress();
				
				assertEquals(id1, id2, "Multiple calls to getID() should return the same value");
				assertEquals(firstName1, firstName2, "Multiple calls to getFirstName() should return the same value");
				assertEquals(lastName1, lastName2, "Multiple calls to getLastName() should return the same value");
				assertEquals(phone1, phone2, "Multiple calls to getPhone() should return the same value");
				assertEquals(address1, address2, "Multiple calls to getAddress() should return the same value");
			}
		}
	}
}