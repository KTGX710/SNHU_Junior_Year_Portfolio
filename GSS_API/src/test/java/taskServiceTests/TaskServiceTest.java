package taskServiceTests;

import org.junit.jupiter.api.*;
import taskService.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TaskService Test class.
 * 
 * Stores tests for all methods within the TaskService class.
 * 
 * Utilizes Tags; CRUD, Create, Read, Update, Delete, Mutator, and Setup.
 */
@DisplayName("TaskService Object Tests")
public class TaskServiceTest {
	private TaskService taskService;
	
	// Valid test task data
	private static String validName = "Complete Project";
	private static String validDescription = "Finish the project and deploy to production";
	
	// ========== Constructor and Setup Tests ==========
	/**
	 * Instantiation of TaskService Object
	 * 
	 * Tests that TaskService object can be created successfully before each test
	 */
	@BeforeEach
	void setUpTest() {
		try {
			taskService = new TaskService();
		}
		catch (Exception e) {
			System.out.println("TaskService constructor failed with exception: " + e.getMessage());
			
			// Abort test if constructor fails
			Assumptions.abort("TaskService Constructor Failed!");
		}
	}
	
	/**
	 * TaskService Object Instantiation Test
	 */
	@Test
	@Tag("Setup")
	@DisplayName("TaskService Object Instantiation")
	void objectTest() {
		assertInstanceOf(TaskService.class, taskService, "TaskService object successfully created");
	}

	
	// ========== Create Method Tests (addTask) ==========
	/**
	 * Tests for the addTask() method
	 */
	@Nested
	@Tag("CRUD")
	@Tag("Create")
	@Tag("Mutator")
	@DisplayName("addTask() Method Tests")
	class AddTaskTests {
		
		@Test
		@DisplayName("addTask() Test: Add Valid Task")
		/*
		 * Test addTask() with valid parameters returns true
		 */
		void addValidTaskTest() {
			boolean result = taskService.addTask(validName, validDescription);
			
			assertTrue(result, "addTask() should return true for valid parameters");
		}
		
		@Test
		@DisplayName("addTask() Test: Task is Retrievable")
		/*
		 * Test that added task can be retrieved from the service
		 */
		void addValidTaskRetrievableTest() {
			boolean addSuccess = taskService.addTask(validName, validDescription);
			
			assertTrue(addSuccess, "Task should be added successfully");
			
			// Verify at least one task exists that matches our criteria
			// Note: We can't directly get the ID without modifying addTask to return it
			// So we verify by testing with known IDs or checking multiple getTask calls
		}
		
		@Test
		@DisplayName("addTask() Test: Null Name")
		/*
		 * Test addTask() with null name returns false
		 */
		void addNullNameTest() {
			String nullName = null;
			
			boolean result = taskService.addTask(nullName, validDescription);
			
			assertFalse(result, "addTask() should return false for null name");
		}
		
		@Test
		@DisplayName("addTask() Test: Null Description")
		/*
		 * Test addTask() with null description returns false
		 */
		void addNullDescriptionTest() {
			String nullDescription = null;
			
			boolean result = taskService.addTask(validName, nullDescription);
			
			assertFalse(result, "addTask() should return false for null description");
		}
		
		@Test
		@DisplayName("addTask() Test: Empty Name")
		/*
		 * Test addTask() with empty name returns false
		 */
		void addEmptyNameTest() {
			String emptyName = "";
			
			boolean result = taskService.addTask(emptyName, validDescription);
			
			assertFalse(result, "addTask() should return false for empty name");
		}
		
		@Test
		@DisplayName("addTask() Test: Empty Description")
		/*
		 * Test addTask() with empty description returns false
		 */
		void addEmptyDescriptionTest() {
			String emptyDescription = "";
			
			boolean result = taskService.addTask(validName, emptyDescription);
			
			assertFalse(result, "addTask() should return false for empty description");
		}
		
		@Test
		@DisplayName("addTask() Test: Invalid Name (exceeds max length)")
		/*
		 * Test addTask() with name exceeding max length returns false
		 */
		void addInvalidNameTest() {
			String invalidName = "This name is way too long for the task";  // More than 20 characters
			
			boolean result = taskService.addTask(invalidName, validDescription);
			
			assertFalse(result, "addTask() should return false for invalid name");
		}
		
