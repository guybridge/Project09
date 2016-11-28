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
import com.teamtreehouse.ribbit.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        try
        {
            holder.timeLabel.setText(formatDate(createdAt));
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
        else if(message.getString(Message.KEY_FILE_TYPE).equals(Message.TYPE_VIDEO))
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
        else if(message.getString(Message.KEY_FILE_TYPE).equals(Constants.KEY_TEXT_FILE_TYPE))
        {
            try
            {
                holder.iconImageView.setImageResource(R.drawable.ic_send_msg);
            }
            catch(NullPointerException e)
            {
                Log.i(TAG, "Error setting text message icon " + e.getMessage());
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

    private String formatDate(Date createdAt)
    {
        long milliseconds = createdAt.getTime();
        long now = new Date().getTime();
        int GMTOffset = createdAt.getTimezoneOffset();
        Log.i(TAG, "Parse date is: " + milliseconds);
        Log.i(TAG, "Now is: " + now);
        Log.i(TAG, "GMT offset is: " + GMTOffset);

        long durationSinceMessage = now - milliseconds;

        long secondsSinceMessage = TimeUnit.MILLISECONDS.toSeconds(durationSinceMessage);
        long minutesSinceMessage = TimeUnit.MILLISECONDS.toMinutes(durationSinceMessage);
        long hoursSinceMessage = TimeUnit.MILLISECONDS.toHours(durationSinceMessage);
        long daysSinceMessage = TimeUnit.MILLISECONDS.toDays(durationSinceMessage);


        if(secondsSinceMessage < 60 && secondsSinceMessage > 1) // If the time is between 1 and 59 seconds
        {
            Log.i(TAG, "Message received: " + secondsSinceMessage + " seconds ago");
            return secondsSinceMessage + " seconds ago";
        }
        else if(secondsSinceMessage >= 60 && secondsSinceMessage < 3600) // If time is between 1 and 60 minutes
        {
            Log.i(TAG, "Message received: " + minutesSinceMessage + " minutes ago");
            return minutesSinceMessage + " minutes ago";
        }
        else if(daysSinceMessage == 1) // Return 1 hour ago for 1 hour
        {
            Log.i(TAG, "Message received: " + daysSinceMessage + " days ago");
            return daysSinceMessage + " hour ago";
        }
        else if(hoursSinceMessage > 1 && hoursSinceMessage <= 24) // If the time is between 1 and 24 hours
        {
            Log.i(TAG, "Message received: " + hoursSinceMessage + " hours ago");
            return hoursSinceMessage + " hours ago";
        }
        else if(daysSinceMessage > 1)
        {
            return daysSinceMessage + " days ago";
        }
        else
        {
            Log.i(TAG, "value was: " + secondsSinceMessage);
            return "Just now";
        }



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






