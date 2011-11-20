package com.qc.friend;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FriendActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("和欧阳晓琪讨论，我总觉得按照其他经典app的布局和用户的习惯，我们总需要给用户一个可以设置基本的功能，查看产品信息还有login out的地方。");
        setContentView(textView);
    }
}