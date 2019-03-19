package ttcnpm.dha.service;

import ttcnpm.dha.constants.Constants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class ScheduleSMSReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String phoneNumberReciver = intent.getStringExtra(Constants.PHONE_NUMBER);
		String message = intent.getStringExtra(Constants.MESSAGE);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumberReciver, null, message, null, null);
		// Show the toast like in above screen shot
		Toast.makeText(context, "Alarm Triggered and SMS Sent",
				Toast.LENGTH_LONG).show();
	}

}
