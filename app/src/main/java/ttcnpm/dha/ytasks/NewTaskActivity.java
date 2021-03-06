package ttcnpm.dha.ytasks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import ttcnpm.dha.constants.Constants;
import ttcnpm.dha.dao.DatabaseConstruction;
import ttcnpm.dha.dao.TaskDAO;
import ttcnpm.dha.dao.UserCheckListDAO;
import ttcnpm.dha.service.AlarmService;
import ttcnpm.dha.service.ScheduleSMSReciever;
import ttcnpm.dha.util.DatePickerFragment;
import ttcnpm.dha.vo.Task;
import ttcnpm.dha.vo.TaskType;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewTaskActivity extends Activity {

	private final String CHECK_LIST_ID_STRING = Constants.CheckListID;
	private static final String keystring = Constants.keystring;
	private static final String keytime = Constants.keytime;
	private static final String keycreate = Constants.keycreate;
	private static final String keyid = Constants.keyid;
	private final int id = 1; // identifier of alarm
	private boolean belongToACheckList;
	private int checkListID;

	private Spinner statusOptions;
	private String currentStatus;

	private Spinner priorityOptions;
	private String currentPriority;

	private Calendar sTime = Calendar.getInstance();
	private Calendar dTime = Calendar.getInstance();
	private Calendar alrTime = Calendar.getInstance();

	private ArrayList<Map<String, String>> mPeopleList;
	private SimpleAdapter mAdapter;
	private MultiAutoCompleteTextView mTxtPhoneNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_task);
		belongToACheckList = true;
		try {
			checkListID = getIntent().getExtras().getInt(CHECK_LIST_ID_STRING);
		} catch (Exception e) {
			belongToACheckList = false;
			Log.d("error on read extra", "No EXTRA CHECK LIST ID");
		}
		// Show the Up button in the action bar.
		setupActionBar();

		// Set adapter for spinner managing status
		statusOptions = (Spinner) findViewById(R.id.spinStatus);
		ArrayAdapter<CharSequence> spinStatusAdapter = ArrayAdapter
				.createFromResource(getApplicationContext(),
						R.array.status_array,
						android.R.layout.simple_spinner_item);
		spinStatusAdapter
				.setDropDownViewResource(R.layout.ytask_spinner_dropdown_item);
		statusOptions.setAdapter(spinStatusAdapter);
		statusOptions.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
				currentStatus = parent.getItemAtPosition(pos).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		priorityOptions = (Spinner) findViewById(R.id.spinPriority);
		ArrayAdapter<CharSequence> spinPrioAdapter = ArrayAdapter
				.createFromResource(getApplicationContext(),
						R.array.priority_array,
						android.R.layout.simple_spinner_item);
		spinPrioAdapter
				.setDropDownViewResource(R.layout.ytask_spinner_dropdown_item);
		priorityOptions.setAdapter(spinPrioAdapter);
		priorityOptions.setSelection(1);// normal priority
		priorityOptions.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
				currentPriority = parent.getItemAtPosition(pos).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		// Start Get Assignee Contact
		mPeopleList = new ArrayList<Map<String, String>>();
		PopulatePeopleList();
		mTxtPhoneNo = (MultiAutoCompleteTextView) findViewById(R.id.txtSearchContactQuery);
		mTxtPhoneNo.setThreshold(0);
		mAdapter = new SimpleAdapter(this, mPeopleList,
				R.layout.contact_item_view, new String[] { "Name", "Phone",
						"Type" }, new int[] { R.id.ccontName, R.id.ccontNo,
						R.id.ccontType });
		mTxtPhoneNo.setAdapter(mAdapter);
		mTxtPhoneNo.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		mTxtPhoneNo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int index,
					long arg3) {
				@SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) av
						.getItemAtPosition(index);

				// String name = map.get("Name");
				String number = map.get("Phone");
				// mTxtPhoneNo.setText(name + " " + "<" + number + ">");
				mTxtPhoneNo.setText(number);
			}
		});

		// Finish Get Assignee Contact

		findViewById(R.id.btnSaveTask).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Date startd = sTime.getTime();
						Date dued = dTime.getTime();
						Date alerttime = alrTime.getTime();
						try {
							String tittle = ((EditText) findViewById(R.id.newTaskTilleField))
									.getText().toString();
							String decipt = ((EditText) findViewById(R.id.newTaskDecripField))
									.getText().toString();

							int spendTime = Integer
									.parseInt(((EditText) findViewById(R.id.newTaskSpendTime))
											.getText().toString());

							String progress = (String) ((TextView) findViewById(R.id.txtPercent))
									.getText();

							// add new task to db
							TaskType type = new TaskType();
							type.setId(1);
							Task newtask = new Task(tittle, decipt, type,
									startd, dued, currentStatus,
									currentPriority, progress, 0, spendTime, 0,
									"", alerttime);
							newtask.setAssignee(mTxtPhoneNo.getText()
									.toString());
							TaskDAO dataHelper = new TaskDAO(
									getApplicationContext());
							dataHelper.open();
							long newTaskID = dataHelper.createTask(newtask);
							//add to TaskList
								if (belongToACheckList) {
								UserCheckListDAO tasklistHelper = new UserCheckListDAO(getApplicationContext());
								tasklistHelper.open();
								tasklistHelper.createTaskList((long)checkListID, newTaskID);
								//tasklistHelper.createTaskList(1, newTaskID);
							}
							// set alarm

							long time = alrTime.getTimeInMillis()
									- new GregorianCalendar().getTimeInMillis();
							String msg = ((EditText) findViewById(R.id.newTaskTilleField))
									.getText().toString();

							Intent i = new Intent(getBaseContext(),
									AlarmService.class);
							i.putExtra(keyid, (int) newTaskID );
							i.putExtra(keycreate, true);
							i.putExtra(keytime, time);
							i.putExtra(keystring, msg);
							startService(i);
							// Send SMS Task info to assignee
							String smsReciever = mTxtPhoneNo.getText()
									.toString();
							String sendUser = "Diep";

							String smsMessage = "You have new Task assign by "
									+ sendUser + ":" + "\n- Title: " + tittle
									+ "\n- Description: " + decipt
									+ "\n- Start Day: " + startd
									+ "\n- Due Day: " + dued;

							try {
								SmsManager smsManager = SmsManager.getDefault();
								smsManager.sendTextMessage(smsReciever, null,
										smsMessage, null, null);
								Log.d("Sms send info", smsReciever + ":"
										+ smsMessage);
								Toast.makeText(getApplicationContext(),
										"SMS Sent!", Toast.LENGTH_LONG).show();
							} catch (Exception e) {
								Toast.makeText(getApplicationContext(),
										"SMS faild, please try again later!",
										Toast.LENGTH_LONG).show();
								e.printStackTrace();
							}

							// SMS alert to Assignee
							smsMessage = "Your Task assign by " + sendUser
									+ " has due day:" + "\n- Title: " + tittle
									+ "\n- Description: " + decipt
									+ "\n- Start Day: " + startd
									+ "\n- Due Day: " + dued;
							long sendTime = alrTime.getTimeInMillis();
							Intent intentAlarm = new Intent(getBaseContext(),
									ScheduleSMSReciever.class);
							intentAlarm.putExtra(Constants.PHONE_NUMBER,
									smsReciever);
							intentAlarm.putExtra(Constants.MESSAGE, smsMessage);
							// create the object
							AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

							// set the alarm for particular time
							alarmManager.set(AlarmManager.RTC_WAKEUP, sendTime,
									PendingIntent.getBroadcast(
											getBaseContext(), 1, intentAlarm,
											PendingIntent.FLAG_UPDATE_CURRENT));
							Toast.makeText(getBaseContext(),
									"SMS Scheduled for " + time + " seconds",
									Toast.LENGTH_LONG).show();
							Log.d("Schedule Sms info: ", smsReciever + ":"
									+ smsMessage + ":" + time);

						} catch (Exception e) {
							NewTaskActivity.this.setResult(RESULT_CANCELED);
							Log.d("Error when create new task", e.getMessage());
						}
						NewTaskActivity.this.setResult(RESULT_OK);

						finish();
					}
				});

		final Button btnSDate = (Button) findViewById(R.id.btnSDate);
		final Button btnDDate = (Button) findViewById(R.id.btnDDate);
		final Button btnADate = (Button) findViewById(R.id.btnAlrDate);
		final Button btnATime = (Button) findViewById(R.id.btnAlrtTime);

		btnSDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment() {
					@Override
					public Dialog onCreateDialog(Bundle savedInstanceState) {
						int year = sTime.get(Calendar.YEAR);
						int month = sTime.get(Calendar.MONTH);
						int day = sTime.get(Calendar.DAY_OF_MONTH);

						// Create a new instance of DatePickerDialog and return
						// it
						return new DatePickerDialog(getActivity(), this, year,
								month, day);
					}

					@Override
					public void onDateSet(DatePicker view, int year, int month,
							int day) {
						super.onDateSet(view, year, month, day);
						btnSDate.setText(day + "/" + (month + 1) + "/" + year);
						sTime.set(year, month, day);
					}
				};
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});

		btnDDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment() {
					@Override
					public Dialog onCreateDialog(Bundle savedInstanceState) {
						int year = dTime.get(Calendar.YEAR);
						int month = dTime.get(Calendar.MONTH);
						int day = dTime.get(Calendar.DAY_OF_MONTH);

						// Create a new instance of DatePickerDialog and return
						// it
						return new DatePickerDialog(getActivity(), this, year,
								month, day);
					}

					@Override
					public void onDateSet(DatePicker view, int year, int month,
							int day) {
						super.onDateSet(view, year, month, day);
						btnDDate.setText(day + "/" + (month + 1) + "/" + year);
						dTime.set(year, month, day);
					}
				};
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});

		btnADate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment() {
					@Override
					public Dialog onCreateDialog(Bundle savedInstanceState) {
						int year = alrTime.get(Calendar.YEAR);
						int month = alrTime.get(Calendar.MONTH);
						int day = alrTime.get(Calendar.DAY_OF_MONTH);

						// Create a new instance of DatePickerDialog and return
						// it
						return new DatePickerDialog(getActivity(), this, year,
								month, day);
					}

					@Override
					public void onDateSet(DatePicker view, int year, int month,
							int day) {
						super.onDateSet(view, year, month, day);
						btnADate.setText(day + "/" + (month + 1) + "/" + year);
						alrTime.set(year, month, day);
					}
				};
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});

		btnATime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int hour = alrTime.get(Calendar.HOUR_OF_DAY);
				int minute = alrTime.get(Calendar.MINUTE);

				new TimePickerDialog(NewTaskActivity.this,
						new OnTimeSetListener() {
							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								int year = alrTime.get(Calendar.YEAR);
								int month = alrTime.get(Calendar.MONTH);
								int day = alrTime.get(Calendar.DAY_OF_MONTH);
								alrTime.set(year, month, day, hourOfDay, minute);
								btnATime.setText(hourOfDay + ":" + minute);
							}
						}, hour, minute, true).show();
			}
		});

		seekbar();
	}

	/*
	 * show % of seekbar
	 */
	private void seekbar() {
		final SeekBar sb = (SeekBar) findViewById(R.id.seekBar1);
		final TextView txtPercent = (TextView) findViewById(R.id.txtPercent);

		txtPercent.setText(String.valueOf(sb.getProgress()));
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				txtPercent.setText(String.valueOf(progress));
			}
		});
	}

	public void PopulatePeopleList() {
		mPeopleList.clear();
		Cursor people = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		while (people.moveToNext()) {
			String contactName = people.getString(people
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			String contactId = people.getString(people
					.getColumnIndex(ContactsContract.Contacts._ID));
			String hasPhone = people
					.getString(people
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
			if ((Integer.parseInt(hasPhone) > 0)) {
				// You know have the number so now query it like this
				Cursor phones = getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + contactId, null, null);
				while (phones.moveToNext()) {
					// store numbers and display a dialog letting the user
					// select which.
					String phoneNumber = phones
							.getString(phones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					String numberType = phones
							.getString(phones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
					Map<String, String> NamePhoneType = new HashMap<String, String>();
					NamePhoneType.put("Name", contactName);
					NamePhoneType.put("Phone", phoneNumber);
					if (numberType.equals("0"))
						NamePhoneType.put("Type", "Work");
					else if (numberType.equals("1"))
						NamePhoneType.put("Type", "Home");
					else if (numberType.equals("2"))
						NamePhoneType.put("Type", "Mobile");
					else
						NamePhoneType.put("Type", "Other");
					// Then add this map to the list.
					mPeopleList.add(NamePhoneType);
				}
				phones.close();
			} else {
				// Map<String, String> NamePhoneType_2 = new HashMap<String,
				// String>();
				// NamePhoneType_2.put("Name", contactName);
				// NamePhoneType_2.put("Phone", "no phone number");
				// NamePhoneType_2.put("Phone", "no phone type");
				// mPeopleList.add(NamePhoneType_2);
			}
		}
		people.close();
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
		getMenuInflater().inflate(R.menu.new_task, menu);
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
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
