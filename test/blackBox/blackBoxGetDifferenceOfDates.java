package blackBox;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.EarnedValueAnalysisTab.EVATestingClass;

public class blackBoxGetDifferenceOfDates {
	//Create a EVA testing object in order to test the private methods in the EarnedValueAnalyssTab
	private EVATestingClass evaTestingClass = null;
	private Calendar nom = Calendar.getInstance();
	private Calendar minP = Calendar.getInstance();
	private Calendar min = Calendar.getInstance();
	private Calendar max = Calendar.getInstance();
	private Calendar maxM = Calendar.getInstance();
	
	@Before
	public void init() {
		evaTestingClass = EVATestingClass.getInstance();
	}
	
	@After
	//Make sure that the EVATestingClass object is set to null
	public void destructor() {
		evaTestingClass = null;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void worstCaseBoundaryTesting() throws ParseException
	{
		nom.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2001"));
		
		minP.setTime(new Date(Long.MIN_VALUE + 1));
		
		min.setTime(new Date(Long.MIN_VALUE));
		
		max.setTime(new Date(Long.MAX_VALUE));
		
		maxM.setTime(new Date(Long.MAX_VALUE - 1));
		
		//<1nom, 2nom>
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(nom,nom), 0, 0 );
		//<1nom, 2min> //error expected
		evaTestingClass.getDifferenceOfDatesMethod(nom,min);
		//<1nom, 2min+>	//error expected
		evaTestingClass.getDifferenceOfDatesMethod(nom,minP);
		//<1nom, 2max->
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(nom,maxM), 9.2233711e+18, 0 );
		//<1nom, 2max>
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(nom,max), 9.2233711e+18, 0 );
		//<1min, 2min>
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(min,min), 0, 0 );
		//<1min, 2min+>
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(min,minP), 1, 0 );
		//<1min, 2nom>
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(min,nom), 9.223373e+18, 0 );
		//<1min, 2max->
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(min,maxM), 1.8446744e+19, 0 );
		//<1min, 2max>
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(min,max), 1.8446744e+19, 0 );
		//<1min+, 2min>	//error expected
		evaTestingClass.getDifferenceOfDatesMethod(minP,min);
		//<1min+, 2min+>
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(minP,minP), 0, 0 );
		//<1min+, 2nom>
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(minP,nom), 9.223373e+18, 0 );
		//<1min+, 2max->
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(minP,maxM), 1.8446744e+19, 0 );
		//<1min+, 2max>
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(minP,max), 1.8446744e+19, 0 );
		//<1max-, 2min> //error expected
		evaTestingClass.getDifferenceOfDatesMethod(maxM,min);
		//<1max-, 2min+> //error expected
		evaTestingClass.getDifferenceOfDatesMethod(maxM,minP);
		//<1max-, 2nom>	//error expected
		evaTestingClass.getDifferenceOfDatesMethod(maxM,nom);
		//<1max-, 2max->
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(maxM,maxM), 0, 0 );
		//<1max-, 2max>
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(maxM,max), 1, 0 );
		//<1max, 2min>	//error expected
		evaTestingClass.getDifferenceOfDatesMethod(max,min);
		//<1max, 2min+>	//error expected
		evaTestingClass.getDifferenceOfDatesMethod(max,minP);
		//<1max, 2nom>	//error expected
		evaTestingClass.getDifferenceOfDatesMethod(max,nom);
		//<1max, 2max->	//error expected
		evaTestingClass.getDifferenceOfDatesMethod(max,maxM);
		//<1max, 2max>
		assertEquals(evaTestingClass.getDifferenceOfDatesMethod(max,max), 0, 0 );
	}
}
