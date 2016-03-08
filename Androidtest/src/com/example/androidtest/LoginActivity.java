package com.example.androidtest;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class LoginActivity extends Activity implements OnClickListener {
	EditText username, password;
	Button login;
	CheckBox checkbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		checkbox = (CheckBox) findViewById(R.id.remember);
		login = (Button) findViewById(R.id.login);
		init();
		login.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			if (username.getText().toString().equals("a")
					&& password.getText().toString().equals("123456")) {
				if (checkbox.isChecked()) {
					SharedPreferences sp = getPreferences(MODE_PRIVATE);
					SharedPreferences.Editor editor = sp.edit();
					editor.putString("username", username.getText().toString());
					editor.putString("password", password.getText().toString());
					editor.putBoolean("checkbox", true);
					editor.commit();
				}
				Intent intent=new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
			}else {
				
				Toast.makeText(this, "请确认输入的账号密码无误", 0).show();
			}
			break;

		default:
			break;
		}

	}

	private void init() {
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		checkbox.setChecked(sp.getBoolean("checkbox", false));

		if (checkbox.isChecked()) {

			String um = sp.getString("username", "a");
			String pw = sp.getString("password", "123456");
			username.setText(um);
			password.setText(pw);

		}
	}

}
