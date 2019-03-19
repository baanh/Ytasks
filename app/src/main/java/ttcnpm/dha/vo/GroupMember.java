package ttcnpm.dha.vo;

public class GroupMember {

	GroupJob groupJob;
	User User;

	public GroupMember() {
		super();
	}

	public GroupMember(GroupJob groupJob, ttcnpm.dha.vo.User user) {
		super();
		this.groupJob = groupJob;
		User = user;
	}

	public GroupJob getGroupJob() {
		return groupJob;
	}

	public void setGroupJob(GroupJob groupJob) {
		this.groupJob = groupJob;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

}
