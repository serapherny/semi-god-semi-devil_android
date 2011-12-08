package com.qc.login;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qc.R;
import com.qc.Util;

public class LoginActivity extends Activity {

    public static final String ACTION_RESULT = "action_result";
    public static final String CONTENT = "content";
    public static final String FAILED = "failed";
    public static final String SUCCESS = "suc";

    // Declare our Views, so we can access them later
    private EditText etEmail;
    private EditText etPassword;
    private EditText etNickname;
    private Button btnLogin;
    private Button btnCancel;
    private TextView lblResult;
    private Button btnSignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayLogin();
    }

    public void displayLogin() {
        setContentView(R.layout.login);

        // Get the EditText and Button References
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        etNickname = (EditText) findViewById(R.id.nickname);
        btnLogin = (Button) findViewById(R.id.login_button);
        btnSignup = (Button) findViewById(R.id.signup_button);
        btnCancel = (Button) findViewById(R.id.cancel_button);
        lblResult = (TextView) findViewById(R.id.login_result);

        btnSignup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check Login
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String nickname = etNickname.getText().toString();

                new RPCUtil().sendSignupRequest(email, password, nickname,
                        new SignupResponseHanlder());
            }
        });

        // Set Click Listener
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check Login
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                new RPCUtil().sendLoginRequest(email, password, new LoginResponseHanlder());
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

    class SignupResponseHanlder {
        public void handle(Map<String, String> response) {
            String result = response.get(ACTION_RESULT);
            if (result.startsWith(FAILED)) {
                Util.showDialog(LoginActivity.this, "注册失败：" + result, null);
            } else if (result.equals(SUCCESS)) {
                Util.isLogin = true;
                Util.showDialog(LoginActivity.this, "注册成功！", LoginActivity.this);
            }
        }
    }

    class LoginResponseHanlder {
        public void handle(Map<String, String> response) {
            String result = response.get(ACTION_RESULT);
            if (result.startsWith(FAILED)) {
                Util.showDialog(LoginActivity.this, "登陆失败：" + result, null);
            } else if (result.equals(SUCCESS)) {
                Util.isLogin = true;
                Util.showDialog(LoginActivity.this, "登陆成功！", LoginActivity.this);
            }
        }
    }
}