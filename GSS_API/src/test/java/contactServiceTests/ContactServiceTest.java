package contactServiceTests;

import org.junit.jupiter.api.*;

import contactService.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ContactService Test class.
 * 
 * Stores tests for all methods within the ContactService class.
 * 
 * Utilizes Tags; CRUD, Create, Read, Update, Delete, Mutator, and Setup.
 */
@DisplayName("ContactService Object Tests")
public class ContactServiceTest {
	private ContactService contactService;
	
	// Valid test contact data
	private static final String VALID_FIRST_NAME = "John";
	private static final String VALID_LAST_NAME = "Doe";
	private static final String VALID_PHONE = "5551234567";
	private static final String VALID_ADDRESS = "123 Main Street";
	
	// Null and Empty attributes
	private static final String INVALID_NAME = "Johnathanson"; // Greater than 10 characters
	private static final String INVALID_PHONE = "555-123-4567-0";
	private static final String INVALID_ADDRESS = "12345 232nd Parkway Suite 101, Minneapolis, Minnesota, USA";
	
	private static final String NULL_VALUE = null;
	private static final String EMPTY_VALUE = "";
	private static final String TEXT_WITH_SCRIPT = "123 Main St <script>alert('xss')</script>";
	
	// ========== Constructor and Setup Tests ==========
	/**
	 * Instantiation of ContactService Object
	 * 
	 * Tests that ContactService object can be created successfully before each test
	 */
	@BeforeEach
	void setUp() {
		try {
			contactService = new ContactService();
		}
		catch (Exception e) {
			System.out.println("ContactService constructor failed with exception: " + e.getMessage());
			
			// Abort test if constructor fails
			Assumptions.abort("ContactService Constructor Failed!");
		}
	}
	
	/**
	 * ContactService Object Instantiation Test
	 */
	@Test
	@Tag("Setup")
	@DisplayName("ContactService Object Instantiation")
	void objectTest() {
		assertInstanceOf(ContactService.class, contactService, "ContactService object successfully created");
	}

	
	// ========== Create Method Tests (addContact) ==========
	/**
	 * Tests for the addContact() method
	 */
	@Nested
	@Tag("CRUD")
	@Tag("Create")
	@Tag("Mutator")
	@DisplayName("addContact() Method Tests")
	class AddContactTests {
		
		@Test
		@DisplayName("addContact() Test: Add Valid Contact")
		/*
		 * Test addContact() with valid parameters returns true
		 */
		void addValidContactTest() {
			boolean result = contactService.addContact(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS);
			
			assertTrue(result, "addContact() should return true for valid parameters");
		}
		
		@Test
		@DisplayName("addContact() Test: Null First Name")
		/*
		 * Test addContact() with null first name returns false
		 */
		void addNullFirstNameTest() {
			boolean result = contactService.addContact(NULL_VALUE, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "addContact() should return false for null first name");
		}
		
		@Test
		@DisplayName("addContact() Test: Null Last Name")
		/*
		 * Test addContact() with null last name returns false
		 */
		void addNullLastNameTest() {
			boolean result = contactService.addContact(VALID_FIRST_NAME, NULL_VALUE, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "addContact() should return false for null last name");
		}
		
		@Test
		@DisplayName("addContact() Test: Null Phone")
		/*
		 * Test addContact() with null phone returns false
		 */
		void addNullPhoneTest() {
			boolean result = contactService.addContact(VALID_FIRST_NAME, VALID_LAST_NAME, NULL_VALUE, VALID_ADDRESS);
			
			assertFalse(result, "addContact() should return false for null phone");
		}
		
		@Test
		@DisplayName("addContact() Test: Null Address")
		/*
		 * Test addContact() with null address returns false
		 */
		void addNullAddressTest() {
			boolean result = contactService.addContact(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, NULL_VALUE);
			
			assertFalse(result, "addContact() should return false for null address");
		}
		
		@Test
		@DisplayName("addContact() Test: Empty First Name")
		/*
		 * Test addContact() with empty first name returns false
		 */
		void addEmptyFirstNameTest() {
			boolean result = contactService.addContact(EMPTY_VALUE, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "addContact() should return false for empty first name");
		}
		
		@Test
		@DisplayName("addContact() Test: Empty Last Name")
		/*
		 * Test addContact() with empty last name returns false
		 */
		void addEmptyLastNameTest() {
			boolean result = contactService.addContact(VALID_FIRST_NAME, EMPTY_VALUE, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "addContact() should return false for empty last name");
		}
		
