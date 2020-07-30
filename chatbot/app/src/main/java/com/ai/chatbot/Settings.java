package com.ai.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Settings extends AppCompatActivity {
    public static final String EXTRA_IP = "com.ai.chatbot.EXTRA_IP";
    public static final String EXTRA_PORT1 = "com.ai.chatbot.EXTRA_PORT1";
    public static final String EXTRA_PORT2 = "com.ai.chatbot.EXTRA_PORT2";

    private SharedPreferences Preferences;
    private SharedPreferences.Editor Editor;

    private EditText mIP, mPort1,mPort2;
    private Button btnOk;
    private android.widget.CheckBox CheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        mIP = (EditText)findViewById(R.id.ipAdd);
        mPort1 = (EditText)findViewById(R.id.port1);
        mPort2 = (EditText)findViewById(R.id.port2);
        btnOk = (Button) findViewById(R.id.btnOk);
        CheckBox = (CheckBox)findViewById(R.id.checkbox);
        Preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Editor = Preferences.edit();

        checkSharedPreferences();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save the checkbox preference
                if(CheckBox.isChecked()){
                    //set a checkbox when the application starts
                    Editor.putString(getString(R.string.checkbox),"True");
                    Editor.commit();

                    //save the ip address
                    String ip = mIP.getText().toString();
                    Editor.putString(getString(R.string.ip),ip);
                    Editor.commit();

                    //save the port number 1
                    String port1 = mPort1.getText().toString();
                    Editor.putString(getString(R.string.port1),port1);
                    Editor.commit();

                    //save the port number 2
                    String port2 = mPort2.getText().toString();
                    Editor.putString(getString(R.string.port2),port2);
                    Editor.commit();
                }else{
                    //set a checkbox when the application starts
                    Editor.putString(getString(R.string.checkbox),"False");
                    Editor.commit();

                    //save the ip address
                    Editor.putString(getString(R.string.ip),"");
                    Editor.commit();

                    //save the port number 1
                    Editor.putString(getString(R.string.port1),"");
                    Editor.commit();

                    //save the port number 2
                    Editor.putString(getString(R.string.port2),"");
                    Editor.commit();
                }

                String sIP = mIP.getText().toString();
                int s_port1 = Integer.parseInt(mPort1.getText().toString());
                int s_port2 = Integer.parseInt(mPort2.getText().toString());
                Intent intent = new Intent(Settings.this, MainActivity.class);
                intent.putExtra(EXTRA_IP, sIP);
                intent.putExtra(EXTRA_PORT1, s_port1);
                intent.putExtra(EXTRA_PORT2, s_port2);
                startActivity(intent);
            }

        });
    }

    private void checkSharedPreferences(){
        String checkbox = Preferences.getString(getString(R.string.checkbox),"False");
        String ip = Preferences.getString(getString(R.string.ip),"");
        String Port1 = Preferences.getString(getString(R.string.port1),"");
        String Port2 = Preferences.getString(getString(R.string.port2),"");

        mIP.setText(ip);
        mPort1.setText(Port1);
        mPort2.setText(Port2);

        if(checkbox.equals("True")){
            CheckBox.setChecked(true);
        }else{
            CheckBox.setChecked(false);
        }

    }
}
