package contactService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ContactService provides a CRUD API for managing Contact objects.
 * 
 * This service handles:
 * - Creation of Contact objects
 * - Storage and management of contacts in a Map
 * - Unique ID generation and assignment
 * 
 * The ID key in the contact library Map is immutable and serves as the unique identifier.
 * All attribute validation is done by Service prior to object creation.
 */
public class ContactService {
    
    // ========== Private Instance Variables ==========
	/**
	 * Constant attributes used for validation
	 */
	protected static final int ID_MAX_LENGTH = 10;
    protected static final int NAME_MAX_LENGTH = 10;
    protected static final int PHONE_MAX_LENGTH = 10;
    protected static final int STRING_MAX_LENGTH = 30;
	
    /** 
     * Unsorted Map for storing (ID, Contact) key-value pairs.
     * The ID is immutable and serves as the unique key.
     */
    protected Map<String, Contact> contactMap;
    
    
    // ========== Constructor ==========
    /**
     * Constructs a ContactService and initializes an empty contact library.
     */
    public ContactService() {
        this.contactMap = new HashMap<>();
    }
    
    
    // ========== UUID Generation ==========
    /**
     * Generates a unique 10-character ID for a new contact.
     * 
     * Uses Java's UUID generator and trims the result to 10 characters.
     * Checks for duplicates in the contact library and regenerates if needed.
     * 
     * @return a unique 10-character String ID
     * 
     * @throws RuntimeException if the UUID generator fails or cannot generate a unique ID
     */
    protected String genUID() throws RuntimeException {
    	try {
            while (true) {
                // Generate UUID and convert to string
                String tempUID = UUID.randomUUID().toString();
                
                // Remove hyphens and trim to ID_MAX_LENGTH
                tempUID = tempUID.replace("-", "");
                tempUID = tempUID.substring(0, ID_MAX_LENGTH);
                
                // Check for duplicate ID
                if (!checkDuplicate(tempUID)) {
                    return tempUID;
                }
                // If duplicate, loop continues to regenerate
            }
        } catch (Exception e) {
            System.out.println("Error generating UUID: " + e.getMessage());
            return null;
        }
    }
    
    // ========== Validation Functions ==========
    // ========================
    // Validation Functions
    // ========================
    
    /**
     * Validates ID format.
     * Must not be null/empty, exactly 10 characters, alphanumeric only.
     * 
     * @param id the ID to validate
     * @return true if valid, false otherwise
     */
    public boolean idValidator(String id) {
        if (id == null || id.isEmpty()) {
            return false;
        }
        if (id.length() != ID_MAX_LENGTH) {
            return false;
        }
        if (!id.matches("[a-zA-Z0-9]+")) {
            return false;
        }
        return true;
    }
    
    /**
     * Validates name format.
     * Must not be null/empty, length between 1 and 10 characters.
     * 
     * @param name the name to validate
     * @return true if valid, false otherwise
     */
    public boolean nameValidator(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (name.length() <= 0 || name.length() > NAME_MAX_LENGTH) {
            return false;
        }
        return true;
    }
    
    /**
     * Validates phone number format.
     * Must not be null/empty, length between 1 and 10, no malicious characters.
     * 
     * @param phone the phone number to validate
     * @return true if valid, false otherwise
     */
    public boolean phoneValidator(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        if (phone.length() <= 0 || phone.length() > PHONE_MAX_LENGTH) {
            return false;
        }
        // Check for malicious patterns
        if (phone.contains("<") || phone.contains(">") || phone.contains("&") ||
            phone.contains("\"") || phone.contains("'")) {
            return false;
        }
        return true;
    }
    
    /**
     * Validates address format.
     * Must not be null/empty, length between 1 and 30, no malicious characters.
     * 
     * @param addr the address to validate
     * @return true if valid, false otherwise
     */
    public boolean addressValidator(String addr) {
        if (addr == null || addr.isEmpty()) {
            return false;
        }
        if (addr.length() <= 0 || addr.length() > STRING_MAX_LENGTH) {
            return false;
        }
        // Check for malicious patterns
        if (addr.contains("<") || addr.contains(">") || addr.contains("&") ||
            addr.contains("\"") || addr.contains("'")) {
            return false;
        }
        return true;
    }
    
