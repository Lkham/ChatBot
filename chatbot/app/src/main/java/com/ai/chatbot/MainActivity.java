package com.ai.chatbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    EditText msgInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get values from intent method (from the Settings layout)
        Intent intent = getIntent();
        final String IPv4 = intent.getStringExtra(Settings.EXTRA_IP);
        final int portNum1 = intent.getIntExtra(Settings.EXTRA_PORT1,8000);
        int portNum2 = intent.getIntExtra(Settings.EXTRA_PORT2,8001);


        //Get RecyclerView object.
        final RecyclerView msgRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        // Set RecyclerView layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(linearLayoutManager);

        // Create the initial data list.
        final List<ResponseMessage> msgList = new ArrayList<ResponseMessage>();

        // Create the data adapter with above data list.
        final Adapter MsgAdapter = new Adapter(msgList);

        // Set data adapter to RecyclerView.
        msgRecyclerView.setAdapter(MsgAdapter);

        Thread serverThread = new Thread(new ReceiverThread(msgList,msgRecyclerView,MsgAdapter,portNum2));
        serverThread.start();

        msgInputText = (EditText)findViewById(R.id.input_msg);

        ImageButton msgSendButton = (ImageButton)findViewById(R.id.send_msg);

        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgContent = msgInputText.getText().toString();
                if(!TextUtils.isEmpty(msgContent))
                {
                    MessageSender messageSender = new MessageSender(IPv4,portNum1);
                    messageSender.execute(msgContent);

//                  Add a new sent message to the list.
                    ResponseMessage msg = new ResponseMessage(ResponseMessage.MSG_TYPE_SENT, msgContent);
                    msgList.add(msg);

                    int newMsgPosition = msgList.size() - 1;

                    // Notify recycler view insert one new data.
                    MsgAdapter.notifyItemInserted(newMsgPosition);

                    // Scroll RecyclerView to the last message.
                    msgRecyclerView.scrollToPosition(newMsgPosition);

                    // Empty the input edit text box.
                    msgInputText.setText("");
                }
            }
        });

        ImageButton mVoiceBtn = (ImageButton)findViewById(R.id.voiceBtn);
        mVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void speak() {
        //intent to show speech to text dialog
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi speak something");

        //start intent
        try{ //in there was no error, show dialog
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

        }catch (Exception e){
            //if there was some error, get message of error and show
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //receive voice input and handle it
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(resultCode == RESULT_OK && null!=data){
                    //get text array from voice intent
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //set to text view
                    msgInputText.setText(result.get(0));
                }
                break;
            }
        }
    }

    class ReceiverThread implements Runnable{
        Socket s;
        ServerSocket ss;
        InputStreamReader isr;
        BufferedReader bufferedReader;
        String message;

        List<ResponseMessage> msgList;
        RecyclerView msgRecyclerView;
        Adapter MsgAdapter;
        int portNum2;

        ReceiverThread(List<ResponseMessage> msgList,
                       RecyclerView msgRecyclerView, Adapter MsgAdapter, int portNum2){
            this.msgList=msgList;
            this.msgRecyclerView=msgRecyclerView;
            this.MsgAdapter=MsgAdapter;
            this.portNum2=portNum2;
        }

        @Override
        public void run(){
            try{
                ss = new ServerSocket(portNum2);
                while(true){
                    s = ss.accept();
                    isr = new InputStreamReader(s.getInputStream());
                    bufferedReader = new BufferedReader(isr);
                    message = bufferedReader.readLine();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //Add a new sent message to the list.
                            ResponseMessage msg = new ResponseMessage(ResponseMessage.MSG_TYPE_RECEIVED, message);
                            msgList.add(msg);

                            int newMsgPosition = msgList.size() - 1;
                            MsgAdapter.notifyItemInserted(newMsgPosition);
                            msgRecyclerView.scrollToPosition(newMsgPosition);

                        }
                    });
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.setting){
            Intent in = new Intent(MainActivity.this, Settings.class);
            startActivity(in);
        }
        return true;
    }

}
