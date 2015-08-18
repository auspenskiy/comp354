package blackBox;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.EarnedValueAnalysisTab.EVATestingClass;

public class blackBoxGetEAC {
	//Create a EVA testing object in order to test the private methods in the EarnedValueAnalyssTab
	private EVATestingClass evaTestingClass = null;
	private double minBAC, minPBAC, nomBAC, maxMBAC, maxBAC;
	private double minCPI, minPCPI, nomCPI, maxMCPI, maxCPI;

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
		minBAC = 0; minCPI = 0;
		minPBAC = 0.01; minPCPI = 0.01;
		nomBAC = 10000; nomCPI = 5000;
		maxMBAC = 999999999; maxMCPI = 9999;
		maxBAC = 1000000000; maxCPI = 10000;
		
		//<1nom, 2nom>
		assertEquals(evaTestingClass.getEACmethod(nomBAC,nomCPI), 2, 0 );
		//<1nom, 2min> 
		assertEquals(evaTestingClass.getEACmethod(nomBAC,minCPI), 0, 0 );
		//<1nom, 2min+>	
		assertEquals(evaTestingClass.getEACmethod(nomBAC,minPCPI), 1000000, 0 );
		//<1nom, 2max->
		assertEquals(evaTestingClass.getEACmethod(nomBAC,maxMCPI), 1, 0 );
		//<1nom, 2max>
		assertEquals(evaTestingClass.getEACmethod(nomBAC,maxCPI), 1, 0 );
		//<1min, 2min>
		assertEquals(evaTestingClass.getEACmethod(minBAC,minCPI), 0, 0 );
		//<1min, 2min+>
		assertEquals(evaTestingClass.getEACmethod(minBAC,minPCPI), 0, 0 );
		//<1min, 2nom>
		assertEquals(evaTestingClass.getEACmethod(minBAC,nomCPI), 0, 0 );
		//<1min, 2max->
		assertEquals(evaTestingClass.getEACmethod(minBAC,maxMCPI), 0, 0 );
		//<1min, 2max>
		assertEquals(evaTestingClass.getEACmethod(minBAC,maxCPI), 0, 0 );
		//<1min+, 2min>	
		assertEquals(evaTestingClass.getEACmethod(minPBAC,minCPI), 0, 0 );
		//<1min+, 2min+>
		assertEquals(evaTestingClass.getEACmethod(minPBAC,minPCPI), 1, 0 );
		//<1min+, 2nom>
		assertEquals(evaTestingClass.getEACmethod(minPBAC,nomCPI), 0, 0 );
		//<1min+, 2max->
		assertEquals(evaTestingClass.getEACmethod(minPBAC,maxMCPI), 0, 0 );
		//<1min+, 2max>
		assertEquals(evaTestingClass.getEACmethod(minPBAC,maxCPI), 0, 0 );
		//<1max-, 2min> 
		assertEquals(evaTestingClass.getEACmethod(maxMBAC,minCPI), 0, 0 );
		//<1max-, 2min+> 
		assertEquals(evaTestingClass.getEACmethod(maxMBAC,minPCPI), 2.147483647e7, 0 );
		//<1max-, 2nom>	
		assertEquals(evaTestingClass.getEACmethod(maxMBAC,nomCPI), 199999.99, 0 );
		//<1max-, 2max->
		assertEquals(evaTestingClass.getEACmethod(maxMBAC,maxMCPI), 100010, 0 );
		//<1max-, 2max>
		assertEquals(evaTestingClass.getEACmethod(maxMBAC,maxCPI), 99999.99, 0 );
		//<1max, 2min>	
		assertEquals(evaTestingClass.getEACmethod(maxBAC,minCPI), 0, 0 );
		//<1max, 2min+>	
		assertEquals(evaTestingClass.getEACmethod(maxBAC,minPCPI), 2.147483647e7, 0 );
		//<1max, 2nom>	
		assertEquals(evaTestingClass.getEACmethod(maxBAC,nomCPI), 200000, 0 );
		//<1max, 2max->	
		assertEquals(evaTestingClass.getEACmethod(maxBAC,maxMCPI), 100010, 0 );
		//<1max, 2max>
		assertEquals(evaTestingClass.getEACmethod(maxBAC,maxCPI), 100000, 0 );
	}
}
