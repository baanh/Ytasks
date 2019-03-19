package ttcnpm.dha.ytasks;

import java.util.Calendar;
import java.util.Date;

import ttcnpm.dha.dao.TaskDAO;
import ttcnpm.dha.dao.UserDAO;
import ttcnpm.dha.util.Validation;
import ttcnpm.dha.vo.Task;
import ttcnpm.dha.vo.TaskType;
import ttcnpm.dha.vo.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.EditText;

public class LoginActivity extends Activity {
	EditText txtEmail, txtPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		txtEmail = (EditText) findViewById(R.id.txtUsername);
		txtPass = (EditText) findViewById(R.id.txtPassword);

		// for test
		txtEmail.setText("Snor@mail.com");
		txtPass.setText("passwork");
//		DB4test();

		findViewById(R.id.btnLogin).setOnClickListener(loginClickListener);
		findViewById(R.id.txtSignUp)
				.setOnClickListener(newAccountClickListener);

		Thread check = new Thread(new Runnable() {
			@Override
			public void run() {
				txtPass.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						Validation.isPassword(txtPass);
					}
				});

				txtEmail.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						Validation.isEmailAddress(txtEmail);
					}
				});
			}
		});
		check.run();
	}

	/*
	 * Database for Testting
	 */
	private void DB4test() {
		deleteDatabase("ytasks.db");
		users();
		Date now = Calendar.getInstance().getTime();
		for (int i = 1; i < 10; i++) {
			addDB("task " + i, "descip task " + i, now, now, "status " + i,
					"priority " + i, String.valueOf(10 * i), i * 2, now);
		}
	}

	private void users() {
		UserDAO userDb = new UserDAO(getApplicationContext());
		userDb.open();
		Date now = Calendar.getInstance().getTime();
		User usr = new User("pass", "phenchua92@yahoo.com.vn", "Snor", "Sjr",
				"01665374974", "Prof", "phenchua92@yahoo.com.vn", now, "fck ",
				"cv");
		userDb.createUser(usr);
		usr = new User("pass", "phenchua93@yahoo.com.vn", "Snor", "Sjr",
				"01665374974", "Prof", "phenchua92@yahoo.com.vn", now, "fck ",
				"cv");
		userDb.createUser(usr);
		usr = new User("pass", "phenchua94@yahoo.com.vn", "Snor", "Sjr",
				"01665374974", "Prof", "phenchua92@yahoo.com.vn", now, "fck ",
				"cv");
		userDb.createUser(usr);
		usr = new User("pass", "phenchua95@yahoo.com.vn", "Snor", "Sjr",
				"01665374974", "Prof", "phenchua92@yahoo.com.vn", now, "fck ",
				"cv");
		userDb.createUser(usr);
	}

	private void addDB(String tittle, String decipt, Date startd, Date dued,
			String currentStatus, String currentPriority, String progress,
			int spendTime, Date alerttime) {
		TaskType type = new TaskType();
		type.setId(1);
		Task newtask = new Task(tittle, decipt, type, startd, dued,
				currentStatus, currentPriority, progress, 0, spendTime, 0, "",
				alerttime);
		TaskDAO dataHelper = new TaskDAO(getApplicationContext());
		dataHelper.open();
		dataHelper.createTask(newtask);
		dataHelper.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private OnClickListener loginClickListener = new OnClickListener() {
		public void onClick(android.view.View arg0) {
			if (ValidLogin()) {
				Intent newacti = new Intent(getApplicationContext(),
						CategoryActivity.class);
				startActivity(newacti);
			}

		};
	};

	private OnClickListener newAccountClickListener = new OnClickListener() {
		public void onClick(android.view.View arg0) {
			Intent newacti = new Intent(getApplicationContext(),
					NewAccountActivity.class);
			startActivity(newacti);
		};
	};

	private boolean ValidLogin() {
		boolean ret = true;
		if (!Validation.hasText(txtEmail) || !Validation.hasText(txtPass))
			ret = false;
		if (!Validation.isEmailAddress(txtEmail))
			ret = false;
		if (!Validation.isPassword(txtPass))
			ret = false;

		return ret;
	}

}