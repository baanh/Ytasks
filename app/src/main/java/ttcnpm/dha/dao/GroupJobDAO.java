package ttcnpm.dha.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ttcnpm.dha.vo.GroupActivity;
import ttcnpm.dha.vo.GroupJob;
import ttcnpm.dha.vo.GroupMember;
import ttcnpm.dha.vo.Task;
import ttcnpm.dha.vo.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class GroupJobDAO {
	// Database fields
	private SQLiteDatabase database;
	private DatabaseConstruction dbHelper;

	public GroupJobDAO() {
		super();
	}

	public GroupJobDAO(Context context) {
		dbHelper = new DatabaseConstruction(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public boolean createGroupJob(GroupJob groupJob) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_GROUP_NAME, groupJob.getName());
		values.put(DatabaseConstruction.COLUMN_GROUP_DESCRIPTION,
				groupJob.getDescription());
		long result = database.insert(DatabaseConstruction.TABLE_GROUP, null,
				values);

		return (result > 0);

	}

	public boolean createProject(GroupJob groupJob) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_GROUP_NAME, groupJob.getName());
		values.put(DatabaseConstruction.COLUMN_GROUP_DESCRIPTION,
				groupJob.getDescription());
		long result = database.insert(DatabaseConstruction.TABLE_GROUP, null,
				values);

		return (result > 0);

	}

	public boolean createGroupActivity(GroupActivity groupActivity) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_ACTIVITY,
				groupActivity.getAcitvity());
		values.put(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_GROUP_ID,
				groupActivity.getGroupJob().getId());
		values.put(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_TASK_ID,
				groupActivity.getTask().getId());
		values.put(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_USER_ID,
				groupActivity.getUser().getId());
		values.put(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_TIME,
				groupActivity.getTime().toString());

		long result = database.insert(
				DatabaseConstruction.TABLE_GROUP_ACTIVITY, null, values);

		return (result > 0);

	}

	public boolean addGroupMember(GroupMember groupMember) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_GROUP_MEMBER_ID, groupMember
				.getGroupJob().getId());
		values.put(DatabaseConstruction.COLUMN_GROUP_MEMBER_USER_ID,
				groupMember.getUser().getId());
		long result = database.insert(DatabaseConstruction.TABLE_GROUP_MEMBER,
				null, values);

		return (result > 0);

	}

	public boolean createGroupTask(int groupID, int taskID, int assignee,
			int created_user) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_GROUP_TASK_GROUP_ID, groupID);
		values.put(DatabaseConstruction.COLUMN_GROUP_TASK_TASK_ID, taskID);
		values.put(DatabaseConstruction.COLUMN_GROUP_TASK_ASSIGNEE, assignee);
		values.put(DatabaseConstruction.COLUMN_GROUP_TASK_CREATED_USER,
				created_user);
		long result = database.insert(DatabaseConstruction.TABLE_GROUP_TASK,
				null, values);

		return (result > 0);

	}

	public GroupJob getGroupJob(int groupID) {
		Cursor result = database.query(DatabaseConstruction.TABLE_GROUP, null,
				DatabaseConstruction.COLUMN_GROUP_ID + "=" + groupID, null,
				null, null, null);
		result.moveToFirst();
		GroupJob groupJob = new GroupJob();
		groupJob.setId(groupID);
		groupJob.setName(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_NAME)));
		groupJob.setName(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_DESCRIPTION)));
		return groupJob;
	}

	public List<GroupJob> getAllGroupJob() {
		Cursor result = database.query(DatabaseConstruction.TABLE_GROUP, null,
				null, null, null, null, null);
		List<GroupJob> lists = new ArrayList<GroupJob>();
		while (result.moveToNext()) {
			GroupJob groupJob = new GroupJob();
			groupJob.setId(result.getInt(result
					.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ID)));
			groupJob.setName(result.getString(result
					.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_NAME)));
			groupJob.setDescription(result.getString(result
					.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_DESCRIPTION)));
			lists.add(groupJob);

		}
		return lists;
	}

	public List<User> getGroupMember(int groupID) {
		List<User> listUser = new ArrayList<User>();
		UserDAO userDAO = new UserDAO();
		Cursor result = database.query(DatabaseConstruction.TABLE_GROUP_MEMBER,
				null, DatabaseConstruction.COLUMN_GROUP_MEMBER_ID + "="
						+ groupID, null, null, null, null);
		while (result.moveToNext()) {
			User user = userDAO.getUser(result.getInt(result
					.getColumnIndex(DatabaseConstruction.COLUMN_USER_ID)),
					database);
			listUser.add(user);
		}
		return listUser;
	}

	public List<GroupJob> getUserGroupList(int userID) {
		List<GroupJob> listGroup = new ArrayList<GroupJob>();
		Cursor result = database.query(DatabaseConstruction.TABLE_GROUP_MEMBER,
				null, DatabaseConstruction.COLUMN_GROUP_MEMBER_USER_ID + "="
						+ userID, null, null, null, null);
		while (result.moveToNext()) {
			GroupJob group = getGroupJob(result.getInt(result
					.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ID)));
			listGroup.add(group);
		}
		return listGroup;
	}

	public List<Task> getGroupTaskList(int groupID) {
		List<Task> listTask = new ArrayList<Task>();
		TaskDAO taskDAO = new TaskDAO();
		Cursor result = database.query(DatabaseConstruction.TABLE_GROUP_TASK,
				null, DatabaseConstruction.COLUMN_GROUP_TASK_GROUP_ID + "="
						+ groupID, null, null, null,
				DatabaseConstruction.COLUMN_GROUP_TASK_TASK_ID + " ASC");
		while (result.moveToNext()) {
			Task task = taskDAO
					.getTask(
							result.getInt(result
									.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_TASK_TASK_ID)),
							database);
			listTask.add(task);
		}
		return listTask;
	}

	public Map<GroupJob, List<Task>> getListGroupTaskUser(int userID) {
		Map<GroupJob, List<Task>> listGroupTaskList = new HashMap<GroupJob, List<Task>>();
		List<GroupJob> listGroupJob = getUserGroupList(userID);
		List<Task> listGroupTask;
		for (GroupJob groupJob : listGroupJob) {
			listGroupTask = getGroupTaskList(groupJob.getId());
			listGroupTaskList.put(groupJob, listGroupTask);
		}
		return listGroupTaskList;
	}

	public List<GroupActivity> getListGroupActivity(int groupID) {
		UserDAO userDAO = new UserDAO();
		TaskDAO taskDAO = new TaskDAO();
		Cursor result = database.query(
				DatabaseConstruction.TABLE_GROUP_ACTIVITY, null,
				DatabaseConstruction.COLUMN_GROUP_ACTIVITY_GROUP_ID + "="
						+ groupID, null, null, null,
				DatabaseConstruction.COLUMN_GROUP_ACTIVITY_TIME + " DESC");
		List<GroupActivity> groupActivityList = new ArrayList<GroupActivity>();
		while (result.moveToNext()) {
			GroupActivity groupActivity = new GroupActivity();
			groupActivity
					.setId(result.getInt(result
							.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_ID)));
			groupActivity
					.setAcitvity(result.getString(result
							.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_ACTIVITY)));
			groupActivity
					.setTime(Date.valueOf(result.getString(result
							.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_TIME))));

			groupActivity
					.setUser(userDAO.getUser(
							result.getInt(result
									.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_USER_ID)),
							database));
			groupActivity
					.setTask(taskDAO.getTask(
							result.getInt(result
									.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_TASK_ID)),
							database));
			groupActivity
					.setGroupJob(getGroupJob(result.getInt(result
							.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_GROUP_ID))));
			groupActivityList.add(groupActivity);
		}
		return groupActivityList;
	}

	public List<GroupActivity> getListTaskActivity(int taskID) {
		UserDAO userDAO = new UserDAO();
		TaskDAO taskDAO = new TaskDAO();
		Cursor result = database.query(
				DatabaseConstruction.TABLE_GROUP_ACTIVITY, null,
				DatabaseConstruction.COLUMN_GROUP_ACTIVITY_TASK_ID + "="
						+ taskID, null, null, null,
				DatabaseConstruction.COLUMN_GROUP_ACTIVITY_TIME + " DESC");
		List<GroupActivity> groupActivityList = new ArrayList<GroupActivity>();
		while (result.moveToNext()) {
			GroupActivity groupActivity = new GroupActivity();
			groupActivity
					.setId(result.getInt(result
							.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_ID)));
			groupActivity
					.setAcitvity(result.getString(result
							.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_ACTIVITY)));
			groupActivity
					.setTime(Date.valueOf(result.getString(result
							.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_TIME))));

			groupActivity
					.setUser(userDAO.getUser(
							result.getInt(result
									.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_USER_ID)),
							database));
			groupActivity
					.setTask(taskDAO.getTask(
							result.getInt(result
									.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_TASK_ID)),
							database));
			groupActivity
					.setGroupJob(getGroupJob(result.getInt(result
							.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_GROUP_ID))));
			groupActivityList.add(groupActivity);
		}
		return groupActivityList;
	}

	public List<GroupActivity> getListUserActivity(int userID) {
		UserDAO userDAO = new UserDAO();
		TaskDAO taskDAO = new TaskDAO();
		Cursor result = database.query(
				DatabaseConstruction.TABLE_GROUP_ACTIVITY, null,
				DatabaseConstruction.COLUMN_GROUP_ACTIVITY_USER_ID + "="
						+ userID, null, null, null,
				DatabaseConstruction.COLUMN_GROUP_ACTIVITY_TIME + " DESC");
		List<GroupActivity> groupActivityList = new ArrayList<GroupActivity>();
		while (result.moveToNext()) {
			GroupActivity groupActivity = new GroupActivity();
			groupActivity
					.setId(result.getInt(result
							.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_ID)));
			groupActivity
					.setAcitvity(result.getString(result
							.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_ACTIVITY)));
			groupActivity
					.setTime(Date.valueOf(result.getString(result
							.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_TIME))));

			groupActivity
					.setUser(userDAO.getUser(
							result.getInt(result
									.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_USER_ID)),
							database));
			groupActivity
					.setTask(taskDAO.getTask(
							result.getInt(result
									.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_TASK_ID)),
							database));
			groupActivity
					.setGroupJob(getGroupJob(result.getInt(result
							.getColumnIndex(DatabaseConstruction.COLUMN_GROUP_ACTIVITY_GROUP_ID))));
			groupActivityList.add(groupActivity);
		}
		return groupActivityList;
	}
}
