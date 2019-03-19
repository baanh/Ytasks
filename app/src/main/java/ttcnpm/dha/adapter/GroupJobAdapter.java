package ttcnpm.dha.adapter;

import java.util.List;

import ttcnpm.dha.vo.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ttcnpm.dha.ytasks.R;

public class GroupJobAdapter extends ArrayAdapter<GroupJob> {

	private Context mContext;
	private List<GroupJob> groupJob;

	public GroupJobAdapter(Context mContext, List<GroupJob> groupJob) {
		super(mContext, R.layout.list_each_group_job, groupJob);
		this.mContext = mContext;
		this.groupJob = groupJob;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_each_group_job, parent,
				false);
		TextView txtView = (TextView) rowView.findViewById(R.id.txtJobItem);
		GroupJob jobItem = groupJob.get(position);
		txtView.setText(jobItem.getName() + ": " + jobItem.getDescription());
		txtView.setTag(jobItem.getId());
		return rowView;
	}
}
