package ttcnpm.dha.adapter;

import java.util.Date;

import ttcnpm.dha.dao.DatabaseConstruction;
import ttcnpm.dha.ytasks.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class YtaskCursorAdapter extends CursorAdapter {
	
	@SuppressWarnings("deprecation")
	public YtaskCursorAdapter(Context context, Cursor cursor) {
		super(context, cursor);
	}

	@Override
	public int getCount() {
		return this.getCursor().getCount();
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		TextView txtPosition = (TextView) convertView
				.findViewById(R.id.textView_TaskList_name);
		txtPosition.setText(cursor.getString(cursor
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_TITLE)));

		TextView txtDueday = (TextView) convertView.findViewById(R.id.textView_DueDay);
        Date tempDueday = new Date(cursor.getString(cursor
				.getColumnIndex(DatabaseConstruction.COLUMN_TASK_DUE_DATE)));          
        String str = String.format("%s %tB %<te, %<tY", 
                "Due date: \n", tempDueday);
        txtDueday.setText(str); 
        convertView.findViewById(R.id.imgBtn_folder).setFocusable(false);
                
		
		convertView.findViewById(R.id.imgBtn_folder).setFocusable(false);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.folder_item, parent, false);
		return view;
	}
}
