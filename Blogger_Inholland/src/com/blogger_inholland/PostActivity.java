package com.blogger_inholland;

import java.io.FileOutputStream;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogger_inholland.task.PostBlogAsyncTask;
import com.example.blogger_inholland.R;

public class PostActivity extends Activity { //implements AsyncResponse {

	// FIELDS
	private EditText mtitle;
	private EditText mdescription;
	private ImageView m_GetImage;
	private Button btn_post;
	private Bitmap mphoto = null;
	private SharedPreferences msp;

	final static String LLC = "0602";
	private PostBlogAsyncTask asyncTask = new PostBlogAsyncTask();

	// private ImageView mImageView;

	// METHODS
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);

//		asyncTask.delegate = this;

		// Sets Actionbar
		getActionBar().setDisplayHomeAsUpEnabled(true);

		msp = getSharedPreferences("com.example.blogger_inholland", 0);

		// Set OnClickListener for ImageView Object to retrieve image from
		// gallery
		m_GetImage = (ImageView) findViewById(R.id.post_imageview);
		m_GetImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pickProfilePicture();
			}
		});

		// // Set OnClickListener for Button Object to make post
		btn_post = (Button) findViewById(R.id.btn_newpost);
		btn_post.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Post();
			}
		});
	}

	public void pickProfilePicture() {
		// Pick Image from image chooser
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 90);
		intent.putExtra("outputY", 90);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, 1);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		 if (resultCode == RESULT_OK) {
		 mphoto = data.getParcelableExtra("data");
		
		 View iv_ProfilePicture = (ImageView)
		 findViewById(R.id.profile_edit_imageView);
		 try {
		 // Set Image of ImageView object
		 ((ImageView) iv_ProfilePicture).setImageBitmap(mphoto);
		
		 // Save Image
		 FileOutputStream out = openFileOutput("post_picture",
		 Context.MODE_PRIVATE);
		 mphoto.compress(Bitmap.CompressFormat.PNG, 90, out);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
		 }
	}

	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(contentUri, proj, null,
				null, null);

		if (cursor == null)
			return null;

		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		cursor.moveToFirst();

		return cursor.getString(column_index);
	}

	public void Post() {
		// Get text from EditText
		mtitle = (EditText) findViewById(R.id.post_edittext_title);
		mdescription = (EditText) findViewById(R.id.post_edittext_description);

		Context context = getApplicationContext();
		Integer text = null;
		int duration = Toast.LENGTH_SHORT;
		Toast toast;

		// Check if EditText fields are filled, otherwise show toast
		if (mtitle.length() != 0 && mdescription.length() != 0) {
			// if(msp.contains("uuid"))
			// {
			AsyncTask<Object, Void, Integer> post_result = new PostBlogAsyncTask()
					.execute(mtitle.getText().toString(), mdescription
							.getText().toString(), LLC, mphoto);

			// text = post_result.toString();// "Post has been sent.";
			try {
				text = post_result.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

			toast = Toast.makeText(context, String.valueOf(text), duration);
			toast.show();

			startActivity(new Intent(this, MainActivity.class));
		}
		// }
		else {
			String text2 = "Please fill in both fields!";
			toast = Toast.makeText(context, text2, duration);
			toast.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_post, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(new Intent(this, MainActivity.class));
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

//	@Override
//	public Integer processFinish(Integer output) {
//		return output;
//	}
}
