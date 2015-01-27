package com.blogger_inholland.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Blog implements Parcelable {

	public int id;
	public String title;
	public String description;
	public String content;
	public String image;
	public String udid;
	public Long createdondate;
	

	public Blog(Parcel in) {
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeString(content);
		dest.writeString(image);
		dest.writeString(udid);
		dest.writeLong(createdondate);
	}
	
	public static final Parcelable.Creator<Blog> CREATOR = new Parcelable.Creator<Blog>() {
		public Blog createFromParcel(Parcel in) {
			return new Blog(in);
		}

		@Override
		public Blog[] newArray(int size) {
			return new Blog[size];
		}
	};
	
	public void readFromParcel(Parcel in) {
		id = in.readInt();
		title = in.readString();
		description = in.readString();
		content = in.readString();
		image = in.readString();
		udid = in.readString();
		createdondate = in.readLong();
	}
}
