package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.DataManagerTest;
import controller.ProjectDB;

import model.Project.InvalidProjectException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Tests model/Project.java
 * @author Lukas Cardot-Goyette
 *
 */

public class ProjectTest {

	@Before
	public void setUp() throws Exception {
		DataManagerTest.createDatabaseFixtures();
	}

	@After
	public void tearDown() throws Exception {
		DataManagerTest.destroyDatabaseFixtures();
	}
	
	// Tests Constructor
	@Test
	public void projectShouldBeCreated()
	{
		//Test Variables
		int id = 1;
		String name = "test";
		Date startDate = new Date();
		Date dueDate = new Date();
		String description = "test";
		
		Project testProject = new Project(id, name, startDate,dueDate, description);
		
		//Tests that the project was created
		assertNotNull("Project not created", testProject);

		// Tests that parameters were passed into the Object
		assertEquals("ID didn't match", testProject.getId(), id);
		assertEquals("Name didn't match", testProject.getName(), name);
		assertEquals("Start Date didn't match", testProject.getStartDate(), startDate);
		assertEquals("Due Date didn't match", testProject.getDueDate(), dueDate);
		assertEquals("Description name didn't match", testProject.getDescription(),
				description);
	}
	
	// Tests equals()
	@Test
	public void shouldBeEqual()
	{
		Project testProject1 = new Project(1, "test", new Date(), new Date(), "Test");
		Project testProject2 = null;
		Project testProject3 = new Project(2, "test", new Date(), new Date(), "Test");
		String testString = "if you read this you're awesome";
		
		//Tests if the same project is equal
		assertTrue("Same Projects are not equal", testProject1.equals(testProject1));
		assertFalse("Null Project", testProject1.equals(testProject2));
		assertFalse("Different class", testProject1.equals(testString));
		assertFalse("Different project", testProject1.equals(testProject3));
	}
	
	//Test delete()
	@Test(expected = NullPointerException.class)
	public void shouldDeleteProjectAndActivities()
	{
		Project testProject = new Project(1, "test", new Date(), new Date(), "Test");
		
		testProject.delete();
		
		Project testProject2 = testProject;
		
		assertNull("Project not deleted properly", testProject2);
	}
	
	//@TODO
	public void shouldBeInProjectDates()
	{
		
	}
	
	
	// hasUniqueName()
	public void shouldHaveUniqueName()
	{
		Project testProject = new Project(1, "test1", new Date(), new Date(), "Test");
		
		Project project = ProjectDB.getByName("test");
		
		//Tests if project is Null, that is, no project with the same name
		assertNull("Project cannot be unique", project);
	}
	
	// tests isValid()
	@Test
	public void shouldBeValid() throws ParseException, InvalidProjectException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Project testProject = new Project(1, "test1", sdf.parse("01/01/2015"), sdf.parse("01/02/2015"), "Test");
		Activity testActivity1 = new Activity(1, "Test1");
		testActivity1.setDueDate(sdf.parse("10/01/2015"));
		testActivity1.setStartDate(sdf.parse("09/01/2015"));
		Activity testActivity2 = new Activity(1, "Test2");
		testActivity2.setDueDate(sdf.parse("20/01/2015"));
		testActivity2.setStartDate(sdf.parse("19/01/2015"));
		Activity testActivity3 = new Activity(1, "Test3");
		testActivity3.setDueDate(sdf.parse("30/01/2015"));
		testActivity3.setStartDate(sdf.parse("29/01/2015"));
		
		testProject.addActivity(testActivity1);
		testProject.addActivity(testActivity2);
		testProject.addActivity(testActivity3);
		
		testActivity1.setActivityId(1);
		testActivity2.setActivityId(2);
		testActivity3.setActivityId(3);
		
		testActivity1.addDependent(2);
		testActivity2.addDependent(3);
		testActivity3.addDependent(1);
		
		User testUser = new User(1, "test","test","test","test","test");
		
		testProject.addProjectPM(testUser);
		
