package ttcnpm.dha.dao;

import java.util.ArrayList;
import java.util.List;

import ttcnpm.dha.vo.Category;
import ttcnpm.dha.vo.CheckList;
import ttcnpm.dha.vo.Task;
import ttcnpm.dha.vo.TaskList;
import ttcnpm.dha.vo.TaskType;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserCheckListDAO {
	// Database fields
	private SQLiteDatabase database;
	private DatabaseConstruction dbHelper;

	public UserCheckListDAO(Context context) {
		dbHelper = new DatabaseConstruction(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public boolean createCategory(Category cate) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_CATEGORY_NAME, cate.getName());
		values.put(DatabaseConstruction.COLUMN_CATEGORY_DESCRIPTION,
				cate.getDescription());
		long result = database.insert(DatabaseConstruction.TABLE_CATEGORY,
				null, values);

		return (result > 0);
	}

	public boolean updateCategory(Category cate) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_CATEGORY_NAME, cate.getName());
		values.put(DatabaseConstruction.COLUMN_CATEGORY_DESCRIPTION,
				cate.getDescription());
		int result = database.update(DatabaseConstruction.TABLE_CATEGORY,
				values,
				DatabaseConstruction.COLUMN_CATEGORY_ID + "=" + cate.getId(),
				null);

		return (result > 0);
	}

	public boolean deleteCategory(int categoryID) {
		int result = database.delete(DatabaseConstruction.TABLE_CATEGORY,
				DatabaseConstruction.COLUMN_CATEGORY_ID + "=" + categoryID,
				null);
		return (result > 0);
	}

	public boolean deleteCheckList(int checkListID) {
		int result = database.delete(DatabaseConstruction.TABLE_CHECK_LIST,
				DatabaseConstruction.COLUMN_CHECK_LIST_ID + "=" + checkListID,
				null);
		return (result > 0);
	}

	public boolean deleteTaskItem(int listID, int taskID) {
		int result = database.delete(DatabaseConstruction.TABLE_TASK_LIST,
				DatabaseConstruction.COLUMN_TASK_LIST_ID + "=" + listID
						+ " and "
						+ DatabaseConstruction.COLUMN_TASK_LIST_ITEM_ID + "="
						+ taskID, null);
		return (result > 0);
	}

	public boolean createCheckList(CheckList newCheckList) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_CHECK_LIST_USER_ID,
				newCheckList.getUserID());
		values.put(DatabaseConstruction.COLUMN_CHECK_LIST_NAME,
				newCheckList.getName());
		values.put(DatabaseConstruction.COLUMN_CHECK_LIST_CATEGORY_ID,
				newCheckList.getCategory().getId());
		long result = database.insert(DatabaseConstruction.TABLE_CHECK_LIST,
				null, values);

		return (result > 0);
	}

	public boolean updateCheckList(CheckList newCheckList) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_CHECK_LIST_USER_ID,
				newCheckList.getUserID());
		values.put(DatabaseConstruction.COLUMN_CHECK_LIST_CATEGORY_ID,
				newCheckList.getCategory().getId());
		long result = database.update(DatabaseConstruction.TABLE_CHECK_LIST,
				values, DatabaseConstruction.COLUMN_CHECK_LIST_ID + "="
						+ newCheckList.getId(), null);

		return (result > 0);
	}

	public boolean createTaskType(TaskType type) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_TASK_TYPE_NAME, type.getName());
		values.put(DatabaseConstruction.COLUMN_TASK_TYPE_DESCRIPTION,
				type.getDescription());
		long result = database.insert(DatabaseConstruction.TABLE_TASK_TYPE,
				null, values);

		return (result > 0);

	}

	public boolean createTaskList(long listID, long taskID) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_TASK_LIST_ID, listID);
		values.put(DatabaseConstruction.COLUMN_TASK_LIST_ITEM_ID, taskID);
		long result = database.insert(DatabaseConstruction.TABLE_TASK_LIST,
				null, values);

		return (result > 0);
	}

	public List<CheckList> getUserCheckLists(int userID) {
		List<CheckList> lists = new ArrayList<CheckList>();
		Cursor result = database.query(DatabaseConstruction.TABLE_CHECK_LIST,
				null, DatabaseConstruction.COLUMN_CHECK_LIST_USER_ID + "="
						+ userID, null, null, null,
				DatabaseConstruction.COLUMN_CHECK_LIST_ID + " ASC");
		while (result.moveToNext()) {
			int id = result.getInt(result
					.getColumnIndex(DatabaseConstruction.COLUMN_CHECK_LIST_ID));
			int categoryID = result
					.getInt(result
							.getColumnIndex(DatabaseConstruction.COLUMN_CHECK_LIST_CATEGORY_ID));
			CheckList checkList = new CheckList();
			checkList.setId(id);
			checkList.setCategory(getCategory(categoryID));
			checkList.setUserID(userID);
			lists.add(checkList);
		}
		result.close();
		return lists;
	}

	public CheckList getCheckListByID(int checkListID) {
		CheckList checkList = new CheckList();
		Cursor result = database.query(DatabaseConstruction.TABLE_CHECK_LIST,
				null, DatabaseConstruction.COLUMN_CHECK_LIST_ID + "="
						+ checkListID, null, null, null,
				DatabaseConstruction.COLUMN_CHECK_LIST_ID + " ASC");
		while (result.moveToNext()) {
			int id = result.getInt(result
					.getColumnIndex(DatabaseConstruction.COLUMN_CHECK_LIST_ID));
			int categoryID = result
					.getInt(result
							.getColumnIndex(DatabaseConstruction.COLUMN_CHECK_LIST_CATEGORY_ID));
			String chname = result
					.getString(result
							.getColumnIndex(DatabaseConstruction.COLUMN_CHECK_LIST_NAME));
			checkList.setName(chname);
			checkList.setId(id);
			checkList.setCategory(getCategory(categoryID));
			checkList.setUserID(checkListID);
		}
		result.close();
		return checkList;
	}

	public Category getCategory(int categoryID) {
		Cursor result = database.query(DatabaseConstruction.TABLE_CATEGORY,
				null, DatabaseConstruction.COLUMN_CATEGORY_ID + "="
						+ categoryID, null, null, null, null);
		result.moveToFirst();
		Category newCate = new Category();
		newCate.setId(categoryID);
		newCate.setName(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_CATEGORY_NAME)));
		newCate.setDescription(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_CATEGORY_DESCRIPTION)));
		result.close();
		return newCate;
	}

	public Cursor getAllCategotyName() {
		Cursor result = database.query(DatabaseConstruction.TABLE_CATEGORY,
				new String[] {
						DatabaseConstruction.COLUMN_CATEGORY_ID + " as _id",
						DatabaseConstruction.COLUMN_CATEGORY_NAME }, null,
				null, null, null, null);
		return result;
	}

	public TaskList getTaskList(CheckList checkList) {
		TaskList taskList = new TaskList();
		taskList.setCheckList(checkList);
		TaskDAO taskDAO = new TaskDAO();
		List<Task> lists = new ArrayList<Task>();
		Cursor result = database.query(DatabaseConstruction.TABLE_TASK_LIST,
				null, DatabaseConstruction.COLUMN_TASK_LIST_ID + "="
						+ checkList.getId(), null, null, null,
				DatabaseConstruction.COLUMN_TASK_LIST_ITEM_ID + " ASC");
		while (result.moveToNext()) {
			Task task = taskDAO
					.getTask(
							result.getInt(result
									.getColumnIndex(DatabaseConstruction.COLUMN_TASK_LIST_ITEM_ID)),
							database);
			lists.add(task);
		}
		taskList.setTaskList(lists);
		return taskList;
	}

	public Cursor searchCheckListNameByCategory(int cateID) {
		String selection = DatabaseConstruction.COLUMN_CHECK_LIST_CATEGORY_ID
				+ "= ?";
		String[] selectionArgs = new String[] { String.valueOf(cateID) };
		String[] columns = new String[] {
				DatabaseConstruction.COLUMN_CHECK_LIST_ID + " as _id",
				DatabaseConstruction.COLUMN_CHECK_LIST_NAME };
		Cursor result = database.query(DatabaseConstruction.TABLE_CHECK_LIST,
				columns, selection, selectionArgs, null, null, null);
		return result;
	}

	public List<TaskList> getUserTaskLists(int userID) {
		List<TaskList> taskLists = new ArrayList<TaskList>();
		List<CheckList> checkLists;
		checkLists = getUserCheckLists(userID);
		for (CheckList checkList : checkLists) {
			taskLists.add(getTaskList(checkList));
		}
		return taskLists;
	}

}
