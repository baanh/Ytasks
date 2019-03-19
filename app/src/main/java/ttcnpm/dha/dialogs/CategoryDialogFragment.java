/**
 * 
 */
package ttcnpm.dha.dialogs;

import ttcnpm.dha.constants.Constants;
import ttcnpm.dha.dao.DatabaseConstruction;
import ttcnpm.dha.dao.UserCheckListDAO;
import ttcnpm.dha.vo.Category;
import ttcnpm.dha.ytasks.NewCheckListActivity;
import ttcnpm.dha.ytasks.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;

/**
 * @author baanh
 * 
 */
public class CategoryDialogFragment extends DialogFragment {
	private final String FRAGMENT_NEW_CATELOGY_TAG = Constants.FRAGMENT_NEW_CATELOGY;
	private EditText jobCategory;
	private String selectedItem = "";
	private UserCheckListDAO check;
	private AlertDialog.Builder builder;
	private Cursor curAllCategoryName;
	private static int pos = 0;
	private Category cate;
	private CreateNewCategoryDialog dialog;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		builder = new AlertDialog.Builder(getActivity());
		check = new UserCheckListDAO(getActivity());

		builder.setTitle("Choose the category");
		check.open();
		curAllCategoryName = check.getAllCategotyName();

		if (curAllCategoryName.moveToFirst()) {
			builder.setSingleChoiceItems(curAllCategoryName, pos,
					DatabaseConstruction.COLUMN_CATEGORY_NAME,
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							pos = which;
						}
					});

			builder.setPositiveButton("OK", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					curAllCategoryName.moveToPosition(pos);
					cate = check.getCategory(curAllCategoryName
							.getInt(curAllCategoryName.getColumnIndex("_id")));
					selectedItem = curAllCategoryName.getString(curAllCategoryName
							.getColumnIndex(DatabaseConstruction.COLUMN_CATEGORY_NAME));
					jobCategory = (EditText) getActivity().findViewById(
							R.id.editJobCategory);
					jobCategory.setText(selectedItem);
					NewCheckListActivity newchecklistactivity = (NewCheckListActivity) getActivity();
					newchecklistactivity.onUserSelectedCatagory(cate);
				}
			});

			builder.setNegativeButton("Create new",  new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					createCategory();
				}
			});

		} else { // there's no category
			builder.setSingleChoiceItems(new String[] { "Create new" }, 0, null)
					.setPositiveButton("OK", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							createCategory();
						}
					}).setNegativeButton("Cancel", null);
		}

		return builder.create();
	}

	private void createCategory() {
		dialog = new CreateNewCategoryDialog();
		FragmentManager fm = getFragmentManager();
		dialog.show(fm, FRAGMENT_NEW_CATELOGY_TAG);
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		//
		// }
		// }).start();
	}
}
