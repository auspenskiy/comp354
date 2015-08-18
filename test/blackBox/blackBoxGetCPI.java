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


/**
 * 
 * Black Box testing for View.EarnedValueAnalysisTab.getCPI() Method
 *
 */
public class blackBoxGetCPI {
	//Create a EVA testing object in order to test the private methods in the EarnedValueAnalyssTab
	private EVATestingClass evaTestingClass = null;
	private double minEV, minPEV, nomEV, maxMEV, maxEV;
	private double minAC, minPAC, nomAC, maxMAC, maxAC;

	@Before
	public void init() {
		evaTestingClass = EVATestingClass.getInstance();
	}
	
	@After
	//Make sure that the EVATestingClass object is set to null
	public void destructor() {
		evaTestingClass = null;
	}
	
	@Test
	public void worstCaseBoundaryTesting() throws ParseException
	{
		minEV = 0; minAC = 0;
		minPEV = 0.01; minPAC = 0.01;
		nomEV = 10000; nomAC = 10000;
		maxMEV = 999999999; maxMAC = 999999999;
		maxEV = 1000000000; maxAC = 1000000000;
		
		//<1nom, 2nom>
		assertEquals(evaTestingClass.getCPImethod(nomEV,nomAC), 1, 0 );
		//<1nom, 2min> 
		assertEquals(evaTestingClass.getCPImethod(nomEV,minAC), 0, 0 );
		//<1nom, 2min+>	
		assertEquals(evaTestingClass.getCPImethod(nomEV,minPAC), 1000000, 0 );
		//<1nom, 2max->
		assertEquals(evaTestingClass.getCPImethod(nomEV,maxMAC), 0, 0 );
		//<1nom, 2max>
		assertEquals(evaTestingClass.getCPImethod(nomEV,maxAC), 0, 0 );
		//<1min, 2min>
		assertEquals(evaTestingClass.getCPImethod(minEV,minAC), 0, 0 );
		//<1min, 2min+>
		assertEquals(evaTestingClass.getCPImethod(minEV,minPAC), 0, 0 );
		//<1min, 2nom>
		assertEquals(evaTestingClass.getCPImethod(minEV,nomAC), 0, 0 );
		//<1min, 2max->
		assertEquals(evaTestingClass.getCPImethod(minEV,maxMAC), 0, 0 );
		//<1min, 2max>
		assertEquals(evaTestingClass.getCPImethod(minEV,maxAC), 0, 0 );
		//<1min+, 2min>	
		assertEquals(evaTestingClass.getCPImethod(minPEV,minAC), 0, 0 );
		//<1min+, 2min+>
		assertEquals(evaTestingClass.getCPImethod(minPEV,minPAC), 1, 0 );
		//<1min+, 2nom>
		assertEquals(evaTestingClass.getCPImethod(minPEV,nomAC), 0, 0 );
		//<1min+, 2max->
		assertEquals(evaTestingClass.getCPImethod(minPEV,maxMAC), 0, 0 );
		//<1min+, 2max>
		assertEquals(evaTestingClass.getCPImethod(minPEV,maxAC), 0, 0 );
		//<1max-, 2min> 
		assertEquals(evaTestingClass.getCPImethod(maxMEV,minAC), 0, 0 );
		//<1max-, 2min+> 
		assertEquals(evaTestingClass.getCPImethod(maxMEV,minPAC), 2.147483647e7, 0 );
		//<1max-, 2nom>	
		assertEquals(evaTestingClass.getCPImethod(maxMEV,nomAC), 99999.99, 0 );
		//<1max-, 2max->
		assertEquals(evaTestingClass.getCPImethod(maxMEV,maxMAC), 1, 0 );
		//<1max-, 2max>
		assertEquals(evaTestingClass.getCPImethod(maxMEV,maxAC), 0.99, 0 );
		//<1max, 2min>	
		assertEquals(evaTestingClass.getCPImethod(maxEV,minAC), 0, 0 );
		//<1max, 2min+>	
		assertEquals(evaTestingClass.getCPImethod(maxEV,minPAC), 2.147483647e7, 0 );
		//<1max, 2nom>	
		assertEquals(evaTestingClass.getCPImethod(maxEV,nomAC), 100000, 0 );
		//<1max, 2max->	
		assertEquals(evaTestingClass.getCPImethod(maxEV,maxMAC), 1, 0 );
		//<1max, 2max>
		assertEquals(evaTestingClass.getCPImethod(maxEV,maxAC), 1, 0 );	
	}
}