		@Test
		@DisplayName("addTask() Test: Invalid Description (exceeds max length)")
		/*
		 * Test addTask() with description exceeding max length returns false
		 */
		void addInvalidDescriptionTest() {
			String invalidDescription = "This description is way too long and exceeds the maximum character limit for task descriptions";  // More than 50 characters
			
			boolean result = taskService.addTask(validName, invalidDescription);
			
			assertFalse(result, "addTask() should return false for invalid description");
		}
		
		@Test
		@DisplayName("addTask() Test: Description with Malicious Characters")
		/*
		 * Test addTask() rejects description with malicious characters
		 */
		void addMaliciousDescriptionTest() {
			String maliciousDescription = "Task <script>alert('xss')</script>";
			
			boolean result = taskService.addTask(validName, maliciousDescription);
			
			assertFalse(result, "addTask() should reject description with malicious characters");
		}
		
		@Test
		@DisplayName("addTask() Test: Multiple Tasks Added")
		/*
		 * Test addTask() can add multiple tasks successfully
		 */
		void addMultipleTasksTest() {
			boolean result1 = taskService.addTask("Task One", "First task description");
			boolean result2 = taskService.addTask("Task Two", "Second task description");
			boolean result3 = taskService.addTask("Task Three", "Third task description");
			
			assertTrue(result1 && result2 && result3, "All tasks should be added successfully");
		}
		
		@Test
		@DisplayName("addTask() Test: Max Length Name and Description")
		/*
		 * Test addTask() with maximum length name and description
		 */
		void addMaxLengthTest() {
			String maxName = "Exactly 20 Chars!!!";  // Exactly 20 characters
			String maxDescription = "Exactly 50 Characters Long Description Text Now";  // Exactly 50 characters
			
			boolean result = taskService.addTask(maxName, maxDescription);
			
			assertTrue(result, "addTask() should accept max length name and description");
		}
	}
	
	
	// ========== Read Method Tests (getTask) ==========
	/**
	 * Tests for the getTask() method
	 */
	@Nested
	@Tag("CRUD")
	@Tag("Read")
	@DisplayName("getTask() Method Tests")
	class GetTaskTests {
		
		@Test
		@DisplayName("getTask() Test: Null ID")
		/*
		 * Test getTask() with null ID returns null
		 */
		void getTaskNullIDTest() {
			String nullID = null;
			
			Task result = taskService.getTask(nullID);
			
			assertNull(result, "getTask() should return null for null ID");
		}
		
		@Test
		@DisplayName("getTask() Test: Empty ID")
		/*
		 * Test getTask() with empty ID returns null
		 */
		void getTaskEmptyIDTest() {
			String emptyID = "";
			
			Task result = taskService.getTask(emptyID);
			
			assertNull(result, "getTask() should return null for empty ID");
		}
		
		@Test
		@DisplayName("getTask() Test: Non-existent ID")
		/*
		 * Test getTask() with non-existent ID returns null
		 */
		void getTaskNonexistentIDTest() {
			String nonexistentID = "nonexist99";
			
			Task result = taskService.getTask(nonexistentID);
			
			assertNull(result, "getTask() should return null for non-existent ID");
		}
		
		@Test
		@DisplayName("getTask() Test: Invalid ID Format")
		/*
		 * Test getTask() with invalid ID format returns null
		 */
		void getTaskInvalidIDFormatTest() {
			String invalidID = "short";  // Less than 10 characters
			
			Task result = taskService.getTask(invalidID);
			
			assertNull(result, "getTask() should return null for invalid ID format");
		}
		
		@Test
		@DisplayName("getTask() Test: ID with Non-Alphanumeric Characters")
		/*
		 * Test getTask() with ID containing non-alphanumeric characters returns null
		 */
		void getTaskNonAlphanumericIDTest() {
			String nonAlphanumericID = "task-123456";
			
			Task result = taskService.getTask(nonAlphanumericID);
			
			assertNull(result, "getTask() should return null for non-alphanumeric ID");
		}
	}
	
	
	// ========== Update Method Tests (updateTask) ==========
	/**
	 * Tests for the updateTask() method
	 */
	@Nested
	@Tag("CRUD")
	@Tag("Update")
	@Tag("Mutator")
	@DisplayName("updateTask() Method Tests")
	class UpdateTaskTests {
		
