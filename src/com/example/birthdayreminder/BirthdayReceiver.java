package com.example.birthdayreminder;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BirthdayReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		Log.d("Birthday", "Inside Receiver");
		cal.set(Calendar.HOUR_OF_DAY,00);
		cal.set(Calendar.MINUTE,00);
		cal.set(Calendar.SECOND,0);
		Intent i = new Intent(context, BirthdayReminderService.class);
		PendingIntent pintent = PendingIntent.getService(context, 0, i, 0);
		AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		alarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pintent);
//		alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 10*1000, pintent);
	}

}
