package com.qc;

import com.qc.camera.CameraActivity;
import com.qc.friend.FriendActivity;
import com.qc.notification.NotificationActivity;
import com.qc.profile.ProfileActivity;
import com.qc.square.SquareActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class SemiPrototypeActivity extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources();
		TabHost tabHost = getTabHost();

		this.addTabSpec(SquareActivity.class, tabHost, res, "square",
				getString(R.string.square), R.drawable.ic_tab_artists);
		this.addTabSpec(NotificationActivity.class, tabHost, res,
				"notifications", getString(R.string.notification),
				R.drawable.ic_tab_artists);
		this.addTabSpec(CameraActivity.class, tabHost, res, "camera",
				getString(R.string.camera), R.drawable.ic_tab_artists);
		this.addTabSpec(ProfileActivity.class, tabHost, res, "profile",
				getString(R.string.profile), R.drawable.ic_tab_artists);
		this.addTabSpec(FriendActivity.class, tabHost, res, "friends",
				getString(R.string.friends), R.drawable.ic_tab_artists);

		tabHost.setCurrentTab(2);
	}

	private void addTabSpec(Class<?> cls, TabHost tabHost, Resources res,
			String name, String indicator, int drawableId) {
		Intent intent = new Intent().setClass(this, cls);
		TabHost.TabSpec spec = tabHost.newTabSpec(name)
				.setIndicator(indicator, res.getDrawable(drawableId))
				.setContent(intent);
		tabHost.addTab(spec);
	}
}