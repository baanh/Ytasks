package ttcnpm.dha.ytasks;

import java.util.List;
import ttcnpm.dha.adapter.GroupJobAdapter;
import ttcnpm.dha.dao.*;
import ttcnpm.dha.dialogs.CreateNewGroupDialog;
import ttcnpm.dha.vo.*;
import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GroupJobActivity extends Activity {

	private ListView listView;
	private Button btnCreateNewGroup;
	private GroupJobDAO groupJobDAO;
	private GroupJobAdapter arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_job);
		listView = (ListView) findViewById(R.id.listGroupJob);
		btnCreateNewGroup = (Button) findViewById(R.id.btnCreateNewGroup);

		groupJobDAO = new GroupJobDAO(this);
		groupJobDAO.open();
		List<GroupJob> listGroupJob = groupJobDAO.getAllGroupJob();
		groupJobDAO.close();
		arrayAdapter = new GroupJobAdapter(this, listGroupJob);
		listView.setAdapter(arrayAdapter);

		btnCreateNewGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				FragmentManager fm = getFragmentManager();
				CreateNewGroupDialog dialog = new CreateNewGroupDialog();
				dialog.setGroupJobActivity(GroupJobActivity.this);
				dialog.show(fm, "Create new group");
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView txtView = (TextView) findViewById(R.id.txtJobItem);
				String itemId = txtView.getTag().toString();
				String project = txtView.getText().toString();

				Toast.makeText(getApplicationContext(),
						"Group Job ID: " + itemId + " Project: " + project,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}