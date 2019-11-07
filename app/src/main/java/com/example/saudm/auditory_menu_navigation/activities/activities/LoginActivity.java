
package com.example.saudm.auditory_menu_navigation.activities.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.saudm.auditory_menu_navigation.R;

import com.example.saudm.auditory_menu_navigation.activities.models.UserInformation;
import com.example.saudm.auditory_menu_navigation.activities.utils.IntentHelper;
import com.example.saudm.auditory_menu_navigation.activities.utils.Utils;

import static com.example.saudm.auditory_menu_navigation.activities.constants.Constants.USER_INFO;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private android.support.v7.widget.Toolbar mToolBar;
    private TextView activityTitleTextView;
    private EditText userIdEditText, usernameEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolBar = findViewById(R.id.toolbar);
        activityTitleTextView = findViewById(R.id.activityTitleTextView);
        userIdEditText = findViewById(R.id.userIdEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        loginButton = findViewById(R.id.loginButton);
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
        }
        activityTitleTextView.setText(getString(R.string.user_information));
        loginButton.setOnClickListener(this );
    }

    @Override
    public void onClick(View view) {
        if (view == loginButton){

            String userID = userIdEditText.getText().toString();
            String userName = usernameEditText.getText().toString();
            if (userID.isEmpty()){
                Utils.displayMessage(this,getString(R.string.provide_user_id));
                return;
            }
            if (userName.isEmpty()){
                Utils.displayMessage(this,getString(R.string.provide_username));
                return;
            }

            UserInformation userInformation = new UserInformation(userID,userName);
            IntentHelper.getInstance().addObject(USER_INFO,userInformation);
            navigate();
        }
    }

    private void navigate() {
        startActivity(new Intent(this,TouchInputActivity.class));
        finish();
    }
}
