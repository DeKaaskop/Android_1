package com.blogger_inholland;

import com.blogger_inholland.task.GetBlogs;
import com.example.blogger_inholland.R;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class BlogsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blogs);
		getLayoutInflater().inflate(R.layout.blog_listitem, null);
		
		//Sets Actionbar
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    
	    new GetBlogs().execute();
	    
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		switch (item.getItemId())
		{
          case android.R.id.home:
        	  startActivity (new Intent (this, MainActivity.class));
        	  return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_blogs, menu);
		return true;
	}
}
