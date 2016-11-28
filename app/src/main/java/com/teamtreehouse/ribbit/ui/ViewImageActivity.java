package com.teamtreehouse.ribbit.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.utils.Constants;


public class ViewImageActivity extends AppCompatActivity
{

    private TextView countDownTextView;
    private TextView textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        // Show the Up button in the action bar.
        setupActionBar();

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        countDownTextView = (TextView) findViewById(R.id.countDownText);
        textMessage = (TextView) findViewById(R.id.textMessageTextView);

        String textData = getIntent().getStringExtra(Constants.KEY_TEXT_DATA);
        if(textData != null)
        {
            // We got a text, display
            textMessage.setVisibility(View.VISIBLE);
            textMessage.setText(textData);
        }
        else
        {
            Uri imageUri = getIntent().getData();
            Picasso.with(this).load(imageUri.toString()).into(imageView);
        }

        countDown();
    }

    private void countDown()
    {
        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                countDownTextView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish()
            {
                finish();
            }
        };

        countDownTimer.start();
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
