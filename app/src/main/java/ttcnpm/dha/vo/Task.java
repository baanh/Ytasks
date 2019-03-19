package ttcnpm.dha.vo;

import java.util.Date;

public class Task {

	private int id;
	private String title;
	private String description;
	private TaskType taskType;
	private Date startDate;
	private Date dueDate;
	private String status;
	private String priority;
	private String progress;
	private int estimateTime;
	private int spendTime;
	private int parentTask;
	private String location;
	private String assignee;
	private Date alert;

	public Task() {

	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Task(String title, String description, TaskType typeID,
			Date startDate, Date dueDate, String status, String priority,
			String progress, int estimateTime, int spendTime, int parentTask,
			String location, Date alert) {
		super();
		this.title = title;
		this.description = description;
		this.taskType = typeID;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.status = status;
		this.priority = priority;
		this.progress = progress;
		this.estimateTime = estimateTime;
		this.spendTime = spendTime;
		this.parentTask = parentTask;
		this.location = location;
		this.alert = alert;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public int getEstimateTime() {
		return estimateTime;
	}

	public void setEstimateTime(int estimateTime) {
		this.estimateTime = estimateTime;
	}

	public int getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(int spendTime) {
		this.spendTime = spendTime;
	}

	public int getParentTask() {
		return parentTask;
	}

	public void setParentTask(int parentTask) {
		this.parentTask = parentTask;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getAlert() {
		return alert;
	}

	public void setAlert(Date alert) {
		this.alert = alert;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
	
	

}
