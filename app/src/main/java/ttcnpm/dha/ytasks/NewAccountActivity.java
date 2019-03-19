package ttcnpm.dha.ytasks;

import ttcnpm.dha.util.DatePickerFragment;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class NewAccountActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_account);
		// Show the Up button in the action bar.
		setupActionBar();
		findViewById(R.id.btnSaveNewAccount).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String email = ((EditText)v.findViewById(R.id.newEmailField)).getText().toString();
				String pass = ((EditText)v.findViewById(R.id.newEmailField)).getText().toString();
				String firstname = ((EditText)v.findViewById(R.id.newEmailField)).getText().toString();
				String lastname = ((EditText)v.findViewById(R.id.newEmailField)).getText().toString();
				String birthday = ((EditText)v.findViewById(R.id.newEmailField)).getText().toString();
				String phone = ((EditText)v.findViewById(R.id.newEmailField)).getText().toString();
				String facebook = ((EditText)v.findViewById(R.id.newEmailField)).getText().toString();
				
				//TODO add new account to db
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_account, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment(){
	    				@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
				// TODO Auto-generated method stub
				super.onDateSet(view, year, month, day);
				((EditText)findViewById(R.id.editTextBirthday)).setText(day+"/"+month+"/"+year);
			}
	    	
	    };
	    newFragment.show(getFragmentManager(), "datePicker");
	}

}
