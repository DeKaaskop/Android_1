package com.blogger_inholland;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.blogger_inholland.config.BlogConstants;
import com.blogger_inholland.pojo.Blog;
import com.blogger_inholland.task.BitmapWorkerTask;
import com.google.gson.Gson;

@SuppressLint("SimpleDateFormat")
public class BlogActivity extends Activity {

	// FIELDS
	private Blog blog = null;
	private Intent shareIntent = new Intent();

	// METHODS
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_post);

		// Sets ActionBar
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Retrieve content to show
		String blogPost = new String();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			blogPost = extras.getString("blog");
		}
		blog = new Gson().fromJson(blogPost, Blog.class);

		ImageView imgView = (ImageView) findViewById(R.id.blogpost_imageview);

		// Fill in the correct fields with data
		// Download images and display
		loadBitmap(BlogConstants.IMAGE_URL + blog.image, imgView);

		TextView txtViewTitle = (TextView) findViewById(R.id.blogpost_textview_title);
		txtViewTitle.setText(blog.title);

		TextView txtViewAuthor = (TextView) findViewById(R.id.blogpost_textview_author);
		txtViewAuthor.setText("By " + blog.author);

		TextView txtViewContent = (TextView) findViewById(R.id.blogpost_textview_content);
		txtViewContent.setText(blog.content);

		TextView txtViewTimeStamp = (TextView) findViewById(R.id.blogpost_textview_timestamp);
		txtViewTimeStamp.setText(getDate(blog.createdondate));

	}

	public CharSequence getDate(long timestamp) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timestamp * 1000);
		Date d = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return sdf.format(d);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu resource file.
		getMenuInflater().inflate(R.menu.activity_blogpost, menu);

		ShareActionProvider mShareActionProvider;

		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.menu_item_share);

		// Fetch and store ShareActionProvider
		mShareActionProvider = (ShareActionProvider) item.getActionProvider();

		// ActionBar -> Share Intent
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Title: " + blog.title);
		shareIntent.putExtra(Intent.EXTRA_TEXT, "Content: " + blog.content);

		// Connect the dots: give the ShareActionProvider its Share Intent
		if (mShareActionProvider != null) {
			mShareActionProvider.setShareIntent(shareIntent);
		}

		// Return true to display menu
		return true;
	}

	// When ActionBar item is clicked
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// startActivity(new Intent(this, MainActivity.class));
			Intent intent = new Intent(this, BlogsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case R.id.menu_item_share:
			startActivity(Intent.createChooser(shareIntent,
					"Sharing is caring!"));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void loadBitmap(String url, ImageView imageView) {
		BitmapWorkerTask task = new BitmapWorkerTask(imageView);
		task.execute(url);
	}
}
