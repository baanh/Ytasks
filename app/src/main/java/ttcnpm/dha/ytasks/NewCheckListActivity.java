/**
 * 
 */
package ttcnpm.dha.ytasks;

import ttcnpm.dha.constants.Constants;
import ttcnpm.dha.dao.UserCheckListDAO;
import ttcnpm.dha.dialogs.CategoryDialogFragment;
import ttcnpm.dha.vo.Category;
import ttcnpm.dha.vo.CheckList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author baanh
 * 
 */
public class NewCheckListActivity extends Activity {
	private final String FRAGMENT_CATELOGY_TAG = Constants.FRAGMENT_CATELOGY;

	private EditText nameField;
	private EditText descField;
	private EditText cateField;
	private Button btnOk;
	private DialogFragment dialog;
	private Category cate;
	private UserCheckListDAO db;
	private int cateID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_check_list);

		nameField = (EditText) findViewById(R.id.editJobName);
		descField = (EditText) findViewById(R.id.editJobDescription);
		cateField = (EditText) findViewById(R.id.editJobCategory);
		btnOk = (Button) findViewById(R.id.btnCreateNewCheckList);
		dialog = new CategoryDialogFragment();
		db = new UserCheckListDAO(this);
		
		Intent intent = getIntent();
		cateID = intent.getIntExtra(Constants.CATEGORY_ID, -1);
		
		cateField.setFocusable(false);
		cateField.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.show(getFragmentManager(), FRAGMENT_CATELOGY_TAG);
			}
		});

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = nameField.getText().toString();
				String desc = descField.getText().toString();
				cate = new Category();
				cate.setId((int)cateID);
				if (cate != null)
					onSaveNewCheckList(name, desc, cate);
				else {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							NewCheckListActivity.this);
					alertDialogBuilder.setPositiveButton("OK", null);					
					alertDialogBuilder
							.setMessage("Please choose or create a category");
					alertDialogBuilder.create().show();
				}
			}
		});
	}

	public void onSaveNewCheckList(String name, String desc, Category cate) {
		CheckList newCheckList = new CheckList();
		
		newCheckList.setName(name);
		newCheckList.setDescription(desc);
		newCheckList.setCategory(cate);
		newCheckList.setUserID(1);

		db.open();
		db.createCheckList(newCheckList);
		db.close();
		finish();
	}

	public void onUserSelectedCatagory(Category cate) {
		this.cate = cate;
	}
}
