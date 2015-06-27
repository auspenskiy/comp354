package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Activity;
import model.DateLabelFormatter;
import model.Project;
import model.User;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.ActivityDB;
import controller.DatabaseConstants;
import controller.PredecessorDB;
import controller.ProjectDB;

/**
 * 
 * @author Zachary Bergeron
 * @modifiedBy Anne-Marie Dube
 *
 */

@SuppressWarnings("serial")
public class EditActivityDialog extends JDialog
{

  private JTextField activityName;
  private JTextArea activityDescription;
  private JScrollPane scrollPanDescription;
  private JComboBox<?> projectBox, statusBox, dependBox, activitiesBox;
  private JLabel projectLabel, dependLabel, activitiesLabel;
  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  UtilDateModel startModel = new UtilDateModel();
  UtilDateModel dueModel = new UtilDateModel();
  Properties p = new Properties();
  private User user;
  private boolean refresh = false;
  private String connectionString = DatabaseConstants.getDb();
  private final ImageIcon deleteIcon = new ImageIcon(MainViewPanel.class.getResource("images/x.png"));
  final JPanel panDependArea = new JPanel();
  final List<JPanel> dependList = new ArrayList<JPanel>();
  

  
  public EditActivityDialog(JFrame parent, String title, boolean modal, User currentUser)
  {
    super(parent, title, modal);
    this.user = currentUser;
    this.setSize(500, 500);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.initComponent();
    this.setVisible(true);
  }
  
  private void initComponent()
  {
	  final JPanel content = new JPanel();
	  
	  //These set the properties for date picker
	  p.put("text.today", "Today");
	  p.put("text.month", "Month");
	  p.put("text.year", "Year");
	  
	  
	  //Choose the Project
	  JPanel panProjectName = new JPanel();
	  panProjectName.setBackground(Color.white);
	  panProjectName.setPreferredSize(new Dimension(465, 60));
	  
	  final List<Project> projects = ProjectDB.getUserProjects(connectionString, user.getId());
	  Vector<String> projectNames = new Vector<String>();
	  for(Project project: projects){
		  projectNames.add(project.getName());
	  }
	  projectBox = new JComboBox<String>(projectNames);
	  panProjectName.setBorder(BorderFactory.createTitledBorder("Project to Edit"));
	  projectLabel = new JLabel("Select Project:");
	  panProjectName.add(projectLabel);
	  panProjectName.add(projectBox);
	  
	  //Choose the Activity
	  JPanel panActivitiesNames = new JPanel();
	  panActivitiesNames.setBackground(Color.white);
	  panActivitiesNames.setPreferredSize(new Dimension(465, 60));
	  
	  final List<Activity> activities = ActivityDB.getProjectActivities(projects.get(projectBox.getSelectedIndex()).getId());
	  final Vector<String> activitiesNames = new Vector<String>();
	  for(Activity activity: activities){
		  activitiesNames.add(activity.getName());
	  }
	  activitiesBox = new JComboBox<String>(activitiesNames);
	  panActivitiesNames.setBorder(BorderFactory.createTitledBorder("Activity to Edit"));
	  activitiesLabel = new JLabel("Select Activity:");
	  panActivitiesNames.add(activitiesLabel);
	  panActivitiesNames.add(activitiesBox);
	  
	  //Activity Name
	  JPanel panActivity = new JPanel();
	  panActivity.setBackground(Color.white);
	  panActivity.setPreferredSize(new Dimension(230, 60));
	  activityName = new JTextField();
	  panActivity.setBorder(BorderFactory.createTitledBorder("Activity Name"));
	  activityName.setPreferredSize(new Dimension(200,30));
	  panActivity.add(activityName);
	  
	  //Start Date
	  JPanel panStartDate = new JPanel();
	  panStartDate.setBackground(Color.white);
	  panStartDate.setPreferredSize(new Dimension(230, 60));
	  panStartDate.setBorder(BorderFactory.createTitledBorder("Start Date"));
	  startModel.setSelected(true);
	  JDatePanelImpl startDateCalendarPanel = new JDatePanelImpl(startModel, p);
	  final JDatePickerImpl startDatePicker = new JDatePickerImpl(startDateCalendarPanel,new DateLabelFormatter());
	  panStartDate.add(startDatePicker);
	  
	  //Due Date
	  JPanel panDueDate = new JPanel();
	  panDueDate.setBackground(Color.white);
	  panDueDate.setPreferredSize(new Dimension(230, 60));
	  panDueDate.setBorder(BorderFactory.createTitledBorder("Due Date"));
	  dueModel.setSelected(false);
	  JDatePanelImpl dueDateCalendarPanel = new JDatePanelImpl(dueModel, p);
	  final JDatePickerImpl dueDatePicker = new JDatePickerImpl(dueDateCalendarPanel,new DateLabelFormatter());
	  panDueDate.add(dueDatePicker);
	  
	  //Status: to-do, in progress, completed
	  JPanel panStatus = new JPanel();
	  panStatus.setBackground(Color.white);
	  panStatus.setPreferredSize(new Dimension(230, 60));
	  final String[] statusArray = new String[]{"To do", "In Progress", "Completed"};
	  statusBox = new JComboBox<String>(statusArray);
	  statusBox.setSelectedIndex(0);
	  panStatus.setBorder(BorderFactory.createTitledBorder("Status"));
	  panStatus.add(statusBox);
	  
	  //Activity Description
	  JPanel panDescription = new JPanel();
	  panDescription.setBackground(Color.white);
	  panDescription.setPreferredSize(new Dimension(465, 80));
	  activityDescription = new JTextArea();
	  panDescription.setBorder(BorderFactory.createTitledBorder("Description (optional)"));
	  activityDescription.setBorder( new JTextField().getBorder() );
	  activityDescription.setLineWrap(true);
	  activityDescription.setWrapStyleWord(true);
	  scrollPanDescription = new JScrollPane(activityDescription, 
			  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	  scrollPanDescription.setPreferredSize(new Dimension(445,45));
	  panDescription.add(scrollPanDescription);
	  
	  //TEMPORARY MESSAGE
	  JLabel dependMesage = new JLabel("Editing Dependents is not yet possible");
	  Font font = new Font(null, 2, 18);
	  dependMesage.setFont(font);
	  
	  //This creates an area to add dependents, this area created so it can be iterated later to add dependencies to table
	  panDependArea.setBackground(Color.white);
	  panDependArea.setLayout(new BoxLayout(panDependArea, BoxLayout.Y_AXIS));
	  
	  final JScrollPane scrollPanDependArea = new JScrollPane(panDependArea, 
			  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	  scrollPanDependArea.setPreferredSize(new Dimension(330, 80));
	  
	  
	  //This button is used to dynamically add dependent fields
	  JButton addDependentButton = new JButton("Add Dependent");
	  addDependentButton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	  createDepend();
	      }      
	  });
	  