		assertTrue(testProject.isValid());
	}
	
	//Test removeActivity()
	@Test
	public void shouldRemoveActivity()
	{
		//Test variables initialize
		Project testProject = new Project(1, "test", new Date(), new Date(), "Test");
		Activity testActivity = new Activity(1, "Test");
		testProject.addActivity(testActivity);
		
		//Remove the only activity
		testProject.removeActivity(testActivity);
		
		//Test if no more activity
		assertEquals("Activity is not removed.", testProject.getActivities(), new ArrayList<Activity>());
	}
	
	//Test addActivity()
	@Test
	public void shouldAddActivity()
	{
		//Test variables initialize
		Project testProject = new Project(1, "test", new Date(), new Date(), "Test");
		Activity testActivity = new Activity(1, "Test");
		
		assertEquals(testProject.getActivities(), new ArrayList<Activity>());
		
		testProject.addActivity(testActivity);
		
		assertNotNull("No Activity was added.", testProject.getActivities());
		assertEquals("Activity added is not the one expected", testProject.getActivities().get(0), testActivity);
	}
	
	//test addProjectPM()
	@Test
	public void shouldAddProjectPM()
	{
		User testUser = new User(1, "test", "test", "test", "test", "test");
		Project testProject = new Project(1, "test", new Date(), new Date(), "Test");
		
		testProject.addProjectPM(testUser);
		
		assertEquals(testProject.getProjectPMs().get(0), testUser);
	}
	
	//Test getActivitiesStrictlyBeforeDate()
	@Test
	public void shouldBeBeforeDate() throws ParseException, InvalidProjectException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date testDate = null;
		int activityNumber = 20;
		Activity[] activities = new Activity[activityNumber];
		
		//Initialize Test variables
		Project testProject = new Project(1, "test", sdf.parse("01/01/2015"), sdf.parse("01/02/2015"), "Test");
		
		for(int i = 0; i < activityNumber; i++)
		{
			if(i == 0)
			{
				//No Activities in Project
				assertEquals(testProject.getActivities(), new ArrayList());
			}
			
			testDate = sdf.parse(((i+3)%30) + "/01/2015");
			activities[i] = new Activity(1, "Test " + (i + 1));
			activities[i].setDueDate(sdf.parse(((i+2)%30) + "/01/2015"));
			activities[i].setStartDate(sdf.parse(((i+1)%30) + "/01/2015"));
			testProject.addActivity(activities[i]);
			
			if(i > 0)
			{
				//testDate is after the last added activity's due date
				assertEquals(testProject.getActivities(), testProject.getActivitiesStrictlyBeforeDate(testDate));
			}
			else if(i == 0)
			{
				assertEquals(testProject.getActivities().get(0), activities[0]);
			}
		}
	}
	
	//Test getActivitiesWithinDate()
	@Test
	public void shouldBeWithinDate() throws ParseException, InvalidProjectException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date testDate1 = null, testDate2 = null, testDate3 = null;
		
		//Initialize Test variables
		Project testProject = new Project(1, "test", sdf.parse("01/01/2015"), sdf.parse("01/02/2015"), "Test");
		Activity testActivity1 = new Activity(1, "Test1");
		testActivity1.setDueDate(sdf.parse("10/01/2015"));
		testActivity1.setStartDate(sdf.parse("08/01/2015"));
		Activity testActivity2 = new Activity(1, "Test2");
		testActivity2.setDueDate(sdf.parse("20/01/2015"));
		testActivity2.setStartDate(sdf.parse("18/01/2015"));
		Activity testActivity3 = new Activity(1, "Test3");
		testActivity3.setDueDate(sdf.parse("30/01/2015"));
		testActivity3.setStartDate(sdf.parse("28/01/2015"));
		
		testProject.addActivity(testActivity1);
		testProject.addActivity(testActivity2);
		testProject.addActivity(testActivity3);
		
		//Create 3 test dates
		try {
			testDate1 = sdf.parse("09/01/2015");
			testDate2 = sdf.parse("19/01/2015");
			testDate3 = sdf.parse("31/01/2015");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Assert method
		assertEquals("Should return all activities.", testProject.getActivities(), testProject.getActivitiesStrictlyBeforeDate(testDate3));
		
		assertEquals("Activity should be equal.", testActivity1, testProject.getActivitiesStrictlyBeforeDate(testDate2).get(0));
		assertEquals("Should have only one activity", 1, testProject.getActivitiesStrictlyBeforeDate(testDate2).size());
		
		assertTrue("Should return no activities.", testProject.getActivitiesStrictlyBeforeDate(testDate1).isEmpty());
		assertFalse("Should return activities.", testProject.getActivitiesStrictlyBeforeDate(testDate3).isEmpty());
	}
	
	//tests setEstimatedBudget
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSetEstimatedBudget()
	{
		Project testProject = new Project(1, "test", new Date(), new Date(), "Test");
		
		//IllegalArgumentException expected
		testProject.setEstimatedBudget(-1000);
	}
	
	//tests setActualBudget
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotSetActualBudget()
	{
		Project testProject = new Project(1, "test", new Date(), new Date(), "Test");
		
		//IllegalArgumentException expected
		testProject.setActualBudget(-1000);
	}
}
