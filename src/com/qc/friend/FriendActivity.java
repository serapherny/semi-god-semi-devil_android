package com.qc.friend;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.qc.R;
import com.qc.Util;

public class FriendActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend);

        TextView textView = (TextView) findViewById(R.id.info);
        textView.setText("和欧阳晓琪讨论，我总觉得按照其他经典app的布局和用户的习惯，我们总需要给用户一个可以设置基本的功能，查看产品信息还有login out的地方。");

        Button aboutButton = (Button) findViewById(R.id.about_button);
        aboutButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Util.launchNativeApp((Activity) FriendActivity.this,
                        "com.qc/com.qc.about.kube.Kube", null);
            }
        });
    }
}