	  //Set Content to activity selection
	  Activity currentActivity = ActivityDB.getActivityById(connectionString, activities.get(activitiesBox.getSelectedIndex()).getActivityId());
	  List<Activity> dependents = PredecessorDB.getPredecessors(connectionString, currentActivity.getActivityId());
	  
	  startModel.setValue(currentActivity.getStartDate());
	  dueModel.setValue(currentActivity.getDueDate());
	  activityName.setText(currentActivity.getName());
	  activityDescription.setText(currentActivity.getDescription());
	  
	  for (Activity dependent : dependents){
		  int selectedIndex = activitiesNames.indexOf(dependent.getName());
		  editDepend(selectedIndex);
	  }
	  
	  content.repaint();
	  content.revalidate();
	  
	  //On change of project set activity list + activity details
	  projectBox.addActionListener(new ActionListener(){
		@SuppressWarnings("all")
		public void actionPerformed(ActionEvent arg0) {
			Project currentProject = ProjectDB.getProjectById(projects.get(projectBox.getSelectedIndex()).getId());
	    	  
			final List<Activity> activities = ActivityDB.getProjectActivities(currentProject.getId());
			final Vector<String> activitiesNames = new Vector<String>();
			
			for(Activity activity: activities){
				  activitiesNames.add(activity.getName());
			  }
			ComboBoxModel model = new DefaultComboBoxModel( activitiesNames );
			activitiesBox.setModel(model);
			
			Activity currentActivity = ActivityDB.getActivityById(connectionString, activities.get(activitiesBox.getSelectedIndex()).getActivityId());
			List<Activity> dependents = PredecessorDB.getPredecessors(connectionString, currentActivity.getActivityId());
			
			startModel.setValue(currentActivity.getStartDate());
			dueModel.setValue(currentActivity.getDueDate());
			activityName.setText(currentActivity.getName());
			activityDescription.setText(currentActivity.getDescription());
			panDependArea.removeAll();
			for (Activity dependent : dependents){
	    		  int selectedIndex = activitiesNames.indexOf(dependent.getName());
	    		  editDepend(selectedIndex);
	    	  }
			
			content.repaint();
			content.revalidate();
	      }      
	  });
	  
