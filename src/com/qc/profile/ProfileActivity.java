package com.qc.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.qc.Util;
import com.qc.login.LoginActivity;

public class ProfileActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您还没注册，请问是否现在注册？").setCancelable(false)
                .setPositiveButton("注册", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Util.startActivity(ProfileActivity.this, LoginActivity.class, null);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        displayError();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        displayUnfinished();
    }

    private void displayError() {
        TextView view = new TextView(this);
        view.setText("此功能只适用于注册用户！");
        setContentView(view);
    }

    private void displayUnfinished() {
        TextView view = new TextView(this);
        view.setText("此功能尚未完成！");
        setContentView(view);
    }
}