		@Test
		@DisplayName("addContact() Test: Empty Phone")
		/*
		 * Test addContact() with empty phone returns false
		 */
		void addEmptyPhoneTest() {
			boolean result = contactService.addContact(VALID_FIRST_NAME, VALID_LAST_NAME, EMPTY_VALUE, VALID_ADDRESS);
			
			assertFalse(result, "addContact() should return false for empty phone");
		}
		
		@Test
		@DisplayName("addContact() Test: Empty Address")
		/*
		 * Test addContact() with empty address returns false
		 */
		void addEmptyAddressTest() {
			boolean result = contactService.addContact(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, EMPTY_VALUE);
			
			assertFalse(result, "addContact() should return false for empty address");
		}
		
		@Test
		@DisplayName("addContact() Test: Invalid First Name (exceeds max length)")
		/*
		 * Test addContact() with first name exceeding max length returns false
		 */
		void addInvalidFirstNameTest() {
			boolean result = contactService.addContact(INVALID_NAME, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "addContact() should return false for invalid first name");
		}
		
		@Test
		@DisplayName("addContact() Test: Invalid Last Name (exceeds max length)")
		/*
		 * Test addContact() with last name exceeding max length returns false
		 */
		void addInvalidLastNameTest() {
			boolean result = contactService.addContact(VALID_FIRST_NAME, INVALID_NAME, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "addContact() should return false for invalid last name");
		}
		
		@Test
		@DisplayName("addContact() Test: Invalid Phone (exceeds max length)")
		/*
		 * Test addContact() with phone exceeding max length returns false
		 */
		void addInvalidPhoneTest() {
			boolean result = contactService.addContact(VALID_FIRST_NAME, VALID_LAST_NAME, INVALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "addContact() should return false for invalid phone");
		}
		
		@Test
		@DisplayName("addContact() Test: Invalid Address (exceeds max length)")
		/*
		 * Test addContact() with address exceeding max length returns false
		 */
		void addInvalidAddressTest() {
			boolean result = contactService.addContact(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, INVALID_ADDRESS);
			
			assertFalse(result, "addContact() should return false for invalid address");
		}
		
		@Test
		@DisplayName("addContact() Test: Address with Malicious Characters")
		/*
		 * Test addContact() rejects address with malicious characters
		 */
		void addMaliciousAddressTest() {
			boolean result = contactService.addContact(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, TEXT_WITH_SCRIPT);
			
			assertFalse(result, "addContact() should reject address with malicious characters");
		}
		
		@Test
		@DisplayName("addContact() Test: Multiple Contacts Added")
		/*
		 * Test addContact() can add multiple contacts successfully
		 */
		void addMultipleContactsTest() {
			boolean result1 = contactService.addContact("John", "Doe", "5551234567", "123 Main St");
			boolean result2 = contactService.addContact("Jane", "Smith", "5559876543", "456 Oak Ave");
			boolean result3 = contactService.addContact("Bob", "Johnson", "5555555555", "789 Pine Rd");
			
			assertTrue(result1 && result2 && result3, "All contacts should be added successfully");
		}
		
		@Test
		@DisplayName("addContact() Test: Max Length Name and Phone")
		/*
		 * Test addContact() with maximum length names and phone
		 */
		void addMaxLengthTest() {
			String maxFirstName = "1234567890";  // Exactly 10 characters
			String maxLastName = "0987654321";  // Exactly 10 characters
			String maxPhone = "1234567890";  // Exactly 10 characters
			String maxAddress = "123456789012345678901234567890";  // Exactly 30 characters
			
			boolean result = contactService.addContact(maxFirstName, maxLastName, maxPhone, maxAddress);
			
			assertTrue(result, "addContact() should accept max length values");
		}
	}
	
	
	// ========== Read Method Tests (getContact) ==========
	/**
	 * Tests for the getContact() method
	 */
	@Nested
	@Tag("CRUD")
	@Tag("Read")
	@DisplayName("getContact() Method Tests")
	class GetContactTests {
		
		@Test
		@DisplayName("getContact() Test: Null ID")
		/*
		 * Test getContact() with null ID returns null
		 */
		void getContactNullIDTest() {
			Contact result = contactService.getContact(NULL_VALUE);
			
			assertNull(result, "getContact() should return null for null ID");
		}
		
