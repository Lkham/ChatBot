package com.ai.chatbot;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender extends AsyncTask<String,Void,Void> {
    Socket s;
    PrintWriter writer;
    String iPv4;
    int portNum1;

    public MessageSender(String iPv4, int portNum1) {
        this.iPv4=iPv4;
        this.portNum1=portNum1;
    }

    @Override
    protected Void doInBackground(String...voids){
        try{
            String message = voids[0];
            s = new Socket(iPv4,portNum1);
            writer = new PrintWriter(s.getOutputStream());
            writer.write(message);
            writer.flush();
            writer.close();
            s.close();

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
