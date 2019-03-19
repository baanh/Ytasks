/**
 * 
 */
package ttcnpm.dha.dialogs;

import ttcnpm.dha.dao.UserCheckListDAO;
import ttcnpm.dha.vo.Category;
import ttcnpm.dha.ytasks.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager.OnDismissListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * @author baanh
 * 
 */
public class CreateNewCategoryDialog extends DialogFragment{
	private AlertDialog.Builder builder;
	private UserCheckListDAO dao;
	private Category newCate;
	private EditText cateName, cateDesc;
	private String name, desc;
	private boolean isCreate;
	private int id;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_new_category, null);
		builder.setView(view);

		cateName = (EditText) view.findViewById(R.id.cateName);
		cateDesc = (EditText) view.findViewById(R.id.cateDesc);
		dao = new UserCheckListDAO(getActivity());
		
		if (!isCreate) {
			dao.open();
			newCate = dao.getCategory(id);
			cateName.setText(newCate.getName());
			cateDesc.setText(newCate.getDescription());
			dao.close();
		}

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				name = cateName.getText().toString();
				desc = cateDesc.getText().toString();
				
				if (name.isEmpty() || desc.isEmpty()) {
					return;
				}
				
				dao.open();

				if (isCreate){
					newCate = new Category(name, desc);
					dao.createCategory(newCate);
				}					
				else {
					newCate.setName(name);
					newCate.setDescription(desc);
					newCate.setId(id);
					dao.updateCategory(newCate);
				}		

				dao.close();
				update();
			}
		});

		builder.setNegativeButton("Cancel", null);

		return builder.create();
	}

	public void setCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void update(){		
	}
}
