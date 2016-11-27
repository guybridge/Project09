package com.teamtreehouse.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.teamtreehouse.ribbit.mockdata.MockMessages;
import com.teamtreehouse.ribbit.mockdata.MockUsers;
import com.teamtreehouse.ribbit.models.User;

/**
 * Created by benjakuben on 10/12/16.
 */

public class RibbitApplication extends Application {

    public static String PACKAGE_NAME;

    @Override
    public void onCreate() {
        super.onCreate();
        MockUsers.initialize();

        // Connect to Parse instance on back4app
        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("9Tk9TCOMLrdN87QnFaetrKOdgPVT7Ic7cIBtVDKN")
                        .clientKey("OHYkfbclgLG6UhdapIlZwy7icrJcLDZs24qZpSze")
                        .server("https://parseapi.back4app.com")
                        .build());

        PACKAGE_NAME = getApplicationContext().getPackageName();
    }
}
