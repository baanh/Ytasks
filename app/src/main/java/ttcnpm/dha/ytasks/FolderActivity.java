package ttcnpm.dha.ytasks;

import ttcnpm.dha.adapter.YtaskCursorAdapter;
import ttcnpm.dha.constants.Constants;
import ttcnpm.dha.dao.TaskDAO;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FolderActivity extends Activity {

	private final int NEW_TASKLIST_REQUEST_CODE = 1;
	private final int NEW_TASK_REQUEST_CODE = 1;
	private final String TASK_ID_STRING = Constants.TASK_ID_STRING;

	private ListView taskList;
	private YtaskCursorAdapter taskListCursorAdapter;
	private TaskDAO taskDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_folder);
		// Show the Up button in the action bar.
		setupActionBar();

		taskList = (ListView) findViewById(R.id.folderView);

		// set adapter
		taskDbHelper = new TaskDAO(this);
		taskDbHelper.open();
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				taskListCursorAdapter = new YtaskCursorAdapter(
						FolderActivity.this, taskDbHelper.getAllTask());
				taskList.setAdapter(taskListCursorAdapter);
			}
		});

		taskList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				Intent intent = new Intent(FolderActivity.this,
						TaskActivity.class);
				intent.putExtra(TASK_ID_STRING, (int) id);
				startActivityForResult(intent,NEW_TASK_REQUEST_CODE);
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
		getMenuInflater().inflate(R.menu.folder, menu);
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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void CreateNewTask(MenuItem i) {	
			new AlertDialog.Builder(FolderActivity.this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Choose type")
	        .setItems(R.array.task_type_array, new DialogInterface.OnClickListener(){
	        	@Override
	            public void onClick(DialogInterface dialog, int which) {
	        		switch (which) {
					case 0:
						  Intent intentNewPersonalTask = new Intent(FolderActivity.this, NewTaskActivity.class);
			                startActivityForResult(intentNewPersonalTask, NEW_TASKLIST_REQUEST_CODE);
						break;
					case 1: 
						  Intent intentGrouptask = new Intent(FolderActivity.this, NewCheckListActivity.class);
			                startActivityForResult(intentGrouptask, NEW_TASKLIST_REQUEST_CODE);
			                break;
					default:
						break;
					}
	          
	            }
	        })      	   
	        .setNegativeButton("Cancel", null)
	        .show();			
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode== RESULT_OK){
		//if (requestCode == NEW_TASKLIST_REQUEST_CODE && resultCode == RESULT_OK) {
			taskListCursorAdapter.changeCursor(taskDbHelper.getAllTask());
			taskList.setAdapter(taskListCursorAdapter);
		}
	}

}
