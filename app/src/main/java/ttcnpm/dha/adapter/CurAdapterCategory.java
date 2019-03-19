package ttcnpm.dha.adapter;

import ttcnpm.dha.dao.DatabaseConstruction;
import ttcnpm.dha.ytasks.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class CurAdapterCategory extends CursorAdapter {

	@SuppressWarnings("deprecation")
	public CurAdapterCategory(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public int getCount() {
		return this.getCursor().getCount();
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		TextView txtName = (TextView) convertView
				.findViewById(R.id.txtCatagoryName);
		txtName.setText(cursor.getString(cursor
				.getColumnIndex(DatabaseConstruction.COLUMN_CATEGORY_NAME)));
		txtName.setFocusable(false);
		ImageButton imgBtn = (ImageButton) convertView.findViewById(R.id.imgBtn_catagory);
		imgBtn.setFocusable(false);
		imgBtn.setFocusableInTouchMode(false);
		imgBtn.setClickable(false);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.category_item, parent, false);
		return view;
	}
}
