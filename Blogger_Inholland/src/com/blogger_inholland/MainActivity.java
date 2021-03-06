package com.blogger_inholland;

import com.example.blogger_inholland.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Show activity_main from Layout 
		setContentView(R.layout.activity_main);
		
		//Buttons
		//navigate to About page
	    final ImageButton btn_About = (ImageButton) findViewById(R.id.btn_About);
	    btn_About.setOnClickListener(new View.OnClickListener() 
	    {
	    	@Override
	    	public void onClick(View v) 
	    	{
	    		startActivity(new Intent(MainActivity.this, AboutActivity.class));
	    	}
	    });
	
	    //navigate to Profile page
	   	final ImageButton btn_Profile = (ImageButton) findViewById(R.id.btn_Profile);
	   	btn_Profile.setOnClickListener(new View.OnClickListener() 
	   	{
	   		@Override
    		public void onClick(View v) 
	   		{
	    		startActivity(new Intent(MainActivity.this, ProfileActivity.class));
	   		}
	   	});	
		
		//navigate to NewPost page
	    final ImageButton btn_NewPost = (ImageButton) findViewById(R.id.btn_NewPost);
	    btn_NewPost.setOnClickListener(new View.OnClickListener() 
	    {
	    	@Override
	    	public void onClick(View v) 
	    	{
	    		startActivity(new Intent(MainActivity.this, PostActivity.class));
	    	}
	    });
	    
		//navigate to Blogs page
	    final ImageButton btn_Blogs = (ImageButton) findViewById(R.id.btn_Blogs);
	    btn_Blogs.setOnClickListener(new View.OnClickListener() 
	    {
	    	@Override
	    	public void onClick(View v) 
	    	{
	    		startActivity(new Intent(MainActivity.this, BlogsActivity.class));
	    	}
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
