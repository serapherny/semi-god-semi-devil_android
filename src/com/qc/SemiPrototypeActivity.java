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

    private static TabHost host;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Resources res = getResources();
        TabHost tabHost = getTabHost();

        this.addTabSpec(SquareActivity.class, tabHost, res, "square", "",
                R.drawable.guangchang_tab_icon);
        this.addTabSpec(NotificationActivity.class, tabHost, res, "notifications", "",
                R.drawable.tixing_tab_icon);
        this.addTabSpec(CameraActivity.class, tabHost, res, "camera", "",
                R.drawable.paizhao_tab_icon);
        this.addTabSpec(ProfileActivity.class, tabHost, res, "profile", "",
                R.drawable.wode_tab_icon);
        this.addTabSpec(FriendActivity.class, tabHost, res, "friends", "",
                R.drawable.dangyu_tab_icon);

        tabHost.setCurrentTab(2);
    }

    private void addTabSpec(Class<?> cls, TabHost tabHost, Resources res, String name,
            String indicator, int drawableId) {
        Intent intent = new Intent().setClass(this, cls);
        TabHost.TabSpec spec = tabHost.newTabSpec(name)
                .setIndicator(indicator, res.getDrawable(drawableId))
                .setContent(intent);
        tabHost.addTab(spec);
    }

    public static void setTab(int index) {
        host.setTag(index);
    }
}