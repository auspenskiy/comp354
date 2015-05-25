package model;

import java.util.Date;

public class Activity
{
	private int 	id;
	private int 	projectId;
	private String 	name;
	private Date 	dueDate = new Date();
	private int 	status = 1;
	public String[] statusArray = new String[]{"To do", "In Progress", "Completed"};
	
	
	public Activity(int id, int projectId, String name, Date dueDate, int status)
	{
		super();
		this.id = id;
		this.projectId = projectId;
		this.name = name;
		this.dueDate = dueDate;
		this.status = status;
	}
	
	public String getStatusName() {
		return statusArray[status];
	}

	public int getActivityId() {
		return id;
	}

	public void setActivityId(int activityId) {
		this.id = activityId;
	}

	public String getActivityName() {
		return name;
	}

	public void setActivityName(String activityName) {
		this.name = activityName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		if (status >= 0 && status < statusArray.length)
			this.status = status;
		else
			this.status = 0;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getAssociatedProjectId() {
		return projectId;
	}

	public void setAssociatedProjectId(int associatedProjectId) {
		this.projectId = associatedProjectId;
	}
	
	
}
