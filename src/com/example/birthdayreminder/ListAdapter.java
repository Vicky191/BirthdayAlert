package com.example.birthdayreminder;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final List<String> values;
//	private final String[] values;
	public ListAdapter(Context context, List<String> values) {
	    super(context, R.layout.row_layout, values);
	    this.context = context;
	    //this.values = (String[])values.toArray();
	    this.values = values;
	  }
	static class ViewHolder {
	    public TextView text;
	  }
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView=convertView;
		if(rowView==null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.row_layout, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text=(TextView)rowView.findViewById(R.id.row_label);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder =(ViewHolder)rowView.getTag();
		holder.text.setText(values.get(position));
		return rowView;
	}
	
	
	
}