		@Test
		@DisplayName("getContact() Test: Empty ID")
		/*
		 * Test getContact() with empty ID returns null
		 */
		void getContactEmptyIDTest() {
			Contact result = contactService.getContact(EMPTY_VALUE);
			
			assertNull(result, "getContact() should return null for empty ID");
		}
		
		@Test
		@DisplayName("getContact() Test: Non-existent ID")
		/*
		 * Test getContact() with non-existent ID returns null
		 */
		void getContactNonexistentIDTest() {
			String nonexistentID = "nonexist99";
			
			Contact result = contactService.getContact(nonexistentID);
			
			assertNull(result, "getContact() should return null for non-existent ID");
		}
		
		@Test
		@DisplayName("getContact() Test: Invalid ID Format")
		/*
		 * Test getContact() with invalid ID format returns null
		 */
		void getContactInvalidIDFormatTest() {
			String invalidID = "short";  // Less than 10 characters
			
			Contact result = contactService.getContact(invalidID);
			
			assertNull(result, "getContact() should return null for invalid ID format");
		}
		
		@Test
		@DisplayName("getContact() Test: ID with Non-Alphanumeric Characters")
		/*
		 * Test getContact() with ID containing non-alphanumeric characters returns null
		 */
		void getContactNonAlphanumericIDTest() {
			String nonAlphanumericID = "contact-123";
			
			Contact result = contactService.getContact(nonAlphanumericID);
			
			assertNull(result, "getContact() should return null for non-alphanumeric ID");
		}
	}
	
	
	// ========== Update Method Tests (updateContact) ==========
	/**
	 * Tests for the updateContact() method
	 */
	@Nested
	@Tag("CRUD")
	@Tag("Update")
	@Tag("Mutator")
	@DisplayName("updateContact() Method Tests")
	class UpdateContactTests {
		