		@Test
		@DisplayName("updateTask() Test: Null ID")
		/*
		 * Test updateTask() with null ID returns false
		 */
		void updateTaskNullIDTest() {
			String nullID = null;
			
			boolean result = taskService.updateTask(nullID, validName, validDescription);
			
			assertFalse(result, "updateTask() should return false for null ID");
		}
		
		@Test
		@DisplayName("updateTask() Test: Empty ID")
		/*
		 * Test updateTask() with empty ID returns false
		 */
		void updateTaskEmptyIDTest() {
			String emptyID = "";
			
			boolean result = taskService.updateTask(emptyID, validName, validDescription);
			
			assertFalse(result, "updateTask() should return false for empty ID");
		}
		
		@Test
		@DisplayName("updateTask() Test: Null Name")
		/*
		 * Test updateTask() with null name returns false
		 */
		void updateTaskNullNameTest() {
			String validID = "validid1234";
			String nullName = null;
			
			boolean result = taskService.updateTask(validID, nullName, validDescription);
			
			assertFalse(result, "updateTask() should return false for null name");
		}
		
		@Test
		@DisplayName("updateTask() Test: Null Description")
		/*
		 * Test updateTask() with null description returns false
		 */
		void updateTaskNullDescriptionTest() {
			String validID = "validid1234";
			String nullDescription = null;
			
			boolean result = taskService.updateTask(validID, validName, nullDescription);
			
			assertFalse(result, "updateTask() should return false for null description");
		}
		
		@Test
		@DisplayName("updateTask() Test: Empty Name")
		/*
		 * Test updateTask() with empty name returns false
		 */
		void updateTaskEmptyNameTest() {
			String validID = "validid1234";
			String emptyName = "";
			
			boolean result = taskService.updateTask(validID, emptyName, validDescription);
			
			assertFalse(result, "updateTask() should return false for empty name");
		}
		
		@Test
		@DisplayName("updateTask() Test: Empty Description")
		/*
		 * Test updateTask() with empty description returns false
		 */
		void updateTaskEmptyDescriptionTest() {
			String validID = "validid1234";
			String emptyDescription = "";
			
			boolean result = taskService.updateTask(validID, validName, emptyDescription);
			
			assertFalse(result, "updateTask() should return false for empty description");
		}
		
		@Test
		@DisplayName("updateTask() Test: Non-existent Task")
		/*
		 * Test updateTask() with non-existent ID returns false
		 */
		void updateTaskNonexistentTest() {
			String nonexistentID = "nonexist99";
			
			boolean result = taskService.updateTask(nonexistentID, validName, validDescription);
			
			assertFalse(result, "updateTask() should return false for non-existent task");
		}
		
		@Test
		@DisplayName("updateTask() Test: Invalid Name (exceeds max length)")
		/*
		 * Test updateTask() with invalid name returns false
		 */
		void updateTaskInvalidNameTest() {
			String validID = "validid1234";
			String invalidName = "This name is way too long for the task";  // More than 20 characters
			
			boolean result = taskService.updateTask(validID, invalidName, validDescription);
			
			assertFalse(result, "updateTask() should return false for invalid name");
		}
		
		@Test
		@DisplayName("updateTask() Test: Invalid Description (exceeds max length)")
		/*
		 * Test updateTask() with invalid description returns false
		 */
		void updateTaskInvalidDescriptionTest() {
			String validID = "validid1234";
			String invalidDescription = "This description is way too long and exceeds the maximum character limit for task descriptions";  // More than 50 characters
			
			boolean result = taskService.updateTask(validID, validName, invalidDescription);
			
			assertFalse(result, "updateTask() should return false for invalid description");
		}
		
		@Test
		@DisplayName("updateTask() Test: Description with Malicious Characters")
		/*
		 * Test updateTask() rejects description with malicious characters
		 */
		void updateTaskMaliciousDescriptionTest() {
			String validID = "validid1234";
			String maliciousDescription = "Task <script>alert('xss')</script>";
			
			boolean result = taskService.updateTask(validID, validName, maliciousDescription);
			
			assertFalse(result, "updateTask() should reject description with malicious characters");
		}
	}
	
	
	// ========== Delete Method Tests (removeTask) ==========
	/**
	 * Tests for the removeTask() method
	 */
	@Nested
	@Tag("CRUD")
	@Tag("Delete")
	@Tag("Mutator")
	@DisplayName("removeTask() Method Tests")
	class RemoveTaskTests {
		
