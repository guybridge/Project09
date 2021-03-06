package com.teamtreehouse.ribbit.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.MessageAdapter;
import com.teamtreehouse.ribbit.models.Message;

import com.teamtreehouse.ribbit.utils.Constants;

import java.util.List;

public class InboxFragment extends ListFragment {

    private static final String TAG = InboxFragment.class.getSimpleName();
    protected List<ParseObject> mMessages;
    protected SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox,
                container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);


        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swipeRefresh1,
                R.color.swipeRefresh2,
                R.color.swipeRefresh3,
                R.color.swipeRefresh4);


        if(ParseUser.getCurrentUser() != null)
        {
            retrieveMessages();
        }
        else
        {
            Log.i(TAG, "Current user is null");
        }


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    private void retrieveMessages()
    {
        
        Log.i(TAG, "Current user is: " + ParseUser.getCurrentUser().getUsername());
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.KEY_MESSAGES);
        query.whereEqualTo(Message.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());
        query.addDescendingOrder(Message.KEY_CREATED_AT);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> messages, ParseException e)
            {

                if (mSwipeRefreshLayout.isRefreshing())
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                if (e == null)
                {
                    // We found messages!
                    Log.i(TAG, "We found " + messages.size() + " messages");
                    mMessages = messages;

                    String[] usernames = new String[mMessages.size()];
                    int i = 0;
                    for (ParseObject message : mMessages)
                    {
                        usernames[i] = message.getString(Message.KEY_SENDER_NAME);
                        i++;
                    }
                    if (getListView().getAdapter() == null)
                    {
                        Log.i(TAG, "Adapter is null, creating a new adapter");
                        MessageAdapter adapter = new MessageAdapter(getListView().getContext(), mMessages);
                        setListAdapter(adapter);
                    } else {
                        // refill the adapter!
                        Log.i(TAG, "Refilling adapter");
                        MessageAdapter adapter = new MessageAdapter(getListView().getContext(), mMessages);
                        setListAdapter(adapter);
                    }
                }
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject message = mMessages.get(position);
        String messageType = message.getString(Message.KEY_FILE_TYPE);

        // Text message
        if(messageType.equals(Constants.KEY_TEXT_FILE_TYPE))
        {
            Log.i(TAG, "msg is text");
            String msg = message.getString(Constants.KEY_TEXT_DATA);
            Intent intent = new Intent(getActivity(), ViewImageActivity.class);
            intent.putExtra(Constants.KEY_TEXT_DATA, msg);
            startActivity(intent);

        }

        if (messageType.equals(Message.TYPE_IMAGE))
        {
            ParseFile file = message.getParseFile(Message.KEY_FILE);
            String fileUri = file.getUrl();
            // view the image
            Intent intent = new Intent(getActivity(), ViewImageActivity.class);
            intent.setData(Uri.parse(fileUri));
            startActivity(intent);
        }
        else if(message.equals(Message.TYPE_VIDEO))
        {
            ParseFile file = message.getParseFile(Message.KEY_FILE);
            String fileUri = file.getUrl();
            // view the video
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileUri));
            intent.setDataAndType(Uri.parse(fileUri), "video/*");
            startActivity(intent);
        }

        // Delete it!
        deleteMessage(message);
    }

    private void deleteMessage(ParseObject message)
    {
        List<String> ids = message.getList(Message.KEY_RECIPIENT_IDS);

        if (ids.size() == 1) {
            // last recipient - delete the whole thing!
            message.deleteInBackground();
        }
        else
        {
            // remove the recipient
            message.remove(ParseUser.getCurrentUser().getObjectId());
        }
    }


    protected OnRefreshListener mOnRefreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh() {
            retrieveMessages();
        }
    };
}