	  //On change of activity set content
	  activitiesBox.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	  final List<Activity> activities = ActivityDB.getProjectActivities(projects.get(projectBox.getSelectedIndex()).getId());
	    	  Activity currentActivity = ActivityDB.getActivityById(connectionString, activities.get(activitiesBox.getSelectedIndex()).getActivityId());
	    	  List<Activity> dependents = PredecessorDB.getPredecessors(connectionString, currentActivity.getActivityId());
	    	  final Vector<String> activitiesNames = new Vector<String>();
				
	    	  for(Activity activity: activities){
	    		  activitiesNames.add(activity.getName());
	    	  }
	    	  startModel.setValue(currentActivity.getStartDate());
	    	  dueModel.setValue(currentActivity.getDueDate());
	    	  activityName.setText(currentActivity.getName());
	    	  activityDescription.setText(currentActivity.getDescription());
	    	  panDependArea.removeAll();
	    	  for (Activity dependent : dependents){
	    		  int selectedIndex = activitiesNames.indexOf(dependent.getName());
	    		  editDepend(selectedIndex);
	    	  }
	    	  
	    	  content.repaint();
	    	  content.revalidate();
	      }      
	  });
	  
	  
	  JPanel control = new JPanel();
	  JButton okButton = new JButton("Edit Activity");
	  
	  //Creates action
	  okButton.addActionListener(new ActionListener(){
	      @SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0) {
	    	  //Sets variables to simplify verifications
	    	  Date activityStartDate = (Date)startDatePicker.getModel().getValue();
	    	  Date activityDueDate = (Date)dueDatePicker.getModel().getValue();
	    	  Date projectStartDate = projects.get(projectBox.getSelectedIndex()).getStartDate();
	    	  Date projectDueDate = projects.get(projectBox.getSelectedIndex()).getDueDate();
	    	  boolean activityExists = false;
	    	  boolean dependsOnItself = false;
	    	  List<Activity> activities = ActivityDB.getProjectActivities(projects.get(projectBox.getSelectedIndex()).getId());
	    	  Activity currentActivity = ActivityDB.getActivityById(connectionString, activities.get(activitiesBox.getSelectedIndex()).getActivityId());
	    	  Component[] dependAreaComponents = panDependArea.getComponents();
	    	  
	    	  //Checks if the activity already exists
    		  for(Activity activity:activities){
	    		  if(activityName.getText().equals(activity.getName()) && !activityName.getText().equals(currentActivity.getName()))
	    		  { activityExists = true; break; } else{activityExists = false;}
    		  }
    		  
    		  //Checks that none of the dependents selected are itself(this activity)
    		  for (int i = 0; i < dependAreaComponents.length; i++){
    			  JPanel dependPanel = (JPanel) dependAreaComponents[i];
	    		  JComboBox<?> dependBox = (JComboBox<?>) dependPanel.getComponents()[1];
	    		  if(dependBox.getSelectedItem().equals(currentActivity.getName())){dependsOnItself = true; break; } else{dependsOnItself = false;}
    		  }
    		  
	    	  //Verifies all text boxes are filled out, if not = error
	    	  if(activityName.getText().hashCode() == 0 || activityStartDate == null
	    			  || activityDueDate == null || projectBox.getSelectedIndex() < 0){
	    		  JOptionPane.showMessageDialog(content,"Please fill out all fields", "Cannot Create Activity", JOptionPane.ERROR_MESSAGE);
	    	  }
	    	  //Provides error if activity name exists
	    	  else if(activityExists){
    			  JOptionPane.showMessageDialog(content,"Activity with this name already exists", "Cannot Create Activity", JOptionPane.ERROR_MESSAGE);
	    	  }
	    	  //Provides error if dependents selected are itself(this activity)
	    	  else if(dependsOnItself){
    			  JOptionPane.showMessageDialog(content,"Activity cannot depend on itself.\nPlease fix dependents.", "Cannot Create Activity", JOptionPane.ERROR_MESSAGE);
	    	  }
	    	  //Checks that due date not before start date
	    	  else if(activityDueDate.before(activityStartDate)){
	    		  JOptionPane.showMessageDialog(content,"Please ensure due date is not before start date", "Cannot Create Activity", JOptionPane.ERROR_MESSAGE);
	    	  }
	    	  //Checks if activity start date falls in project date constraints
	    	  else if((activityStartDate.getDate() < projectStartDate.getDate() 
	    			  && activityStartDate.getMonth() <= projectStartDate.getMonth() 
	    			  && activityStartDate.getYear() <= projectStartDate.getYear())
	    			  || (activityStartDate.getMonth() < projectStartDate.getMonth() 
	    			  && activityStartDate.getYear() <= projectStartDate.getYear())
	    			  || activityStartDate.getYear() < projectStartDate.getYear()){
	    		  JOptionPane.showMessageDialog(content,"Please ensure start date is within project dates:"
	    				  + dateFormat.format(projectStartDate) +" to "
	    				  + dateFormat.format(projectDueDate), "Cannot Create Activity", JOptionPane.ERROR_MESSAGE);
	    	  }
	    	  //Checks if activity due date falls in project date constraints
	    	  else if((activityDueDate.getDate() > projectDueDate.getDate() 
	    			  && activityDueDate.getMonth() >= projectDueDate.getMonth() 
	    			  && activityDueDate.getYear() >= projectDueDate.getYear())
	    			  || (activityDueDate.getMonth() > projectDueDate.getMonth() 
	    			  && activityDueDate.getYear() >= projectDueDate.getYear())
	    			  || activityDueDate.getYear() > projectDueDate.getYear()){
	    		  JOptionPane.showMessageDialog(content,"Please ensure due date is within project dates : "
	    				  + dateFormat.format(projectStartDate) +" to "
	    				  + dateFormat.format(projectDueDate), "Cannot Create Activity", JOptionPane.ERROR_MESSAGE);
	    	  }
	    	  else{
	    		  
	    		  int response = JOptionPane.showConfirmDialog(content,
	    				  "Are you sure you want to edit the following Activity: \n"
	    						  + "\nActivity Name: " + currentActivity.getName()
	    						  + "\nStart Date: "+ dateFormat.format(currentActivity.getStartDate())
	    						  + "\nDue Date: "+ dateFormat.format(currentActivity.getDueDate())
	    						  +  "\n\nWith the following modifications: \n"
	    						  + "\nActivity Name: " + activityName.getText()
	    						  + "\nStart Date: "+dateFormat.format(startDatePicker.getModel().getValue())
	    						  + "\nDue Date: "+dateFormat.format(dueDatePicker.getModel().getValue()),
	    						  "Confirm "+activities.get(activitiesBox.getSelectedIndex()).getName()+" edit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
	    		  if(response == JOptionPane.YES_OPTION){
	    			  
	    			  //Call the editing Method of a given activity
	    			  ActivityDB.editActivityById(DatabaseConstants.getDb(),
	    					  activities.get(activitiesBox.getSelectedIndex()).getActivityId(),
	    					  activityName.getText(),
	    					  dateFormat.format(startDatePicker.getModel().getValue()), 
	    					  dateFormat.format(dueDatePicker.getModel().getValue()),
	    					  statusBox.getSelectedIndex(),
	    					  activityDescription.getText());
		    		  
		    		  refresh = true;
		    		  
		    		  //Removes all activity dependents
		    		  PredecessorDB.deleteActivityPredecessors(connectionString, currentActivity.getActivityId());
		    		  //Iterates through all dependents and adds them to the DB
		    		  for (int i = 0; i < dependAreaComponents.length; i++){
		    			  JPanel dependPanel = (JPanel) dependAreaComponents[i];
			    		  JComboBox<?> dependBox = (JComboBox<?>) dependPanel.getComponents()[1];
			    		  if(!dependBox.getSelectedItem().equals(currentActivity.getName())){
			    				  PredecessorDB.insertPredecessorIntoTable(connectionString, 
			    						  currentActivity.getActivityId(), activities.get(dependBox.getSelectedIndex()).getActivityId());
			    		  }
		    		  }
		    		  setVisible(false); 
	    		  }
	    	  }
	      }      
	    });
	  JButton cancelButton = new JButton("Cancel");
	  cancelButton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	        setVisible(false);
	      }      
	    });
	  JButton deleteButton = new JButton("Delete Activity");
	  deleteButton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	  final List<Activity> activities = ActivityDB.getProjectActivities(projects.get(projectBox.getSelectedIndex()).getId());
	    	  Activity currentActivity = ActivityDB.getActivityById(connectionString, activities.get(activitiesBox.getSelectedIndex()).getActivityId());
	    	  int response = JOptionPane.showConfirmDialog(content,
    				  "Are you sure you want to DELETE the following Activity: \n"
    						  + "\nActivity Name: " + currentActivity.getName(),
    						  "Confirm "+currentActivity.getName()+" edit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    		  if(response == JOptionPane.YES_OPTION){
    			  //Call the editing Method of a given project
    			  ActivityDB.deleteActivity(connectionString, currentActivity.getActivityId());
    			  setVisible(false);
    			  refresh = true;
    		  }
	    	  
	      }
	    }); 
	  
	  control.add(okButton);
	  control.add(cancelButton);
	  control.add(deleteButton);
	  content.setBackground(Color.white);
	  content.add(panProjectName);
	  content.add(panActivitiesNames);
	  content.add(panActivity);
	  content.add(panStatus);
	  content.add(panStartDate);
	  content.add(panDueDate);
	  content.add(panDescription);
	  content.add(addDependentButton);
	  content.add(scrollPanDependArea);
	  
	  this.getContentPane().add(content, BorderLayout.CENTER);
	  this.getContentPane().add(control, BorderLayout.SOUTH);
  }
  
  public boolean isRefresh()
  {
	  return refresh;
	  
  }
  
  public void createDepend(){
	  
	  final List<Project> projects = ProjectDB.getUserProjects(connectionString, user.getId());
	  final JPanel panDepend = new JPanel();
	  panDepend.setBackground(Color.white);
	  panDepend.setPreferredSize(new Dimension(310, 60));
	  final List<Activity> activities = ActivityDB.getProjectActivities(projects.get(projectBox.getSelectedIndex()).getId());
	  final Vector<String> activitiesNames = new Vector<String>();
	  for(Activity activity: activities){
		  activitiesNames.add(activity.getName());
	  }
	  dependBox = new JComboBox<String>(activitiesNames);
	  panDepend.setBorder(BorderFactory.createTitledBorder("Depends on..."));
	  dependLabel = new JLabel("Select Activity:");
	  panDepend.add(dependLabel);
	  panDepend.add(dependBox);
	  
	  JButton deleteDependentPanelButton = new JButton(deleteIcon);
	  deleteDependentPanelButton.setBorder(BorderFactory.createEmptyBorder());
	  deleteDependentPanelButton.setContentAreaFilled(false);
	  panDepend.add(deleteDependentPanelButton);
	  
	  //This adds the dependent panel to the interface dynamically
	  panDependArea.add(panDepend);
	  panDependArea.repaint();
	  panDependArea.revalidate();
	  dependList.add(panDepend);
	  
	  //When clicking delete, dependent panel is removed
	  deleteDependentPanelButton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	  panDependArea.remove(panDepend);
	    	  panDependArea.repaint();
	    	  panDependArea.revalidate();
	    	  dependList.remove(panDepend);
	      }      
	  });
	 
	  //On change of project removes all dependents
	  projectBox.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	  panDependArea.remove(panDepend);
	    	  panDependArea.repaint();
	    	  panDependArea.revalidate();
	      }      
	  });
  }
  
  public void editDepend(int selectedIndex){
	  
	  final List<Project> projects = ProjectDB.getUserProjects(connectionString, user.getId());
	  final JPanel panDepend = new JPanel();
	  panDepend.setBackground(Color.white);
	  panDepend.setPreferredSize(new Dimension(310, 60));
	  final List<Activity> activities = ActivityDB.getProjectActivities(projects.get(projectBox.getSelectedIndex()).getId());
	  final Vector<String> activitiesNames = new Vector<String>();
	
	  for(Activity activity: activities){
		  activitiesNames.add(activity.getName());
	  }
	  dependBox = new JComboBox<String>(activitiesNames);
	  dependBox.setSelectedIndex(selectedIndex);
	  panDepend.setBorder(BorderFactory.createTitledBorder("Depends on..."));
	  dependLabel = new JLabel("Select Activity:");
	  panDepend.add(dependLabel);
	  panDepend.add(dependBox);
	  
	  JButton deleteDependentPanelButton = new JButton(deleteIcon);
	  deleteDependentPanelButton.setBorder(BorderFactory.createEmptyBorder());
	  deleteDependentPanelButton.setContentAreaFilled(false);
	  panDepend.add(deleteDependentPanelButton);
	  
	  //This adds the dependent panel to the interface dynamically
	  panDependArea.add(panDepend);
	  panDependArea.repaint();
	  panDependArea.revalidate();
	  dependList.add(panDepend);
	  
	  //When clicking delete, dependent panel is removed
	  deleteDependentPanelButton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	  panDependArea.remove(panDepend);
	    	  panDependArea.repaint();
	    	  panDependArea.revalidate();
	    	  dependList.remove(panDepend);
	      }      
	  });
	 
	  //On change of project removes all dependents
	  projectBox.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	  panDependArea.remove(panDepend);
	    	  panDependArea.repaint();
	    	  panDependArea.revalidate();
	      }      
	  });
  }
}
