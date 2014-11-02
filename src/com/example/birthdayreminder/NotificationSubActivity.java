package com.example.birthdayreminder;


import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

public class NotificationSubActivity extends ListActivity {
	private List<ContactInfo> list = null;
	private List<String> list_name=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		list=getIntent().getParcelableArrayListExtra("Birthday Contacts Info");
		for(ContactInfo c : list)
		{
			list_name.add(c.getCname());
			Log.d("Birthday","Name: "+c.getCname()+" Number: "+c.getCno()+" Type: "+c.getType());
		}
		ListAdapter adapter = new ListAdapter(this,list_name);
	    setListAdapter(adapter);
	    
	}
}
