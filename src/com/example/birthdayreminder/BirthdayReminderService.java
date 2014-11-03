package com.example.birthdayreminder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

public class BirthdayReminderService extends IntentService{
	public BirthdayReminderService() {
		super("BirthdayReminderService");
		// TODO Auto-generated constructor stub
	}
	
	private List<ContactInfo> list;
	private int count;
	private Intent mNotificationIntent;
	private PendingIntent mContentIntent;
	public void getBirthdays()
	{
		list=new ArrayList<ContactInfo>();
		count = 0;
		DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
        Calendar birthDate= Calendar.getInstance();
     	Calendar currentDate = Calendar.getInstance();
 	
     	ContentResolver cr = getContentResolver(); //getContnetResolver()
		String[] projection = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, projection, null, null,
		            ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");

		while (cur.moveToNext())
		{
		   String contactId = cur.getString(cur.getColumnIndex(ContactsContract.Data._ID));
		   String displayName =  cur.getString(cur.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
	//	   Log.d("Birthday","Contact Name:  "+displayName+"  Contact name "+contactId);
	        
           String columns[] = 
           {
		         ContactsContract.CommonDataKinds.Event.START_DATE,
		         ContactsContract.CommonDataKinds.Event.TYPE,
		         ContactsContract.CommonDataKinds.Event.MIMETYPE,
		   };

		   String where = Event.TYPE + "=" + Event.TYPE_BIRTHDAY + 
		                    " and " + Event.MIMETYPE + " = '" + Event.CONTENT_ITEM_TYPE + "' and " + ContactsContract.Data.CONTACT_ID + " = " + contactId;

		   String[] selectionArgs = null;
		   String sortOrder = ContactsContract.CommonDataKinds.Event.START_DATE;

           Cursor birthdayCur = cr.query(ContactsContract.Data.CONTENT_URI, columns, where, selectionArgs, sortOrder); 
		   String birthday=null;
		   if (birthdayCur.getCount() > 0) 
		   {
		        while (birthdayCur.moveToNext())
		        {
			        birthday = birthdayCur.getString(birthdayCur.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
			        if(birthday == null) 
			        	continue;
			        Log.d("Birthday","Contact Name:  "+displayName+"  Date "+birthday);
			        try
			        {
						birthDate.setTime(dateFormat.parse(birthday));
						   
					} catch (ParseException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.d("Error","Exception date is "+ birthday);
					}
		        }
		        birthdayCur.close();
			   if((currentDate.get(Calendar.MONTH)==birthDate.get(Calendar.MONTH))&&(currentDate.get(Calendar.DATE)==birthDate.get(Calendar.DATE)))
			   {
				   String number=getNumber(contactId);
				   count++;
				   
				   if(number!=null)
						  list.add(new ContactInfo(number,displayName,0));
				      else
				    	  list.add(new ContactInfo("None",displayName,0));
				   Log.d("Birthday","Today is "+displayName+"'s Birthday. Date is Month:: "+(currentDate.get(Calendar.MONTH)+1)+" day:: "+currentDate.get(Calendar.DATE)+" and year:: "+currentDate.get(Calendar.YEAR));
			   }
		   }
		}
		    cur.close();
			
	}
	public String getNumber(String cid)
	{
		String number=null;
		Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
			      new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +"="+cid, 
			     null, null);
	   while (phones.moveToNext()) {
	          number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	      }
		
		return number; 
	}
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("Birthday","Inside Service");
		getBirthdays();
		Log.d("Birthday", "Count: " +count);
		if(count>0)
		{
			mNotificationIntent = new Intent(getApplicationContext(),
					NotificationSubActivity.class);
			mNotificationIntent.putParcelableArrayListExtra("Birthday Contacts Info", (ArrayList<ContactInfo>) list);
			mContentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
					mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
			Notification.Builder notificationBuilder = new Notification.Builder(
					getApplicationContext())
					.setTicker(count+" Birthdays today")
					.setSmallIcon(android.R.drawable.stat_sys_warning)
					.setAutoCancel(true)
					.setContentTitle("Wish Birthday")
					.setContentText(count+" people have Birthday today")
					.setContentIntent(mContentIntent);
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(0, notificationBuilder.build());
		}
	}
	
}
