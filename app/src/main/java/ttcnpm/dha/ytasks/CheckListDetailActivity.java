package ttcnpm.dha.ytasks;

import ttcnpm.dha.adapter.TaskListAdapter;
import ttcnpm.dha.constants.Constants;
import ttcnpm.dha.dao.UserCheckListDAO;
import ttcnpm.dha.vo.CheckList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckListDetailActivity extends Activity {
	
	private final String CHECK_LIST_ID_STRING = Constants.CheckListID;
	private final String TASK_ID_STRING = Constants.TASK_ID_STRING;
	private final int NEW_TASK_REQUEST_CODE = 1;
	
	int checkListId;
	private UserCheckListDAO checkListDbHelper;
	ListView checkList;
	Button addBtn; 
	CheckList currentCheckList;
	TaskListAdapter checkListAdapter;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_list_detail);
		checkListId=getIntent().getExtras().getInt(CHECK_LIST_ID_STRING);
		//setup database helper
		checkListDbHelper = new UserCheckListDAO(this);
		checkListDbHelper.open();
		//setup head name
		//TextView currentname = (TextView) findViewById(R.id.txtCheckListName);
		//currentname.setText(checkListDbHelper.getCheckListByID(checkListId).getName());
		//setup listview
		
		setTitle(checkListDbHelper.getCheckListByID(checkListId).getName());
		
		currentCheckList = checkListDbHelper.getCheckListByID(checkListId); 
				
		checkList = (ListView) findViewById(R.id.listTaskOfCheckList);
		checkListAdapter = new TaskListAdapter(this, R.layout.task_list_item, checkListDbHelper.getTaskList(currentCheckList).getTaskList());
		
		checkList.setAdapter(checkListAdapter);
		
		checkList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				Intent intent = new Intent(CheckListDetailActivity.this,
						TaskActivity.class);
				intent.putExtra(TASK_ID_STRING, (int)id);
				intent.putExtra(CHECK_LIST_ID_STRING, (int)checkListId);
				startActivityForResult(intent,NEW_TASK_REQUEST_CODE);
			}
		});
		
		//setup add button
		findViewById(R.id.btnAddTaskOnCheckList).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(CheckListDetailActivity.this,
								NewTaskActivity.class);
						intent.putExtra(CHECK_LIST_ID_STRING, checkListId);
						startActivityForResult(intent,NEW_TASK_REQUEST_CODE);						
					}
				});		
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_list_detail, menu);
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
		if (requestCode == NEW_TASK_REQUEST_CODE) {
			checkListAdapter = new TaskListAdapter(this, R.layout.task_list_item, checkListDbHelper.getTaskList(currentCheckList).getTaskList());			
			checkList.setAdapter(checkListAdapter);
		}
	}

}
