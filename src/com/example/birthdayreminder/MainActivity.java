package com.example.birthdayreminder;

import java.util.Calendar;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button b=(Button)findViewById(R.id.check);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar cal = Calendar.getInstance();
/*				cal.set(Calendar.HOUR_OF_DAY,17);
				cal.set(Calendar.MINUTE,9);
				cal.set(Calendar.SECOND,0); */
				Intent i = new Intent(getApplicationContext(), BirthdayReminderService.class);
				PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, i, 0);
				AlarmManager alarm = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		//		alarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pintent);
				alarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()+10000, pintent);
				(Toast.makeText(MainActivity.this, "Alarm Set", Toast.LENGTH_SHORT)).show();
				
			}
		});
		

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
