package taskService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * TaskService provides an API for managing Task objects.
 * 
 * This service handles:
 * - Creation of Task objects with unique IDs
 * - Storage and management of tasks in a Map
 * - Deletion of Task objects by ID
 * - Unique ID generation and validation
 * 
 * All attribute validation is performed by TaskService before task creation.
 */
public class TaskService {
    
    // ========== Private Constants ==========
    /** Maximum length for task ID */
    protected static final int ID_MAX_LENGTH = 10;
    
    /** Maximum length for task name */
    protected static final int NAME_MAX_LENGTH = 20;
    
    /** Maximum length for task description */
    protected static final int DESCRIPTION_MAX_LENGTH = 50;
    
    
    // ========== Private Instance Variables ==========
    /** 
     * Map for storing (ID, Task) key-value pairs.
     * The ID is immutable and serves as the unique key.
     */
    protected Map<String, Task> taskMap;
    
    
    // ========== Constructor ==========
    /**
     * Constructs a TaskService and initializes an empty task map.
     */
    public TaskService() {
        this.taskMap = new HashMap<>();
    }
    
    
    // ========== UUID Generation ==========
    /**
     * Generates a unique 10-character ID for a new task.
     * 
     * Uses Java's UUID generator and trims the result to 10 characters.
     * Checks for duplicates in the task map and regenerates if needed.
     * 
     * @return a unique 10-character String ID, or null if generation fails
     */
    protected String genUID() {
        try {
            while (true) {
                // Generate UUID string
                String tempUID = UUID.randomUUID().toString();
                
                // Convert to String if not already and remove hyphens
                tempUID = tempUID.replace("-", "");
                
                // Trim to ID_MAX_LENGTH
                tempUID = tempUID.substring(0, ID_MAX_LENGTH);
                
                // Check for duplicate ID in Map
                if (!checkDuplicate(tempUID)) {
                    // No duplicate found, return this ID
                    return tempUID;
                }
                // Duplicate found, continue loop to regenerate
            }
        } catch (Exception e) {
            // Error from UUID Generator
            System.out.println("UUID generation failed: " + e.getMessage());
            return null;
        }
    }
    
    
    // ========== Validation Methods ==========
    /**
     * Validates a task ID.
     * 
     * Checks that the ID:
     * - is not null or empty
     * - is exactly 10 characters long
     * - contains only alphanumeric characters
     * 
     * @param id the ID string to validate
     * @return true if the ID is valid, false otherwise
     */
    public boolean idValidator(String id) {
        // Check for null or empty
        if (id == null || id.isEmpty()) {
            return false;
        }
        
        // Check for exactly 10 characters
        if (id.length() != ID_MAX_LENGTH) {
            return false;
        }
        
        // Check for only alphanumeric characters
        if (!id.matches("[a-zA-Z0-9]+")) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Validates a task name.
     * 
     * Checks that the name:
     * - is not null or empty
     * - length is between 1 and NAME_MAX_LENGTH characters
     * 
     * @param name the name string to validate
     * @return true if the name is valid, false otherwise
     */
    public boolean nameValidator(String name) {
        // Check for null or empty
        if (name == null || name.isEmpty()) {
            return false;
        }
        
        // Check length constraints
        if (name.length() <= 0 || name.length() > NAME_MAX_LENGTH) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Validates a task description.
     * 
     * Checks that the description:
     * - is not null or empty
     * - length is between 1 and DESCRIPTION_MAX_LENGTH characters
     * - does not contain malicious characters: '<', '>', '&', '"', '\''
     * 
     * @param description the description string to validate
     * @return true if the description is valid, false otherwise
     */
    public boolean descValidator(String description) {
        // Check for null or empty
        if (description == null || description.isEmpty()) {
            return false;
        }
        
        // Check length constraints
        if (description.length() <= 0 || description.length() > DESCRIPTION_MAX_LENGTH) {
            return false;
        }
        
        // Check for common malicious patterns
        if (description.contains("<") || description.contains(">") || description.contains("&") || 
            description.contains("\"") || description.contains("'")) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Checks if an ID already exists in the task map.
     * 
     * @param id the ID to check for duplicates
     * @return true if the ID already exists in the map, false if it doesn't
     */
    public boolean checkDuplicate(String id) {
        // Null/Empty check using idValidator
        if (!idValidator(id)) {
            return false;
        }
        
        // Check if taskMap already contains id as key
        return taskMap.containsKey(id);
    }
    
    
    // ========== CRUD Operations ==========
    /**
     * Adds a new task to the map with an auto-generated ID.
     * 
     * Validates all input parameters before creating and storing the task.
     * 
     * @param name the name of the new task
     * @param description the description of the new task
     * 
     * @return true if the task was successfully added, false otherwise
     */
    public boolean addTask(String name, String description) {
        try {
            // Null/Empty check
            if (!nameValidator(name) || !descValidator(description)) {
                System.out.println("Null values are not accepted for task creation");
                return false;
            }
            
            // Get ID from UUID Generator
            String newID = genUID();
            
            // Check for null value from genUID()
            if (!idValidator(newID)) {
                System.out.println("Null values are not accepted for task creation: ID generation failed");
                return false;
            }
            
            // Create Task record with immutable fields
            Task newTask = new Task(newID, name, description);
            
            // Insert (ID, Task) key-value pair into Map
            taskMap.put(newID, newTask);
            
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Null values are not accepted for task creation: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Failure adding new task: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Retrieves a task by its unique ID.
     * 
     * @param id the unique ID of the task to retrieve
     * 
     * @return the Task record if found, null otherwise
     */
    public Task getTask(String id) {
        // Null/Empty check
        if (!idValidator(id)) {
            System.out.println("Null values are not accepted");
            return null;
        }
        
        // Search Map keys for ID
        if (taskMap.containsKey(id)) {
            // Return Task if ID is found
            return taskMap.get(id);
        } else {
            return null;
        }
    }
    
    /**
     * Updates an existing task's information.
     * 
     * Validates all input parameters, retrieves the existing task, creates a new
     * Task record with the updated information, and replaces the old task in the map.
     * Since Task is a record with immutable fields, we create a new Task instance.
     * 
     * @param id the unique ID of the task to update
     * @param name the updated task name
     * @param description the updated task description
     * 
     * @return true if the task was successfully updated, false otherwise
     */
    public boolean updateTask(String id, String name, String description) {
        // Null/Empty check
        if (!idValidator(id) || !nameValidator(name) || !descValidator(description)) {
            return false;
        }
        
        // If Task found in map
        if (taskMap.containsKey(id)) {
            try {
                Task oldTask = taskMap.get(id);
                
                if (oldTask != null) {
                    // Create new Task record with updated information
                    Task updatedTask = new Task(id, name, description);
                    
                    // Replace the old task in the Map with the new one
                    taskMap.put(id, updatedTask);
                    
                    // Old task will be garbage collected
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                System.out.println("Error updating task: " + e.getMessage());
                return false;
            }
        } else {
            // If no Task found, return False
            return false;
        }
    }
    
    /**
     * Removes a task from the map by its unique ID.
     * 
     * @param id the unique ID of the task to remove
     * 
     * @return true if the task was successfully removed, false otherwise
     */
    public boolean removeTask(String id) {
        // Null/Empty check
        if (!idValidator(id)) {
            return false;
        }
        
        try {
            // Search for task in map and delete if found
            if (taskMap.containsKey(id)) {
                taskMap.remove(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error removing task: " + e.getMessage());
            return false;
        }
    }
}
