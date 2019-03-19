package ttcnpm.dha.ytasks;

import ttcnpm.dha.adapter.CurAdapterCategory;
import ttcnpm.dha.constants.Constants;
import ttcnpm.dha.dao.UserCheckListDAO;
import ttcnpm.dha.dialogs.CreateNewCategoryDialog;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class CategoryActivity extends Activity {
	private final String CATEGORY_ID = Constants.CATEGORY_ID;
	private final String FRAGMENT_NEW_CATELOGY_TAG = Constants.FRAGMENT_NEW_CATELOGY;
	private GridView gridView;
	private Button btnCreate;
	private UserCheckListDAO db;
	private Cursor allCatagory;
	private CurAdapterCategory adapter;

	@Override
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);

		prepare();

		new Thread(new Runnable() {
			@Override
			public void run() {
				init();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				function();
			}
		}).start();
	}

	private void prepare() {
		gridView = (GridView) findViewById(R.id.gvCategory);
		btnCreate = (Button) findViewById(R.id.btnCreate);
		db = new UserCheckListDAO(this);
		adapter = new CurAdapterCategory(this, allCatagory);		
		registerForContextMenu(gridView);

		// for testing: clean old database
//		deleteDatabase("ytasks.db");
	}

	private void init() {
		db.open();
		allCatagory = db.getAllCategotyName();
		adapter.changeCursor(allCatagory);
		gridView.setAdapter(adapter);
		if (allCatagory.moveToFirst()) {
			gridView.setVisibility(View.VISIBLE);
		} else {
			btnCreate.setVisibility(View.VISIBLE);
		}
	}

	private void notifyDatasetChange() {
		allCatagory = db.getAllCategotyName();

		if (!allCatagory.moveToFirst()) {
			gridView.setVisibility(View.INVISIBLE);
			btnCreate.setVisibility(View.VISIBLE);
		} else {
			gridView.setVisibility(View.VISIBLE);
			btnCreate.setVisibility(View.INVISIBLE);
		}

		adapter.changeCursor(allCatagory);
	}

	private void function() {
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long id) {
				selectCategory((int) id);
			}
		});

		btnCreate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createCategory();
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.category_context_menu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int id = (int) adapter.getItemId(info.position);
		switch (item.getItemId()) {
		case R.id.action_create_category:
			createCategory();
			return true;
		case R.id.action_edit_category:
			updateCategory(id);
			return true;
		case R.id.action_delete_category:
			db.deleteCategory(id);
			notifyDatasetChange();
			return true;
		case R.id.action_select_category:
			selectCategory(id);
			return true;
		default:
			return false;
		}
	}

	private void selectCategory(int id) {
		// Call ChecklistActivity
		Intent intent = new Intent(this, CheckListActivity.class);
		intent.putExtra(CATEGORY_ID, id);
		startActivity(intent);
	}

	private void createCategory() {
		updateCategory(true, 0);
	}

	private void updateCategory(int id) {
		updateCategory(false, id);
	}

	private void updateCategory(boolean isCreate, int id) {
		CreateNewCategoryDialog dialog = new CreateNewCategoryDialog() {
			@Override
			public void update() {
				notifyDatasetChange();
			}
		};
		FragmentManager fm = getFragmentManager();
		dialog.setCreate(isCreate);
		dialog.setId(id);
		dialog.show(fm, FRAGMENT_NEW_CATELOGY_TAG);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.category, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.create_category:
			createCategory();
			return true;
		default:
			return false;
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

}
