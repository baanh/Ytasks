package ttcnpm.dha.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import ttcnpm.dha.adapter.GroupJobAdapter;
import ttcnpm.dha.dao.GroupJobDAO;
import ttcnpm.dha.vo.GroupJob;
import ttcnpm.dha.ytasks.GroupJobActivity;
import ttcnpm.dha.ytasks.R;

public class CreateNewGroupDialog extends DialogFragment {

	public GroupJobActivity getGroupJobActivity() {
		return groupJobActivity;
	}

	public void setGroupJobActivity(GroupJobActivity groupJobActivity) {
		this.groupJobActivity = groupJobActivity;
	}

	private GroupJobActivity groupJobActivity;

	public CreateNewGroupDialog() {
		super();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		final View view = inflater.inflate(R.layout.new_group_job_pop_up, null);
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(view)
				// Add action buttons
				.setPositiveButton("Save",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								EditText txtGroupName = (EditText) view
										.findViewById(R.id.txtGroupName);
								EditText txtGroupDescription = (EditText) view
										.findViewById(R.id.txtGroupDescription);
								GroupJobDAO groupJobDAO = new GroupJobDAO(view
										.getContext());
								groupJobDAO.open();
								GroupJob newGroup = new GroupJob();
								newGroup.setName(txtGroupName.getText()
										.toString());
								newGroup.setDescription(txtGroupDescription
										.getText().toString());
								groupJobDAO.createGroupJob(newGroup);
								groupJobDAO.close();
								groupJobActivity.finish();
								groupJobActivity.startActivity(groupJobActivity
										.getIntent());
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								CreateNewGroupDialog.this.getDialog().cancel();
							}
						});
		return builder.create();
	}

}
