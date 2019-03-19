package ttcnpm.dha.service;

import ttcnpm.dha.constants.Constants;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {
	private String keystring = Constants.keystring;
	private String keytime = Constants.keytime;
	private String keycreate = Constants.keycreate;
	private String keyid = Constants.keyid;
	Alarm alarm = new Alarm();

	public void onCreate() {
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		int id = intent.getExtras().getInt(keyid);

		if (intent.getExtras().getBoolean(keycreate)) {
			long time = intent.getExtras().getLong(keytime);
			String msg = intent.getExtras().getString(keystring);
			alarm.SetAlarm(getBaseContext(), id, msg, time);
		} else {
			alarm.CancelAlarm(getBaseContext(), id);
		}
		return super.onStartCommand(intent, flags, startId);
	}

}
