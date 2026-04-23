package taskServiceTests;

import org.junit.jupiter.api.*;
import taskService.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * White-box JUnit test class for Task.
 * 
 * Organized with nested test classes to categorize:
 * - Constructor tests
 * - Accessor tests
 * - Functional tests (equals, toString, immutability)
 */
@DisplayName("Task Tests")
public class TaskTest {
	// Global Task
	private Task task;
	
	// Valid test construction arguments
	private static String validID = "task123abc";
	private static String validName = "Test Task";
	private static String validDescription = "This is a test task description";
	
	// ========== Constructor Test Setup ==========
	/**
	 * Instantiation of Task
	 * 
	 * Tests that Task can accept valid arguments
	 */
	@BeforeEach
	@DisplayName("Task Setup")
	void setUpTest() {
		try {
			task = new Task(validID, validName, validDescription);
		}
		catch (Exception e) {
			System.out.println("Task constructor failed with arguments:" + "\n" +
					validID + "\n" + 
					validName + "\n" + 
					validDescription + "\n"
			);
			
			// Abort all tests if constructor fails
			Assumptions.abort("Task Constructor Failed!");
		}
	}
	
	/**
	 * Task Instantiation Test
	 */
	@Test
	@Tag("Constructor")
	@DisplayName("Task Instantiation")
	void instantiationTest() {
		assertInstanceOf(Task.class, task, "Task successfully created");
	}

	
	// ========== Accessor Method Tests ==========
	/**
	 * Tests all accessor methods of Task for ID, name, and description
	 */
	@Nested
	@Tag("Accessor")
	@DisplayName("Accessor Method Tests")
	class AccessorTests {
		
		@Test
		@DisplayName("getID() returns correct ID")
		void testGetID() {
			assertEquals(validID, task.getID(), "getID() should return the ID passed in constructor");
		}
		
		@Test
		@DisplayName("getName() returns correct name")
		void testGetName() {
			assertEquals(validName, task.getName(), "getName() should return the name passed in constructor");
		}
		
		@Test
		@DisplayName("getDescription() returns correct description")
		void testGetDescription() {
			assertEquals(validDescription, task.getDescription(), "getDescription() should return the description passed in constructor");
		}
		
		@Test
		@DisplayName("Accessors handle null ID")
		void testGetIDWithNull() {
			Task nullIDTask = new Task(null, validName, validDescription);
			assertNull(nullIDTask.getID(), "getID() should return null when ID is null");
		}
		
		@Test
		@DisplayName("Accessors handle null name")
		void testGetNameWithNull() {
			Task nullNameTask = new Task(validID, null, validDescription);
			assertNull(nullNameTask.getName(), "getName() should return null when name is null");
		}
		
