package com.example.birthdayreminder;


import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

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
			Log.d("Birthday","Inside notificationsubactivity Name: "+c.getCname()+" Number: "+c.getCno()+" Type: "+c.getType());
		}
		ListAdapter adapter = new ListAdapter(this,list_name);
	    setListAdapter(adapter);
	    getListView().setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Display a Toast message indicting the selected item
//			Toast.makeText(getApplicationContext(),
	//					((TextView) view).getText(), Toast.LENGTH_SHORT).show();
				list.get(position).getCno();
				Log.d("Birthday","Inside notificationsubactivity Number: "+list.get(position).getCno());

			}
		});
	    
	}
}