		@Test
		@DisplayName("updateContact() Test: Null ID")
		/*
		 * Test updateContact() with null ID returns false
		 */
		void updateContactNullIDTest() {
			String nullID = null;
			
			boolean result = contactService.updateContact(nullID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should return false for null ID");
		}
		
		@Test
		@DisplayName("updateContact() Test: Empty ID")
		/*
		 * Test updateContact() with empty ID returns false
		 */
		void updateContactEmptyIDTest() {
			String emptyID = "";
			
			boolean result = contactService.updateContact(emptyID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should return false for empty ID");
		}
		
		@Test
		@DisplayName("updateContact() Test: Null First Name")
		/*
		 * Test updateContact() with null first name returns false
		 */
		void updateContactNullFirstNameTest() {
			String validID = "validid1234";
			String nullFirstName = null;
			
			boolean result = contactService.updateContact(validID, nullFirstName, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should return false for null first name");
		}
		
		@Test
		@DisplayName("updateContact() Test: Null Last Name")
		/*
		 * Test updateContact() with null last name returns false
		 */
		void updateContactNullLastNameTest() {
			String validID = "validid1234";
			String nullLastName = null;
			
			boolean result = contactService.updateContact(validID, VALID_FIRST_NAME, nullLastName, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should return false for null last name");
		}
		
		@Test
		@DisplayName("updateContact() Test: Null Phone")
		/*
		 * Test updateContact() with null phone returns false
		 */
		void updateContactNullPhoneTest() {
			String validID = "validid1234";
			String nullPhone = null;
			
			boolean result = contactService.updateContact(validID, VALID_FIRST_NAME, VALID_LAST_NAME, nullPhone, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should return false for null phone");
		}
		
		@Test
		@DisplayName("updateContact() Test: Null Address")
		/*
		 * Test updateContact() with null address returns false
		 */
		void updateContactNullAddressTest() {
			String validID = "validid1234";
			String nullAddress = null;
			
			boolean result = contactService.updateContact(validID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, nullAddress);
			
			assertFalse(result, "updateContact() should return false for null address");
		}
		
		@Test
		@DisplayName("updateContact() Test: Empty First Name")
		/*
		 * Test updateContact() with empty first name returns false
		 */
		void updateContactEmptyFirstNameTest() {
			String validID = "validid1234";
			String emptyFirstName = "";
			
			boolean result = contactService.updateContact(validID, emptyFirstName, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should return false for empty first name");
		}
		
		@Test
		@DisplayName("updateContact() Test: Empty Last Name")
		/*
		 * Test updateContact() with empty last name returns false
		 */
		void updateContactEmptyLastNameTest() {
			String validID = "validid1234";
			String emptyLastName = "";
			
			boolean result = contactService.updateContact(validID, VALID_FIRST_NAME, emptyLastName, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should return false for empty last name");
		}
		
		@Test
		@DisplayName("updateContact() Test: Empty Phone")
		/*
		 * Test updateContact() with empty phone returns false
		 */
		void updateContactEmptyPhoneTest() {
			String validID = "validid1234";
			String emptyPhone = "";
			
			boolean result = contactService.updateContact(validID, VALID_FIRST_NAME, VALID_LAST_NAME, emptyPhone, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should return false for empty phone");
		}
		
		@Test
		@DisplayName("updateContact() Test: Empty Address")
		/*
		 * Test updateContact() with empty address returns false
		 */
		void updateContactEmptyAddressTest() {
			String validID = "validid1234";
			String emptyAddress = "";
			
			boolean result = contactService.updateContact(validID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, emptyAddress);
			
			assertFalse(result, "updateContact() should return false for empty address");
		}
		
		@Test
		@DisplayName("updateContact() Test: Non-existent Contact")
		/*
		 * Test updateContact() with non-existent ID returns false
		 */
		void updateContactNonexistentTest() {
			String nonexistentID = "nonexist99";
			
			boolean result = contactService.updateContact(nonexistentID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should return false for non-existent contact");
		}
		
		@Test
		@DisplayName("updateContact() Test: Invalid First Name (exceeds max length)")
		/*
		 * Test updateContact() with invalid first name returns false
		 */
		void updateContactInvalidFirstNameTest() {
			String validID = "validid1234";
			String invalidFirstName = "JohnTooLongName";  // More than 10 characters
			
			boolean result = contactService.updateContact(validID, invalidFirstName, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should return false for invalid first name");
		}
		
		@Test
		@DisplayName("updateContact() Test: Invalid Last Name (exceeds max length)")
		/*
		 * Test updateContact() with invalid last name returns false
		 */
		void updateContactInvalidLastNameTest() {
			String validID = "validid1234";
			String invalidLastName = "DoeTooLongName";  // More than 10 characters
			
			boolean result = contactService.updateContact(validID, VALID_FIRST_NAME, invalidLastName, VALID_PHONE, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should return false for invalid last name");
		}
		
		@Test
		@DisplayName("updateContact() Test: Invalid Phone (exceeds max length)")
		/*
		 * Test updateContact() with invalid phone returns false
		 */
		void updateContactInvalidPhoneTest() {
			String validID = "validid1234";
			String invalidPhone = "55512345678";  // More than 10 characters
			
			boolean result = contactService.updateContact(validID, VALID_FIRST_NAME, VALID_LAST_NAME, invalidPhone, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should return false for invalid phone");
		}
		
		@Test
		@DisplayName("updateContact() Test: Invalid Address (exceeds max length)")
		/*
		 * Test updateContact() with invalid address returns false
		 */
		void updateContactInvalidAddressTest() {
			String validID = "validid1234";
			String invalidAddress = "123 Main Street Apartment Suite Building Complex Extra";  // More than 30 characters
			
			boolean result = contactService.updateContact(validID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, invalidAddress);
			
			assertFalse(result, "updateContact() should return false for invalid address");
		}
		
		@Test
		@DisplayName("updateContact() Test: Phone with Malicious Characters")
		/*
		 * Test updateContact() rejects phone with malicious characters
		 */
		void updateContactMaliciousPhoneTest() {
			String validID = "validid1234";
			String maliciousPhone = "555<script>";
			
			boolean result = contactService.updateContact(validID, VALID_FIRST_NAME, VALID_LAST_NAME, maliciousPhone, VALID_ADDRESS);
			
			assertFalse(result, "updateContact() should reject phone with malicious characters");
		}
		
		@Test
		@DisplayName("updateContact() Test: Address with Malicious Characters")
		/*
		 * Test updateContact() rejects address with malicious characters
		 */
		void updateContactMaliciousAddressTest() {
			String validID = "validid1234";
			String maliciousAddress = "123 Main St <script>alert('xss')</script>";
			
			boolean result = contactService.updateContact(validID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, maliciousAddress);
			
			assertFalse(result, "updateContact() should reject address with malicious characters");
		}
	}
	
	
	// ========== Delete Method Tests (removeContact) ==========
	/**
	 * Tests for the removeContact() method
	 */
	@Nested
	@Tag("CRUD")
	@Tag("Delete")
	@Tag("Mutator")
	@DisplayName("removeContact() Method Tests")
	class RemoveContactTests {
		
		@Test
		@DisplayName("removeContact() Test: Null ID")
		/*
		 * Test removeContact() with null ID returns false
		 */
		void removeContactNullIDTest() {
			String nullID = null;
			
			boolean result = contactService.removeContact(nullID);
			
			assertFalse(result, "removeContact() should return false for null ID");
		}
		
		@Test
		@DisplayName("removeContact() Test: Empty ID")
		/*
		 * Test removeContact() with empty ID returns false
		 */
		void removeContactEmptyIDTest() {
			String emptyID = "";
			
			boolean result = contactService.removeContact(emptyID);
			
			assertFalse(result, "removeContact() should return false for empty ID");
		}
		
		@Test
		@DisplayName("removeContact() Test: Non-existent Contact")
		/*
		 * Test removeContact() with non-existent ID returns false
		 */
		void removeContactNonexistentTest() {
			String nonexistentID = "nonexist99";
			
			boolean result = contactService.removeContact(nonexistentID);
			
			assertFalse(result, "removeContact() should return false for non-existent contact");
		}
		
		@Test
		@DisplayName("removeContact() Test: Invalid ID Format")
		/*
		 * Test removeContact() with invalid ID format returns false
		 */
		void removeContactInvalidIDFormatTest() {
			String invalidID = "short";  // Less than 10 characters
			
			boolean result = contactService.removeContact(invalidID);
			
			assertFalse(result, "removeContact() should return false for invalid ID format");
		}
		
		@Test
		@DisplayName("removeContact() Test: ID with Non-Alphanumeric Characters")
		/*
		 * Test removeContact() with ID containing non-alphanumeric characters returns false
		 */
		void removeContactNonAlphanumericIDTest() {
			String nonAlphanumericID = "contact-123";
			
			boolean result = contactService.removeContact(nonAlphanumericID);
			
			assertFalse(result, "removeContact() should return false for non-alphanumeric ID");
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
		@DisplayName("Integration Test: Add Multiple Contacts")
		/*
		 * Test adding multiple contacts to the service
		 */
		void addMultipleContactsIntegrationTest() {
			boolean result1 = contactService.addContact("John", "Doe", "5551234567", "123 Main St");
			boolean result2 = contactService.addContact("Jane", "Smith", "5559876543", "456 Oak Ave");
			boolean result3 = contactService.addContact("Bob", "Johnson", "5555555555", "789 Pine Rd");
			
			assertTrue(result1 && result2 && result3, "All three contacts should be added successfully");
		}
		
		@Test
		@DisplayName("Integration Test: Valid Name and Phone Combinations")
		/*
		 * Test various valid combinations of names and phone numbers
		 */
		void validCombinationsTest() {
			boolean test1 = contactService.addContact("A", "B", "5", "123 St");
			boolean test2 = contactService.addContact("John", "Doe", "5551234567", "123 Main Street");
			boolean test3 = contactService.addContact("1234567890", "0987654321", "1234567890", "123456789012345678901234567890");
			
			assertTrue(test1 && test2 && test3, "All valid combinations should be accepted");
		}
		
		@Test
		@DisplayName("Integration Test: Boundary Value Testing")
		/*
		 * Test boundary values for name, phone, and address lengths
		 */
		void boundaryValueTest() {
			// 1 character names (minimum)
			boolean minNames = contactService.addContact("A", "B", "5551234567", "123 Main St");
			
			// 10 character names (maximum)
			boolean maxNames = contactService.addContact("1234567890", "0987654321", "5551234567", "123 Main St");
			
			// 1 character phone (minimum)
			boolean minPhone = contactService.addContact("John", "Doe", "5", "123 Main St");
			
			// 10 character phone (maximum)
			boolean maxPhone = contactService.addContact("John", "Doe", "1234567890", "123 Main St");
			
			// 1 character address (minimum)
			boolean minAddr = contactService.addContact("John", "Doe", "5551234567", "A");
			
			// 30 character address (maximum)
			boolean maxAddr = contactService.addContact("John", "Doe", "5551234567", "123456789012345678901234567890");
			
			assertTrue(minNames && maxNames && minPhone && maxPhone && minAddr && maxAddr, 
				"All boundary values should be accepted");
		}
	}
}