package ttcnpm.dha.ytasks;

import ttcnpm.dha.constants.Constants;
import ttcnpm.dha.dao.TaskDAO;
import ttcnpm.dha.dao.UserCheckListDAO;
import ttcnpm.dha.vo.Task;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.SeekBar;
import android.widget.TextView;

public class TaskActivity extends Activity {
	private final String TASK_ID_STRING = Constants.TASK_ID_STRING;
	private final String CHECK_LIST_ID_STRING = Constants.CheckListID;
	private final int EDIT_TASK_REQUEST_CODE = 2;
	int taskid;
	int checklistid;
	private TaskDAO taskDbHelper;
	Task currentTask;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		// Show the Up button in the action bar.
		setupActionBar();

		try {
			taskid = getIntent().getExtras().getInt(TASK_ID_STRING);
			checklistid=getIntent().getExtras().getInt(CHECK_LIST_ID_STRING);
			taskDbHelper = new TaskDAO(this);
			taskDbHelper.open();

			currentTask = taskDbHelper.getTask(taskid,
					taskDbHelper.getDatabase());
			((TextView) findViewById(R.id.TaskTilleField)).setText(currentTask
					.getTitle());
			((TextView) findViewById(R.id.TaskDecripField)).setText(currentTask
					.getDescription());
			((TextView) findViewById(R.id.TaskStartDay)).setText(currentTask
					.getStartDate().toString());
			((TextView) findViewById(R.id.TaskDueDay)).setText(currentTask
					.getDueDate().toString());
			((TextView) findViewById(R.id.TaskSpendTime)).setText(String
					.valueOf(currentTask.getSpendTime()));
			((TextView) findViewById(R.id.TaskSpinPriority))
					.setText(currentTask.getPriority());
			((TextView) findViewById(R.id.taskSpinStatus)).setText(currentTask
					.getStatus());
			((TextView) findViewById(R.id.txtPercent)).setText(currentTask
					.getProgress());
			((SeekBar) findViewById(R.id.seekBar1)).setProgress(Integer
					.parseInt(currentTask.getProgress()));
			final SeekBar  a = ((SeekBar) findViewById(R.id.seekBar1));
			a.setThumb(null);
			a.setEnabled(false);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// dell btn
		findViewById(R.id.btnDelTask).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(TaskActivity.this)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle("Delete")
						.setMessage("Are you sure?")
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										// Delete task
										UserCheckListDAO tasklistHelper = new UserCheckListDAO(getApplicationContext());
										tasklistHelper.open();
										tasklistHelper.deleteTaskItem(checklistid, taskid);
										tasklistHelper.close();
										taskDbHelper.deleteTask(taskid);
										TaskActivity.this.setResult(RESULT_OK);
										finish();
									}

								}).setNegativeButton("No", null).show();
			}
		});

		// edit btn
		findViewById(R.id.btnEditTask).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(TaskActivity.this,
								EditTaskActivity.class);
						intent.putExtra(TASK_ID_STRING, (int) taskid);
						startActivityForResult(intent, EDIT_TASK_REQUEST_CODE);
					}
				});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		try {
			taskid = getIntent().getExtras().getInt(TASK_ID_STRING);
			taskDbHelper = new TaskDAO(this);
			taskDbHelper.open();

			currentTask = taskDbHelper.getTask(taskid,
					taskDbHelper.getDatabase());
			((TextView) findViewById(R.id.TaskTilleField)).setText(currentTask
					.getTitle());
			((TextView) findViewById(R.id.TaskDecripField)).setText(currentTask
					.getDescription());
			((TextView) findViewById(R.id.TaskStartDay)).setText(currentTask
					.getStartDate().toString());
			((TextView) findViewById(R.id.TaskDueDay)).setText(currentTask
					.getDueDate().toString());
			((TextView) findViewById(R.id.TaskSpendTime)).setText(String
					.valueOf(currentTask.getSpendTime()));
			((TextView) findViewById(R.id.TaskSpinPriority))
					.setText(currentTask.getPriority());
			((TextView) findViewById(R.id.taskSpinStatus)).setText(currentTask
					.getStatus());
			((TextView) findViewById(R.id.txtPercent)).setText(currentTask
					.getProgress());
			((SeekBar) findViewById(R.id.seekBar1)).setProgress(Integer
					.parseInt(currentTask.getProgress()));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