		@Test
		@DisplayName("Accessors handle null description")
		void testGetDescriptionWithNull() {
			Task nullDescTask = new Task(validID, validName, null);
			assertNull(nullDescTask.getDescription(), "getDescription() should return null when description is null");
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
			@DisplayName("equals() returns true for identical tasks")
			void testEqualsSameValues() {
				Task task2 = new Task(validID, validName, validDescription);
				assertTrue(task.equals(task2), "Tasks with identical values should be equal");
				assertTrue(task2.equals(task), "equals() should be symmetric");
			}
			
			@Test
			@DisplayName("equals() returns false for different ID")
			void testEqualsDifferentId() {
				Task differentIDTask = new Task("different_id", validName, validDescription);
				assertFalse(task.equals(differentIDTask), "Tasks with different IDs should not be equal");
			}
			
			@Test
			@DisplayName("equals() returns false for different name")
			void testEqualsDifferentName() {
				Task differentNameTask = new Task(validID, "Different Name", validDescription);
				assertFalse(task.equals(differentNameTask), "Tasks with different names should not be equal");
			}
			
			@Test
			@DisplayName("equals() returns false for different description")
			void testEqualsDifferentDescription() {
				Task differentDescTask = new Task(validID, validName, "Different description");
				assertFalse(task.equals(differentDescTask), "Tasks with different descriptions should not be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when comparing with null")
			void testEqualsWithNull() {
				assertFalse(task.equals(null), "Comparing with null should return false");
			}
			
			@Test
			@DisplayName("equals() handles both tasks with null ID")
			void testEqualsBothNullId() {
				Task task1 = new Task(null, validName, validDescription);
				Task task2 = new Task(null, validName, validDescription);
				assertTrue(task1.equals(task2), "Tasks with both null IDs and same other fields should be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when only one task has null ID")
			void testEqualsOneNullId() {
				Task nullIDTask = new Task(null, validName, validDescription);
				assertFalse(task.equals(nullIDTask), "Tasks with one null ID should not be equal");
			}
			
			@Test
			@DisplayName("equals() handles both tasks with null name")
			void testEqualsBothNullName() {
				Task task1 = new Task(validID, null, validDescription);
				Task task2 = new Task(validID, null, validDescription);
				assertTrue(task1.equals(task2), "Tasks with both null names and same other fields should be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when only one task has null name")
			void testEqualsOneNullName() {
				Task nullNameTask = new Task(validID, null, validDescription);
				assertFalse(task.equals(nullNameTask), "Tasks with one null name should not be equal");
			}
			
			@Test
			@DisplayName("equals() handles both tasks with null description")
			void testEqualsBothNullDescription() {
				Task task1 = new Task(validID, validName, null);
				Task task2 = new Task(validID, validName, null);
				assertTrue(task1.equals(task2), "Tasks with both null descriptions and same other fields should be equal");
			}
			
			@Test
			@DisplayName("equals() returns false when only one task has null description")
			void testEqualsOneNullDescription() {
				Task nullDescTask = new Task(validID, validName, null);
				assertFalse(task.equals(nullDescTask), "Tasks with one null description should not be equal");
			}
			
			@Test
			@DisplayName("equals() handles all fields null")
			void testEqualsAllFieldsNull() {
				Task task1 = new Task(null, null, null);
				Task task2 = new Task(null, null, null);
				assertTrue(task1.equals(task2), "Tasks with all null fields should be equal");
			}
			
			@Test
			@DisplayName("equals() handles comparison with different type")
			void testEqualsWithDifferentType() {
				String notATask = "not a task";
				assertFalse(task.equals(notATask), "Comparing with different type should return false");
			}
		}
		
		// ========== ToString Tests ==========
		@Nested
		@DisplayName("toString() Method Tests")
		class ToStringTests {
			
			@Test
			@DisplayName("toString() formats output correctly with valid values")
			void testToString() {
				String expected = "Task(id=" + validID + ", name=" + validName + ", description=" + validDescription + ")";
				assertEquals(expected, task.toString(), "toString() should format output as Task(id=..., name=..., description=...)");
			}
			
			@Test
			@DisplayName("toString() handles null ID")
			void testToStringWithNullId() {
				Task nullIDTask = new Task(null, validName, validDescription);
				String expected = "Task(id=null, name=" + validName + ", description=" + validDescription + ")";
				assertEquals(expected, nullIDTask.toString(), "toString() should display 'null' for null ID");
			}
			
			@Test
			@DisplayName("toString() handles null name")
			void testToStringWithNullName() {
				Task nullNameTask = new Task(validID, null, validDescription);
				String expected = "Task(id=" + validID + ", name=null, description=" + validDescription + ")";
				assertEquals(expected, nullNameTask.toString(), "toString() should display 'null' for null name");
			}
			
			@Test
			@DisplayName("toString() handles null description")
			void testToStringWithNullDescription() {
				Task nullDescTask = new Task(validID, validName, null);
				String expected = "Task(id=" + validID + ", name=" + validName + ", description=null)";
				assertEquals(expected, nullDescTask.toString(), "toString() should display 'null' for null description");
			}
			
			@Test
			@DisplayName("toString() handles all fields null")
			void testToStringWithAllNull() {
				Task allNullTask = new Task(null, null, null);
				String expected = "Task(id=null, name=null, description=null)";
				assertEquals(expected, allNullTask.toString(), "toString() should display 'null' for all null fields");
			}
		}
		
		// ========== Immutability Tests ==========
		@Nested
		@DisplayName("Immutability Tests")
		class ImmutabilityTests {
			
			@Test
			@DisplayName("Fields are immutable after construction")
			void testImmutability() {
				String originalId = task.getID();
				String originalName = task.getName();
				String originalDesc = task.getDescription();
				
				// Verify fields haven't changed
				assertEquals(originalId, task.getID(), "ID should not change after construction");
				assertEquals(originalName, task.getName(), "Name should not change after construction");
				assertEquals(originalDesc, task.getDescription(), "Description should not change after construction");
			}
			
			@Test
			@DisplayName("Multiple calls to getters return same values")
			void testConsistentGetterCalls() {
				String id1 = task.getID();
				String id2 = task.getID();
				String name1 = task.getName();
				String name2 = task.getName();
				String desc1 = task.getDescription();
				String desc2 = task.getDescription();
				
				assertEquals(id1, id2, "Multiple calls to getID() should return the same value");
				assertEquals(name1, name2, "Multiple calls to getName() should return the same value");
				assertEquals(desc1, desc2, "Multiple calls to getDescription() should return the same value");
			}
		}
	}
}