package blackBox;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.Project;

import org.junit.Test;

public class blackBoxIsWithinProjectDates {
	private Project testMinProject, testMinPProject, testNomProject, testMaxMProject, testMaxProject;
	private Date minDate, minPDate, nomDate, maxMDate, maxDate;
	
	@Test(expected = Exception.class)
	public void worstCaseBoundaryTesting() throws Exception
	{
		minDate = new Date(Long.MIN_VALUE);
		minPDate = new Date(Long.MIN_VALUE + 1);
		nomDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000");
		maxMDate = new Date(Long.MAX_VALUE - 1);
		maxDate = new Date(Long.MAX_VALUE);
		
		testMinProject = new Project(1, "testmin", minDate, minPDate, "test");
		testMinPProject = new Project(2, "testminP", minPDate, new Date(Long.MIN_VALUE + 2), "test");
		testNomProject = new Project(3, "testnom", nomDate, new SimpleDateFormat("dd/MM/yyyy").parse("02/01/2000"), "test");
		testMaxMProject = new Project(4, "testmaxM", new Date(Long.MAX_VALUE - 2), maxMDate, "test");
		testMaxProject = new Project(5, "testmax", maxMDate, maxDate, "test");
		
		//<1nom, 2nom>
		assertTrue(testNomProject.isWithinProjectDates(testNomProject, nomDate));
		//<1nom, 2min> // expected error
		testNomProject.isWithinProjectDates(testNomProject, minDate);
		//<1nom, 2min+>	// expected error
		testNomProject.isWithinProjectDates(testNomProject, minPDate);
		//<1nom, 2max-> // expected error
		testNomProject.isWithinProjectDates(testNomProject, maxMDate);
		//<1nom, 2max> // expected error
		testNomProject.isWithinProjectDates(testNomProject, maxDate);
		//<1min, 2min>
		assertTrue(testMinProject.isWithinProjectDates(testMinProject, minDate));
		//<1min, 2min+>
		assertTrue(testMinProject.isWithinProjectDates(testMinProject, minPDate));
		//<1min, 2nom> // expected error
		testMinProject.isWithinProjectDates(testMinProject, nomDate);
		//<1min, 2max-> // expected error
		testMinProject.isWithinProjectDates(testMinProject, maxMDate);
		//<1min, 2max> // expected error
		testMinProject.isWithinProjectDates(testMinProject, maxDate);
		//<1min+, 2min> // expected error
		testMinPProject.isWithinProjectDates(testMinPProject, minDate);
		//<1min+, 2min+>
		assertTrue(testMinPProject.isWithinProjectDates(testMinPProject, minPDate));
		//<1min+, 2nom> // expected error
		testMinPProject.isWithinProjectDates(testMinPProject, nomDate);
		//<1min+, 2max-> // expected error
		testMinPProject.isWithinProjectDates(testMinPProject, maxMDate);
		//<1min+, 2max> // expected error
		testMinPProject.isWithinProjectDates(testMinPProject, maxDate);
		//<1max-, 2min> // expected error
		testMaxMProject.isWithinProjectDates(testMaxMProject, minDate);
		//<1max-, 2min+> // expected error
		testMaxMProject.isWithinProjectDates(testMaxMProject, minPDate);
		//<1max-, 2nom>	// expected error
		testMaxMProject.isWithinProjectDates(testMaxMProject, nomDate);
		//<1max-, 2max->
		assertTrue(testMaxMProject.isWithinProjectDates(testMaxMProject, maxMDate));
		//<1max-, 2max> // expected error
		testMaxMProject.isWithinProjectDates(testMaxMProject, maxDate);
		//<1max, 2min>	// expected error
		testMaxProject.isWithinProjectDates(testMaxProject, minDate);
		//<1max, 2min+>	// expected error
		testMaxProject.isWithinProjectDates(testMaxProject, minPDate);
		//<1max, 2nom>	// expected error
		testMaxProject.isWithinProjectDates(testMaxProject, nomDate);
		//<1max, 2max->	
		assertTrue(testMaxProject.isWithinProjectDates(testMaxProject, maxMDate));
		//<1max, 2max>
		assertTrue(testMaxProject.isWithinProjectDates(testMaxProject, maxDate));
	}
}
