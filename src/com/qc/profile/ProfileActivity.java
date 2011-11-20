package com.qc.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qc.R;
import com.qc.login.LoginUtil;

public class ProfileActivity extends Activity {

    // Declare our Views, so we can access them later
    private EditText etEmail;
    private EditText etPassword;
    private EditText etNickname;
    private Button btnLogin;
    private Button btnCancel;
    private TextView lblResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Get the EditText and Button References
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        etNickname = (EditText) findViewById(R.id.nickname);
        btnLogin = (Button) findViewById(R.id.login_button);
        btnCancel = (Button) findViewById(R.id.cancel_button);
        lblResult = (TextView) findViewById(R.id.login_result);

        // Set Click Listener
        btnLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Check Login
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String nickname = etNickname.getText().toString();

                new LoginUtil().sendLoginRequest(email, password, nickname);
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the application
                finish();
            }
        });
    }

}