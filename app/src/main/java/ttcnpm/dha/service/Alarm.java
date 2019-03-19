//this is Alarm - a BroadcastReciever
package ttcnpm.dha.service;

import ttcnpm.dha.constants.Constants;
import ttcnpm.dha.ytasks.R;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

@SuppressLint("NewApi")
public class Alarm extends BroadcastReceiver {
	private String keystring = Constants.keystring;
	private String keyid = Constants.keyid;

	@SuppressLint("Wakelock")
	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.PARTIAL_WAKE_LOCK, "");
		
		int id = intent.getExtras().getInt(keyid);
		wl.acquire();

		// do when in time
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.app_logo)
				.setContentTitle(intent.getExtras().getString(keystring))
				.setContentText("This task has met it's dueday, check it out");
		Intent resultIntent = new Intent(context,
				ttcnpm.dha.ytasks.TaskActivity.class);
		resultIntent.putExtra(Constants.TASK_ID_STRING, id);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(ttcnpm.dha.ytasks.FolderActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		mBuilder.setSound(Uri.parse("android.resource://"
				+ context.getPackageName() + "/" + R.raw.ytaskalarm));
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(id, mBuilder.build());

		wl.release();
	}

	public void SetAlarm(Context context, int id, String msg, long time) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, Alarm.class);
		intent.putExtra(keystring, msg);
		intent.putExtra(keyid, id);
		PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, id);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pi);
	}

	public void CancelAlarm(Context context, int id) {
		Intent intent = new Intent(context, Alarm.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, id, intent,
				0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
}