import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	controller.ActivityDBTest.class,
	controller.DatabaseConstantsTest.class,
	controller.DataManagerTest.class,
	controller.PredecessorDBTest.class,
	controller.ProjectDBTest.class,
	controller.UserDBTest.class,
	controller.UserRolesDBTest.class,
//	controller.UserRolesDictDBTest.class,
	controller.ViewManagerTest.class,
	model.UserTest.class,
	model.ActivityTest.class,
	view.LoginPanelTest.class,
	view.TreePanelTest.class,
	view.MainViewPanelTest.class,
	view.ApplicationPanelTest.class
})

public class TestSuite {}  	