package com.teamtreehouse.ribbit.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.teamtreehouse.ribbit.R;

import org.w3c.dom.Text;

/**
 * Created by guyb on 28/11/16.
 */
public class WriteTextFragment extends DialogFragment
{
    private TextView messageData;
    private Button confirm;
    private callback callback;

    public interface callback
    {
        void messageData(String data);
        void onFail(String errorMessage);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        callback = (callback) activity;


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dialog_write_text, container, false);

        messageData = (TextView) rootView.findViewById(R.id.messageContents);
        confirm = (Button) rootView.findViewById(R.id.confirmButton);

        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                callback.messageData(messageData.getText().toString());
            }
        });

        return rootView;
    }
}
