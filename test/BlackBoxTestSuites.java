import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	blackBox.blackBoxGetDifferenceOfDates.class,
	blackBox.blackBoxGetPvCost.class,
	blackBox.blackBoxGetCPI.class,
	blackBox.blackBoxGetEAC.class
})

public class BlackBoxTestSuites {

}
