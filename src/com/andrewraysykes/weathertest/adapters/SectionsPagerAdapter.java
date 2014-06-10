package com.andrewraysykes.weathertest.adapters;

import java.util.Locale;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andrewraysykes.weathertest.ui.CurrentlyFragment;
import com.example.weathertest.R;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
	
	protected Context mContext;

	public SectionsPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position) {
		
		switch(position) {
			case 0:
				return new CurrentlyFragment();
			case 1:
				//return new HourlyFragment();
			case 2:
				//return new DailyFragment();
		}
		
		return null;
		
	}

	@Override
	public int getCount() {
		
		// during testing phase, eliminating the other 2 tabs
		return 1;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return mContext.getString(R.string.title_currently).toUpperCase(l);
		case 1:
			return mContext.getString(R.string.title_hourly).toUpperCase(l);
		case 2:
			return mContext.getString(R.string.title_daily).toUpperCase(l);
		}
		return null;
	}
	
	public int getIcon(int position) {
		switch (position) {
		case 0:
			return R.drawable.ic_launcher;
		case 1:
			return R.drawable.ic_launcher;
		case 2:
			return R.drawable.ic_launcher;
		}
		return R.drawable.ic_launcher; // fail-safe return
	}
}