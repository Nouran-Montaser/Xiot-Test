package com.example.lenovo.xiottest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lenovo.xiottest.Data.Chat;
import com.example.lenovo.xiottest.Data.MyDataBase;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    private static final String MY_PREFS_NAME = "MyPrefsFile";
    private RecyclerView mMessagesist;
    private List<Chat> messageList;
    private LinearLayoutManager mLinearLayoutManager;
    private MqttHelper mqttHelper;
    private Toolbar mToolbar;
    private String mURI;
    private String mUserName;
    private String mPassword;
    private String mPort;
    private SharedPreferences.Editor sharedPrefsEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mToolbar = findViewById(R.id.start_page_toobar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Chat");
        messageList = new ArrayList<>();
        findViewById(R.id.Bye_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttHelper.publishTopic("bye");
            }
        });
        findViewById(R.id.Hi_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttHelper.publishTopic("HI");
            }
        });
        mMessagesist = findViewById(R.id.message_list);

        mLinearLayoutManager = new LinearLayoutManager(this);

        mMessagesist.setHasFixedSize(true);
        mMessagesist.setLayoutManager(mLinearLayoutManager);

        mUserName = getIntent().getStringExtra("UserName");
        mPassword = getIntent().getStringExtra("Password");
        mPort = getIntent().getStringExtra("Port");
        mURI = getIntent().getStringExtra("URL");

        mqttHelper = new MqttHelper(getApplicationContext(), mUserName, mPassword, mPort, mURI);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectionLost(Throwable throwable) {
                Log.e("mqtt", throwable.getMessage());
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) {
                Chat chat = new Chat();
                chat.setName("lol");
                chat.setMessage(mqttMessage.toString());
                messageList.add(chat);
                MyDataBase.getAppDatabase(getApplicationContext()).userDao().insertAll(chat);
                mMessagesist.setAdapter(new MyAdapter(messageList));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }

            @Override
            public void connectComplete(boolean b, String s) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        messageList = App.get().getDB().userDao().getAll();
                    }
                }).start();
                Log.e("mqtt", s);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout_button) {
            sendTolOGIN();
        }
        return true;
    }

    private void sendTolOGIN() {

        mqttHelper.disconnect(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken iMqttToken) {

                sharedPrefsEditor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                sharedPrefsEditor.putString("UserName", null);
                sharedPrefsEditor.putString("Password", null);
                sharedPrefsEditor.putString("Port", null);
                sharedPrefsEditor.putString("URL", null);
                sharedPrefsEditor.apply();
                Intent LogInIntent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(LogInIntent);
                finish();//when we are don't need to go back (no back button)
            }

            @Override
            public void onFailure(IMqttToken iMqttToken, Throwable throwable) {

            }
        });

    }

}


