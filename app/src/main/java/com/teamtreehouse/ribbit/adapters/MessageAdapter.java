package com.teamtreehouse.ribbit.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<ParseObject> {

    private static final String TAG = MessageAdapter.class.getSimpleName();
    protected Context mContext;
    protected List<ParseObject> mMessages;

    public MessageAdapter(Context context, List<ParseObject> messages) {
        super(context, R.layout.message_item, messages);
        mContext = context;

        // Create a full copy of mMessages
        mMessages = new ArrayList<ParseObject>();

        for (ParseObject msg : messages)
        {
            mMessages.add(msg);

        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.messageIcon);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.senderLabel);
            holder.timeLabel = (TextView) convertView.findViewById(R.id.timeLabel);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject message = mMessages.get(position);

        Date createdAt = message.getCreatedAt();
        long now = new Date().getTime();
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d");
        String convertedDate = format.format(createdAt);

        try
        {
            holder.timeLabel.setText(convertedDate);
        }
        catch(NullPointerException e)
        {
            Log.i(TAG, "Error setting time label " + e.getMessage());
        }


        if(message.getString(Message.KEY_FILE_TYPE).equals(Message.TYPE_IMAGE))
        {
            try
            {
                holder.iconImageView.setImageResource(R.drawable.ic_picture);
            }
            catch(NullPointerException e)
            {
                Log.i(TAG, "Error setting picture icon " + e.getMessage());
            }
        }
        else
        {
            try
            {
                holder.iconImageView.setImageResource(R.drawable.ic_video);
            }
            catch(NullPointerException e)
            {
                Log.i(TAG, "Error setting video icon " + e.getMessage());
            }

        }

        try
        {
            holder.nameLabel.setText(message.getString(Message.KEY_SENDER_NAME));
        }
        catch(NullPointerException e)
        {
            Log.i(TAG, "Error setting name label " + e.getMessage());
        }


        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView nameLabel;
        TextView timeLabel;
    }

    public void refill(List<ParseObject> messages) {
        mMessages.clear();
        mMessages.addAll(messages);
        notifyDataSetChanged();
    }
}






