package ttcnpm.dha.ytasks;

import ttcnpm.dha.adapter.CheckListResultAdapter;
import ttcnpm.dha.constants.Constants;
import ttcnpm.dha.dao.UserCheckListDAO;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CheckListActivity extends Activity {

	private ListView resultList;
	private CheckListResultAdapter resultAdapter;
	private int cateID;
	UserCheckListDAO checkDAO;
	Cursor curCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_list_filter_result);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		cateID = intent.getIntExtra(Constants.CATEGORY_ID, -1);
		resultList = (ListView) findViewById(R.id.list_result_check_list);
		if (cateID != -1) {
			checkDAO = new UserCheckListDAO(this);
			checkDAO.open();
			curCheck = checkDAO
					.searchCheckListNameByCategory( cateID);
			resultAdapter = new CheckListResultAdapter(getApplicationContext(),
					curCheck);
			if (curCheck.moveToFirst()) {

				resultList.setAdapter(resultAdapter);
			}
		}
		
		resultList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long id) {
				Intent intent = new  Intent(CheckListActivity.this, CheckListDetailActivity.class);
				intent.putExtra(Constants.CheckListID, (int) id);
				startActivity(intent);
				
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_list_filter_result, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new_check_list:
			Intent createCheck = new Intent(getApplicationContext(),
					NewCheckListActivity.class);
			createCheck.putExtra(Constants.CATEGORY_ID, cateID);
			startActivityForResult(createCheck,
					Constants.NEW_CHECK_LIST_REQUEST);
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == Constants.NEW_CHECK_LIST_REQUEST) {
			curCheck = checkDAO
					.searchCheckListNameByCategory((int) cateID);
			resultAdapter.changeCursor(curCheck);
			resultList.setAdapter(resultAdapter);
			Toast.makeText(getApplicationContext(), "add successful",
					Toast.LENGTH_SHORT).show();
		}
		// super.onActivityResult(requestCode, resultCode, data);
	}

}
