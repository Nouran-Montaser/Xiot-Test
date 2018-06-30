package com.example.lenovo.xiottest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MainActivity extends AppCompatActivity {

    private static final String MY_PREFS_NAME = "MyPrefsFile";
    private EditText mUserNameEdt;
    private EditText mPasswordEdt;
    private EditText mPortEdt;
    private EditText mUrlEdt;
    private String mUserName;
    private String mPassword;
    private String mPort;
    private String mUrl;
    private SharedPreferences.Editor sharedPrefsEditor;
    private SharedPreferences sharedPrefs;
    private Button mLogInBtn;
    private MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserNameEdt = findViewById(R.id.UserName);
        mPasswordEdt = findViewById(R.id.pass);
        mPortEdt = findViewById(R.id.port);
        mUrlEdt = findViewById(R.id.Url);
        mLogInBtn = findViewById(R.id.login_button);

        sharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String user_name_pref = sharedPrefs.getString("UserName", null);
        String password_pref = sharedPrefs.getString("Password", null);
        String port_pref = sharedPrefs.getString("Port", null);
        String url_pref = sharedPrefs.getString("URL", null);
        if (user_name_pref != null || password_pref != null || port_pref != null || url_pref != null) {
            Intent StartIntent = new Intent(MainActivity.this, StartActivity.class);
            StartIntent.putExtra("UserName", user_name_pref);
            StartIntent.putExtra("Password", password_pref);
            StartIntent.putExtra("Port", port_pref);
            StartIntent.putExtra("URL", url_pref);
            startActivity(StartIntent);
            finish();
        }


        mLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mUserName = mUserNameEdt.getText().toString();
                mPassword = mPasswordEdt.getText().toString();
                mPort = mPortEdt.getText().toString();
                mUrl = mUrlEdt.getText().toString();

                if (mUserName.isEmpty() || mPassword.isEmpty() || mPort.isEmpty() || mUrl.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Fill your Data", Toast.LENGTH_LONG).show();
                } else {
                    mqttHelper = new MqttHelper(getApplicationContext(), mUserName, mPassword, mPort, mUrl);
                    mqttHelper.setCallback(new MqttCallbackExtended() {
                        @Override
                        public void connectionLost(Throwable throwable) {
                            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                        }

                        @Override
                        public void connectComplete(boolean b, String s) {
                            Toast.makeText(MainActivity.this, "hoooo", Toast.LENGTH_LONG).show();
                            sharedPrefsEditor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            sharedPrefsEditor.putString("UserName", mUserName);
                            sharedPrefsEditor.putString("Password", mPassword);
                            sharedPrefsEditor.putString("Port", mPort);
                            sharedPrefsEditor.putString("URL", mUrl);
                            sharedPrefsEditor.apply();

                            Intent StartIntent = new Intent(MainActivity.this, StartActivity.class);
                            StartIntent.putExtra("UserName", mUserName);
                            StartIntent.putExtra("Password", mPassword);
                            StartIntent.putExtra("Port", mPort);
                            StartIntent.putExtra("URL", mUrl);
                            startActivity(StartIntent);
                            finish();
                        }
                    });
                }


            }
        });

    }
}