    /**
     * Checks if an ID already exists in the contact library.
     * 
     * @param id the ID to check for duplicates
     * @return true if the ID already exists in the library, false otherwise
     * 
     * @throws IllegalArgumentException if the ID is null or empty
     */
    public boolean checkDuplicate(String id) {
        if (!idValidator(id)) {
            throw new IllegalArgumentException("Invalid ID format");
        }
        return contactMap.containsKey(id);
    }
    
    
    // ========== CRUD Operations ==========
    /**
     * Adds a new contact to the library with auto-generated ID.
     * 
     * Validates all input parameters, generates a unique ID, creates a Contact object,
     * and stores it in the library.
     * 
     * @param fName the first name of the new contact
     * @param lName the last name of the new contact
     * @param ph the phone number of the new contact
     * @param adr the address of the new contact
     * 
     * @return True if contact was successfully added, or False otherwise
     */
    public boolean addContact(String fName, String lName, String ph, String addr) {
    	try {
            // Validate all parameters
            if (!nameValidator(fName) || !nameValidator(lName) || 
                !phoneValidator(ph) || !addressValidator(addr)) {
                throw new IllegalArgumentException(
                    "Invalid parameters: \n firstName=" + fName + 
                    "\n lastName=" + lName + 
                    "\n phone=" + ph + 
                    "\n address=" + addr);
            }
            
            // Generate unique ID
            String newID = genUID();
            
            // Check if ID generation succeeded
            if (!idValidator(newID)) {
                throw new IllegalArgumentException("Failed to generate valid ID (null or invalid)");
            }
            
            // Create and add new contact
            Contact newContact = new Contact(newID, fName, lName, ph, addr);
            contactMap.put(newID, newContact);
            
            System.out.println("Contact added successfully");
            return true;
            
        } catch (IllegalArgumentException e) {
            System.out.println("Null values are not accepted for contact creation: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Failure adding new contact: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Retrieves a contact by ID.
     * 
     * @param id the contact ID
     * 
     * @return Contact object if found, null otherwise
     */
    public Contact getContact(String id) {
        if (!idValidator(id)) {
            System.out.println("Null or invalid ID values are not accepted");
            return null;
        }
        
        Contact contact = contactMap.get(id);
        if (contact == null) {
            System.out.println("Contact not found for ID: " + id);
        }
        return contact;
    }
    
    /**
     * Updates an existing contact by ID.
     * Validates all parameters and replaces contact in map.
     * 
     * @param id the contact ID to update
     * @param fName new first name
     * @param lName new last name
     * @param phone new phone number
     * @param addr new address
     * 
     * @return true if update successful, false otherwise
     */
    public boolean updateContact(String id, String fName, String lName, String phone, String addr) {
        try {
            // Validate all parameters
            if (!idValidator(id) || !nameValidator(fName) || !nameValidator(lName) ||
                !phoneValidator(phone) || !addressValidator(addr)) {
                System.out.println("Invalid parameters for update");
                return false;
            }
            
            // Check if contact exists
            if (!contactMap.containsKey(id)) {
                System.out.println("Contact not found for ID: " + id);
                return false;
            }
            
            // Get old contact reference
            Contact oldContact = contactMap.get(id);
            
            if (oldContact == null) {
                return false;
            }
            
            // Create and store new contact
            Contact newContact = new Contact(id, fName, lName, phone, addr);
            contactMap.put(id, newContact);
            
            System.out.println("Contact updated successfully");
            return true;
            
        } catch (Exception e) {
            System.out.println("Error updating contact: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Removes a contact by ID.
     * 
     * @param id the contact ID to remove
     * @return true if contact removed, false if not found or error occurs
     */
    public boolean removeContact(String id) {
        if (!idValidator(id)) {
            System.out.println("Null or invalid ID values are not accepted");
            return false;
        }
        
        try {
            if (contactMap.containsKey(id)) {
                contactMap.remove(id);
                System.out.println("Contact removed successfully");
                return true;
            } else {
                System.out.println("Contact not found for ID: " + id);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error removing contact: " + e.getMessage());
            return false;
        }
    }
}
