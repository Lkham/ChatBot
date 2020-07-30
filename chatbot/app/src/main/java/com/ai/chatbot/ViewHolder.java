package com.ai.chatbot;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    LinearLayout leftMsgLayout;

    LinearLayout rightMsgLayout;

    TextView leftMsgTextView;

    TextView rightMsgTextView;

    public ViewHolder(View itemView) {
        super(itemView);

        if(itemView!=null) {
            leftMsgLayout = (LinearLayout) itemView.findViewById(R.id.left_msg_layout);
            rightMsgLayout = (LinearLayout) itemView.findViewById(R.id.right_msg_layout);
            leftMsgTextView = (TextView) itemView.findViewById(R.id.left_msg_textView);
            rightMsgTextView = (TextView) itemView.findViewById(R.id.right_msg_textView);
        }
    }
}
