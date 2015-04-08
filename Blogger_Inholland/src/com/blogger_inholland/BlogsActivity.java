package com.blogger_inholland;

import java.util.concurrent.ExecutionException;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.blogger_inholland.adapters.BlogAdapter;
import com.blogger_inholland.pojo.Blog;
import com.blogger_inholland.pojo.BlogList;
import com.blogger_inholland.response.BlogListResponse;
import com.blogger_inholland.task.GetBlogsAsyncTask;
import com.google.gson.Gson;

public class BlogsActivity extends FragmentActivity implements
		ActionBar.TabListener {

	// FIELDS
	private BlogListResponse blr = new BlogListResponse();
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the 2 primary sections of the app. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will display the 2 primary sections of the
	 * app, one at a time.
	 */
	ViewPager mViewPager;

	// METHODS
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blogs);

		// Retrieve BlogListResponse object from GetBlogsAsyncTask
		try {
			blr = new GetBlogsAsyncTask().execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		// Create the adapter that will return a fragment for each of the 2
		// primary sections of the app.
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Enable Home Up button
		actionBar.setHomeButtonEnabled(true);

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between different app sections, select
						// the corresponding tab.
						// We can also use ActionBar.Tab#select() to do this if
						// we have a reference to the
						// Tab.
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			// Also specify this Activity object, which implements the
			// TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mAppSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {

			Fragment fragment = new Fragment();
			switch (i) {

			case 0:
				// First section from the app
				fragment = new SectionFragment();
				Bundle args = new Bundle();
				args.putInt(SectionFragment.ARG_SECTION_NUMBER, i + 1);
				fragment.setArguments(args);
				return fragment;
				// case 1:
			default:
				// Second section from the app
				fragment = new SectionFragment();
				Bundle args2 = new Bundle();
				args2.putInt(SectionFragment.ARG_SECTION_NUMBER, i + 1);
				fragment.setArguments(args2);
				return fragment;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "Section " + (position + 1);
		}
	}

	/**
	 * A fragment representing a section of the app
	 */
	public class SectionFragment extends ListFragment {

		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Bundle args = getArguments();

			// Display this in the first Fragment
			if (args.getInt(ARG_SECTION_NUMBER) == 1) {

				/** Creating BlogAdapter to set data in listview */
				BlogAdapter blogAdapter = new BlogAdapter(getActivity()
						.getBaseContext(), blr.responseObject.blogs);

				/** Setting the BlogAdapter to the listview */
				setListAdapter(blogAdapter);

			}// Display this in the second Fragment
			else if (args.getInt(ARG_SECTION_NUMBER) == 2) {

				// Add Blog from this user (UDID) to BlogList object
				BlogList blrTemp = new BlogList();
				for (Blog b : blr.responseObject.blogs) {
					if (b.udid.contains("ffffffff-bb00-8db1-ffff-ffff99d603a9")) {
						blrTemp.blogs.add(b);
					}

				}
				/** Creating BlogAdapter to set data in listview */
				BlogAdapter blogAdapter = new BlogAdapter(getActivity()
						.getBaseContext(), blrTemp.blogs);

				/** Setting the BlogAdapter to the listview */
				setListAdapter(blogAdapter);
			}

			return super.onCreateView(inflater, container, savedInstanceState);
		}

		@Override
		public void onStart() {
			super.onStart();

			/** Setting the multiselect choice mode for the listview */
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);

			// Retrieve data in accordance to its position
			Blog blog = (Blog) l.getItemAtPosition(position);

			// Convert Blog to JSONObject and pass it on to Bundle
			Intent activity = new Intent((BlogsActivity) getActivity(),
					BlogActivity.class);
			activity.putExtra("blog", new Gson().toJson(blog));
			startActivity(activity);
		}
	}
}