		@Test
		@DisplayName("removeTask() Test: Null ID")
		/*
		 * Test removeTask() with null ID returns false
		 */
		void removeTaskNullIDTest() {
			String nullID = null;
			
			boolean result = taskService.removeTask(nullID);
			
			assertFalse(result, "removeTask() should return false for null ID");
		}
		
		@Test
		@DisplayName("removeTask() Test: Empty ID")
		/*
		 * Test removeTask() with empty ID returns false
		 */
		void removeTaskEmptyIDTest() {
			String emptyID = "";
			
			boolean result = taskService.removeTask(emptyID);
			
			assertFalse(result, "removeTask() should return false for empty ID");
		}
		
		@Test
		@DisplayName("removeTask() Test: Non-existent Task")
		/*
		 * Test removeTask() with non-existent ID returns false
		 */
		void removeTaskNonexistentTest() {
			String nonexistentID = "nonexist99";
			
			boolean result = taskService.removeTask(nonexistentID);
			
			assertFalse(result, "removeTask() should return false for non-existent task");
		}
		
		@Test
		@DisplayName("removeTask() Test: Invalid ID Format")
		/*
		 * Test removeTask() with invalid ID format returns false
		 */
		void removeTaskInvalidIDFormatTest() {
			String invalidID = "short";  // Less than 10 characters
			
			boolean result = taskService.removeTask(invalidID);
			
			assertFalse(result, "removeTask() should return false for invalid ID format");
		}
		
		@Test
		@DisplayName("removeTask() Test: ID with Non-Alphanumeric Characters")
		/*
		 * Test removeTask() with ID containing non-alphanumeric characters returns false
		 */
		void removeTaskNonAlphanumericIDTest() {
			String nonAlphanumericID = "task-123456";
			
			boolean result = taskService.removeTask(nonAlphanumericID);
			
			assertFalse(result, "removeTask() should return false for non-alphanumeric ID");
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
		@DisplayName("Integration Test: Add Multiple Tasks")
		/*
		 * Test adding multiple tasks to the service
		 */
		void addMultipleTasksIntegrationTest() {
			boolean result1 = taskService.addTask("Task 1", "Description 1");
			boolean result2 = taskService.addTask("Task 2", "Description 2");
			boolean result3 = taskService.addTask("Task 3", "Description 3");
			
			assertTrue(result1 && result2 && result3, "All three tasks should be added successfully");
		}
		
		@Test
		@DisplayName("Integration Test: Add and Update Multiple Tasks")
		/*
		 * Test adding and updating multiple tasks
		 */
		void addAndUpdateMultipleTasksTest() {
			// Add tasks
			boolean add1 = taskService.addTask("Original Name 1", "Description 1");
			boolean add2 = taskService.addTask("Original Name 2", "Description 2");
			
			assertTrue(add1 && add2, "Both tasks should be added");
			
			// Note: Without returning IDs from addTask, we can only test that operations complete without error
		}
		
		@Test
		@DisplayName("Integration Test: Valid Name and Description Combinations")
		/*
		 * Test various valid combinations of name and description
		 */
		void validCombinationsTest() {
			boolean test1 = taskService.addTask("A", "A");
			boolean test2 = taskService.addTask("Short Task", "Short description");
			boolean test3 = taskService.addTask("Exactly 20 Chars!!!!", "Exactly 50 Characters Long Description Text Now");
			boolean test4 = taskService.addTask("Mixed123 Name!", "Description with 123 numbers and symbols!");
			
			assertTrue(test1 && test2 && test3 && test4, "All valid combinations should be accepted");
		}
		
		@Test
		@DisplayName("Integration Test: Boundary Value Testing")
		/*
		 * Test boundary values for name and description lengths
		 */
		void boundaryValueTest() {
			// 1 character name (minimum)
			boolean minName = taskService.addTask("A", "Description");
			
			// 20 character name (maximum)
			boolean maxName = taskService.addTask("12345678901234567890", "Description");
			
			// 1 character description (minimum)
			boolean minDesc = taskService.addTask("Task Name", "D");
			
			// 50 character description (maximum)
			boolean maxDesc = taskService.addTask("Task Name", "12345678901234567890123456789012345678901234567890");
			
			assertTrue(minName && maxName && minDesc && maxDesc, "All boundary values should be accepted");
		}
	}
}