package model;

import static org.junit.Assert.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.DataManagerTest;
import controller.ProjectDB;
import model.Gantt;

import java.util.ArrayList;
import java.util.Date;

/**
 * Tests model/Gantt.java
 * @author Lukas Cardot-Goyette
 *
 */

public class GanttTest {
	
	@Before
	public void setUp() throws Exception {
		DataManagerTest.createDatabaseFixtures();
	}

	@After
	public void tearDown() throws Exception {
		DataManagerTest.destroyDatabaseFixtures();
	}
	
	//Test Constructor
	@Test
	public void chartShouldBeCreated()
	{
		//Project Test Variables
		int id = 1;
		String name = "test";
		Date startDate = new Date();
		Date dueDate = new Date();
		String description = "test";
				
		Project testProject = new Project(id, name, startDate,dueDate, description);
		
		Gantt testGantt = new Gantt(testProject);
		
		assertNotNull("Gantt not created", testGantt);
		
		//Chart Test Variables
		String nameChart = testProject.getName();
		String xAxis = "Activity";
		String yAxis = "Date";
		IntervalCategoryDataset dataset = testGantt.createDataset();
		JFreeChart testChart = ChartFactory.createGanttChart(
				nameChart, xAxis, yAxis, dataset, true, true, false);
		
		//Test if chart was correctly created
		assertNotNull("Chart not Created", testChart);
	}
	
	//Test createDataset()
	@Test
	public void shouldCreateDataset()
	{
		//Project Test Variables		
		Project testProject = new Project(1, "test", new Date(), new Date(), "Test");
		Activity testActivity1 = new Activity(1, "Test1");
		testProject.addActivity(testActivity1);
		testActivity1.setDueDate(new Date());
		Activity testActivity2 = new Activity(1, "Test2");
		testProject.addActivity(testActivity2);
		testActivity2.setDueDate(new Date());
				
		Gantt testGantt = new Gantt(testProject);
		
		IntervalCategoryDataset dataset = testGantt.createDataset();
		
		//Test if dataset was correctly created
		assertNotNull("Dataset is not created", dataset);
	}
}
