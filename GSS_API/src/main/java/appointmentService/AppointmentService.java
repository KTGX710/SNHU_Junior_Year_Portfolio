package appointmentService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * AppointmentService provides a CRUD API for managing Appointment objects.
 * 
 * This service handles:
 * - Creation of Appointment objects
 * - Storage and management of appointments in a Map
 * - Unique ID generation and assignment
 * - Validation of appointment dates (must be in the future)
 * 
 * The ID key in the appointment library Map is immutable and serves as the unique identifier.
 * All attribute validation is done by Service prior to object creation.
 */
public class AppointmentService {
    
    // ========== Private Instance Variables ==========
    /**
     * Constant attributes used for validation
     */
    protected static final int APPOINTMENT_ID_MAX_LENGTH = 10;
    protected static final int DESCRIPTION_MAX_LENGTH = 50;
    
    /** 
     * Unsorted Map for storing (AppointmentID, Appointment) key-value pairs.
     * The appointment ID is immutable and serves as the unique key.
     */
    protected Map<String, Appointment> appointmentMap;
    
    
    // ========== Constructor ==========
    /**
     * Constructs an AppointmentService and initializes an empty appointment library.
     */
    public AppointmentService() {
        this.appointmentMap = new HashMap<>();
    }
    
    
    // ========== UUID Generation ==========
    /**
     * Generates a unique 10-character ID for a new appointment.
     * 
     * Uses Java's UUID generator and trims the result to 10 characters.
     * Checks for duplicates in the appointment library and regenerates if needed.
     * 
     * @return a unique 10-character String appointment ID
     * 
     * @throws RuntimeException if the UUID generator fails or cannot generate a unique ID
     */
    protected String genUID() throws RuntimeException {
        try {
            while (true) {
                // Generate UUID and convert to string
                String tempID = UUID.randomUUID().toString();
                
                // Remove hyphens and trim to APPOINTMENT_ID_MAX_LENGTH
                tempID = tempID.replace("-", "").substring(0, APPOINTMENT_ID_MAX_LENGTH);
                
                // Check for duplicate ID
                if (!checkDuplicate(tempID)) {
                    return tempID;
                }
                // If duplicate, loop continues to regenerate
            }
        } catch (Exception e) {
            System.out.println("Error generating appointment ID: " + e.getMessage());
            return null;
        }
    }
    
    
    // ========== Validation Functions ==========
    /**
     * Validates appointment ID format.
     * Must not be null/empty, exactly 10 characters, alphanumeric only.
     * 
     * @param appointmentID the appointment ID to validate
     * @return true if valid, false otherwise
     */
    public boolean appointmentIDValidator(String appointmentID) {
        if (appointmentID == null || appointmentID.isEmpty()) {
            return false;
        }
        if (appointmentID.length() != APPOINTMENT_ID_MAX_LENGTH) {
            return false;
        }
        if (!appointmentID.matches("[a-zA-Z0-9]+")) {
            return false;
        }
        return true;
    }
    
    /**
     * Validates appointment date format.
     * Must not be null and must be in the future (not in the past).
     * 
     * @param appointmentDate the appointment date to validate
     * @return true if valid, false otherwise
     */
    public boolean appointmentDateValidator(Date appointmentDate) {
        if (appointmentDate == null) {
            return false;
        }
        // Check if the date is in the past
        if (appointmentDate.before(new Date()) || appointmentDate.equals(new Date())) {
            return false;
        }
        return true;
    }
    
    /**
     * Validates description format.
     * Must not be null/empty, length between 1 and 50 characters.
     * 
     * @param description the description to validate
     * @return true if valid, false otherwise
     */
    public boolean descriptionValidator(String description) {
        if (description == null || description.isEmpty()) {
            return false;
        }
        if (description.length() <= 0 || description.length() > DESCRIPTION_MAX_LENGTH) {
            return false;
        }
        return true;
    }
    
    /**
     * Checks if an appointment ID already exists in the appointment library.
     * 
     * @param appointmentID the appointment ID to check for duplicates
     * @return true if the appointment ID already exists in the library, false otherwise
     * 
     * @throws IllegalArgumentException if the appointment ID is null or empty
     */
    public boolean checkDuplicate(String appointmentID) {
        if (!appointmentIDValidator(appointmentID)) {
            throw new IllegalArgumentException("Invalid appointment ID format");
        }
        return appointmentMap.containsKey(appointmentID);
    }
    
    
    // ========== CRUD Operations ==========
    /**
     * Adds a new appointment to the library with auto-generated ID.
     * 
     * Validates all input parameters, generates a unique appointment ID, creates an Appointment object,
     * and stores it in the library.
     * 
     * @param appointmentDate the date of the new appointment (must be in the future)
     * @param description the description of the new appointment
     * 
     * @return True if appointment was successfully added, or False otherwise
     */
    public boolean addAppointment(Date appointmentDate, String description) {
        try {
            // Validate all parameters
            if (!appointmentDateValidator(appointmentDate) || !descriptionValidator(description)) {
                throw new IllegalArgumentException(
                    "Invalid parameters: \n appointmentDate=" + appointmentDate + 
                    "\n description=" + description);
            }
            
            // Generate unique appointment ID
            String newAppointmentID = genUID();
            
            // Check if ID generation succeeded
            if (!appointmentIDValidator(newAppointmentID)) {
                throw new IllegalArgumentException("Failed to generate valid appointment ID (null or invalid)");
            }
            
            // Create and add new appointment
            Appointment newAppointment = new Appointment(newAppointmentID, appointmentDate, description);
            appointmentMap.put(newAppointmentID, newAppointment);
            
            System.out.println("Appointment added successfully");
            return true;
            
        } catch (IllegalArgumentException e) {
            System.out.println("Null values are not accepted for appointment creation: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Failure adding new appointment: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Retrieves an appointment by ID.
     * 
     * @param appointmentID the appointment ID
     * 
     * @return Appointment object if found, null otherwise
     */
    public Appointment getAppointment(String appointmentID) {
        if (!appointmentIDValidator(appointmentID)) {
            System.out.println("Null or invalid appointment ID values are not accepted");
            return null;
        }
        
        Appointment appointment = appointmentMap.get(appointmentID);
        if (appointment == null) {
            System.out.println("Appointment not found for ID: " + appointmentID);
        }
        return appointment;
    }
    
    /**
     * Removes an appointment by ID.
     * 
     * @param appointmentID the appointment ID to remove
     * @return true if appointment removed, false if not found or error occurs
     */
    public boolean removeAppointment(String appointmentID) {
        if (!appointmentIDValidator(appointmentID)) {
            System.out.println("Null or invalid appointment ID values are not accepted");
            return false;
        }
        
        try {
            if (appointmentMap.containsKey(appointmentID)) {
                appointmentMap.remove(appointmentID);
                System.out.println("Appointment removed successfully");
                return true;
            } else {
                System.out.println("Appointment not found for ID: " + appointmentID);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error removing appointment: " + e.getMessage());
            return false;
        }
    }
}
