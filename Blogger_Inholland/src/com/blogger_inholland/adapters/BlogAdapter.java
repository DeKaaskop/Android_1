package com.blogger_inholland.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogger_inholland.R;
import com.blogger_inholland.config.BlogConstants;
import com.blogger_inholland.pojo.Blog;
import com.blogger_inholland.task.*;

@SuppressLint("InflateParams")
public class BlogAdapter extends BaseAdapter {
	
	//FIELDS
	private ArrayList<Blog> mItems; 
	private LayoutInflater mInflater;
	
	//CONSTRUCTOR
	public BlogAdapter(Context context, ArrayList<Blog> items) { 
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		mItems = items;
	}

	//METHODS
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
			holder.author = (TextView) convertView.findViewById(R.id.blog_listitem_author);
			holder.title = (TextView) convertView.findViewById(R.id.blog_listitem_title);
			holder.description = (TextView) convertView.findViewById(R.id.blog_listitem_description);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		//Download images and display
		loadBitmap(BlogConstants.IMAGE_URL + node.image, holder.image);
		
		holder.title.setText(node.title);
		holder.description.setText(node.content.replace("\n", ""));
		holder.author.setText("By " + node.author);
		
		convertView.setTag(holder);

		return convertView;
	}
	
	public void loadBitmap(String url, ImageView imageView) {
	    BitmapWorkerTask task = new BitmapWorkerTask(imageView);
	    task.execute(url);
	}

	//Static Nested Class
	static class ViewHolder {
		TextView title;
		TextView description;
		ImageView image;
		TextView author;
	}
}