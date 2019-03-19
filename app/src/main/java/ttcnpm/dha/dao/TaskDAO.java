package ttcnpm.dha.dao;

import java.util.Date;

import ttcnpm.dha.vo.Task;
import ttcnpm.dha.vo.TaskType;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TaskDAO {
	// Database fields
	private SQLiteDatabase database;

	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	public DatabaseConstruction getDbHelper() {
		return dbHelper;
	}

	public void setDbHelper(DatabaseConstruction dbHelper) {
		this.dbHelper = dbHelper;
	}

	private DatabaseConstruction dbHelper;

	public TaskDAO() {
		super();
	}

	public TaskDAO(Context context) {
		dbHelper = new DatabaseConstruction(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public long createTask(Task newTask) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_TASK_ALERT, newTask.getAlert()
				.toString());
		values.put(DatabaseConstruction.COLUMN_TASK_DESCRIPTION,
				newTask.getDescription());
		values.put(DatabaseConstruction.COLUMN_TASK_DUE_DATE, newTask
				.getDueDate().toString());
		values.put(DatabaseConstruction.COLUMN_TASK_ESTIMATE_TIME, newTask
				.getDueDate().toString());
		values.put(DatabaseConstruction.COLUMN_TASK_LOCATION,
				newTask.getLocation());
		values.put(DatabaseConstruction.COLUMN_TASK_PARENT_TASK,
				newTask.getParentTask());
		values.put(DatabaseConstruction.COLUMN_TASK_PRIORITY,
				newTask.getPriority());
		values.put(DatabaseConstruction.COLUMN_TASK_PROGRESS,
				newTask.getProgress());
		values.put(DatabaseConstruction.COLUMN_TASK_SPEND_TIME,
				String.valueOf(newTask.getSpendTime()));
		values.put(DatabaseConstruction.COLUMN_TASK_ESTIMATE_TIME,
				newTask.getEstimateTime());
		values.put(DatabaseConstruction.COLUMN_TASK_START_DATE, newTask
				.getStartDate().toString());
		values.put(DatabaseConstruction.COLUMN_TASK_ASSIGNEE, newTask
				.getAssignee());
		values.put(DatabaseConstruction.COLUMN_TASK_STATUS, newTask.getStatus());
		values.put(DatabaseConstruction.COLUMN_TASK_TITLE, newTask.getTitle());
		long result = database.insert(DatabaseConstruction.TABLE_TASK, null,
				values);
		return result;
	}

	public boolean updateTask(Task newTask, int taskID) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_TASK_ALERT, newTask.getAlert()
				.toString());
		values.put(DatabaseConstruction.COLUMN_TASK_DESCRIPTION,
				newTask.getDescription());
		values.put(DatabaseConstruction.COLUMN_TASK_DUE_DATE, newTask
				.getDueDate().toString());
		values.put(DatabaseConstruction.COLUMN_TASK_ESTIMATE_TIME, newTask
				.getDueDate().toString());
		values.put(DatabaseConstruction.COLUMN_TASK_LOCATION,
				newTask.getLocation());
		values.put(DatabaseConstruction.COLUMN_TASK_PARENT_TASK,
				newTask.getParentTask());
		values.put(DatabaseConstruction.COLUMN_TASK_PRIORITY,
				newTask.getPriority());
		values.put(DatabaseConstruction.COLUMN_TASK_PROGRESS,
				newTask.getProgress());
		values.put(DatabaseConstruction.COLUMN_TASK_SPEND_TIME,
				newTask.getSpendTime());
		values.put(DatabaseConstruction.COLUMN_TASK_ESTIMATE_TIME,
				newTask.getEstimateTime());
		values.put(DatabaseConstruction.COLUMN_TASK_START_DATE, newTask
				.getStartDate().toString());
		values.put(DatabaseConstruction.COLUMN_TASK_ASSIGNEE, newTask
				.getAssignee());
		values.put(DatabaseConstruction.COLUMN_TASK_STATUS, newTask.getStatus());
		values.put(DatabaseConstruction.COLUMN_TASK_TITLE, newTask.getTitle());
		int result = database.update(DatabaseConstruction.TABLE_TASK, values,
				DatabaseConstruction.COLUMN_TASK_ID + "=" + taskID, null);
		return (result > 0);
	}
	public boolean updateTask(String Col, int taskID, String newValue) {
		ContentValues values = new ContentValues();
		values.put(Col, newValue);		
		int result = database.update(DatabaseConstruction.TABLE_TASK, values,
				DatabaseConstruction.COLUMN_TASK_ID + "=" + taskID, null);
		return (result > 0);
	}

	public boolean deleteTask(int taskID) {
		int result = database.delete(DatabaseConstruction.TABLE_TASK,
				DatabaseConstruction.COLUMN_TASK_ID + "=" + taskID, null);
		return (result > 0);
	}

	public TaskType getTaskType(int typeID) {
		Cursor result = database.query(DatabaseConstruction.TABLE_TASK_TYPE,
				null, DatabaseConstruction.COLUMN_TASK_TYPE_ID + "=" + typeID,
				null, null, null, null);
		result.moveToFirst();
		TaskType type = new TaskType();
		type.setId(typeID);
		type.setName(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_TYPE_NAME)));
		type.setDescription(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_TYPE_DESCRIPTION)));
		return type;
	}

	public Task getTask(int taskID, SQLiteDatabase database) {
		Cursor result = database.query(DatabaseConstruction.TABLE_TASK, null,
				DatabaseConstruction.COLUMN_TASK_ID + "=" + taskID, null, null,
				null, null);
		result.moveToFirst();

		Task task = new Task();
		task.setId(taskID);
		task.setAlert(new Date((result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_ALERT)))));
		task.setDescription(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_DESCRIPTION)));
		task.setDueDate(new Date(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_DUE_DATE))));
		task.setEstimateTime(result.getInt(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_ESTIMATE_TIME)));
		task.setId(taskID);
		task.setLocation(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_LOCATION)));
		task.setParentTask(result.getInt(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_PARENT_TASK)));
		task.setPriority(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_PRIORITY)));
		task.setProgress(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_PROGRESS)));
		Log.d("spend time", result.getString(result.getColumnIndex(DatabaseConstruction.COLUMN_TASK_SPEND_TIME)));
		task.setSpendTime(Integer.parseInt(result.getString(result.getColumnIndex(DatabaseConstruction.COLUMN_TASK_SPEND_TIME))));
		task.setStartDate(new Date(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_START_DATE))));
		task.setStatus(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_STATUS)));
		//error : lack of task type columm
//		task.setTaskType(getTaskType(result.getInt(result
//				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_TYPE_ID))));
		task.setTitle(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_TITLE)));
		return task;
	}

	public Cursor getAllTask() {
		String buildSQL = "SELECT * FROM " + DatabaseConstruction.TABLE_TASK
				+ " ORDER BY " + DatabaseConstruction.COLUMN_TASK_DUE_DATE;
		return database.rawQuery(buildSQL, null);
	}

}
