package com.blogger_inholland.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogger_inholland.pojo.Blog;
import com.example.blogger_inholland.R;

public class BlogAdapter extends BaseAdapter {
	private ArrayList<Blog> mItems; 
	private LayoutInflater mInflater;

	public BlogAdapter(Context context, ArrayList<Blog> items) { 
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		mItems = items;
	}

	public int getCount() { 
		return mItems.size();
	}

	public Blog getItem(int position) { 
		return mItems.get(position);
	}

	public long getItemId(int position) { 
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) { 
		//Viewholder pattern for efficiency
		Blog node = mItems.get(position);
		ViewHolder holder = new ViewHolder();
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.blog_listitem, null);
			//				holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.blog_listitem_image);
			holder.title = (TextView) convertView.findViewById(R.id.blog_listitem_title);
			holder.description = (TextView) convertView.findViewById(R.id.blog_listitem_description);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(node.title);
		holder.description.setText(node.description);

		convertView.setTag(holder);

		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView description;
		ImageView image;
	}
}