package ttcnpm.dha.vo;

import java.util.List;

public class GroupTask {

	GroupJob groupJob;
	List<Task> listTask;
	User assignee;
	User created_user;

	public GroupTask(GroupJob groupJob, List<Task> listTask, User assignee,
			User created_user) {
		super();
		this.groupJob = groupJob;
		this.listTask = listTask;
		this.assignee = assignee;
		this.created_user = created_user;
	}

	public GroupTask() {
		super();
	}

	public GroupJob getGroupJob() {
		return groupJob;
	}

	public void setGroupJob(GroupJob groupJob) {
		this.groupJob = groupJob;
	}

	public List<Task> getListTask() {
		return listTask;
	}

	public void setListTask(List<Task> listTask) {
		this.listTask = listTask;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public User getCreated_user() {
		return created_user;
	}

	public void setCreated_user(User created_user) {
		this.created_user = created_user;
	}

}
