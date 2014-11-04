package com.example.birthdayreminder;


import java.util.ArrayList;
import java.util.List;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NotificationSubActivity extends ListActivity {
	private List<ContactInfo> list = null;
	private static ContactInfo ci=null;
	private DialogFragment mDialog;
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
				ci=list.get(position);
				mDialog = AlertDialogFragment.newInstance();
				mDialog.show(getFragmentManager(), "Birthday");
				Log.d("Birthday","Inside notificationsubactivity Number: "+list.get(position).getCno());
				
			}
		});
	    
	    
	}
	public static class AlertDialogFragment extends DialogFragment {

		public static AlertDialogFragment newInstance() {
			return new AlertDialogFragment();
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new AlertDialog.Builder(getActivity())
					.setMessage("Wish "+ ci.getCname()+" Birthday!")
					.setCancelable(false)
					.setNeutralButton("Call",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Intent callIntent = new Intent(Intent.ACTION_DIAL);
									callIntent.setData(Uri.parse("tel:"+ci.getCno()));
									startActivity(callIntent);
								}
							})
					.setPositiveButton("Send SMS",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int id) {
							SmsManager smsManager = SmsManager.getDefault();
							smsManager.sendTextMessage(ci.getCno(), null, "Happy Birthday "+ci.getCname(), null, null);
						}
					}).create();
		}
	}

}
