package ttcnpm.dha.vo;

import java.util.List;

public class TaskList {
	private CheckList checkList;
	private List<Task> taskList;

	public TaskList(CheckList checkList, List<Task> taskList) {
		super();
		this.checkList = checkList;
		this.taskList = taskList;
	}

	public TaskList() {
		// TODO Auto-generated constructor stub
	}

	public CheckList getCheckList() {
		return checkList;
	}

	public void setCheckList(CheckList checkList) {
		this.checkList = checkList;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

}
