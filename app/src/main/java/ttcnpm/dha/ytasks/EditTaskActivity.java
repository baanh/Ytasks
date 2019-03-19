package ttcnpm.dha.ytasks;

import java.util.Calendar;
import java.util.Date;

import ttcnpm.dha.constants.Constants;
import ttcnpm.dha.dao.TaskDAO;
import ttcnpm.dha.service.AlarmService;
import ttcnpm.dha.util.DatePickerFragment;
import ttcnpm.dha.vo.Task;
import ttcnpm.dha.vo.TaskType;
import android.R.integer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class EditTaskActivity extends Activity {
	private static final String keystring = Constants.keystring;
	private static final String keytime = Constants.keytime;
	private static final String keycreate = Constants.keycreate;
	private static final String keyid = Constants.keyid;
	private final int id = 1; // identifier of alarm

	private Spinner statusOptions;
	private String currentStatus;

	private Spinner priorityOptions;
	private String currentPriority;

	private Calendar sTime = Calendar.getInstance();
	private Calendar dTime = Calendar.getInstance();
	private Calendar alrTime = Calendar.getInstance();
	
	int taskid;
	private TaskDAO taskDbHelper;
	Task currentTask;
	private final String TASK_ID_STRING = Constants.TASK_ID_STRING;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent result = new Intent();
		result.putExtra(TASK_ID_STRING,taskid);								
		EditTaskActivity.this.setResult(RESULT_OK,result);
		finish();
	}
	

	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		// Show the Up button in the action bar.
		setupActionBar();
		
		taskid = getIntent().getExtras().getInt(TASK_ID_STRING);
		
		taskDbHelper = new TaskDAO(this);
		taskDbHelper.open();
		
		currentTask = taskDbHelper.getTask(taskid, taskDbHelper.getDatabase());
		((EditText) findViewById(R.id.editTaskTilleField)).setText(currentTask.getTitle());
		((EditText) findViewById(R.id.editTaskDecripField)).setText(currentTask.getDescription());
		((EditText) findViewById(R.id.editTaskSpendTime)).setText(String.valueOf(currentTask.getSpendTime()));
		((TextView) findViewById(R.id.txtPercent)).setText(currentTask.getProgress());
		((SeekBar) findViewById(R.id.seekBar1)).setProgress(Integer.parseInt(currentTask.getProgress()));
		Date currentSDay = new Date(currentTask.getStartDate().toString());		
		((Button) findViewById(R.id.btnSDate)).setText(currentSDay.getDate()+"/"+currentSDay.getMonth()+"/"+(currentSDay.getYear()+1900));
		currentSDay = new Date(currentTask.getDueDate().toString());		
		((Button) findViewById(R.id.btnDDate)).setText(currentSDay.getDate()+"/"+currentSDay.getMonth()+"/"+(currentSDay.getYear()+1900));
		currentSDay = new Date(currentTask.getAlert().toString());		
		((Button) findViewById(R.id.btnAlrDate)).setText(currentSDay.getDate()+"/"+currentSDay.getMonth()+"/"+(currentSDay.getYear()+1900));
		((Button) findViewById(R.id.btnAlrtTime)).setText(currentSDay.getHours()+":"+currentSDay.getMinutes());
		// Set adapter for spinner managing status
				statusOptions = (Spinner) findViewById(R.id.spinStatus);
				ArrayAdapter<CharSequence> spinStatusAdapter = ArrayAdapter
						.createFromResource(getApplicationContext(),
								R.array.status_array,
								android.R.layout.simple_spinner_item);
				spinStatusAdapter
						.setDropDownViewResource(R.layout.ytask_spinner_dropdown_item);
				statusOptions.setAdapter(spinStatusAdapter);
				for (int i=0;i<=3;i++){
					if (currentTask.getStatus().equals(statusOptions.getItemAtPosition(i))){
						statusOptions.setSelection(i);
						break;
					}
				}
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
				for (int i=0;i<=3;i++){
					if (currentTask.getPriority().equals(priorityOptions.getItemAtPosition(i))){
						priorityOptions.setSelection(i);
						break;
					}
				}
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

				findViewById(R.id.btnSaveTask).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								Date startd = sTime.getTime();
								Date dued = dTime.getTime();
								Date alerttime = alrTime.getTime();
								try {
									String tittle = ((EditText) findViewById(R.id.editTaskTilleField))
											.getText().toString();
									String decipt = ((EditText) findViewById(R.id.editTaskDecripField))
											.getText().toString();
									
									int spendTime = Integer.parseInt(((EditText) findViewById(R.id.editTaskSpendTime)).getText().toString());
									String progress = (String) ((TextView) findViewById(R.id.txtPercent))
											.getText();		
									
									// TODO add new task to db

									TaskType type = new TaskType();
									type.setId(1);
									Task newtask = new Task(tittle, decipt, type,
											startd, dued, currentStatus,
											currentPriority, progress, 0, spendTime, 0,
											"", alerttime);
									TaskDAO dataHelper = new TaskDAO(
											getApplicationContext());
									dataHelper.open();
									dataHelper.updateTask(newtask,taskid);
									
									// set alarm

									long time = alrTime.getTime().getTime()
											- System.currentTimeMillis();
									String msg = ((EditText) findViewById(R.id.newTaskTilleField))
											.getText().toString();

									Intent i = new Intent(getBaseContext(),
											AlarmService.class);						
									i.putExtra(keyid, id);
									i.putExtra(keycreate, true);
									i.putExtra(keytime, time);
									i.putExtra(keystring, msg);
									startService(i);
									
								} catch (Exception e) {
									EditTaskActivity.this.setResult(RESULT_CANCELED);
								}
								
								Intent result = new Intent();
								result.putExtra(TASK_ID_STRING,taskid);								
								EditTaskActivity.this.setResult(RESULT_OK,result);
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

						new TimePickerDialog(EditTaskActivity.this,
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
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_task, menu);
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
			Intent result = new Intent();
			result.putExtra(TASK_ID_STRING,taskid);								
			EditTaskActivity.this.setResult(RESULT_OK,result);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
