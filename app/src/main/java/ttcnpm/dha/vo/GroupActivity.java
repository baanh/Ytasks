package ttcnpm.dha.vo;

import java.util.Date;

public class GroupActivity {

	private int id;
	GroupJob groupJob;
	Task task;
	User user;
	Date time;
	String acitvity;

	public GroupActivity(int id, GroupJob groupJob, Task task, User user,
			Date time, String acitvity) {
		super();
		this.id = id;
		this.groupJob = groupJob;
		this.task = task;
		this.user = user;
		this.time = time;
		this.acitvity = acitvity;
	}

	public GroupActivity() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GroupJob getGroupJob() {
		return groupJob;
	}

	public void setGroupJob(GroupJob groupJob) {
		this.groupJob = groupJob;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getAcitvity() {
		return acitvity;
	}

	public void setAcitvity(String acitvity) {
		this.acitvity = acitvity;
	}

}
