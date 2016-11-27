package com.teamtreehouse.ribbit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.teamtreehouse.ribbit.models.User;

/**
 * Class to cache a users login credentials
 */
public class UserHelper
{
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String username;
    private String password;
    private String email;

    public UserHelper(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFS_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        username = sharedPreferences.getString(Constants.KEY_USERNAME, "");
        password = sharedPreferences.getString(Constants.KEY_PASSWORD, "");
        email = sharedPreferences.getString(Constants.KEY_EMAIL, "");

    }

    public void cacheLogin(User user)
    {
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();

        editor.putString(Constants.KEY_USERNAME, username);
        editor.putString(Constants.KEY_PASSWORD, password);
        editor.putString(Constants.KEY_EMAIL, email);
        editor.apply();

    }

    public void logout()
    {
        editor.clear();
        editor.apply();
    }

    public boolean isAuthenticated()
    {
        if(username != null && password != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }



}
