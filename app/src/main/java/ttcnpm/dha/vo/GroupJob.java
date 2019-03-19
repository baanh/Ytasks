package ttcnpm.dha.vo;

public class GroupJob {
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private int id;
	private String name;
	private String description;

	public GroupJob(int id, String name, String project) {
		super();
		this.id = id;
		this.name = name;
		this.description = project;
	}

	public GroupJob() {
		super();
	}

}
