package com.example.birthdayreminder;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactInfo implements Parcelable{
	private String  cno;
	private String cname;
	private int type;
	public ContactInfo(String cno,String cname, int type)
	{
		this.cno=cno;
		this.cname=cname;
		this.type=type;
	}
	public String getCno()
	{
		return cno;
	}
	public String getCname()
	{
		return cname;
	}
	public int getType()
	{
		return type;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		// TODO Auto-generated method stub
		parcel.writeString(cno);
		parcel.writeString(cname);
		parcel.writeInt(type);
		
	}
	
	public static final Parcelable.Creator<ContactInfo> CREATOR
    = new Parcelable.Creator<ContactInfo>() {
        public ContactInfo createFromParcel(Parcel in) {
            return new ContactInfo(in);
        }

        public ContactInfo[] newArray(int size) {
            return new ContactInfo[size];
        }
    };

	
	private ContactInfo(Parcel in) {
		cno = in.readString();
		cname = in.readString();
		type=in.readInt();
    }

}
