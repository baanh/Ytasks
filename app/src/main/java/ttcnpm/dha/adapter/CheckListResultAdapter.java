/**
 * 
 */
package ttcnpm.dha.adapter;

import ttcnpm.dha.dao.DatabaseConstruction;
import ttcnpm.dha.ytasks.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * @author baanh 
 * Adapter for list view displaying result after search check list
 *         by category
 */
public class CheckListResultAdapter extends CursorAdapter {

	public CheckListResultAdapter(Context context, Cursor c) {
		super(context, c, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView checkName = (TextView) view
				.findViewById(R.id.result_check_list_name);
		checkName.setText(cursor.getString(cursor
				.getColumnIndex(DatabaseConstruction.COLUMN_CHECK_LIST_NAME)));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		return inflater.inflate(R.layout.check_list_item, parent, false);
	}

}
